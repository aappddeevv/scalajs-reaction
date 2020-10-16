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

object Card {

  @js.native
  @JSImport("@material-ui/core/Card", JSImport.Default)
  object JS extends ReactJSComponent

  def apply(props: Props = null)(children: ReactNode*) =
    createElementN(JS, props)(children: _*)

  trait Props extends js.Object {
    var className: js.UndefOr[String] = js.undefined
    var classes: js.UndefOr[js.Object] = js.undefined
    var component: js.UndefOr[js.Any] = js.undefined
    var elevation: js.UndefOr[Double] = js.undefined
    var key: js.UndefOr[String] = js.undefined
    var raised: js.UndefOr[Boolean] = js.undefined
    var square: js.UndefOr[Boolean] = js.undefined
    var style: js.UndefOr[js.Object] = js.undefined
  }

  object Media {
    @js.native
    @JSImport("@material-ui/core/CardMedia", JSImport.Default)
    object JS extends ReactJSComponent

    def apply(props: Props = null)(children: ReactNode*) =
      createElementN(JS, props)(children: _*)

    trait Props extends js.Object {
      var className: js.UndefOr[String] = js.undefined
      var classes: js.UndefOr[js.Object] = js.undefined
      var component: js.UndefOr[js.Any] = js.undefined
      var image: js.UndefOr[String] = js.undefined
      var key: js.UndefOr[String] = js.undefined
      var src: js.UndefOr[String] = js.undefined
      var style: js.UndefOr[js.Object] = js.undefined
    }
  }

  object Header {
    @js.native
    @JSImport("@material-ui/core/CardHeader", JSImport.Default)
    object JS extends ReactJSComponent

    def apply(props: Props = null)(children: ReactNode*) =
      createElementN(JS, props)(children: _*)

    trait Props extends js.Object {
      var action: js.UndefOr[ReactNode] = js.undefined
      var avatar: js.UndefOr[ReactNode] = js.undefined
      var className: js.UndefOr[String] = js.undefined
      var classes: js.UndefOr[js.Object] = js.undefined
      var component: js.UndefOr[js.Any] = js.undefined
      var disableTypography: js.UndefOr[Boolean] = js.undefined
      var key: js.UndefOr[String] = js.undefined
      var style: js.UndefOr[js.Object] = js.undefined
      var subheader: js.UndefOr[ReactNode] = js.undefined
      var subheaderTypographyProps: js.UndefOr[js.Object] = js.undefined
      var title: js.UndefOr[ReactNode] = js.undefined
      var titleTypographyProps: js.UndefOr[js.Object] = js.undefined
    }
  }

  object Content {
    @js.native
    @JSImport("@material-ui/core/CardContent", JSImport.Default)
    object JS extends ReactJSComponent

    def apply(props: Props = null)(children: ReactNode*) =
      createElementN(JS, props)(children: _*)

    trait Props extends js.Object {
      var className: js.UndefOr[String] = js.undefined
      var classes: js.UndefOr[js.Object] = js.undefined
      var component: js.UndefOr[js.Any] = js.undefined
      var key: js.UndefOr[String] = js.undefined
      var style: js.UndefOr[js.Object] = js.undefined
    }
  }

  object Actions {
    @js.native
    @JSImport("@material-ui/core/CardActions", JSImport.Default)
    object JS extends ReactJSComponent

    def apply(props: Props = null)(children: ReactNode*) =
      createElementN(JS, props)(children: _*)

    trait Props extends js.Object {
      var className: js.UndefOr[String] = js.undefined
      var classes: js.UndefOr[js.Object] = js.undefined
      var disableActionSpacing: js.UndefOr[Boolean] = js.undefined
      var key: js.UndefOr[String] = js.undefined
      var style: js.UndefOr[js.Object] = js.undefined
    }
  }

  object ActionArea {
    @js.native
    @JSImport("@material-ui/core/CardActionArea", JSImport.Default)
    object JS extends ReactJSComponent

    def apply(props: Props = null)(children: ReactNode*) =
      createElementN(JS, props)(children: _*)

    trait Props extends js.Object {
      var TouchRippleProps: js.UndefOr[js.Object] = js.undefined
      var action: js.UndefOr[js.Any] = js.undefined
      var buttonRef: js.UndefOr[js.Any] = js.undefined
      var centerRipple: js.UndefOr[Boolean] = js.undefined
      var className: js.UndefOr[String] = js.undefined
      var classes: js.UndefOr[js.Object] = js.undefined
      var component: js.UndefOr[js.Any] = js.undefined
      var disableRipple: js.UndefOr[Boolean] = js.undefined
      var disableTouchRipple: js.UndefOr[Boolean] = js.undefined
      var disabled: js.UndefOr[Boolean] = js.undefined
      var focusRipple: js.UndefOr[Boolean] = js.undefined
      var focusVisibleClassName: js.UndefOr[String] = js.undefined
      var key: js.UndefOr[String] = js.undefined
      var onBlur: js.UndefOr[FocusEventHandler[org.scalajs.dom.html.Element]] = js.undefined
      var onClick: js.UndefOr[MouseEventHandler[org.scalajs.dom.html.Element]] = js.undefined
      var onFocus: js.UndefOr[FocusEventHandler[org.scalajs.dom.html.Element]] = js.undefined
      var onFocusVisible: js.UndefOr[js.Function0[Unit]] = js.undefined
      var onKeyDown: js.UndefOr[KeyboardEventHandler[org.scalajs.dom.html.Element]] = js.undefined
      var onKeyUp: js.UndefOr[KeyboardEventHandler[org.scalajs.dom.html.Element]] = js.undefined
      var onMouseDown: js.UndefOr[MouseEventHandler[org.scalajs.dom.html.Element]] = js.undefined
      var onMouseLeave: js.UndefOr[MouseEventHandler[org.scalajs.dom.html.Element]] = js.undefined
      var onMouseUp: js.UndefOr[MouseEventHandler[org.scalajs.dom.html.Element]] = js.undefined
      var onTouchEnd: js.UndefOr[TouchEventHandler[org.scalajs.dom.html.Element]] = js.undefined
      var onTouchMove: js.UndefOr[TouchEventHandler[org.scalajs.dom.html.Element]] = js.undefined
      var onTouchStart: js.UndefOr[TouchEventHandler[org.scalajs.dom.html.Element]] = js.undefined
      var role: js.UndefOr[String] = js.undefined
      var style: js.UndefOr[js.Object] = js.undefined
      var tabIndex: js.UndefOr[js.Any] = js.undefined
      var `type`: js.UndefOr[String] = js.undefined
    }
  }

}
