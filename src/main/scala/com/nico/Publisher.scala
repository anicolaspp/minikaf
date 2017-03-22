/**
  * Created by anicolaspp on 3/20/17.
  */
package com.nico

trait Publisher {
  def publish[A](a: Event[A]): Unit
}

object Publisher {
  def apply(): Publisher = new InMemoryPublisher
}

class InMemoryPublisher extends Publisher with TopicExtractor {

  val log = InMemoryLog.instance

  override def publish[A](a: Event[A]): Unit = log.append(a, topicFor(a.value))
}














