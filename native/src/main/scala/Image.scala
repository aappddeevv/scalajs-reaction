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

@js.native
sealed trait CacheControl extends js.Any
object CacheControl {
  val default = "default".asInstanceOf[CacheControl]
  val reload = "reload".asInstanceOf[CacheControl]
  val forceCache = "force-cache".asInstanceOf[CacheControl]
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
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) =
    createElementN(JS, props)(children*)

  trait IOS {}

  trait Android {}

  trait Props extends js.Object {
    var style: js.UndefOr[ImageStyle] = js.undefined
    var blurRadius: js.UndefOr[Int] = js.undefined
    var onLayout: js.UndefOr[NativeSyntheticEvent[LayoutChangeEvent] => Unit] = js.undefined
    var onLoad: js.UndefOr[() => Unit] = js.undefined
    var onLoadEnd: js.UndefOr[() => Unit] = js.undefined
    var onLoadStart: js.UndefOr[() => Unit] = js.undefined

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

@js.native
sealed trait ResizeMode extends js.Any
object ResizeMode {
  val cover = "cover".asInstanceOf[ResizeMode]
  val contain = "contain".asInstanceOf[ResizeMode]
  val stretch = "stretch".asInstanceOf[ResizeMode]
  val repeat = "repeat".asInstanceOf[ResizeMode]
  val center = "center".asInstanceOf[ResizeMode]
}

@js.native
sealed trait ImageOverflow extends js.Any
object Oveflow {
  val visible = "visible".asInstanceOf[Overflow]
  val hidden = "hidden".asInstanceOf[Overflow]
}

@js.native
sealed trait BackfaceVisibility extends js.Any
object BackfaceVisibility {
  val visible = "visible".asInstanceOf[BackfaceVisibility]
  val hiden = "hidden".asInstanceOf[BackfaceVisibility]
}

trait ImageStyle extends FlexStyle with ShadowStyleIOS with TransformsStyle {
  var resizeMode: js.UndefOr[ResizeMode] = js.undefined
  var backfaceVisibility: js.UndefOr[BackfaceVisibility] = js.undefined
  var borderBottomLeftRadius: js.UndefOr[Int] = js.undefined
  var borderBottomRightRadius: js.UndefOr[Int] = js.undefined
  var backgroundColor: js.UndefOr[String] = js.undefined
  var borderColor: js.UndefOr[String] = js.undefined
  //var borderWidth: js.UndefOr[Double] = js.undefined
  var borderRadius: js.UndefOr[Int] = js.undefined
  var borderTopLeftRadius: js.UndefOr[Double] = js.undefined
  var borderTopRightRadius: js.UndefOr[Double] = js.undefined
  //var overflow: js.UndefOr[ImageOverflow] = js.undefined
  var overlayColor: js.UndefOr[String] = js.undefined
  var tintColor: js.UndefOr[String] = js.undefined
  var opacity: js.UndefOr[Double] = js.undefined
}
