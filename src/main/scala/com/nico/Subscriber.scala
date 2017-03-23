
package com.nico

import scala.reflect.runtime.universe._

trait Subscriber {
  def subscribe[A](f: Event[A] => Unit)(implicit tag: TypeTag[A]): Unit
}

object Subscriber {
  def apply(): Subscriber = new InMemorySubscriber
}

class InMemorySubscriber extends Subscriber with TopicExtractor {
  override def subscribe[A](f: (Event[A]) => Unit)(implicit tag: TypeTag[A]): Unit =
    InMemoryLog.instance
      .stream
      .filter{ x =>
          println(x.topic)
          println(topicFor[A](tag))

        x.topic == topicFor[A](tag)}
      .map(e => e.value.asInstanceOf[Event[A]])
      .subscribe(event => f(event))

//  private def topicFor[A](tag: TypeTag[A]) = {
//
////    println(typeOf[A].toString)
//
////    typeOf[A].toString
//    tag.tpe.toString
//  }
}