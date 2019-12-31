// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package react
package native



import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

object KeyboardAvoidingView {

  @js.native
  @JSImport("react-native", "KeyboardAvoidingView")
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    React.createElement(JS, props)(children:_*)

  trait Props extends View.Props {
    var keyboardVerticalOffset: js.UndefOr[Double] = js.undefined
    var behavior: js.UndefOr[Behavior] = js.undefined
    var contentContainerStyle: js.UndefOr[ViewStyle] = js.undefined
    var enabled: js.UndefOr[Boolean] = js.undefined
  }

}

@js.native
sealed trait Behavior extends js.Any
object Behavior {
  val height = "height".asInstanceOf[Behavior]
  val position = "position".asInstanceOf[Behavior]
  val padding = "padding".asInstanceOf[Behavior]
}
