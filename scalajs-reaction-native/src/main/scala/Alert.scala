// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
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
  def alert(title: String,
    message: String,
    buttons: js.UndefOr[js.Array[AlertButton]] = js.undefined,
    options: js.UndefOr[AlertOptions] = js.undefined,
    `type`: js.UndefOr[AlertType] = js.undefined
  ): Unit = js.native
}

@js.native
@JSImport("react-native", "AlertIOS")
object AlertIOS extends js.Object {
  def alert(title: String,
    message: String,
    buttons: js.UndefOr[js.Array[AlertButton]] = js.undefined,
    options: js.UndefOr[AlertOptions] = js.undefined,
    `type`: js.UndefOr[AlertType] = js.undefined
  ): Unit = js.native

  def prompt(title: String, message: String,
    cbOrButtons: js.Function1[js.Dynamic,Unit],
    `type`: AlertType,
    defaultValue: js.UndefOr[String] = js.undefined,
    keyboardType: js.UndefOr[String] = js.undefined // @todo make type for this
  ): Unit = js.native
}
