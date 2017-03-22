
package com.nico

import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers}
import Event._
import com.nico.Events.TestEvenWithId

import scala.collection.mutable.ListBuffer

import Events._


class InMemorySubscriberSpec extends FlatSpec with Matchers
  with BeforeAndAfterEach {

  implicit def toAnyEvent[A]: ToEvent[A] = new ToEvent[A] {
    override def event(a: A) = E(a)
  }

  it should "receive events from publisher" in {
    val log = ListBuffer.empty[Event[String]]

    Subscriber.apply().subscribe[String](e => log.append(e))
    Publisher().publish("5")

    log.toList should be (List(E("5")))
  }

  it should "subscribe to more than one stream" in {
    val log1 = ListBuffer.empty[Event[String]]
    val log2 = ListBuffer.empty[Event[String]]

    Subscriber.apply().subscribe[String](e => log1.append(e))
    Subscriber.apply().subscribe[String](e => log2.append(e))

    Publisher().publish("hello")
    Publisher().publish(TestEvenWithId("world", 5))

    log1.toList should be (List(E("hello")))
    log2.toList.map(_.toString) should be (List(TestEvenWithId("world", 5).toString))
  }

  override protected def beforeEach(): Unit = InMemoryLog.clear()
}
