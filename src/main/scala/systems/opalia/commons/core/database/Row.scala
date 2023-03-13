package systems.opalia.commons.core.database

import systems.opalia.commons.core.utility.json.Json


trait Row {

  def apply[T](column: String)(implicit reader: FieldReader[T]): T

  def toJson: Json.ObjectNode
}
