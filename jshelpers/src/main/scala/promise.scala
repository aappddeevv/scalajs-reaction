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
package syntax

import scala.scalajs.js
import scala.annotation.targetName

// To make a promise:
// (resolve: js.Function1[Unit|js.Thenable[Unit],_], reject: js.Function1[scala.Any,_]) =>

/** More ergonomic typed methods for js.Promise processing. It is easy to convert to js
 * Promise to a future (jspromiseInstance.toFuture) but if you have alot of js.Promise
 * API then you are converting alot of Futures :-).
 */
object promise:
  type RVAL[A] = A | js.Thenable[A]
  type RESOLVE[A, B] = js.Function1[A, RVAL[B]]
  type REJECTED[A] = js.Function1[scala.Any, RVAL[A]]
    
  extension [A](self: js.Thenable[A])

    /** Cast since these js effect types get a bit screwed up in scala-js. */
    def asPromise = self.asInstanceOf[js.Promise[A]]

    /** map */
    def jsThen[B](f: A => B): js.Promise[B] = {
      val onf = js.Any.fromFunction1(f).asInstanceOf[RESOLVE[A, B]]
      self.`then`[B](onf, js.undefined).asInstanceOf[js.Promise[B]]
    }

    /** map */
    def map[B](f: A => B): js.Promise[B] = jsThen[B](f)

    /** Cast to S otherwise throw a ClassCastException. */
    def mapTo[S](implicit tag: scala.reflect.ClassTag[S]): js.Promise[S] =
      self.`then`[S]((a: A) => tag.runtimeClass.cast(a).asInstanceOf[RVAL[S]], js.undefined).asInstanceOf[js.Promise[S]]

    /** flatMap */
    def jsThenF[B](f: A => js.Thenable[B]) = {
      val onf = js.Any.fromFunction1(f).asInstanceOf[RESOLVE[A, B]]
      self.`then`[B](onf, js.undefined)
    }

    /** flatMap */
    def flatMap[B](f: A => js.Thenable[B]): js.Promise[B] = {
      val onf = js.Any.fromFunction1(f).asInstanceOf[RESOLVE[A, B]]
      self.`then`[B](onf, js.undefined).asInstanceOf[js.Promise[B]]
    }

    /** Map on error. */
    def mapError(f: scala.Any => scala.Any) = {
      val onf: REJECTED[A] = (err: scala.Any) => js.Promise.reject(f(err)).asInstanceOf[RVAL[A]]
      self.`then`[A]((), onf)
    }

    /** Map on error. Use the value in the effect and redirect to the error in self. */
    def flatMapError(f: scala.Any => js.Thenable[scala.Any]) = {
      val onf = (err: scala.Any) => f(err).asInstanceOf[RVAL[A]]
      //f(err).`then`(nerrIsPromise => nerrIsPromise.asInstanceOf[RVAL[A]], js.undefined).asInstanceOf[RVAL[A]]
      //f(err).`then`(nerrIsPromise => nerrIsPromise.asInstanceOf[RVAL[A]], js.undefined).asInstanceOf[RVAL[A]]
      self.`then`[A]((), onf)
    }

    /** Sequence computation, then replace final values with `b`. */
    def as[B](b: B): js.Thenable[B] = map(_ => b)

    /** co-map */
    def jsThen[B >: A](onFulfilled: A => B, onRejected: scala.Any => B) = {
      val onf = js.Any.fromFunction1(onFulfilled).asInstanceOf[RESOLVE[A, B]]
      val onr = js.Any.fromFunction1(onRejected).asInstanceOf[REJECTED[B]]
      self.`then`[B](onf, onr)
    }

    /** co-flatMap */
    def jsThenF[B](onFulfilled: A => js.Thenable[B], onRejected: scala.Any => js.Thenable[B]) = {
      val onf = js.Any.fromFunction1(onFulfilled).asInstanceOf[RESOLVE[A, B]]
      val onr = js.Any.fromFunction1(onRejected).asInstanceOf[REJECTED[B]]
      self.`then`[B](onf, js.defined(onr))
    }

    /** Transform the rejected value to another value which is also rejected. */
    def jsCatch(f: scala.Any => scala.Any): js.Promise[A] = {
      val onr = js.Any.fromFunction1((e: Any) => js.Promise.reject(f(e)))
      self.`then`[A]((), onr).asInstanceOf[js.Promise[A]]
    }

    /** Transform the rejected value to a Thenable, which may be rejected or resolved. */
    def jsCatchF[B](f: scala.Any => js.Thenable[B]): js.Promise[B] = {
      val onr = js.Any.fromFunction1(f).asInstanceOf[REJECTED[B]]
      self.`then`[B](().asInstanceOf[RESOLVE[A, B]], onr).asInstanceOf[js.Promise[B]]
    }

    /** Unlike `Future.foreach`, return an effect after running `f`. It's like `tapValue`. */
    def foreach[U](f: A => U) =
      self.`then`[U]((a: A) => f(a).asInstanceOf[RVAL[U]], js.undefined)

    /** If this fails, push the error into the value position of the promise. */
    def failed: js.Promise[scala.Any] = {
      val onf: REJECTED[scala.Any] = (err: scala.Any) => js.Promise.resolve[scala.Any](err)
      self.`then`[scala.Any]((), js.defined(onf)).asInstanceOf[js.Promise[scala.Any]]
    }

    def flatten[S](implicit ev: A <:< js.Thenable[S]): js.Promise[S] = {
      val onf = js.Any.fromFunction1(ev).asInstanceOf[RESOLVE[A, S]]
      self.`then`[S](onf, js.undefined).asInstanceOf[js.Promise[S]]
    }

    /** Not sure this is semantically right... */
    def collect[S](pf: PartialFunction[A, S]): js.Promise[S] = {
      val onf: RESOLVE[A, S] = (a: A) => {
        if pf.isDefinedAt(a) then js.Promise.resolve[S](pf.apply(a))
        else js.Promise.reject(new NoSuchElementException())
      }
      self.`then`[S](onf, js.undefined).asInstanceOf[js.Promise[S]]
    }

    /** Return this if it succeeds otherwise return that. If that fails,
     * return failure from this.
     */
    def fallbackTo[U >: A](that: js.Promise[U]): js.Promise[U] =
      if self == that then self.asInstanceOf[js.Promise[U]]
      else {
        val onf: RESOLVE[A, U] = (a: A) => a
        val onr: REJECTED[U] = (erra: scala.Any) => {
          val onur: REJECTED[U] = (erru: scala.Any) => erra.asInstanceOf[RVAL[U]]
          that.`then`((u: U) => u.asInstanceOf[RVAL[U]], js.defined(onur))
        }
        self.`then`[U](onf, onr).asInstanceOf[js.Promise[U]]
      }

    /** Return this value if successful, else that's value. */
    def orElse[B >: A](that: => js.Thenable[B]) = {
      val onf = js.Any.fromFunction1((a: A) => a.asInstanceOf[B]).asInstanceOf[RESOLVE[A, B]]
      val onr = js.Any.fromFunction1((e: Any) => that).asInstanceOf[REJECTED[B]]
      self.`then`[B](onf, onr)
    }

    /** Tap into the result. */
    def tapValue(f: A => Any): js.Promise[A] = {
      val onf = js.Any.fromFunction1 { (a: A) => f(a); a }.asInstanceOf[RESOLVE[A, A]]
      self.`then`[A](onf, js.undefined).asInstanceOf[js.Promise[A]]
    }

    /** Tap into the result. */
    def tapValueF[U >: A](f: U => js.Thenable[Any]): js.Promise[U] = {
      val onf =
        js.Any.fromFunction1((a: U) => f(a).`then`((_ => a): RESOLVE[Any, U], js.undefined)).asInstanceOf[RESOLVE[U, U]]
      self.`then`[U](onf, js.undefined).asInstanceOf[js.Promise[U]]
    }

    /** Tap into the error. */
    def tapError(f: scala.Any => Any): js.Promise[A] = {
      val onr = js.Any.fromFunction1 { (e: Any) => f(e); js.Promise.reject(e) }.asInstanceOf[REJECTED[A]]
      self.`then`[A]((), onr).asInstanceOf[js.Promise[A]]
    }

    def tapErrorF(f: scala.Any => js.Thenable[Any]): js.Promise[A] = {
      val onr = js.Any
        .fromFunction1((e: Any) =>
          f(e).`then`[Any](()/*.asInstanceOf[RESOLVE[Any, Unit]]*/, js.defined((_: Any) => js.Promise.reject(e)).asInstanceOf[REJECTED[Any]]))
        .asInstanceOf[REJECTED[A]]
      self.`then`[A]((), onr).asInstanceOf[js.Promise[A]]
    }

    /** Map the resolved value to unit. Otherwise the error falls through. */
    def unit = {
      val onf = js.Any.fromFunction1((_: A) => ()).asInstanceOf[RESOLVE[A, Unit]]
      self.`then`[Unit](onf, js.undefined)
    }

    /** Filter on the value. Return failed Thenable with
     * NoSuchElementException if p => false.
     */
    def filter(p: A => Boolean): js.Promise[A] = {
      val onf = js.Any.fromFunction1 { (a: A) =>
        val result = p(a)
        if result then JSPromiseCreators[A](a)
        else JSPromiseCreators.fail(new NoSuchElementException())
      }.asInstanceOf[RESOLVE[A, A]]
      self.`then`[A](onf, js.undefined).asInstanceOf[js.Promise[A]]
    }

    /** for-comprehension support. */
    def withFilter(p: A => Boolean): js.Promise[A] = filter(p)

    /** Recover from the error using a partial function. If the partial function is
     * defined at the value, then the function is applied. Otherwise the original
     * value or error remains. recover = catch.
     */
    def recover[U >: A](pf: PartialFunction[scala.Any, U]): js.Promise[U] = {
      val onf = ().asInstanceOf[RESOLVE[A, U]]
      val onr = js.Any
        .fromFunction1((any: Any) => if pf.isDefinedAt(any) then pf.apply(any) else js.Promise.reject(any))
        .asInstanceOf[REJECTED[U]]
      self.`then`[U](onf, onr).asInstanceOf[js.Promise[U]]
    }

    /** Like `recover` but the partial function returns a `js.Thenable`. recover = catch. */
    def recoverWith[U >: A](pf: PartialFunction[scala.Any, js.Thenable[U]]): js.Promise[U] = {
      val onf = ().asInstanceOf[RESOLVE[A, U]]
      val onr = js.Any
        .fromFunction1((any: Any) => 
            if pf.isDefinedAt(any) then 
              pf.apply(any) 
            else 
              js.Promise.reject(any))
        .asInstanceOf[REJECTED[U]]
      self.`then`[U](onf, onr).asInstanceOf[js.Promise[U]]
    }

    /** Map over the resolved value or the error. */
    def transform[U](s: A => U, f: scala.Any => scala.Any): js.Promise[U] = {
      val onf = js.Any.fromFunction1(s).asInstanceOf[RESOLVE[A, U]]
      val onr = js.Any.fromFunction1((e: Any) => js.Promise.reject(f(e))).asInstanceOf[REJECTED[U]]
      self.`then`[U](onf, onr).asInstanceOf[js.Promise[U]]
    }

    /** Not sure this is semantically right... */
    def transform[S](f: scala.util.Try[A] => scala.util.Try[S]): js.Promise[S] = {
      val onf: RESOLVE[A, S] = (a: A) =>
        f(scala.util.Success(a)) match {
          case scala.util.Success(s) => js.Promise.resolve(s.asInstanceOf[RVAL[S]])
          case scala.util.Failure(t) => js.Promise.reject(t.asInstanceOf[RVAL[S]])
        }
      val onr: REJECTED[S] = (err: scala.Any) => {
        val trya = err match {
          case th: Throwable => scala.util.Failure(th)
          case _             => scala.util.Failure(js.JavaScriptException(err))
        }
        f(trya) match {
          case scala.util.Success(v)  => js.Promise.resolve(v.asInstanceOf[RVAL[S]])
          case scala.util.Failure(th) => js.Promise.reject(th.asInstanceOf[RVAL[S]])
        }
      }
      self.`then`[S](onf, onr).asInstanceOf[js.Promise[S]]
    }

    /** While the result is still wrapped in a promise effect, the error has been
     * pushed into `Either`. It would be nice to enhance the types for Left.
     */
    def either = {
      val onf: RESOLVE[A, Either[scala.Any, A]] = (a: A) => Right(a)
      val onr: REJECTED[Either[scala.Any, A]] = (err: scala.Any) => Left(err)
      self.`then`[Either[scala.Any, A]](onf, onr)
    }

    /** Push the value into `Option` and the error into None. The result
     * is still wrapped in an effect.
     */
    def opt = {
      val onf: RESOLVE[A, Option[A]] = (a: A) => Option(a)
      val onr: REJECTED[Option[A]] = (err: scala.Any) => Option.empty[A]
      self.`then`[Option[A]](onf, onr)
    }


  /** Ergonomic syntax for Promise.resolve and Promise.reject. Or, use like
   * `PromiseValue(true).resolve`. `a` is a strict value.
   */
  extension [A](a: A)
    /** Return a js.Thenable. */
    def resolve = JSPromiseCreators.effectTotal[A](a)

  extension (a: scala.Any)
    /** Return a reject promise with value `a`, type `Nothing`. */
    def reject = JSPromiseCreators.reject(a)

    /** Return a `js.Thenable[B]` with rejected value `a`. */
    def fail[A] = JSPromiseCreators.fail[A](a)

  extension [A, B](tuple: (js.Thenable[A], js.Thenable[B]))
    @targetName("parMapX2")
    def parMapX[T](thunk: (A, B) => T) =
      (for
        valueA <- tuple._1
        valueB <- tuple._2
      yield thunk(valueA, valueB)).asInstanceOf[js.Promise[T]]

    @targetName("parFlatMapX2")
    def parFlatMapX[T](thunk: (A, B) => js.Thenable[T]) =
      (for
        valueA <- tuple._1
        valueB <- tuple._2
        d <- thunk(valueA, valueB)
      yield d).asInstanceOf[js.Promise[T]]


  extension [A, B, C](tuple: (js.Thenable[A], js.Thenable[B], js.Thenable[C]))
    @targetName("parMapX3")
    def parMapX[T](thunk: (A, B, C) => T) =
      (for
        valueA <- tuple._1
        valueB <- tuple._2
        valueC <- tuple._3
      yield thunk(valueA, valueB, valueC)).asInstanceOf[js.Promise[T]]

    @targetName("parFlatMapX3")
    def parFlatMapX[T](thunk: (A, B, C) => js.Thenable[T]) =
      (for
        valueA <- tuple._1
        valueB <- tuple._2
        valueC <- tuple._3
        d <- thunk(valueA, valueB, valueC)
      yield d).asInstanceOf[js.Promise[T]]

  extension [A, B, C, D](tuple: (js.Thenable[A], js.Thenable[B], js.Thenable[C], js.Thenable[D]))
    @targetName("parMapX4")
    def parMapX[T](thunk: (A, B, C, D) => T) =
      (for
        valueA <- tuple._1
        valueB <- tuple._2
        valueC <- tuple._3
        valueD <- tuple._4
      yield thunk(valueA, valueB, valueC, valueD)).asInstanceOf[js.Promise[T]]

    @targetName("parFlatMapX4")
    def parFlatMapX[T](thunk: (A, B, C, D) => js.Thenable[T]) =
      (for
        valueA <- tuple._1
        valueB <- tuple._2
        valueC <- tuple._3
        valueD <- tuple._4
        d <- thunk(valueA, valueB, valueC, valueD)
      yield d).asInstanceOf[js.Promise[T]]
      
  extension [A](arr: js.Array[js.Thenable[A]])
    /** `js.Promise.all` only takes js.Promise arrays. This takes an array of `js.Thenable[_]`s. */
    @targetName("allArrayOfThenables")
    def all: js.Promise[js.Array[A]] = js.Promise.all(arr.asInstanceOf[js.Array[js.Promise[A]]])

  extension [A](arr: js.Array[js.Promise[A]])
    /** `js.Promise.all` only takes js.Promise arrays. This takes an array of `js.Thenable[_]`s. */
    @targetName("allArrayOfPromises")
    def all = js.Promise.all(arr.asInstanceOf[js.Array[js.Promise[A]]])

// trait JSPromiseSyntax 
//   extends JSPromiseOps
//   with JSArrayPromiseOps 
//   with JSArrayPromiseOpsThenable 
//   with JSParMappers
//   with JSPromiseFailObjectOps
//   with JSPromiseObjectOps

/** Helpers for creating a `js.Promise`. These are mostly strict except call-by-name.
 * If you want anything more complicated you should use a scala effect such as
 * zio, monix or cats-effect.
 */
private[jshelpers] object JSPromiseCreators {

  /** Reject with `Nothing` type. */
  def reject(error: => scala.Any): js.Promise[Nothing] = js.Promise.reject(error)

  /** Reject a value with `A` type. */
  def fail[A](error: => scala.Any): js.Promise[A] = js.Promise.reject(error).asInstanceOf[js.Promise[A]]

  def fromOption[A](v: => Option[A]): js.Promise[A] =
    v.fold[js.Promise[A]](js.Promise.reject(()))(a => js.Promise.resolve[A](a))

  def none: js.Promise[Nothing] = js.Promise.reject(())

  def some[A](a: => A): js.Promise[Option[A]] = js.Promise.resolve[Option[A]](Option(a))

  def unit: js.Promise[Unit] = js.Promise.resolve[Unit]((()))

  /** Saves you a few keystrokes. This should be js.Promise... */
  def undefined[A]: js.Promise[js.UndefOr[A]] = from[js.UndefOr[A]](js.undefined)

  def defined[A](a: A): js.Promise[js.UndefOr[A]] = js.Promise.resolve(js.defined(a).asInstanceOf[A | js.Thenable[A]])

  def nullValue: js.Promise[Null] = js.Promise.resolve[Null](null)

  def nullAs[A]: js.Promise[A | Null] = js.Promise.resolve[A | Null](null)

  def apply[A](a: => A): js.Promise[A] = js.Promise.resolve[A](a)

  def from[A](a: => A): js.Promise[A] = js.Promise.resolve[A](a)

  /** Use this if you *know* this does not throw any exceptions. */
  def effectTotal[A](effect: => A): js.Promise[A] = js.Promise.resolve[A](effect)

  /** If the effect can fail, it will fail with a Throwable. Only catches non-fatal errors.*/
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
