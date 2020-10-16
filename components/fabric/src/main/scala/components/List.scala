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

  trait PageSpecification extends js.Object {}

  @js.native
  trait Page[T <: js.Object] extends js.Object {}

  @js.native
  trait PageProps[T <: js.Object] extends js.Object {}

  @js.native
  trait ListOnRenderRootProps[T <: js.Object] extends js.Object {}

  @js.native
  trait ListOnRenderSurfaceProps[T <: js.Object] extends js.Object {}

  @js.native
  @JSImport("office-ui-fabric-react/lib/List", "List")
  object JS extends ReactJSComponent

//   def apply[T <: js.Object](props: Props[T] = null)(children: ReactNode*) =
//     createElement(JS, props, children: _*)

  def apply[T <: js.Object](props: Props[T] = null) = createElement(JS, props)

  trait Props[T <: js.Object] extends ComponentRef[IList] {
    var className: js.UndefOr[String] = js.undefined
    var getItemCountForPage: js.UndefOr[js.Function2[Int, js.Any, Int]] = js.undefined
    var getKey: js.UndefOr[js.Function2[T, js.UndefOr[Int], String | Int] | js.Function1[T, String | Int]] =
      js.undefined
    @JSName("getKey")
    var getKeyString: js.UndefOr[js.Function2[js.UndefOr[T], Int, String]] = js.undefined
    @JSName("getKey")
    var getKeyInt: js.UndefOr[js.Function2[js.UndefOr[T], Int, Int]] = js.undefined
    var getPageHeight: js.UndefOr[js.Function3[Int, js.Any, Int, Int]] = js.undefined
    var getPageSpecification: js.UndefOr[js.Function2[Int, js.Any, PageSpecification]] = js.undefined
    var getPageSyle: js.UndefOr[js.Function1[Page[T], js.Any]] = js.undefined
    var ignoreScrollingState: js.UndefOr[Boolean] = js.undefined
    var items: js.UndefOr[js.Array[T]] = js.undefined
    var onPageAdded: js.UndefOr[js.Function1[Page[T], Unit]] = js.undefined
    var onPageRemoved: js.UndefOr[js.Function1[Page[T], Unit]] = js.undefined
    var onPagesUpdated: js.UndefOr[js.Function1[js.Array[Page[T]], Unit]] = js.undefined

    var onRenderCell: js.UndefOr[OnRenderCell[T]] = js.undefined
    var onRenderPage: js.UndefOr[IRenderFunction[PageProps[T]]] = js.undefined
    var onRenderRoot: js.UndefOr[IRenderFunction[ListOnRenderRootProps[T]]] = js.undefined
    var onRenderSurface: js.UndefOr[IRenderFunction[ListOnRenderSurfaceProps[T]]] = js.undefined
    var onShouldVirtualize: js.UndefOr[Boolean] = js.undefined
    var renderCount: js.UndefOr[Int] = js.undefined
    var renderWindowsAhead: js.UndefOr[Int] = js.undefined
    var renderWindowsBehind: js.UndefOr[Int] = js.undefined
    var role: js.UndefOr[String] = js.undefined
    var startIndex: js.UndefOr[Int] = js.undefined
    var usePageCache: js.UndefOr[Boolean] = js.undefined
    var version: js.UndefOr[js.Object] = js.undefined
  }

  type OnRenderCell[T] = js.Function3[T, Int, Boolean, react.ReactNode]

  def OnRenderCell[T](f: (T, Int, Boolean) => react.ReactNode) =
    js.Any.fromFunction3(f).asInstanceOf[OnRenderCell[T]]

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
