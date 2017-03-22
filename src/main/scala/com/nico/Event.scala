package com.nico

trait Event[A] {
  def value: A
}

object Event {

  implicit def toEvent[A](a: A): Event[A] = E(a)

  case class E[A](value: A) extends Event[A]
}
