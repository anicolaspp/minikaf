package com.github.anicolaspp


import org.openjdk.jmh.annotations.{Benchmark, Param, Scope, Setup, State}

@State(Scope.Benchmark)
class StreamBench {

  @State(Scope.Thread)
  def sendMessages: Unit = {
    var sum = 0

    val subscriber = Subscriber()
    subscriber.subscribe[Int](e => sum += e.value)

    val publisher = Publisher()

    (1 to 1000000).foreach(v => publisher.publish(v))
  }
}
