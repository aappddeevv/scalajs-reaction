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

object VirtualizedList {

  @js.native
  @JSImport("react-native", "VirtualizedList")
  object JS extends ReactJSComponent

  def apply[T](props: Props[T] = null) =
    createElement0(JS, props)

  trait Props[T] extends ScrollView.Props {
    var data: Seq[T]
    var debug: js.UndefOr[Boolean]                                = js.undefined
    var disableVirtualization: js.UndefOr[Boolean]                = js.undefined
    var extraData: js.UndefOr[Object]                             = js.undefined
    var getItem: js.UndefOr[js.Function2[T, Int, js.Any]]         = js.undefined
    var getItemLayout: js.UndefOr[(js.Object, Int) => ItemLayout] = js.undefined
    var getItemCount: js.UndefOr[js.Function1[js.Any, Int]]       = js.undefined
    var initialScrollIndex: js.UndefOr[Int]                       = js.undefined
    var inverted: js.UndefOr[Boolean]                             = js.undefined
    var keyExtractor: js.UndefOr[js.Function2[T, Int, String]]    = js.undefined
    var ListEmptyComponent: js.UndefOr[ReactType]                 = js.undefined
    var ListFooterComponent: js.UndefOr[ReactType]                = js.undefined
    var ListHeaderComponent: js.UndefOr[ReactType]                = js.undefined
    var progressViewOffset: js.UndefOr[Double]                    = js.undefined
    //var horizontal: js.UndefOr[Boolean] = js.undefined
    var initialNumToRender: js.UndefOr[Int]                                              = js.undefined
    var onEndReachedThreshold: js.UndefOr[Double]                                        = js.undefined
    var onEndReached: js.UndefOr[OnEndReachedEvent => Unit]                              = js.undefined
    var onRefresh: js.UndefOr[() => Unit]                                                = js.undefined
    var onViewableItemsChanged: js.UndefOr[ViewableItemsChangedEvent[js.Object] => Unit] = js.undefined
    var refreshing: js.UndefOr[Boolean]                                                  = js.undefined
    var renderItem: RenderItemInfo[Object] => ReactElement
    //var removeClippedSubviews: js.UndefOr[Boolean] = js.undefined
    var viewabiltyConfig: js.UndefOr[ViewabilityConfig]                 = js.undefined
    var viewabilityConfigCallbackPairs: js.UndefOr[js.Array[js.Object]] = js.undefined

    /*
CellRendererComponent
onLayout
onScrollToIndexFailed
renderScrollComponent
initialNumToRender
maxToRenderPerBatch
updateCellsBatchingPeriod
windowSize
progressViewOffset
   */
  }
}
