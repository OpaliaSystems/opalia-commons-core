package systems.opalia.commons.core.scripting

import systems.opalia.commons.core.utility.json.Json


trait ScriptContext {

  def bindings: ScriptValue

  def eval(script: String, name: String): ScriptValue

  def eval(script: String): ScriptValue

  def eval(script: ScriptCompilation): ScriptValue

  def asValue(value: Any): ScriptValue

  def asValue(value: Json.Node): ScriptValue

  def asJson(value: Any): Json.Node

  def asJson(value: ScriptValue): Json.Node
}
