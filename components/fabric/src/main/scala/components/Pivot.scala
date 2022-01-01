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

object Pivot {
  @js.native
  @JSImport("office-ui-fabric-react/lib/Pivot", "Pivot")
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) = createElementN(JS, props)(children: _*)
  def apply(children: ReactNode*) = createElementN(JS, null)(children: _*)

  @js.native
  trait IPivot extends Focusable

  trait StyleProps extends Theme {
    var className: js.UndefOr[String] = js.undefined
    //var theme: js.UndefOr[ITheme] = js.undefined
    var rootIsLarge: js.UndefOr[Boolean] = js.undefined
    var rootIsTabs: js.UndefOr[Boolean] = js.undefined
    var linkIsSelected: js.UndefOr[Boolean] = js.undefined
  }

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
    var link: js.UndefOr[IStyle] = js.undefined
    var linkContent: js.UndefOr[IStyle] = js.undefined
    var linkIsSelected: js.UndefOr[IStyle] = js.undefined
    var itemContainer: js.UndefOr[IStyle] = js.undefined
    var text: js.UndefOr[IStyle] = js.undefined
    var count: js.UndefOr[IStyle] = js.undefined
    var icon: js.UndefOr[IStyle] = js.undefined
  }

  trait Props extends Attributes with Theme with ComponentRef[IPivot] {
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
    var className: js.UndefOr[String] = js.undefined

    var defaultSelectedKey: js.UndefOr[String] = js.undefined
    var defaultSelectedIndex: js.UndefOr[Int] = js.undefined
    var selectedKey: js.UndefOr[String] = js.undefined
    var selectedIndex: js.UndefOr[Int] = js.undefined
    var onLinkClick: js.UndefOr[LinkClickHandler] = js.undefined
    var linkSize: js.UndefOr[LinkSize] = js.undefined
    var linkFormat: js.UndefOr[LinkFormat] = js.undefined
    var headersOnly: js.UndefOr[Boolean] = js.undefined
    var getTabId: js.UndefOr[js.Function2[String, Int, String]] = js.undefined
    var overflowBehavior: js.UndefOr[OverflowBehavior] = js.undefined
  }

  type LinkClickHandler =
    js.Function2[js.UndefOr[PivotItem.PivotItemLike], js.UndefOr[ReactMouseEvent[dom.html.Element]], Unit]

  def OnLinkClick(f: (js.UndefOr[PivotItem.PivotItemLike], js.UndefOr[ReactMouseEvent[dom.html.Element]]) => Unit) =
    js.Any.fromFunction2(f).asInstanceOf[LinkClickHandler]

  def OnLinkClickHandlerNoArg(f: () => Unit) = js.Any.fromFunction0(f).asInstanceOf[LinkClickHandler]

  @js.native
  sealed trait LinkSize extends js.Any
  object LinkSize {
    val normal = 0.asInstanceOf[LinkSize]
    val large = 1.asInstanceOf[LinkSize]
  }

  @js.native
  abstract trait LinkFormat extends js.Any
  object LinkFormat {
    val link = 0.asInstanceOf[LinkFormat]
    val tabs = 1.asInstanceOf[LinkFormat]
  }

  @js.native
  abstract trait OverflowBehavior extends js.Any
  object OverflowBehavior {
    val none = "none".asInstanceOf[OverflowBehavior]
    val menu = "menu".asInstanceOf[OverflowBehavior]
  }

}
