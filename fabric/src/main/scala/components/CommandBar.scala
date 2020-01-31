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

import js.Dynamic.literal
import js.annotation._

import org.scalajs.dom

import react._

import vdom._

import fabric.styling._

object CommandBar {

  @js.native
  @JSImport("office-ui-fabric-react/lib/CommandBar", "CommandBar")
  object JS extends ReactJsComponent

  def apply(props: Props) = createElement0(JS, props)

  @js.native
  trait ICommandBar extends Focusable {
    def remeasure(): Unit = js.native
  }

  trait PropsBase extends HTMLAttributes[dom.html.Div] with ReactJSProps {
    var theme: js.UndefOr[Theme]                                       = js.undefined
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
    var componentRef: js.UndefOr[ICommandBar => Unit]                  = js.undefined
    var isSearchBoxVisble: js.UndefOr[Boolean]                         = js.undefined
    var searchBoxPlaceholderText: js.UndefOr[String]                   = js.undefined
    var farItems: js.UndefOr[js.Array[_ >: ItemProps]]                 = js.undefined
    var overflowItems: js.UndefOr[js.Array[_ >: ItemProps]]            = js.undefined
    var overflowButtonProps: js.UndefOr[Button.Props]                  = js.undefined
    var shiftOnReduce: js.UndefOr[Boolean]                             = js.undefined
    var onReduceData: js.UndefOr[js.Function1[Data, js.UndefOr[Data]]] = js.undefined
    var onGrowData: js.UndefOr[js.Function1[Data, Data]]               = js.undefined
    var onDataReduced: js.UndefOr[js.Function1[ItemProps, Unit]]       = js.undefined
    var onDataGrown: js.UndefOr[js.Function1[ItemProps, Unit]]         = js.undefined
  }

  trait PropsInit extends PropsBase {
    var items: js.UndefOr[js.Array[_ >: ItemProps]] = js.undefined
  }

  object PropsInit {
    implicit class RichProps(p: PropsInit) {
      def required(_items: js.Array[ItemProps]) = react.merge[Props](p, literal("items" -> _items))
      def required                              = p.asInstanceOf[Props]
    }
  }

  trait Props extends PropsBase {

    /** Required in the end, but made optional so you can create props then merge. */
    val items: js.Array[_ >: ItemProps]
  }

  trait ItemProps extends IContextualMenuItem {
    var iconOnly: js.UndefOr[Boolean] = js.undefined
    //tooltipHostPropsp
    // buttonStyles
    var cacheKey: js.UndefOr[Boolean]           = js.undefined
    var renderedInOverflow: js.UndefOr[Boolean] = js.undefined
    //commandBarButtonAs is a IComponentAs<ICommandBarItemProps> is this something new?
  }

  trait StyleProps extends js.Object {
    var theme: js.UndefOr[ITheme]     = js.undefined
    var className: js.UndefOr[String] = js.undefined
  }

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle]         = js.undefined
    var primarySet: js.UndefOr[IStyle]   = js.undefined
    var secondarySet: js.UndefOr[IStyle] = js.undefined
  }

  @js.native
  trait Data extends js.Object {
    val primaryItems: js.Array[ItemProps]     = js.native
    val overflowItems: js.Array[ItemProps]    = js.native
    val farItems: js.Array[ItemProps]         = js.native
    val minimumOverflowItems: js.UndefOr[Int] = js.native
    val cacheKey: String                      = js.native
  }

}
