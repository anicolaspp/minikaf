
package com.github.anicolaspp

import scala.reflect.runtime.universe._

trait TopicExtractor {
  def topicFor[A](implicit typeTag: TypeTag[A]): String = typeTag.tpe.toString //.replace("$", ".")
}
