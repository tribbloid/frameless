package frameless
package ml

import org.scalactic.anyvals.PosZInt
import org.scalatest.BeforeAndAfterAll
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.scalacheck.Checkers

class FramelessMlSuite
    extends AnyFunSuite
    with Checkers
    with BeforeAndAfterAll
    with SparkTesting {

  // Limit size of generated collections and number of checks because Travis
  implicit override val generatorDrivenConfig: PropertyCheckConfiguration =
    PropertyCheckConfiguration(sizeRange = PosZInt(10), minSize = PosZInt(10))
  implicit val sparkDelay: SparkDelay[Job] = Job.framelessSparkDelayForJob
}
