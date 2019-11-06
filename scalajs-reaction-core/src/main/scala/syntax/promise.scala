// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js
import js._

// To make a promise:
// (resolve: js.Function1[Unit|js.Thenable[Unit],_], reject: js.Function1[scala.Any,_]) =>
trait JSPromiseSyntax {
  /** Provide more robustly typed methods so you can use a js.Promise more
   * easily.
   */
  implicit class RichJSPromise[A](self: js.Thenable[A]) {
    def jsThen[B](f: A => B) = self.`then`[B](f(_): B | js.Thenable[B], js.undefined)

    def jsThenF[B](f: A => js.Thenable[B]) = self.`then`[B](f(_): B | js.Thenable[B], js.undefined)

    def jsThen[B](onFulfilled: A => B, onRejected: scala.Any => B) =
      self.`then`[B](
        onFulfilled(_): B | js.Thenable[B],
        js.defined{(any: scala.Any) => onRejected(any): B | js.Thenable[B]})

    def jsThenF[B](onFulfilled: A => js.Thenable[B], onRejected: scala.Any => js.Thenable[B]) =
      self.`then`[B](
        onFulfilled(_): B | js.Thenable[B],
        js.defined{(any: scala.Any) => onRejected(any): B | js.Thenable[B]})

    def jsCatch[B](f: scala.Any => B) = self.`then`[B](
      js.undefined.asInstanceOf[js.Function1[A,B|js.Thenable[B]]],
      js.defined{(any: scala.Any) => f(any): B | js.Thenable[B]})

    def jsCatchF[B](f: scala.Any => js.Thenable[B]) = self.`then`[B](
      js.undefined.asInstanceOf[js.Function1[A,B|js.Thenable[B]]],
      js.defined{(any: scala.Any) => f(any): B | js.Thenable[B]})
  }
}
