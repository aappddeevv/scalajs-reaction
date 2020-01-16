// Copyright (c) 2019 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package react

import scala.scalajs.js
import js._

/** Handle js.UndefOr. Note that js.Undef.orNull exists in scala.js 1.0 */
trait UndefOrCommon[A] {
  val a: UndefOr[A]

  /** Tests for overall nullness which is different than `.isEmpty|.nonEmpty`. */
  def isNull = a == null

  /** This may override `UndefOr.isEmpty` but these semantics are different. */
  def isEmpty = isNull || !a.isDefined

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

  /** undefined => null, else the value remains, inverse of filterNull. */
  def orElseNull: js.UndefOr[A] = a orElse js.defined(null.asInstanceOf[A])

  /** Very dangerous! */
  def as[B]: js.UndefOr[B] = a.map(_.asInstanceOf[B])
}

final case class JsUndefOrStringOps(val a: UndefOr[String]) extends UndefOrCommon[String] {

  /** Return string's "zero" which is an empty string. */
  def orEmpty: String = a.getOrElse("")

  /** Return null or the value. */
  //@inline def orNull: String = a.getOrElse(null)

  /** Filter out empty string and null. */
  def filterEmpty = a.filter(str => str != "" && str != null)
}

// final case class JsUndefOrAOrNullOps[A](val a: UndefOr[A|Null])
//     extends UndefOrCommon[A|Null] {

//   /** Push null into UndefOr as undefined if its null, otherwise A. */
//   def flatten: js.UndefOr[A] = a.flatMap(_.asInstanceOf[js.Any] match {
//     // could not use the other implicit in OrNullOps :-(
//     case null => js.undefined
//     case x => js.defined(x.asInstanceOf[A])
//   })
// }

final case class JsUndefOrBooleanOps(val a: UndefOr[Boolean]) extends UndefOrCommon[Boolean] {
  def orTrue: Boolean  = a.getOrElse(true)
  def orFalse: Boolean = a.getOrElse(false)
  def notUndef: UndefOr[Boolean] = a.map(!_)
}

/** Handled js.UndefOr[T|Null] directly vs needing to flatmap into it. */
final case class JsUndefOrNullOps[T](val a: UndefOr[T|Null]) {
  /** Treat null as undefined and change type from T|Null to T. */
  @inline def absorbNull: js.UndefOr[T] = a.flatMap{value =>
    if(value == null) js.undefined
    else value.asInstanceOf[js.UndefOr[T]]
  }

  /** Absorb the null so that js.UndefOr[T|Null] => js.UndefOr[T] */
  @inline def ?? = absorbNull

  /** T|Null may still have T not being truthy, so absorb null and non-truthiness => js.undefined. */
  @inline def absorbNullKeepTruthy: js.UndefOr[T] = a flatMap { value =>
    if(value == null) js.undefined else JsUndefOrOps(a.asInstanceOf[js.UndefOr[T]]).filterTruthy
  }
}

/** Note that js.UndefOr and js.| already have a `.orNull` method. */
final case class JsUndefOrOps[A](a: UndefOr[A]) extends UndefOrCommon[A] {}

trait JsUndefOrSyntax {
  implicit def jsUndefOrOpsSyntax[A](a: js.UndefOr[A])     = JsUndefOrOps(a)
  implicit def jsUndefOrStringOps(a: js.UndefOr[String])   = JsUndefOrStringOps(a)
//  implicit def jsUndefOrAOrNullOps[A](a: UndefOr[String])   = JsUndefOrAOrNullOps(a)
  implicit def jsUndefOrNullOps[A](a: js.UndefOr[A|Null]) = JsUndefOrNullOps[A](a)
  implicit def jsUndefOrBooleanOps(a: js.UndefOr[Boolean]) = JsUndefOrBooleanOps(a)  
}
