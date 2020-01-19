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

package react
package native

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object FlatList {

  @js.native
  @JSImport("react-native", "FlatList")
  object JS extends ReactJsComponent

  def apply[T](props: Props[T] = null) =
    createElement0(JS, props)

  trait Props[T] extends VirtualizedList.Props[T] {
    var ItemSeparatorComponent: js.UndefOr[ReactType] = js.undefined
    // view style?
    var columnWrapperStyle: js.UndefOr[js.Object] = js.undefined
    var numColumns: js.UndefOr[Int]               = js.undefined
    var legacyImplementation: js.UndefOr[Boolean] = js.undefined
  }

}

trait ViewabilityConfig extends js.Object {
  var minimumViewTime: js.UndefOr[Double]                  = js.undefined
  var viewAreaCoveragePercentThreshold: js.UndefOr[Double] = js.undefined
  var itemVisiblePercentThreshold: js.UndefOr[Double]      = js.undefined
  var waitForInteraction: js.UndefOr[Double]               = js.undefined
}

trait Separators extends js.Object {
  var highlight: () => Unit
  var unhighlight: () => Unit
  var updateProps: (String, js.Object) => ReactJsComponent
}

trait ItemLayout extends js.Object {
  var length: Int
  var offset: Int
  var index: Int
}

trait OnEndReachedInfo extends js.Object {
  var distanceFromEnd: Int
}

trait OnEndReachedEvent extends js.Object {
  var info: OnEndReachedInfo
}

trait Section[T] extends js.Object {}

trait ViewToken[T] extends js.Object {
  var item: T
  var key: String
  var index: js.UndefOr[Int] = js.undefined
  var isViewable: Boolean
  var section: js.UndefOr[Section[T]] = js.undefined
}

trait ViewableItemsChangedInfo[T] extends js.Object {
  var viewableItems: Seq[js.Object]
  var changed: Seq[js.Object]
}

trait ViewableItemsChangedEvent[T] extends js.Object {
  var info: ViewableItemsChangedInfo[T]
}

trait ScrollToEnd extends js.Object {
  var animated: js.UndefOr[Boolean] = js.undefined
}

trait ScrollToIndex extends js.Object {
  var index: Int
  var viewOffset: Int
  var animated: js.UndefOr[Boolean]    = js.undefined
  var viewPosition: js.UndefOr[Double] = js.undefined
}

trait ScrollToItem[T] extends js.Object {
  var item: T
  var animated: js.UndefOr[Boolean]    = js.undefined
  var viewPosition: js.UndefOr[Double] = js.undefined
}

trait ScrollToOffset extends js.Object {
  var offset: Int
  var animated: js.UndefOr[Boolean] = js.undefined
}

trait RenderItemInfo[T] extends js.Object {
  var item: T
  var index: Int
  var separators: Separators
}

@js.native
trait IFlatList[T] extends js.Object {
  def scrollToEnd(): Unit                          = js.native
  def scrollToEnd(params: ScrollToEnd): Unit       = js.native
  def scrollToIndex(params: ScrollToIndex): Unit   = js.native
  def scrollToItem(params: ScrollToItem[T]): Unit  = js.native
  def scrollToOffset(params: ScrollToOffset): Unit = js.native
  def recordInteraction(): Unit                    = js.native
  def flashScrollIndicators(): Unit                = js.native
}
