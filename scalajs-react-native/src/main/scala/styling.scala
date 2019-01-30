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

object styling {
  /** Tag for returning from StyleSheet.create */
  trait StyleSetTag extends js.Object

  type Style = ViewStyle | js.Object | js.Dynamic

  type StyleSet = js.Dictionary[Style]

  object styleset {
    @inline def apply(stylePairs: (String, Style)*): StyleSet =
      js.Dictionary[Style](stylePairs:_*)
  }
}

@js.native
trait AbsoluteFillStyle extends js.Object {
  val position: js.UndefOr[String] = js.undefined
  val left: js.UndefOr[Int] = js.undefined
  val right: js.UndefOr[Int] = js.undefined
  val top: js.UndefOr[Int] = js.undefined
  val bottom: js.UndefOr[Int] = js.undefined
}

@js.native
@JSImport("react-native", "StyleSheet")
object StyleSheet extends js.Object {
  def create[T <: styling.StyleSetTag](styles: styling.StyleSet): T = js.native

  val hairlineWidth: Double = js.native
  val absoluteFillObject: AbsoluteFillStyle = js.native
  // val absoluteFile: = js.native
}
