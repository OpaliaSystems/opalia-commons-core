package systems.opalia.commons.core.scripting

import systems.opalia.commons.core.json.JsonAst


trait ScriptContext {

  def bindings: ScriptValue

  def eval(script: String, name: String): ScriptValue

  def eval(script: String): ScriptValue

  def eval(script: ScriptCompilation): ScriptValue

  def asValue(value: Any): ScriptValue

  def asValue(value: JsonAst.JsonValue): ScriptValue

  def asJson(value: Any): JsonAst.JsonValue

  def asJson(value: ScriptValue): JsonAst.JsonValue
}
