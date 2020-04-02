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
import js.|

import org.scalajs.dom._

import react._

import vdom._

object Radio {

  @js.native
  @JSImport("@material-ui/core/Radio", JSImport.Default)
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    createElementN(JS, props)(children: _*)

  @js.native
  sealed trait Color extends js.Any
  object Color {
    val primary   = "primary".asInstanceOf[Color]
    val secondary = "secondary".asInstanceOf[Color]
    val default   = "defaulut".asInstanceOf[Color]
  }

  trait Props extends js.Object {
    var checked: js.UndefOr[Boolean | String]                                     = js.undefined
    var checkedIcon: js.UndefOr[ReactNode]                                        = js.undefined
    var classes: js.UndefOr[js.Object]                                            = js.undefined
    var color: js.UndefOr[Color]                                                  = js.undefined
    var disableRipple: js.UndefOr[Boolean]                                        = js.undefined
    var disabled: js.UndefOr[Boolean]                                             = js.undefined
    var icon: js.UndefOr[ReactNode]                                               = js.undefined
    var id: js.UndefOr[String]                                                    = js.undefined
    var inputProps: js.UndefOr[js.Object]                                         = js.undefined
    var inputRef: js.UndefOr[js.Any]                                              = js.undefined
    var key: js.UndefOr[String]                                                   = js.undefined
    var onChange: js.UndefOr[js.Function2[ReactEvent[org.scalajs.dom.html.Input], Boolean, Unit]] = js.undefined
    var style: js.UndefOr[js.Object]                                              = js.undefined
    var `type`: js.UndefOr[String]                                                = js.undefined
    var value: js.UndefOr[js.Any]                                                 = js.undefined
  }

}
