package com.nico

trait Event[A] {
  def value: A
}

object Event {

//  def event[A](f: A => Event[A]): ToEvent[A] = new ToEvent[A] {
//    override def event(a: A) = f(a)
//  }

  case class E[A](value: A) extends Event[A]
}

//object A {
//
//  import ToEvent._
//  import ToEvent.ops._
//
//  case class MyEvent(value: Int) extends Event[Int]
//
//  def x = {
//    val event = 5.event
//
//
//    val anotherEvent = 4.event(MyEvent)
//
//
//  }
//}