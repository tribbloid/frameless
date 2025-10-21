package frameless

import org.apache.spark.sql.Encoder
import org.apache.spark.sql.catalyst.analysis.GetColumnByOrdinal
import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder
import org.apache.spark.sql.catalyst.expressions.{
  Alias,
  BoundReference,
  CreateNamedStruct,
  Expression,
  GetStructField,
  If,
  Literal
}
import org.apache.spark.sql.types.StructType

object TypedExpressionEncoder {

  /**
   * In Spark, DataFrame has always schema of StructType
   *
   * DataFrames of primitive types become records
   * with a single field called "value" set in ExpressionEncoder.
   */
  def targetStructType[A](encoder: TypedEncoder[A]): StructType =
    encoder.catalystRepr match {
      case x: StructType =>
        if (encoder.nullable) StructType(x.fields.map(_.copy(nullable = true)))
        else x

      case dt => new StructType().add("value", dt, nullable = encoder.nullable)
    }

  def apply[T](
      implicit
      encoder: TypedEncoder[T]
    ): Encoder[T] = {
    val in = BoundReference(0, encoder.jvmRepr, encoder.nullable)

    // Serializer expression encodes the entire value (either atomic or struct)
    val serializerExpr: Expression = encoder.toCatalyst(in)

    // Build deserializer root from top-level row columns matching the target schema shape
    val schema = targetStructType(encoder)
    val deserializerRoot: Expression = {
      val fields = schema.fields
      if (fields.length == 1) {
        GetColumnByOrdinal(0, fields.head.dataType)
      } else {
        val pairs: Seq[Expression] = fields.zipWithIndex.flatMap {
          case (sf, i) =>
            Seq(Literal(sf.name), GetColumnByOrdinal(i, sf.dataType))
        }
        CreateNamedStruct(pairs)
      }
    }
    val deserializerExpr: Expression = encoder.fromCatalyst(deserializerRoot)

    createExpressionEncoder[T](
      serializerExpr = serializerExpr,
      deserializerExpr = deserializerExpr,
      runtimeClass = encoder.classTag.runtimeClass,
      schema = schema
    )
  }

  private def createExpressionEncoder[T](
      serializerExpr: Expression,
      deserializerExpr: Expression,
      runtimeClass: Class[_],
      schema: StructType
    ): Encoder[T] = {
    import scala.reflect.ClassTag

    // Build ClassTag for runtimeClass
    val classTag = ClassTag(runtimeClass)

    // Use public RowEncoder API to create AgnosticEncoder for the schema
    // This eliminates reflection for accessing RowEncoder
    val agnostic = org.apache.spark.sql.catalyst.encoders.RowEncoder.encoderFor(schema)

    // In Spark 4.0, the serializer and deserializer need to match the AgnosticEncoder structure
    // Unlike Spark 3.x where serializer was Seq[NamedExpression], Spark 4.0 uses single expressions
    // but they should still be properly structured for multi-field types
    val spark4Serializer = {
      val fields = schema.fields
      if (fields.length == 1) {
        // Single field: use the serializer expression directly
        serializerExpr
      } else {
        // Multiple fields: create a struct from field extractions
        val namedFields = fields.zipWithIndex.map {
          case (sf, i) =>
            Alias(GetStructField(serializerExpr, i, Some(sf.name)), sf.name)()
        }
        // Wrap in CreateStruct to build the row
        org.apache.spark.sql.catalyst.expressions.CreateStruct(namedFields)
      }
    }

    // Use public ExpressionEncoder.apply method instead of reflection
    // This eliminates the need for constructor lookup and reflection
    org.apache.spark.sql.catalyst.encoders.ExpressionEncoder(
      agnostic,
      spark4Serializer,
      deserializerExpr
    ).asInstanceOf[Encoder[T]]
  }
}
