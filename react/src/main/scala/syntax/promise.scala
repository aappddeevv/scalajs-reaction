// Copyright (c) 2019 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package react

import scala.scalajs.js
import js._

// To make a promise:
// (resolve: js.Function1[Unit|js.Thenable[Unit],_], reject: js.Function1[scala.Any,_]) =>
trait JSPromiseSyntax {
  /** More ergonomic typed methods for js.Promise processing. */
  implicit class RichPromise[A](self: js.Thenable[A]) {
    /** map */
    def jsThen[B](f: A => B) = self.`then`[B](f(_): B | js.Thenable[B], js.undefined)

    /** flatMap */
    def jsThenF[B](f: A => js.Thenable[B]) = self.`then`[B](f(_): B | js.Thenable[B], js.undefined)

    /** co-map */
    def jsThen[B](onFulfilled: A => B, onRejected: scala.Any => B) =
      self.`then`[B](
        onFulfilled(_): B | js.Thenable[B],
        js.defined{(any: scala.Any) => onRejected(any): B | js.Thenable[B]})

    /** co-flatMap */
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

    /** Return this value if successful, else that's value or error. */
    def orElse[A](that: => js.Thenable[A]) = self.`then`[A](
      a => a.asInstanceOf[A|js.Thenable[A]],
      js.defined{(_:scala.Any) => that.asInstanceOf[A|js.Thenable[A]]}
    )

    /** Tap into the result. */
    def tap(f: A => js.Thenable[Unit]) = self.jsThenF(a => f(a).jsThen(_ => a))
  }

  /** Ergonomic syntax for Promise.resolve and Promise.reject. Or, use like
   * `PromiseValue(true).resolve`. `a` should be a strict value.
   */
  implicit class PromiseValue[A](a: A) {
    def resolve = js.Promise.resolve[A](a)
    def reject = js.Promise.reject(a)
  }
}
