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

import js.annotation._

import org.scalajs.dom

import react._

import vdom._

import fabric.styling._

object Checkbox {

  @js.native
  @JSImport("office-ui-fabric-react/lib/Checkbox", "Checkbox")
  object JS extends ReactJsComponent

  def apply(props: Props = null) = createElement0(JS, props)

  @js.native
  trait ICheckbox extends Focusable {
    val checked: Boolean = js.native
  }

  @js.native
  sealed trait BoxSide extends js.Any
  object BoxSide {
    val start = "start".asInstanceOf[BoxSide]
    val end   = "end".asInstanceOf[BoxSide]
  }

  // element or inputelement
  trait Props extends HTMLAttributes[dom.html.Input] with ComponentRef[ICheckbox] {
    //var className: js.UndefOr[String] = js.undefined
    var checked: js.UndefOr[Boolean] = js.undefined
    //var defaultChecked: js.UndefOr[Boolean] = js.undefined
    var label: js.UndefOr[String]     = js.undefined
    var disabled: js.UndefOr[Boolean] = js.undefined
    // element or inputelement
    var onChange: js.UndefOr[js.Function2[ReactFormEvent[dom.html.Input], Boolean, Unit]] = js.undefined

    // HTMLButtonElement or HTMLElement
    var inputProps: js.UndefOr[ButtonHTMLAttributes[dom.html.Button]]  = js.undefined
    var boxSide: js.UndefOr[BoxSide]                                   = js.undefined
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
    var theme: js.UndefOr[ITheme]                                      = js.undefined

    var onRenderLabel: js.UndefOr[IRenderFunction[Props]] = js.undefined
    var checkmarkIconProps: js.UndefOr[IIconProps]        = js.undefined
    var keytipProps: js.UndefOr[IKeytipProps]             = js.undefined
  }

  trait StyleProps extends js.Object {
    var theme: js.UndefOr[ITheme]                       = js.undefined
    var className: js.UndefOr[String]                   = js.undefined
    var disabled: js.UndefOr[Boolean]                   = js.undefined
    var checked: js.UndefOr[Boolean]                    = js.undefined
    var reversed: js.UndefOr[Boolean]                   = js.undefined
    var isUsingCustomLabelRenderer: js.UndefOr[Boolean] = js.undefined
  }

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle]      = js.undefined
    var input: js.UndefOr[IStyle]     = js.undefined
    var label: js.UndefOr[IStyle]     = js.undefined
    var checkbox: js.UndefOr[IStyle]  = js.undefined
    var checkmark: js.UndefOr[IStyle] = js.undefined
    var text: js.UndefOr[IStyle]      = js.undefined
  }

}
