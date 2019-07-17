// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package native

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import react.elements._
import native.styling._

object View {

  @js.native
  @JSImport("react-native", "View")
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    React.createElement(JS, props)(children:_*)

  trait IOS extends js.Object {
    var accessibilityViewIsModel: js.UndefOr[Boolean] = js.undefined
    var accessibilityActions: js.UndefOr[js.Array[String]] = js.undefined
    var onAccessibilityAction: js.UndefOr[js.Function0[Unit]] = js.undefined
    var shouldRasterizeIOS: js.UndefOr[Boolean] = js.undefined
  }

  trait Android extends js.Object {
    var collapsable: js.UndefOr[Boolean] = js.undefined
    var needsOffscreenAlphaCompositing: js.UndefOr[Boolean] = js.undefined
    var renderToHardwareTextureAndroid: js.UndefOr[Boolean] = js.undefined
  }

  trait Props
      extends IOS
      with Android
      with AccessibilityProps {
    var hitSlop: js.UndefOr[Insets] = js.undefined
    var onLayout: js.UndefOr[js.Function1[LayoutChangeEvent, Unit]] = js.undefined
    var pointerEvents: js.UndefOr[PointerEvents] = js.undefined
    var removeClippedSubviews: js.UndefOr[Boolean] = js.undefined
    var style: js.UndefOr[StyleProp[ViewStyle]] = js.undefined
    var testID: js.UndefOr[String] = js.undefined
  }

  def stylelist(s: StyleProp[ViewStyle]*) = styling.stylelist[ViewStyle](s:_*)
}

trait Insets extends js.Object {
  var top, let, bottom, right: js.UndefOr[Int] = js.undefined
}

@js.native
sealed trait PointerEvents extends js.Any
object PointerEvents {
  val boxNone = "box-none".asInstanceOf[PointerEvents]
  val none = "none".asInstanceOf[PointerEvents]
  val boxOnly = "boxonly".asInstanceOf[PointerEvents]
  val autuo = "auto".asInstanceOf[PointerEvents]
}

@js.native
trait LayoutRectangle extends js.Object {
  val x: Int
  val y: Int
  val width: Int
  val height: Int
}

@js.native
trait NativeLayoutChangeEvent extends js.Object {
  var layout: LayoutRectangle
}

@js.native
trait LayoutChangeEvent extends js.Object {
  var nativeEvent: NativeLayoutChangeEvent
}

trait ViewStyle
    extends FlexStyle
    with ShadowStyleIOS
    with TransformsStyle {
  //var backfaceVisibility
  var backgroundColor: js.UndefOr[String] = js.undefined
  var borderBottomColor: js.UndefOr[String] = js.undefined
  var borderBottomEndRadius: js.UndefOr[Double] = js.undefined
  var borderBottomLeftRadius: js.UndefOr[Double] = js.undefined
  var borderBottoRightRadius: js.UndefOr[Double] = js.undefined
  var borderBottomStartRadius: js.UndefOr[Double] = js.undefined
  //var borderBottomWidth: js.UndefOr[Double] = js.undefined
  var borderColor: js.UndefOr[String] = js.undefined
  var borderEndColor: js.UndefOr[String] = js.undefined
  var borderLeftColor: js.UndefOr[String] = js.undefined
  //var borderLeftWidth: js.UndefOr[Double] = js.undefined
  var borderRadius: js.UndefOr[Double] = js.undefined
  var borderRightColor: js.UndefOr[String] = js.undefined
  //var borderRightWidth: js.UndefOr[Double] = js.undefined
  var borderStartColor: js.UndefOr[String] = js.undefined
  //var borderStyle
  var borderTopColor: js.UndefOr[String] = js.undefined
  var borderTopEndRadius: js.UndefOr[Double] = js.undefined
  var borderTopLeftRadius: js.UndefOr[Double] = js.undefined
  var borderTopRightRadius: js.UndefOr[Double] = js.undefined
  var borderTopStartRadius: js.UndefOr[Double] = js.undefined
  //var borderTopWidth: js.UndefOr[Double] = js.undefined
  //var borderWidth: js.UndefOr[Double] = js.undefined
  var opacity: js.UndefOr[Double] = js.undefined
  var testID: js.UndefOr[String] = js.undefined  
}
