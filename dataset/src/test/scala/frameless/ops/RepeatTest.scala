package frameless
package ops

import formless.hlist.{ HNil, :: }
import shapeless.test.illTyped

class RepeatTest extends TypedDatasetSuite {
  test("summoning with implicitly") {
    implicitly[
      Repeat.Aux[Int :: Boolean :: HNil, 1, Int :: Boolean :: HNil]
    ]
    implicitly[Repeat.Aux[
      Int :: Boolean :: HNil,
      2,
      Int :: Boolean :: Int :: Boolean :: HNil
    ]]
    implicitly[Repeat.Aux[
      Int :: Boolean :: HNil,
      3,
      Int :: Boolean :: Int :: Boolean :: Int :: Boolean :: HNil
    ]]
    implicitly[Repeat.Aux[
      String :: HNil,
      5,
      String :: String :: String :: String :: String :: HNil
    ]]
  }

  test("ill typed") {
    illTyped("""implicitly[Repeat.Aux[String::HNil, 5, String::String::String::String::HNil]]""")
  }
}
