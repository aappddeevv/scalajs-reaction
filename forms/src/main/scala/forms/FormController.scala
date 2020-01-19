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

trait ScalaObjectValues[T] extends HasValues.Service {
  type Value = T
  def eq(lhs: Value, rhs: Value) = lhs == rhs
}

trait JSObjectValues[T <: js.Object] extends HasValues.Service {
  type Value = T
  def eq(lhs: Value, rhs: Value) = react.jsEqual(lhs, rhs)
}

case class FormController[T](nameBase: String) extends FormControllerBase {
  val Name     = nameBase + "FormController"
  val _touches = MapTouches
  val _errors  = MapErrors
  val _values  = new ScalaObjectValues[T] {}
}

case class JSFormController[T <: js.Object](nameBase: String) extends FormControllerBase {
  val Name     = nameBase + "JSFormController"
  val _touches = MapTouches
  val _errors  = MapErrors
  val _values  = new JSObjectValues[T] {}
}
