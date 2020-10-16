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
package native

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

trait AlertOptions extends js.Object {
  var cancelable: js.UndefOr[Boolean] = js.undefined
}

trait AlertButton extends js.Object {
  val text: String
  val onPress: js.UndefOr[js.Function0[Unit]] = js.undefined
  var style: js.UndefOr[AlertButtonStyle] = js.undefined
}

@js.native
sealed trait AlertType extends js.Any
object AlertType {
  val default = "default".asInstanceOf[AlertType]
  val plainText = "plain-text".asInstanceOf[AlertType]
  val secureText = "secure-text".asInstanceOf[AlertType]
  val loginPassword = "login-password".asInstanceOf[AlertType]
}

@js.native
sealed trait AlertButtonStyle extends js.Any
object AlertButtonStyle {
  val default = "default".asInstanceOf[AlertButtonStyle]
  val cancel = "cancel".asInstanceOf[AlertButtonStyle]
  val destructive = "destructive".asInstanceOf[AlertButtonStyle]
}

@js.native
@JSImport("react-native", "Alert")
object Alert extends js.Object {
  def alert(
    title: String,
    message: String,
    buttons: js.UndefOr[js.Array[AlertButton]] = js.undefined,
    options: js.UndefOr[AlertOptions] = js.undefined,
    `type`: js.UndefOr[AlertType] = js.undefined
  ): Unit = js.native
}

@js.native
@JSImport("react-native", "AlertIOS")
object AlertIOS extends js.Object {
  def alert(
    title: String,
    message: String,
    buttons: js.UndefOr[js.Array[AlertButton]] = js.undefined,
    options: js.UndefOr[AlertOptions] = js.undefined,
    `type`: js.UndefOr[AlertType] = js.undefined
  ): Unit = js.native

  def prompt(
    title: String,
    message: String,
    cbOrButtons: js.Function1[js.Dynamic, Unit],
    `type`: AlertType,
    defaultValue: js.UndefOr[String] = js.undefined,
    keyboardType: js.UndefOr[String] = js.undefined // @todo make type for this
  ): Unit = js.native
}
