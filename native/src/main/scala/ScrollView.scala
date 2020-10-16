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
import scala.scalajs.js.|

import native.styling._

object ScrollView {

  @js.native
  @JSImport("react-native", "ScrollView")
  object JS extends ReactJSComponent

  def apply(props: Props = null)(children: ReactNode*) =
    createElementN(JS, props)(children: _*)

  trait IOS extends js.Object {
    var alwaysBounceVertical: js.UndefOr[Boolean] = js.undefined
    var alwaysBounceHorizontal: js.UndefOr[Boolean] = js.undefined
    var automaticallyAdjustContentInsets: js.UndefOr[Boolean] = js.undefined
    var bounces: js.UndefOr[Boolean] = js.undefined
    var bouncesZoom: js.UndefOr[Boolean] = js.undefined
    var canCancelContentTouches: js.UndefOr[Boolean] = js.undefined
    var centerContent: js.UndefOr[Boolean] = js.undefined
    var contentOffset: js.UndefOr[ContentOffset] = js.undefined
    var decelerationRate: js.UndefOr[String | Double] = js.undefined
    var directionalLockEnabled: js.UndefOr[Boolean] = js.undefined
    var onScrollAnimatedEnabled: js.UndefOr[js.Function0[Unit]] = js.undefined
    var pinchGestureEnabled: js.UndefOr[Boolean] = js.undefined
    var scrollIndicatorInsets: js.UndefOr[BoundingBox] = js.undefined
    var scrollsToTop: js.UndefOr[Boolean] = js.undefined
    var snapToAlignment: js.UndefOr[String] = js.undefined
    var snapToInterval: js.UndefOr[Double] = js.undefined
    var stickyHeaderIndices: js.UndefOr[Seq[Int]] = js.undefined
    var zoomScale: js.UndefOr[Double] = js.undefined
  }

  trait Android extends js.Object {
    var endFillColor: js.UndefOr[String] = js.undefined
    var scrollPerfTag: js.UndefOr[String] = js.undefined
    var overScrollMode: js.UndefOr[String] = js.undefined
    var nestedScrollEnabled: js.UndefOr[Boolean] = js.undefined
  }

  trait Props extends IOS with Android {
    var contentInset: js.UndefOr[BoundingBox] = js.undefined
    var contentInsetAdjustmentBehavior: js.UndefOr[String] = js.undefined
    var contentContainerStyle: js.UndefOr[StyleProp[ViewStyle]] = js.undefined
    var horizontal: js.UndefOr[Boolean] = js.undefined
    var indicatorStyle: js.UndefOr[String] = js.undefined
    var keyboardDismissMode: js.UndefOr[String] = js.undefined
    var keyboardShouldPersistTaps: js.UndefOr[String] = js.undefined
    var maximumZoomScale: js.UndefOr[Double] = js.undefined
    var minimumZoomScale: js.UndefOr[Double] = js.undefined
    var onContentSizeChange: js.UndefOr[(Int, Int) => Unit] = js.undefined
    var onMomentumScrollBegin: js.UndefOr[() => Unit] = js.undefined
    var onMomentumScrollEnd: js.UndefOr[() => Unit] = js.undefined
    var onScroll: js.UndefOr[() => Unit] = js.undefined
    var onScrollBeginDrag: js.UndefOr[() => Unit] = js.undefined
    var onScrollEndDrag: js.UndefOr[() => Unit] = js.undefined
    var pagingEnabled: js.UndefOr[Boolean] = js.undefined
    var refreshControl: js.UndefOr[ReactElement] = js.undefined
    var removeClippedSubviews: js.UndefOr[Boolean] = js.undefined
    var scrollEnabled: js.UndefOr[Boolean] = js.undefined
    var scrollEventThrottle: js.UndefOr[Int] = js.undefined
    var showsHorizontalScrollIndicator: js.UndefOr[Boolean] = js.undefined
    var showsVerticalScrollIndicator: js.UndefOr[Boolean] = js.undefined
    var style: js.UndefOr[StyleProp[ViewStyle]] = js.undefined
  }

  def stylelist(s: StyleProp[ViewStyle]*) = styling.stylelist(s: _*)
}

trait BoundingBox extends js.Object {
  var top: Double
  var left: Double
  var bottom: Double
  var right: Double
}
