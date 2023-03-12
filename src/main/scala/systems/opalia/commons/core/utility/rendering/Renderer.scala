package systems.opalia.commons.core.utility.rendering

import java.nio.charset.Charset
import java.nio.{ByteBuffer, ByteOrder}
import java.util.Locale
import scala.collection.mutable


sealed trait Renderer[R <: Renderer[R, S, U, T], S <: Renderable, U, T]
  extends IndexedSeq[T] {

  def newEmpty: R

  def result(): U

  def +=(value: T): this.type

  def ++=(iterable: Iterable[T]): this.type

  def ~(value: Boolean): this.type =
    append(value)

  def ~(value: Byte): this.type =
    append(value)

  def ~(value: Short): this.type =
    append(value)

  def ~(value: Int): this.type =
    append(value)

  def ~(value: Long): this.type =
    append(value)

  def ~(value: Float): this.type =
    append(value)

  def ~(value: Double): this.type =
    append(value)

  def ~(value: Char): this.type =
    append(value)

  def ~(string: String): this.type =
    append(string)

  def ~(renderer: R): this.type =
    append(renderer)

  def ~(renderable: S): this.type =
    append(renderable)

  def append(value: Boolean): this.type

  def append(value: Byte): this.type

  def append(value: Short): this.type

  def append(value: Int): this.type

  def append(value: Long): this.type

  def append(value: Float): this.type

  def append(value: Double): this.type

  def append(value: Char): this.type

  def append(string: String): this.type

  def append(renderer: R): this.type

  def append(renderable: S): this.type

  def append(charSequence: CharSequence): this.type

  def append(iterable: Iterable[Char]): this.type

  def appendCodePoint(value: Int): this.type

  def appendCodePoints(iterable: Iterable[Int]): this.type

  def appendAny(value: Any): this.type =
    append(String.valueOf(value))

  def appendAny(value: Any, format: String): this.type =
    append(format.format(value))

  def appendAny(value: Any, format: String, locale: Locale): this.type =
    append(format.formatLocal(locale, value))

  def glue(iterable: Iterable[R], delimiter: String): this.type = {

    val iterator = iterable.iterator

    if (iterator.hasNext) {

      append(iterator.next())

      while (iterator.hasNext) {

        append(delimiter)
        append(iterator.next())
      }
    }

    this
  }
}

object Renderer {

  val utf8Charset: Charset = Charset.forName("UTF-8")
  val appDefaultCharset: Charset = utf8Charset
  val osDefaultCharset: Charset = Charset.defaultCharset()
  val bigEndianByteOrder: ByteOrder = ByteOrder.BIG_ENDIAN
  val littleEndianByteOrder: ByteOrder = ByteOrder.LITTLE_ENDIAN
  val appDefaultByteOrder: ByteOrder = bigEndianByteOrder
  val osDefaultByteOrder: ByteOrder = ByteOrder.nativeOrder()
}

final class StringRenderer()
  extends Renderer[StringRenderer, StringRenderable, String, Char]
    with CharSequence {

  private val builder = new java.lang.StringBuilder()

  def newEmpty: StringRenderer =
    new StringRenderer()

  def charAt(index: Int): Char =
    builder.charAt(index)

  def apply(index: Int): Char =
    builder.charAt(index)

  def subSequence(indexBegin: Int, indexEnd: Int): CharSequence =
    builder.subSequence(indexBegin, indexEnd)

  def length: Int =
    builder.length()

  def result(): String =
    builder.toString

  def clear(): Unit =
    builder.setLength(0)

  def +=(value: Char): this.type =
    append(value)

  def ++=(iterable: Iterable[Char]): this.type = {

    builder.append(iterable.toArray)

    this
  }

  def append(value: Boolean): this.type = {

    builder.append(value)

    this
  }

  def append(value: Byte): this.type = {

    builder.append(value)

    this
  }

  def append(value: Short): this.type = {

    builder.append(value)

    this
  }

  def append(value: Int): this.type = {

    builder.append(value)

    this
  }

  def append(value: Long): this.type = {

    builder.append(value)

    this
  }

  def append(value: Float): this.type = {

    builder.append(value)

    this
  }

  def append(value: Double): this.type = {

    builder.append(value)

    this
  }

  def append(value: Char): this.type = {

    builder.append(value)

    this
  }

  def append(string: String): this.type = {

    builder.append(string)

    this
  }

  def append(renderer: StringRenderer): this.type = {

    builder.append(renderer)

    this
  }

  def append(renderable: StringRenderable): this.type = {

    renderable.renderString(this)

    this
  }

  def append(charSequence: CharSequence): this.type = {

    builder.append(charSequence)

    this
  }

  def append(iterable: Iterable[Char]): this.type = {

    builder.append(iterable.toArray)

    this
  }

  def appendCodePoint(value: Int): this.type = {

    builder.appendCodePoint(value)

    this
  }

  def appendCodePoints(iterable: Iterable[Int]): this.type = {

    iterable.foreach(builder.appendCodePoint)

    this
  }
}

final class ByteRenderer(val charset: Charset = Renderer.appDefaultCharset,
                         val byteOrder: ByteOrder = Renderer.appDefaultByteOrder)
  extends Renderer[ByteRenderer, ByteRenderable, Vector[Byte], Byte] {

  private val builder = mutable.ArrayBuffer[Byte]()
  private val buffer = ByteBuffer.allocate(8).order(byteOrder)

  def newEmpty: ByteRenderer =
    new ByteRenderer(charset, byteOrder)

  def apply(index: Int): Byte =
    builder.apply(index)

  def length: Int =
    builder.length

  def result(): Vector[Byte] =
    builder.toVector

  def clear(): Unit =
    builder.clear()

  def +=(value: Byte): this.type =
    append(value)

  def ++=(iterable: Iterable[Byte]): this.type = {

    builder.appendAll(iterable)

    this
  }

  def append(value: Boolean): this.type = {

    if (value)
      builder.append(0x01.toByte)
    else
      builder.append(0x00.toByte)

    this
  }

  def append(value: Byte): this.type = {

    builder.append(value)

    this
  }

  def append(value: Short): this.type = {

    buffer.putShort(value)
    builder.appendAll(buffer.array())
    buffer.clear()

    this
  }

  def append(value: Int): this.type = {

    buffer.putInt(value)
    builder.appendAll(buffer.array())
    buffer.clear()

    this
  }

  def append(value: Long): this.type = {

    buffer.putLong(value)
    builder.appendAll(buffer.array())
    buffer.clear()

    this
  }

  def append(value: Float): this.type = {

    buffer.putFloat(value)
    builder.appendAll(buffer.array())
    buffer.clear()

    this
  }

  def append(value: Double): this.type = {

    buffer.putDouble(value)
    builder.appendAll(buffer.array())
    buffer.clear()

    this
  }

  def append(value: Char): this.type = {

    builder.appendAll((new String(Array(value))).getBytes(charset))

    this
  }

  def append(string: String): this.type = {

    builder.appendAll(string.getBytes(charset))

    this
  }

  def append(renderer: ByteRenderer): this.type = {

    builder.appendAll(renderer)

    this
  }

  def append(renderable: ByteRenderable): this.type = {

    renderable.renderBytes(this)

    this
  }

  def append(charSequence: CharSequence): this.type = {

    builder.appendAll(charSequence.toString.getBytes(charset))

    this
  }

  def append(iterable: Iterable[Char]): this.type = {

    builder.appendAll((new String(iterable.toArray)).getBytes(charset))

    this
  }

  def appendCodePoint(value: Int): this.type = {

    builder.appendAll((new String(Array(value), 0, 1)).getBytes(charset))

    this
  }

  def appendCodePoints(iterable: Iterable[Int]): this.type = {

    builder.appendAll((new String(iterable.toArray, 0, iterable.size)).getBytes(charset))

    this
  }
}
