// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js
import js.annotation._
import js.|
import js._
import js.Dynamic.{literal => lit}

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
