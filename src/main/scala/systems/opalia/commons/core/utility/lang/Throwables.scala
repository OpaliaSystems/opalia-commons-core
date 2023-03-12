package systems.opalia.commons.core.utility.lang

import java.io.{PrintWriter, StringWriter}
import scala.annotation.tailrec


object Throwables {

  def getRootCause(throwable: Throwable): Throwable = {

    getCausalChain(throwable).last
  }

  def getCausalChain(throwable: Throwable): Seq[Throwable] = {

    @tailrec
    def traverse(cause: Throwable, acc: List[Throwable]): List[Throwable] = {

      if (cause == null)
        acc
      else if (acc.contains(cause))
        throw new IllegalArgumentException("Cycle in causal chain detected.", cause)
      else
        traverse(cause.getCause, acc :+ cause)
    }

    traverse(throwable, Nil)
  }

  def getStackTraceAsString(throwable: Throwable): String = {

    val stringWriter = new StringWriter()

    throwable.printStackTrace(new PrintWriter(stringWriter))

    stringWriter.toString
  }

  def isFatal(throwable: Throwable): Boolean =
    getCausalChain(throwable).exists(_.isInstanceOf[Error])
}
