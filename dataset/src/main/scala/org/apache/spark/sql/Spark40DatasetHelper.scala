package org.apache.spark.sql

import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import org.apache.spark.sql.catalyst.encoders.RowEncoder
import org.apache.spark.sql.types.StructType

/**
 * Helper object to access package-private Spark 4.0 Dataset APIs.
 *
 * This is placed in the org.apache.spark.sql package to gain access to
 * package-private constructors and methods that are not accessible from
 * frameless package.
 *
 * Spark 4.0 made Dataset class abstract and removed the public ofRows method,
 * requiring internal access to create DataFrames from LogicalPlans.
 */
object Spark40DatasetHelper {

  /**
   * Create a DataFrame from a LogicalPlan preserving the plan structure.
   *
   * This method has package-private access and can call internal Spark APIs
   * that are not accessible from user code.
   */
  def createDataFrame(
      sparkSession: SparkSession,
      logicalPlan: LogicalPlan
    ): DataFrame = {
    // In Spark 4.0, Dataset is abstract and must be created through internal APIs
    // We need to preserve the logical plan structure (critical for joins)

    val schema = logicalPlan.schema
    val encoder: Encoder[Row] = RowEncoder.encoderFor(schema)

    // Approach 0: Use Dataset companion object's apply/ofRows if available
    // This would preserve the logical plan without executing it
    try {
      val datasetCompanion = Class.forName("org.apache.spark.sql.Dataset$")
      val companionInstance = datasetCompanion.getField("MODULE$").get(null)

      // Try to find apply or ofRows method
      val methods = datasetCompanion.getMethods.filter { m =>
        (m.getName == "apply" || m.getName == "ofRows") &&
        m.getParameterTypes.exists(_.getName.contains("LogicalPlan"))
      }

      for (method <- methods) {
        try {
          val paramCount = method.getParameterCount
          if (paramCount == 2) {
            // Try (SparkSession, LogicalPlan)
            return method
              .invoke(companionInstance, sparkSession, logicalPlan)
              .asInstanceOf[DataFrame]
          } else if (paramCount == 3) {
            // Try (SparkSession, LogicalPlan, Encoder)
            return method
              .invoke(companionInstance, sparkSession, logicalPlan, encoder)
              .asInstanceOf[DataFrame]
          }
        } catch {
          case _: Exception => // Try next method
        }
      }
    } catch {
      case _: Exception => // Continue to next approach
    }

    // Approach 1: Try using QueryExecution with reflection
    val qe = sparkSession.sessionState.executePlan(logicalPlan)
    try {
      val qeClass = qe.getClass
      val methods = qeClass.getMethods.filter(_.getName == "toDS")

      for (method <- methods) {
        try {
          method.setAccessible(true)
          val paramCount = method.getParameterCount

          // Try different toDS signatures
          if (paramCount == 0) {
            // toDS() with implicits
            return method.invoke(qe).asInstanceOf[DataFrame]
          } else if (paramCount == 1) {
            // toDS(encoder)
            return method.invoke(qe, encoder).asInstanceOf[DataFrame]
          } else if (paramCount == 2) {
            // toDS(encoder, session)
            return method
              .invoke(qe, encoder, sparkSession)
              .asInstanceOf[DataFrame]
          }
        } catch {
          case _: Exception => // Try next method
        }
      }
    } catch {
      case _: Exception => // Continue to next approach
    }

    // Approach 2: Try toDF method on QueryExecution
    try {
      val toDFMethod = qe.getClass.getMethods.find(_.getName == "toDF")
      if (toDFMethod.isDefined) {
        return toDFMethod.get.invoke(qe).asInstanceOf[DataFrame]
      }
    } catch {
      case _: Exception => // Continue to final fallback
    }

    // Approach 3: Use SparkSession.internalCreateDataFrame as last resort
    try {
      val internalCreateMethod = sparkSession.getClass.getMethods.find { m =>
        m.getName.contains("internalCreate") || m.getName.contains(
          "createDataFrame"
        )
      }

      if (internalCreateMethod.isDefined) {
        val method = internalCreateMethod.get
        method.setAccessible(true)

        // Try with RDD and schema
        if (method.getParameterCount == 2) {
          return method
            .invoke(sparkSession, qe.toRdd, schema)
            .asInstanceOf[DataFrame]
        }
      }
    } catch {
      case _: Exception => // Final fallback below
    }

    // Final fallback: Use public createDataFrame API (loses plan structure but works)
    try {
      import org.apache.spark.sql.catalyst.CatalystTypeConverters
      import scala.collection.JavaConverters._

      val rdd = qe.toRdd
      val rowRDD = rdd.map { internalRow =>
        val values = schema.fields.zipWithIndex.map {
          case (field, i) =>
            if (internalRow.isNullAt(i)) null
            else {
              val value = internalRow.get(i, field.dataType)
              CatalystTypeConverters.convertToScala(value, field.dataType)
            }
        }
        Row.fromSeq(values)
      }

      sparkSession.createDataFrame(rowRDD, schema)
    } catch {
      case e: Exception =>
        throw new RuntimeException(
          s"All approaches to create DataFrame in Spark 4.0 failed. " +
            s"Error: ${e.getMessage}",
          e
        )
    }
  }
}
