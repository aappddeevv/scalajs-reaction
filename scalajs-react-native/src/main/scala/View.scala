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

object View {

  @js.native
  @JSImport("react-native", "View")
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    wrapJsForScala(JS, props, children:_*)

  trait Props extends js.Object {
    var style: js.UndefOr[ViewStyle] = js.undefined
  }

}

@js.native
trait LayoutRectangle extends js.Object {
  val x: Int
  val y: Int
  val width: Int
  val height: Int
}

@js.native
trait LayoutChangeEvent extends js.Object {
  val layout: LayoutRectangle
}

trait ViewStyle extends FlexStyle {
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
