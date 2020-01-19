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

package mui
package components

import scala.scalajs.js

import js.annotation._

import org.scalajs.dom._

import react._

import vdom._

object Button {

  @js.native
  @JSImport("@material-ui/core/Button", JSImport.Default)
  object ButtonJS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    createElement(ButtonJS, props)(children: _*)

  @js.native
  sealed trait Color extends js.Any
  object Color {
    val default   = "default".asInstanceOf[Color]
    val inherit   = "inherit".asInstanceOf[Color]
    val primary   = "primary".asInstanceOf[Color]
    val secondary = "secondary".asInstanceOf[Color]
  }

  @js.native
  sealed trait Size extends js.Any
  object Size {
    val small  = "small".asInstanceOf[Size]
    val medium = "medium".asInstanceOf[Size]
    val large  = "large".asInstanceOf[Size]
  }

  @js.native
  sealed trait Variant extends js.Any
  object Variant {
    val fab         = "fab".asInstanceOf[Variant]
    val raised      = "raised".asInstanceOf[Variant]
    val outlined    = "outlined".asInstanceOf[Variant]
    val flat        = "flat".asInstanceOf[Variant]
    val contained   = "contained".asInstanceOf[Variant]
    val text        = "text".asInstanceOf[Variant]
    val extendedFab = "extendedFab".asInstanceOf[Variant]
  }

  trait Props extends js.Object {
    var action: js.UndefOr[js.Any]                              = js.undefined
    var buttonRef: js.UndefOr[js.Any]                           = js.undefined
    var centerRipple: js.UndefOr[Boolean]                       = js.undefined
    var className: js.UndefOr[String]                           = js.undefined
    var classes: js.UndefOr[js.Object]                          = js.undefined
    var color: js.UndefOr[Color]                                = js.undefined
    var component: js.UndefOr[js.Any]                           = js.undefined
    var disableFocusRipple: js.UndefOr[Boolean]                 = js.undefined
    var disableRipple: js.UndefOr[Boolean]                      = js.undefined
    var disableTouchRipple: js.UndefOr[Boolean]                 = js.undefined
    var disabled: js.UndefOr[Boolean]                           = js.undefined
    var focusRipple: js.UndefOr[Boolean]                        = js.undefined
    var focusVisibleClassName: js.UndefOr[String]               = js.undefined
    var fullWidth: js.UndefOr[Boolean]                          = js.undefined
    var href: js.UndefOr[String]                                = js.undefined
    var key: js.UndefOr[String]                                 = js.undefined
    var mini: js.UndefOr[Boolean]                               = js.undefined
    var onBlur: js.UndefOr[FocusEventHandler[html.Input]]       = js.undefined
    var onClick: js.UndefOr[MouseEventHandler[html.Input]]      = js.undefined
    var onFocus: js.UndefOr[FocusEventHandler[html.Input]]      = js.undefined
    var onFocusVisible: js.UndefOr[scalajs.js.Function0[Unit]]  = js.undefined
    var onKeyDown: js.UndefOr[KeyboardEventHandler[html.Input]] = js.undefined
    var onKeyUp: js.UndefOr[KeyboardEventHandler[html.Input]]   = js.undefined
    var onMouseDown: js.UndefOr[MouseEventHandler[html.Input]]  = js.undefined
    var onMouseLeave: js.UndefOr[MouseEventHandler[html.Input]] = js.undefined
    var onMouseUp: js.UndefOr[MouseEventHandler[html.Input]]    = js.undefined
    var onTouchEnd: js.UndefOr[TouchEventHandler[html.Input]]   = js.undefined
    var onTouchMove: js.UndefOr[TouchEventHandler[html.Input]]  = js.undefined
    var onTouchStart: js.UndefOr[TouchEventHandler[html.Input]] = js.undefined
    var role: js.UndefOr[String]                                = js.undefined
    var size: js.UndefOr[Size]                                  = js.undefined
    var style: js.UndefOr[js.Object]                            = js.undefined
    var tabIndex: js.UndefOr[js.Any]                            = js.undefined
    var `type`: js.UndefOr[String]                              = js.undefined
    var variant: js.UndefOr[Variant]                            = js.undefined
  }

}
