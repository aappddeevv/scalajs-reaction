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

import js.annotation._

@js.native
@JSImport("prop-types", JSImport.Namespace)
object PropTypes extends ReactPropTypes {}

@js.native
trait Requireable[T <: js.Any] extends js.Object {
  def isRequired(obj: T, key: String, componentName: String, rest: js.Any*): js.Any = js.native
}

@js.native
trait ReactPropTypes extends js.Object {
  val `any`: Requireable[js.Any] = js.native
  val array: Requireable[js.Any] = js.native
  val bool: Requireable[js.Any] = js.native
  val func: Requireable[js.Any] = js.native
  val number: Requireable[js.Any] = js.native
  val `object`: Requireable[js.Any] = js.native
  val string: Requireable[js.Any] = js.native
  val node: Requireable[js.Any] = js.native
  val element: Requireable[js.Any] = js.native
  def instanceOf(expectedClass: js.Object): Requireable[js.Any] = js.native
  def oneOf(types: js.Array[js.Any]): Requireable[js.Any] = js.native
  def oneOfType(types: js.Array[Requireable[js.Any]]): Requireable[js.Any] = js.native
  def arrayOf(`type`: Requireable[js.Any]): Requireable[js.Any] = js.native
  def objectOf(`type`: Requireable[js.Any]): Requireable[js.Any] = js.native
  def shape(`type`: Requireable[js.Any]): Requireable[js.Any] = js.native
}
