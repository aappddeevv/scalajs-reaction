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

@js.native
trait NativeSyntheticEvent[T <: js.Object] extends js.Object {
  var nativeEvent: T
}

@js.native
sealed trait CacheControl extends js.Any
object CacheControl {
  val default = "default".asInstanceOf[CacheControl]
  val reload ="reload".asInstanceOf[CacheControl]
  val forceCache ="force-cache".asInstanceOf[CacheControl]
  val onlyIfCached = "only-if-cached".asInstanceOf[CacheControl]
}

trait ImageURISource extends js.Object {
  var uri: js.UndefOr[String] = js.undefined
  var bundle: js.UndefOr[String] = js.undefined
  var method: js.UndefOr[String] = js.undefined
  var headers: js.UndefOr[js.Object] = js.undefined
  var body: js.UndefOr[String] = js.undefined
  var cache: js.UndefOr[CacheControl] = js.undefined
  var width: js.UndefOr[Int] = js.undefined
  var height: js.UndefOr[Int] = js.undefined
  var scale: js.UndefOr[Double] = js.undefined
}

@js.native
trait ImageErrorEvent extends js.Object {
  var error: js.Error
}

@js.native
trait ImageProgressEvent extends js.Object {
  var loaded: Int
  var total: Int
}

@js.native
sealed trait ResizeMethod extends js.Any
object ResizeMethod {
  val auto = "auto".asInstanceOf[ResizeMethod]
  val resize = "resize".asInstanceOf[ResizeMethod]
  val scale = "scale".asInstanceOf[ResizeMethod]  
}

object Image {

  @js.native
  @JSImport("react-native", "Image")
  object JS extends ReactJsComponent

  def apply(props: Props = null) = wrapJsForScala(JS, props)

  trait Props extends js.Object {
    var style: js.UndefOr[ViewStyle] = js.undefined
    var blurRadius: js.UndefOr[Int] = js.undefined
    var onLayout: js.UndefOr[NativeSyntheticEvent[LayoutChangeEvent] => Unit] = js.undefined
    var onLoad: js.UndefOr[() => Unit] = js.undefined
    var onLoadEnd: js.UndefOr[() => Unit] = js.undefined
    var onLoadStart: js.UndefOr[() => Unit] = js.undefined
    var resizeMode: js.UndefOr[String] = js.undefined
    var source: js.UndefOr[ImageURISource | Int | Seq[ImageURISource]] = js.undefined
    var loadingIndicatorSource: js.UndefOr[Seq[ImageURISource | Int]] = js.undefined
    var onError: js.UndefOr[NativeSyntheticEvent[ImageErrorEvent] => Unit] = js.undefined
    var testID: js.UndefOr[String] = js.undefined
    var resizeMethod: js.UndefOr[ResizeMethod] = js.undefined
    var accessibilityLabel: js.UndefOr[ReactElement] = js.undefined
    var accessible: js.UndefOr[Boolean] = js.undefined
    var capInsets: js.UndefOr[BoundingBox] = js.undefined
    var defaultSource: js.UndefOr[js.Object | Int] = js.undefined
    var onPartialLoad: js.UndefOr[() => Unit] = js.undefined
    //var onProgress: js.UndefOr[NativeSyntheticEvent[ImageProgressEvent] => Unit] = js.undefined
  }

}
