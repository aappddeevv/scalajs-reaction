// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
package materialui
package components

import scala.scalajs.js
import js.annotation._
import js.|
import org.scalajs.dom._

import vdom._

object TextField {
  import ttg.react.elements.wrapJsForScala

  @JSImport("@material-ui/core/TextField", JSImport.Default)
  @js.native
  object TextFieldJS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    wrapJsForScala(TextFieldJS, props, children:_*)

  @js.native
  sealed trait Margin extends js.Any
  object Margin {
    val none = "none".asInstanceOf[Margin]
    val dense = "none".asInstanceOf[Margin]
    val normal = "none".asInstanceOf[Margin]
  }

  @js.native
  sealed trait Variant extends js.Any
  object Variant {
    val standard = "standard".asInstanceOf[Variant]
    val outlined = "outlined".asInstanceOf[Variant]
    val filled = "filled".asInstanceOf[Variant]
  }

  trait Props extends js.Object {
    var FormHelperTextProps: js.UndefOr[js.Object] = js.undefined
    var InputLabelProps: js.UndefOr[js.Object] = js.undefined
    var InputProps: js.UndefOr[js.Object] = js.undefined
    var SelectProps: js.UndefOr[js.Object] = js.undefined
    var autoComplete: js.UndefOr[String] = js.undefined
    var autoFocus: js.UndefOr[Boolean] = js.undefined
    var className: js.UndefOr[String] = js.undefined
    var classes: js.UndefOr[js.Object] = js.undefined
    var component: js.UndefOr[js.Any] = js.undefined
    var defaultValue: js.UndefOr[js.Any] = js.undefined
    var disabled: js.UndefOr[Boolean] = js.undefined
    var error: js.UndefOr[Boolean] = js.undefined
    var fullWidth: js.UndefOr[Boolean] = js.undefined
    var helperText: js.UndefOr[ReactNode] = js.undefined
    var id: js.UndefOr[String] = js.undefined
    var inputProps: js.UndefOr[js.Object] = js.undefined
    var inputRef: js.UndefOr[js.Any] = js.undefined
    var key: js.UndefOr[String] = js.undefined
    var label: js.UndefOr[ReactNode] = js.undefined
    var margin: js.UndefOr[Margin] = js.undefined
    var multiline: js.UndefOr[Boolean] = js.undefined
    var name: js.UndefOr[String] = js.undefined
    var onBlur: js.UndefOr[FocusEventHandler[html.Input]] = js.undefined
    var onChange: js.UndefOr[scalajs.js.Function1[js.Dynamic, Unit]] = js.undefined
    var onFocus: js.UndefOr[FocusEventHandler[html.Input]] = js.undefined
    var placeholder: js.UndefOr[String] = js.undefined
    var required: js.UndefOr[Boolean] = js.undefined
    var rows: js.UndefOr[js.Any] = js.undefined
    var rowsMax: js.UndefOr[js.Any] = js.undefined
    var select: js.UndefOr[Boolean] = js.undefined
    var style: js.UndefOr[js.Object] = js.undefined
    var `type`: js.UndefOr[String] = js.undefined
    var value: js.UndefOr[String|Number] = js.undefined
    var variant: js.UndefOr[Variant] = js.undefined
  }

}
