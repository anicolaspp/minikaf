
package com.github.anicolaspp

import scala.reflect.runtime.universe._

trait Publisher {
  def publish[A](a: A)(implicit c: ToEvent[A], typeTag: TypeTag[A]): Unit
}

object Publisher {
  def apply(): Publisher = new InMemoryPublisher
}

class InMemoryPublisher extends Publisher with TopicExtractor {

  val log = InMemoryLog.instance

  override def publish[A](a: A)(implicit c: ToEvent[A], typeTag: TypeTag[A]): Unit = log.append(c.event(a), topicFor[A])
}














