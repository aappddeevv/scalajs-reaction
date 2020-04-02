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

package jshelpers 

import scala.scalajs.js

import js.|

// To make a promise:
// (resolve: js.Function1[Unit|js.Thenable[Unit],_], reject: js.Function1[scala.Any,_]) =>
/** More ergonomic typed methods for js.Promise processing. Should just convert to Future
  * as the overhead is *not* that much and its much easier to use.
  */
final class JSPromiseOps[A](private val self: js.Thenable[A]) extends AnyVal {

  type RESOLVE[A, B] = js.Function1[A, B | js.Thenable[B]]
  type REJECTED[A] = js.Function1[scala.Any, A | js.Thenable[A]]

  /** map */
  def jsThen[B](f: A => B) = {
    val onf = js.Any.fromFunction1(f).asInstanceOf[RESOLVE[A, B]]
    self.`then`[B](onf, js.undefined)
  }

  /** flatMap */
  def jsThenF[B](f: A => js.Thenable[B]) = {
    val onf = js.Any.fromFunction1(f).asInstanceOf[RESOLVE[A, B]]
    self.`then`[B](onf, js.undefined)
  }

  /** co-map */
  def jsThen[B >: A](onFulfilled: A => B, onRejected: scala.Any => B) = {
    val onf = js.Any.fromFunction1(onFulfilled).asInstanceOf[RESOLVE[A, B]]
    val onr = js.Any.fromFunction1(onRejected).asInstanceOf[REJECTED[B]]
    self.`then`[B](onf, onr)
  }

  /** co-flatMap */
  def jsThenF[B](onFulfilled: A => js.Thenable[B], onRejected: scala.Any => js.Thenable[B]) =
    self.`then`[B](js.Any.fromFunction1(onFulfilled)(_): B | js.Thenable[B], js.defined { (any: scala.Any) =>
      onRejected(any): B | js.Thenable[B]
    })

  def jsCatch[B](f: scala.Any => B) =
    self.`then`[B](().asInstanceOf[RESOLVE[A, B]], js.defined { (any: scala.Any) => f(any): B | js.Thenable[B] })

  def jsCatchF[B](f: scala.Any => js.Thenable[B]) =
    self.`then`[B](().asInstanceOf[RESOLVE[A, B]], js.defined { (any: scala.Any) => f(any): B | js.Thenable[B] })

  /** Return this value if successful, else that's value or error. */
  def orElse[B >: A](that: => js.Thenable[B]) = {
    val onf: RESOLVE[A, B] = a => a.asInstanceOf[B | js.Thenable[B]]
    self.`then`[B](
      onf,
      js.defined { (_: scala.Any) => that.asInstanceOf[B | js.Thenable[B]] }
    )
  }

  /** Tap into the result. NOT TESTED! */
  def tap(f: A => Unit) =
    self.`then`[A]((a: A) => { f(a); a.asInstanceOf[A | js.Thenable[A]] }, js.undefined)

  /** Tap into the result. NOT TESTED! */
  def tapF(f: A => js.Thenable[_]) =
    self.`then`[A]((a: A) => f(a).`then`[A](_ => a, js.undefined).asInstanceOf[A | js.Thenable[A]], js.undefined)

  /** Tap into the error. NOT TESTED! */
  def tapError(f: js.UndefOr[scala.Any] => Unit) =
    self.`then`[A](
      (a: A) => a.asInstanceOf[A | js.Thenable[A]],
      js.defined { (err: scala.Any) => { f(err); js.Promise.reject(err).asInstanceOf[A | js.Thenable[A]] } }
    )
}

/** Ergonomic syntax for Promise.resolve and Promise.reject. Or, use like
  * `PromiseValue(true).resolve`. `a` is a strict value.
  */
final class JSPromiseObjectOps[A](private val a: A) extends AnyVal {

  /** Return a js.Thenable. */
  def resolve = js.Promise.resolve[A](a)

  /** Return a js.Thenable[Nothing]. */
  def reject = js.Promise.reject(a)

  /** Return a js.Thenable[A] */
  def fail[B] = js.Promise.reject(a).asInstanceOf[js.Thenable[B]]
}

trait JSPromiseSyntax {
  @inline implicit def anyToJSPromise[A](a: A) = new JSPromiseObjectOps[A](a)
  @inline implicit def toJSPromiseOps[A](p: js.Thenable[A]) = new JSPromiseOps[A](p)
}
