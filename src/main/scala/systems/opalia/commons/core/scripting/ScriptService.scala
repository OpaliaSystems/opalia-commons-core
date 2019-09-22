package systems.opalia.commons.core.scripting

import scala.concurrent.duration.Duration


trait ScriptService {

  def newScriptEngine(contextTimeout: Duration = Duration.Inf): ScriptEngine
}
