package systems.opalia.commons.core.database

import scala.util.Try


trait FieldReader[T] {

  def apply(column: String, value: Any): Try[T]
}
