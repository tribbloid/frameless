package frameless
package functions

import org.apache.spark.sql.{Column, functions => sparkFunctions}
import org.apache.spark.sql.catalyst.expressions._
import org.apache.spark.sql.types.BooleanType

import scala.math.Ordering

trait UnaryFunctions {
  /** Returns length of array
    *
    * apache/spark
    */
  def size[T, A, V[_] : CatalystSizableCollection](column: TypedColumn[T, V[A]]): TypedColumn[T, Int] = {
    val expr = implicitly[CatalystSizableCollection[V]].sizeExpr(column.expr)
    new TypedColumn[T, Int](expr)
  }

  /** Returns length of Map
    *
    * apache/spark
    */
  def size[T, A, B](column: TypedColumn[T, Map[A, B]]): TypedColumn[T, Int] =
    new TypedColumn[T, Int](Size(column.expr))

  /** Sorts the input array for the given column in ascending order, according to
    * the natural ordering of the array elements.
    *
    * apache/spark
    */
  def sortAscending[T, A: Ordering, V[_] : CatalystSortableCollection](column: TypedColumn[T, V[A]]): TypedColumn[T, V[A]] =
    new TypedColumn[T, V[A]](implicitly[CatalystSortableCollection[V]].sortExpr(column.expr, sortAscending = true))(column.uencoder)

  /** Sorts the input array for the given column in descending order, according to
    * the natural ordering of the array elements.
    *
    * apache/spark
    */
  def sortDescending[T, A: Ordering, V[_] : CatalystSortableCollection](column: TypedColumn[T, V[A]]): TypedColumn[T, V[A]] =
    new TypedColumn[T, V[A]](implicitly[CatalystSortableCollection[V]].sortExpr(column.expr, sortAscending = false))(column.uencoder)


  /** Creates a new row for each element in the given collection. The column types
    * eligible for this operation are constrained by CatalystExplodableCollection.
    *
    * apache/spark
    */
  @deprecated("Use explode() from the TypedDataset instead. This method will result in " +
    "runtime error if applied to two columns in the same select statement.", "0.6.2")
  def explode[T, A: TypedEncoder, V[_] : CatalystExplodableCollection](column: TypedColumn[T, V[A]]): TypedColumn[T, A] =
    new TypedColumn[T, A](sparkFunctions.explode(column.untyped))
}

trait CatalystSizableCollection[V[_]] {
  def sizeExpr(expr: Expression): Expression
}

object CatalystSizableCollection {
  implicit def sizableVector: CatalystSizableCollection[Vector] = new CatalystSizableCollection[Vector] {
    def sizeExpr(expr: Expression): Expression = Size(expr)
  }

  implicit def sizableArray: CatalystSizableCollection[Array] = new CatalystSizableCollection[Array] {
    def sizeExpr(expr: Expression): Expression = Size(expr)
  }

  implicit def sizableList: CatalystSizableCollection[List] = new CatalystSizableCollection[List] {
    def sizeExpr(expr: Expression): Expression = Size(expr)
  }

}

trait CatalystExplodableCollection[V[_]]

object CatalystExplodableCollection {
  implicit def explodableVector: CatalystExplodableCollection[Vector] = new CatalystExplodableCollection[Vector] {}
  implicit def explodableArray: CatalystExplodableCollection[Array] = new CatalystExplodableCollection[Array] {}
  implicit def explodableList: CatalystExplodableCollection[List] = new CatalystExplodableCollection[List] {}
  implicit def explodableSeq: CatalystExplodableCollection[Seq] = new CatalystExplodableCollection[Seq] {}
}

trait CatalystSortableCollection[V[_]] {
  def sortExpr(expr: Expression, sortAscending: Boolean): Expression
}

object CatalystSortableCollection {
  implicit def sortableVector: CatalystSortableCollection[Vector] = new CatalystSortableCollection[Vector] {
    def sortExpr(expr: Expression, sortAscending: Boolean): Expression = SortArray(expr, Literal.create(sortAscending, BooleanType))
  }

  implicit def sortableArray: CatalystSortableCollection[Array] = new CatalystSortableCollection[Array] {
    def sortExpr(expr: Expression, sortAscending: Boolean): Expression = SortArray(expr, Literal.create(sortAscending, BooleanType))
  }

  implicit def sortableList: CatalystSortableCollection[List] = new CatalystSortableCollection[List] {
    def sortExpr(expr: Expression, sortAscending: Boolean): Expression = SortArray(expr, Literal.create(sortAscending, BooleanType))
  }
}
