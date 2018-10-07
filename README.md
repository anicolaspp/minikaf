# Minikaf

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.anicolaspp/com.github.anicolaspp/minikaf_2.12/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.anicolaspp/minikaf_2.12) [![Build Status](https://travis-ci.com/anicolaspp/minikaf.svg?branch=master)](https://travis-ci.com/anicolaspp/minikaf)

```sbt
libraryDependencies += "com.github.anicolaspp" % "minikaf_2.12" % "x.y.z"
```

**Minikaf** is a minimal implementation of Publish / Subscriber for in memory communication.

The main idea of this library is to have a pub / sub mechanism based on topics similar to `Kafka` using automatic topic 
selection based on the data being sent.

**Minikaf** is based on `ScalaRx` for in memory message passing. However, it removes the need of connecting `publishers` 
and `subscribes`. There is a central, transparent hub where `publishers` can write to, and completely decompled, `subscribers` can read from. 
 
When using `ScalaRx`, in order to publish a message and the message to be processed, we need a `Subject[A]` that we can use
in the following way.

```scala
val subject = Subject[Int]
```

Then we need a `subscriber` that listen to the `Subject[A]`, connecting both ends, publisher and subscriber. 

```scala
val subscriber = subject.subscribe(event => someFunction(event)
```

Once we have created this connection, the publisher uses `.onNext` to push messages to the `Stream` that the connection represents.

```scala
subject.onNext(5)
```

Once the message is pushed, the defined function `someFunction` will be executed.

**Minikaf** eliminates the problem of connecting both ends by allowing us to define `publishers` and `subscribers` in any part of our code without explicitely connect them.

Our subscription model works similar to `Kafka`, where messages are published and consumed to and from topics, but with complete independence from each other. 

The specific topic used to send and received messages is automatically extracted from the type of the message being sent. 

```scala
val subscriber = Subscriber()
subscriber.subscribe[Int](e => println((e.topic, e.value)))

val publisher = Publisher()
publisher.publish(5)
publisher.publish("hello")
```

The result will be:
 
```scala
(Int, 5)
```

When we publish the value `5` we extract the type `Int` from it and use it as the `topic`.
We do the same when we `subscribe`. We use the type parameter as the `topic` for our messages.

As you might noticed, we are not explicitely connecting the `subscriber` and `publisher`, all this machinery is happening 
behind the scene. 

Multiple `subscribers` and `publishers` can be created and they work in total isolation.

```scala
val intSubscriber = Subscriber()
intSubscriber.subscribe[Int](e => println(s"I am an Int: $e.value")

val stringSubscriber = Subscriber()
stringSubscriber.subscribe[Int](e => println(s"I am a String: $e.value")


Publisher().publish(5)
Publisher().publish("hello")
```

Each `subscriber` will get only the messages that it is `subscribed` to.
