package org.apache.spark.sql

import org.apache.spark.sql.catalyst.expressions._
import org.apache.spark.sql.catalyst.expressions.codegen._
import org.apache.spark.sql.catalyst.expressions.{Alias, CreateStruct}
import org.apache.spark.sql.catalyst.expressions.{Expression, NamedExpression}
import org.apache.spark.sql.catalyst.InternalRow
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import org.apache.spark.sql.catalyst.plans.logical.{LogicalPlan, Project}
import org.apache.spark.sql.execution.QueryExecution
import org.apache.spark.sql.types._
import org.apache.spark.sql.types.ObjectType
import scala.reflect.ClassTag

object FramelessInternals {
  def objectTypeFor[A](implicit classTag: ClassTag[A]): ObjectType = ObjectType(classTag.runtimeClass)

  def resolveExpr(ds: Dataset[_], colNames: Seq[String]): NamedExpression = {
    ds.toDF().queryExecution.analyzed.resolve(colNames, ds.sparkSession.sessionState.analyzer.resolver).getOrElse {
      val errorMsg = s"""Cannot resolve column name "$colNames" among (${ds.schema.fieldNames.mkString(", ")})"""
      // Spark 4 changed AnalysisException constructor - use SparkException instead
      try {
        val sparkExceptionClass = Class.forName("org.apache.spark.SparkException")
        val internalErrorMethod = sparkExceptionClass.getMethod("internalError", classOf[String])
        throw internalErrorMethod.invoke(null, errorMsg).asInstanceOf[Throwable]
      } catch {
        case _: ClassNotFoundException | _: NoSuchMethodException | _: IllegalAccessException =>
          // Spark 3.x - use old constructor with single String parameter
          val analysisExceptionClass = classOf[AnalysisException]
          try {
            // Try single-parameter constructor first (Spark 3.2+)
            val constructor = analysisExceptionClass.getConstructor(classOf[String])
            throw constructor.newInstance(errorMsg)
          } catch {
            case _: NoSuchMethodException =>
              // Fall back to creating a runtime exception
              throw new RuntimeException(errorMsg)
          }
      }
    }
  }

  def expr(column: Column): Expression = {
    try {
      // Spark 4.x: column.node.toExpr
      val nodeMethod = classOf[Column].getMethod("node")
      val columnNode = nodeMethod.invoke(column)
      val toExprMethod = columnNode.getClass.getMethod("toExpr")
      toExprMethod.invoke(columnNode).asInstanceOf[Expression]
    } catch {
      case _: NoSuchMethodException =>
        // Spark 3.x: column.expr
        val exprMethod = classOf[Column].getMethod("expr")
        exprMethod.invoke(column).asInstanceOf[Expression]
    }
  }

  /** Creates a Column from a Catalyst Expression.
    * This method provides a consistent API across Spark versions.
    * In Spark 3.x, Column constructor accepts Expression directly.
    * In Spark 4.x, Column constructor requires ColumnNode.
    */
  def column(expr: Expression): Column = {
    try {
      // Try Spark 4.x approach first: new Column(ColumnNode.fromExpr(expr))
      val columnNodeClass = Class.forName("org.apache.spark.sql.internal.ColumnNode")
      val fromExprMethod = columnNodeClass.getMethod("fromExpr", classOf[Expression])
      val columnNode = fromExprMethod.invoke(null, expr)
      val columnConstructor = classOf[Column].getConstructor(columnNodeClass)
      columnConstructor.newInstance(columnNode)
    } catch {
      case _: ClassNotFoundException | _: NoSuchMethodException =>
        // Fall back to Spark 3.x approach: new Column(expr)
        val columnConstructor = classOf[Column].getConstructor(classOf[Expression])
        columnConstructor.newInstance(expr)
    }
  }

  /** Helper to access SQLContext for Spark version compatibility.
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

  def joinPlan(ds: Dataset[_], plan: LogicalPlan, leftPlan: LogicalPlan, rightPlan: LogicalPlan): LogicalPlan = {
    val joined = executePlan(ds, plan)
    val leftOutput = joined.analyzed.output.take(leftPlan.output.length)
    val rightOutput = joined.analyzed.output.takeRight(rightPlan.output.length)

    Project(List(
      Alias(CreateStruct(leftOutput), "_1")(),
      Alias(CreateStruct(rightOutput), "_2")()
    ), joined.analyzed)
  }

  def mkDataset[T](sqlContext: SQLContext, plan: LogicalPlan, encoder: Encoder[T]): Dataset[T] =
    {
      val df = ofRows(sqlContext.sparkSession, plan)
      df.as[T](encoder)
    }

  def ofRows(sparkSession: SparkSession, logicalPlan: LogicalPlan): DataFrame = {
    try {
      // Spark 3.x: Dataset.ofRows(sparkSession, logicalPlan)
      val ofRowsMethod = Class.forName("org.apache.spark.sql.Dataset").getMethod("ofRows", classOf[SparkSession], classOf[LogicalPlan])
      ofRowsMethod.invoke(null, sparkSession, logicalPlan).asInstanceOf[DataFrame]
    } catch {
      case _: NoSuchMethodException =>
        // Spark 4.x: new Dataset[Row](sparkSession, logicalPlan, RowEncoder(sparkSession))
        val rowEncoderClass = Class.forName("org.apache.spark.sql.catalyst.encoders.RowEncoder")
        val rowEncoderMethod = rowEncoderClass.getMethod("apply", classOf[SparkSession])
        val encoder = rowEncoderMethod.invoke(null, sparkSession).asInstanceOf[Encoder[Row]]
        
        val datasetConstructor = classOf[Dataset[_]].getConstructor(classOf[SparkSession], classOf[LogicalPlan], classOf[Encoder[_]])
        datasetConstructor.newInstance(sparkSession, logicalPlan, encoder).asInstanceOf[DataFrame]
    }
  }

  // because org.apache.spark.sql.types.UserDefinedType is private[spark]
  type UserDefinedType[A >: Null] =  org.apache.spark.sql.types.UserDefinedType[A]

  // below only tested in SelfJoinTests.colLeft and colRight are equivalent to col outside of joins
  //  - via files (codegen) forces doGenCode eval.
  /** Expression to tag columns from the left hand side of join expression. */
  case class DisambiguateLeft[T](tagged: Expression) extends Expression with NonSQLExpression {
    def eval(input: InternalRow): Any = tagged.eval(input)
    def nullable: Boolean = false
    def children: Seq[Expression] = tagged :: Nil
    def dataType: DataType = tagged.dataType
    protected def doGenCode(ctx: CodegenContext, ev: ExprCode): ExprCode = tagged.genCode(ctx)
    protected def withNewChildrenInternal(newChildren: IndexedSeq[Expression]): Expression = copy(newChildren.head)
  }

  /** Expression to tag columns from the right hand side of join expression. */
  case class DisambiguateRight[T](tagged: Expression) extends Expression with NonSQLExpression {
    def eval(input: InternalRow): Any = tagged.eval(input)
    def nullable: Boolean = false
    def children: Seq[Expression] = tagged :: Nil
    def dataType: DataType = tagged.dataType
    protected def doGenCode(ctx: CodegenContext, ev: ExprCode): ExprCode = tagged.genCode(ctx)
    protected def withNewChildrenInternal(newChildren: IndexedSeq[Expression]): Expression = copy(newChildren.head)
  }
}
