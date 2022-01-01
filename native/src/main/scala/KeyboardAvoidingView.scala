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

object KeyboardAvoidingView {

  @js.native
  @JSImport("react-native", "KeyboardAvoidingView")
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) =
    createElementN(JS, props)(children: _*)

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
