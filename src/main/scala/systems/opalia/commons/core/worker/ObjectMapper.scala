package systems.opalia.commons.core.worker


trait ObjectMapper {

  def id: Int

  def canHandle(obj: AnyRef): Boolean

  def toBinary(obj: AnyRef): IndexedSeq[Byte]

  def fromBinary(bytes: Seq[Byte]): AnyRef
}
