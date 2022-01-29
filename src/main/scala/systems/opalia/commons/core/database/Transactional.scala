package systems.opalia.commons.core.database


trait Transactional {

  def withTransaction[T](block: (QueryFactory) => T): T

  def createClosableTransaction(): ClosableTransaction
}
