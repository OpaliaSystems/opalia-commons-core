package systems.opalia.commons.core.logging


trait SubLogger {

  val name: String

  def apply(message: => String): Unit

  def apply(message: => String, throwable: Throwable): Unit
}

object SubLogger {

  def nop: SubLogger =
    new SubLogger {

      val name: String = "no-op-logger"

      def apply(message: => String): Unit = {
      }

      def apply(message: => String, throwable: Throwable): Unit = {
      }
    }
}
