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
final class OrNullOps[A](private val a: A | Null) extends AnyVal {
  @inline def isDefined: Boolean  = a != null
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
  @inline implicit def orNullSyntax[A](a: A | Null): OrNullOps[A] = new OrNullOps[A](a)
}
