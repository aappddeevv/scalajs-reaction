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

object List {

  @js.native
  @JSImport("office-ui-fabric-react/lib/List", "List")
  object JS extends ReactJSComponent

  def apply[T <: js.Object](props: Props[T] = null)(children: ReactNode*) =
    createElementN(JS, props)(children: _*)

  trait Props[T <: js.Object] extends IViewportProps with ComponentRef[IList] {

    var getKey: js.UndefOr[js.Function2[T, js.UndefOr[Int], String | Int] | js.Function1[T, String | Int]] =
      js.undefined
    val items: js.Array[T]
    var setKey: js.UndefOr[String] = js.undefined
    var className: js.UndefOr[String] = js.undefined
    var initialFocusedIndex: js.UndefOr[Int] = js.undefined
    // do the groups thing
    var selection: js.UndefOr[ISelection[T]] = js.undefined
    var selectionMode: js.UndefOr[SelectionMode] = js.undefined
    var selectionPreservedOnEmptyClick: js.UndefOr[Boolean] = js.undefined
    var layoutMode: js.UndefOr[Int] = js.undefined
    // checkbox visibility....
    var isHeaderVisible: js.UndefOr[Boolean] = js.undefined
    var columns: js.UndefOr[js.Array[IColumn] | js.Array[js.Object] | js.Array[js.Dynamic]] =
      js.undefined
    var constrainMode: js.UndefOr[Int] = js.undefined

    var onItemInvoked: js.UndefOr[OII[T]] = js.undefined

    var onRenderRow: js.UndefOr[IRenderFunction[Details.Row.Props]] = js.undefined
    var onRenderMissingItem: js.UndefOr[js.Function1[Int, js.Any]] = js.undefined
    var onRenderDetailsHeader: js.UndefOr[IRenderFunction[Details.Header.Props]] = js.undefined

    /** You can still be "active" but not deselected. */
    var onActiveItemChanged: js.UndefOr[OAIC[T]] = js.undefined

    var onColumnHeaderClick: js.UndefOr[OCHC[T]] = js.undefined

    var maximumPixelsForDrag: js.UndefOr[Int] = js.undefined
    var compact: js.UndefOr[Boolean] = js.undefined
    var checkboxCellClassName: js.UndefOr[String] = js.undefined
    var enterModelSelectionOnTouch: js.UndefOr[Boolean] = js.undefined

    var usePageCache: js.UndefOr[Boolean] = js.undefined
    var renderCount: js.UndefOr[Int] = js.undefined
  }

  def onItemInvoked[T <: js.Object](f: (T, Int, ReactFocusEvent[dom.html.Element]) => Unit): OII[T] =
    js.Any.fromFunction3(f)

  type OII[T <: js.Object] = js.Function3[T, Int, ReactFocusEvent[dom.html.Element], Unit]

  def onActiveItemChanged[T <: js.Object](f: (T, Int, ReactFocusEvent[dom.html.Element]) => Unit): OAIC[T] =
    js.Any.fromFunction3(f)

  type OAIC[T <: js.Object] = js.Function3[T, Int, ReactFocusEvent[dom.html.Element], Unit]

  def onColumnHeaderClick[T <: js.Object](f: (ReactMouseEvent[dom.html.Element], IColumn) => Unit): OCHC[T] =
    js.Any.fromFunction2(f)

  type OCHC[T <: js.Object] =
    js.Function2[ReactMouseEvent[dom.html.Element], IColumn, Unit]

  @js.native
  trait IList extends js.Object {
    def forceUpdate(): Unit = js.native
    def scrollToIndex(
        index: Int,
        measureItem: js.UndefOr[js.Function1[Int, Int]] = js.undefined,
        scrollToMode: js.UndefOr[ScrollToMode] = js.undefined
      ): Unit = js.native
    def getTotalListHeight(): Int = js.native
    def getStartItemIndexInView(): Int = js.native
  }

  @js.native
  sealed trait ScrollToMode extends js.Any
  object ScrollToMode {
    var auto = 0.asInstanceOf[ScrollToMode]
    var top = 1.asInstanceOf[ScrollToMode]
    var bottom = 2.asInstanceOf[ScrollToMode]
    var center = 3.asInstanceOf[ScrollToMode]
  }

}
