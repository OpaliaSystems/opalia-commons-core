package systems.opalia.commons.core.database

import scala.util.Try


trait FieldWriter[T] {

  def apply(key: String, value: T): Try[Any]
}
