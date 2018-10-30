package systems.opalia.commons.core.logging


trait LoggingService {

  def newLogger(name: String): Logger
}
