package systems.opalia.commons.core.logging


abstract class Logger {

  val name: String

  val logLevel: LogLevel

  def traceEnabled: Boolean =
    logLevel >= LogLevel.TRACE

  def debugEnabled: Boolean =
    logLevel >= LogLevel.DEBUG

  def infoEnabled: Boolean =
    logLevel >= LogLevel.INFO

  def warningEnabled: Boolean =
    logLevel >= LogLevel.WARNING

  def errorEnabled: Boolean =
    logLevel >= LogLevel.ERROR

  def trace(message: => String): Unit = {

    if (traceEnabled)
      internal(LogLevel.TRACE, message)
  }

  def trace(message: => String, throwable: Throwable): Unit = {

    if (traceEnabled)
      internal(LogLevel.TRACE, message, throwable)
  }

  def debug(message: => String): Unit = {

    if (debugEnabled)
      internal(LogLevel.DEBUG, message)
  }

  def debug(message: => String, throwable: Throwable): Unit = {

    if (debugEnabled)
      internal(LogLevel.DEBUG, message, throwable)
  }

  def info(message: => String): Unit = {

    if (infoEnabled)
      internal(LogLevel.INFO, message)
  }

  def info(message: => String, throwable: Throwable): Unit = {

    if (infoEnabled)
      internal(LogLevel.INFO, message, throwable)
  }

  def warning(message: => String): Unit = {

    if (warningEnabled)
      internal(LogLevel.WARNING, message)
  }

  def warning(message: => String, throwable: Throwable): Unit = {

    if (warningEnabled)
      internal(LogLevel.WARNING, message, throwable)
  }

  def error(message: => String): Unit = {

    if (errorEnabled)
      internal(LogLevel.ERROR, message)
  }

  def error(message: => String, throwable: Throwable): Unit = {

    if (errorEnabled)
      internal(LogLevel.ERROR, message, throwable)
  }

  def log(logLevel: LogLevel, message: => String): Unit = {

    if (logLevel <= this.logLevel)
      internal(logLevel, message)
  }

  def log(logLevel: LogLevel, message: => String, throwable: Throwable): Unit = {

    if (logLevel <= this.logLevel)
      internal(logLevel, message, throwable)
  }

  def subLogger(logLevel: LogLevel): SubLogger = {

    new SubLogger {

      val name: String = Logger.this.name

      def apply(message: => String): Unit = {

        if (logLevel <= Logger.this.logLevel)
          Logger.this.internal(logLevel, message)
      }

      def apply(message: => String, throwable: Throwable): Unit = {

        if (logLevel <= Logger.this.logLevel)
          Logger.this.internal(logLevel, message, throwable)
      }
    }
  }

  protected def internal(logLevel: LogLevel, message: String, throwable: Throwable = null): Unit
}
