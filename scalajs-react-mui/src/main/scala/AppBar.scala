// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
package mui
package components

import scala.scalajs.js
import js.annotation._
import js.|
import org.scalajs.dom._

import vdom._

object AppBar {
  import ttg.react.elements.wrapJsForScala

  @js.native
  @JSImport("@material-ui/core/AppBar", JSImport.Default)
  object AppBarJS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    wrapJsForScala(AppBarJS, props, children:_*)

  @js.native
  sealed trait Color extends js.Any
  object Color {
    val inherit = "inherit".asInstanceOf[Color]
    val primary  = "primary".asInstanceOf[Color]
    val secondary  = "secondary".asInstanceOf[Color]
    val default  = "default".asInstanceOf[Color]
  }

  @js.native
  sealed trait Position extends js.Any
  object Position {
    val relative = "relative".asInstanceOf[Position]
    val absolute = "absolute".asInstanceOf[Position]
    val fixed = "fixed".asInstanceOf[Position]
    val sticky = "sticky".asInstanceOf[Position]
    val static = "static".asInstanceOf[Position]
  }
          
  trait Props extends js.Object {
    var className: js.UndefOr[String] = js.undefined
    var classes: js.UndefOr[js.Object] = js.undefined
    var color: js.UndefOr[Color] = js.undefined
    var component: js.UndefOr[js.Any] = js.undefined
    var elevation: js.UndefOr[Double] = js.undefined
    var key: js.UndefOr[String] = js.undefined
    var position: js.UndefOr[Position] = js.undefined
    var square: js.UndefOr[Boolean] = js.undefined
    var style: js.UndefOr[js.Object] = js.undefined
  }

}
