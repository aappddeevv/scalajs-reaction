// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
package materialui
package components

import scala.scalajs.js
import js.annotation._
import js.|
import org.scalajs.dom._

import vdom._

object Button {
  import ttg.react.elements.wrapJsForScala

  @js.native
  @JSImport("@material-ui/core/Button", JSImport.Default)
  object ButtonJS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    wrapJsForScala(ButtonJS, props, children:_*)

  trait Props extends js.Object {
    var action: js.UndefOr[js.Any] = js.undefined
    var buttonRef: js.UndefOr[js.Any] = js.undefined
    var centerRipple: js.UndefOr[Boolean] = js.undefined
    var className: js.UndefOr[String] = js.undefined
    var classes: js.UndefOr[js.Object] = js.undefined
    var color: js.UndefOr[String] = js.undefined
    var component: js.UndefOr[js.Any] = js.undefined
    var disableFocusRipple: js.UndefOr[Boolean] = js.undefined
    var disableRipple: js.UndefOr[Boolean] = js.undefined
    var disableTouchRipple: js.UndefOr[Boolean] = js.undefined
    var disabled: js.UndefOr[Boolean] = js.undefined
    var focusRipple: js.UndefOr[Boolean] = js.undefined
    var focusVisibleClassName: js.UndefOr[String] = js.undefined
    var fullWidth: js.UndefOr[Boolean] = js.undefined
    var href: js.UndefOr[String] = js.undefined
    var key: js.UndefOr[String] = js.undefined
    var mini: js.UndefOr[Boolean] = js.undefined
    var onBlur: js.UndefOr[FocusEventHandler[html.Input]] = js.undefined
    var onClick: js.UndefOr[MouseEventHandler[html.Input]] = js.undefined
    var onFocus: js.UndefOr[FocusEventHandler[html.Input]] = js.undefined
    var onFocusVisible: js.UndefOr[scalajs.js.Function0[Unit]] = js.undefined
    var onKeyDown: js.UndefOr[KeyboardEventHandler[html.Input]] = js.undefined
    var onKeyUp: js.UndefOr[KeyboardEventHandler[html.Input]] = js.undefined
    var onMouseDown: js.UndefOr[MouseEventHandler[html.Input]] = js.undefined
    var onMouseLeave: js.UndefOr[MouseEventHandler[html.Input]] = js.undefined
    var onMouseUp: js.UndefOr[MouseEventHandler[html.Input]] = js.undefined
    var onTouchEnd: js.UndefOr[TouchEventHandler[html.Input]] = js.undefined
    var onTouchMove: js.UndefOr[TouchEventHandler[html.Input]] = js.undefined
    var onTouchStart: js.UndefOr[TouchEventHandler[html.Input]] = js.undefined
    var role: js.UndefOr[String] = js.undefined
    var size: js.UndefOr[String] = js.undefined
    var style: js.UndefOr[js.Object] = js.undefined
    var tabIndex: js.UndefOr[js.Any] = js.undefined
    var `type`: js.UndefOr[String] = js.undefined
    var variant: js.UndefOr[String] = js.undefined
  }

}
