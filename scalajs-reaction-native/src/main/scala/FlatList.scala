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

object FlatList {

  @js.native
  @JSImport("react-native", "FlatList")
  object JS extends ReactJsComponent

  def apply[T](props: Props[T] = null) =
    React.createElement0(JS, props)

  trait Props[T] extends VirtualizedList.Props[T] {
    var ItemSeparatorComponent: js.UndefOr[ReactType] = js.undefined
    // view style?
    var columnWrapperStyle: js.UndefOr[js.Object] = js.undefined
    var numColumns: js.UndefOr[Int] = js.undefined
    var legacyImplementation: js.UndefOr[Boolean] = js.undefined
  }

}

trait ViewabilityConfig extends js.Object {
  var minimumViewTime: js.UndefOr[Double] = js.undefined
  var viewAreaCoveragePercentThreshold: js.UndefOr[Double] = js.undefined
  var itemVisiblePercentThreshold: js.UndefOr[Double] = js.undefined
  var waitForInteraction: js.UndefOr[Double] = js.undefined
}

trait  Separators extends js.Object {
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

trait Section[T] extends js.Object {
}

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
  var animated: js.UndefOr[Boolean] = js.undefined
  var viewPosition: js.UndefOr[Double] = js.undefined
}

trait ScrollToItem[T] extends js.Object {
  var item: T
  var animated: js.UndefOr[Boolean] = js.undefined
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
  def scrollToEnd(): Unit = js.native
  def scrollToEnd(params: ScrollToEnd): Unit = js.native
  def scrollToIndex(params: ScrollToIndex): Unit = js.native
  def scrollToItem(params: ScrollToItem[T]): Unit = js.native
  def scrollToOffset(params: ScrollToOffset): Unit = js.native
  def recordInteraction(): Unit = js.native
  def flashScrollIndicators(): Unit = js.native
}
