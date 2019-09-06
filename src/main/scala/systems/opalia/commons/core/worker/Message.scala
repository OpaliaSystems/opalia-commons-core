package systems.opalia.commons.core.worker


trait Message {

  def timestamp: Long

  def key: Option[AnyRef]

  def value: AnyRef
}

object Message {

  def apply(key: AnyRef, value: AnyRef): Message = {

    val _timestamp: Long = System.currentTimeMillis()
    val _key: Option[AnyRef] = Some(key)
    val _value: AnyRef = value

    new Message {

      def timestamp: Long =
        _timestamp

      def key: Option[AnyRef] =
        _key

      def value: AnyRef =
        _value
    }
  }

  def apply(value: AnyRef): Message = {

    val _timestamp: Long = System.currentTimeMillis()
    val _key: Option[AnyRef] = None
    val _value: AnyRef = value

    new Message {

      def timestamp: Long =
        _timestamp

      def key: Option[AnyRef] =
        _key

      def value: AnyRef =
        _value
    }
  }
}
