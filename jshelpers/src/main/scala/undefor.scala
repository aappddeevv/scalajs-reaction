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
import js._

/** Add Option-like methods to js.UndefOr. Note that js.Undef.orNull exists in scala.js 1.0 */
trait UndefOrCommon[A] {
  def a: UndefOr[A]

  /** Tests for overall nullness which is different than `.isEmpty|.nonEmpty`. */
  def isNull = a == null

  /** This may override `UndefOr.isEmpty` but this checks for null as well. */
  def isTotalEmpty = isNull || !a.isDefined

  /** This could also be `_.toOption.filter(_ != null)` but below is slightly faster. */
  def toNonNullOption =
    if (a.isEmpty || a == null) None
    else a.toOption

  /** Calls toString. I'm not sure this is needed at all. */
  def toStringJs = a.asInstanceOf[js.Any].toString()

  /** Equivalent to !!someJSValue */
  def toTruthy: Boolean = js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic])

  /** Keep the value if its truthy, otherwise return undefined. */
  def filterTruthy: js.UndefOr[A] =
    a.filter(v => js.DynamicImplicits.truthValue(v.asInstanceOf[js.Dynamic]))

  /** null => undefined, else the value remains. */
  def filterNull: js.UndefOr[A] = a.filter(_ != null)

  /** Very dangerous! */
  def as[B]: js.UndefOr[B] = a.map(_.asInstanceOf[B])

  /** Convert UndefOr[A] => A|Null */
  def toNull: A | Null = if (as.isDefined) a.asInstanceOf[A | Null] else null
  
  /** Same as `.getOrElse` just shorter. */
  @inline def ??[B >: A](default: => B): B = a.getOrElse(default)
  
  /** Same as `.getOrElse` just shorter. */
  @inline def !?[B >: A](default: => B): B = a.getOrElse[B](default)
  
  /** Uh-oh, thought it was `js.UndefOr[A]` but you need to say its a
    * `js.UndefOr[A|Null]` because the docs were wrong :-).
    */
  @inline def toUndefOrNull: js.UndefOr[A|Null] = a.asInstanceOf[js.UndefOr[A|Null]]
}

final case class JsUndefOrStringOps(val a: UndefOr[String]) extends UndefOrCommon[String] {

  /** Return string's "zero" which is an empty string. */
  @inline def orEmpty: String = a.getOrElse("")

  /** Filter out empty string and null. */
  @inline def filterEmpty = a.filter(str => str != "" && str != null)
}

final class JsUndefOrBooleanOps(val a: UndefOr[Boolean]) extends UndefOrCommon[Boolean] {
  /** Get the value or return true. */
  @inline def orTrue: Boolean = a.getOrElse(true)
  
  /** Get the value or return false. */
  @inline def orFalse: Boolean = a.getOrElse(false)

  /** Flip the boolean if defined. */
  @inline def flip: UndefOr[Boolean] = a.map(!_)
}

/** Handled js.UndefOr[T|Null] directly vs needing to flatmap into it. Don't forget that
 * scala.js has `anUndefOr.orNull` to extract the value or return null which is *not*
 * what the methods below do.
 */
final class JsUndefOrNullOps[T](val a: UndefOr[T | Null]) extends AnyVal {
  @inline private def forceGet: T = a.asInstanceOf[T]

  /** Treat null as undefined and change type from T|Null to T. */
  @inline def absorbNull: js.UndefOr[T] = a.flatMap { value =>
    if (value == null) js.undefined
    else value.asInstanceOf[js.UndefOr[T]]
  }

  /** Flatten the UndefOr and Null to UndefOr only. */
  @inline def flatten = absorbNull

  /** T|Null may still have T not being truthy, so absorb null and non-truthiness => js.undefined. */
  @inline def absorbNullKeepTruthy: js.UndefOr[T] = a flatMap { value =>
    if (value == null) js.undefined
    else new JsUndefOrOps(a.asInstanceOf[js.UndefOr[T]]).filterTruthy
  }

  /** Absorb the `js.UndefOr` leaving `T|Null`. */
  @inline def absorbUndef: T | Null =
    if (a.isEmpty) null.asInstanceOf[T | Null] else a.asInstanceOf[T | Null]

  /** `flatten` but leave the UndefOr. Same as `absorbUndef`. */
  @inline def flattenUndefOr = absorbUndef

  /** Natural transformation. */
  @inline def swap: js.UndefOr[T] | Null =
    if (a.isDefined && a != null) a.asInstanceOf[js.UndefOr[T] | Null]
    else ().asInstanceOf[js.UndefOr[T] | Null]

  @inline def getOrElse[B >: T](default: => T): T = 
    if(a.isEmpty || a == null) default else a.asInstanceOf[T]
    
  /** Alias for getOrElse. */
  @inline def ??[B >: T](default: => T): T = getOrElse[B](default)
    
  @inline def !?[B >: T](default: => T): T = getOrElse[B](default)
    
  /** Alias for `.get` */
  @inline def ! = forceGet
  
  /** May be undefined or null or something. Throws exception. */
  @inline def get: T = 
    if(a==null || a.isEmpty) throw new NoSuchElementException("get on UndefOr[T|Null]") 
    else forceGet
}

/** Note that js.UndefOr and js.| already have a `.orNull` method. */
final class JsUndefOrOps[A](val a: UndefOr[A]) extends UndefOrCommon[A] {}

final class UndefMap2[A, B](private val tuple: (js.UndefOr[A], js.UndefOr[B])) extends AnyVal {
  @inline def mapX[T](f: (A, B) => T): js.UndefOr[T] =
    if (tuple._1.isDefined && tuple._2.isDefined) js.defined(f(tuple._1.get, tuple._2.get))
    else js.undefined
}

final class UndefMap3[A, B, C](private val tuple: (js.UndefOr[A], js.UndefOr[B], js.UndefOr[C])) extends AnyVal {
  @inline def mapX[T](f: (A, B, C) => T): js.UndefOr[T] =
    if (tuple._1.isDefined && tuple._2.isDefined && tuple._3.isDefined)
      js.defined(f(tuple._1.get, tuple._2.get, tuple._3.get))
    else js.undefined
}

final class UndefMap4[A, B, C, D](private val tuple: (js.UndefOr[A], js.UndefOr[B], js.UndefOr[C], js.UndefOr[D]))
    extends AnyVal {
  @inline def mapX[T](f: (A, B, C, D) => T): js.UndefOr[T] =
    if (tuple._1.isDefined && tuple._2.isDefined && tuple._3.isDefined &&
        tuple._4.isDefined)
      js.defined(f(tuple._1.get, tuple._2.get, tuple._3.get, tuple._4.get))
    else js.undefined
}

trait JsUndefLowerOrderImplicits {
  @inline implicit def jsUndefOrTuple2[A, B](a: (js.UndefOr[A], js.UndefOr[B])) =
    new UndefMap2[A, B](a)
  @inline implicit def jsUndefOrTuple3[A, B, C](a: (js.UndefOr[A], js.UndefOr[B], js.UndefOr[C])) =
    new UndefMap3[A, B, C](a)
  @inline implicit def jsUndefOrTuple4[A, B, C, D](a: (js.UndefOr[A], js.UndefOr[B], js.UndefOr[C], js.UndefOr[D])) =
    new UndefMap4[A, B, C, D](a)
}

trait JsUndefOrSyntax extends JsUndefLowerOrderImplicits {

  @inline implicit def jsUndefOrOpsSyntax[A](a: js.UndefOr[A]) = new JsUndefOrOps(a)
  @inline implicit def jsUndefOrStringOps(a: js.UndefOr[String]) = new JsUndefOrStringOps(a)
//  implicit def jsUndefOrAOrNullOps[A](a: UndefOr[String])   = JsUndefOrAOrNullOps(a)
  @inline implicit def jsUndefOrNullOps[A](a: js.UndefOr[A | Null]) = new JsUndefOrNullOps[A](a)
  @inline implicit def jsUndefOrBooleanOps(a: js.UndefOr[Boolean]) = new JsUndefOrBooleanOps(a)
}
