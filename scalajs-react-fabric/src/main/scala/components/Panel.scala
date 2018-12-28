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

object Panel {
  import ttg.react.elements.wrapJsForScala

  @js.native
  @JSImport("office-ui-fabric-react/lib/Panel", "Panel")
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    wrapJsForScala(JS, props, children: _*)

  @js.native
  trait IPanel extends js.Object {
    def open(): Unit = js.native
    def dismiss(ev: ReactKeyboardEvent[dom.html.Element]): Unit = js.native
  }

  trait StyleProps extends js.Object {
    var className: js.UndefOr[String] = js.undefined
    var isOpen: js.UndefOr[Boolean] = js.undefined
    var isAnimating: js.UndefOr[Boolean] = js.undefined
    var isOnRightSide: js.UndefOr[Boolean] = js.undefined
    var isHiddenOnDismiss: js.UndefOr[Boolean] = js.undefined
    var isFooterAtBottom: js.UndefOr[Boolean] = js.undefined
    var isFooterSticky: js.UndefOr[Boolean] = js.undefined
    var hasCloseButton: js.UndefOr[Boolean] = js.undefined
    var `type`: js.UndefOr[PanelType] = js.undefined
    var headerClassName: js.UndefOr[String] = js.undefined
  }

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
    var overlay: js.UndefOr[IStyle] = js.undefined
    var hiddenPanel: js.UndefOr[IStyle] = js.undefined
    var main: js.UndefOr[IStyle] = js.undefined
    var commands: js.UndefOr[IStyle] = js.undefined
    var contentInner: js.UndefOr[IStyle] = js.undefined
    var scrollableContent: js.UndefOr[IStyle] = js.undefined
    var navigation: js.UndefOr[IStyle] = js.undefined
    var closeButton: js.UndefOr[IStyle] = js.undefined
    var header: js.UndefOr[IStyle] = js.undefined
    var headerText: js.UndefOr[IStyle] = js.undefined
    var content: js.UndefOr[IStyle] = js.undefined
    var footer: js.UndefOr[IStyle] = js.undefined
    var footerInner: js.UndefOr[IStyle] = js.undefined
  }

  @js.native
  abstract sealed trait PanelType extends js.Any
  object PanelType {
    val smallFluid = 0.asInstanceOf[PanelType]
    val smallFixedFar = 1.asInstanceOf[PanelType]
    val smallFixedNear = 2.asInstanceOf[PanelType]
    val medium = 3.asInstanceOf[PanelType]
    val large = 4.asInstanceOf[PanelType]
    val largeFixed = 5.asInstanceOf[PanelType]
    val extraLarge = 6.asInstanceOf[PanelType]
    val custom = 7.asInstanceOf[PanelType]
  }

  trait Props extends ComponentRef[IPanel] {
    var isOpen: js.UndefOr[Boolean] = js.undefined
    var hasCloseButton: js.UndefOr[Boolean] = js.undefined
    var isLightDismiss: js.UndefOr[Boolean] = js.undefined
    var isHiddenOnDismiss: js.UndefOr[Boolean] = js.undefined
    var isBlocking: js.UndefOr[Boolean] = js.undefined
    var isFooterAtBottom: js.UndefOr[Boolean] = js.undefined
    var headerText: js.UndefOr[String] = js.undefined
    var onDismss: js.UndefOr[js.Function1[ReactEvent[dom.html.Element], Unit]] = js.undefined
    var onDismissed: js.UndefOr[js.Function0[Unit]] = js.undefined
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
    var theme: js.UndefOr[ITheme] = js.undefined
    var `type`: js.UndefOr[PanelType] = js.undefined
    var className: js.UndefOr[String] = js.undefined
    var customWidth: js.UndefOr[String] = js.undefined
    var closeButtonAriaLabel: js.UndefOr[String] = js.undefined
    var headerClassName: js.UndefOr[String] = js.undefined
    var elementToFocusOnDismiss: js.UndefOr[dom.html.Element] = js.undefined
    var layerProps: js.UndefOr[Layer.Props] = js.undefined
    var onLightDismissClick: js.UndefOr[js.Function0[Unit]] = js.undefined
    var onOuterClick: js.UndefOr[js.Function0[Unit]] = js.undefined

    var onRenderNavigation: js.UndefOr[IRenderFunction[Props]] = js.undefined
    /** This is not quite right. See API docs. */
    var onRenderHeader: js.UndefOr[IRenderFunction[Props]] = js.undefined
    var onRenderBody: js.UndefOr[IRenderFunction[Props]] = js.undefined
    var onRenderFooter: js.UndefOr[IRenderFunction[Props]] = js.undefined
    var onRenderFooterContent: js.UndefOr[IRenderFunction[Props]] = js.undefined
  }

}

