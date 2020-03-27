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
import vdom._
import fabric.styling._

object Dropdown {

  @js.native
  @JSImport("office-ui-fabric-react/lib/Dropdown", "Dropdown")
  object JS extends ReactJSComponent

  def apply(props: Props = null) = createElement0(JS, props)

  @js.native
  trait IDropdown extends js.Object {
    var focus: js.Function1[Boolean, Unit]
    var selectedOptions: js.Array[IDropdownOption]
  }

  trait Props extends ISelectableDroppableTextProps[IDropdownOption, dom.html.Div] with ReactJSProps {
    //var placeholder: js.UndefOr[String] = js.undefined
    //var onChanged: js.UndefOr[js.Function1[IDropdownOption, Unit] | js.Function2[IDropdownOption, Int, Unit]] = js.undefined
    var onChange: js.UndefOr[js.Function3[ReactFormEvent[dom.html.Div], IDropdownOption, Int, Unit]] = js.undefined
    //var onChanged: js.UndefOr[js.Function2[IDropdownOption, js.UndefOr[Int], Unit]] = js.undefined
    var onDismiss: js.UndefOr[js.Function0[Unit]]               = js.undefined
    var onRenderPlaceholder: js.UndefOr[IRenderFunction[Props]] = js.undefined
    var onRenderTitle: js.UndefOr[IRenderFunction[IDropdownOption] | IRenderFunction[js.Array[IDropdownOption]]] =
      js.undefined
    var onRenderCaretDown: js.UndefOr[IRenderFunction[IDropdownOption]] = js.undefined
    var dropdownWidth: js.UndefOr[Int]                                  = js.undefined

    /** Responsive mode */
    var responsiveMode: js.UndefOr[ResponsiveMode]                        = js.undefined
    var multiselect: js.UndefOr[Boolean]                                  = js.undefined
    //var defaultSelectedKeys: js.UndefOr[js.Array[String] | js.Array[Int]] = js.undefined
    //var selectedKeys: js.UndefOr[js.Array[String] | js.Array[Int]]        = js.undefined
    var multiSelectDelimiter: js.UndefOr[String]                          = js.undefined
    //var isDisabled: js.UndefOr[Boolean]                                   = js.undefined

    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
    var theme: js.UndefOr[ITheme]                                      = js.undefined
  }

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
    var label: js.UndefOr[IStyle] = js.undefined
    var dropdown: js.UndefOr[IStyle] = js.undefined
    var title: js.UndefOr[IStyle] = js.undefined
    var caretDownWrapper: js.UndefOr[IStyle] = js.undefined
    var caretDown: js.UndefOr[IStyle] = js.undefined
    var errorMessage: js.UndefOr[IStyle] = js.undefined
    var dropdownItemsWrapper: js.UndefOr[IStyle] = js.undefined
    var dropdownItems: js.UndefOr[IStyle] = js.undefined
    var dropdownItemSelected: js.UndefOr[IStyle] = js.undefined
    var dropdownItemDisabled: js.UndefOr[IStyle] = js.undefined
    var dropdownItemSelectedAndDisabled: js.UndefOr[IStyle] = js.undefined
    var dropdownItemHidden: js.UndefOr[IStyle] = js.undefined
    var dropdownOptionText: js.UndefOr[IStyle] = js.undefined
    var dropdownDivider: js.UndefOr[IStyle] = js.undefined
    var dropdownItemHeader: js.UndefOr[IStyle] = js.undefined
    var panel: js.UndefOr[IStyle] = js.undefined
    var callout: js.UndefOr[IStyle] = js.undefined
    var subComponentStyles: js.UndefOr[Subs] = js.undefined
  }

  trait Subs extends IStyleSetTag {
    var panel: js.UndefOr[IStyleFunctionOrObject[Panel.StyleProps, Panel.Styles]] = js.undefined
    var label: js.UndefOr[IStyleFunctionOrObject[Label.StyleProps, Label.Styles]] = js.undefined
   var multiSelectItem: js.UndefOr[IStyleFunctionOrObject[Checkbox.StyleProps, Checkbox.Styles]] = js.undefined

}

  trait StyleProps extends js.Object {
    var className: js.UndefOr[String] = js.undefined
  var hasError: js.UndefOr[Boolean] = js.undefined
  var hasLabel: js.UndefOr[Boolean] = js.undefined
  var isRenderingPlaceholder: js.UndefOr[Boolean] = js.undefined
  var panelClassName: js.UndefOr[String] = js.undefined
  var calloutClassName: js.UndefOr[String] = js.undefined
  //var calloutRenderEdge: js.UndefOr[RectangleEdge] = js.undefined
  }

}
