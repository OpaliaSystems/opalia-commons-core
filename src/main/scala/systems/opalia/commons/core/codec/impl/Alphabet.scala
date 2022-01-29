package systems.opalia.commons.core.codec.impl

import java.nio.charset.Charset
import scala.collection.immutable.ArraySeq
import scala.collection.mutable
import scala.util.Try
import systems.opalia.commons.core.codec.AsciiCodec
import systems.opalia.commons.core.mathx
import systems.opalia.commons.core.rendering.*


object Alphabet {

  val dec: String = "0123456789"
  val alphaLower: String = "abcdefghijklmnopqrstuvwxyz"
  val alphaUpper: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
  val hexLower: String = dec + "abcdef"
  val hexUpper: String = dec + "ABCDEF"
  val radix36: String = dec + alphaLower
  val radix62: String = dec + alphaLower + alphaUpper
  val radix94: String = (for (x <- 0x21 to 0x7E) yield x.toChar).mkString

  def getCodec(alphabet: String): AsciiCodec = {

    if (alphabet.length < 2)
      throw new NumberFormatException("The alphabet length is out of range.");

    if (alphabet.distinct.length != alphabet.length)
      throw new NumberFormatException("Expect unique characters in alphabet.");

    if (alphabet.exists(x => x.toInt < 0x21 && x.toInt > 0x7E))
      throw new NumberFormatException("Reject unsupported characters in alphabet.");

    new AsciiCodec {

      def encode(data: Seq[Byte]): String = {

        if (data.isEmpty | data.length > 127)
          throw new NumberFormatException("Number of bytes is out of range.");

        val result = toStringFromBigInt(toBigIntFromBytes(data))
        val length = getNumberOfDigits(data.length)

        padString(result, length)
      }

      def encode(data: String): String =
        encode(data, Renderer.appDefaultCharset)

      def encode(data: String, charset: Charset): String =
        encode(ArraySeq.unsafeWrapArray(data.getBytes(charset)))

      def decode(data: String): IndexedSeq[Byte] = {

        val result = toBytesFromBigInt(toBigIntFromString(data))
        val length = getNumberOfBytes(data.length)

        padBytes(result, length)
      }

      def decodeToString(data: String): String =
        decodeToString(data, Renderer.appDefaultCharset)

      def decodeToString(data: String, charset: Charset): String =
        new String(decode(data).toArray, charset)

      def decodeTry(data: String): Try[IndexedSeq[Byte]] =
        Try(decode(data))

      def decodeToStringTry(data: String): Try[String] =
        Try(decodeToString(data))

      def decodeToStringTry(data: String, charset: Charset): Try[String] =
        Try(decodeToString(data, charset))

      def isValid(data: String): Boolean =
        data.forall(x => alphabet.contains(x))

      private def toBigIntFromBytes(data: Seq[Byte]): BigInt = {

        data.flatMap {
          x =>

            List(
              (x >>> 7) & 1,
              (x >>> 6) & 1,
              (x >>> 5) & 1,
              (x >>> 4) & 1,
              (x >>> 3) & 1,
              (x >>> 2) & 1,
              (x >>> 1) & 1,
              x & 1,
            )
        }
          .foldLeft(BigInt(0))((a, b) => a * 2 + b)
      }

      private def toBigIntFromString(data: String): BigInt = {

        val values = data.map(x => alphabet.indexOf(x))

        if (values.exists(x => x < 0))
          throw new NumberFormatException("Cannot handle unexpected character.")

        values.foldLeft(BigInt(0))((a, b) => a * alphabet.length + b)
      }

      private def toStringFromBigInt(value: BigInt): String = {

        val renderer = new StringRenderer()
        var number = value;

        while {

          val digit = (number mod alphabet.length).toInt
          number /= alphabet.length

          renderer ~ alphabet.charAt(digit)

          number != 0
        }
        do ()

        renderer.result().reverse
      }

      private def toBytesFromBigInt(value: BigInt): IndexedSeq[Byte] = {

        val renderer = new ByteRenderer()
        val buffer = mutable.ArrayBuffer[Int]()
        var number = value;

        while {

          val digit = (number mod 2).toInt
          number /= 2

          buffer.append(digit)

          number != 0
        }
        do ()

        for (i <- buffer.indices by 8) {

          val byte =
            (if (i + 7 < buffer.length) buffer(i + 7) << 7 else 0) |
              (if (i + 6 < buffer.length) buffer(i + 6) << 6 else 0) |
              (if (i + 5 < buffer.length) buffer(i + 5) << 5 else 0) |
              (if (i + 4 < buffer.length) buffer(i + 4) << 4 else 0) |
              (if (i + 3 < buffer.length) buffer(i + 3) << 3 else 0) |
              (if (i + 2 < buffer.length) buffer(i + 2) << 2 else 0) |
              (if (i + 1 < buffer.length) buffer(i + 1) << 1 else 0) |
              buffer.apply(i)

          renderer ~ byte.toByte
        }

        renderer.result().reverse
      }

      private def padString(data: String, length: Int): String = {

        val renderer = new StringRenderer()

        for (_ <- data.length until length)
          renderer ~ alphabet.head

        renderer ~ data

        renderer.result()
      }

      private def padBytes(data: IndexedSeq[Byte], length: Int): IndexedSeq[Byte] = {

        val renderer = new ByteRenderer()

        for (_ <- data.length until length)
          renderer ~ 0.toByte

        renderer ++= data

        renderer.result()
      }

      private def getNumberOfDigits(numberOfBytes: Int): Int = {

        mathx.digitCount(alphabet.length, math.pow(2, numberOfBytes * 8) - 1)
      }

      private def getNumberOfBytes(numberOfDigits: Int): Int = {

        var numberOfBytes = 1

        while (numberOfBytes <= 127)
        do {

          if (numberOfDigits == getNumberOfDigits(numberOfBytes))
            return numberOfBytes

          numberOfBytes += 1
        }

        throw new NumberFormatException("The number of digits is too large.")
      }
    }
  }
}
