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
import org.scalajs.dom.html
import react.*
import vdom.*
import fabric.styling.*

object Label:
  @js.native
  @JSImport("office-ui-fabric-react/lib/Label", "Label")
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) =
    createElementN(JS, props)(children: _*)

  def apply(children: ReactNode*) =
    createElementN(JS, null)(children: _*)

  def keyAndText(k: String, text: String) =
    createElement(JS, new Props {
      key = k
    }, text.asInstanceOf[ReactNode])

  trait Props
      extends LabelHTMLAttributes[html.Label]
      with ComponentRef[js.Any]
      with Disabled
      with Theme
      with MaybeHasStrKey
      with ReactJSProps:
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
    //var key: js.UndefOr[react.KeyType] = js.undefined
    var required: js.UndefOr[Boolean] = js.undefined

  trait StyleProps extends js.Object:
    var className: js.UndefOr[String] = js.undefined
    var disabled: js.UndefOr[Boolean] = js.undefined
    var required: js.UndefOr[Boolean] = js.undefined
    var theme: js.UndefOr[Theme] = js.undefined

  trait Styles extends IStyleSetTag:
    var root: js.UndefOr[IStyle] = js.undefined

