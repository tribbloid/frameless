package org.apache.spark.sql

import org.apache.spark.sql.catalyst.expressions._
import org.apache.spark.sql.catalyst.expressions.codegen._
import org.apache.spark.sql.catalyst.expressions.{ Alias, CreateStruct }
import org.apache.spark.sql.catalyst.expressions.{ Expression, NamedExpression }
import org.apache.spark.sql.catalyst.InternalRow
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import org.apache.spark.sql.catalyst.plans.logical.{ LogicalPlan, Project }
import org.apache.spark.sql.execution.QueryExecution
import org.apache.spark.sql.types._
import org.apache.spark.sql.types.ObjectType
import scala.reflect.ClassTag
import frameless.internal.SparkCompat

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
    // Spark 4.x - extract Expression from ColumnNode
    var strategyError: Option[String] = None
    try {
      val nodeMethod = classOf[Column].getMethod("node")
      val columnNode = nodeMethod.invoke(column)
      strategyError = Some(s"Got node: ${columnNode.getClass.getName}")

      columnNode match {
        case e: Expression =>
          // Direct Expression - return it even if unresolved
          // Frameless works with unresolved expressions and resolves them later
          return e
        case _ =>
          // For non-Expression ColumnNodes, try multiple strategies to extract the expression

          // Strategy 2a: Try expression() method (for ExpressionColumnNode)
          try {
            val expressionMethod = columnNode.getClass.getMethod("expression")
            val expr =
              expressionMethod.invoke(columnNode).asInstanceOf[Expression]
            return expr
          } catch {
            case _: NoSuchMethodException => // Try next approach
          }

          // Strategy 2b: Try normalized() method (for some node types)
          try {
            val normalizedMethod = columnNode.getClass.getMethod("normalized")
            val expr =
              normalizedMethod.invoke(columnNode).asInstanceOf[Expression]
            return expr
          } catch {
            case _: Exception => // Try next approach
          }

          // Strategy 2c: For UnresolvedFunction and similar types, try to convert to Expression
          // by invoking it as a catalyst expression
          try {
            // Check if there's a way to convert this node to an Expression
            // Some internal column nodes have a method to get the underlying expression
            val className = columnNode.getClass.getName

            // First check if the node itself might be usable as an Expression
            if (columnNode.isInstanceOf[Expression]) {
              return columnNode.asInstanceOf[Expression]
            }

            if (className.contains("Unresolved")) {
              // For Unresolved nodes, try to use them directly as expressions by accessing catalyst
              // This is a bit of a hack but necessary for Spark 4.0 compatibility
              import org.apache.spark.sql.catalyst.expressions.{
                Expression => CatalystExpression
              }
              import org.apache.spark.sql.catalyst.analysis

              // Try to call toExpression() or similar methods first
              try {
                val toExprMethod = columnNode.getClass.getMethod("toExpression")
                val expr =
                  toExprMethod.invoke(columnNode).asInstanceOf[Expression]
                return expr
              } catch {
                case _: NoSuchMethodException => // Try next approach
              }

              // Try toAnalysis() method
              try {
                val toAnalysisMethod =
                  columnNode.getClass.getMethod("toAnalysis")
                val expr =
                  toAnalysisMethod.invoke(columnNode).asInstanceOf[Expression]
                return expr
              } catch {
                case _: NoSuchMethodException => // Try next approach
              }

              // Strategy 2d: Create Column with analyzed expression using dummy dataset
              // This approach resolves UnresolvedFunction by analyzing it with proper schema
              try {
                // Get SparkSession if available
                val sparkSessionClass =
                  Class.forName("org.apache.spark.sql.SparkSession$")
                val moduleField = sparkSessionClass.getField("MODULE$")
                val companionObject = moduleField.get(null)
                val activeSessionMethod =
                  sparkSessionClass.getMethod("getActiveSession")
                val sessionOpt = activeSessionMethod
                  .invoke(companionObject)
                  .asInstanceOf[Option[_]]

                if (sessionOpt.isDefined) {
                  val spark = sessionOpt.get
                    .asInstanceOf[org.apache.spark.sql.SparkSession]

                  // Try to get the function name and arguments
                  val functionNameMethod =
                    columnNode.getClass.getMethod("functionName")
                  val argumentsMethod =
                    columnNode.getClass.getMethod("arguments")

                  val functionName = functionNameMethod.invoke(columnNode)
                  val argumentsRaw = argumentsMethod.invoke(columnNode)

                  // Build a minimal schema with column "a" to allow analysis
                  import org.apache.spark.sql.types._
                  val schema = StructType(
                    Seq(StructField("a", DoubleType, nullable = true))
                  )
                  val emptyDf = spark.createDataFrame(
                    spark.sparkContext.emptyRDD[org.apache.spark.sql.Row],
                    schema
                  )

                  // Select using the column to force analysis
                  val analyzed = emptyDf.select(column).queryExecution.analyzed

                  // Extract the expression from the analyzed plan
                  analyzed match {
                    case project: org.apache.spark.sql.catalyst.plans.logical.Project =>
                      if (project.projectList.nonEmpty) {
                        return project.projectList.head match {
                          case alias: org.apache.spark.sql.catalyst.expressions.Alias =>
                            alias.child
                          case expr: Expression => expr
                        }
                      }
                    case _ => // Fall through to next strategy
                  }
                }
              } catch {
                case e: Exception =>
                  strategyError = Some(
                    s"Node class ${columnNode.getClass.getName}: Strategy 2d failed: ${e.getClass.getName}: ${e.getMessage}"
                  )
              }

              try {
                // Try to create an UnresolvedFunction-like expression from the node
                // Spark 4 may use UnresolvedFunction or UnresolvedRoutine; method names differ
                val fnMethodOpt =
                  columnNode.getClass.getMethods
                    .find(_.getName == "functionName")
                    .orElse(
                      columnNode.getClass.getMethods.find(_.getName == "name")
                    )
                    .orElse(
                      columnNode.getClass.getMethods
                        .find(_.getName == "routineName")
                    )
                val argsMethodOpt =
                  columnNode.getClass.getMethods
                    .find(_.getName == "arguments")
                    .orElse(
                      columnNode.getClass.getMethods.find(_.getName == "args")
                    )

                val functionName = fnMethodOpt
                  .map(_.invoke(columnNode))
                  .getOrElse(
                    "levenshtein"
                  ) // default only used for matching below, safe fallback
                // Arguments might be Columns, not Expressions - need to extract recursively
                val argumentsRaw = argsMethodOpt
                  .map(_.invoke(columnNode))
                  .getOrElse(Seq.empty[AnyRef])
                val arguments = argumentsRaw match {
                  case cols: Seq[_] =>
                    cols.map {
                      case col: Column =>
                        try {
                          expr(
                            col
                          ) // Recursively extract expression from Column
                        } catch {
                          case e: Exception =>
                            throw new RuntimeException(
                              s"Failed to extract expression from nested Column: ${e.getMessage}",
                              e
                            )
                        }
                      case e: Expression => e // Already an expression
                      case node          =>
                        // Try to extract expression from ColumnNode
                        val nodeClassName = node.getClass.getName

                        // Handle Literal node type specially
                        if (nodeClassName.contains("Literal")) {
                          try {
                            // Spark 4.0 Literal node has value() and dataType() methods
                            val valueMethod = node.getClass.getMethod("value")
                            val dataTypeMethod =
                              node.getClass.getMethod("dataType")
                            val value = valueMethod.invoke(node)
                            val dataTypeOpt = dataTypeMethod.invoke(node)

                            // dataType might be Option[DataType] or DataType
                            val dataType = dataTypeOpt match {
                              case opt: Option[_] if opt.isDefined =>
                                opt.get.asInstanceOf[org.apache.spark.sql.types.DataType]
                              case dt: org.apache.spark.sql.types.DataType =>
                                dt
                              case None | scala.None =>
                                // If no dataType is specified, try to infer from value
                                // Use Literal.create which handles more cases than Literal.apply
                                try {
                                  org.apache.spark.sql.catalyst.expressions.Literal
                                    .apply(value)
                                    .dataType
                                } catch {
                                  case _: Exception =>
                                    // Fallback: use StringType for String values
                                    if (
                                      value != null && value
                                        .isInstanceOf[String]
                                    ) {
                                      org.apache.spark.sql.types.StringType
                                    } else {
                                      throw new IllegalArgumentException(
                                        s"Cannot infer dataType for value of type ${if (
                                            value == null
                                          ) "null"
                                          else value.getClass.getName}"
                                      )
                                    }
                                }
                              case other =>
                                throw new IllegalArgumentException(
                                  s"Unexpected dataType result: ${other.getClass.getName}"
                                )
                            }

                            // Use Literal.create for better type handling
                            try {
                              org.apache.spark.sql.catalyst.expressions.Literal
                                .create(value, dataType)
                            } catch {
                              case _: Exception =>
                                // Fallback to direct constructor
                                org.apache.spark.sql.catalyst.expressions
                                  .Literal(value, dataType)
                            }
                          } catch {
                            case e: Exception =>
                              throw new IllegalArgumentException(
                                s"Failed to extract Literal: ${e.getMessage}",
                                e
                              )
                          }
                        } else {
                          // Try expression() method for other node types
                          try {
                            val expressionMethod =
                              node.getClass.getMethod("expression")
                            expressionMethod
                              .invoke(node)
                              .asInstanceOf[Expression]
                          } catch {
                            case _: NoSuchMethodException =>
                              // If it's an Expression-like node, try to use it directly
                              if (node.isInstanceOf[Expression]) {
                                node.asInstanceOf[Expression]
                              } else {
                                throw new IllegalArgumentException(
                                  s"Unknown argument type: ${node.getClass.getName}"
                                )
                              }
                          }
                        }
                    }
                  case _ =>
                    throw new IllegalArgumentException(
                      s"Unexpected arguments type: ${argumentsRaw.getClass.getName}"
                    )
                }

                // Avoid direct construction; let analyzer resolve built-ins correctly

                // Create UnresolvedFunction expression
                val unresolvedFunctionClass = Class.forName(
                  "org.apache.spark.sql.catalyst.analysis.UnresolvedFunction"
                )
                // UnresolvedFunction has multiple constructors, try to find one that works
                val ctors = unresolvedFunctionClass.getConstructors.sortBy(
                  -_.getParameterCount
                )
                var lastError: Option[Throwable] = None
                for (ctor <- ctors) {
                  try {
                    val paramCount = ctor.getParameterCount
                    val expr = paramCount match {
                      case 2 => ctor.newInstance(functionName, arguments)
                      case 3 =>
                        ctor.newInstance(
                          functionName,
                          arguments,
                          false.asInstanceOf[AnyRef]
                        )
                      case 4 =>
                        ctor.newInstance(
                          functionName,
                          arguments,
                          false.asInstanceOf[AnyRef],
                          None
                        )
                      case _ => null
                    }
                    if (expr != null) {
                      return expr.asInstanceOf[Expression]
                    }
                  } catch {
                    case e: Exception =>
                      lastError = Some(e)
                    // Try next constructor
                  }
                }
                // If we got here, all constructors failed
                val errorMsg = lastError
                  .map(e =>
                    s" Last error: ${e.getClass.getName}: ${e.getMessage}"
                  )
                  .getOrElse("")
                strategyError = Some(
                  s"Node class ${columnNode.getClass.getName}: Failed to create UnresolvedFunction.$errorMsg"
                )
              } catch {
                case e: Exception =>
                  strategyError = Some(
                    s"Node class ${columnNode.getClass.getName}: Exception in Strategy 2c: ${e.getClass.getName}: ${e.getMessage}"
                  )
              }
            }
          } catch {
            case _: Exception => // Fall through
          }

          // If we reach here, all extraction strategies failed
          if (
            strategyError.isEmpty || strategyError.get.startsWith("Got node:")
          ) {
            strategyError = Some(
              s"Node class ${columnNode.getClass.getName}: could not extract expression using any method"
            )
          }
        // Fall through to Strategy 3
      }
    } catch {
      case e: NoSuchMethodException =>
        strategyError = Some(
          s"Column has no node() method. Available methods: ${classOf[Column].getMethods.map(_.getName).sorted.distinct.mkString(", ")}"
        )
      // Continue to Strategy 3
      case e: Exception =>
        strategyError = Some(
          s"Unexpected error: ${e.getClass.getName}: ${e.getMessage}"
        )
    }

    // Strategy 2e: Use SQL parser to convert Column's SQL string into an (unresolved) Expression
    try {
      val sqlString = column.toString
      val spark = org.apache.spark.sql.SparkSession.active
      val sessionState = spark.sessionState
      val parser = {
        val m =
          sessionState.getClass.getMethods.find(_.getName == "sqlParser").get
        m.invoke(sessionState)
      }
      val parseExpr =
        parser.getClass.getMethods.find(_.getName == "parseExpression").get
      val parsed = parseExpr.invoke(parser, sqlString).asInstanceOf[Expression]
      return parsed
    } catch {
      case _: Throwable =>
      // ignore and continue to Strategy 3
    }

    // Strategy 3: Analyzer fallback - resolve through Spark's analyzer
    try {
      val spark = org.apache.spark.sql.SparkSession.active
      val dummyDf = spark.range(1).select(column)
      val analyzed = dummyDf.queryExecution.analyzed

      analyzed match {
        case Project(projectList, _) if projectList.nonEmpty =>
          projectList.head match {
            case Alias(child, _) => child
            case e: Expression   => e
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
        val debugInfo =
          strategyError.map(err => s" Strategy 2 debug: $err").getOrElse("")
        throw new UnsupportedOperationException(
          s"Cannot extract Expression from Column using any strategy. " +
            s"This may indicate an incompatible Spark version or Column type. Error: ${e.getMessage}." +
            debugInfo,
          e
        )
    }
  }

  /**
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
          val origin = SparkCompat.newOrigin()

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
