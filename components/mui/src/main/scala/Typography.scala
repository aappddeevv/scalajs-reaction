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

import react._

object Typography {

  @js.native
  @JSImport("@material-ui/core/Typography", JSImport.Default)
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    createElementN(JS, props)(children: _*)

  @js.native
  sealed trait Align extends js.Any
  object Align {
    val center  = "center".asInstanceOf[Align]
    val left    = "left".asInstanceOf[Align]
    val justify = "justify".asInstanceOf[Align]
    val right   = "right".asInstanceOf[Align]
    val inherit = "inherit".asInstanceOf[Align]
  }

  @js.native
  sealed trait Color extends js.Any
  object Color {
    val secondary     = "secondary".asInstanceOf[Color]
    val textPrimary   = "textPrimary".asInstanceOf[Color]
    val primary       = "primary".asInstanceOf[Color]
    val default       = "default".asInstanceOf[Color]
    val error         = "error".asInstanceOf[Color]
    val inherit       = "inherit".asInstanceOf[Color]
    val textSecondary = "textSecondary".asInstanceOf[Color]
  }

  @js.native
  sealed trait Variant extends js.Any
  object Variant {
    val h5         = "h5".asInstanceOf[Variant]
    val srOnly     = "srOnly".asInstanceOf[Variant]
    val button     = "button".asInstanceOf[Variant]
    val h2         = "h2".asInstanceOf[Variant]
    val display2   = "display2".asInstanceOf[Variant]
    val h6         = "h6".asInstanceOf[Variant]
    val display3   = "display3".asInstanceOf[Variant]
    val display1   = "display1".asInstanceOf[Variant]
    val title      = "title".asInstanceOf[Variant]
    val inherit    = "inherit".asInstanceOf[Variant]
    val h1         = "h1".asInstanceOf[Variant]
    val subtitle1  = "subtitle1".asInstanceOf[Variant]
    val body1      = "body1".asInstanceOf[Variant]
    val subheading = "subheading".asInstanceOf[Variant]
    val headline   = "headline".asInstanceOf[Variant]
    val display4   = "display4".asInstanceOf[Variant]
    val subtitle2  = "subtitle2".asInstanceOf[Variant]
    val body2      = "body2".asInstanceOf[Variant]
    val h3         = "h3".asInstanceOf[Variant]
    val caption    = "caption".asInstanceOf[Variant]
    val overline   = "overline".asInstanceOf[Variant]
    val h4         = "h4".asInstanceOf[Variant]
  }

  trait Props extends js.Object {
    var align: js.UndefOr[Align]                         = js.undefined
    var className: js.UndefOr[String]                    = js.undefined
    var classes: js.UndefOr[js.Object]                   = js.undefined
    var color: js.UndefOr[Color]                         = js.undefined
    var component: js.UndefOr[String | ReactJsComponent] = js.undefined
    var gutterBottom: js.UndefOr[Boolean]                = js.undefined
    var headlineMapping: js.UndefOr[js.Object]           = js.undefined
    var internalDeprecatedVariant: js.UndefOr[Boolean]   = js.undefined
    var key: js.UndefOr[String]                          = js.undefined
    var noWrap: js.UndefOr[Boolean]                      = js.undefined
    var paragraph: js.UndefOr[Boolean]                   = js.undefined
    var style: js.UndefOr[js.Object]                     = js.undefined
    var variant: js.UndefOr[Variant]                     = js.undefined
  }
}
