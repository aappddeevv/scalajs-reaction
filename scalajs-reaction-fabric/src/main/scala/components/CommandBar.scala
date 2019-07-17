// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
package fabric
package components

import scala.scalajs.js
import js.annotation._
import js.|
import org.scalajs.dom

import vdom._
import fabric.styling._

object CommandBar {

  @js.native
  @JSImport("office-ui-fabric-react/lib/CommandBar", "CommandBar")
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    React.createElement(JS, props)(children: _*)

  @js.native
  trait ICommandBar extends Focusable {
    def remeasure(): Unit = js.native
  }

  trait Props extends HTMLAttributes[dom.html.Div] {
    var componentRef: js.UndefOr[ICommandBar => Unit] = js.undefined
    var isSearchBoxVisble: js.UndefOr[Boolean]        = js.undefined
    var searchBoxPlaceholderText: js.UndefOr[String]  = js.undefined
    val items: js.Array[IContextualMenuItem]
    var farItems: js.UndefOr[js.Array[IContextualMenuItem]] = js.undefined
  }

  trait ItemProps extends IContextualMenuItem {
    var iconOnly: js.UndefOr[Boolean] = js.undefined
    //tooltipHostPropsp
    // buttonStyles
    var cacheKey: js.UndefOr[Boolean] = js.undefined
    var renderedInOverflow: js.UndefOr[Boolean] = js.undefined
    //commandBarButtonAs is a IComponentAs<ICommandBarItemProps> is this something new?
  }

  trait StyleProps extends js.Object {
    var theme: js.UndefOr[ITheme] = js.undefined
    var className: js.UndefOr[String] = js.undefined
  }

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
    var primarySet: js.UndefOr[IStyle] = js.undefined
    var secondarySet: js.UndefOr[IStyle] = js.undefined
  }

}

