package frameless
package ops

import formless.hlist.Prepend
import formless.hlist.HList

/**
 * Typeclass supporting repeating L-typed HLists N times.
 *
 * Repeat[Int :: String :: HNil, 2].Out =:=
 * Int :: String :: Int :: String :: HNil
 *
 * By Jeremy Smith. To be replaced by `formless.hlist.Repeat`
 * once (https://github.com/milessabin/shapeless/pull/730 is published.
 */
trait Repeat[L <: HList, N <: Int] {
  type Out <: HList
}

object Repeat {

  type Aux[L <: HList, N <: Int, Out0 <: HList] = Repeat[L, N] {
    type Out = Out0
  }

  implicit def repeat1[L <: HList]: Aux[L, 1, L] = new Repeat[L, 1] {
    type Out = L
  }

  implicit def repeat2[L <: HList, P <: HList](
      implicit
      i1: Prepend.Aux[L, L, P]
    ): Aux[L, 2, P] = new Repeat[L, 2] {
    type Out = P
  }

  implicit def repeat3[L <: HList, P1 <: HList, P2 <: HList](
      implicit
      i1: Prepend.Aux[L, L, P1],
      i2: Prepend.Aux[L, P1, P2]
    ): Aux[L, 3, P2] = new Repeat[L, 3] {
    type Out = P2
  }

  implicit def repeat4[L <: HList, P1 <: HList, P2 <: HList, P3 <: HList](
      implicit
      i1: Prepend.Aux[L, L, P1],
      i2: Prepend.Aux[L, P1, P2],
      i3: Prepend.Aux[L, P2, P3]
    ): Aux[L, 4, P3] = new Repeat[L, 4] {
    type Out = P3
  }

  implicit def repeat5[L <: HList, P1 <: HList, P2 <: HList, P3 <: HList, P4 <: HList](
      implicit
      i1: Prepend.Aux[L, L, P1],
      i2: Prepend.Aux[L, P1, P2],
      i3: Prepend.Aux[L, P2, P3],
      i4: Prepend.Aux[L, P3, P4]
    ): Aux[L, 5, P4] = new Repeat[L, 5] {
    type Out = P4
  }
}
