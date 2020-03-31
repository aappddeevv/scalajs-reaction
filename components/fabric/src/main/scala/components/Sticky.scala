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

package fabric
package components

import scala.scalajs.js

import js.annotation._

import react._

object Sticky {
  @js.native
  @JSImport("office-ui-fabric-react/lib/Sticky", "Sticky")
  object JS extends ReactJSComponent

  def apply(props: Props = null)(children: ReactNode*) =
    createElementN(JS, props)(children: _*)

  @js.native
  trait ISticky extends js.Object

  trait Props extends ComponentRef[ISticky] {
    var stickyClassName: js.UndefOr[String]       = js.undefined
    var stickyBackgroundColor: js.UndefOr[String] = js.undefined
    var stickyPosition: js.UndefOr[Position]      = js.undefined
    var isScrollSynced: js.UndefOr[Boolean]       = js.undefined
  }

  @js.native
  abstract trait Position extends js.Any
  object Position {
    val Both   = 0.asInstanceOf[Position]
    val Header = 1.asInstanceOf[Position]
    val Footer = 2.asInstanceOf[Position]
  }

}
