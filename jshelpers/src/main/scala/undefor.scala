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
import js.*

/** Add Option-like methods to js.UndefOr. Note that js.Undef.orNull exists in scala.js 1.0 */
trait UndefOrCommon[A] {
  def _a: UndefOr[A]

  /** Tests for overall nullness which is different than `.isEmpty|.nonEmpty`. */
  def isNull = _a == null

  /** Like `UndefOr.isEmpty` but this checks for null and undefined as well. */
  def isTotalEmpty = isNull || !_a.isDefined

  /** This could also be `_.toOption.filter(_ != null)` but below is slightly faster. */
  def toNonNullOption =
    if (_a.isEmpty || _a == null) None
    else _a.toOption

  /** Calls toString. I'm not sure this is needed at all. */
  def toStringJs = _a.asInstanceOf[js.Any].toString()

  /** Equivalent to !!someJSValue */
  def toTruthy: Boolean = js.DynamicImplicits.truthValue(_a.asInstanceOf[js.Dynamic])

  /** Keep the value if its truthy, otherwise return undefined. */
  def filterTruthy: js.UndefOr[A] =
    _a.filter(v => js.DynamicImplicits.truthValue(v.asInstanceOf[js.Dynamic]))

  /** null => undefined, else the value remains. */
  def filterNull: js.UndefOr[A] = _a.filter(_ != null)

  /** Changes what's inside but preserves UndefOr. */
  def as[B]: js.UndefOr[B] = _a.map(_.asInstanceOf[B])

  /** Convert UndefOr[A] => A|Null */
  def toNull: A | Null = if (_a.isDefined) _a.asInstanceOf[A | Null] else null

  /** Same as `.getOrElse` just shorter. */
  def ??[B >: A](default: => B): B = _a.getOrElse(default)

  /** Same as `.getOrElse` just shorter. */
  def !?[B >: A](default: => B): B = _a.getOrElse[B](default)

  /** Uh-oh, thought it was `js.UndefOr[A]` but you need to say its a
   * `js.UndefOr[A|Null]` because the docs were wrong :-).
   */
  def toUndefOrNull: js.UndefOr[A | Null] = _a.asInstanceOf[js.UndefOr[A | Null]]

  /** Factor out the null along they way of the flatMap. */
//   inline def flatMapAbsorb[B](f: A => js.UndefOr[B|Null]): js.UndefOr[B] =
//     a.flatMap{ v =>
//        val x = f(v)
//        if(x == null) js.undefined
//        else x.asInstanceOf[js.UndefOr[B]]
//     }
}

/** Handled js.UndefOr[T|Null] directly vs needing to flatmap into it. Don't forget that
 * scala.js has `anUndefOr.orNull` to extract the value or return null which is *not*
 * what the methods below do. Note that the input is really `T|Null|Unit`.
 */
extension [T](a: js.UndefOr[T | Null])
  inline private def forceGet: T = a.asInstanceOf[T]

  // does this conflict with scalajs isDefined ?
  def isDefined: Boolean = if (a.isDefined && a != null) true else false
  def isEmpty: Boolean = !isDefined

  /** Treat null as undefined and change type from `js.UndefOr[T|Null]` to `js.UndefOr[T]`. */
  def absorbNull: js.UndefOr[T] = a.flatMap { value =>
    if (value == null) js.undefined
    else value.asInstanceOf[js.UndefOr[T]]
  }

  /** Collapse everything at once. */
  def toNonNullOption: Option[T] =
    a.fold(Option.empty[T])(value => if (value != null) Option(value.asInstanceOf[T]) else Option.empty[T])

//   /** Not a true flatMap because the Null is factored out along the way. */
//   inline def flatMapAbsorb[B](f: T => js.UndefOr[B|Null]): js.UndefOr[B] =
//     if(a.isDefined && a != null) f(forceGet) else js.undefined

  /** Flatten the UndefOr and Null to UndefOr only. */
  def flatten: js.UndefOr[T] = absorbNull

  /** T|Null may still have T not being truthy, so absorb null and non-truthiness => js.undefined. */
  def absorbNullKeepTruthy: js.UndefOr[T] = a flatMap { value =>
    if (value == null) js.undefined
    else new JsUndefOrOps(a.asInstanceOf[js.UndefOr[T]]).filterTruthy
  }

  /** Keep type signature, but filter out non-truthy values. */
  def filterTruthy =
    if (js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic])) a
    else js.undefined

  /** Absorb the `js.UndefOr` leaving `T|Null`. */
  def absorbUndef: T | Null =
    if (a.isEmpty) null.asInstanceOf[T | Null] else a.asInstanceOf[T | Null]

  /** `flatten` but leave the UndefOr. Same as `absorbUndef`. */
  def flattenUndefOr: T | Null = absorbUndef

  /** Natural transformation. */
  def swap: js.UndefOr[T] | Null =
    if (a.isDefined && a != null) a.asInstanceOf[js.UndefOr[T] | Null]
    else ().asInstanceOf[js.UndefOr[T] | Null]

  /** Undestands UndefOr and Null to do the orElse. */
  def getOrElse[B >: T](default: => T): T =
    if (a.isEmpty || a == null) default else a.asInstanceOf[T]

  /** Alias for getOrElse. */
  def ??[B >: T](default: => T): T = getOrElse[B](default)

  def !?[B >: T](default: => T): T = getOrElse[B](default)

  /** Alias for `.get` */
  //inline def ! = forceGet

  /** May be undefined or null or something. Throws exception. */
  def get: T =
    if (a == null || a.isEmpty) throw new NoSuchElementException("get on UndefOr[T|Null]")
    else forceGet

  /** Only works with another js.UndefOr[T|Null] and takes into account null. */
  def orDeepElse(that: js.UndefOr[T | Null]) = if (a.isDefined && a != null) a else that

/** Note that js.UndefOr and js.| already have a `.orNull` method. */
final class JsUndefOrOps[A](val _a: UndefOr[A]) extends UndefOrCommon[A] {}

extension [A <: js.Object](a: js.UndefOr[A])
  /** Duplicate inner value if it exists. Saves you a `.map`. */
  def duplicate = a.map(value => js.Object.assign(new js.Object{}, value.asInstanceOf[js.Object]).asInstanceOf[A])

final class UndefMap2[A, B](private val tuple: (js.UndefOr[A], js.UndefOr[B])) extends AnyVal {
  inline def mapX[T](f: (A, B) => T): js.UndefOr[T] =
    if (tuple._1.isDefined && tuple._2.isDefined) js.defined(f(tuple._1.get, tuple._2.get))
    else js.undefined
}

final class UndefMap3[A, B, C](private val tuple: (js.UndefOr[A], js.UndefOr[B], js.UndefOr[C])) extends AnyVal {
  inline def mapX[T](f: (A, B, C) => T): js.UndefOr[T] =
    if (tuple._1.isDefined && tuple._2.isDefined && tuple._3.isDefined)
      js.defined(f(tuple._1.get, tuple._2.get, tuple._3.get))
    else js.undefined
}

final class UndefMap4[A, B, C, D](private val tuple: (js.UndefOr[A], js.UndefOr[B], js.UndefOr[C], js.UndefOr[D]))
    extends AnyVal {
  inline def mapX[T](f: (A, B, C, D) => T): js.UndefOr[T] =
    if (tuple._1.isDefined && tuple._2.isDefined && tuple._3.isDefined && tuple._4.isDefined)
      js.defined(f(tuple._1.get, tuple._2.get, tuple._3.get, tuple._4.get))
    else js.undefined
}

trait JsUndefLowerOrderImplicits:
  inline implicit def jsUndefOrTuple2[A, B](a: (js.UndefOr[A], js.UndefOr[B])): UndefMap2[A,B] = new UndefMap2[A, B](a)
  inline implicit def jsUndefOrTuple3[A, B, C](a: (js.UndefOr[A], js.UndefOr[B], js.UndefOr[C])): UndefMap3[A,B,C] =
    new UndefMap3[A, B, C](a)
  inline implicit def jsUndefOrTuple4[A, B, C, D](a: (js.UndefOr[A], js.UndefOr[B], js.UndefOr[C], js.UndefOr[D])): UndefMap4[A,B,C,D] =
    new UndefMap4[A, B, C, D](a)

trait JsUndefOrSyntax extends JsUndefLowerOrderImplicits:
  inline implicit def jsUndefOrOpsSyntax[A](a: js.UndefOr[A]): JsUndefOrOps[A] = new JsUndefOrOps(a)

type JSAnyConversionTargetBase = Double|String|Boolean|Int|Double|Float|Byte|Long|js.Object|js.Function|js.Dynamic
type JSAnyConversionTarget = JSAnyConversionTargetBase | js.Array[JSAnyConversionTargetBase] | js.UndefOr[JSAnyConversionTargetBase]

/** Convert any of the allowed target types `js.Any`. */
extension (_a: js.UndefOr[JSAnyConversionTarget])
  /** Cast. */
  def asJSAny: js.Any = _a.asInstanceOf[js.Any]

/** Unsafely converty anything that is wrapped in a `js.UndefOr`. */
extension [A](_a: js.UndefOr[A])
  /** Unsafe cast. */
  def unsafeAsJSAny: js.Any = _a.asInstanceOf[js.Any]

extension (_a: js.UndefOr[Boolean])
  /** Get the value or return true. */
  def orTrue: Boolean = _a.getOrElse(true)

  /** Get the value or return false. */
  def orFalse: Boolean = _a.getOrElse(false)

  /** Flip the boolean if defined. */
  def flip: UndefOr[Boolean] = _a.map(!_)


extension (_a: js.UndefOr[String])
  /** Return string's "zero" which is an empty string. */
  def orEmpty: String = _a.getOrElse("")

  /** Filter out empty string and null. Same as filterTruthy. */
  def filterEmpty = _a.filter(str => str != "" && str != null)