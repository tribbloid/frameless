package frameless.internal

import org.apache.spark.sql.catalyst.trees.Origin

object SparkCompat {

  /** Always true since we only support Spark 4.x */
  val isSpark4: Boolean = true

  /** Construct an Origin instance for Spark 4 ColumnNode constructors. */
  def newOrigin(): Origin =
    new Origin(None, None, None, None, None, None, None, None, None)
}
