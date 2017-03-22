///**
//  * Created by anicolaspp on 3/3/17.
//  */
//package com.nico
//
//import com.nico.Events.TestEvent
//import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers}
//
//import scala.collection.mutable.ListBuffer
//
//class InMemorySubscriberSpec extends FlatSpec with Matchers
//  with BeforeAndAfterEach {
//
//  it should "receive events from publisher" in {
//    val log = ListBuffer.empty[Int]
//
//    Subscriber.apply().subscribe[Int](e => log.append(e))
//    Publisher.inMemoryPublisher().publish(5)
//
//    log.toList should be (List(TestEvent("hello")))
//  }
//
//  it should "subscribe to more than one stream" in {
//    val log1 = ListBuffer.empty[DomainEvent]
//    val log2 = ListBuffer.empty[DomainEvent]
//
//    Subscriber.apply().subscribe[TestEvent](e => log1.append(e))
//    Subscriber.apply().subscribe[TestEvenWithId](e => log2.append(e))
//
//    DomainEventPublisher.inMemoryPublisher().publish(TestEvent("hello"))
//    DomainEventPublisher.inMemoryPublisher().publish(createEvent("world", 5))
//
//    log1.toList should be (List(TestEvent("hello")))
//    log2.toList.map(_.toString) should be (List(createEvent("world", 5).toString))
//  }
//
//  override protected def beforeEach(): Unit = InMemoryLog.clear()
//}
