// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package native

import react.elements._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object Button {

  @js.native
  @JSImport("react-native", "Button")
  object JS extends ReactJsComponent

  def apply(props: Props = null) = React.createElement0(JS, props)

  trait Props extends js.Object {
    val title: String
    val onPress: js.Function1[js.Dynamic, Unit]
    var color: js.UndefOr[String] = js.undefined
    var accessibilityLabel: js.UndefOr[String] = js.undefined
    var disabled: js.UndefOr[Boolean] = js.undefined
    var testID: js.UndefOr[String] = js.undefined
  }
}
