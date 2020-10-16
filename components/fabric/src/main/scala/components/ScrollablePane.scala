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

object ScrollablePane {

  @js.native
  @JSImport("office-ui-fabric-react/lib/ScrollablePane", "ScrollablePane")
  object JS extends ReactJSComponent

  def apply(props: Props = null)(children: ReactNode*) =
    createElementN(JS, props)(children: _*)

  @js.native
  trait IScrollablePane extends js.Object {
    def forceLayoutUpdate(): Unit = js.native
    def getScrollPosition(): Double = js.native
  }

  trait Props extends HTMLAttributes[dom.html.Element] with ComponentRef[IScrollablePane] {
    val initialScrollPosition: js.UndefOr[Double] = js.undefined
    var scrollbarVisibility: js.UndefOr[Visibility] = js.undefined
    var theme: js.UndefOr[ITheme] = js.undefined
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
  }

  trait StyleProps extends js.Object {
    var className: js.UndefOr[String] = js.undefined
    var theme: js.UndefOr[ITheme] = js.undefined
    var scrollbarVisibility: js.UndefOr[Visibility] = js.undefined
  }

  trait Styles extends fabric.styling.IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
    var stickyAbove: js.UndefOr[IStyle] = js.undefined
    var stickyBelow: js.UndefOr[IStyle] = js.undefined
    var stickyBelowItems: js.UndefOr[IStyle] = js.undefined
    var contentContainer: js.UndefOr[IStyle] = js.undefined
  }

  @js.native
  sealed trait Visibility extends js.Any
  object Visibility {
    var auto = "auto".asInstanceOf[Visibility]
    var always = "always".asInstanceOf[Visibility]
  }
}
