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

object Panel {
  @js.native
  @JSImport("office-ui-fabric-react/lib/Panel", "Panel")
  object JS extends ReactJSComponent

  def apply(props: Props = null)(children: ReactNode*) =
    createElementN(JS, props)(children: _*)

  @js.native
  trait IPanel extends js.Object {
    def open(): Unit                                            = js.native
    def dismiss(ev: ReactKeyboardEvent[dom.html.Element]): Unit = js.native
  }

  trait StyleProps extends js.Object {
    var className: js.UndefOr[String]          = js.undefined
    var isOpen: js.UndefOr[Boolean]            = js.undefined
    var isAnimating: js.UndefOr[Boolean]       = js.undefined
    var isOnRightSide: js.UndefOr[Boolean]     = js.undefined
    var isHiddenOnDismiss: js.UndefOr[Boolean] = js.undefined
    var isFooterAtBottom: js.UndefOr[Boolean]  = js.undefined
    var isFooterSticky: js.UndefOr[Boolean]    = js.undefined
    var hasCloseButton: js.UndefOr[Boolean]    = js.undefined
    var `type`: js.UndefOr[Type]               = js.undefined
    var headerClassName: js.UndefOr[String]    = js.undefined
  }

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle]              = js.undefined
    var overlay: js.UndefOr[IStyle]           = js.undefined
    var hiddenPanel: js.UndefOr[IStyle]       = js.undefined
    var main: js.UndefOr[IStyle]              = js.undefined
    var commands: js.UndefOr[IStyle]          = js.undefined
    var contentInner: js.UndefOr[IStyle]      = js.undefined
    var scrollableContent: js.UndefOr[IStyle] = js.undefined
    var navigation: js.UndefOr[IStyle]        = js.undefined
    var closeButton: js.UndefOr[IStyle]       = js.undefined
    var header: js.UndefOr[IStyle]            = js.undefined
    var headerText: js.UndefOr[IStyle]        = js.undefined
    var content: js.UndefOr[IStyle]           = js.undefined
    var footer: js.UndefOr[IStyle]            = js.undefined
    var footerInner: js.UndefOr[IStyle]       = js.undefined
  }

  @js.native
  abstract sealed trait Type extends js.Any
  object Type {
    val smallFluid     = 0.asInstanceOf[Type]
    val smallFixedFar  = 1.asInstanceOf[Type]
    val smallFixedNear = 2.asInstanceOf[Type]
    val medium         = 3.asInstanceOf[Type]
    val large          = 4.asInstanceOf[Type]
    val largeFixed     = 5.asInstanceOf[Type]
    val extraLarge     = 6.asInstanceOf[Type]
    val custom         = 7.asInstanceOf[Type]
  }

  trait Props extends ComponentRef[IPanel] with Theme {
    var isOpen: js.UndefOr[Boolean]                                             = js.undefined
    var allowTouchBodyScroll: js.UndefOr[Boolean]                               = js.undefined
    var focusTrapZoneProps: js.UndefOr[FocusTrapZone.Props]                     = js.undefined
    var hasCloseButton: js.UndefOr[Boolean]                                     = js.undefined
    var isLightDismiss: js.UndefOr[Boolean]                                     = js.undefined
    var isHiddenOnDismiss: js.UndefOr[Boolean]                                  = js.undefined
    var isBlocking: js.UndefOr[Boolean]                                         = js.undefined
    var isFooterAtBottom: js.UndefOr[Boolean]                                   = js.undefined
    var headerText: js.UndefOr[String]                                          = js.undefined
    var onDismiss: js.UndefOr[OnDismiss] = js.undefined
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]]          = js.undefined
    var `type`: js.UndefOr[Type]                                                = js.undefined
    var className: js.UndefOr[String]                                           = js.undefined
    var customWidth: js.UndefOr[String]                                         = js.undefined
    var closeButtonAriaLabel: js.UndefOr[String]                                = js.undefined
    var headerClassName: js.UndefOr[String]                                     = js.undefined
    var elementToFocusOnDismiss: js.UndefOr[dom.html.Element]                   = js.undefined
    var layerProps: js.UndefOr[Layer.Props]                                     = js.undefined
    var overlapProps: js.UndefOr[Overlay.Props]                                 = js.undefined
    var onLightDismissClick: js.UndefOr[js.Function0[Unit]]                     = js.undefined

    var onOpen: js.UndefOr[js.Function0[Unit]]                 = js.undefined
    var onOpened: js.UndefOr[js.Function0[Unit]]               = js.undefined
    var onOuterClick: js.UndefOr[js.Function0[Unit]]           = js.undefined
    var onRenderNavigation: js.UndefOr[IRenderFunction[Props]] = js.undefined

    /** This is not quite right. See API docs. */
    var onRenderHeader: js.UndefOr[IRenderFunction[Props]]        = js.undefined
    var onRenderBody: js.UndefOr[IRenderFunction[Props]]          = js.undefined
    var onRenderFooter: js.UndefOr[IRenderFunction[Props]]        = js.undefined
    var onRenderFooterContent: js.UndefOr[IRenderFunction[Props]] = js.undefined
  }
  
  type OnDismiss = js.Function1[ReactEvent[dom.html.Element], Unit]
  def OnDismiss(f: () => Unit) = js.Any.fromFunction0(f).asInstanceOf[OnDismiss]
  def OnDismiss(f: js.Function0[Unit]) = f.asInstanceOf[OnDismiss]
  def OnDismiss(f: ReactEvent[dom.html.Element] => Unit) =
    js.Any.fromFunction1(f).asInstanceOf[OnDismiss]

}
