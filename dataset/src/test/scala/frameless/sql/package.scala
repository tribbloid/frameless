package frameless

import org.apache.spark.sql.catalyst.expressions.{And, Expression, Or}

package object sql {

  implicit class ExpressionOps(private val self: Expression) extends AnyVal {

    def toList: List[Expression] = {
      def rec(expr: Expression, acc: List[Expression]): List[Expression] = {
        expr match {
          case And(left, right) => rec(left, rec(right, acc))
          case Or(left, right)  => rec(left, rec(right, acc))
          case e                => e +: acc
        }
      }

      rec(self, Nil)
    }
  }
}
