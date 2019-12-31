// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package react
package native
package sideswipe

import scala.scalajs.js
import js.annotation._
import js.|


import native.styling._

object SideSwipe {

  @js.native
  @JSImport("react-native-sideswipe", JSImport.Default)
  object JS extends ReactJsComponent

  def apply[T](props: Props[T] = null) = React.createElement0(JS, props)

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
    var shouldCapture: js.UndefOr[js.Function1[GestureState,Boolean]] = js.undefined
    var shouldRelease: js.UndefOr[js.Function1[GestureState,Boolean]] = js.undefined
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
