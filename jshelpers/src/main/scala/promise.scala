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

  type RVAL[A] = A | js.Thenable[A]
  type RESOLVE[A, B] = js.Function1[A, RVAL[B]]
  type REJECTED[A] = js.Function1[scala.Any, RVAL[A]]

  /** Cast since these js effect types get a bit screwed up in scala-js. */
  @inline def asPromise = self.asInstanceOf[js.Promise[A]]
  
  /** map */
  @inline def jsThen[B](f: A => B): js.Thenable[B] = {
    val onf = js.Any.fromFunction1(f).asInstanceOf[RESOLVE[A, B]]
    self.`then`[B](onf, js.undefined)
  }

  /** map */
  @inline def map[B](f: A => B): js.Thenable[B] = jsThen[B](f)

  /** flatMap */
  @inline def jsThenF[B](f: A => js.Thenable[B]) = {
    val onf = js.Any.fromFunction1(f).asInstanceOf[RESOLVE[A, B]]
    self.`then`[B](onf, js.undefined)
  }

  /** flatMap */
  @inline def flatMap[B](f: A => js.Thenable[B]): js.Thenable[B] = {
    val onf = js.Any.fromFunction1(f).asInstanceOf[RESOLVE[A, B]]
    self.`then`[B](onf, js.undefined)
  }

  /** co-map */
  @inline def jsThen[B >: A](onFulfilled: A => B, onRejected: scala.Any => B) = {
    val onf = js.Any.fromFunction1(onFulfilled).asInstanceOf[RESOLVE[A, B]]
    val onr = js.Any.fromFunction1(onRejected).asInstanceOf[REJECTED[B]]
    self.`then`[B](onf, onr)
  }

  /** co-flatMap */
  @inline def jsThenF[B](onFulfilled: A => js.Thenable[B], onRejected: scala.Any => js.Thenable[B]) = {
    val onf = js.Any.fromFunction1(onFulfilled).asInstanceOf[RESOLVE[A, B]]
    val onr = js.Any.fromFunction1(onRejected).asInstanceOf[REJECTED[B]]
    self.`then`[B](onf, js.defined(onr))
  }

  /** Transform the rejected value to another value which is also rejected. */
  @inline def jsCatch(f: scala.Any => scala.Any): js.Thenable[A] = {
    val onr = js.Any.fromFunction1((e: Any) => js.Promise.reject(f(e)))
    self.`then`[A]((), onr)
  }

  /** Transform the rejected value to a Thenable, which may be rejected or resolved. */
  @inline def jsCatchF[B](f: scala.Any => js.Thenable[B]): js.Thenable[B] = {
    val onr = js.Any.fromFunction1(f).asInstanceOf[REJECTED[B]]
    self.`then`[B](().asInstanceOf[RESOLVE[A, B]], onr)
  }

  /** Return this value if successful, else that's value. */
  @inline def orElse[B >: A](that: => js.Thenable[B]) = {
    val onf = js.Any.fromFunction1((a: A) => a.asInstanceOf[B]).asInstanceOf[RESOLVE[A, B]]
    val onr = js.Any.fromFunction1((e: Any) => that).asInstanceOf[REJECTED[B]]
    self.`then`[B](onf, onr)
  }

  /** Tap into the result. */
  @inline def tapValue(f: A => Unit) = {
    val onf = js.Any.fromFunction1 { (a: A) => f(a); a }.asInstanceOf[RESOLVE[A, A]]
    self.`then`[A](onf, js.undefined)
  }

  /** Tap into the result. */
  @inline def tapValueF(f: A => js.Thenable[A]) = {
    val onf =
      js.Any.fromFunction1((a: A) => f(a).`then`((_ => a): RESOLVE[A, A], js.undefined)).asInstanceOf[RESOLVE[A, A]]
    self.`then`[A](onf, js.undefined)
  }

  /** Tap into the error. */
  @inline def tapError(f: scala.Any => Unit) = {
    val onr = js.Any.fromFunction1 { (e: Any) => f(e); js.Promise.reject(e) }.asInstanceOf[REJECTED[A]]
    self.`then`[A]((), onr)
  }

  @inline def tapErrorF(f: scala.Any => js.Thenable[Unit]) = {
    val onr = js.Any
      .fromFunction1((e: Any) => f(e).`then`[Unit]((), js.defined((_: Any) => js.Promise.reject(e))))
      .asInstanceOf[REJECTED[A]]
    self.`then`[A]((), onr)
  }

  /** Map the resolved value to unit. Otherwise the error falls through. */
  @inline def unit = {
    val onf = js.Any.fromFunction1((_: A) => ()).asInstanceOf[RESOLVE[A, Unit]]
    self.`then`[Unit](onf, js.undefined)
  }

  /** Filter on the value. Return failed Thenable with
   * NoSuchElementException if p => false.
   */
  @inline def filter(p: A => Boolean): js.Thenable[A] = {
    val onf = js.Any.fromFunction1 { (a: A) =>
      val result = p(a)
      if (result) JSPromiseCreators[A](a)
      else JSPromiseCreators.fail(new NoSuchElementException())
    }.asInstanceOf[RESOLVE[A, A]]
    self.`then`[A](onf, js.undefined)
  }

  /** for-comprehension support. */
  @inline def withFilter(p: A => Boolean) = filter(p)

  /** Recover from the error using a partial function. If the partial function is
   * defined at the value, then the function is applied and its resolved. Otherwise the original
   * value or error remains.
   */
  @inline def recover[U >: A](pf: PartialFunction[scala.Any, U]): js.Thenable[U] = {
    val onf = ().asInstanceOf[RESOLVE[A, U]]
    val onr = js.Any
      .fromFunction1((any: Any) => if (pf.isDefinedAt(any)) pf.apply(any) else js.Promise.reject(any))
      .asInstanceOf[REJECTED[U]]
    self.`then`[U](onf, onr)
  }

  @inline def recoverWith[U >: A](pf: PartialFunction[scala.Any, js.Thenable[U]]): js.Thenable[U] = {
    val onf = ().asInstanceOf[RESOLVE[A, U]]
    val onr = js.Any
      .fromFunction1((any: Any) => if (pf.isDefinedAt(any)) pf.apply(any) else js.Promise.reject(any))
      .asInstanceOf[REJECTED[U]]
    self.`then`[U](onf, onr)
  }

  /** Map over the resolved value or the error. */
  @inline def transform[U](s: A => U, f: scala.Any => scala.Any): js.Thenable[U] = {
    val onf = js.Any.fromFunction1(s).asInstanceOf[RESOLVE[A, U]]
    val onr = js.Any.fromFunction1((e: Any) => js.Promise.reject(f(e))).asInstanceOf[REJECTED[U]]
    self.`then`[U](onf, onr)
  }

  @inline def flatten[S](implicit ev: A <:< js.Thenable[S]): js.Thenable[S] = {
    val onf = js.Any.fromFunction1(ev).asInstanceOf[RESOLVE[A, S]]
    self.`then`(onf, js.undefined)
  }
}

/** Ergonomic syntax for Promise.resolve and Promise.reject. Or, use like
 * `PromiseValue(true).resolve`. `a` is a strict value.
 */
final class JSPromiseObjectOps[A](private val a: A) extends AnyVal {

  /** Return a js.Thenable. */
  def resolve = JSPromiseCreators.effectTotal[A](a)

  /** Return a reject promise with value `a`. */
  def reject = JSPromiseCreators.fail(a)

  /** Return a `js.Thenable[B]` with rejected value `a`. */
  def fail[B] = js.Promise.reject(a).asInstanceOf[js.Thenable[B]]
}

final class JSPromise2[A, B](private val tuple: (js.Thenable[A], js.Thenable[B])) extends AnyVal {
  implicit def toJSPromiseOps[A](p: js.Thenable[A]) = new JSPromiseOps[A](p)
  def parMapX[T](thunk: (A, B) => T) =
    (for {
      valueA <- tuple._1
      valueB <- tuple._2
    } yield thunk(valueA, valueB)).asInstanceOf[js.Promise[T]]
    
  def parFlatMapX[T](thunk: (A, B) => js.Thenable[T]) =
    (for {
      valueA <- tuple._1
      valueB <- tuple._2
      d <- thunk(valueA, valueB)
    } yield d).asInstanceOf[js.Promise[T]]    
}

final class JSPromise3[A, B, C](private val tuple: (js.Thenable[A], js.Thenable[B], js.Thenable[C])) extends AnyVal {
  implicit def toJSPromiseOps[A](p: js.Thenable[A]) = new JSPromiseOps[A](p)
  def parMapX[T](thunk: (A, B, C) => T) =
    (for {
      valueA <- tuple._1
      valueB <- tuple._2
      valueC <- tuple._3
    } yield thunk(valueA, valueB, valueC)).asInstanceOf[js.Promise[T]]
    
  def parFlatMapX[T](thunk: (A, B, C) => js.Thenable[T]) =
    (for {
      valueA <- tuple._1
      valueB <- tuple._2
      valueC <- tuple._3
      d <- thunk(valueA, valueB, valueC)
    } yield d).asInstanceOf[js.Promise[T]]
}

final class JSPromise4[A, B, C, D](private val tuple: (js.Thenable[A], js.Thenable[B], js.Thenable[C], js.Thenable[D]))
    extends AnyVal {
  implicit def toJSPromiseOps[A](p: js.Thenable[A]) = new JSPromiseOps[A](p)
  def parMapX[T](thunk: (A, B, C, D) => T) =
    (for {
      valueA <- tuple._1
      valueB <- tuple._2
      valueC <- tuple._3
      valueD <- tuple._4
    } yield thunk(valueA, valueB, valueC, valueD)).asInstanceOf[js.Promise[T]]

  def parFlatMapX[T](thunk: (A, B, C, D) => js.Thenable[T]) =
    (for {
      valueA <- tuple._1
      valueB <- tuple._2
      valueC <- tuple._3
      valueD <- tuple._4
      d <- thunk(valueA,valueB,valueC,valueD)
    } yield d).asInstanceOf[js.Promise[T]]
}

trait JSPromiseLowerOrderImplicits {
  @inline implicit def jsPromise2[A, B](a: (js.Thenable[A], js.Thenable[B])) = new JSPromise2[A, B](a)
  @inline implicit def jsPromise3[A, B, C](a: (js.Thenable[A], js.Thenable[B], js.Thenable[C])) =
    new JSPromise3[A, B, C](a)
  @inline implicit def jsPromise4[A, B, C, D](a: (js.Thenable[A], js.Thenable[B], js.Thenable[C], js.Thenable[D])) =
    new JSPromise4[A, B, C, D](a)
}

trait JSPromiseSyntax extends JSPromiseLowerOrderImplicits {
  @inline implicit def anyToJSPromise[A](a: A) = new JSPromiseObjectOps[A](a)
  @inline implicit def toJSPromiseOps[A](p: js.Thenable[A]) = new JSPromiseOps[A](p)
}

/** Helpers for creating a `js.Promise`. These are mostly strict except call-by-name.
 * If you want anything more complicated you should use a scala effect such as
 * zio, monix or cats-effect.
 */
private[jshelpers] object JSPromiseCreators {
  def fail(error: => scala.Any): js.Promise[Nothing] = js.Promise.reject(error)

  def fromOption[A](v: => Option[A]): js.Promise[A] =
    v.fold[js.Promise[A]](js.Promise.reject(()))(a => js.Promise.resolve[A](a))

  def none: js.Promise[Nothing] = js.Promise.reject(())

  def some[A](a: => A): js.Promise[Option[A]] = js.Promise.resolve[Option[A]](Option(a))

  def unit: js.Promise[Unit] = js.Promise.resolve[Unit]((()))

  def nullValue: js.Promise[Null] = js.Promise.resolve[Null](null)

  def apply[A](a: => A): js.Promise[A] = js.Promise.resolve[A](a)

  def from[A](a: => A): js.Promise[A] = js.Promise.resolve[A](a)

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

trait JSPromiseInstances {
  val JSPromise = JSPromiseCreators
}
