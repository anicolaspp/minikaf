package com.github.anicolaspp

import simulacrum.typeclass

@typeclass trait ToEvent[A] {
  def event(a: A): Event[A]
}

object ToEvent {
  def event[A](f: A => Event[A]): ToEvent[A] = new ToEvent[A] {
    override def event(a: A): Event[A] = f(a)
  }

  implicit def toEvent[A]: ToEvent[A] = new ToEvent[A] {
    override def event(a: A): Event[A] = E(a)
  }
}