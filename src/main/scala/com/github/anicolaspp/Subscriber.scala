
package com.github.anicolaspp

import rx.lang.scala.Subscription

import scala.reflect.runtime.universe._

trait Subscriber {
  def unsubscribe[A](): Unit

  def subscribe[A](f: Event[A] => Unit)(implicit tag: TypeTag[A]): Unit
}

object Subscriber {
  def apply(): Subscriber = new InMemorySubscriber
}

class InMemorySubscriber extends Subscriber with TopicExtractor {

  private var subscription: Subscription = _

  override def subscribe[A](f: Event[A] => Unit)(implicit tag: TypeTag[A]): Unit = {
    subscription = InMemoryLog.instance
      .stream
      .filter(x => x.topic == topicFor[A](tag))
      .map(_.value.asInstanceOf[Event[A]])
      .subscribe(event => f(event))
  }

  override def unsubscribe[A](): Unit = if (!subscription.isUnsubscribed) subscription.unsubscribe()
}