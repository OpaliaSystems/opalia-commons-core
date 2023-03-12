package systems.opalia.commons.core.utility.json

import scala.collection.IndexedSeq
import scala.collection.immutable.ListMap
import scala.math.ScalaNumber


object JsonAst {

  sealed trait JsonValue

  case object JsonNull
    extends JsonValue

  sealed case class JsonBoolean(value: Boolean)
    extends JsonValue

  sealed case class JsonString(value: String)
    extends JsonValue

  sealed trait JsonNumber
    extends ScalaNumber
      with JsonValue {

    def byteValue: Byte

    def shortValue: Short

    def intValue: Int

    def longValue: Long

    def floatValue: Float

    def doubleValue: Double

    def bigIntValue: BigInt

    def bigDecimalValue: BigDecimal
  }

  sealed case class JsonNumberByte(value: Byte)
    extends JsonNumber {

    override def isWhole: Boolean = value.isWhole

    override def underlying: AnyRef = Byte.box(value)

    override def byteValue: Byte = value.byteValue

    override def shortValue: Short = value.shortValue

    override def intValue: Int = value.intValue

    override def longValue: Long = value.longValue

    override def floatValue: Float = value.floatValue

    override def doubleValue: Double = value.doubleValue

    override def bigIntValue: BigInt = BigInt(value)

    override def bigDecimalValue: BigDecimal = BigDecimal.decimal(value)
  }

  sealed case class JsonNumberShort(value: Short)
    extends JsonNumber {

    override def isWhole: Boolean = value.isWhole

    override def underlying: AnyRef = Short.box(value)

    override def byteValue: Byte = value.byteValue

    override def shortValue: Short = value.shortValue

    override def intValue: Int = value.intValue

    override def longValue: Long = value.longValue

    override def floatValue: Float = value.floatValue

    override def doubleValue: Double = value.doubleValue

    override def bigIntValue: BigInt = BigInt(value)

    override def bigDecimalValue: BigDecimal = BigDecimal.decimal(value)
  }

  sealed case class JsonNumberInt(value: Int)
    extends JsonNumber {

    override def isWhole: Boolean = value.isWhole

    override def underlying: AnyRef = Int.box(value)

    override def byteValue: Byte = value.byteValue

    override def shortValue: Short = value.shortValue

    override def intValue: Int = value.intValue

    override def longValue: Long = value.longValue

    override def floatValue: Float = value.floatValue

    override def doubleValue: Double = value.doubleValue

    override def bigIntValue: BigInt = BigInt(value)

    override def bigDecimalValue: BigDecimal = BigDecimal.decimal(value)
  }

  sealed case class JsonNumberLong(value: Long)
    extends JsonNumber {

    override def isWhole: Boolean = value.isWhole

    override def underlying: AnyRef = Long.box(value)

    override def byteValue: Byte = value.byteValue

    override def shortValue: Short = value.shortValue

    override def intValue: Int = value.intValue

    override def longValue: Long = value.longValue

    override def floatValue: Float = value.floatValue

    override def doubleValue: Double = value.doubleValue

    override def bigIntValue: BigInt = BigInt(value)

    override def bigDecimalValue: BigDecimal = BigDecimal.decimal(value)
  }

  sealed case class JsonNumberFloat(value: Float)
    extends JsonNumber {

    override def isWhole: Boolean = value.isWhole

    override def underlying: AnyRef = Float.box(value)

    override def byteValue: Byte = value.byteValue

    override def shortValue: Short = value.shortValue

    override def intValue: Int = value.intValue

    override def longValue: Long = value.longValue

    override def floatValue: Float = value.floatValue

    override def doubleValue: Double = value.doubleValue

    override def bigIntValue: BigInt = BigDecimal.decimal(value).toBigInt

    override def bigDecimalValue: BigDecimal = BigDecimal.decimal(value)
  }

  sealed case class JsonNumberDouble(value: Double)
    extends JsonNumber {

    override def isWhole: Boolean = value.isWhole

    override def underlying: AnyRef = Double.box(value)

    override def byteValue: Byte = value.byteValue

    override def shortValue: Short = value.shortValue

    override def intValue: Int = value.intValue

    override def longValue: Long = value.longValue

    override def floatValue: Float = value.floatValue

    override def doubleValue: Double = value.doubleValue

    override def bigIntValue: BigInt = BigDecimal.decimal(value).toBigInt

    override def bigDecimalValue: BigDecimal = BigDecimal.decimal(value)
  }

  sealed case class JsonNumberBigInt(value: BigInt)
    extends JsonNumber {

    override def isWhole: Boolean = value.isWhole

    override def underlying: AnyRef = value

    override def byteValue: Byte = value.byteValue

    override def shortValue: Short = value.shortValue

    override def intValue: Int = value.intValue

    override def longValue: Long = value.longValue

    override def floatValue: Float = value.floatValue

    override def doubleValue: Double = value.doubleValue

    override def bigIntValue: BigInt = value

    override def bigDecimalValue: BigDecimal = BigDecimal(value)
  }

  sealed case class JsonNumberBigDecimal(value: BigDecimal)
    extends JsonNumber {

    override def isWhole: Boolean = value.isWhole

    override def underlying: AnyRef = value

    override def byteValue: Byte = value.byteValue

    override def shortValue: Short = value.shortValue

    override def intValue: Int = value.intValue

    override def longValue: Long = value.longValue

    override def floatValue: Float = value.floatValue

    override def doubleValue: Double = value.doubleValue

    override def bigIntValue: BigInt = value.toBigInt

    override def bigDecimalValue: BigDecimal = value
  }

  sealed case class JsonArray(elements: IndexedSeq[JsonValue])
    extends JsonValue

  sealed case class JsonObject(fields: ListMap[String, JsonValue])
    extends JsonValue

}
