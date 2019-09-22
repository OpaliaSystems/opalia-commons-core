package systems.opalia.commons.core.scripting

import systems.opalia.commons.core.concurrent.Terminatable


trait ScriptSession
  extends Terminatable[Unit] {

  def context: ScriptContext

  def enter(): Unit

  def leave(): Unit

  def withContext[T](block: (ScriptContext) => T): T = {

    enter()

    try {

      block(context)

    } finally {

      leave()
    }
  }
}
