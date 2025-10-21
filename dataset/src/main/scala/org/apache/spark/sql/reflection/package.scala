package org.apache.spark.sql

import java.math.BigDecimal

import org.apache.spark.sql.types.{
  BinaryType,
  BooleanType,
  ByteType,
  CalendarIntervalType,
  DataType,
  DecimalType,
  DoubleType,
  FloatType,
  IntegerType,
  LongType,
  NullType,
  ObjectType,
  ShortType
}
import org.apache.spark.unsafe.types.CalendarInterval

import scala.reflect.ClassTag

/**
 * Optimized type mapping without reflection
 *
 * This replaces the original reflection-based type mapping with pre-computed
 * mappings for better performance and type safety.
 */
package object reflection {

  /**
   * copy of pre 3.5.0 isNativeType, https://issues.apache.org/jira/browse/SPARK-44343 removed it
   */
  def isNativeType(dt: DataType): Boolean = dt match {
    case NullType | BooleanType | ByteType | ShortType | IntegerType |
        LongType | FloatType | DoubleType | BinaryType | CalendarIntervalType =>
      true
    case _ => false
  }

  /**
   * Pre-computed type mappings that replace reflection-based type discovery
   */
  private def dataTypeForClass(clazz: Class[_]): DataType = clazz match {
    case java.lang.Byte.TYPE      => ByteType
    case java.lang.Short.TYPE     => ShortType
    case java.lang.Integer.TYPE   => IntegerType
    case java.lang.Long.TYPE      => LongType
    case java.lang.Float.TYPE     => FloatType
    case java.lang.Double.TYPE    => DoubleType
    case java.lang.Boolean.TYPE   => BooleanType
    case _ if clazz.isArray && clazz.getComponentType == classOf[Byte] => BinaryType
    case _ if classOf[CalendarInterval].isAssignableFrom(clazz) => CalendarIntervalType
    case _ if classOf[BigDecimal].isAssignableFrom(clazz) => DecimalType.SYSTEM_DEFAULT
    case _ => ObjectType(clazz)
  }

  /**
   * Returns the Spark SQL DataType for a given scala type using ClassTag instead of reflection.
   * This provides the same functionality as the original reflection-based version but without
   * the performance overhead of runtime type inspection.
   */
  def dataTypeFor[T: ClassTag]: DataType = dataTypeForClass(implicitly[ClassTag[T]].runtimeClass)

}
