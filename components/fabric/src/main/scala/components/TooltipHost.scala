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
import js.|
import js.annotation._
import org.scalajs.dom
import react._
import vdom._
import fabric.styling._

object Tooltip {

  @js.native
  @JSImport("office-ui-fabric-react/lib/components/Tooltip", "Tooltip")
  object JS extends ReactJSComponent
 
  def apply(props: Props)(children: ReactNode*) =
    createElementN(JS, props)(children:_*)

  @js.native
  trait ITooltip extends js.Object

  trait Props extends ComponentRef[ITooltip] {
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
    var theme: js.UndefOr[ITheme] = js.undefined
    var content: js.UndefOr[String|ReactNode] = js.undefined
    var onRenderContent: js.UndefOr[IRenderFunction[Props]] = js.undefined
    var delay: js.UndefOr[TooltipDelay] = js.undefined
    var maxWidth: js.UndefOr[String] = js.undefined
    var targetElement: js.UndefOr[dom.html.Element] = js.undefined
    var directionalHint: js.UndefOr[DirectionalHint] = js.undefined
    var directionalHintForRTL: js.UndefOr[DirectionalHint] = js.undefined
  }

  @js.native
  abstract trait TooltipDelay extends js.Any 
  object TooltipDelay {
    val zero = 0.asInstanceOf[TooltipDelay]
    val medium = 1.asInstanceOf[TooltipDelay]
    val long = 2.asInstanceOf[TooltipDelay]
  }

  trait StyleProps extends js.Object {
    var theme: js.UndefOr[ITheme] = js.undefined
    var className: js.UndefOr[String] = js.undefined
    var delay: js.UndefOr[TooltipDelay] = js.undefined
    var maxWidth: js.UndefOr[String] = js.undefined
    var gapSpace:  js.UndefOr[Int] = js.undefined
    var beakWidth:  js.UndefOr[Int] = js.undefined
  }

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
    var content: js.UndefOr[IStyle] = js.undefined
    var subText: js.UndefOr[IStyle] = js.undefined
  }


  object Host {
    @js.native
    trait ITooltipHost extends js.Object {
      def show(): Unit = js.native
      def dismiss(): Unit = js.native
    }

    @js.native
    abstract trait OverflowMode extends js.Any
    object OverflowMode {
      val Parent = "Parent".asInstanceOf[OverflowMode]
      val Self = "Self".asInstanceOf[OverflowMode]
    }

    trait Props extends ReactJSProps with ComponentRef[ITooltipHost] with HTMLAttributes[dom.html.Div] {

      var calloutProps: js.UndefOr[CalloutProps] = js.undefined
      var closeDelay: js.UndefOr[Int] = js.undefined
      var content: js.UndefOr[String|ReactNode] = js.undefined
      var delay: js.UndefOr[TooltipDelay] = js.undefined
      var directionalHint: js.UndefOr[DirectionalHint] = js.undefined
      var directionalHintForRTL: js.UndefOr[DirectionalHint] = js.undefined
      //var className
      //var className
      var hostClassName: js.UndefOr[String] = js.undefined
      var overflowMode: js.UndefOr[OverflowMode] = js.undefined
      //var tooltipProps
      //setAriaDescribedBy
      //id
      var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
      var theme: js.UndefOr[ITheme] = js.undefined
      var onTooltipToggle: js.UndefOr[js.Function1[Boolean,Unit]] = js.undefined
    }

    @js.native
    @JSImport("office-ui-fabric-react/lib/components/Tooltip", "TooltipHost")
    object JS extends ReactJSComponent

    def apply(props: Props)(children: ReactNode*) =
      createElementN(JS, props)(children:_*)

    trait StyleProps extends js.Object {
      var theme: js.UndefOr[ITheme] = js.undefined
      var className: js.UndefOr[String] = js.undefined
    }

    trait Styles extends IStyleSetTag {
      var root: js.UndefOr[IStyle] = js.undefined
    }
  }
}
