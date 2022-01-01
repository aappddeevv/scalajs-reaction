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

object FocusZone {
  @js.native
  @JSImport("office-ui-fabric-react/lib/FocusZone", "FocusZone")
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) =
    createElementN(JS, props)(children: _*)

  @js.native
  trait IFocusZone extends js.Object {
    def focus(forceIntoFirstElement: js.UndefOr[Boolean]): Boolean = js.native
    def focusElement(childElement: js.UndefOr[dom.html.Element]): Boolean = js.native
  }

  trait Props extends ComponentRef[IFocusZone] with HTMLAttributes[dom.html.Div] {
    //var className: js.UndefOr[String] = js.undefined
    var direction: js.UndefOr[Direction] = js.undefined
    var defaultActiveElement: js.UndefOr[String] = js.undefined
    var disabled: js.UndefOr[Boolean] = js.undefined

    /** e.g. div */
    var elementType: js.UndefOr[String] = js.undefined
    var isCircularNavigation: js.UndefOr[Boolean] = js.undefined
    var isInnerZoneKeyStroke: js.UndefOr[js.Function1[SyntheticKeyboardEvent[dom.html.Element], Boolean]] = js.undefined
    var ariaLabelledBy: js.UndefOr[String] = js.undefined
    var ariaDescribedBy: js.UndefOr[String] = js.undefined
    var onActiveElementChanged: js.UndefOr[
      js.Function0[Unit] | js.Function2[
        js.UndefOr[dom.html.Element],
        js.UndefOr[
          SyntheticFocusEvent[dom.html.Element]
        ],
        Unit]
    ] = js.undefined
    var rootProps: js.UndefOr[HTMLAttributes[dom.html.Div]] = js.undefined
    var onBeforeFocus: js.UndefOr[js.Function1[dom.html.Element, Boolean] | js.Function0[Boolean]] =
      js.undefined
    var allowFocusRoot: js.UndefOr[Boolean] = js.undefined
    var allowTabKey: js.UndefOr[Boolean] = js.undefined

    /** FocusZoneTabbableElements */
    var handleTabKey: js.UndefOr[TabableElements] = js.undefined
    var shouldInputLoseFocusOnArrowKey: js.UndefOr[js.Function1[dom.html.Input, Boolean]] =
      js.undefined
    var checkForNoWrap: js.UndefOr[Boolean] = js.undefined
  }

  @js.native
  sealed trait Direction extends js.Any
  object Direction {
    val vertical = 0.asInstanceOf[Direction]
    val horizontal = 1.asInstanceOf[Direction]
    val biderectional = 2.asInstanceOf[Direction]
  }

  @js.native
  sealed trait TabableElements extends js.Any
  object TabbableElements {
    val none = 0.asInstanceOf[TabableElements]
    val all = 1.asInstanceOf[TabableElements]
    val inputOnly = 2.asInstanceOf[TabableElements]
  }

}
