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

object Card {

  @js.native
  @JSImport("@material-ui/core/Card", JSImport.Default)
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    React.createElement(JS, props)(children:_*)

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
    object JS extends ReactJsComponent

    def apply(props: Props = null)(children: ReactNode*) =
      React.createElement(JS, props)(children:_*)

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
    object JS extends ReactJsComponent

    def apply(props: Props = null)(children: ReactNode*) =
      React.createElement(JS, props)(children:_*)

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
    object JS extends ReactJsComponent

    def apply(props: Props = null)(children: ReactNode*) =
      React.createElement(JS, props)(children:_*)
    
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
    object JS extends ReactJsComponent

    def apply(props: Props = null)(children: ReactNode*) =
      React.createElement(JS, props)(children:_*)

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
    object JS extends ReactJsComponent

    def apply(props: Props = null)(children: ReactNode*) =
      React.createElement(JS, props)(children:_*)

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
      var onBlur: js.UndefOr[FocusEventHandler[html.Element]] = js.undefined
      var onClick: js.UndefOr[MouseEventHandler[html.Element]] = js.undefined
      var onFocus: js.UndefOr[FocusEventHandler[html.Element]] = js.undefined
      var onFocusVisible: js.UndefOr[js.Function0[Unit]] = js.undefined
      var onKeyDown: js.UndefOr[KeyboardEventHandler[html.Element]] = js.undefined
      var onKeyUp: js.UndefOr[KeyboardEventHandler[html.Element]] = js.undefined
      var onMouseDown: js.UndefOr[MouseEventHandler[html.Element]] = js.undefined
      var onMouseLeave: js.UndefOr[MouseEventHandler[html.Element]] = js.undefined
      var onMouseUp: js.UndefOr[MouseEventHandler[html.Element]] = js.undefined
      var onTouchEnd: js.UndefOr[TouchEventHandler[html.Element]] = js.undefined
      var onTouchMove: js.UndefOr[TouchEventHandler[html.Element]] = js.undefined
      var onTouchStart: js.UndefOr[TouchEventHandler[html.Element]] = js.undefined
      var role: js.UndefOr[String] = js.undefined
      var style: js.UndefOr[js.Object] = js.undefined
      var tabIndex: js.UndefOr[js.Any] = js.undefined
      var `type`: js.UndefOr[String] = js.undefined
    }
  }

}
