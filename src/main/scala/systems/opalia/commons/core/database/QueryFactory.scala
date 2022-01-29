package systems.opalia.commons.core.database


trait QueryFactory {

  def newQuery(statement: String): Query
}
