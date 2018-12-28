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

object Button {
 import ttg.react.elements.wrapJsForScala

  @js.native
  trait IButton extends Focusable {
    def dismissMenu(): Unit
  }

  trait Props
      extends ComponentRef[IButton]
      with WithIconProps
      with AllHTMLAttributes[dom.html.Button]
      with Attributes {
    // having variance problems
    //dom.html.Anchor|FabricNS.BaseButton|FabricNS.Button] {
    //var href: js.UndefOr[String] = js.undefined
    var primary: js.UndefOr[Boolean] = js.undefined
    //var disabled: js.UndefOr[Boolean] = js.undefined
    var primaryDisabled: js.UndefOr[Boolean] = js.undefined
    //var checked: js.UndefOr[Boolean] = js.undefined
    var ariaLabel: js.UndefOr[String]         = js.undefined
    var ariaDescription: js.UndefOr[String]   = js.undefined
    var ariaHidden: js.UndefOr[Boolean]       = js.undefined
    var text: js.UndefOr[String]              = js.undefined
    var split: js.UndefOr[Boolean]            = js.undefined
    var menuIconProps: js.UndefOr[IIconProps] = js.undefined
    var onMenuClick: js.UndefOr[
      js.Function2[SyntheticMouseEvent[dom.html.Element], js.UndefOr[Props], Unit]] =
      js.undefined
    var onRenderIcon: js.UndefOr[IRenderFunction[Props]]            = js.undefined
    var onRenderText: js.UndefOr[IRenderFunction[Props]]            = js.undefined
    var onRenderDescription: js.UndefOr[IRenderFunction[Props]]     = js.undefined
    var onRenderAriaDescription: js.UndefOr[IRenderFunction[Props]] = js.undefined
    var onRenderChildren: js.UndefOr[IRenderFunction[Props]]        = js.undefined
    var onRenderMenuIcon: js.UndefOr[IRenderFunction[Props]]        = js.undefined
    var onRenderMenu: js.UndefOr[IRenderFunction[IContextualMenuProps]]    = js.undefined
    var description: js.UndefOr[String]                                    = js.undefined
    var toggled: js.UndefOr[Boolean]                                       = js.undefined
    // note scala.Any not js.Any
    //var data: js.UndefOr[scala.Any] = js.undefined
  }

  object Default {
    @js.native
    @JSImport("office-ui-fabric-react/lib/Button", "DefaultButton")
    object JS extends ReactJsComponent

    def apply(props: Props = null)(children: ReactNode*) =
      wrapJsForScala(JS, props, children: _*)
  }

  object Primary {
    @js.native
    @JSImport("office-ui-fabric-react/lib/Button", "PrimaryButton")
    object JS extends ReactJsComponent

    def apply(props: Props = null)(children: ReactNode*) =
      wrapJsForScala(JS, props, children: _*)
  }

  object IconButton {
    @js.native
    @JSImport("office-ui-fabric-react/lib/Button", "IconButton")
    object JS extends ReactJsComponent

    def apply(props: Props = null)(children: ReactNode*) =
      wrapJsForScala(JS, props, children: _*)
  }

}
