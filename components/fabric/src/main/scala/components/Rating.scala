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
import js.annotation.*
import org.scalajs.dom
import react.*
import react.syntax.*
import react.conversions.*
import fabric.styling.*
import vdom.*

object Rating {

  @js.native
  abstract trait Size extends js.Any
  object Size {
    val Large = 1.asInstanceOf[Size]
    val Small = 0.asInstanceOf[Size]
  }

  @js.native
  trait IRating extends js.Object

  @js.native
  @JSImport("office-ui-fabric-react/lib", "Rating")
  object JS extends ReactJSComponent

  def apply(props: Props) = createElement(JS, props)

  trait Props extends ComponentRef[IRating] with AllHTMLAttributes[dom.html.Element] {
    var rating: js.UndefOr[Double] = js.undefined
    //var min: js.UndefOr[Double] = js.undefined
    //var max: js.UndefOr[Double] = js.undefined
    var allowZeroStars: js.UndefOr[Boolean] = js.undefined
    var icon: js.UndefOr[String] = js.undefined
    var unselectedIcon: js.UndefOr[String] = js.undefined
    @JSName("size")
    var ratingSize: js.UndefOr[Size] = js.undefined
    var onChange: js.UndefOr[js.Function2[ReactFocusEvent[dom.html.Element], Double, Unit]] = js.undefined
    var ariaLabelFormat: js.UndefOr[String] = js.undefined
    var ariaLabelId: js.UndefOr[String] = js.undefined
    var getAriaLabel: js.UndefOr[js.Function2[Double, Double, String]] = js.undefined
    //var readOnly: js.UndefOr[Boolean] = js.undefined
    //var className: js.UndefOr[String] = js.undefined
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
  }

  trait StyleProps extends js.Object {
    var disabled: js.UndefOr[Boolean] = js.undefined
    var readOnly: js.UndefOr[Boolean] = js.undefined
  }

  /*@deriveClassNames */trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
    // lots more
  }
  @js.native
  trait ClassNames extends IClassNamesTag { 
    var root: String= js.native
  }
}
