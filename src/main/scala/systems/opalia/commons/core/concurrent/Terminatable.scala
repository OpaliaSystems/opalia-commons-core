package systems.opalia.commons.core.concurrent

import java.util.concurrent.atomic.AtomicBoolean
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future, Promise}
import scala.util.Try


trait Terminatable[T] {

  private val promise = Promise[T]()
  private val down: AtomicBoolean = new AtomicBoolean(false)

  def completelyUp: Boolean =
    true

  final def completelyDown: Boolean =
    promise.isCompleted

  final def awaitDown(): Future[T] =
    promise.future

  final def shutdown(): Boolean = {

    if (!completelyUp)
      false
    else if (down.getAndSet(true))
      false
    else {

      promise.complete(Try(shutdownTask()))
      true
    }
  }

  final def addShutdownHook(timeout: Duration = Duration.Inf): Unit = {

    sys.addShutdownHook({
      shutdown()
      Await.result(awaitDown(), timeout)
    })
  }

  protected def shutdownTask(): T
}
