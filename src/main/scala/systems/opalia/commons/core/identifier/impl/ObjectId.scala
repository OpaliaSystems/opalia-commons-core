package systems.opalia.commons.core.identifier.impl

import java.net.NetworkInterface
import java.nio.ByteBuffer
import java.util.Objects
import java.util.concurrent.atomic.AtomicInteger
import scala.jdk.CollectionConverters.*
import scala.util.Random
import systems.opalia.commons.core.codec.impl.Alphabet
import systems.opalia.commons.core.identifier.*
import systems.opalia.commons.core.rendering.*


final class ObjectId private(protected val data: Vector[Byte])
  extends Identifier {

  def renderString(renderer: StringRenderer): StringRenderer = {

    renderer ~ ObjectId.defaultCodec.encode(data)
  }
}

object ObjectId
  extends IdentifierCompanion[ObjectId] {

  private val defaultCodec = Alphabet.getCodec(Alphabet.radix62)

  private object Generator {

    val counter = new AtomicInteger(Random.nextInt())

    def machinePart: Int = {

      val hardwareAddresses =
        NetworkInterface.getNetworkInterfaces.asScala.toSeq
          .map(_.getHardwareAddress)
          .filter(_ != null)
          .flatten

      hardwareAddresses.hashCode
    }

    def processPart: Int = {

      val processId = Int.box(ProcessHandle.current.pid.toInt)
      val classLoaderId = Int.box(System.identityHashCode(this.getClass.getClassLoader))

      Objects.hash(processId, classLoaderId)
    }

    def applicationPart: Int = {

      val processValue = Int.box(processPart)
      val machineValue = Int.box(machinePart)

      Objects.hash(processValue, machineValue)
    }

    def counterPart: Int = {

      counter.getAndIncrement
    }

    def timestampPart: Long = {

      System.currentTimeMillis()
    }

    def randomPart: Int = {

      Random.nextInt()
    }
  }

  def isValid(that: String): Boolean =
    defaultCodec.isValid(that)

  def isValid(that: Seq[Byte]): Boolean =
    that.length == length

  def length: Int =
    16

  def getNew: ObjectId = {

    val counterValue = Generator.counterPart
    val timestampValue = Generator.timestampPart / 10

    val bytes =
      ByteBuffer.allocate(length)
        .order(Renderer.appDefaultByteOrder)
        .putInt(Generator.applicationPart)
        .put(((counterValue >>> 16) & 0xFF).toByte)
        .put(((counterValue >>> 8) & 0xFF).toByte)
        .put((counterValue & 0xFF).toByte)
        .put(((timestampValue >>> 32) & 0xFF).toByte)
        .put(((timestampValue >>> 24) & 0xFF).toByte)
        .put(((timestampValue >>> 16) & 0xFF).toByte)
        .put(((timestampValue >>> 8) & 0xFF).toByte)
        .put((timestampValue & 0xFF).toByte)
        .putInt(Generator.randomPart)

    new ObjectId(bytes.array.toVector)
  }

  def getFrom(that: String): ObjectId =
    getFromOpt(that)
      .getOrElse(throw new IllegalArgumentException(s"Cannot generate ObjectId from: $that"))

  def getFrom(that: Seq[Byte]): ObjectId =
    getFromOpt(that)
      .getOrElse(throw new IllegalArgumentException(s"Cannot generate ObjectId from: $that"))

  def getFromOpt(that: String): Option[ObjectId] =
    if (ObjectId.isValid(that))
      defaultCodec.decodeTry(that).toOption.map(x => new ObjectId(x.toVector))
    else
      None

  def getFromOpt(that: Seq[Byte]): Option[ObjectId] =
    if (ObjectId.isValid(that))
      Some(new ObjectId(that.toVector))
    else
      None
}
