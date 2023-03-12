package systems.opalia.commons.core.utility.string

import systems.opalia.commons.core.utility.mathx


object Implicits {

  implicit class StringNumberConversion(string: String) {

    import scala.util.control.Exception.*

    def toBigInt: BigInt =
      BigInt(string)

    def toBigIntOption: Option[BigInt] =
      catching(classOf[NumberFormatException]) opt toBigInt

    def toBigDecimal: BigDecimal =
      BigDecimal(string)

    def toBigDecimalOption: Option[BigDecimal] =
      catching(classOf[NumberFormatException]) opt toBigDecimal

    def toByte(radix: Int): Byte =
      if (string.startsWith("-"))
        (-mathx.parseUnsignedLong(string.drop(1), radix, java.lang.Byte.BYTES)).toByte
      else
        mathx.parseUnsignedLong(string, radix, java.lang.Byte.BYTES).toByte

    def toShort(radix: Int): Short =
      if (string.startsWith("-"))
        (-mathx.parseUnsignedLong(string.drop(1), radix, java.lang.Short.BYTES)).toShort
      else
        mathx.parseUnsignedLong(string, radix, java.lang.Short.BYTES).toShort

    def toInt(radix: Int): Int =
      if (string.startsWith("-"))
        (-mathx.parseUnsignedLong(string.drop(1), radix, java.lang.Integer.BYTES)).toInt
      else
        mathx.parseUnsignedLong(string, radix, java.lang.Integer.BYTES).toInt

    def toLong(radix: Int): Long =
      if (string.startsWith("-"))
        -mathx.parseUnsignedLong(string.drop(1), radix, java.lang.Long.BYTES)
      else
        mathx.parseUnsignedLong(string, radix, java.lang.Long.BYTES)

    def toByteOption(radix: Int): Option[Byte] =
      catching(classOf[NumberFormatException]) opt toByte(radix)

    def toShortOption(radix: Int): Option[Short] =
      catching(classOf[NumberFormatException]) opt toShort(radix)

    def toIntOption(radix: Int): Option[Int] =
      catching(classOf[NumberFormatException]) opt toInt(radix)

    def toLongOption(radix: Int): Option[Long] =
      catching(classOf[NumberFormatException]) opt toLong(radix)
  }

  implicit class StringBooleanConversion(string: String) {

    import scala.util.control.Exception.*

    def toStrictBoolean: Boolean =
      if (string.toLowerCase == "true" || string.toLowerCase == "on" || string.toLowerCase == "yes")
        true
      else if (string.toLowerCase == "false" || string.toLowerCase == "off" || string.toLowerCase == "no")
        false
      else
        throw new NumberFormatException(s"Cannot get boolean from string: $string")

    def toStrictBooleanOption: Option[Boolean] =
      catching(classOf[NumberFormatException]) opt string.toStrictBoolean
  }
}
