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

import vdom._

object PivotItem {

  @js.native
  trait PivotItemLike extends js.Object {
    val props: Props
  }

  @js.native
  @JSImport("office-ui-fabric-react/lib/Pivot", "PivotItem")
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) =
    createElementN(JS, props)(children: _*)

  trait Props extends HTMLAttributes[dom.html.Div] with MaybeHasStrKey {
    var headerText: js.UndefOr[String] = js.undefined
    var itemKey: js.UndefOr[String] = js.undefined
    var headerButtonProps: js.UndefOr[js.Object] = js.undefined
    var itemCount: js.UndefOr[Int] = js.undefined
    var itemIcon: js.UndefOr[String] = js.undefined
    var ariaLabel: js.UndefOr[String] = js.undefined
    var onRenderItemLink: js.UndefOr[IRenderFunction[Props]] = js.undefined
    var keytipProps: js.UndefOr[IKeytipProps] = js.undefined
  }

}
