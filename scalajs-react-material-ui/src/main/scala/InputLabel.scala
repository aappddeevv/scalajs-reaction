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

object InputLabel {
  import ttg.react.elements.wrapJsForScala

  @JSImport("@material-ui/core/InputLabel", JSImport.Default)
  @js.native
  object InputLabelJS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    wrapJsForScala(InputLabelJS, props, children:_*)

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
    val filled = "filled".asInstanceOf[Variant]
  }
          
  trait Props extends js.Object {
    var FormLabelClasses: js.UndefOr[js.Object] = js.undefined
    var className: js.UndefOr[String] = js.undefined
    var classes: js.UndefOr[js.Object] = js.undefined
    var component: js.UndefOr[js.Any] = js.undefined
    var disableAnimation: js.UndefOr[Boolean] = js.undefined
    var disabled: js.UndefOr[Boolean] = js.undefined
    var error: js.UndefOr[Boolean] = js.undefined
    var filled: js.UndefOr[Boolean] = js.undefined
    var focused: js.UndefOr[Boolean] = js.undefined
    var key: js.UndefOr[String] = js.undefined
    var margin: js.UndefOr[String] = js.undefined
    var muiFormControl: js.UndefOr[js.Object] = js.undefined
    var required: js.UndefOr[Boolean] = js.undefined
    var shrink: js.UndefOr[Boolean] = js.undefined
    var style: js.UndefOr[js.Object] = js.undefined
    var variant: js.UndefOr[String] = js.undefined
  }

}
