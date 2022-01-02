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

package fabric
package components

import scala.scalajs.js
import js.annotation.*
import org.scalajs.dom
import react.*
import vdom.*
import fabric.styling.*

object Toggle {
  @js.native
  @JSImport("office-ui-fabric-react/lib/Toggle", "Toggle")
  object JS extends ReactJSComponent

  def apply(props: js.UndefOr[Props]=js.undefined) = createElement0(JS, props)

  @js.native
  trait IToggle extends Focusable

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
    var label: js.UndefOr[IStyle] = js.undefined
    var container: js.UndefOr[IStyle] = js.undefined
    var pill: js.UndefOr[IStyle] = js.undefined
    var thumb: js.UndefOr[IStyle] = js.undefined
    var text: js.UndefOr[IStyle] = js.undefined
  }

  trait StyleProps extends js.Object {
    var theme: js.UndefOr[Theme] = js.undefined
    var className: js.UndefOr[String] = js.undefined
    var disabled: js.UndefOr[Boolean] = js.undefined
    var checked: js.UndefOr[Boolean] = js.undefined
    var inlineLabel: js.UndefOr[Boolean] = js.undefined
    var onOffMissing: js.UndefOr[Boolean] = js.undefined
  }

  trait Props extends KeyAndRef with ComponentRef[IToggle] with Theme {
    var label: js.UndefOr[String] = js.undefined
    var onText: js.UndefOr[String] = js.undefined
    var offText: js.UndefOr[String] = js.undefined
    var className: js.UndefOr[String] = js.undefined
    var ariaLabel: js.UndefOr[String] = js.undefined
    var checked: js.UndefOr[Boolean] = js.undefined
    var defaultChecked: js.UndefOr[Boolean] = js.undefined
    var disabled: js.UndefOr[Boolean] = js.undefined
    var inlineLabel: js.UndefOr[Boolean] = js.undefined
    var onChange: js.UndefOr[js.Function2[ReactMouseEvent[dom.html.Element], Boolean, Unit]] = js.undefined
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
    var keytipProps: js.UndefOr[IKeytipProps] = js.undefined
  }

  @js.native
  abstract sealed trait AriaLive extends js.Any
  object AriaLive {
    var assertive = "assertive".asInstanceOf[AriaLive]
    var polite = "polite".asInstanceOf[AriaLive]
    var off = "off".asInstanceOf[AriaLive]
  }

  @js.native
  abstract sealed trait Size extends js.Any
  object Size {
    var xSmall = 0.asInstanceOf[Size]
    var small = 1.asInstanceOf[Size]
    var medium = 2.asInstanceOf[Size]
    var large = 3.asInstanceOf[Size]
  }

  @js.native
  sealed trait LabelPosition extends js.Any
  object LabelPosition {
    val top = "top".asInstanceOf[LabelPosition]
    val right = "right".asInstanceOf[LabelPosition]
    val bottom = "bottom".asInstanceOf[LabelPosition]
    val left = "left".asInstanceOf[LabelPosition]
  }

}
