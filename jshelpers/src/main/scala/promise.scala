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
 * as the overhead is *not* that much and its much easier to use. You should not import the
 * implicit converters for js.Promise if you want to use map/flatMap on the promise itself:
 * `import scala.scalajs.js.Thenable.Implicits._`
 */
final class JSPromiseOps[A](private val self: js.Thenable[A]) extends AnyVal {

  type RESOLVE[A, B] = js.Function1[A, B | js.Thenable[B]]
  type REJECTED[A] = js.Function1[scala.Any, A | js.Thenable[A]]
  type RVAL[A] = A | js.Thenable[A]

  /** map */
  def jsThen[B](f: A => B): js.Thenable[B] = {
    val onf = js.Any.fromFunction1(f).asInstanceOf[RESOLVE[A, B]]
    self.`then`[B](onf, js.undefined)
  }
  
  /** map */
  def map[B](f: A => B): js.Thenable[B] = jsThen[B](f)

  /** flatMap */
  def jsThenF[B](f: A => js.Thenable[B]) = {
    val onf = js.Any.fromFunction1(f).asInstanceOf[RESOLVE[A, B]]
    self.`then`[B](onf, js.undefined)
  }
  
  /** flatMap */
  def flatMap[B](f: A => js.Thenable[B]): js.Thenable[B] = jsThenF[B](f)

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

  def jsCatch(f: scala.Any => A): js.Thenable[A] =
    self.`then`[A](().asInstanceOf[RESOLVE[A, A]], js.defined((any: scala.Any) => f(any).asInstanceOf[RVAL[A]]))

  def jsCatchF[B](f: scala.Any => js.Thenable[B]): js.Thenable[B] =
    self.`then`[B](().asInstanceOf[RESOLVE[A, B]], js.defined((any: scala.Any) => f(any).asInstanceOf[RVAL[B]]))

  /** Return this value if successful, else that's value or error. */
  def orElse[B >: A](that: => js.Thenable[B]) = {
    val onf: RESOLVE[A, B] = a => a.asInstanceOf[B | js.Thenable[B]]
    self.`then`[B](
      onf,
      js.defined((_: scala.Any) => that.asInstanceOf[B | js.Thenable[B]])
    )
  }

  /** Tap into the result. NOT TESTED! */
  def tap(f: A => Unit) =
    self.`then`[A]((a: A) => { f(a); a.asInstanceOf[A | js.Thenable[A]] }, js.undefined)

  /** Tap into the result. NOT TESTED! */
  def tapF(f: A => js.Thenable[_]) =
    self.`then`[A]((a: A) => f(a).`then`[A](_ => a, js.undefined).asInstanceOf[RVAL[A]], js.undefined)

  /** Tap into the error. NOT TESTED! */
  def tapError(f: js.UndefOr[scala.Any] => Unit) =
    self.`then`[A](
      (a: A) => a.asInstanceOf[A | js.Thenable[A]],
      js.defined { (err: scala.Any) => f(err); js.Promise.reject(err).asInstanceOf[RVAL[A]] }
    )
    
  /** Complete the promise but then return a unit if this was successful. Otherwise
  * the error falls through, essentially a flatMap to Unit.
  */
  def unit = self.`then`[Unit](
    js.Any.fromFunction1[A,Unit|js.Thenable[Unit]]((_:A) => ()),
    js.undefined
    )
    
  /** Filter on the value. Return failed Thenable with
  * NoSuchElementException if p => false.
  */
  @inline def filter(p: A => Boolean): js.Thenable[A] = {
    val onf = js.Any.fromFunction1((a:A) => {
        val result = p(a)
        if(result) JSPromise[A](a)
        else JSPromise.fail(new NoSuchElementException())
    }).asInstanceOf[RESOLVE[A, A]]
    self.`then`[A](onf, js.undefined)
  }

  /** for-comprehension support. */
  @inline def withFilter(p: A => Boolean) = filter(p)
  
//   @inline def recover[U >: A](pf: PartialFunction[scala.Any, U]): js.Thenable[U] =
//     self.`then`[U](().asInstanceOf[RVAL[U]], js.defined((any: Any) => pf.applyOrElse(any, any.asInstanceOf[U]).asInstanceOf[RVAL[U]]))
//     
//   @inline def transform[S](s: A => S, f: scala.Any => scala.Any): js.Thenable[S] = 
//     self.map[S](s).jsCatch(f)
  
  //@inline def flatten[S](implicit ev: A <:< js.Thenable[S]): js.Thenable[S] = 
  //  flatMap(identity)
}


/** Ergonomic syntax for Promise.resolve and Promise.reject. Or, use like
 * `PromiseValue(true).resolve`. `a` is a strict value.
 */
final class JSPromiseObjectOps[A](private val a: A) extends AnyVal {

  /** Return a js.Thenable. */
  def resolve = JSPromise.effectTotal[A](a)

  /** Return a reject promise with value `a`. */
  def reject = JSPromise.fail(a)

  /** Return a `js.Thenable[B]` with rejected value `a`. */
  def fail[B] = js.Promise.reject(a).asInstanceOf[js.Thenable[B]]
}

trait JSPromiseSyntax {
  @inline implicit def anyToJSPromise[A](a: A) = new JSPromiseObjectOps[A](a)
  @inline implicit def toJSPromiseOps[A](p: js.Thenable[A]) = new JSPromiseOps[A](p)
}

/** Helpers for creating a `js.Promise`. These are all strict of course.
 * If you want anything more complicated you should use a scala effect such as
 * zio, monix or cats-effect.
 */
object JSPromise {
  def fail(error: => scala.Any): js.Promise[Nothing] = js.Promise.reject(error)

  def fromOption[A](v: => Option[A]): js.Promise[A] =
    v.fold[js.Promise[A]](js.Promise.reject(()))(a => js.Promise.resolve[A](a))

  def none: js.Promise[Nothing] = js.Promise.reject(())

  def some[A](a: => A): js.Promise[Option[A]] = js.Promise.resolve[Option[A]](Option(a))

  def unit: js.Promise[Unit] = js.Promise.resolve[Unit]((()))

  def apply[A](a: => A): js.Promise[A] = js.Promise.resolve[A](a)

  /** Use this if you *know* this does not throw any exceptions. */
  def effectTotal[A](effect: => A): js.Promise[A] = js.Promise.resolve[A](effect)

  /** If the effect can fail, it will fail with a Throwable. Only catches non fatal errors.*/
  def effect[A](a: => A): js.Promise[A] =
    new js.Promise[A]((res, rej) =>
      try {
        res(a)
      } catch {
        case scala.util.control.NonFatal(t) => rej(t)
      })
}
