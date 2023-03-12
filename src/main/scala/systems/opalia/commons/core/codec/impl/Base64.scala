package systems.opalia.commons.core.codec.impl

import java.nio.charset.Charset
import java.util.Base64 as Base64Handler
import scala.collection.immutable.ArraySeq
import scala.util.Try
import systems.opalia.commons.core.codec.AsciiCodec
import systems.opalia.commons.core.utility.rendering.Renderer


object Base64
  extends AsciiCodec {

  def encode(data: Seq[Byte]): String =
    Base64Handler.getEncoder.encodeToString(data.toArray)

  def encode(data: String): String =
    encode(data, Renderer.appDefaultCharset)

  def encode(data: String, charset: Charset): String =
    encode(ArraySeq.unsafeWrapArray(data.getBytes(charset)))

  def decode(data: String): IndexedSeq[Byte] =
    Base64Handler.getDecoder.decode(data.getBytes(Renderer.appDefaultCharset)).toIndexedSeq

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
    data.matches("""^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$""")
}
