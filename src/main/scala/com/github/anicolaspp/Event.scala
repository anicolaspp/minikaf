package com.github.anicolaspp

trait Event[A] {
  def value: A
}

case class E[A](value: A) extends Event[A]


