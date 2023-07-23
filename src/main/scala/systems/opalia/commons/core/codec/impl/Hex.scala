package systems.opalia.commons.core.codec.impl

import java.nio.charset.Charset
import scala.collection.immutable.ArraySeq
import scala.collection.mutable
import scala.util.Try
import systems.opalia.commons.core.codec.AsciiCodec
import systems.opalia.commons.core.utility.mathx


object Hex
  extends AsciiCodec {

  def encode(data: Seq[Byte]): String =
    data.map("%02x" format _).mkString

  def encode(data: String): String =
    encode(data, Charset.defaultCharset)

  def encode(data: String, charset: Charset): String =
    encode(ArraySeq.unsafeWrapArray(data.getBytes(charset)))

  def decode(data: String): IndexedSeq[Byte] =
    data.toList
      .sliding(2, 2)
      .map {
        octet =>

          if (octet.size != 2)
            throw new IllegalArgumentException("Expect a complete octet sequence.")

          mathx.parseUnsignedLong(octet, radix = 16, java.lang.Byte.BYTES).toByte
      }
      .toVector

  def decodeToString(data: String): String =
    decodeToString(data, Charset.defaultCharset)

  def decodeToString(data: String, charset: Charset): String =
    new String(decode(data).toArray, charset)

  def decodeTry(data: String): Try[IndexedSeq[Byte]] =
    Try(decode(data))

  def decodeToStringTry(data: String): Try[String] =
    Try(decodeToString(data))

  def decodeToStringTry(data: String, charset: Charset): Try[String] =
    Try(decodeToString(data, charset))

  def isValid(data: String): Boolean =
    data.matches("""^([0-9A-Fa-f]{2})+$""")
}
