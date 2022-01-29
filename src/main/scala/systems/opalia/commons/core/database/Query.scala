package systems.opalia.commons.core.database


trait Query {

  def on[T](key: String, value: T)(implicit writer: FieldWriter[T]): Query

  def executeAndFetch(): Result

  def execute(): Unit
}
