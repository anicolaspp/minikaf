/**
  * Created by anicolaspp on 3/3/17.
  */
package com.nico

trait TopicExtractor {
  def topicFor[A](a: A): String = a.getClass.getName.replace("$", ".")
}
