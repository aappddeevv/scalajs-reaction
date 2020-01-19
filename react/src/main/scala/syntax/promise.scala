/*
 * Copyright (c) 2018 The Trapelo Group
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package react

import scala.scalajs.js

import js.|

// To make a promise:
// (resolve: js.Function1[Unit|js.Thenable[Unit],_], reject: js.Function1[scala.Any,_]) =>
/** More ergonomic typed methods for js.Promise processing. */
final class JSPromiseOps[A](private val self: js.Thenable[A]) extends AnyVal {

  /** map */
  def jsThen[B](f: A => B) =
    self.`then`[B](f(_): B | js.Thenable[B], js.undefined)

  /** flatMap */
  def jsThenF[B](f: A => js.Thenable[B]) =
    self.`then`[B](f(_): B | js.Thenable[B], js.undefined)

  /** co-map */
  def jsThen[B](onFulfilled: A => B, onRejected: scala.Any => B) =
    self.`then`[B](onFulfilled(_): B | js.Thenable[B], js.defined { (any: scala.Any) =>
      onRejected(any): B | js.Thenable[B]
    })

  /** co-flatMap */
  def jsThenF[B](onFulfilled: A => js.Thenable[B], onRejected: scala.Any => js.Thenable[B]) =
    self.`then`[B](onFulfilled(_): B | js.Thenable[B], js.defined { (any: scala.Any) =>
      onRejected(any): B | js.Thenable[B]
    })

  def jsCatch[B](f: scala.Any => B) =
    self.`then`[B](js.undefined.asInstanceOf[js.Function1[A, B | js.Thenable[B]]], js.defined { (any: scala.Any) =>
      f(any): B | js.Thenable[B]
    })

  def jsCatchF[B](f: scala.Any => js.Thenable[B]) =
    self.`then`[B](js.undefined.asInstanceOf[js.Function1[A, B | js.Thenable[B]]], js.defined { (any: scala.Any) =>
      f(any): B | js.Thenable[B]
    })

  /** Return this value if successful, else that's value or error. */
  def orElse[A](that: => js.Thenable[A]) = self.`then`[A](
    a => a.asInstanceOf[A | js.Thenable[A]],
    js.defined { (_: scala.Any) =>
      that.asInstanceOf[A | js.Thenable[A]]
    }
  )

  /** Tap into the result. */
  def tap(f: A => js.Thenable[_]) =
    self.`then`[A]((a: A) => f(a).`then`[A](_ => a, js.undefined).asInstanceOf[A | js.Thenable[A]], js.undefined)

  /** Tap into the error. */
  /*
  def tapError(f: scala.Any => js.Thenable[_]) =
    self.`then`[A](
      (a: A) => a.asInstanceOf[A|Thenable[A]],
      js.defined{(err: scala.Any) => f(err).`then`[A](_ => js.Promise.reject(err), js.undefined).asInstanceOf[A|js.Thenable[A]]}
    )
 */
}

/** Ergonomic syntax for Promise.resolve and Promise.reject. Or, use like
 * `PromiseValue(true).resolve`. `a` is a strict value.
 */
final class JSPromiseObjectOps[A](private val a: A) extends AnyVal {
  def resolve = js.Promise.resolve[A](a)
  def reject  = js.Promise.reject(a)
}

trait JSPromiseSyntax {
  @inline implicit def anyToJSPromise[A](a: A)              = new JSPromiseObjectOps[A](a)
  @inline implicit def toJSPromiseOps[A](p: js.Thenable[A]) = new JSPromiseOps[A](p)
}
