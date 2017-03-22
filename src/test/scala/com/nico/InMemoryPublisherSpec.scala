/**
  * Created by anicolaspp on 3/2/17.
  */
package com.nico

import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers}
import Event._

class InMemoryPublisherSpec extends FlatSpec
  with Matchers
  with BeforeAndAfterEach {

  import Events._

  it should "send event to log" in {

    val publisher = Publisher()

    publisher.publish(Event.toEvent("hehe"))

    InMemoryLog.instance.run.get("TestEvent").foreach(_.length should be (1))
    InMemoryLog.instance.run.get("TestEvent").foreach(_ should be (List(E("hehe"))))
  }

  it should "send a lot of events to the log and preserve the order" in {

    val publisher = Publisher()

    (0 to 99).foreach(i => publisher.publish(TestEvenWithId(i.toString, i)))

    InMemoryLog.instance.run.get("Int").foreach(_.length should be (100))
    InMemoryLog.instance.run.get("Int").foreach(_.reverse.zipWithIndex.foreach { case (e, i) =>

        e.asInstanceOf[TestEvenWithId].id.toString should be (i.toString)
    })
  }

  it should "separate events by topic automatically" in {
    val publisher = Publisher()

    publisher.publish(Event.toEvent("hello"))
    publisher.publish(TestEvenWithId("other", 5))
    publisher.publish(Event.toEvent("world"))

    InMemoryLog.instance.run.get("TestEvent").foreach(_ should be (List("world", "hello")))
    InMemoryLog.instance.run.get("TestEvenWithId").foreach(topic => {
      topic.head.asInstanceOf[TestEvenWithId].id.toString should be ("5")
    })
  }

  override protected def beforeEach(): Unit = InMemoryLog.clear()
}




