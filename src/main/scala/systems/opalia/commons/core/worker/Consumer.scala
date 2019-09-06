package systems.opalia.commons.core.worker


trait Consumer {

  def topic: String

  def apply(message: Message): Message

  def register(): Unit

  def unregister(): Unit
}
