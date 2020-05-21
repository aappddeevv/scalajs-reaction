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

import scala.reflect.ClassTag
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
  def asPromise = self.asInstanceOf[js.Promise[A]]
  
  /** map */
  def jsThen[B](f: A => B): js.Thenable[B] = {
    val onf = js.Any.fromFunction1(f).asInstanceOf[RESOLVE[A, B]]
    self.`then`[B](onf, js.undefined)
  }

  /** map */
  def map[B](f: A => B): js.Thenable[B] = jsThen[B](f)

  /** Cast to S otherwise throw a ClassCastException. */
  def mapTo[S](implicit tag: ClassTag[S]): js.Thenable[S] = {
    self.`then`[S]((a: A) => tag.runtimeClass.cast(a).asInstanceOf[RVAL[S]], js.undefined)
  }
  
  /** flatMap */
  def jsThenF[B](f: A => js.Thenable[B]) = {
    val onf = js.Any.fromFunction1(f).asInstanceOf[RESOLVE[A, B]]
    self.`then`[B](onf, js.undefined)
  }

  /** flatMap */
  def flatMap[B](f: A => js.Thenable[B]): js.Thenable[B] = {
    val onf = js.Any.fromFunction1(f).asInstanceOf[RESOLVE[A, B]]
    self.`then`[B](onf, js.undefined)
  }
  
  /** Map on error. */
  def mapError(f: scala.Any => scala.Any) = {
     val onf: REJECTED[A] = (err: scala.Any) => f(err).asInstanceOf[RVAL[A]]
     self.`then`[A]((), onf)
  }
  
  /** Map on error. Use the value in the effect and redirect to the error in self. 
   * Any error in `f` is ignored.
   */
  def flatMapError(f: scala.Any => js.Thenable[scala.Any]) = {
    val onf: REJECTED[A] = (err: scala.Any) => 
        f(err).`then`(nerr => js.Promise.reject(nerr).asInstanceOf[RVAL[A]], js.undefined).asInstanceOf[RVAL[A]]
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
  def jsCatch(f: scala.Any => scala.Any): js.Thenable[A] = {
    val onr = js.Any.fromFunction1((e: Any) => js.Promise.reject(f(e)))
    self.`then`[A]((), onr)
  }

  /** Transform the rejected value to a Thenable, which may be rejected or resolved. */
  def jsCatchF[B](f: scala.Any => js.Thenable[B]): js.Thenable[B] = {
    val onr = js.Any.fromFunction1(f).asInstanceOf[REJECTED[B]]
    self.`then`[B](().asInstanceOf[RESOLVE[A, B]], onr)
  }

  /** Unlike `Future.foreach`, return an effect after running `f`. It's like `tapValue`. */
  def foreach[U](f: A => U) = 
    self.`then`[U]((a:A) => f(a).asInstanceOf[RVAL[U]], js.undefined)
  
  /** If this fails, push the error into the value position of the promise. */
  def failed: js.Thenable[scala.Any] = {
    val onf: REJECTED[scala.Any] = (err: scala.Any) => js.Promise.resolve[scala.Any](err)
    self.`then`[scala.Any]((), js.defined(onf))
  }
  
  def flatten[S](implicit ev: A <:< js.Thenable[S]): js.Thenable[S] = {
    val onf = js.Any.fromFunction1(ev).asInstanceOf[RESOLVE[A, S]]
    self.`then`[S](onf, js.undefined)
  }
  
  /** Not sure this is semantically right... */
  def collect[S](pf: PartialFunction[A,S]): js.Thenable[S] = {
    val onf: RESOLVE[A,S] = (a: A) => {
        if(pf.isDefinedAt(a)) js.Promise.resolve[S](pf.apply(a))
        else js.Promise.reject(new NoSuchElementException())
    }
    self.`then`[S](onf, js.undefined)
  }
  
  /** Return this if it succeeds otherwise return that. If that fails,
    * return failure from this.
    */
  def fallbackTo[U >: A](that: js.Promise[U]): js.Thenable[U] = {
     if(self == that) self
     else {
        val onf: RESOLVE[A,U] = (a: A) => a
        val onr: REJECTED[U] = (erra: scala.Any) => {
            val onur: REJECTED[U] = (erru: scala.Any) => erra.asInstanceOf[RVAL[U   ]]
            that.`then`((u:U) => u.asInstanceOf[RVAL[U]], js.defined(onur))
        }
        self.`then`[U](onf, onr)
     }
  }
  
  /** Return this value if successful, else that's value. */
  def orElse[B >: A](that: => js.Thenable[B]) = {
    val onf = js.Any.fromFunction1((a: A) => a.asInstanceOf[B]).asInstanceOf[RESOLVE[A, B]]
    val onr = js.Any.fromFunction1((e: Any) => that).asInstanceOf[REJECTED[B]]
    self.`then`[B](onf, onr)
  }

  /** Tap into the result. */
  def tapValue(f: A => Unit) = {
    val onf = js.Any.fromFunction1 { (a: A) => f(a); a }.asInstanceOf[RESOLVE[A, A]]
    self.`then`[A](onf, js.undefined)
  }

  /** Tap into the result. */
  def tapValueF[U >: A](f: U => js.Thenable[U]): js.Thenable[U] = {
    val onf =
      js.Any.fromFunction1((a: U) => f(a).`then`((_ => a): RESOLVE[U,U], js.undefined)).asInstanceOf[RESOLVE[U,U]]
    self.`then`[U](onf, js.undefined)
  }

  /** Tap into the error. */
  def tapError(f: scala.Any => Unit) = {
    val onr = js.Any.fromFunction1 { (e: Any) => f(e); js.Promise.reject(e) }.asInstanceOf[REJECTED[A]]
    self.`then`[A]((), onr)
  }

  def tapErrorF(f: scala.Any => js.Thenable[Unit]) = {
    val onr = js.Any
      .fromFunction1((e: Any) => f(e).`then`[Unit]((), js.defined((_: Any) => js.Promise.reject(e))))
      .asInstanceOf[REJECTED[A]]
    self.`then`[A]((), onr)
  }

  /** Map the resolved value to unit. Otherwise the error falls through. */
  def unit = {
    val onf = js.Any.fromFunction1((_: A) => ()).asInstanceOf[RESOLVE[A, Unit]]
    self.`then`[Unit](onf, js.undefined)
  }

  /** Filter on the value. Return failed Thenable with
   * NoSuchElementException if p => false.
   */
  def filter(p: A => Boolean): js.Thenable[A] = {
    val onf = js.Any.fromFunction1 { (a: A) =>
      val result = p(a)
      if (result) JSPromiseCreators[A](a)
      else JSPromiseCreators.fail(new NoSuchElementException())
    }.asInstanceOf[RESOLVE[A, A]]
    self.`then`[A](onf, js.undefined)
  }

  /** for-comprehension support. */
  def withFilter(p: A => Boolean) = filter(p)

  /** Recover from the error using a partial function. If the partial function is
   * defined at the value, then the function is applied. Otherwise the original
   * value or error remains. recover = catch.
   */
  def recover[U >: A](pf: PartialFunction[scala.Any, U]): js.Thenable[U] = {
    val onf = ().asInstanceOf[RESOLVE[A, U]]
    val onr = js.Any
      .fromFunction1((any: Any) => if (pf.isDefinedAt(any)) pf.apply(any) else js.Promise.reject(any))
      .asInstanceOf[REJECTED[U]]
    self.`then`[U](onf, onr)
  }

  /** Like `recover` but the partial function returns a `js.Thenable`. recover = catch. */
  def recoverWith[U >: A](pf: PartialFunction[scala.Any, js.Thenable[U]]): js.Thenable[U] = {
    val onf = ().asInstanceOf[RESOLVE[A, U]]
    val onr = js.Any
      .fromFunction1((any: Any) => if (pf.isDefinedAt(any)) pf.apply(any) else js.Promise.reject(any))
      .asInstanceOf[REJECTED[U]]
    self.`then`[U](onf, onr)
  }

  /** Map over the resolved value or the error. */
  def transform[U](s: A => U, f: scala.Any => scala.Any): js.Thenable[U] = {
    val onf = js.Any.fromFunction1(s).asInstanceOf[RESOLVE[A, U]]
    val onr = js.Any.fromFunction1((e: Any) => js.Promise.reject(f(e))).asInstanceOf[REJECTED[U]]
    self.`then`[U](onf, onr)
  }
  
  /** Not sure this is semantically right... */
  def transform[S](f: scala.util.Try[A] => scala.util.Try[S]): js.Thenable[S] = {
    val onf: RESOLVE[A,S] = (a: A) =>
        f(scala.util.Success(a)) match { 
          case scala.util.Success(s) => js.Promise.resolve(s.asInstanceOf[RVAL[S]])
          case scala.util.Failure(t) => js.Promise.reject(t.asInstanceOf[RVAL[S]])
        }
    val onr: REJECTED[S] = (err: scala.Any) => {
        val trya = err match {
            case th: Throwable => scala.util.Failure(th)
            case _ => scala.util.Failure(js.JavaScriptException(err))
        }
        f(trya) match { 
            case scala.util.Success(v) => js.Promise.resolve(v.asInstanceOf[RVAL[S]])
            case scala.util.Failure(th) => js.Promise.reject(th.asInstanceOf[RVAL[S]])
        }
    }
    self.`then`[S](onf, onr)
  }
  
  /** While the result is still wrapped in a promise effect, the error has been
   * pushed into `Either`.
   */
  def either = {
    val onf: RESOLVE[A,Either[scala.Any,A]] = (a: A) => Right(a)
    val onr: REJECTED[Either[scala.Any,A]] = (err: scala.Any) => Left(err)
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
}

/** Ergonomic syntax for Promise.resolve and Promise.reject. Or, use like
 * `PromiseValue(true).resolve`. `a` is a strict value.
 */
final class JSPromiseObjectOps[A](private val a: A) extends AnyVal {
  /** Return a js.Thenable. */
  def resolve = JSPromiseCreators.effectTotal[A](a)
}

final class JSPromiseFailObjectOps(private val a: scala.Any) extends AnyVal {
  /** Return a reject promise with value `a`, type `Nothing`. */
  def reject = JSPromiseCreators.reject(a)
  
  /** Return a `js.Thenable[B]` with rejected value `a`. */
  def fail[A] = JSPromiseCreators.fail[A](a)
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

/** Extension methods for js.Array[js.Thenable[_]]. */
final class JSArrayPromiseOps[A](private val arr: js.Array[js.Thenable[A]]) extends AnyVal {
  /** `js.Promise.all` only takes js.Promise arrays. This takes an array of `js.Thenable[_]`s. */
  def all = js.Promise.all(arr.asInstanceOf[js.Array[js.Promise[A]]])
}

trait JSPromiseSyntax extends JSPromiseLowerOrderImplicits {
  @inline implicit def jsArrayToPromise[A](arr: js.Array[js.Thenable[A]]) = new JSArrayPromiseOps[A](arr)
  @inline implicit def anyToJSPromise[A](a: A) = new JSPromiseObjectOps[A](a)
  @inline implicit def anyToJSPromiseFail(a: scala.Any) = new JSPromiseFailObjectOps(a)
  @inline implicit def toJSPromiseOps[A](p: js.Thenable[A]) = new JSPromiseOps[A](p)
}

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

  def nullValue: js.Promise[Null] = js.Promise.resolve[Null](null)
  
  def nullAs[A]: js.Promise[A|Null] = js.Promise.resolve[A|Null](null)

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
