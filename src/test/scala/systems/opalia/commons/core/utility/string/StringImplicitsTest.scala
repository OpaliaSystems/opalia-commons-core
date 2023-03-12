package systems.opalia.commons.core.utility.string

import org.scalatest.flatspec.*
import org.scalatest.matchers.should.*
import systems.opalia.commons.core.utility.string.Implicits.*


class StringImplicitsTest
  extends AnyFlatSpec
    with Matchers {

  it should "be able to convert a string to BigInt object" in {

    "4273".toBigInt shouldBe BigInt(4273)
    "4273".toBigIntOption shouldBe Some(BigInt(4273))
    "4273.9".toBigIntOption shouldBe None
    the[NumberFormatException] thrownBy "4273a".toBigInt
  }

  it should "be able to convert a string to BigDecimal object" in {

    "4273".toBigDecimal shouldBe BigDecimal(4273)
    "4273.9".toBigDecimalOption shouldBe Some(BigDecimal(4273.9))
    "4273.9a".toBigDecimalOption shouldBe None
    the[NumberFormatException] thrownBy "4273-".toBigDecimal
  }

  it should "be able to convert a string to Byte, Short, Int and Long by a specific radix" in {

    "FF".toByte(radix = 16) shouldBe 0xFF.toByte
    "FFFF".toShort(radix = 16) shouldBe 0xFFFF.toShort
    "FFFFFFFF".toInt(radix = 16) shouldBe 0xFFFFFFFF
    "FFFFFFFFFFFFFFFF".toLong(radix = 16) shouldBe 0xFFFFFFFFFFFFFFFFL

    "377".toByte(radix = 8) shouldBe 0xFF.toByte
    "377".toByteOption(radix = 8) shouldBe Some(0xFF.toByte)
    "255".toByte(radix = 10) shouldBe 0xFF.toByte
    "255".toByteOption(radix = 10) shouldBe Some(0xFF.toByte)

    "FG".toByteOption(radix = 16) shouldBe None
    the[NumberFormatException] thrownBy "FG".toByte(radix = 16)

    "-5".toByteOption(radix = 16) shouldBe Some((-5).toByte)

    "555".toByteOption(radix = 16) shouldBe None
    the[NumberFormatException] thrownBy "555".toByte(radix = 16)

    "101".toByte(radix = 2) shouldBe 5
    "10".toShort(radix = 8) shouldBe 8
    "42".toInt(radix = 10) shouldBe 42
    "73".toLong(radix = 16) shouldBe 115
  }

  it should "be able to convert strings to boolean values more strictly than it does by default" in {

    "true".toStrictBoolean shouldBe true
    "yes".toStrictBoolean shouldBe true
    "on".toStrictBoolean shouldBe true
    the[NumberFormatException] thrownBy "foo".toStrictBoolean

    "false".toStrictBoolean shouldBe false
    "no".toStrictBoolean shouldBe false
    "off".toStrictBoolean shouldBe false
    the[NumberFormatException] thrownBy "bar".toStrictBoolean

    "TRUE".toStrictBooleanOption shouldBe Some(true)
    "FALSE".toStrictBooleanOption shouldBe Some(false)

    "1".toStrictBooleanOption shouldBe None
    "0".toStrictBooleanOption shouldBe None
  }
}
