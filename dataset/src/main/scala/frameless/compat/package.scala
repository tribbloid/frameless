package frameless

package object compat {

//  lazy val illTyped: shapeless.test.illTyped.type = shapeless.test.illTyped
  lazy val illTyped: FakeIllTyped.type = FakeIllTyped

  object FakeIllTyped {
    def apply(code: String): Unit = {}
    def apply(code: String, expected: String): Unit = {}
  }

  type XString = String with Singleton
}
