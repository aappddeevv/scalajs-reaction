// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package mui
package components

import scala.scalajs.js
import js.annotation._
import js.|
import org.scalajs.dom._

import react._
import vdom._

object Radio {

  @js.native
  @JSImport("@material-ui/core/Radio", JSImport.Default)
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    React.createElement(JS, props)(children:_*)

  @js.native
  sealed trait Color extends js.Any
  object Color {
    val primary = "primary".asInstanceOf[Color]
    val secondary = "secondary".asInstanceOf[Color]
    val default = "defaulut".asInstanceOf[Color]
  }
          
  trait Props extends js.Object {
    var checked: js.UndefOr[Boolean|String] = js.undefined
    var checkedIcon: js.UndefOr[ReactNode] = js.undefined
    var classes: js.UndefOr[js.Object] = js.undefined
    var color: js.UndefOr[Color] = js.undefined
    var disableRipple: js.UndefOr[Boolean] = js.undefined
    var disabled: js.UndefOr[Boolean] = js.undefined
    var icon: js.UndefOr[ReactNode] = js.undefined
    var id: js.UndefOr[String] = js.undefined
    var inputProps: js.UndefOr[js.Object] = js.undefined
    var inputRef: js.UndefOr[js.Any] = js.undefined
    var key: js.UndefOr[String] = js.undefined
    var onChange: js.UndefOr[js.Function2[ReactEvent[html.Input], Boolean, Unit]] = js.undefined
    var style: js.UndefOr[js.Object] = js.undefined
    var `type`: js.UndefOr[String] = js.undefined
    var value: js.UndefOr[js.Any] = js.undefined
  }

}
