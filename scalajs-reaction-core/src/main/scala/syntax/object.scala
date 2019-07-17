// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js
import js._

final case class JsObjectOps[A <: js.Object](o: A) {
  def asDict[B]                                = o.asInstanceOf[js.Dictionary[B]]
  def asAnyDict                                = o.asInstanceOf[js.Dictionary[js.Any]]
  def asDyn                                    = o.asInstanceOf[js.Dynamic]
  def asUndefOr[A]: js.UndefOr[A]              = o.asInstanceOf[js.UndefOr[A]]
  def combine(that: A) = react.merge[A](o, that)
  def combine(that: js.Dynamic) = react.merge[A](o, that.asInstanceOf[A])
  def combineTo[B <: js.Object](that: js.Object) = react.merge[js.Object](o, that).asInstanceOf[B]
}

/** These should be picked by `<: js.Object` but don't seem to be. */
final case class JsDictionaryOps(o: js.Dictionary[_]) {
  def asJsObj = o.asInstanceOf[js.Object]
  def asDyn   = o.asInstanceOf[js.Dynamic]
}

trait JsObjectSyntax {
  implicit def jsObjectOpsSyntax[A <: js.Object](a: A)    = new JsObjectOps(a)
  implicit def jsDictionaryOpsSyntax(a: js.Dictionary[_]) = new JsDictionaryOps(a)
}
