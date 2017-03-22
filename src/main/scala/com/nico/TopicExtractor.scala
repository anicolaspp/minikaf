
package com.nico

trait TopicExtractor {
  def topicFor[A](a: A): String = a.getClass.getName.replace("$", ".")
}
