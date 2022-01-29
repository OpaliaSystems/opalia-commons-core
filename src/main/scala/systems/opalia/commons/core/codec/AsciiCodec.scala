package systems.opalia.commons.core.codec

import java.nio.charset.Charset
import scala.util.Try


trait AsciiCodec {

  def encode(data: Seq[Byte]): String

  def encode(data: String): String

  def encode(data: String, charset: Charset): String

  def decode(data: String): IndexedSeq[Byte]

  def decodeToString(data: String): String

  def decodeToString(data: String, charset: Charset): String

  def decodeTry(data: String): Try[IndexedSeq[Byte]]

  def decodeToStringTry(data: String): Try[String]

  def decodeToStringTry(data: String, charset: Charset): Try[String]

  def isValid(data: String): Boolean
}
