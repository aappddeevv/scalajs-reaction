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

object Tabs {

  @js.native
  @JSImport("@material-ui/core/Tabs", JSImport.Default)
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    React.createElement(JS, props)(children:_*)

  @js.native
  sealed trait IndicatorColor extends js.Any
  object IndicatorColor {
    val primary = "primary".asInstanceOf[IndicatorColor]
    val secondary = "secondary".asInstanceOf[IndicatorColor]
  }

  @js.native
  sealed trait ScrollButtons extends js.Any
  object ScrollButtons {
    var auto = "auto".asInstanceOf[ScrollButtons]
    var on = "on".asInstanceOf[ScrollButtons]
    var off = "off".asInstanceOf[ScrollButtons]
  }

  trait Props extends js.Object {
    var action: js.UndefOr[js.Any] = js.undefined
    var centered: js.UndefOr[Boolean] = js.undefined
    var classes: js.UndefOr[js.Object] = js.undefined
    var component: js.UndefOr[String] = js.undefined
    var fullWidth: js.UndefOr[Boolean] = js.undefined
    var indicatorColor: js.UndefOr[IndicatorColor] = js.undefined
    var onChange: js.UndefOr[js.Function2[ReactEvent[html.Input], Int, Unit]] = js.undefined
    var scrollable: js.UndefOr[Boolean] = js.undefined
    var scrollButtons: js.UndefOr[ScrollButtons] = js.undefined
    var textColor: js.UndefOr[Tab.TextColor] = js.undefined
    var value: js.Any // can be false
  }
}
