// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package vdom

/**
  * Tagging experiment. Do not depend on this in your code.
  * From: https://github.com/softwaremill/scala-common/blob/master/tagging/src/main/scala/com/softwaremill/tagging/package.scala
  */
package object tagging {
  type Tag[+U]        = { type Tag <: U }
  type @@[+T, +U]     = T with Tag[U]
  type Tagged[+T, +U] = T with Tag[U]
  implicit class Tagger[T](val t: T) extends AnyVal {
    def taggedWith[U]: T @@ U = t.asInstanceOf[T @@ U]
  }
  implicit class AndTagger[T, U](val t: T @@ U) extends AnyVal {
    def andTaggedWith[V]: T @@ (U with V) = t.asInstanceOf[T @@ (U with V)]
  }
}
