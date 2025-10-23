package frameless.functions

import org.apache.spark.sql.catalyst.expressions.Expression

/**
 * Simple marker class for lit functions to maintain compatibility
 * In Spark 4.0, we just delegate to the underlying catalyst expression
 */
private[frameless] case class Lit[T](
    catalystExpr: Expression
  ) extends Expression {

  def eval(input: org.apache.spark.sql.catalyst.InternalRow): Any = catalystExpr.eval(input)
  def children: Seq[Expression] = catalystExpr.children
  protected def doGenCode(ctx: org.apache.spark.sql.catalyst.expressions.codegen.CodegenContext, ev: org.apache.spark.sql.catalyst.expressions.codegen.ExprCode): org.apache.spark.sql.catalyst.expressions.codegen.ExprCode =
    catalystExpr.genCode(ctx)
  protected def withNewChildrenInternal(newChildren: IndexedSeq[Expression]): Expression = this

  // Delegate to catalystExpr for all properties
  override val foldable: Boolean = catalystExpr.foldable
  override def dataType: org.apache.spark.sql.types.DataType = catalystExpr.dataType
  override def nullable: Boolean = catalystExpr.nullable
  override def toString: String = s"FramelessLit($catalystExpr)"
}