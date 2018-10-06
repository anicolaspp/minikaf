
package com.github.anicolaspp

import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers}

class InMemoryPublisherSpec extends FlatSpec
  with Matchers
  with TopicExtractor
  with BeforeAndAfterEach {

  implicit def toAnyEvent[A]: ToEvent[A] = new ToEvent[A] {
    override def event(a: A) = E(a)
  }

  it should "send event to log" in {

    val publisher = Publisher()

    publisher.publish("hehe")

    InMemoryLog.instance.run.get("TestEvent").foreach(_.length should be (1))
    InMemoryLog.instance.run.get("TestEvent").foreach(_ should be (List(E("hehe"))))
  }

  it should "send a lot of events to the log and preserve the order" in {

    val publisher = Publisher()

    (0 to 99).foreach(i => publisher.publish(TestEvenWithId(i.toString, i)))

    InMemoryLog.instance.run.get(topicFor[TestEvenWithId]).foreach(_.length should be (100))
    InMemoryLog.instance.run.get(topicFor[TestEvenWithId]).foreach(_.reverse.zipWithIndex.foreach { case (e, i) =>
      e.value.asInstanceOf[TestEvenWithId].id.toString should be (i.toString)
    })
  }

  it should "separate events by topic automatically" in {
    val publisher = Publisher()

    publisher.publish("hello")
    publisher.publish(Data("other", 5))
    publisher.publish("world")

    InMemoryLog.instance.run.get("TestEvent").foreach(_ should be (List("world", "hello")))
    InMemoryLog.instance.run.get("TestEvenWithId").foreach(topic => {
      topic.head.asInstanceOf[TestEvenWithId].id.toString should be ("5")
    })
  }
  
  override protected def beforeEach(): Unit = InMemoryLog.clear()
}




