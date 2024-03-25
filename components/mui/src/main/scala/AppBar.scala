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

package mui
package components

import scala.scalajs.js
import js.annotation._
import react._

object AppBar {

  @js.native
  @JSImport("@material-ui/core/AppBar", JSImport.Default)
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) =
    createElementN(JS, props)(children*)

  @js.native
  sealed trait Color extends js.Any
  object Color {
    val inherit = "inherit".asInstanceOf[Color]
    val primary = "primary".asInstanceOf[Color]
    val secondary = "secondary".asInstanceOf[Color]
    val default = "default".asInstanceOf[Color]
  }

  @js.native
  sealed trait Position extends js.Any
  object Position {
    val relative = "relative".asInstanceOf[Position]
    val absolute = "absolute".asInstanceOf[Position]
    val fixed = "fixed".asInstanceOf[Position]
    val sticky = "sticky".asInstanceOf[Position]
    val static = "static".asInstanceOf[Position]
  }

  trait Props extends js.Object {
    var className: js.UndefOr[String] = js.undefined
    var classes: js.UndefOr[js.Object] = js.undefined
    var color: js.UndefOr[Color] = js.undefined
    var component: js.UndefOr[js.Any] = js.undefined
    var elevation: js.UndefOr[Double] = js.undefined
    var key: js.UndefOr[String] = js.undefined
    var position: js.UndefOr[Position] = js.undefined
    var square: js.UndefOr[Boolean] = js.undefined
    var style: js.UndefOr[js.Object] = js.undefined
  }

}
