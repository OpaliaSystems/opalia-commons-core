package systems.opalia.commons.core.database

import systems.opalia.commons.core.utility.json.JsonAst


trait Row {

  def apply[T](column: String)(implicit reader: FieldReader[T]): T

  def toJson: JsonAst.JsonObject
}
