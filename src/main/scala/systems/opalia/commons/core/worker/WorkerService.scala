package systems.opalia.commons.core.worker


trait WorkerService {

  def newProducer(topic: String, local: Boolean = false): Producer

  def newConsumer(topic: String, consume: (Message) => Message): Consumer

  def registerObjectMapper(objectMapper: ObjectMapper): Unit

  def unregisterObjectMapper(id: Int): Unit
}
