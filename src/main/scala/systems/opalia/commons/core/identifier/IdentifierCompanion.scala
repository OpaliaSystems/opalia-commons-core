package systems.opalia.commons.core.identifier


trait IdentifierCompanion[T <: Identifier] {

  def isValid(that: String): Boolean

  def isValid(that: Seq[Byte]): Boolean

  def length: Int

  def getNew: T

  def getFrom(that: String): T

  def getFrom(that: Seq[Byte]): T

  def getFromOpt(that: String): Option[T]

  def getFromOpt(that: Seq[Byte]): Option[T]
}
