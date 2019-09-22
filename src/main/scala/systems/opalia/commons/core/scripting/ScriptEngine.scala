package systems.opalia.commons.core.scripting


trait ScriptEngine {

  def newSession(): ScriptSession

  def withSession[T](block: (ScriptSession) => T): T = {

    val session = newSession()

    try {

      block(session)

    } finally {

      session.shutdown()
    }
  }

  def compile(script: String, name: String): ScriptCompilation

  def compile(script: String): ScriptCompilation
}
