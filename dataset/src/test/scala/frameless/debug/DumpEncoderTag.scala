package frameless.debug

import frameless.TypedExpressionEncoder
import frameless.TypedEncoder

object DumpEncoderTag extends App {
  implicit val te: TypedEncoder[Int] = TypedEncoder.intEncoder
  val e = TypedExpressionEncoder[Int]
  val m = e.getClass.getMethods.find(_.getName == "clsTag").get
  val tag = m.invoke(e)
  val rc = tag.getClass.getMethod("runtimeClass").invoke(tag)
  println(s"Encoder.clsTag.runtimeClass = ${rc}")
}
