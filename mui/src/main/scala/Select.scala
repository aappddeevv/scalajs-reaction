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

object Select {

  @js.native
  @JSImport("@material-ui/core/Select", JSImport.Default)
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    createElement(JS, props)(children: _*)

  @js.native
  sealed trait Margin extends js.Any
  object Margin {
    val dense = "dense".asInstanceOf[Margin]
    val none  = "none".asInstanceOf[Margin]
  }

  @js.native
  sealed trait Variant extends js.Any
  object Variant {
    val standard = "standard".asInstanceOf[Variant]
    val outlined = "outlined".asInstanceOf[Variant]
    val filled   = "filled".asInstanceOf[Variant]
  }

  trait Props extends js.Object {
    var IconComponent: js.UndefOr[js.Any]                       = js.undefined
    var MenuProps: js.UndefOr[js.Object]                        = js.undefined
    var SelectDisplayProps: js.UndefOr[js.Object]               = js.undefined
    var autoComplete: js.UndefOr[String]                        = js.undefined
    var autoFocus: js.UndefOr[Boolean]                          = js.undefined
    var autoWidth: js.UndefOr[Boolean]                          = js.undefined
    var className: js.UndefOr[String]                           = js.undefined
    var classes: js.UndefOr[js.Object]                          = js.undefined
    var defaultValue: js.UndefOr[js.Any]                        = js.undefined
    var disableUnderline: js.UndefOr[Boolean]                   = js.undefined
    var disabled: js.UndefOr[Boolean]                           = js.undefined
    var displayEmpty: js.UndefOr[Boolean]                       = js.undefined
    var endAdornment: js.UndefOr[ReactNode]                     = js.undefined
    var error: js.UndefOr[Boolean]                              = js.undefined
    var fullWidth: js.UndefOr[Boolean]                          = js.undefined
    var id: js.UndefOr[String]                                  = js.undefined
    var input: js.UndefOr[ReactElement]                         = js.undefined
    var inputComponent: js.UndefOr[js.Any]                      = js.undefined
    var inputProps: js.UndefOr[js.Object]                       = js.undefined
    var inputRef: js.UndefOr[js.Any]                            = js.undefined
    var key: js.UndefOr[String]                                 = js.undefined
    var margin: js.UndefOr[Margin]                              = js.undefined
    var muiFormControl: js.UndefOr[js.Object]                   = js.undefined
    var multiline: js.UndefOr[Boolean]                          = js.undefined
    var multiple: js.UndefOr[Boolean]                           = js.undefined
    var name: js.UndefOr[String]                                = js.undefined
    var native: js.UndefOr[Boolean]                             = js.undefined
    var onBlur: js.UndefOr[FocusEventHandler[html.Input]]       = js.undefined
    var onChange: js.UndefOr[ReactEventHandler[html.Input]]     = js.undefined
    var onClose: js.UndefOr[scalajs.js.Function0[Unit]]         = js.undefined
    var onEmpty: js.UndefOr[scalajs.js.Function0[Unit]]         = js.undefined
    var onFilled: js.UndefOr[scalajs.js.Function0[Unit]]        = js.undefined
    var onFocus: js.UndefOr[FocusEventHandler[html.Input]]      = js.undefined
    var onKeyDown: js.UndefOr[KeyboardEventHandler[html.Input]] = js.undefined
    var onKeyUp: js.UndefOr[KeyboardEventHandler[html.Input]]   = js.undefined
    var onOpen: js.UndefOr[scalajs.js.Function0[Unit]]          = js.undefined
    var open: js.UndefOr[Boolean]                               = js.undefined
    var placeholder: js.UndefOr[String]                         = js.undefined
    var readOnly: js.UndefOr[Boolean]                           = js.undefined
    var renderPrefix: js.UndefOr[js.Any]                        = js.undefined
    var renderValue: js.UndefOr[js.Any]                         = js.undefined
    var required: js.UndefOr[Boolean]                           = js.undefined
    var rows: js.UndefOr[js.Any]                                = js.undefined
    var rowsMax: js.UndefOr[js.Any]                             = js.undefined
    var startAdornment: js.UndefOr[ReactNode]                   = js.undefined
    var style: js.UndefOr[js.Object]                            = js.undefined
    var `type`: js.UndefOr[String]                              = js.undefined
    var value: js.UndefOr[js.Any]                               = js.undefined
    var variant: js.UndefOr[Variant]                            = js.undefined
  }
}
