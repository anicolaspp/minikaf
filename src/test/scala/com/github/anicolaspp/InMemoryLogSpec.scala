
package com.github.anicolaspp

import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers}


case class TestEvenWithId(value: String, id: Int) extends Event[String]


class InMemoryLogSpec extends FlatSpec
  with Matchers
  with BeforeAndAfterEach {


  it should "be a singleton log object" in {
    InMemoryLog.instance should be(InMemoryLog.instance)
  }

  it should "be a unique (shared) log" in {
    val logger = InMemoryLog.instance

    logger.append(E("hello"), "a")

    val otherLogger = InMemoryLog.instance
    otherLogger.run.get("a").foreach(_ should be(List(E("hello"))))
  }

  it should "clear the log for everyone" in {
    val logger = InMemoryLog.instance
    val other = InMemoryLog.instance

    logger.append(E("hello"), "a")
    other.append(E("world"), "a")

    InMemoryLog.instance.run.get("a").foreach(_.length should be(2))

    InMemoryLog.clear()

    logger.run.keys.toList.length should be(0)
    other.run.keys.toList.length should be(0)
    InMemoryLog.instance.run.keys.toList.length should be(0)
  }

  it should "limit the size of the log" in {
    val logger = InMemoryLog.instance

    (1 to 1005).foreach(i => logger.append(E(i.toString), "a"))

    logger.run("a").length should be(1000)
  }

  it should "contain the most recent events" in {
    val logger = InMemoryLog.instance

    (1 to 1005).foreach(i => logger.append(E(i.toString), "a"))

    logger
      .run("a")
      .map(_.asInstanceOf[E[String]].value)
      .reverse should be((6 to 1005).map(_.toString).toList)

  }

  override protected def beforeEach(): Unit = InMemoryLog.clear()
}

