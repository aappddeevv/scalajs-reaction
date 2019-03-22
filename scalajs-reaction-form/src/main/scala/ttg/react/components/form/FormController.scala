// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package components
package form

import scala.scalajs.js

trait ScalaObjectValues[T] extends HasValues.Service {
    type Value = T
    def eq(lhs: Value, rhs: Value) = lhs == rhs
}

trait JSObjectValues[T <: js.Object] extends HasValues.Service {
    type Value = T
    def eq(lhs: Value, rhs: Value) = react.jsEqual(lhs,rhs)
}

case class FormController[T](Name: String)
    extends FormControllerBase {
  val _touches = MapTouches
  val _errors = MapErrors
  val _values = new ScalaObjectValues[T] { }
}

case class JSFormController[T <: js.Object](Name: String)
    extends FormControllerBase {
  val _touches = MapTouches
  val _errors = MapErrors
  val _values = new JSObjectValues[T] { }  
}
