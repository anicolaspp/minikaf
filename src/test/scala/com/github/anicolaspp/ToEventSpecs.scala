package com.github.anicolaspp

import org.scalatest.{FlatSpec, Matchers}

import ToEvent.ops._

class ToEventSpecs extends FlatSpec with Matchers {

  it should "create an event" in {
    5.event should be(E(5))
  }
}
