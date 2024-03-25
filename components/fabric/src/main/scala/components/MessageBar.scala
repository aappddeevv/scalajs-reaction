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

object MessageBar {
  @js.native
  @JSImport("office-ui-fabric-react/lib/MessageBar", "MessageBar")
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) = createElementN(JS, props)(children*)

  trait IMessageBar extends js.Object

  @js.native
  sealed abstract trait Type extends js.Any

  object Type {
    val info = 0.asInstanceOf[Type]
    val error = 1.asInstanceOf[Type]
    val blocked = 2.asInstanceOf[Type]
    val severeWarning = 3.asInstanceOf[Type]
    val success = 4.asInstanceOf[Type]
    val warning = 5.asInstanceOf[Type]
  }

  trait StyleProps extends js.Object {
    var className: js.UndefOr[IStyle] = js.undefined
    var messageBarType: js.UndefOr[Type] = js.undefined
    var onDismiss: js.UndefOr[Boolean] = js.undefined
    var truncated: js.UndefOr[Boolean] = js.undefined
    var isMultiline: js.UndefOr[Boolean] = js.undefined
    var expandSingleLine: js.UndefOr[Boolean] = js.undefined
    var actions: js.UndefOr[Boolean] = js.undefined
  }

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
    var content: js.UndefOr[IStyle] = js.undefined
    var iconContainer: js.UndefOr[IStyle] = js.undefined
    var icon: js.UndefOr[IStyle] = js.undefined
    var text: js.UndefOr[IStyle] = js.undefined
    var innerText: js.UndefOr[IStyle] = js.undefined
    var dismissal: js.UndefOr[IStyle] = js.undefined
    var expand: js.UndefOr[IStyle] = js.undefined
    var dismissSingleLine: js.UndefOr[IStyle] = js.undefined
    var expandSingleLine: js.UndefOr[IStyle] = js.undefined
    var actions: js.UndefOr[IStyle] = js.undefined
  }

  trait Props extends HTMLAttributes[dom.html.Element] with ComponentRef[IMessageBar] with Theme with Attributes {
    @JSName("messageBarType")
    var barType: js.UndefOr[Type] = js.undefined
    var messageBarType: js.UndefOr[Type] = js.undefined
    var actions: js.UndefOr[ReactElement] = js.undefined
    var arialLabel: js.UndefOr[String] = js.undefined
    var onDismiss: js.UndefOr[js.Function1[js.Any, js.Any]] = js.undefined
    var isMultiline: js.UndefOr[Boolean] = js.undefined
    var dismissButtonAriaLabel: js.UndefOr[String] = js.undefined
    var truncated: js.UndefOr[Boolean] = js.undefined
    var overflowButtonAriaLabel: js.UndefOr[String] = js.undefined
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
  }
}
