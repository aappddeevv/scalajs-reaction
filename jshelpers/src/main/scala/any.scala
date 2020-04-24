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

trait AnyOps[T] {
  def a: T

  /** If T is js.Any, this may be redundent. */
  def asJsAny: js.Any = a.asInstanceOf[js.Any]
  def asJsObj: js.Object = a.asInstanceOf[js.Object]
  def asDyn: js.Dynamic = a.asInstanceOf[js.Dynamic]
  def asString: String = a.asInstanceOf[String]
  def asNumber: Number = a.asInstanceOf[Number]
  def asInt: Int = a.asInstanceOf[Int]
  def asDouble: Double = a.asInstanceOf[Double]
  def asBoolean: Boolean = a.asInstanceOf[Boolean]
  def asJsArray[A]: js.Array[A] = a.asInstanceOf[js.Array[A]]
  def asJson: String = js.JSON.stringify(a.asInstanceOf[js.Object])

  /** `.asInstanceOf[T]` but shorter. Very dangerous! */
  def as[T] = a.asInstanceOf[T]

  /** Call the toString method after casting to js.Any. Not sure
   *  casting makes any difference though.
   */
  def toStringJs = a.asInstanceOf[js.Any].toString()

  /** Internal null values become undefined. */
  def filterNull = toNonNullUndefOr

  /** If value is null or undefined be undefined, otherwise defined. Could be called "filterNull". */
  def toNonNullUndefOr: js.UndefOr[T] =
    if (a == null || js.isUndefined(a)) js.undefined
    else js.defined(a)

  /** If value is null or undefined be None, else Some. */
  def toNonNullOption: Option[T] =
    // also defined in react package, repeated here
    if (js.isUndefined(a) || a == null) None
    else Option(a)

  /** Equivalent `!!x` for some javascript value x. */
  def toTruthy: Boolean = js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic])

  /**
   * Wow, a mouthful! If its a javascript truthy=true, its defined, otherwise
   * undef. Takes into account 0, "" and [] javascript idioms i.e. takes into account
   * the FP zero.
   * @example {{{
   *  val s = "" // s.toTruthyUndefOr[String] => js.undefined
   *  val s = "blah" // s.toTurthyUndefOr[String] => defined "blah"
   *  val n = 0  // n.toTruthyUndefOr[Int] => js.undefined
   *  val n1 = 1 // n1.toTruthyUndefOr[Int] => defined 1
   * }}}
   */
  def toTruthyUndefOr: js.UndefOr[T] =
    if (js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic])) js.defined(a)
    else js.undefined
}

final class JsAnyOps[T <: js.Any](val a: T) extends AnyOps[T] {}

trait JsAnySyntax {
  // this does not seem to pick p scala.js mapped types, ask gitter
  @inline implicit def jsAnyOpsSyntax[T <: js.Any](a: T): JsAnyOps[T] = new JsAnyOps(a)
}

/**
 * Intended for directly mapped scala types, not scala.Any in general. Know what
 * you are doing!!! Very dangerous!
 */
final class ScalaMappedOps[T <: scala.Any](val a: T) extends AnyOps[T] {
  ///** Very dangerous! You should know what you are doing. */
  @inline def unsafeAsJsAny = a.asInstanceOf[js.Any]
  @inline def unsafeAsJsObject = a.asInstanceOf[js.Object]
}

/** Typeclass to get `.toLocaleString` on a few targeted scala AnyVal types. */
trait ToLocaleString[A <: AnyVal] {
  def toLocaleString(a: A): String = a.asInstanceOf[js.Object].toLocaleString
}

trait ToLocaleStringInstances { 
  implicit object intToLocaleString extends ToLocaleString[Int]
  implicit object floatToLocaleString extends ToLocaleString[Int]
  implicit object doubleToLocaleString extends ToLocaleString[Int]
}

final class ToLocaleStringInstanceOps[A <: AnyVal](private val a: A) extends AnyVal {
  def toLocaleString(implicit ev: ToLocaleString[A]) = ev.toLocaleString(a)
}

trait ScalaMappedSyntax {
  @inline implicit def toLocaleStringOps[A <: AnyVal](a: A) = new ToLocaleStringInstanceOps[A](a)
  @inline implicit def stringScalaOpsSyntax[String](a: String): ScalaMappedOps[String] =
    new ScalaMappedOps[String](a)
  // all of these seem to conflict with the first String def above and are these needed if they are <: js.Any
  // these seem to conflict as scala things String and Boolean, Byte, etc. lead to ambiguous implicits
  //implicit def booleanScalaOpsSyntax[Boolean](a: Boolean): ScalaMappedOps[Boolean] = ScalaMappedOps[Boolean](a)
  //implicit def byteScalaOpsSyntax[Byte](a: Byte): ScalaMappedOps[Byte] = ScalaMappedOps[Byte](a)
  //implicit def shortScalaOpsSyntax[Short](a: Short): ScalaMappedOps[Short] = ScalaMappedOps[Short](a)
  //implicit def intScalaOpsSyntax[Int](a: Int): ScalaMappedOps[Int] = ScalaMappedOps[Int](a)
  //implicit def floatScalaOpsSyntax[Float](a: Float): ScalaMappedOps[Float] = ScalaMappedOps[Float](a)
  //implicit def doubleScalaOpsSyntax[Double](a: Double): ScalaMappedOps[Double] = ScalaMappedOps[Double](a)
}
