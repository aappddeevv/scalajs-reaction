// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
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
  * as well.  Note that chaining many `js.|` together probably not work like you
  * think and sometimes its better to create a new target type then target
  * implicits to convert from each individual type (in the or) to the new target
  * type.
  */
final case class OrNullOps[A](a: A | Null) {
  /** Convert an A|Null to a well formed Option. Should we check or undefined? */
  def toNonNullOption: Option[A] =
    // doesn't Option(a.asInstanceOf[A]) work?
    //if (a == null) Option.empty[A]
    //else Option(a.asInstanceOf[A])
    Option(a.asInstanceOf[A])

  /** If Null, then false, else true. */
  def toTruthy: Boolean =
    if (js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic])) true
    else false

  /** null => undefined, otherwise A. */
  def toUndefOr: js.UndefOr[A] =
    if (a == null) js.undefined
    else js.defined(a.asInstanceOf[A])

  def toTruthyUndefOr: js.UndefOr[A] =
    if (js.DynamicImplicits.truthValue(a.asInstanceOf[js.Dynamic]))
      js.defined(a.asInstanceOf[A])
    else js.undefined

  /** Collapse A|Null => A but the value may be null! You are on your own */
  def merge: A = a.asInstanceOf[A]
}

trait OrNullSyntax {
  implicit def orNullSyntax[A](a: A | Null): OrNullOps[A] = OrNullOps[A](a)
}

