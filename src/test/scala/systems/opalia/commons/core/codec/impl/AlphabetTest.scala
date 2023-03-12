package systems.opalia.commons.core.codec.impl

import org.scalatest.flatspec.*
import org.scalatest.matchers.should.*
import scala.collection.mutable
import scala.util.Random
import systems.opalia.commons.core.identifier.impl.UniversallyUniqueId
import systems.opalia.commons.core.utility.mathx


class AlphabetTest
  extends AnyFlatSpec
    with Matchers {

  it should "validate strings correctly" in {

    val codec1 = Alphabet.getCodec(Alphabet.dec)
    val codec2 = Alphabet.getCodec(Alphabet.hexLower)
    val codec3 = Alphabet.getCodec(Alphabet.radix36)
    val codec4 = Alphabet.getCodec("01")

    codec1.isValid("1234567890") shouldBe true
    codec2.isValid("42fdea") shouldBe true
    codec2.isValid("42fde") shouldBe true
    codec3.isValid("16za") shouldBe true
    codec4.isValid("011101") shouldBe true

    codec1.isValid("123456789a0") shouldBe false
    codec2.isValid("004Faf10") shouldBe false
    codec2.isValid("42af73fg") shouldBe false
    codec3.isValid("-5") shouldBe false
    codec4.isValid("2") shouldBe false
  }

  it should "be proven that the original number of bytes can be found by padding" in {

    for (radix <- 2 to 127) {

      val buffer = mutable.ArrayBuffer[Int]()

      for (numberOfBytes <- 1 to 127)
        buffer.append(mathx.digitCount(radix, math.pow(2, numberOfBytes * 8) - 1))

      buffer.distinct should have length buffer.length
    }
  }

  it should "create paddings correctly" in {

    val codec = Alphabet.getCodec("01")

    val original = Seq(0, 3).map(_.toByte)
    val encoded = codec.encode(original)
    val decoded = codec.decode(encoded)

    encoded shouldBe "0000000000000011"
    decoded shouldBe original
  }

  it should "encode and decode with radix=10 correctly" in {

    val codec = Alphabet.getCodec(Alphabet.dec)

    val original = "This is a test."
    val encoded = codec.encode(original)
    val decoded = codec.decodeToString(encoded)

    decoded shouldBe original
  }

  it should "encode and decode with radix=16 correctly" in {

    val codec = Alphabet.getCodec(Alphabet.hexLower)

    val original = "This is a test."
    val encoded = codec.encode(original)
    val decoded = codec.decodeToString(encoded)

    decoded shouldBe original
  }

  it should "encode and decode with radix=62 correctly" in {

    val codec = Alphabet.getCodec(Alphabet.radix62)

    val original = "This is a test."
    val encoded = codec.encode(original)
    val decoded = codec.decodeToString(encoded)

    decoded shouldBe original
  }

  it should "shorten the a UUID with radix=62 to 22 digits" in {

    val codec = Alphabet.getCodec(Alphabet.radix62)

    val original = UniversallyUniqueId.getNew.toBytes
    val encoded = codec.encode(original)
    val decoded = codec.decode(encoded)

    original should have length 16
    encoded should have length 22
    decoded should have length 16
    decoded shouldBe original
  }

  it should "shorten the a UUID with radix=94 to 20 digits" in {

    val codec = Alphabet.getCodec(Alphabet.radix94)

    val original = UniversallyUniqueId.getNew.toBytes
    val encoded = codec.encode(original)
    val decoded = codec.decode(encoded)

    original should have length 16
    encoded should have length 20
    decoded should have length 16
    decoded shouldBe original
  }

  it should "work with maximum byte length correctly" in {

    val codec = Alphabet.getCodec(Alphabet.radix62)

    val original = Random.nextBytes(127).toIndexedSeq
    val encoded = codec.encode(original)
    val decoded = codec.decode(encoded)

    decoded shouldBe original
  }

  it should "work with minimum byte length correctly" in {

    val codec = Alphabet.getCodec(Alphabet.radix62)

    val original = Random.nextBytes(1).toIndexedSeq
    val encoded = codec.encode(original)
    val decoded = codec.decode(encoded)

    decoded shouldBe original
  }
}
