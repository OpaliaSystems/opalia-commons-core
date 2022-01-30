package systems.opalia.commons.core.codec.impl

import org.scalatest.flatspec.*
import org.scalatest.matchers.should.*
import scala.util.Random


class HexTest
  extends AnyFlatSpec
    with Matchers {

  it should "validate strings correctly" in {

    Hex.isValid("42fdea") shouldBe true
    Hex.isValid("42fde") shouldBe false
    Hex.isValid("004Faf10") shouldBe true
    Hex.isValid("42af73fg") shouldBe false
  }

  it should "be able to generate a hex bubble string from byte array" in {

    Hex.encode(Vector(
      0xbb, 0x74, 0x68, 0x69, 0x73,
      0x20, 0x69, 0x73, 0x20, 0x61,
      0x20, 0x74, 0x65, 0x73, 0x74,
      0xab).map(_.toByte)) should be("bb7468697320697320612074657374ab")
  }

  it should "be able to read a hex bubble string to generate a byte sequence" in {

    Hex.decodeTry("bb6a75737420616e6f746865722074657374AB").toOption.map(_.toSeq) should be(Some(Vector(
      0xbb, 0x6a, 0x75, 0x73, 0x74,
      0x20, 0x61, 0x6e, 0x6f, 0x74,
      0x68, 0x65, 0x72, 0x20, 0x74,
      0x65, 0x73, 0x74, 0xab).map(_.toByte).toSeq))
  }

  it should "be able to react on invalid hex bubble strings" in {

    Hex.decodeTry("bb6a75737420616e6f746865722074657374ax").toOption.map(_.toSeq) shouldBe None
  }

  it should "encode and decode correctly" in {

    val original = "This is a test."
    val encoded = Hex.encode(original)
    val decoded = Hex.decodeToString(encoded)

    decoded shouldBe original
  }

  it should "encode and decode large amounts of data" in {

    val original = Random.nextBytes(2000).toIndexedSeq

    val encoded = Hex.encode(original)
    val decoded = Hex.decode(encoded)

    decoded shouldBe original
  }
}
