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

import native.styling._

object Text {

  @js.native
  @JSImport("react-native", "Text")
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    createElement(JS, props)(children: _*)

  trait IOS extends js.Object {
    var adjustsFontTSizeToFit: js.UndefOr[Boolean] = js.undefined
    var minimumFontTScale: js.UndefOr[Double]      = js.undefined
    var suppressHighlighting: js.UndefOr[Boolean]  = js.undefined
  }

  trait Android extends js.Object {
    var selectable: js.UndefOr[Boolean]                  = js.undefined
    var selectionColor: js.UndefOr[String]               = js.undefined
    var textBreakStrategy: js.UndefOr[TextBreakStrategy] = js.undefined
  }

  trait Props extends Android with IOS {
    var allowFontScaling: js.UndefOr[Boolean]                              = js.undefined
    var ellipsizeMode: js.UndefOr[String]                                  = js.undefined
    var lineBreakMode: js.UndefOr[String]                                  = js.undefined
    var numberOfLines: js.UndefOr[Int]                                     = js.undefined
    var onLayout: js.UndefOr[js.Function1[LayoutChangeEvent, Unit]]        = js.undefined
    var onPress: js.UndefOr[js.Function1[GestureResponderEvent, Unit]]     = js.undefined
    var onLongPress: js.UndefOr[js.Function1[GestureResponderEvent, Unit]] = js.undefined
    var style: js.UndefOr[StyleProp[TextStyle]]                            = js.undefined
    var testID: js.UndefOr[String]                                         = js.undefined
    var nativeID: js.UndefOr[String]                                       = js.undefined
  }

  def stylelist(s: StyleProp[TextStyle]*) = styling.stylelist[TextStyle](s: _*)
}

@js.native
sealed trait TextAlignVertical extends js.Any
object TextAlignVertical {
  val auto   = "auto".asInstanceOf[TextAlignVertical]
  val top    = "top".asInstanceOf[TextAlignVertical]
  val bottom = "bottom".asInstanceOf[TextAlignVertical]
  val center = "center".asInstanceOf[TextAlignVertical]
}

@js.native
sealed trait FontStyle extends js.Any
object FontStyle {
  val normal = "normal".asInstanceOf[FontStyle]
  val italic = "italic".asInstanceOf[FontStyle]
}

trait Offset extends js.Object {
  var width: js.UndefOr[Double]  = js.undefined
  var height: js.UndefOr[Double] = js.undefined
}

@js.native
sealed trait FontWeight extends js.Any
object FontWeight {
  val normal = "normal".asInstanceOf[FontWeight]
  val bold   = "bold".asInstanceOf[FontWeight]
  val w100   = "100".asInstanceOf[FontWeight]
  val w200   = "200".asInstanceOf[FontWeight]
  val w300   = "300".asInstanceOf[FontWeight]
  val w400   = "400".asInstanceOf[FontWeight]
  val w500   = "500".asInstanceOf[FontWeight]
  val w600   = "600".asInstanceOf[FontWeight]
  val w700   = "700".asInstanceOf[FontWeight]
  val w800   = "800".asInstanceOf[FontWeight]
  val w900   = "900".asInstanceOf[FontWeight]
}

@js.native
sealed trait TextAlign extends js.Any
object TextAlign {
  val auto    = "auto".asInstanceOf[TextAlign]
  val left    = "left".asInstanceOf[TextAlign]
  val right   = "right".asInstanceOf[TextAlign]
  val center  = "center".asInstanceOf[TextAlign]
  val justify = "justify".asInstanceOf[TextAlign]
}

trait TextStyleIOS extends ViewStyle {
  var letterSpacing: js.UndefOr[Double]       = js.undefined
  var textDecorationColor: js.UndefOr[String] = js.undefined
  var textDecorationStyle: js.UndefOr[String] = js.undefined
  var textTransform: js.UndefOr[String]       = js.undefined
  var wirtingDirection: js.UndefOr[String]    = js.undefined
}

trait TextStyleAndroid extends js.Object {
  var textAlignVertical: js.UndefOr[String]   = js.undefined
  var includeFontPadding: js.UndefOr[Boolean] = js.undefined
}

/** Text related. */
trait TextStyle extends TextStyleAndroid with TextStyleIOS {
  var color: js.UndefOr[String]          = js.undefined
  var fontFamily: js.UndefOr[String]     = js.undefined
  var fontSize: js.UndefOr[Double]       = js.undefined
  var fontStyle: js.UndefOr[FontStyle]   = js.undefined
  var fontWeight: js.UndefOr[FontWeight] = js.undefined
  //var letterSpacing: js.UndefOr[Double] = js.undefined
  var lineHeight: js.UndefOr[Double]         = js.undefined
  var textAlign: js.UndefOr[TextAlign]       = js.undefined
  var textDecorationLine: js.UndefOr[String] = js.undefined
  //var textDecorationStyle: js.UndefOr[String] = js.undefined
  //var textDecorationColor: js.UndefOr[String] = js.undefined
  var textShadowColor: js.UndefOr[String]  = js.undefined
  var textShadowOffset: js.UndefOr[Offset] = js.undefined
  var textShadowRadius: js.UndefOr[Double] = js.undefined
  //var testID: js.UndefOr[String] = js.undefined
}

@js.native
sealed trait TextBreakStrategy extends js.Any
object TextBreakStrategy {
  val simple      = "simple".asInstanceOf[TextBreakStrategy]
  val highQuality = "highQuality".asInstanceOf[TextBreakStrategy]
  val balanced    = "balanced".asInstanceOf[TextBreakStrategy]
}
