// Copyright (c) 2019 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package fabric
package components

import scala.scalajs.js
import js.|
import js.annotation._
import org.scalajs.dom
import react._

object FocusTrapZone {

  @js.native
  trait IFocusTrapZone extends js.Object {
    def focus(): Unit = js.native
  }

  trait Props extends ComponentRef[IFocusTrapZone] {
    var ariaLabelBy: js.UndefOr[String] = js.undefined
    var disableFirstFocus: js.UndefOr[Boolean] = js.undefined
    var disabled: js.UndefOr[Boolean] = js.undefined
    var elementToFocusOnDismiss: js.UndefOr[dom.html.Element] = js.undefined
    var firstFocusSelector: js.UndefOr[String | js.Function0[String]] = js.undefined
    var focusPreviouslyFocusedInnerElement: js.UndefOr[Boolean] = js.undefined
    var forceFocusInsideTrap: js.UndefOr[Boolean] = js.undefined
    var ignoreExternalFocusing: js.UndefOr[Boolean] = js.undefined
    var isClickableOutsideFocusTrap: js.UndefOr[Boolean] = js.undefined
  }

  @js.native
  @JSImport("office-ui-fabric-react", "lib/FocusTrapZone")
  object JS extends ReactJsComponent

  def apply(props: Props)(children: ReactNode*) =
    createElement(JS, props)(children:_*)

}
