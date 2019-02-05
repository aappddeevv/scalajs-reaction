// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js
import js._

final case class JsObjectOps[A <: js.Object](o: A) {
  @inline def asDict[B]                                = o.asInstanceOf[js.Dictionary[B]]
  @inline def asAnyDict                                = o.asInstanceOf[js.Dictionary[js.Any]]
  @inline def asDyn                                    = o.asInstanceOf[js.Dynamic]
  @inline def asUndefOr[A]: js.UndefOr[A]              = o.asInstanceOf[js.UndefOr[A]]
  @inline def combine[B <: js.Object](that: js.Object) = react.merge(o, that).asInstanceOf[B]
}

/** These should be picked by `<: js.Object` but don't seem to be. */
final case class JsDictionaryOps(o: js.Dictionary[_]) {
  @inline def asJsObj = o.asInstanceOf[js.Object]
  @inline def asDyn   = o.asInstanceOf[js.Dynamic]
}

trait JsObjectSyntax {
  @inline implicit def jsObjectOpsSyntax[A <: js.Object](a: A)    = new JsObjectOps(a)
  @inline implicit def jsDictionaryOpsSyntax(a: js.Dictionary[_]) = new JsDictionaryOps(a)
}
