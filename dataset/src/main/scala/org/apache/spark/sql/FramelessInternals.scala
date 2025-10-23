package org.apache.spark.sql

import org.apache.spark.sql.catalyst.expressions._
import org.apache.spark.sql.catalyst.expressions.codegen._
import org.apache.spark.sql.catalyst.InternalRow
import org.apache.spark.sql.catalyst.plans.logical._
import org.apache.spark.sql.execution.QueryExecution
import org.apache.spark.sql.types._
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
        throw org.apache.spark.SparkException.internalError(errorMsg)
      }
  }

  def expr(column: Column): Expression = {
    // Spark 4.x optimized version - use public APIs
    try {
      val sqlString = column.toString
      val spark = SparkSession.active
      val parsed = spark.sessionState.sqlParser.parseExpression(sqlString)
      return parsed
    } catch {
      case _: Exception =>
        // Fallback to analyzer approach
        val spark = SparkSession.active
        val dummyDf = spark.range(1).select(column)
        val analyzed = dummyDf.queryExecution.analyzed
        analyzed match {
          case Project(projectList, _) if projectList.nonEmpty =>
            projectList.head match {
              case alias: Alias => alias.child
              case e: Expression => e
              case other => throw new UnsupportedOperationException(s"Cannot extract expression from column: $other")
            }
          case other => throw new UnsupportedOperationException(s"Cannot extract expression from analyzed plan: $other")
        }
    }
  }

  def column(expr: Expression): Column = {
    // Spark 4.x: Use functions.expr with expression's SQL representation
    // This is the most reliable public API approach
    try {
      functions.expr(expr.sql)
    } catch {
      case _: Exception =>
        // Fallback: Use expression toString if SQL representation fails
        functions.expr(expr.toString)
    }
  }

  def sqlContext(ds: Dataset[_]): SQLContext = {
    // Spark 4.x: use sparkSession.sqlContext
    ds.sparkSession.sqlContext
  }

  def logicalPlan(ds: Dataset[_]): LogicalPlan = {
    // Spark 4.x: use queryExecution.logical
    ds.queryExecution.logical
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
    // Spark 4.x: Use Spark40DatasetHelper for creating DataFrames from logical plans
    Spark40DatasetHelper.createDataFrame(sparkSession, logicalPlan)
  }

  // because org.apache.spark.sql.types.UserDefinedType is private[spark]
  type UserDefinedType[A >: Null] =
    org.apache.spark.sql.types.UserDefinedType[A]

  // Expression to tag columns from left hand side of join expression.
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

  // Expression to tag columns from right hand side of join expression.
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