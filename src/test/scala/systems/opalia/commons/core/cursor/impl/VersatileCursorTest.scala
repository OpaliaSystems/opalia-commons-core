package systems.opalia.commons.core.cursor.impl

import java.io.ByteArrayInputStream
import org.scalatest.flatspec.*
import org.scalatest.matchers.should.*
import scala.util.Random
import systems.opalia.commons.core.rendering.Renderer


class VersatileCursorTest
  extends AnyFlatSpec
    with Matchers {

  it should "be able to create a cursor from an iterable or iterator" in {

    val original = Random.nextBytes(2000).toIndexedSeq
    val cursor = VersatileCursor.fromIterable(original)

    cursor.toIndexedSeq shouldBe original
  }

  it should "be able to create a cursor from another cursor" in {

    val original = Random.nextBytes(2000).toIndexedSeq
    val cursor1 = VersatileCursor.fromIterable(original)
    val cursor2 = VersatileCursor.fromCursor(cursor1)
    val cursor3 = VersatileCursor.fromCursor(cursor2)

    cursor3.toIndexedSeq shouldBe original
  }

  it should "be able to create a cursor from an input stream" in {

    val original = "This is a test."
    val stream = new ByteArrayInputStream(original.getBytes(Renderer.appDefaultCharset))
    val cursor = VersatileCursor.fromInputStream(stream)

    new String(cursor.toIndexedSeq.toArray, Renderer.appDefaultCharset) shouldBe original
  }

  it should "be able to create an iterator from a cursor" in {

    val original = Random.nextBytes(2000).toIndexedSeq
    val cursor = VersatileCursor.fromIterable(original)
    val iterator = cursor.iterator

    iterator.toIndexedSeq shouldBe original
  }

  it should "be able to concatenate multiple cursors to one cursor" in {

    val cursor1 = VersatileCursor.fromIterable("This")
    val cursor2 = VersatileCursor.fromIterable(" is")
    val cursor3 = VersatileCursor.fromIterable(" a")
    val cursor4 = VersatileCursor.fromIterable(" test.")
    val cursor5 = VersatileCursor.concat(cursor1, cursor2, cursor3, cursor4)

    cursor5.toIndexedSeq.mkString shouldBe "This is a test."
  }

  it should "be able to transform from a cursor to another cursor" in {

    val original = Seq(1, 2, 3, 4)
    val cursor1 = VersatileCursor.fromIterable(original)
    val cursor2 = cursor1.transform(x => x * 2)

    cursor2.toIndexedSeq shouldBe original.map(x => x * 2)
  }

  it should "validate the number of elements if required" in {

    val cursor1 = VersatileCursor.fromIterable(Seq[Int]())
    val cursor2 = VersatileCursor.fromIterable(Seq[Int]())
    val cursor3 = VersatileCursor.fromIterable(Seq[Int](1, 2))
    val cursor4 = VersatileCursor.fromIterable(Seq[Int](1, 2))

    the[NoSuchElementException] thrownBy cursor1.toNonEmptyIndexedSeq
    the[NoSuchElementException] thrownBy cursor2.toSingle
    the[IllegalArgumentException] thrownBy cursor3.toSingle
    the[IllegalArgumentException] thrownBy cursor4.toSingleOpt
  }
}
