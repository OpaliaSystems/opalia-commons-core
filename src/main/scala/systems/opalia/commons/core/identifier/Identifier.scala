package systems.opalia.commons.core.identifier

import java.util.Objects
import systems.opalia.commons.core.codec.AsciiCodec
import systems.opalia.commons.core.utility.rendering.*


trait Identifier
  extends IndexedSeq[Byte]
    with StringRenderable
    with ByteRenderable {

  protected val data: Vector[Byte]

  override final def apply(index: Int): Byte =
    data(index)

  override final def length: Int =
    data.length

  override final def equals(that: Any): Boolean =
    that match {

      case that: Identifier if (this.getClass == that.getClass && this.sameElements(that)) => true
      case _ => false
    }

  override final def hashCode: Int =
    Objects.hash(data.map(Byte.box): _*)

  override final def renderBytes(renderer: ByteRenderer): ByteRenderer = {

    renderer ++= data
  }

  final def toStringWithCodec(codec: AsciiCodec): String =
    codec.encode(data)
}
