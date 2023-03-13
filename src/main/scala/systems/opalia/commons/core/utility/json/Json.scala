package systems.opalia.commons.core.utility.json

import scala.collection.immutable.ListMap
import scala.math.ScalaNumericConversions
import systems.opalia.commons.core.utility.rendering.*


object Json {

  type Value = String | Boolean | Byte | Short | Int | Long | Float | Double | BigInt | BigDecimal | Node

  def obj(fields: (String, Value)*): ObjectNode =
    ObjectNode(ListMap(fields.map(x => x._1 -> toNode(x._2)): _*))

  def arr(elements: Value*): ArrayNode =
    ArrayNode(IndexedSeq(elements.map(toNode): _*))

  def toNode(value: Value): Node =
    value match {
      case x: String => StringNode(x)
      case x: Boolean => BooleanNode(x)
      case x: Byte => ByteNumberNode(x)
      case x: Short => ShortNumberNode(x)
      case x: Int => IntNumberNode(x)
      case x: Long => LongNumberNode(x)
      case x: Float => FloatNumberNode(x)
      case x: Double => DoubleNumberNode(x)
      case x: BigInt => BigIntNumberNode(x)
      case x: BigDecimal => BigDecimalNumberNode(x)
      case x: Node => x
    }

  sealed trait Node
    extends StringRenderable

  sealed case class ObjectNode(fields: ListMap[String, Node])
    extends Node {

    def renderString(renderer: StringRenderer): StringRenderer =
      renderer ~ "{" ~ renderer.newEmpty.glue(fields.map(x =>
        renderer.newEmpty ~ "\"" ~ JsonUtils.escape(x._1) ~ "\"" ~ ":" ~ x._2
      ), ",") ~ "}"
  }

  sealed case class ArrayNode(elements: IndexedSeq[Node])
    extends Node {

    def renderString(renderer: StringRenderer): StringRenderer =
      renderer ~ "[" ~ renderer.newEmpty.glue(elements.map(renderer.newEmpty ~ _), ",") ~ "]"
  }

  sealed case class StringNode(value: String)
    extends Node {

    def renderString(renderer: StringRenderer): StringRenderer =
      renderer ~ "\"" ~ JsonUtils.escape(value) ~ "\""
  }

  sealed case class BooleanNode(value: Boolean)
    extends Node {

    def renderString(renderer: StringRenderer): StringRenderer =
      renderer ~ value
  }

  case object NullNode
    extends Node {

    def renderString(renderer: StringRenderer): StringRenderer =
      renderer ~ "null"
  }

  sealed trait NumberNode
    extends ScalaNumericConversions
      with Node {

    def byteValue: Byte

    def shortValue: Short

    def intValue: Int

    def longValue: Long

    def floatValue: Float

    def doubleValue: Double

    def toBigInt: BigInt

    def toBigDecimal: BigDecimal

    def renderString(renderer: StringRenderer): StringRenderer =
      renderer ~ underlying.toString
  }

  sealed case class ByteNumberNode(value: Byte)
    extends NumberNode {

    override def isWhole: Boolean = value.isWhole

    override def underlying: AnyRef = Byte.box(value)

    override def byteValue: Byte = value.byteValue

    override def shortValue: Short = value.shortValue

    override def intValue: Int = value.intValue

    override def longValue: Long = value.longValue

    override def floatValue: Float = value.floatValue

    override def doubleValue: Double = value.doubleValue

    override def toBigInt: BigInt = BigInt(value)

    override def toBigDecimal: BigDecimal = BigDecimal.decimal(value)
  }

  sealed case class ShortNumberNode(value: Short)
    extends NumberNode {

    override def isWhole: Boolean = value.isWhole

    override def underlying: AnyRef = Short.box(value)

    override def byteValue: Byte = value.byteValue

    override def shortValue: Short = value.shortValue

    override def intValue: Int = value.intValue

    override def longValue: Long = value.longValue

    override def floatValue: Float = value.floatValue

    override def doubleValue: Double = value.doubleValue

    override def toBigInt: BigInt = BigInt(value)

    override def toBigDecimal: BigDecimal = BigDecimal.decimal(value)
  }

  sealed case class IntNumberNode(value: Int)
    extends NumberNode {

    override def isWhole: Boolean = value.isWhole

    override def underlying: AnyRef = Int.box(value)

    override def byteValue: Byte = value.byteValue

    override def shortValue: Short = value.shortValue

    override def intValue: Int = value.intValue

    override def longValue: Long = value.longValue

    override def floatValue: Float = value.floatValue

    override def doubleValue: Double = value.doubleValue

    override def toBigInt: BigInt = BigInt(value)

    override def toBigDecimal: BigDecimal = BigDecimal.decimal(value)
  }

  sealed case class LongNumberNode(value: Long)
    extends NumberNode {

    override def isWhole: Boolean = value.isWhole

    override def underlying: AnyRef = Long.box(value)

    override def byteValue: Byte = value.byteValue

    override def shortValue: Short = value.shortValue

    override def intValue: Int = value.intValue

    override def longValue: Long = value.longValue

    override def floatValue: Float = value.floatValue

    override def doubleValue: Double = value.doubleValue

    override def toBigInt: BigInt = BigInt(value)

    override def toBigDecimal: BigDecimal = BigDecimal.decimal(value)
  }

  sealed case class FloatNumberNode(value: Float)
    extends NumberNode {

    override def isWhole: Boolean = value.isWhole

    override def underlying: AnyRef = Float.box(value)

    override def byteValue: Byte = value.byteValue

    override def shortValue: Short = value.shortValue

    override def intValue: Int = value.intValue

    override def longValue: Long = value.longValue

    override def floatValue: Float = value.floatValue

    override def doubleValue: Double = value.doubleValue

    override def toBigInt: BigInt = BigDecimal.decimal(value).toBigInt

    override def toBigDecimal: BigDecimal = BigDecimal.decimal(value)
  }

  sealed case class DoubleNumberNode(value: Double)
    extends NumberNode {

    override def isWhole: Boolean = value.isWhole

    override def underlying: AnyRef = Double.box(value)

    override def byteValue: Byte = value.byteValue

    override def shortValue: Short = value.shortValue

    override def intValue: Int = value.intValue

    override def longValue: Long = value.longValue

    override def floatValue: Float = value.floatValue

    override def doubleValue: Double = value.doubleValue

    override def toBigInt: BigInt = BigDecimal.decimal(value).toBigInt

    override def toBigDecimal: BigDecimal = BigDecimal.decimal(value)
  }

  sealed case class BigIntNumberNode(value: BigInt)
    extends NumberNode {

    override def isWhole: Boolean = value.isWhole

    override def underlying: AnyRef = value

    override def byteValue: Byte = value.byteValue

    override def shortValue: Short = value.shortValue

    override def intValue: Int = value.intValue

    override def longValue: Long = value.longValue

    override def floatValue: Float = value.floatValue

    override def doubleValue: Double = value.doubleValue

    override def toBigInt: BigInt = value

    override def toBigDecimal: BigDecimal = BigDecimal(value)
  }

  sealed case class BigDecimalNumberNode(value: BigDecimal)
    extends NumberNode {

    override def isWhole: Boolean = value.isWhole

    override def underlying: AnyRef = value

    override def byteValue: Byte = value.byteValue

    override def shortValue: Short = value.shortValue

    override def intValue: Int = value.intValue

    override def longValue: Long = value.longValue

    override def floatValue: Float = value.floatValue

    override def doubleValue: Double = value.doubleValue

    override def toBigInt: BigInt = value.toBigInt

    override def toBigDecimal: BigDecimal = value
  }
}
