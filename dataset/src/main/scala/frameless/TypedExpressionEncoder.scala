package frameless

import org.apache.spark.sql.Encoder
import org.apache.spark.sql.catalyst.analysis.GetColumnByOrdinal
import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder
import org.apache.spark.sql.catalyst.expressions.{Alias, BoundReference, CreateNamedStruct, Expression, GetStructField, If, Literal}
import org.apache.spark.sql.types.StructType
import frameless.internal.SparkCompat

object TypedExpressionEncoder {

  /** In Spark, DataFrame has always schema of StructType
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

  def apply[T](implicit encoder: TypedEncoder[T]): Encoder[T] = {
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
        val pairs: Seq[Expression] = fields.zipWithIndex.flatMap { case (sf, i) =>
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
    val encCls = classOf[ExpressionEncoder[_]]

    if (SparkCompat.isSpark4) {
      // Spark 4.x: ExpressionEncoder(AgnosticEncoder, serializer: Expression, deserializer: Expression)
      val ctorOpt = SparkCompat.findCtorByParamFQNs(
        encCls,
        List(
          List(
            "org.apache.spark.sql.catalyst.encoders.AgnosticEncoder",
            "org.apache.spark.sql.catalyst.expressions.Expression",
            "org.apache.spark.sql.catalyst.expressions.Expression"
          )
        )
      )
      val ctor = ctorOpt.getOrElse(
        throw new RuntimeException(
          s"Spark 4.x ExpressionEncoder constructor not found. Found constructors:\n${SparkCompat.debugConstructors(encCls)}"
        )
      )

      // Build scala.reflect.ClassTag for runtimeClass reflectively
      val classTagComp = Class.forName("scala.reflect.ClassTag$")
      val classTagMod = classTagComp.getField("MODULE$").get(null)
      val classTagApply = classTagComp.getMethod("apply", classOf[Class[_]])
      val classTag = classTagApply.invoke(classTagMod, runtimeClass)

      // Create AgnosticEncoder with the correct ClassTag using ProductEncoder
      // ProductEncoder(ClassTag, fields: Seq[AgnosticEncoder.Field], outer: Option[...])
      // NOTE: We pass our custom serializer/deserializer expressions to ExpressionEncoder,
      // so the field-level encoders in AgnosticEncoder are only used for schema/type metadata.
      // We still use RowEncoder's fields to maintain compatibility with Spark's expectations.
      val agnostic = try {
        // Get RowEncoder to get the correct field structure for the schema
        val rowEncoderComp = Class.forName("org.apache.spark.sql.catalyst.encoders.RowEncoder$")
        val rowEncoderMod = rowEncoderComp.getField("MODULE$").get(null)
        val encoderFor = rowEncoderComp.getMethod("encoderFor", classOf[org.apache.spark.sql.types.StructType])
        val rowEncoder = encoderFor.invoke(rowEncoderMod, schema)
        
        // We need to modify the RowEncoder to use our ClassTag
        // Since RowEncoder is immutable, we create a new ProductEncoder with the same fields
        // but with our ClassTag
        val rowEncoderClass = rowEncoder.getClass
        val fieldsMethod = rowEncoderClass.getMethod("fields")
        val fields = fieldsMethod.invoke(rowEncoder)
        
        // Try to create ProductEncoder with our ClassTag but RowEncoder's fields
        val productEncoderClass = Class.forName("org.apache.spark.sql.catalyst.encoders.AgnosticEncoders$ProductEncoder")
        val productEncoderCtor = productEncoderClass.getConstructors.head
        val noneOption = Class.forName("scala.None$").getField("MODULE$").get(null)
        productEncoderCtor.newInstance(classTag, fields, noneOption)
      } catch {
        case e: Exception =>
          // If ProductEncoder creation fails, fall back to RowEncoder
          // This will use Row's ClassTag but at least won't break basic functionality
          val rowEncoderComp = Class.forName("org.apache.spark.sql.catalyst.encoders.RowEncoder$")
          val rowEncoderMod = rowEncoderComp.getField("MODULE$").get(null)
          val encoderFor = rowEncoderComp.getMethod("encoderFor", classOf[org.apache.spark.sql.types.StructType])
          encoderFor.invoke(rowEncoderMod, schema)
      }

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
          val namedFields = fields.zipWithIndex.map { case (sf, i) =>
            Alias(GetStructField(serializerExpr, i, Some(sf.name)), sf.name)()
          }
          // Wrap in CreateStruct to build the row
          org.apache.spark.sql.catalyst.expressions.CreateStruct(namedFields)
        }
      }
      
      ctor
        .newInstance(agnostic, spark4Serializer, deserializerExpr)
        .asInstanceOf[Encoder[T]]
    } else {
      // Build scala.reflect.ClassTag for runtimeClass reflectively
      val classTagComp = Class.forName("scala.reflect.ClassTag$")
      val classTagMod = classTagComp.getField("MODULE$").get(null)
      val classTagApply = classTagComp.getMethod("apply", classOf[Class[_]])
      val classTag = classTagApply.invoke(classTagMod, runtimeClass)

      // Spark 3.5+: ExpressionEncoder(serializer: Expression, deserializer: Expression, clsTag: ClassTag)
      val spark35Wanted = List(
        List(
          "org.apache.spark.sql.catalyst.expressions.Expression",
          "org.apache.spark.sql.catalyst.expressions.Expression",
          "scala.reflect.ClassTag"
        )
      )
      val spark35Ctor = SparkCompat.findCtorByParamFQNs(encCls, spark35Wanted)
      
      if (spark35Ctor.isDefined) {
        // Spark 3.5+ path: single Expression serializer (similar to Spark 4.x structure)
        val spark35Serializer = {
          val fields = schema.fields
          if (fields.length == 1) {
            serializerExpr
          } else {
            val namedFields = fields.zipWithIndex.map { case (sf, i) =>
              Alias(GetStructField(serializerExpr, i, Some(sf.name)), sf.name)()
            }
            org.apache.spark.sql.catalyst.expressions.CreateStruct(namedFields)
          }
        }
        
        spark35Ctor.get.newInstance(spark35Serializer, deserializerExpr, classTag).asInstanceOf[Encoder[T]]
      } else {
        // Spark 3.3-3.4: ExpressionEncoder(serializer: Seq[Expression], deserializer: Expression, clsTag: ClassTag)
        val wanted = List(
          List(
            "scala.collection.Seq",
            "org.apache.spark.sql.catalyst.expressions.Expression",
            "scala.reflect.ClassTag"
          ),
          List(
            "scala.collection.Seq",
            "org.apache.spark.sql.catalyst.expressions.Expression",
            "scala.reflect.Manifest"
          )
        )
        val ctor = SparkCompat.findCtorByParamFQNs(encCls, wanted).getOrElse(
          throw new RuntimeException(
            s"Spark 3.x ExpressionEncoder constructor not found. Found constructors:\n${SparkCompat.debugConstructors(encCls)}"
          )
        )

        // Shape serializer as a Seq of NamedExpressions matching top-level schema fields
        val serializerSeq: scala.collection.Seq[Expression] = {
          val fields = schema.fields
          if (fields.length == 1) {
            val name = fields.head.name
            scala.collection.Seq(Alias(serializerExpr, name)())
          } else {
            val namedFields: Seq[Expression] = fields.zipWithIndex.map { case (sf, i) =>
              Alias(GetStructField(serializerExpr, i, Some(sf.name)), sf.name)()
            }
            scala.collection.Seq(namedFields: _*)
          }
        }

        ctor.newInstance(serializerSeq, deserializerExpr, classTag).asInstanceOf[Encoder[T]]
      }
    }
  }
}

