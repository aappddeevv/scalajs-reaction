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

package react_native_sideswipe

import scala.scalajs.js

import js.annotation._

import react._

import native._
import native.styling._

object SideSwipe {

  @js.native
  @JSImport("react-native-sideswipe", JSImport.Default)
  object JS extends ReactJSComponent

  def apply[T](props: Props[T] = null) = createElement0(JS, props)

  trait Props[T] extends js.Object {
    var contentContainerStyle: js.UndefOr[StyleProp[ViewStyle]] = js.undefined
    var contentOffset: js.UndefOr[Int] = js.undefined
    val data: js.Array[T]
    var keyExtractor: js.UndefOr[js.Function2[T, Int, String]] = js.undefined
    var flatListStyle: js.UndefOr[StyleProp[ViewStyle]] = js.undefined
    var index: js.UndefOr[Int] = js.undefined
    var itemWidth: js.UndefOr[Double] = js.undefined
    var onEndReached: js.UndefOr[js.Function0[Unit]] = js.undefined
    var onEndReachedThreshold: js.UndefOr[Int] = js.undefined
    var onIndexChange: js.UndefOr[js.Function1[Int, Unit]] = js.undefined
    var threshold: js.UndefOr[Int] = js.undefined
    var useVelocityforIndex: js.UndefOr[Boolean] = js.undefined
    // array of elements, single element or null
    val renderItem: js.Function1[CarouselRenderProps[T], ReactNode]
    var shouldCapture: js.UndefOr[js.Function1[GestureState, Boolean]] = js.undefined
    var shouldRelease: js.UndefOr[js.Function1[GestureState, Boolean]] = js.undefined
    var style: js.UndefOr[StyleProp[ViewStyle]] = js.undefined
    var useNativeDriver: js.UndefOr[Boolean] = js.undefined
  }
}

trait GestureState extends js.Object {
  var stateID: Int
  var moveX: Int
  var moveY: Int
  var x0, y0, dx, dy, vx, vy: Int
  var numberActiveToucuhes: Int
}

trait CarouselRenderProps[T] extends js.Object {
  var itemIndex: Int
  var currentIndex: Int
  var itemCount: Int
  var item: T
  var animatedValue: Value
}
