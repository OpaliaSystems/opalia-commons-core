package systems.opalia.commons.core.identifier

import java.util.Objects


trait Identifier
  extends IndexedSeq[Byte] {

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
}
