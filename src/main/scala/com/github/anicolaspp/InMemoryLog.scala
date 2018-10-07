
package com.github.anicolaspp

import rx.lang.scala.Subject

object InMemoryLog {

  lazy val instance = new InMemoryLog()

  private[anicolaspp] def clear() = instance.clear()

  case class EventRecord[A](topic: String, value: A)

  class InMemoryLog {

    lazy val stream: Subject[EventRecord[Event[_]]] = Subject[EventRecord[Event[_]]]
    
    private[this] val log = scala.collection.mutable.Map.empty[String, List[Event[_]]]

    def append[A](a: Event[A], topic: String): Unit = {

      val update = log.getOrElseUpdate(topic, List.empty[Event[A]])

      log.update(topic, withSize(1000, a :: update))

      stream.onNext(EventRecord(topic, a))
    }

    def run = log.toMap

    private def withSize[A](n: Int, log: List[A]) =
      if (log.length > n) {
        log.dropRight(1)
      } else {
        log
      }

    def clear() = log.clear()
  }

}


object Example {

  case class EvenNumber(n: Int)

  def main(args: Array[String]): Unit = {
    val p = Publisher()

    p.publish(5)


  }


}

