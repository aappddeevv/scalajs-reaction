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
case class DictFormController(Name: String) extends FormControllerBase {
  val _values  = MapValues
  val _touches = MapTouches
  val _errors  = MapErrors
}
