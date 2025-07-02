package frameless

import org.scalacheck.Prop
import org.scalacheck.Prop._
import org.scalatest.matchers.should.Matchers

import scala.jdk.CollectionConverters._

class ToLocalIteratorTests extends TypedDatasetSuite with Matchers {
  test("toLocalIterator") {
    def prop[A: TypedEncoder](data: Vector[A]): Prop = {
      val dataset = TypedDataset.create(data)

      dataset
        .toLocalIterator()
        .run()
        .asScala
        .toIterator sameElements dataset.dataset
        .toLocalIterator()
        .asScala
        .toIterator
    }

    check(forAll(prop[Int] _))
    check(forAll(prop[String] _))
  }
}
