
package com.github.anicolaspp

import scala.reflect.runtime.universe._

trait Subscriber {
  def unsubscribe[A](): Unit

  def subscribe[A](f: Event[A] => Unit)(implicit tag: TypeTag[A]): Unit
}

object Subscriber {
  def apply(): Subscriber = new InMemorySubscriber
}

