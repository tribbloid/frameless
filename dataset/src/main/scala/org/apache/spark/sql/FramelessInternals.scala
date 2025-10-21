package org.apache.spark.sql

import org.apache.spark.sql.catalyst.expressions._
import org.apache.spark.sql.catalyst.expressions.codegen._
import org.apache.spark.sql.catalyst.expressions.{ Alias, CreateStruct }
import org.apache.spark.sql.catalyst.expressions.{ Expression, NamedExpression }
import org.apache.spark.sql.catalyst.InternalRow
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import org.apache.spark.sql.catalyst.plans.logical.{ LogicalPlan, Project }
import org.apache.spark.sql.catalyst.trees.Origin
import org.apache.spark.sql.execution.QueryExecution
import org.apache.spark.sql.types._
import org.apache.spark.sql.types.ObjectType
import scala.reflect.ClassTag

object FramelessInternals {

  def objectTypeFor[A](
      implicit
      classTag: ClassTag[A]
    ): ObjectType = ObjectType(classTag.runtimeClass)

  def resolveExpr(ds: Dataset[_], colNames: Seq[String]): NamedExpression = {
    ds.toDF()
      .queryExecution
      .analyzed
      .resolve(colNames, ds.sparkSession.sessionState.analyzer.resolver)
      .getOrElse {
        val errorMsg =
          s"""Cannot resolve column name "$colNames" among (${ds.schema.fieldNames
              .mkString(", ")})"""
        // Spark 4 uses SparkException.internalError for internal errors
        throw org.apache.spark.SparkException.internalError(errorMsg)
      }
  }

def expr(column: Column): Expression = {
    // Spark 4.x optimized version - use direct Column.node access
    val columnNode = column.node

    // Primary strategy: Check if the node is already an Expression
    columnNode match {
      case e: Expression =>
        // Direct Expression - return it even if unresolved
        // Frameless works with unresolved expressions and resolves them later
        return e
      case _ => // Continue to next strategies
    }

    // Secondary strategy: Try SQL string parsing (public API)
    try {
      val sqlString = column.toString
      val spark = org.apache.spark.sql.SparkSession.active
      val parsed = spark.sessionState.sqlParser.parseExpression(sqlString)
      return parsed
    } catch {
      case _: Exception => // Continue to next strategy
    }

    // Tertiary strategy: Use analyzer to resolve the column
    try {
      val spark = org.apache.spark.sql.SparkSession.active
      val dummyDf = spark.range(1).select(column)
      val analyzed = dummyDf.queryExecution.analyzed

      analyzed match {
        case org.apache.spark.sql.catalyst.plans.logical.Project(projectList, _) if projectList.nonEmpty =>
          projectList.head match {
            case alias: org.apache.spark.sql.catalyst.expressions.Alias => alias.child
            case e: Expression => e
            case other =>
              throw new UnsupportedOperationException(
                s"Unsupported analyzed project element: ${other.getClass.getName}"
              )
          }
        case other =>
          throw new UnsupportedOperationException(
            s"Cannot extract Expression from Column. Analyzed plan: ${other.getClass.getName}"
          )
      }
    } catch {
      case e: Exception =>
        throw new UnsupportedOperationException(
          s"Cannot extract Expression from Column using any available strategy. " +
            s"Column node type: ${columnNode.getClass.getName}. Error: ${e.getMessage}",
          e
        )
    }
  }  /**
   * Creates a Column from a Catalyst Expression.
   * This method provides a consistent API across Spark versions.
   * In Spark 3.x, Column constructor accepts Expression directly.
   * In Spark 4.x, Column constructor requires ColumnNode.
   */
  def column(expr: Expression): Column = {
    try {
      // Try Spark 3.x approach first: new Column(expr)
      val columnConstructor =
        classOf[Column].getConstructor(classOf[Expression])
      columnConstructor.newInstance(expr)
    } catch {
      case _: NoSuchMethodException =>
        // Spark 4.x approach: try to rebuild certain expressions via public functions API first
        try {
          val className = expr.getClass.getName
          if (
            className == "org.apache.spark.sql.catalyst.expressions.Levenshtein" && expr.children.size == 2
          ) {
            val leftCol = column(expr.children(0))
            val rightCol = column(expr.children(1))
            return org.apache.spark.sql.functions.levenshtein(leftCol, rightCol)
          }
        } catch { case _: Throwable => () }

        // Then: new Column(ExpressionColumnNode(expr, Origin()))
        try {
          // Spark 4 renamed/moved the ColumnNode wrappers. Try internal first, then classic.
          val exprColNodeClass =
            try {
              Class.forName(
                "org.apache.spark.sql.internal.ExpressionColumnNode"
              )
            } catch {
              case _: Throwable =>
                Class.forName(
                  "org.apache.spark.sql.classic.ExpressionColumnNode"
                )
            }
          val origin = new Origin(None, None, None, None, None, None, None, None)

          // Prefer (Expression, Origin) constructor, fallback to (Expression)
          val exprCtorOpt: Option[java.lang.reflect.Constructor[_]] =
            try {
              Some(
                exprColNodeClass
                  .getConstructor(classOf[Expression], origin.getClass)
              )
            } catch { case _: Throwable => None }

          val exprCtor: java.lang.reflect.Constructor[_] =
            exprCtorOpt.getOrElse {
              try {
                exprColNodeClass.getConstructor(classOf[Expression])
              } catch {
                case _: Throwable =>
                  throw new NoSuchMethodException(
                    "ExpressionColumnNode constructor not found"
                  )
              }
            }

          val exprColNode =
            if (exprCtor.getParameterTypes.length == 2)
              exprCtor.newInstance(expr, origin)
            else exprCtor.newInstance(expr)

          // Find a single-arg Column constructor whose param type is assignable from the exprColNode class
          val exprNodeCls = exprColNode.getClass
          val columnCtorOpt = classOf[Column].getConstructors.find { c =>
            val pts = c.getParameterTypes
            pts.length == 1 && pts(0).isAssignableFrom(exprNodeCls)
          }
          val columnConstructor = columnCtorOpt.getOrElse {
            throw new RuntimeException(
              s"No suitable Column constructor found for ${exprNodeCls.getName}. Constructors:\n" +
                classOf[Column].getConstructors.map { cc =>
                  val ps =
                    cc.getParameterTypes.map(_.getName).mkString("(", ", ", ")")
                  s"<init>$ps"
                }.mkString("\n")
            )
          }
          columnConstructor.newInstance(exprColNode).asInstanceOf[Column]
        } catch {
          case e: Exception =>
            // Fallback: construct Column via SQL string representation
            // This avoids relying on internal ColumnNode classes in Spark 4.x
            try {
              val sqlMethod = classOf[Expression].getMethod("sql")
              val sqlString = sqlMethod.invoke(expr).asInstanceOf[String]
              val functionsClass =
                Class.forName("org.apache.spark.sql.functions")
              val exprMethod = functionsClass.getMethod("expr", classOf[String])
              exprMethod.invoke(null, sqlString).asInstanceOf[Column]
            } catch {
              case ee: Exception =>
                throw new RuntimeException(
                  s"Could not create Column from Expression in Spark 3 or 4 (both direct and SQL fallback failed): ${ee.getMessage}",
                  ee
                )
            }
        }
    }
  }

  /**
   * Helper to access SQLContext for Spark version compatibility.
   * In Spark 3.x, Dataset has sqlContext field directly.
   * In Spark 4.x, it was removed, use sparkSession.sqlContext instead.
   */
  def sqlContext(ds: Dataset[_]): SQLContext = {
    try {
      // Spark 3.x: ds.sqlContext
      val sqlContextMethod = ds.getClass.getMethod("sqlContext")
      sqlContextMethod.invoke(ds).asInstanceOf[SQLContext]
    } catch {
      case _: NoSuchMethodException =>
        // Spark 4.x: ds.sparkSession.sqlContext
        ds.sparkSession.sqlContext
    }
  }

  def logicalPlan(ds: Dataset[_]): LogicalPlan = {
    try {
      // Spark 3.x: ds.logicalPlan
      val logicalPlanMethod = ds.getClass.getMethod("logicalPlan")
      logicalPlanMethod.invoke(ds).asInstanceOf[LogicalPlan]
    } catch {
      case _: NoSuchMethodException =>
        // Spark 4.x: ds.queryExecution.logical
        ds.queryExecution.logical
    }
  }

  def executePlan(ds: Dataset[_], plan: LogicalPlan): QueryExecution =
    ds.sparkSession.sessionState.executePlan(plan)

  def joinPlan(
      ds: Dataset[_],
      plan: LogicalPlan,
      leftPlan: LogicalPlan,
      rightPlan: LogicalPlan
    ): LogicalPlan = {
    val joined = executePlan(ds, plan)
    val leftOutput = joined.analyzed.output.take(leftPlan.output.length)
    val rightOutput = joined.analyzed.output.takeRight(rightPlan.output.length)

    Project(
      List(
        Alias(CreateStruct(leftOutput), "_1")(),
        Alias(CreateStruct(rightOutput), "_2")()
      ),
      joined.analyzed
    )
  }

  def mkDataset[T](
      sqlContext: SQLContext,
      plan: LogicalPlan,
      encoder: Encoder[T]
    ): Dataset[T] = {
    val df = ofRows(sqlContext.sparkSession, plan)
    df.as[T](encoder)
  }

  def ofRows(
      sparkSession: SparkSession,
      logicalPlan: LogicalPlan
    ): DataFrame = {
    try {
      // Spark 3.x: Dataset.ofRows(sparkSession, logicalPlan)
      val ofRowsMethod = Class
        .forName("org.apache.spark.sql.Dataset")
        .getMethod("ofRows", classOf[SparkSession], classOf[LogicalPlan])
      ofRowsMethod
        .invoke(null, sparkSession, logicalPlan)
        .asInstanceOf[DataFrame]
    } catch {
      case _: NoSuchMethodException =>
        // Spark 4.x: Use package helper to access internal APIs
        // This preserves the logical plan structure (critical for joins, etc.)
        Spark40DatasetHelper.createDataFrame(sparkSession, logicalPlan)
    }
  }

  // because org.apache.spark.sql.types.UserDefinedType is private[spark]
  type UserDefinedType[A >: Null] =
    org.apache.spark.sql.types.UserDefinedType[A]

  // below only tested in SelfJoinTests.colLeft and colRight are equivalent to col outside of joins
  //  - via files (codegen) forces doGenCode eval.
  /** Expression to tag columns from the left hand side of join expression. */
  case class DisambiguateLeft[T](tagged: Expression)
      extends Expression
      with NonSQLExpression {
    def eval(input: InternalRow): Any = tagged.eval(input)
    def nullable: Boolean = false
    def children: Seq[Expression] = tagged :: Nil
    def dataType: DataType = tagged.dataType

    protected def doGenCode(ctx: CodegenContext, ev: ExprCode): ExprCode =
      tagged.genCode(ctx)

    protected def withNewChildrenInternal(
        newChildren: IndexedSeq[Expression]
      ): Expression = copy(newChildren.head)
  }

  /** Expression to tag columns from the right hand side of join expression. */
  case class DisambiguateRight[T](tagged: Expression)
      extends Expression
      with NonSQLExpression {
    def eval(input: InternalRow): Any = tagged.eval(input)
    def nullable: Boolean = false
    def children: Seq[Expression] = tagged :: Nil
    def dataType: DataType = tagged.dataType

    protected def doGenCode(ctx: CodegenContext, ev: ExprCode): ExprCode =
      tagged.genCode(ctx)

    protected def withNewChildrenInternal(
        newChildren: IndexedSeq[Expression]
      ): Expression = copy(newChildren.head)
  }
}
