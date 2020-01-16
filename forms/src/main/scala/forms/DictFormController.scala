// Copyright (c) 2019 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg 
package forms

import scala.scalajs.js
import react._
import react.implicits._

trait MapValues extends HasValues.Service {
  type Value = js.Dictionary[scala.Any]
  def eq(lhs: Value, rhs: Value) = lhs == rhs
  def set(field: String, value: scala.Any, values: Value): Value = {
    val newvalues = copy[js.Object](values.asJsObj).asDict[scala.Any]
    newvalues(field) = value
    newvalues
  }
}

object MapValues extends MapValues

/**
  * Common use case plugging in some specific algebras.
  */
case class DictFormController(Name: String)
    extends FormControllerBase {
  val _values = MapValues
  val _touches = MapTouches
  val _errors = MapErrors
}

