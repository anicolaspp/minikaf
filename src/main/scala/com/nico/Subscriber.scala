
package com.nico

import scala.reflect.runtime.universe._

trait Subscriber {
  def subscribe[A](f: Event[A] => Unit)(implicit tag: TypeTag[A]): Unit
}

object Subscriber {
  def apply(): Subscriber = new InMemorySubscriber
}

class InMemorySubscriber extends Subscriber {
  override def subscribe[A](f: (Event[A]) => Unit)(implicit tag: TypeTag[A]): Unit =
    InMemoryLog.instance
      .stream
      .filter{ x =>
          println(x.topic)
          println(topicFor[A])

        x.topic == topicFor[A]}
      .map(e => e.value.asInstanceOf[Event[A]])
      .subscribe(event => f(event))

  private def topicFor[A](implicit tag: TypeTag[A]) = typeOf[A].toString
}