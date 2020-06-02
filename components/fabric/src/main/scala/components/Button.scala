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

object Button {

  @js.native
  trait IButton extends Focusable {
    def dismissMenu(): Unit
    def openMenu(shouldFocusOnContainer: js.UndefOr[Boolean], shouldFocusOnMount: js.UndefOr[Boolean]): Unit
  }

  trait Props
      extends ComponentRef[IButton]
      with WithIconProps
      with AllHTMLAttributes[dom.html.Button]
      with Theme
      with ReactJSProps {
    
    /** Simplest callback. () => Unit. */
    @JSName("onClick")
    var onClickSimple: js.UndefOr[js.Function0[Unit]] = js.undefined
    // having variance problems
    //dom.html.Anchor|FabricNS.BaseButton|FabricNS.Button] {
    //var href: js.UndefOr[String] = js.undefined
    var primary: js.UndefOr[Boolean] = js.undefined
    var uniqueId: js.UndefOr[String] = js.undefined
    //var disabled: js.UndefOr[Boolean] = js.undefined
    var allowDisabledFocus: js.UndefOr[Boolean] = js.undefined
    var primaryDisabled: js.UndefOr[Boolean] = js.undefined
    //var checked: js.UndefOr[Boolean] = js.undefined
    var styles: js.UndefOr[Styles] = js.undefined
    // theme
    //var checked: js.UndefOr[Boolean] = js.undefined
    var toggle: js.UndefOr[Boolean] = js.undefined
    // var className
    var ariaLabel: js.UndefOr[String] = js.undefined
    var ariaDescription: js.UndefOr[String] = js.undefined
    var ariaHidden: js.UndefOr[Boolean] = js.undefined
    var text: js.UndefOr[String] = js.undefined
    //var iconProps: js.UndefOr[IIconProps] = js.undefined
    var menuProps: js.UndefOr[IContextualMenuProps] = js.undefined
    var onAfterMenuDismiss: js.UndefOr[js.Function0[Unit]] = js.undefined
    var split: js.UndefOr[Boolean] = js.undefined
    var menuIconProps: js.UndefOr[components.Icon.Props] = js.undefined
    var splitButtonAriaLabel: js.UndefOr[String] = js.undefined
    var onMenuClick: js.UndefOr[js.Function2[SyntheticMouseEvent[dom.html.Element], js.UndefOr[Props], Unit]] =
      js.undefined
    var onRenderIcon: js.UndefOr[IRenderFunction[Props]] = js.undefined
    var onRenderText: js.UndefOr[IRenderFunction[Props]] = js.undefined
    var onRenderDescription: js.UndefOr[IRenderFunction[Props]] = js.undefined
    var onRenderAriaDescription: js.UndefOr[IRenderFunction[Props]] = js.undefined
    var onRenderChildren: js.UndefOr[IRenderFunction[Props]] = js.undefined
    var onRenderMenuIcon: js.UndefOr[IRenderFunction[Props]] = js.undefined
    var onRenderMenu: js.UndefOr[IRenderFunction[IContextualMenuProps]] = js.undefined
    var menuAs: js.UndefOr[IContextualMenuProps] = js.undefined
    var secondaryText: js.UndefOr[String] = js.undefined
    var buttonType: js.UndefOr[ButtonType] = js.undefined
    //var rootProps deprecated
    var toggled: js.UndefOr[Boolean] = js.undefined
    //var data: js.UndefOr[js.Any] = js.undefined
    var getClassNames: js.UndefOr[js.Function9[
      ITheme,
      String, // className
      String, // variant class name
      js.UndefOr[String], // icon class name
      js.UndefOr[String], // menu icon class name
      Boolean, // disabled
      Boolean, // checked
      Boolean, // expanded
      js.UndefOr[Boolean], // isSplit
      Unit
    ]] = js.undefined
    var getSplitButtonClassNames: js.UndefOr[js.Function3[Boolean, Boolean, Boolean, ISplitButtonClassNames]] =
      js.undefined
    var menuTriggerKeyCode: js.UndefOr[Int | Null] = js.undefined
    var keytipProps: js.UndefOr[IKeytipProps] = js.undefined
    var persistMenu: js.UndefOr[Boolean] = js.undefined
    var description: js.UndefOr[IStyle] = js.undefined
  }
  
  @deprecated("Use OnClick")
  type OC = OnClick
  type OnClick = MouseEventHandler[dom.html.Button]
  
  def OnClick(f: MouseEventHandler[dom.html.Button] => Unit) = 
    js.Any.fromFunction1(f).asInstanceOf[OnClick]
    
  def OnClick(f: () => Unit) = js.Any.fromFunction0(f).asInstanceOf[OnClick]

  object Default {
    @js.native
    @JSImport("office-ui-fabric-react/lib/Button", "DefaultButton")
    object JS extends ReactJSComponent

    def apply(props: Props = null)(children: ReactNode*) = createElementN(JS, props)(children: _*)
    def only(props: Props) = createElement0(JS,props)

  }

  object Primary {
    @js.native
    @JSImport("office-ui-fabric-react/lib/Button", "PrimaryButton")
    object JS extends ReactJSComponent

    def apply(props: Props = null)(children: ReactNode*) =
      createElementN(JS, props)(children: _*)
    def only(props: Props) = createElement0(JS,props)

  }

  object Action {
    @js.native
    @JSImport("office-ui-fabric-react/lib/Button", "ActionButton")
    object JS extends ReactJSComponent

    def apply(props: Props = null)(children: ReactNode*) =
      createElementN(JS, props)(children: _*)
    def only(props: Props) = createElement0(JS,props)

  }

  object CommandBar {
    @js.native
    @JSImport("office-ui-fabric-react/lib/Button", "CommandBarButton")
    object JS extends ReactJSComponent

    def apply(props: Props = null)(children: ReactNode*) =
      createElementN(JS, props)(children: _*)
    def only(props: Props) = createElement0(JS,props)
  }

  object Command {
    @js.native
    @JSImport("office-ui-fabric-react/lib/Button", "CommandButton")
    object JS extends ReactJSComponent

    def apply(props: Props = null)(children: ReactNode*) =
      createElementN(JS, props)(children: _*)
    def only(props: Props) = createElement0(JS,props)

  }

  object Compound {
    @js.native
    @JSImport("office-ui-fabric-react/lib/Button", "CompoundButton")
    object JS extends ReactJSComponent

    def apply(props: Props = null)(children: ReactNode*) =
      createElementN(JS, props)(children: _*)
    def only(props: Props) = createElement0(JS,props)
  }

  object MessageBar {
    @js.native
    @JSImport("office-ui-fabric-react/lib/Button", "MessageBarButton")
    object JS extends ReactJSComponent

    def apply(props: Props = null)(children: ReactNode*) =
      createElementN(JS, props)(children: _*)
    def only(props: Props) = createElement0(JS,props)
  }

  object Icon {
    @js.native
    @JSImport("office-ui-fabric-react/lib/Button", "IconButton")
    object JS extends ReactJSComponent

    def apply(props: Props)(children: ReactNode*) = createElementN(JS, props)(children: _*)
    def only(props: Props) = createElement0(JS, props)
  }

  trait Styles extends js.Object {
    var root: js.UndefOr[IStyle] = js.undefined
    var rootChecked: js.UndefOr[IStyle] = js.undefined
    var rootDisabled: js.UndefOr[IStyle] = js.undefined
    var rootHovered: js.UndefOr[IStyle] = js.undefined
    var rootFocused: js.UndefOr[IStyle] = js.undefined
    var rootPressed: js.UndefOr[IStyle] = js.undefined
    var rootExpanded: js.UndefOr[IStyle] = js.undefined
    var rootCheckedHovered: js.UndefOr[IStyle] = js.undefined
    var rootCheckedPressed: js.UndefOr[IStyle] = js.undefined
    var rootCheckedDisabled: js.UndefOr[IStyle] = js.undefined
    var rootExpandedHovered: js.UndefOr[IStyle] = js.undefined
    var flexContainer: js.UndefOr[IStyle] = js.undefined
    var textContainer: js.UndefOr[IStyle] = js.undefined
    var icon: js.UndefOr[IStyle] = js.undefined
    var iconHovered: js.UndefOr[IStyle] = js.undefined
    var iconPressed: js.UndefOr[IStyle] = js.undefined
    var iconExpanded: js.UndefOr[IStyle] = js.undefined
    var iconExpandedHovered: js.UndefOr[IStyle] = js.undefined
    var iconDisabled: js.UndefOr[IStyle] = js.undefined
    var iconChecked: js.UndefOr[IStyle] = js.undefined
    var label: js.UndefOr[IStyle] = js.undefined
    var labelHovered: js.UndefOr[IStyle] = js.undefined
    var labelDisabled: js.UndefOr[IStyle] = js.undefined
    var labelChecked: js.UndefOr[IStyle] = js.undefined
    var menuIcon: js.UndefOr[IStyle] = js.undefined
    var menuIconHovered: js.UndefOr[IStyle] = js.undefined
    var menuIconPressed: js.UndefOr[IStyle] = js.undefined
    var menuIconExpanded: js.UndefOr[IStyle] = js.undefined
    var menuIconExpandedHovered: js.UndefOr[IStyle] = js.undefined
    var menuIconDisabled: js.UndefOr[IStyle] = js.undefined
    var menuIconChecked: js.UndefOr[IStyle] = js.undefined
    var description: js.UndefOr[IStyle] = js.undefined
    var secondaryText: js.UndefOr[IStyle] = js.undefined
    var descriptionHovered: js.UndefOr[IStyle] = js.undefined
    var descriptionPressed: js.UndefOr[IStyle] = js.undefined
    var descriptionDisabled: js.UndefOr[IStyle] = js.undefined
    var descriptionChecked: js.UndefOr[IStyle] = js.undefined
    var screenReaderText: js.UndefOr[IStyle] = js.undefined
    var splitButtonContainer: js.UndefOr[IStyle] = js.undefined
    var splitButtonContainerHovered: js.UndefOr[IStyle] = js.undefined
    var splitButtonContainerFocused: js.UndefOr[IStyle] = js.undefined
    var splitButtonContainerChecked: js.UndefOr[IStyle] = js.undefined
    var splitButtonContainerCheckedHovered: js.UndefOr[IStyle] = js.undefined
    var splitButtonContainerDisabled: js.UndefOr[IStyle] = js.undefined
    var splitButtonDivider: js.UndefOr[IStyle] = js.undefined
    var splitButtonMenuButton: js.UndefOr[IStyle] = js.undefined
    var splitButtonMenuButtonDisabled: js.UndefOr[IStyle] = js.undefined
    var splitButtonMenuButtonChecked: js.UndefOr[IStyle] = js.undefined
    var splitButtonMenuButtonExpanded: js.UndefOr[IStyle] = js.undefined
    var splitButtonMenuIcon: js.UndefOr[IStyle] = js.undefined
    var splitButtonMenuIconDisabled: js.UndefOr[IStyle] = js.undefined
    var splitButtonFlexContainer: js.UndefOr[IStyle] = js.undefined
  }

}
@js.native
sealed trait ButtonType extends js.Any
object ButtonType {
  val normal = 0.asInstanceOf[ButtonType]
  val primary = 1.asInstanceOf[ButtonType]
  val hero = 2.asInstanceOf[ButtonType]
  val compound = 3.asInstanceOf[ButtonType]
  val command = 4.asInstanceOf[ButtonType]
  val icon = 5.asInstanceOf[ButtonType]
  val `default` = 6.asInstanceOf[ButtonType]
}

@js.native
sealed trait ElementType extends js.Any
object ElementType {
  val button = 0.asInstanceOf[ElementType]
  val anchor = 1.asInstanceOf[ElementType]
}

trait IButtonClassNames extends js.Object {
  /*
  root?: string;
  flexContainer?: string;
  textContainer?: string;
  icon?: string;
  label?: string;
  menuIcon?: string;
  description?: string;
  screenReaderText?: string;
 */
}

trait ISplitButtonClassNames extends js.Object {
  /*
    root?: string;
  icon?: string;
  splitButtonContainer?: string;
  flexContainer?: string;
  divider?: string;
 */
}
