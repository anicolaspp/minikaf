/**
  * Created by anicolaspp on 3/23/17.
  */
package com.nico

import com.nico.Event.E
import simulacrum.typeclass

@typeclass trait ToEvent[A] {
  def event(a: A): Event[A]
}

object ToEvent {

//  implicit class Ext[A](a: A) {
//    def event(f: A => Event[A]): Event[A] = f(a)
//
//    def event: ToEvent[A] = new ToEvent[A] {
//      override def event(a: A): Event[A] = E(a)
//    }
//  }

  def event[A](a: A)(f: A => Event[A]): Event[A] = f(a)

  implicit def toEvent[A]: ToEvent[A] = new ToEvent[A] {
    override def event(a: A) = E(a)
  }
}
