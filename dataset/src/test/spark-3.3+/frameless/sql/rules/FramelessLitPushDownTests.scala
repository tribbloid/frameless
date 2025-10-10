package frameless.sql.rules

import frameless._
import frameless.functions.Lit
import org.apache.spark.sql.catalyst.util.DateTimeUtils
import org.apache.spark.sql.sources.{EqualTo, GreaterThanOrEqual, IsNotNull}
import org.apache.spark.sql.catalyst.expressions
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema
import java.time.Instant

class FramelessLitPushDownTests extends SQLRulesSuite {
  // Spark 4 removed currentTimestamp(), use System.currentTimeMillis() * 1000 for microseconds
  private val now: Long = try {
    val method = DateTimeUtils.getClass.getMethod("currentTimestamp")
    method.invoke(DateTimeUtils).asInstanceOf[Long]
  } catch {
    case _: NoSuchMethodException =>
      // Spark 4: use current time in microseconds
      System.currentTimeMillis() * 1000
  }
  
  private def microsToInstant(micros: Long): Instant = {
    try {
      val method = DateTimeUtils.getClass.getMethod("microsToInstant", classOf[Long])
      method.invoke(DateTimeUtils, Long.box(micros)).asInstanceOf[Instant]
    } catch {
      case _: NoSuchMethodException =>
        // Spark 4: manual conversion
        Instant.ofEpochSecond(micros / 1000000, (micros % 1000000) * 1000)
    }
  }

  test("java.sql.Timestamp push-down") {
    val expected = java.sql.Timestamp.from(microsToInstant(now))
    val expectedStructure = X1(SQLTimestamp(now))
    val expectedPushDownFilters = List(IsNotNull("a"), GreaterThanOrEqual("a", expected))

    predicatePushDownTest[SQLTimestamp](
      expectedStructure,
      expectedPushDownFilters,
      { case e @ expressions.GreaterThanOrEqual(_, _: Lit[_]) => e },
      _ >= expectedStructure.a
    )
  }

  test("java.time.Instant push-down") {
    val expected = java.sql.Timestamp.from(microsToInstant(now))
    val expectedStructure = X1(microsToInstant(now))
    val expectedPushDownFilters = List(IsNotNull("a"), GreaterThanOrEqual("a", expected))

    predicatePushDownTest[Instant](
      expectedStructure,
      expectedPushDownFilters,
      { case e @ expressions.GreaterThanOrEqual(_, _: Lit[_]) => e },
      _ >= expectedStructure.a
    )
  }

  test("struct push-down") {
    type Payload = X4[Int, Int, Int, Int]
    val expectedStructure = X1(X4(1, 2, 3, 4))
    val expected = new GenericRowWithSchema(Array(1, 2, 3, 4), TypedExpressionEncoder[Payload].schema)
    val expectedPushDownFilters = List(IsNotNull("a"), EqualTo("a", expected))

    predicatePushDownTest[Payload](
      expectedStructure,
      expectedPushDownFilters,
      { case e @ expressions.EqualTo(_, _: Lit[_]) => e },
      _ === expectedStructure.a
    )
  }
}
