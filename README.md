# minikaf
Minimal Implementation of Pub / Sub based on Kafka ideas

The main idea of this library is to have a pub / sub mechanism based on topics like in Kafka but with automatic topic 
selection. 

The library is based on `ScalaRx` for in memory message passing, but it also removes the need of connecting `publishers` 
and `subscribes`.
 

In `ScalaRx` in order to publish a message and the message to be process we need a `Subject[A]` that we can use
in the following way:

First, we need to create the `Subject` with the specific type of message we are going to send

```
val subject = Subject[Int]
```

Then we need a `subscriber` that listen to the `subject`

```
def subscribeTo[A](s: Subject[A], with: A => Unit) = s.subscribe(event => with(event)) 
```

And now we call `onNext` to push the message into the `Stream`

```
subject.onNext(5)
```

Once the message is pushed the function `with` will be executed async (this is how `ScalaRx` works). 

This implementation is very well tested and work fine, the `Rx` guys have done a magnificent job here. However, in order
to connect a `publisher` and a `subscriber` we need to have them both. 

Our library eliminate this problem completely. It allows us to define `publishers` and `subscribers` in any part of our 
code without knowing anything about the rest. In here is where the `Kafka` ideas come up. 

Our subscription model works similar to `Kafka` where we publish a message into a topic and receive message from topics.
However, we don't want to specify the topic itself, we are going to use the `types` of the message as topics.

```
val subscriber = Subscriber()
subscriber.subscribe[Int](e => println((e.topic, e.value)))

val publisher = Publisher()
publisher.publish(5)
publisher.publish("hello")
```

The result will be:
 
```
(Int, 5)
```

When we `publish` the value `5` we extract the type `Int` from it and use it as the `topic`.
We do the same when we `subscribe`. We use the type parameter as the `topic` for our messages.

As you might noticed, we are not connecting in any way the `subscriber` and `publisher`, all this machinery is happening 
behind the scene. Also, we can have multiple `subscribers` and `publishers` without problem. Let's see an example.

```
val intSubscriber = Subscriber()
intSubscriber.subscribe[Int](e => println(s"I am an Int: $e.value")

val stringSubscriber = Subscriber()
stringSubscriber.subscribe[Int](e => println(s"I am a String: $e.value")


Publisher().publish(5)
Publisher().publish("hello")
```

Each `subscriber` will get only the messages that it is `subscribed` to.


