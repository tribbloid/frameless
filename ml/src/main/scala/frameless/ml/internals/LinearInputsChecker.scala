package frameless
package ml
package internals

import org.apache.spark.ml.linalg._
import shapeless.ops.hlist.Length
import shapeless.{ HList, LabelledGeneric, Nat }

import scala.annotation.implicitNotFound

/**
 * Can be used for linear reg algorithm
 */
@implicitNotFound(
  msg = "Cannot prove that ${Inputs} is a valid input type. " +
    "Input type must only contain a field of type Double (the label) and a field of type " +
    "org.apache.spark.ml.linalg.Vector (the features) and optional field of float type (weight)."
)
trait LinearInputsChecker[Inputs] {
  val featuresCol: String
  val labelCol: String
  val weightCol: Option[String]
}

object LinearInputsChecker {

  implicit def checkLinearInputs[
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
    ): LinearInputsChecker[Inputs] = {
    new LinearInputsChecker[Inputs] {
      val labelCol: String = i3.value
      val featuresCol: String = i5.value
      val weightCol: Option[String] = None
    }
  }

  implicit def checkLinearInputs2[
      Inputs,
      InputsRec <: HList,
      LabelK <: String,
      FeaturesK <: String,
      WeightK <: String
    ](implicit
      i0: LabelledGeneric.Aux[Inputs, InputsRec],
      i1: Length.Aux[InputsRec, Nat._3],
      i2: SelectorByValue.Aux[InputsRec, Vector, FeaturesK],
      i3: ValueOf[FeaturesK],
      i4: SelectorByValue.Aux[InputsRec, Double, LabelK],
      i5: ValueOf[LabelK],
      i6: SelectorByValue.Aux[InputsRec, Float, WeightK],
      i7: ValueOf[WeightK]
    ): LinearInputsChecker[Inputs] = {
    new LinearInputsChecker[Inputs] {
      val labelCol: String = i5.value
      val featuresCol: String = i3.value
      val weightCol: Option[String] = Some(
        i7.value
      )
    }
  }

}
