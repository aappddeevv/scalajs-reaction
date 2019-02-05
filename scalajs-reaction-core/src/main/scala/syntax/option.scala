// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js
import js._

/** If you want js.UndefOr, use JSConverters and `.orUndefined`. */
final case class OptionOps[T](val a: Option[T]) {

  /** If Some and value is truthy according to JS, then keep it, otherwise become a None. */
  @inline def filterTruthy: Option[T] =
    a.filter(v => js.DynamicImplicits.truthValue(v.asInstanceOf[js.Dynamic]))

  /** Filter nulls out in case it *might* be null.
    * @deprecated USe [[filterNull]].
    */
  @inline def toNonNullOption = a.filter(_ != null)

  /** Filter nulls out in case it *might* be null. */
  @inline def filterNull = a.filter(_ != null)

  /** If Some, keep the value, else set the value to null. */
  @inline def orElseNull = a orElse Some(null.asInstanceOf[T])
}

trait OptionSyntax {
  @inline implicit def optionSyntax[T](a: Option[T]): OptionOps[T] = OptionOps(a)
}
