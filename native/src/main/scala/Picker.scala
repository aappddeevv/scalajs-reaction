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

object Picker {

  @js.native
  @JSImport("react-native", "Picker")
  object JS extends ReactJSComponent {
    val Item: ReactJSComponent = js.native
  }

  def apply[T](props: Props[T])(children: ReactNode*) =
    createElementN(JS, props)(children: _*)

  trait IOS extends View.Props {
    var itemStyle: js.UndefOr[ViewStyle] = js.undefined
  }

  trait Android extends View.Props {
    var enabled: js.UndefOr[Boolean] = js.undefined
    var mode: js.UndefOr[Mode] = js.undefined
    var prompt: js.UndefOr[String] = js.undefined
  }

  trait Props[T] extends Android with IOS {
    var onValueChange: js.UndefOr[js.Function2[T, Int, Unit]] = js.undefined
    var selectedValue: js.UndefOr[T] = js.undefined
    //val style: js.UndefOr[StyleProp[ViewStyle]] = js.undefined
    //var testID: js.UndefOr[String] = js.undefined
  }

  // values can only be this...
  type ValueType = String | Int

  @js.native
  sealed trait Mode extends js.Any
  object Mode {
    val dialog = "dialog".asInstanceOf[Mode]
    val dropdown = "dropdown".asInstanceOf[Mode]
  }

  object Item {
    def apply[T](props: Props[T]) = createElement0(JS.Item, props)

    trait Props[T] extends js.Object {
      val label: String
      val value: T
    }
  }

}
