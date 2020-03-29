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

object InputLabel {
  @js.native
  @JSImport("@material-ui/core/InputLabel", JSImport.Default)
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    createElement(JS, props)(children: _*)

  @js.native
  sealed trait Margin extends js.Any
  object Margin {
    val dense = "none".asInstanceOf[Margin]
  }

  @js.native
  sealed trait Variant extends js.Any
  object Variant {
    val standard = "standard".asInstanceOf[Variant]
    val outlined = "outlined".asInstanceOf[Variant]
    val filled   = "filled".asInstanceOf[Variant]
  }

  trait Props extends js.Object {
    var FormLabelClasses: js.UndefOr[js.Object] = js.undefined
    var className: js.UndefOr[String]           = js.undefined
    var classes: js.UndefOr[js.Object]          = js.undefined
    var component: js.UndefOr[js.Any]           = js.undefined
    var disableAnimation: js.UndefOr[Boolean]   = js.undefined
    var disabled: js.UndefOr[Boolean]           = js.undefined
    var error: js.UndefOr[Boolean]              = js.undefined
    var filled: js.UndefOr[Boolean]             = js.undefined
    var focused: js.UndefOr[Boolean]            = js.undefined
    var key: js.UndefOr[String]                 = js.undefined
    var margin: js.UndefOr[String]              = js.undefined
    var muiFormControl: js.UndefOr[js.Object]   = js.undefined
    var required: js.UndefOr[Boolean]           = js.undefined
    var shrink: js.UndefOr[Boolean]             = js.undefined
    var style: js.UndefOr[js.Object]            = js.undefined
    var variant: js.UndefOr[String]             = js.undefined
  }

}
