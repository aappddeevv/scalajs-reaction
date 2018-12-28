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

object Input {
  import ttg.react.elements.wrapJsForScala

  @js.native
  @JSImport("@material-ui/core/Input", JSImport.Default)
  object InputJS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    wrapJsForScala(InputJS, props, children:_*)

  @js.native
  sealed trait Margin extends js.Any
  object Margin {
    val dense = "done".asInstanceOf[Margin]
    val none  = "none".asInstanceOf[Margin]
  }
          
  trait Props extends js.Object {
    var autoComplete: js.UndefOr[String] = js.undefined
    var autoFocus: js.UndefOr[Boolean] = js.undefined
    var className: js.UndefOr[String] = js.undefined
    var classes: js.UndefOr[js.Object] = js.undefined
    var defaultValue: js.UndefOr[js.Any] = js.undefined
    var disableUnderline: js.UndefOr[Boolean] = js.undefined
    var disabled: js.UndefOr[Boolean] = js.undefined
    var endAdornment: js.UndefOr[ReactNode] = js.undefined
    var error: js.UndefOr[Boolean] = js.undefined
    var fullWidth: js.UndefOr[Boolean] = js.undefined
    var id: js.UndefOr[String] = js.undefined
    var inputComponent: js.UndefOr[js.Any] = js.undefined
    var inputProps: js.UndefOr[js.Object] = js.undefined
    var inputRef: js.UndefOr[js.Any] = js.undefined
    var key: js.UndefOr[String] = js.undefined
    var margin: js.UndefOr[Margin] = js.undefined
    var muiFormControl: js.UndefOr[js.Object] = js.undefined
    var multiline: js.UndefOr[Boolean] = js.undefined
    var name: js.UndefOr[String] = js.undefined
    var onBlur: js.UndefOr[FocusEventHandler[html.Input]] = js.undefined
    var onChange: js.UndefOr[ReactEventHandler[html.Input]] = js.undefined
    var onEmpty: js.UndefOr[scalajs.js.Function0[Unit]] = js.undefined
    var onFilled: js.UndefOr[scalajs.js.Function0[Unit]] = js.undefined
    var onFocus: js.UndefOr[FocusEventHandler[html.Input]] = js.undefined
    var onKeyDown: js.UndefOr[KeyboardEventHandler[html.Input]] = js.undefined
    var onKeyUp: js.UndefOr[KeyboardEventHandler[html.Input]] = js.undefined
    var placeholder: js.UndefOr[String] = js.undefined
    var readOnly: js.UndefOr[Boolean] = js.undefined
    var renderPrefix: js.UndefOr[js.Any] = js.undefined
    var required: js.UndefOr[Boolean] = js.undefined
    var rows: js.UndefOr[js.Any] = js.undefined
    var rowsMax: js.UndefOr[js.Any] = js.undefined
    var startAdornment: js.UndefOr[ReactNode] = js.undefined
    var style: js.UndefOr[js.Object] = js.undefined
    var `type`: js.UndefOr[String] = js.undefined
    var value: js.UndefOr[js.Any] = js.undefined
  }
}
