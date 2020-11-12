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
import js.|

/** If you want js.UndefOr, use JSConverters `.toOption`and `.orUndefined`. */
final class OptionOps[T](private val a: Option[T]) extends AnyVal {

  /** If Some and value is truthy according to JS, then keep it, otherwise become a None. */
  def filterTruthy: Option[T] =
    a.filter(v => js.DynamicImplicits.truthValue(v.asInstanceOf[js.Dynamic]))

  /** Filter nulls out in case it *might* be null.
   * @deprecated USe [[filterNull]].
   */
  def toNonNullOption = a.filter(_ != null)

  /** Filter nulls out in case it *might* be null. */
  def filterNull = a.filter(_ != null)

  /** If Some, keep the value, else set the value to null. */
  def orElseNull = a orElse Some(null.asInstanceOf[T])

  def ???[B >: T](other: Option[B]): Option[B] =
    if (a.isEmpty) other else a

  def ??[B >: T](default: => B): B = a.getOrElse(default)

  /** Changes type to T but does *not* add `|Null`. */
  def orNull: T = a.getOrElse(null).asInstanceOf[T]

  /** Changes type to `T|Null`. */
  def withNull: T | Null = a.getOrElse(null).asInstanceOf[T | Null]

  /** Tap the value. */
  def tapValue(f: T => Any) = a.map { t => f(t); t }
  
  /** Add | Null to T */
  def maybeNull: Option[T|Null] = a.map(_.asInstanceOf[T|Null])
}

trait OptionSyntax {
  @inline implicit def optionSyntax[T](a: Option[T]): OptionOps[T] = new OptionOps(a)
}
