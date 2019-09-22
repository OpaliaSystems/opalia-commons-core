package systems.opalia.commons.core.scripting

import scala.collection.mutable


sealed trait ScriptProxy

trait ExecutableProxy
  extends ScriptProxy
    with (Seq[ScriptValue] => Any)

trait ImmutableArrayProxy
  extends ScriptProxy
    with IndexedSeq[Any]

trait ImmutableMapProxy
  extends ScriptProxy
    with Map[String, Any]

trait MutableArrayProxy
  extends ScriptProxy
    with mutable.IndexedSeq[Any]

trait MutableMapProxy
  extends ScriptProxy
    with mutable.Map[String, Any]
