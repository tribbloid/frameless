package frameless.internal

import org.apache.spark.sql.catalyst.trees.Origin

object SparkCompat {

  /** Always true since we only support Spark 4.x */
  val isSpark4: Boolean = true

  def classExists(fqn: String): Boolean =
    try {
      Class.forName(fqn)
      true
    } catch { case _: Throwable => false }

  /** Construct an Origin instance for Spark 4 ColumnNode constructors. */
  def newOrigin(): Origin =
    new Origin(None, None, None, None, None, None, None, None, None)

  /** Find a public constructor whose erased parameter type FQNs match one of the provided signatures. */
  def findCtorByParamFQNs(
      c: Class[_],
      wanted: List[List[String]]
    ): Option[java.lang.reflect.Constructor[_]] = {
    val ctors = c.getConstructors.toList
    val ctorsWithNames = ctors.map { ctor =>
      ctor -> ctor.getParameterTypes.toList.map(_.getName)
    }
    wanted.collectFirst(Function.unlift { sig =>
      ctorsWithNames.collectFirst { case (ctor, names) if names == sig => ctor }
    })
  }

  def debugConstructors(c: Class[_]): String =
    c.getConstructors.map { ctor =>
      val params =
        ctor.getParameterTypes.map(_.getName).mkString("(", ", ", ")")
      s"<init>$params"
    }.mkString("\n")
}
