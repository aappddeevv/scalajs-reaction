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

import org.scalajs.dom._

import react._

import vdom._

object Tabs {

  @js.native
  @JSImport("@material-ui/core/Tabs", JSImport.Default)
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    createElement(JS, props)(children: _*)

  @js.native
  sealed trait IndicatorColor extends js.Any
  object IndicatorColor {
    val primary   = "primary".asInstanceOf[IndicatorColor]
    val secondary = "secondary".asInstanceOf[IndicatorColor]
  }

  @js.native
  sealed trait ScrollButtons extends js.Any
  object ScrollButtons {
    var auto = "auto".asInstanceOf[ScrollButtons]
    var on   = "on".asInstanceOf[ScrollButtons]
    var off  = "off".asInstanceOf[ScrollButtons]
  }

  trait Props extends js.Object {
    var action: js.UndefOr[js.Any]                                            = js.undefined
    var centered: js.UndefOr[Boolean]                                         = js.undefined
    var classes: js.UndefOr[js.Object]                                        = js.undefined
    var component: js.UndefOr[String]                                         = js.undefined
    var fullWidth: js.UndefOr[Boolean]                                        = js.undefined
    var indicatorColor: js.UndefOr[IndicatorColor]                            = js.undefined
    var onChange: js.UndefOr[js.Function2[ReactEvent[org.scalajs.dom.html.Input], Int, Unit]] = js.undefined
    var scrollable: js.UndefOr[Boolean]                                       = js.undefined
    var scrollButtons: js.UndefOr[ScrollButtons]                              = js.undefined
    var textColor: js.UndefOr[Tab.TextColor]                                  = js.undefined
    var value: js.Any // can be false
  }
}
