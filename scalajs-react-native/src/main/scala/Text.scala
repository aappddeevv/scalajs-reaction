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
import js.Dynamic.{literal => jsobj}

object Text {

  @js.native
  @JSImport("react-native", "Text")
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    wrapJsForScala(JS, props, children:_*)

  trait Props extends js.Object {
    var style: js.UndefOr[TextStyle] = js.undefined
  }
}

@js.native
sealed trait TextAlignVertical extends js.Any
object TextAlignVertical {
  val auto = "auto".asInstanceOf[TextAlignVertical]
  val top = "top".asInstanceOf[TextAlignVertical]
  val bottom = "bottom".asInstanceOf[TextAlignVertical]
  val center = "center".asInstanceOf[TextAlignVertical]  
}

trait TextStyleIOS extends js.Object {
  var letterSpacing: js.UndefOr[Double] = js.undefined
}

trait TextStyleAndroid extends js.Object {
  var textAlignVertical: js.UndefOr[TextAlignVertical] = js.undefined
  var includeFontPadding: js.UndefOr[Boolean] = js.undefined
}

@js.native
sealed trait FontStyle extends js.Any
object FontStyle {
  val normal = "normal".asInstanceOf[FontStyle]
  val italic = "italic".asInstanceOf[FontStyle]  
}

trait TextOffset extends js.Object {
  var width: js.UndefOr[Double] = js.undefined
  var height: js.UndefOr[Double] = js.undefined
}

@js.native
sealed trait FontWeight extends js.Any
object FontWeight {
  val normal = "normal".asInstanceOf[FontWeight]
  val bold = "bold".asInstanceOf[FontWeight]  
  val w100 = "100".asInstanceOf[FontWeight]
  val w200 = "200".asInstanceOf[FontWeight]
  val w300 = "300".asInstanceOf[FontWeight]
  val w400 = "400".asInstanceOf[FontWeight]
  val w500 = "500".asInstanceOf[FontWeight]
  val w600 = "600".asInstanceOf[FontWeight]
  val w700 = "700".asInstanceOf[FontWeight]
  val w800 = "800".asInstanceOf[FontWeight]
  val w900 = "900".asInstanceOf[FontWeight]
}

@js.native
sealed trait TextAlign extends js.Any
object TextAlign {
  val auto = "auto".asInstanceOf[TextAlign]
  val left = "left".asInstanceOf[TextAlign]
  val right = "right".asInstanceOf[TextAlign]
  val center = "center".asInstanceOf[TextAlign]
  val justify = "justify".asInstanceOf[TextAlign]
}

trait TextStyle
    extends TextStyleAndroid
    with TextStyleIOS
    with ViewStyle {
  var color: js.UndefOr[String] = js.undefined
  var fontFamily: js.UndefOr[String] = js.undefined
  var fontSize: js.UndefOr[Double] = js.undefined
  var fontStyle: js.UndefOr[FontStyle] = js.undefined
  var fontWeight: js.UndefOr[FontWeight] = js.undefined
  //var letterSpacing: js.UndefOr[Double] = js.undefined
  var lineHeight: js.UndefOr[Double] = js.undefined
  var textAlign: js.UndefOr[TextAlign] = js.undefined
  var textDecorationLine: js.UndefOr[String] = js.undefined
  var textDecorationStyle: js.UndefOr[String] = js.undefined
  var textDecorationColor: js.UndefOr[String] = js.undefined
  var textShadowColor: js.UndefOr[String] = js.undefined
  var textShadowOffset: js.UndefOr[TextOffset] = js.undefined
  var textShadowRadius: js.UndefOr[Double] = js.undefined  
}
