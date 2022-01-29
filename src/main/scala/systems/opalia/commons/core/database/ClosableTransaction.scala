package systems.opalia.commons.core.database

import java.io.Closeable


trait ClosableTransaction
  extends Closeable {

  def queryFactory: QueryFactory

  def close(): Unit

  def close(cause: Exception): Unit
}
