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

object Nav {
  @js.native
  @JSImport("office-ui-fabric-react/lib/Nav", "Nav")
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) = createElementN(JS, props)(children: _*)
  def apply(props: Props) = createElement0(JS, props)

  @js.native
  trait INav extends js.Object {
    var selectedKey: js.UndefOr[String] = js.native
  }

  trait StyleProps extends js.Object {
    var theme: js.UndefOr[ITheme] = js.undefined
    var className: js.UndefOr[String] = js.undefined
    var isOnTop: js.UndefOr[Boolean] = js.undefined
    var isLink: js.UndefOr[Boolean] = js.undefined
    var isGroup: js.UndefOr[Boolean] = js.undefined
    var isExpanded: js.UndefOr[Boolean] = js.undefined
    var isSelected: js.UndefOr[Boolean] = js.undefined
    var isButtonEntry: js.UndefOr[Boolean] = js.undefined
    var navHeight: js.UndefOr[Double] = js.undefined
    var leftPadding: js.UndefOr[Double] = js.undefined
    var leftPaddingExpanded: js.UndefOr[Double] = js.undefined
    var rightPadding: js.UndefOr[Double] = js.undefined
    var position: js.UndefOr[Int] = js.undefined
    var groups: js.UndefOr[js.Array[LinkGroup] | Null] = js.undefined
  }

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
    var linkText: js.UndefOr[IStyle] = js.undefined
    var link: js.UndefOr[IStyle] = js.undefined
    var compositeLink: js.UndefOr[IStyle] = js.undefined
    var chevronButton: js.UndefOr[IStyle] = js.undefined
    var chevronIcon: js.UndefOr[IStyle] = js.undefined
    var navItems: js.UndefOr[IStyle] = js.undefined
    var navItem: js.UndefOr[IStyle] = js.undefined
    var group: js.UndefOr[IStyle] = js.undefined
    var groupContent: js.UndefOr[IStyle] = js.undefined
  }

  trait Props extends ComponentRef[INav] with ReactJSProps {
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
    var theme: js.UndefOr[ITheme] = js.undefined
    var className: js.UndefOr[String] = js.undefined
    var groups: js.UndefOr[js.Array[LinkGroup] | Null] = js.undefined
    var onLinkClick: js.UndefOr[js.Function2[ReactMouseEvent[dom.html.Element], Link, Unit]] = js.undefined
    var onLinkExpandClick: js.UndefOr[js.Function2[ReactMouseEvent[dom.html.Element], Link, Unit]] = js.undefined
    var isOnTop: js.UndefOr[Boolean] = js.undefined
    var initialSelectedKey: js.UndefOr[String] = js.undefined
    var selectedKey: js.UndefOr[String] = js.undefined
    var ariaLabel: js.UndefOr[String] = js.undefined
    var expandButtonAriaLabel: js.UndefOr[String] = js.undefined
  }

  trait LinkGroup extends js.Object {
    var name: js.UndefOr[String] = js.undefined
    var links: js.UndefOr[js.Array[Link]] = js.undefined
    var automationId: js.UndefOr[String] = js.undefined
    var collapseByDefault: js.UndefOr[Boolean] = js.undefined
    var onHeaderClick: js.UndefOr[js.Function2[ReactMouseEvent[dom.html.Element], Boolean, Unit]] = js.undefined
  }

  trait Link extends WithIconProps {
    var name: js.UndefOr[String] = js.undefined
    var url: js.UndefOr[String] = js.undefined
    var key: js.UndefOr[String] = js.undefined
    var links: js.UndefOr[js.Array[Link]] = js.undefined
    var onClick: js.UndefOr[js.Function2[ReactMouseEvent[dom.html.Element], Link, Unit]] = js.undefined
    var automationId: js.UndefOr[String] = js.undefined
    var isExpanded: js.UndefOr[Boolean] = js.undefined
    var ariaLabel: js.UndefOr[String] = js.undefined
    var title: js.UndefOr[String] = js.undefined
    var target: js.UndefOr[String] = js.undefined
    var forceAnchor: js.UndefOr[Boolean] = js.undefined
  }

}
