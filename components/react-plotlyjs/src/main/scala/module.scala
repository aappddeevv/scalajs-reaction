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

package react_plotlyjs

import scala.scalajs.js
import js.annotation._
import js.|
import org.scalajs.dom
import react.vdom._
import react._

import plotlyjs._

object Plot {

  @js.native
  @JSImport("react-plotly.js", JSImport.Default)
  object JS extends ReactJSComponent

  def apply(props: Props) = createElement0(JS, props)

  /** Call back type, opaque. See `OnCallback`. */
  trait CallbackFn extends js.Object

  def OnCallback(f: (js.Object, dom.html.Div) => Unit) =
    js.Any.fromFunction2(f)

  trait PropsBase extends js.Object {
    var key: js.UndefOr[String] = js.undefined
    var className: js.UndefOr[String] = js.undefined
    var config: js.UndefOr[Config] = js.undefined

    var debug: js.UndefOr[Boolean] = js.undefined
    var divId: js.UndefOr[String] = js.undefined
    var frames: js.UndefOr[js.Array[Frame]] = js.undefined
    var layout: js.UndefOr[Layout] = js.undefined
    var revision: js.UndefOr[Int] = js.undefined
    var useResizeHandler: js.UndefOr[Boolean] = js.undefined

    var onInitialized: js.UndefOr[CallbackFn] = js.undefined
    var onUpdate: js.UndefOr[CallbackFn] = js.undefined

    var onError: js.UndefOr[js.Function1[js.Error, Unit]] = js.undefined

    // events
    var onAfterExport: js.UndefOr[js.Function0[Unit]] = js.undefined
    var onAfterPlot: js.UndefOr[js.Function0[Unit]] = js.undefined
    var onAnimated: js.UndefOr[js.Function0[Unit]] = js.undefined
    //onAnimatingFrame
    var onAnimationInterrupted: js.UndefOr[js.Function0[Unit]] = js.undefined
    var onAutoSize: js.UndefOr[js.Function0[Unit]] = js.undefined
    var onBeforeExport: js.UndefOr[js.Function0[Unit]] = js.undefined

    var onDeselect: js.UndefOr[js.Function0[Unit]] = js.undefined
    var onDoubleClick: js.UndefOr[js.Function0[Unit]] = js.undefined
    var onFramework: js.UndefOr[js.Function0[Unit]] = js.undefined

    var onRedraw: js.UndefOr[js.Function0[Unit]] = js.undefined

    var onTransitioning: js.UndefOr[js.Function0[Unit]] = js.undefined
    var onTransitionInterrupted: js.UndefOr[js.Function0[Unit]] = js.undefined

    var style: js.UndefOr[StyleAttr] = js.undefined
  }

  trait PropsInit extends PropsBase {
    var data: js.UndefOr[js.Array[Trace]] = js.undefined
  }

  object PropsInit {
    implicit class RichPropsInit(private val pi: PropsInit) extends AnyVal {
      def hasRequired = pi.asInstanceOf[Props]
    }
  }

  trait Props extends PropsBase {
     val data: js.Array[Trace]
  }
}
