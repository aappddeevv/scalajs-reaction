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
import js.|

import org.scalajs.dom

import react._

object FocusTrapZone {

  @js.native
  trait IFocusTrapZone extends js.Object {
    def focus(): Unit = js.native
  }

  trait Props extends ComponentRef[IFocusTrapZone] {
    var ariaLabelBy: js.UndefOr[String]                               = js.undefined
    var disableFirstFocus: js.UndefOr[Boolean]                        = js.undefined
    var disabled: js.UndefOr[Boolean]                                 = js.undefined
    var elementToFocusOnDismiss: js.UndefOr[dom.html.Element]         = js.undefined
    var firstFocusSelector: js.UndefOr[String | js.Function0[String]] = js.undefined
    var focusPreviouslyFocusedInnerElement: js.UndefOr[Boolean]       = js.undefined
    var forceFocusInsideTrap: js.UndefOr[Boolean]                     = js.undefined
    var ignoreExternalFocusing: js.UndefOr[Boolean]                   = js.undefined
    var isClickableOutsideFocusTrap: js.UndefOr[Boolean]              = js.undefined
  }

  @js.native
  @JSImport("office-ui-fabric-react", "lib/FocusTrapZone")
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) =
    createElement(JS, props)(children: _*)

}
