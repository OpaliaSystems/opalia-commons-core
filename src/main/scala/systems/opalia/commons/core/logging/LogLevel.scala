package systems.opalia.commons.core.logging


sealed abstract class LogLevel(val id: Int, val name: String)
  extends Ordered[LogLevel] {

  def compare(that: LogLevel): Int =
    this.id - that.id

  override def toString: String =
    name
}

object LogLevel
  extends Ordering[LogLevel] {

  case object OFF
    extends LogLevel(0, "OFF")

  case object ERROR
    extends LogLevel(1, "ERROR")

  case object WARNING
    extends LogLevel(2, "WARNING")

  case object INFO
    extends LogLevel(3, "INFO")

  case object DEBUG
    extends LogLevel(4, "DEBUG")

  case object TRACE
    extends LogLevel(5, "TRACE")

  val values: Seq[LogLevel] =
    OFF :: ERROR :: WARNING :: INFO :: DEBUG :: TRACE :: Nil

  def compare(a: LogLevel, b: LogLevel): Int =
    a.compare(b)

  def withNameOpt(string: String): Option[LogLevel] =
    values.find(_.toString == string)

  def withName(string: String): LogLevel =
    withNameOpt(string).getOrElse(throw new IllegalArgumentException(s"Cannot find LogLevel with name “$string”."))
}
