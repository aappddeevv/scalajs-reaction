// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package native

import react.elements._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

object VirtualizedList {

  @js.native
  @JSImport("react-native", "VirtualizedList")
  object JS extends ReactJsComponent

  def apply[T](props: Props[T] = null) =
    React.createElement0(JS, props)

  trait Props[T] extends ScrollView.Props {
    var data: Seq[T]
    var debug: js.UndefOr[Boolean] = js.undefined
    var disableVirtualization: js.UndefOr[Boolean] = js.undefined
    var extraData: js.UndefOr[Object] = js.undefined
    var getItem: js.UndefOr[js.Function2[T, Int, js.Any]] = js.undefined
    var getItemLayout: js.UndefOr[(js.Object, Int) => ItemLayout] = js.undefined
    var getItemCount: js.UndefOr[js.Function1[js.Any, Int]] = js.undefined
    var initialScrollIndex: js.UndefOr[Int] = js.undefined
    var inverted: js.UndefOr[Boolean] = js.undefined
    var keyExtractor: js.UndefOr[js.Function2[T, Int, String]] = js.undefined
    var ListEmptyComponent: js.UndefOr[ReactType] = js.undefined
    var ListFooterComponent: js.UndefOr[ReactType] = js.undefined
    var ListHeaderComponent: js.UndefOr[ReactType] = js.undefined
    var progressViewOffset: js.UndefOr[Double] = js.undefined
    //var horizontal: js.UndefOr[Boolean] = js.undefined
    var initialNumToRender: js.UndefOr[Int] = js.undefined
    var onEndReachedThreshold: js.UndefOr[Double] = js.undefined
    var onEndReached: js.UndefOr[OnEndReachedEvent => Unit] = js.undefined
    var onRefresh: js.UndefOr[() => Unit] = js.undefined
    var onViewableItemsChanged: js.UndefOr[ViewableItemsChangedEvent[js.Object] => Unit] = js.undefined
    var refreshing: js.UndefOr[Boolean] = js.undefined
    var renderItem: RenderItemInfo[Object] => ReactElement
    //var removeClippedSubviews: js.UndefOr[Boolean] = js.undefined
    var viewabiltyConfig: js.UndefOr[ViewabilityConfig] = js.undefined
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

