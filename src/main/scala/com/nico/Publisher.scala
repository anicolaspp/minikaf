
package com.nico

trait ToEvent[A] {
  def event(a: A): Event[A]
}

trait Publisher {
  def publish[A](a: A)(implicit c: ToEvent[A]): Unit
}

object Publisher {
  def apply(): Publisher = new InMemoryPublisher
}

class InMemoryPublisher extends Publisher with TopicExtractor {

  val log = InMemoryLog.instance

  override def publish[A](a: A)(implicit c: ToEvent[A]): Unit = log.append(c.event(a), topicFor(a))
}














