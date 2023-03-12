package systems.opalia.commons.core.identifier.impl

import java.nio.ByteBuffer
import java.util.UUID
import scala.collection.immutable.ArraySeq
import scala.util.Try
import systems.opalia.commons.core.codec.impl.Hex
import systems.opalia.commons.core.identifier.*
import systems.opalia.commons.core.utility.rendering.*


final class UniversallyUniqueId private(protected val data: Vector[Byte])
  extends Identifier {

  def renderString(renderer: StringRenderer): StringRenderer = {

    renderer ~
      Hex.encode(data.slice(0, 4)) ~
      '-' ~
      Hex.encode(data.slice(4, 6)) ~
      '-' ~
      Hex.encode(data.slice(6, 8)) ~
      '-' ~
      Hex.encode(data.slice(8, 10)) ~
      '-' ~
      Hex.encode(data.slice(10, 16))
  }
}

object UniversallyUniqueId
  extends IdentifierCompanion[UniversallyUniqueId] {

  def isValid(that: String): Boolean = {

    val parts =
      that.split("-")

    if (parts.length != 5)
      false
    else
      parts
        .zip(List(4, 2, 2, 2, 6))
        .forall(part => Hex.isValid(part._1) && part._1.length == part._2 * 2)
  }

  def isValid(that: Seq[Byte]): Boolean =
    that.length == length

  def length: Int =
    16

  def getNew: UniversallyUniqueId = {

    val uuid = UUID.randomUUID()

    val bytes =
      ByteBuffer.allocate(length)
        .order(Renderer.appDefaultByteOrder)
        .putLong(uuid.getMostSignificantBits) // 8 bytes
        .putLong(uuid.getLeastSignificantBits) // 8 bytes

    new UniversallyUniqueId(bytes.array.toVector)
  }

  def getFrom(that: String): UniversallyUniqueId =
    getFromOpt(that)
      .getOrElse(throw new IllegalArgumentException(s"Cannot generate UUID from: $that"))

  def getFrom(that: Seq[Byte]): UniversallyUniqueId =
    getFromOpt(that)
      .getOrElse(throw new IllegalArgumentException(s"Cannot generate UUID from: $that"))

  def getFromOpt(that: String): Option[UniversallyUniqueId] =
    if (UniversallyUniqueId.isValid(that))
      Try(UUID.fromString(that)).toOption.map((x) => {

        val bytes =
          ByteBuffer.allocate(length)
            .order(Renderer.appDefaultByteOrder)
            .putLong(x.getMostSignificantBits) // 8 bytes
            .putLong(x.getLeastSignificantBits) // 8 bytes

        new UniversallyUniqueId(bytes.array.toVector)
      })
    else
      None

  def getFromOpt(that: Seq[Byte]): Option[UniversallyUniqueId] =
    if (UniversallyUniqueId.isValid(that))
      Some(new UniversallyUniqueId(that.toVector))
    else
      None

  def getFromName(that: String): UniversallyUniqueId =
    getFromName(ArraySeq.unsafeWrapArray(that.getBytes(Renderer.appDefaultCharset)))

  def getFromName(that: Seq[Byte]): UniversallyUniqueId = {

    val uuid = UUID.nameUUIDFromBytes(that.toArray)

    val bytes =
      ByteBuffer.allocate(length)
        .order(Renderer.appDefaultByteOrder)
        .putLong(uuid.getMostSignificantBits) // 8 bytes
        .putLong(uuid.getLeastSignificantBits) // 8 bytes

    new UniversallyUniqueId(bytes.array.toVector)
  }
}
