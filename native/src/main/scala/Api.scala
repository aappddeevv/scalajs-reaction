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

import js.annotation._
import js.|

@js.native
@JSImport("react-native", "Animated")
object Animated extends js.Object {
  def decay(value: AnimatedValue | AnimatedValueXY, config: DecayConfig): CompositeAnimation = js.native
  def add(lhs: Animated, rhs: Animated): AnimatedAddition                                    = js.native
  def subtract(lhs: Animated, rhs: Animated): AnimatedSubtraction                            = js.native
  // def divide(lhs: Animated, rhs: Animated): Animated = js.native
  // def multiply(lhs: Animated, rhs: Animated): Animated = js.native
  // def modulo(lhs: Animated, rhs: Animated): Animated = js.native
  // def diffClamp(value: Animated, min: Animated, max: Animated): Animated = js.native
  // def delay(time: Double): Animated = js.native
  // def sequence(anims: js.Array[js.Object]): Unit = js.native
  // def timing()
}

@js.native
trait CompositeAnimation extends js.Object {
  def start(cb: js.UndefOr[js.Function1[EndCallback, Unit]]): Unit = js.native
  def stop(): Unit                                                 = js.native
}

@js.native
trait EndResult extends js.Object {
  var finished: Boolean
}

// opaque
@js.native
@JSImport("react-native", "Animated.Animated")
class Animated extends js.Object

@js.native
@JSImport("react-native", "Animated.AnimatedInterpolation")
class AnimatedInterpolation extends Animated

@js.native
@JSImport("react-native", "Animated.AnimatedAddition")
class AnimatedAddition extends AnimatedInterpolation

@js.native
@JSImport("react-native", "Animated.AnimatedSubtraction")
class AnimatedSubtraction extends AnimatedInterpolation

@js.native
@JSImport("react-native", "Animated.Value")
class Value(value: Double) extends Animated {
  def setValue(value: Double): Unit                                   = js.native
  def setOffset(offset: Double): Unit                                 = js.native
  def flattenOffset(): Unit                                           = js.native
  def extractOffset(): Unit                                           = js.native
  def addListener(cb: ValueListenerCb): String                        = js.native
  def removeListener(id: String): Unit                                = js.native
  def removenAllListeners(): Unit                                     = js.native
  def stopAnimation(cb: js.UndefOr[js.Function1[Double, Unit]]): Unit = js.native
  def interpolate(config: InterpolationConfig): AnimatedInterpolation = js.native
}

@js.native
trait InterpolationConfig extends js.Object {
  var inputRange: js.Array[Double]                     = js.native
  var outputRange: js.Array[Double] | js.Array[String] = js.native
  var easing: js.UndefOr[js.Function1[Double, Double]] = js.undefined
  var extrapolate: js.UndefOr[Extrapolate]             = js.undefined
  var extrapolateLeft: js.UndefOr[Extrapolate]         = js.undefined
  var extrapolateRight: js.UndefOr[Extrapolate]        = js.undefined
}

@js.native
sealed trait Extrapolate extends js.Any
object Extrapolate {
  val extend   = "extend".asInstanceOf[Extrapolate]
  val identity = "identity".asInstanceOf[Extrapolate]
  val clamp    = "clamp".asInstanceOf[Extrapolate]
}

@js.native
trait ValueListenerCbArgs extends js.Object {
  var value: Double = js.native
}

@js.native
@JSImport("react-native", "Animated.ValueXY")
class ValueXY() extends Animated {
  // add methods here :-)
}

trait DecayConfig extends AnimationConfig {
  var velocity: js.UndefOr[Double]     = js.undefined
  var deceleration: js.UndefOr[Double] = js.undefined
}

trait AnimationConfig extends js.Object {
  var isInteraction: js.UndefOr[Boolean]   = js.undefined
  var useNativeDriver: js.UndefOr[Boolean] = js.undefined
}

@js.native
trait ScaledSize extends js.Object {
  var width: Double
  var height: Double
  var scale: Double
  var fontScale: Double
}

@js.native
sealed trait Dim extends js.Any
object Dim {
  val window = "window".asInstanceOf[Dim]
  val screen = "screen".asInstanceOf[Dim]
}

@js.native
trait DimChangedCbArgs extends js.Object {
  var window: ScaledSize
  var screen: ScaledSize
}

@js.native
@JSImport("react-native", "Dimensions")
object Dimensions extends js.Object {
  def get(dim: Dim): ScaledSize = js.native
  // use t = change
  def addEventListener(t: String, handler: js.Function1[DimChangedCbArgs, Unit]): Unit    = js.native
  def removeEventListener(t: String, handler: js.Function1[DimChangedCbArgs, Unit]): Unit = js.native
}

@js.native
@JSImport("react-native", "Clipboard")
object Clipboard extends js.Object {
  // call .toFuture
  def getString(): js.Promise[String]  = js.native
  def setString(content: String): Unit = js.native
}
