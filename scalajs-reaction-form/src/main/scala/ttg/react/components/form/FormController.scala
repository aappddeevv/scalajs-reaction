// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react.components
package form

import scala.scalajs.js

trait ObjectValuePart[T] {
  self: FormControllerBase =>

  object Values extends ValuesLike {
    type Value = T
    def eq(lhs: Value, rhs: Value) = lhs == rhs
  }

  type Values = Values.type
  val values = Values
}

/**
  * General non-native JS trait version which is really just a dictionary
  * underneath :-).
  */
class FormController[T <: js.Object]
    extends FormControllerBase
    with MapErrorModelPart
    with SetTouchModelPart
    with ObjectValuePart[T]
