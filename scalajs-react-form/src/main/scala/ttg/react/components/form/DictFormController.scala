// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react.components
package form

import scala.scalajs.js
import ttg.react._
import ttg.react.implicits._

trait MapValuePart {
  self: FormControllerBase =>

  object Values extends ValuesLike {
    type Value = js.Dictionary[scala.Any]
    def eq(lhs: Value, rhs: Value) = lhs == rhs
    def set(field: String, value: scala.Any, values: Value): Value = {
      val newvalues = copy[js.Object](values.asJsObj).asDict[scala.Any]
      newvalues(field) = value
      newvalues
    }
  }

  type Values = Values.type
  val values = Values
}

/**
  * Common use case plugging in some specific algebras.
  */
object DictFormController
    extends FormControllerBase
    with MapErrorModelPart
    with SetTouchModelPart
    with MapValuePart
