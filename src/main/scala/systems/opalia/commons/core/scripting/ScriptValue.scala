package systems.opalia.commons.core.scripting


trait ScriptValue {

  // for arrays

  def hasArrayElements: Boolean

  def getArrayElement(index: Int): ScriptValue

  def setArrayElement(index: Int, value: Any): Unit

  def removeArrayElement(index: Int): Boolean

  def getArraySize: Int

  // for structured objects

  def hasMembers: Boolean

  def hasMember(key: String): Boolean

  def getMember(key: String): ScriptValue

  def getMemberKeys: Set[String]

  def putMember(key: String, value: Any): Unit

  def removeMember(key: String): Boolean

  // for executables and type objects

  def getMetaObject: ScriptValue

  def canExecute: Boolean

  def execute(arguments: Any*): ScriptValue

  def canInstantiate: Boolean

  def newInstance(arguments: Any*): ScriptValue

  def canInvokeMember(key: String): Boolean

  def invokeMember(key: String, arguments: Any*): ScriptValue

  // for native types

  def isNull: Boolean

  def isBoolean: Boolean

  def isString: Boolean

  def isNumber: Boolean

  def fitsInByte: Boolean

  def fitsInShort: Boolean

  def fitsInInt: Boolean

  def fitsInLong: Boolean

  def fitsInFloat: Boolean

  def fitsInDouble: Boolean

  def asBoolean: Boolean

  def asString: String

  def asByte: Byte

  def asShort: Short

  def asInt: Int

  def asLong: Long

  def asFloat: Float

  def asDouble: Double

  // for other types

  def isForeignObject: Boolean

  def asForeignObject: AnyRef

  def as[T](clazz: Class[T]): T
}
