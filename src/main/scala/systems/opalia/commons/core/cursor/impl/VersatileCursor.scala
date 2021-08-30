package systems.opalia.commons.core.cursor.impl

import java.io.Closeable
import java.util.NoSuchElementException
import systems.opalia.commons.core.cursor.*


abstract class VersatileCursor[T]
  extends Cursor[T] {

  def transform[R](f: T => R): VersatileCursor[R] =
    new VersatileCursor[R]() {

      override def next(): Boolean =
        VersatileCursor.this.next()

      override def get: R =
        f.apply(VersatileCursor.this.get)

      override def close(): Unit =
        VersatileCursor.this.close()
    }

  override def iterator: ClosableIterator[T] =
    iterator(identity)

  def iterator[R](f: T => R): ClosableIterator[R] =
    new ClosableIterator[R]() {

      private var initialized, hasMore = false

      override def hasNext: Boolean = {

        if (!initialized) {

          hasMore = VersatileCursor.this.next()
          initialized = true
        }

        hasMore
      }

      override def next: R = {

        if (!initialized)
          hasMore = VersatileCursor.this.next()

        if (!hasMore)
          throw new NoSuchElementException("There is no element left for further iterations.")

        initialized = false

        f.apply(VersatileCursor.this.get)
      }

      override def close(): Unit = {

        VersatileCursor.this.close()
      }
    }

  override def close(): Unit = {
  }

  def toNonEmptyIndexedSeq: IndexedSeq[T] = {

    val result = this.toIndexedSeq

    if (result.isEmpty)
      throw new NoSuchElementException("Expect a cursor with at least one element.")

    result
  }

  def toSingle: T = {

    val result = this.toNonEmptyIndexedSeq

    if (result.size > 1)
      throw new IllegalArgumentException("Expect a cursor with exactly one element.")

    result.head
  }

  def toSingleOpt: Option[T] = {

    val result = this.toIndexedSeq

    if (result.size > 1)
      throw new IllegalArgumentException("Expect a cursor with one or zero elements.")

    result.headOption
  }
}

object VersatileCursor {

  def fromIterable[T](source: Iterable[T]): VersatileCursor[T] =
    fromIterator(source.iterator)

  def fromIterator[T](source: Iterator[T]): VersatileCursor[T] =
    new VersatileCursor[T]() {

      private var element: T = null.asInstanceOf[T] // "Option" is not used here for performance reasons

      override def next(): Boolean = {

        if (source.hasNext)
          element = source.next()
        else
          element = null.asInstanceOf[T]

        element != null
      }

      override def get: T = {

        if (element == null) {

          if (source.hasNext)
            throw new IllegalStateException("The cursor is not yet initialized.")

          throw new NoSuchElementException("There is no element left.")
        }

        element
      }

      override def close(): Unit = {

        source match {
          case value: Closeable => value.close()
          case _ =>
        }
      }
    }

  def fromCursor[T](source: Cursor[T]): VersatileCursor[T] =
    new VersatileCursor[T]() {

      override def next(): Boolean =
        source.next()

      override def get: T =
        source.get

      override def close(): Unit =
        source.close()
    }

  def concat[T, C <: Cursor[T]](cursorsHead: C, cursorsTail: C*): VersatileCursor[T] =
    concat(cursorsHead +: cursorsTail)

  def concat[T, C <: Cursor[T]](cursors: Seq[C]): VersatileCursor[T] =
    new VersatileCursor[T]() {

      var index = 0

      override def next(): Boolean = {

        while (index < cursors.size) {

          if (cursors(index).next())
            return true

          index += 1
        }

        false
      }

      override def get: T = {

        cursors(index).get
      }

      override def close(): Unit = {

        for (cursor <- cursors)
          cursor.close()
      }
    }
}
