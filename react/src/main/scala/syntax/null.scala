// Copyright (c) 2019 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package react

import scala.scalajs.js
import js._

/*
String | Unit  => Option[String]: provides a string when Some and js.undefined when None
x.fold[String | Unit](())(x => x)
 */

/**
  * It is common in interop code to model a value as A or null but not undefined
  * even though null and undefined may both mean "absent value." See `|.merge`
  * Note that chaining many `js.|` together probably not work like you think and
  * sometimes its better to create a new target type then target implicits to
  * convert from each individual type (in the or) to the new target type. You
  * must model your type as `A|Null` for this implicit to be picked up.
 * Note that `js.|.orNull` exist in scala.js 1.0.
  */
final case class OrNullOps[A](a: A | Null) {
  @inline def isDefined: Boolean = a != null
  @inline private def forceGet: A = a.asInstanceOf[A]

  /** Convert an A|Null to a well formed Option. Should we check or undefined? */
  def toNonNullOption: Option[A] =
    Option(a.asInstanceOf[A])

  /** Like .toNonNullOption */
  def toOption: Option[A] =
    Option(a.asInstanceOf[A])

  /** If Null, then false, else true. */
  def toTruthy: Boolean =
    if (js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic])) true
    else false

  /** null => undefined, otherwise A. */
  def toUndefOr: js.UndefOr[A] =
    if (!isDefined) js.undefined
    else js.defined(a.asInstanceOf[A])

  /** Avoid calling toUndefOr */
  def getOrElse[B >: A](b: B): B = toUndefOr.getOrElse(b)

  def toTruthyUndefOr: js.UndefOr[A] =
    if (js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic]))
      js.defined(a.asInstanceOf[A])
    else js.undefined

  /** Collapse A|Null => A but the value may be null! You are on your own */
  @inline def merge: A = forceGet

  /** Absorb the null and change A|Null => A */
  @inline def absorbNull: A = forceGet

  /** Same as absorbNull */
  @inline def ?? = absorbNull
}

trait OrNullSyntax {
  implicit def orNullSyntax[A](a: A | Null): OrNullOps[A] = OrNullOps[A](a)
}

