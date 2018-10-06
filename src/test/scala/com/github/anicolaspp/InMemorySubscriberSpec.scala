
package com.github.anicolaspp

import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers}

import scala.collection.mutable.ListBuffer

case class Data[A](value: A, id: Int)


class InMemorySubscriberSpec extends FlatSpec with Matchers
  with BeforeAndAfterEach {

  implicit def toAnyEvent[A]: ToEvent[A] = new ToEvent[A] {
    override def event(a: A): Event[A] = E(a)
  }

  it should "receive events from publisher" in {
    val log = ListBuffer.empty[Event[String]]

    Subscriber.apply().subscribe[java.lang.String](e => log.append(e))
    Publisher().publish("5")

    log.toList should be(List(E("5")))
  }

  it should "subscribe to more than one stream" in {
    val log1 = ListBuffer.empty[Event[Int]]
    val log2 = ListBuffer.empty[Event[Data[String]]]

    Subscriber.apply().subscribe[Int](e => log1.append(e))
    Subscriber().subscribe[Data[String]](e => log2.append(e))

    Publisher().publish(5)
    Publisher().publish(Data[String]("world", 5))

    log1.toList should contain(E(5))
    log2.toList should contain(E(Data("world", 5)))
  }

  it should "unsubcribe from topic" in {
    val log1 = ListBuffer.empty[Event[Int]]
    val log2 = ListBuffer.empty[Event[Data[String]]]

    val intSubscriber = Subscriber.apply()
    intSubscriber.subscribe[Int](e => log1.append(e))

    Subscriber().subscribe[Data[String]](e => log2.append(e))

    Publisher().publish(5)
    Publisher().publish(Data[String]("world", 5))

    log1.toList should contain only (E(5))
    log2.toList should contain(E(Data("world", 5)))

    intSubscriber.unsubscribe[Int]()

    Publisher().publish(5)
    log1.toList should contain only (E(5))
  }

  override protected def beforeEach(): Unit = InMemoryLog.clear()
}
