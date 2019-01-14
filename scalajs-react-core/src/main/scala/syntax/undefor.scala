// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js
import js._

trait UndefOrCommon[A] {
  val a: UndefOr[A]

  /** Tests for overall nullness which is different than `.isEmpty|.nonEmpty`. */
  @inline def isNull = a == null

  /** This may override `UndefOr.isEmpty` but these semantics are different. */
  @inline def isEmpty = isNull || !a.isDefined

  /** This could also be `_.toOption.filter(_ != null)` but below is slightly faster. */
  @inline def toNonNullOption =
    if (a.isEmpty || a == null) None
    else a.toOption

  /** Calls toString. I'm not sure this is needed at all. */
  @inline def toStringJs = a.asInstanceOf[js.Any].toString()

  /** Equivalent to !!someJSValue */
  @inline def toTruthy: Boolean = js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic])

  /** Keep the value if its truthy, otherwise return undefined. */
  @inline def filterTruthy: js.UndefOr[A] =
    a.filter(v => js.DynamicImplicits.truthValue(v.asInstanceOf[js.Dynamic]))

  /** null => undefined, else the value remains. */
  @inline def filterNull: js.UndefOr[A] = a.filter(_ != null)

  /** undefined => null, else the value remains. */
  @inline def orElseNull: js.UndefOr[A] = a orElse js.defined(null.asInstanceOf[A])
}

final case class JsUndefOrStringOps(val a: UndefOr[String]) extends UndefOrCommon[String] {

  /** Return string's "zero" which is an empty string. */
  @inline def orEmpty: String = a.getOrElse("")

  /** Return null or the value. */
  //@inline def orNull: String = a.getOrElse(null)

  /** Filter out empty string and null. */
  @inline def filterEmpty = a.filter(str => str != "" && str != null)
}

final case class JsUndefOrBooleanOps(val a: UndefOr[Boolean]) extends UndefOrCommon[Boolean] {
  @inline def orTrue: Boolean  = a.getOrElse(true)
  @inline def orFalse: Boolean = a.getOrElse(false)
  @inline def notUndef: UndefOr[Boolean] = a.map(!_)
}

/** Note that js.UdefoOr already has a `.orNull` method. */
final case class JsUndefOrOps[A](a: UndefOr[A]) extends UndefOrCommon[A] {}

trait JsUndefOrSyntax {
  @inline implicit def jsUndefOrOpsSyntax[A](a: UndefOr[A])     = JsUndefOrOps(a)
  @inline implicit def jsUndefOrStringOps(a: UndefOr[String])   = JsUndefOrStringOps(a)
  @inline implicit def jsUndefOrBooleanOps(a: UndefOr[Boolean]) = JsUndefOrBooleanOps(a)
}
