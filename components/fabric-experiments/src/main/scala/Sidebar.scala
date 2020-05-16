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
package experiments

import scala.scalajs.js
import js.annotation._
import org.scalajs.dom
import react._
import react.vdom._
import fabric.components._
import fabric.styling._

@js.native
trait ISidebar extends js.Object {
  def toggleCollapsed(): Unit = js.native
  def setCollapsed(newValue: Boolean): Unit = js.native
  def getCollapsed(): Boolean = js.native
}

object Sidebar {

  @js.native
  @JSImport("@uifabric/experiments/lib/Sidebar", "Sidebar")
  object JS extends ReactJSComponent

  def apply(props: Props = null) = createElement0(JS, props)

  trait PropsBase extends HTMLAttributes[dom.html.Div] with ComponentRef[ISidebar] {
    var collapsible: js.UndefOr[Boolean] = js.undefined
    var onCollapseChanged: js.UndefOr[js.Function0[Unit]] = js.undefined
    var collapseButtonAriaLabel: js.UndefOr[String] = js.undefined
    var footerItems: js.UndefOr[js.Array[ItemProps]] = js.undefined
    var items: js.UndefOr[js.Array[ItemProps]] = js.undefined
    var colors: js.UndefOr[Colors] = js.undefined
    var styles: js.UndefOr[Styles] = js.undefined
    var collapseButtonStyles: js.UndefOr[components.Button.Styles] = js.undefined
    var buttonStyles: js.UndefOr[components.Button.Styles] = js.undefined
    var defaultButton: js.UndefOr[ReactComponentType] = js.undefined
  }

  trait PropsInit extends PropsBase {
    var theme: js.UndefOr[ITheme] = js.undefined
  }

  trait Props extends PropsBase {
    val theme: ITheme
  }

  @js.native
  sealed trait Colors extends js.Any
  object Colors {
    val Dark = "Dark".asInstanceOf[Colors]
    val Light = "Light".asInstanceOf[Colors]
  }

  // trait IColors extends js.Object {
  //   val background: String
  //   var backgroundHovered: js.UndefOr[String] = js.undefined
  //   val backgroundActive: String
  //   val buttonColor: String
  // }

  // @js.native
  // trait IColorsN extends js.Object {
  //   val background: String
  //   var backgroundHovered: js.UndefOr[String] = js.undefined
  //   val backgroundActive: String
  //   val buttonColor: String
  // }

  // @js.native
  // @JSImport("@uifabric/experiments/lib/Sidebar", "SidebarDarkColors")
  // object SidebarDarkColors extends IColorsN

  // @js.native
  // @JSImport("@uifabric/experiments/lib/Sidebar", "SidebarLightColors")
  // object SidebarLightColors extends IColorsN

  @js.native
  sealed trait StylingConstants extends js.Any
  object StylingConstants {
    val sidewbarWidth = "220px".asInstanceOf[Colors]
    val sidebarCollapsedWidth = "48px".asInstanceOf[Colors]
    val sidebarIconSize = "16px".asInstanceOf[Colors]
  }

  trait ItemPropsInit extends IContextualMenuItemInit

  object ItemsPropInit {
    implicit class RichItemsPropInit(private val i: ItemPropsInit) extends AnyVal {
      def required = i.asInstanceOf[ItemProps]
    }
  }

  trait ItemProps extends IContextualMenuItem {
    var active: js.UndefOr[Boolean] = js.undefined
    var items: js.UndefOr[js.Array[ItemProps]] = js.undefined
    var buttonAs: js.UndefOr[js.Any] = js.undefined
  }

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
    var rootCallapsed: js.UndefOr[IStyle] = js.undefined
    var content: js.UndefOr[IStyle] = js.undefined
    var contentCollapsed: js.UndefOr[IStyle] = js.undefined
    var footer: js.UndefOr[IStyle] = js.undefined
  }

  object Button {
    @js.native
    @JSImport("@uifabric/experiments/lib/Sidebar", "SidebarButton")
    object JS extends ReactJSComponent

    def apply(props: components.Button.Props) = createElement0(JS, props)
  }
}
