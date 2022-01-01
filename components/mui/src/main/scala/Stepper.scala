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

object Stepper {
  @js.native
  @JSImport("@material-ui/core/Stepper", JSImport.Default)
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) =
    createElementN(JS, props)(children: _*)

  @js.native
  sealed trait Orientation extends js.Any
  object Orientation {
    val horizontal = "horizontal".asInstanceOf[Orientation]
    val vertical = "vertical".asInstanceOf[Orientation]
  }

  trait Props extends js.Object {
    var activeStep: js.UndefOr[Double] = js.undefined
    var alternativeLabel: js.UndefOr[Boolean] = js.undefined
    var className: js.UndefOr[String] = js.undefined
    var classes: js.UndefOr[js.Object] = js.undefined
    var component: js.UndefOr[js.Any] = js.undefined
    var connector: js.UndefOr[ReactElement] = js.undefined
    var elevation: js.UndefOr[Double] = js.undefined
    var key: js.UndefOr[String] = js.undefined
    var nonLinear: js.UndefOr[Boolean] = js.undefined
    var orientation: js.UndefOr[Orientation] = js.undefined
    var square: js.UndefOr[Boolean] = js.undefined
    var style: js.UndefOr[js.Object] = js.undefined
  }
}

object StepLabel {

  @js.native
  @JSImport("@material-ui/core/StepLabel", JSImport.Default)
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) =
    createElementN(JS, props)(children: _*)

  trait Props extends js.Object {
    var StepIconComponent: js.UndefOr[js.Any] = js.undefined
    var StepIconProps: js.UndefOr[js.Object] = js.undefined
    var active: js.UndefOr[Boolean] = js.undefined
    var alternativeLabel: js.UndefOr[Boolean] = js.undefined
    var className: js.UndefOr[String] = js.undefined
    var classes: js.UndefOr[js.Object] = js.undefined
    var completed: js.UndefOr[Boolean] = js.undefined
    var disabled: js.UndefOr[Boolean] = js.undefined
    var error: js.UndefOr[Boolean] = js.undefined
    var icon: js.UndefOr[ReactNode] = js.undefined
    var key: js.UndefOr[String] = js.undefined
    var last: js.UndefOr[Boolean] = js.undefined
    var optional: js.UndefOr[ReactNode] = js.undefined
    var orientation: js.UndefOr[Stepper.Orientation] = js.undefined
    var style: js.UndefOr[js.Object] = js.undefined
  }
}

object Step {

  @js.native
  @JSImport("@material-ui/core/Step", JSImport.Default)
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) =
    createElementN(JS, props)(children: _*)

  trait Props extends js.Object {
    var active: js.UndefOr[Boolean] = js.undefined
    var alternativeLabel: js.UndefOr[Boolean] = js.undefined
    var className: js.UndefOr[String] = js.undefined
    var classes: js.UndefOr[js.Object] = js.undefined
    var completed: js.UndefOr[Boolean] = js.undefined
    var connector: js.UndefOr[ReactElement] = js.undefined
    var disabled: js.UndefOr[Boolean] = js.undefined
    var index: js.UndefOr[Double] = js.undefined
    var key: js.UndefOr[String] = js.undefined
    var last: js.UndefOr[Boolean] = js.undefined
    var orientation: js.UndefOr[Stepper.Orientation] = js.undefined
    var style: js.UndefOr[js.Object] = js.undefined
  }
}

object StepContent {

  @js.native
  @JSImport("@material-ui/core/StepContent", JSImport.Default)
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) =
    createElementN(JS, props)(children: _*)

  trait Props extends js.Object {
    var TransitionComponent: js.UndefOr[js.Any] = js.undefined
    var TransitionProps: js.UndefOr[js.Object] = js.undefined
    var active: js.UndefOr[Boolean] = js.undefined
    var alternativeLabel: js.UndefOr[Boolean] = js.undefined
    var className: js.UndefOr[String] = js.undefined
    var classes: js.UndefOr[js.Object] = js.undefined
    var completed: js.UndefOr[Boolean] = js.undefined
    var key: js.UndefOr[String] = js.undefined
    var last: js.UndefOr[Boolean] = js.undefined
    var optional: js.UndefOr[Boolean] = js.undefined
    var orientation: js.UndefOr[Stepper.Orientation] = js.undefined
    var style: js.UndefOr[js.Object] = js.undefined
    var transitionDuration: js.UndefOr[js.Any] = js.undefined
  }
}

object StepButton {

  @js.native
  @JSImport("@material-ui/core/StepButton", JSImport.Default)
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) =
    createElementN(JS, props)(children: _*)

  trait Props extends js.Object {
    var TouchRippleProps: js.UndefOr[js.Object] = js.undefined
    var action: js.UndefOr[js.Any] = js.undefined
    var active: js.UndefOr[Boolean] = js.undefined
    var alternativeLabel: js.UndefOr[Boolean] = js.undefined
    var buttonRef: js.UndefOr[js.Any] = js.undefined
    var centerRipple: js.UndefOr[Boolean] = js.undefined
    var className: js.UndefOr[String] = js.undefined
    var classes: js.UndefOr[js.Object] = js.undefined
    var completed: js.UndefOr[Boolean] = js.undefined
    var component: js.UndefOr[js.Any] = js.undefined
    var disableRipple: js.UndefOr[Boolean] = js.undefined
    var disableTouchRipple: js.UndefOr[Boolean] = js.undefined
    var disabled: js.UndefOr[Boolean] = js.undefined
    var focusRipple: js.UndefOr[Boolean] = js.undefined
    var focusVisibleClassName: js.UndefOr[String] = js.undefined
    var icon: js.UndefOr[ReactNode] = js.undefined
    var key: js.UndefOr[String] = js.undefined
    var last: js.UndefOr[Boolean] = js.undefined
    var onBlur: js.UndefOr[FocusEventHandler[org.scalajs.dom.html.Input]] = js.undefined
    var onClick: js.UndefOr[MouseEventHandler[org.scalajs.dom.html.Input]] = js.undefined
    var onFocus: js.UndefOr[FocusEventHandler[org.scalajs.dom.html.Input]] = js.undefined
    var onFocusVisible: js.UndefOr[scalajs.js.Function0[Unit]] = js.undefined
    var onKeyDown: js.UndefOr[KeyboardEventHandler[org.scalajs.dom.html.Input]] = js.undefined
    var onKeyUp: js.UndefOr[KeyboardEventHandler[org.scalajs.dom.html.Input]] = js.undefined
    var onMouseDown: js.UndefOr[MouseEventHandler[org.scalajs.dom.html.Input]] = js.undefined
    var onMouseLeave: js.UndefOr[MouseEventHandler[org.scalajs.dom.html.Input]] = js.undefined
    var onMouseUp: js.UndefOr[MouseEventHandler[org.scalajs.dom.html.Input]] = js.undefined
    var onTouchEnd: js.UndefOr[TouchEventHandler[org.scalajs.dom.html.Input]] = js.undefined
    var onTouchMove: js.UndefOr[TouchEventHandler[org.scalajs.dom.html.Input]] = js.undefined
    var onTouchStart: js.UndefOr[TouchEventHandler[org.scalajs.dom.html.Input]] = js.undefined
    var optional: js.UndefOr[ReactNode] = js.undefined
    var orientation: js.UndefOr[String] = js.undefined
    var role: js.UndefOr[String] = js.undefined
    var style: js.UndefOr[js.Object] = js.undefined
    var tabIndex: js.UndefOr[js.Any] = js.undefined
    var `type`: js.UndefOr[String] = js.undefined
  }
}

object StepConnecter {

  @js.native
  @JSImport("@material-ui/core/StepConnector", JSImport.Default)
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) =
    createElementN(JS, props)(children: _*)

  trait Props extends js.Object {
    var active: js.UndefOr[Boolean] = js.undefined
    var alternativeLabel: js.UndefOr[Boolean] = js.undefined
    var className: js.UndefOr[String] = js.undefined
    var classes: js.UndefOr[js.Object] = js.undefined
    var completed: js.UndefOr[Boolean] = js.undefined
    var disabled: js.UndefOr[Boolean] = js.undefined
    var index: js.UndefOr[Double] = js.undefined
    var key: js.UndefOr[String] = js.undefined
    var orientation: js.UndefOr[String] = js.undefined
    var style: js.UndefOr[js.Object] = js.undefined
  }
}

object StepIcon {

  @js.native
  @JSImport("@material-ui/core/StepIcon", JSImport.Default)
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) =
    createElementN(JS, props)(children: _*)

  trait Props extends js.Object {
    var active: js.UndefOr[Boolean] = js.undefined
    var classes: js.UndefOr[js.Object] = js.undefined
    var completed: js.UndefOr[Boolean] = js.undefined
    var error: js.UndefOr[Boolean] = js.undefined
    val icon: ReactNode
    var key: js.UndefOr[String] = js.undefined
    var style: js.UndefOr[js.Object] = js.undefined
  }
}
