package systems.opalia.commons.core.cursor

import java.io.Closeable


trait ClosableIterator[T]
  extends Iterator[T]
    with Closeable {

  def hasNext: Boolean

  def next(): T

  def close(): Unit
}
