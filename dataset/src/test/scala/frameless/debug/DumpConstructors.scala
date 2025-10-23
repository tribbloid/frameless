package frameless.debug

import frameless.internal.SparkCompat
import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder

object DumpConstructors extends App {
  val encCls = classOf[ExpressionEncoder[_]]
  println(
    "ExpressionEncoder constructors:\n" + "debugConstructors method not available"
  )

  try {
    val agnFqn = "org.apache.spark.sql.catalyst.encoders.AgnosticEncoder"
    val agnCls = Class.forName(agnFqn)
    println(
      "\nAgnosticEncoder constructors:\n" + "debugConstructors method not available"
    )

    val agnMod = Class.forName(agnFqn + "$")
    val module = agnMod.getField("MODULE$").get(null)
    val methods = agnMod.getDeclaredMethods
      .map(m =>
        s"${m.getName}(${m.getParameterTypes.map(_.getName).mkString(", ")}) : ${m.getReturnType.getName}"
      )
      .sorted
      .mkString("\n")
    println("\nAgnosticEncoder$ methods:\n" + methods)
  } catch {
    case t: Throwable =>
      t.printStackTrace()
  }
}
