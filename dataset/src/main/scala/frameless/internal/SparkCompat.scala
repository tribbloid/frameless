package frameless.internal

object SparkCompat {

  lazy val isSpark4: Boolean = {
    val encCls =
      classOf[org.apache.spark.sql.catalyst.encoders.ExpressionEncoder[_]]
    val spark4Ctor = findCtorByParamFQNs(
      encCls,
      List(
        List(
          "org.apache.spark.sql.catalyst.encoders.AgnosticEncoder",
          "org.apache.spark.sql.catalyst.expressions.Expression",
          "org.apache.spark.sql.catalyst.expressions.Expression"
        )
      )
    )
    spark4Ctor.isDefined
  }

  def classExists(fqn: String): Boolean =
    try {
      Class.forName(fqn)
      true
    } catch { case _: Throwable => false }

  /** Construct an Origin instance for Spark 4 classic ColumnNode constructors. */
  def newOrigin(): AnyRef = {
    // Try no-arg apply (Spark 3.x style) then reflectively build with None params (Spark 4 path)
    val originFqn = "org.apache.spark.sql.catalyst.trees.Origin"
    try {
      val originComp = Class.forName(originFqn + "$")
      val module = originComp.getField("MODULE$").get(null)
      val apply = originComp.getMethod("apply")
      apply.invoke(module).asInstanceOf[AnyRef]
    } catch {
      case _: Throwable =>
        try {
          val originCls = Class.forName(originFqn)
          val none = Class.forName("scala.None$").getField("MODULE$").get(null)
          // 9 Options parameters in Spark 4 Origin
          val ctor = originCls.getConstructor(
            classOf[Option[_]],
            classOf[Option[_]],
            classOf[Option[_]],
            classOf[Option[_]],
            classOf[Option[_]],
            classOf[Option[_]],
            classOf[Option[_]],
            classOf[Option[_]],
            classOf[Option[_]]
          )
          ctor
            .newInstance(none, none, none, none, none, none, none, none, none)
            .asInstanceOf[AnyRef]
        } catch {
          case e: Throwable =>
            // Last resort - try default constructor
            val originCls = Class.forName(originFqn)
            originCls
              .getDeclaredConstructor()
              .newInstance()
              .asInstanceOf[AnyRef]
        }
    }
  }

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
