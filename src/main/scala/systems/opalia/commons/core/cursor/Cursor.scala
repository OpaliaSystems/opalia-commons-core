package systems.opalia.commons.core.cursor

import java.io.Closeable


trait Cursor[T]
  extends Iterable[T]
    with Closeable {

  def next(): Boolean

  def get: T

  def close(): Unit
}
