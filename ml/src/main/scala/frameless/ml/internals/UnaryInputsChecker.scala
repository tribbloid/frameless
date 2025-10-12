package frameless
package ml
package internals

import shapeless.ops.hlist.Length
import shapeless.{ HList, LabelledGeneric, Nat }

import scala.annotation.implicitNotFound

/**
 * Can be used for all unary transformers (i.e almost all of them)
 */
@implicitNotFound(
  msg = "Cannot prove that ${Inputs} is a valid input type. Input type must have only one field of type ${Expected}"
)
trait UnaryInputsChecker[Inputs, Expected] {
  val inputCol: String
}

object UnaryInputsChecker {

  implicit def checkUnaryInputs[
      Inputs,
      Expected,
      InputsRec <: HList,
      InputK <: String
    ](implicit
      i0: LabelledGeneric.Aux[Inputs, InputsRec],
      i1: Length.Aux[InputsRec, Nat._1],
      i2: SelectorByValue.Aux[InputsRec, Expected, InputK],
      i3: ValueOf[InputK]
    ): UnaryInputsChecker[Inputs, Expected] =
    new UnaryInputsChecker[Inputs, Expected] {
      val inputCol: String = i3.value
    }

}
