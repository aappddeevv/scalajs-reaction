// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package vdom

import scala.scalajs.js

trait Classname[A] {
  def classname(a: A): String
}

object Classname {
  def apply[A](implicit w: Classname[A]): Classname[A] = w

  object syntax {

    /** Call as in classname(yourA) */
    def classname[A: Classname](a: A) = Classname[A].classname(a)

    /** Call as yourA.classname or yourA.cn. */
    implicit class ClassnameOps[A: Classname](a: A) {
      def classname = Classname[A].classname(a)
      def cn        = Classname[A].classname(a)
    }
  }

  // implicit resolution search in the companion will find these

  implicit val stringClassname: Classname[String] = s => s
  implicit val intClassname: Classname[Int]       = i => i.toString()
  // implicit def undefToClassname[T](t: js.UndefOr[T]): Classname[T] =
  //   t.fold("")(t => t.toString())
  //implicit def tuple2Classname[T](p: (T, Boolean)): Classname[(T,Boolean)] =
  //  if(p._2) p._1.toString() else ""
}
