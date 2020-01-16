// Copyright (c) 2019 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package react

import scala.scalajs.js
import js._

/** If you want js.UndefOr, use JSConverters `.toOption`and `.orUndefined`. */
final case class OptionOps[T](val a: Option[T]) {

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
}

trait OptionSyntax {
  implicit def optionSyntax[T](a: Option[T]): OptionOps[T] = OptionOps(a)
}
