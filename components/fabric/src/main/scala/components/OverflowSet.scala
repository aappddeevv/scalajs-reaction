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

import org.scalajs.dom

import react._

import fabric.styling._

object OverflowSet {
  @js.native
  @JSImport("office-ui-fabric-react/lib/OverflowSet", "OverflowSet")
  object JS extends ReactJSComponent

  def apply(props: Props = null)(children: ReactNode*) =
    createElement(JS, props)(children: _*)

  @js.native
  trait IOveflowSet extends js.Object {
    var focus: js.Function1[Boolean, Unit]
    var focusElement: js.Function1[dom.html.Element, Boolean]
  }

  trait StyleProps extends js.Object {}

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle]           = js.undefined
    var item: js.UndefOr[IStyle]           = js.undefined
    var overflowButton: js.UndefOr[IStyle] = js.undefined
  }

  /** Subclass to add your own properties. */
  trait ItemProps extends js.Object {
    val key: String
    var keytipProps: js.UndefOr[IKeytipProps] = js.undefined
  }

  trait Props extends js.Object {
    var className: js.UndefOr[String]                    = js.undefined
    var items: js.UndefOr[Seq[ItemProps]]                = js.undefined
    var vertical: js.UndefOr[Boolean]                    = js.undefined
    var overflowItems: js.UndefOr[Seq[ItemProps]]        = js.undefined
    var doNotContainWithinFocusZone: js.UndefOr[Boolean] = js.undefined
    var role: js.UndefOr[String]                         = js.undefined

    /** Always make this a function. */
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
  }

}
