package ttg
package react
package fabric
package experiments

import scala.scalajs.js
import js.annotation._
import js.|

import elements.wrapJsForScala
import implicits._

import fabric._
import fabic.styling._

object Sidebar {

  @js.native
  @JSImport("@uifabric/experiments/lib/Sidebar", "Sidebar")
  object JS extends ReactJsComponent

  def apply(props: Props = null) = wrapJsForScala(JS, props)

  @js.native
  trait ISidebar extends js.Object {
    val toggleCollapsed: js.Function0[Unit] = js.native
    val setCollapsed: js.Function1[Boolean,Unit] = js.native
    val getCallapsed: js.Function0[Boolean] = js.native
  }

  trait Props
      extends HTMLAttributes[dom.html.Div]
      with Theme
      with ComponentRef[ISidebar] {
    var collapsible: js.UndefOr[Boolean] = js.undefined
    var onCallapseChanged: js.UndefOr[js.Function0[Unit]] = js.undefined
    var collapseButtonAriaLabel: js.UndefOr[String] = js.undefined
    var footerItems: js.UndefOr[js.Arry[ItemProps]] = js.undefined
    var items: js.UndefOr[js.Arry[ItemProps]] = js.undefined    
    var colors: js.UndefOr[Colors] = js.undefined
    var styles: js.UndefOr[Styles] = js.undefined
    var collapseButtonStyles: js.UndefOr[Button.Styles] = js.undefined
    var buttonStyles: js.UndefOr[Button.Styles] = js.undefined
    var defaultButton: js.UndefOr[js.Any] = js.undefined
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
  sealed trait StylingConstants extends js.Anyb
  object StylingConstants {
    val sidewbarWidth = "220px".asInstanceOf[Colors]
    val sidebarCollapsedWidth = "48px".asInstanceOf[Colors]
    val sidebarIconSize = "16px".asInstanceOf[Colors]
  }

  trait ItemProps extends IContextMenuItem {
    var active: js.UndefOr[Boolean] = js.undefined
    var items: js.UndefOr[js.Array[ItemProps]] = js.undefined
    var buttonAs: js.UndefOr[js.Any] = js.undefined
  }

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
    var rootCallopsed: js.UndefOr[IStyle] = js.undefined
    var content: js.UndefOr[IStyle] = js.undefined
    var contentCollapsed: js.UndefOr[IStyle] = js.undefined
    var footer: js.UndefOr[IStyle] = js.undefined
  }



}
