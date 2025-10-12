package frameless
package ml
package internals

import org.apache.spark.ml.linalg._
import shapeless.ops.hlist.Length
import shapeless.{ HList, LabelledGeneric, Nat }

import scala.annotation.implicitNotFound

/**
 * Can be used for all tree-based ML algorithm (decision tree, random forest, gradient-boosted trees)
 */
@implicitNotFound(
  msg = "Cannot prove that ${Inputs} is a valid input type. " +
    "Input type must only contain a field of type Double (the label) and a field of type " +
    "org.apache.spark.ml.linalg.Vector (the features)."
)
trait TreesInputsChecker[Inputs] {
  val featuresCol: String
  val labelCol: String
}

object TreesInputsChecker {

  implicit def checkTreesInputs[
      Inputs,
      InputsRec <: HList,
      LabelK <: String,
      FeaturesK <: String
    ](implicit
      i0: LabelledGeneric.Aux[Inputs, InputsRec],
      i1: Length.Aux[InputsRec, Nat._2],
      i2: SelectorByValue.Aux[InputsRec, Double, LabelK],
      i3: ValueOf[LabelK],
      i4: SelectorByValue.Aux[InputsRec, Vector, FeaturesK],
      i5: ValueOf[FeaturesK]
    ): TreesInputsChecker[Inputs] = {
    new TreesInputsChecker[Inputs] {
      val labelCol: String = i3.value
      val featuresCol: String = i5.value
    }
  }

}
