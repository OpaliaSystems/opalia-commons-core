package systems.opalia.commons.core.concurrent

import java.util.concurrent.atomic.AtomicBoolean
import scala.concurrent.{Future, Promise}
import scala.util.Try


trait Bootable[T, U]
  extends Terminatable[U] {

  private val promise = Promise[T]()
  private val up: AtomicBoolean = new AtomicBoolean(false)

  override final def completelyUp: Boolean =
    promise.isCompleted

  final def awaitUp(): Future[T] =
    promise.future

  final def setup(): Boolean = {

    if (up.getAndSet(true))
      false
    else {

      promise.complete(Try(setupTask()))
      true
    }
  }

  protected def setupTask(): T
}
