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

package react
package bootstrap

import scala.scalajs.js

import js.annotation._
import js.|

@js.native
@JSImport("react-bootstrap", JSImport.Namespace)
object BootstrapNS extends js.Object {
  val Button: ReactJSComponent = js.native
  val ButtonToolbar: ReactJSComponent = js.native
  val ButtonGroup: ReactJSComponent = js.native
  val ToggleButtonGroup: ReactJSComponent = js.native
  val ToggleButton: ReactJSComponent = js.native

  val FormControl: ReactJSComponent = js.native
  val FormLabel: ReactJSComponent = js.native
  val FormGroup: ReactJSComponent = js.native

  val Checkbox: ReactJSComponent = js.native
  val Radio: ReactJSComponent = js.native
  //val FormControl.Static: ReactJSComponent = js.native
  //val HelpBlock: ReactJSComponent = js.native
}

object components {

  def Button(props: IButtonProps|Null = null)(children: ReactNode*) =
    createElementN(BootstrapNS.Button, props)(children: _*)
  def ButtonToolbar(props: IButtonToolbarProps|Null = null)(children: ReactNode*) =
    createElementN(BootstrapNS.ButtonToolbar, props)(children: _*)
  def ButtonGroup(props: IButtonGroupProps|Null = null)(children: ReactNode*) =
    createElementN(BootstrapNS.ButtonToolbar, props)(children: _*)
  def ToggleButtonGroup(props: IToggleButtonGroupProps|Null = null)(children: ReactNode*) =
    createElementN(BootstrapNS.ButtonToolbar, props)(children: _*)
  def ToggleButton(props: IToggleButtonProps|Null = null)(children: ReactNode*) =
    createElementN(BootstrapNS.ButtonToolbar, props)(children: _*)

  def FormControl(props: IFormControlProps|Null = null)(children: ReactNode*) =
    createElementN(BootstrapNS.FormControl, props)(children: _*)
  def FormLabel(props: IFormLabelProps|Null = null)(children: ReactNode*) =
    createElementN(BootstrapNS.FormLabel, props)(children: _*)
  def FormGroup(props: IFormGroupProps|Null = null)(children: ReactNode*) =
    createElementN(BootstrapNS.FormGroup, props)(children: _*)

  //def HelpBlock(props: IHelpBlockProps=null)(children: ReactNode*) =
  //  createElement(BootstrapNS.HelpBlock, props, children: _*)
}

@js.native
sealed trait Variant extends js.Any
object Variant {
  val primary = "primary".asInstanceOf[Variant]
  val secondary = "primary".asInstanceOf[Variant]
  val success = "success".asInstanceOf[Variant]
  val danger = "danger".asInstanceOf[Variant]
  val warning = "warning".asInstanceOf[Variant]
  val info = "info".asInstanceOf[Variant]
  val dark = "link".asInstanceOf[Variant]
  val light = "link".asInstanceOf[Variant]
  val link = "link".asInstanceOf[Variant]
}

@js.native
sealed trait Size extends js.Any
object Size {
  val lg = "lg".asInstanceOf[Size]
  val large = "large".asInstanceOf[Size]
  val sm = "sm".asInstanceOf[Size]
  val small = "small".asInstanceOf[Size]
  val xs = "xs".asInstanceOf[Size]
  val xsmall = "xsmall".asInstanceOf[Size]
}

@js.native
sealed trait ButtonType extends js.Any
object ButtonType {
  val button = "button".asInstanceOf[ButtonType]
  val reset = "reset".asInstanceOf[ButtonType]
  val submit = "submit".asInstanceOf[ButtonType]
  val `null` = null.asInstanceOf[ButtonType]
}

trait IButtonProps extends js.Object {
  var active: js.UndefOr[Boolean] = js.undefined
  var disabled: js.UndefOr[Boolean] = js.undefined
  var block: js.UndefOr[Boolean] = js.undefined
  var `type`: js.UndefOr[ButtonType] = js.undefined
  var href: js.UndefOr[String] = js.undefined
  var variant: js.UndefOr[Variant] = js.undefined
  var size: js.UndefOr[Size] = js.undefined
  var className: js.UndefOr[String] = js.undefined
  var onChange: js.UndefOr[js.Function1[js.Any, Unit]] = js.undefined
  var bsPrefix: js.UndefOr[String] = js.undefined
}

trait IButtonGroupProps extends js.Object {
  var vertical: js.UndefOr[Boolean] = js.undefined
  var justified: js.UndefOr[Boolean] = js.undefined
  var block: js.UndefOr[Boolean] = js.undefined
  var bsClass: js.UndefOr[String] = js.undefined
}

trait IButtonToolbarProps extends js.Object {
  var bsClass: js.UndefOr[String] = js.undefined
}

@js.native
sealed trait ToggleButtonGroupType extends js.Any
object ToggleButtonGroupType {
  val checkbox = "checkbox".asInstanceOf[ToggleButtonGroupType]
  val radio = "radio".asInstanceOf[ToggleButtonGroupType]
}

trait IToggleButtonGroupProps extends js.Object {
  var name: js.UndefOr[String] = js.undefined
  var value: js.UndefOr[js.Any] = js.undefined
  var onChange: js.UndefOr[js.Function1[js.Any, Unit]] = js.undefined
  //var `type`: js.UndefOr[ToggleButtonGroupType] = js.undefined
}

trait IToggleButtonProps extends js.Object {
  //var `type`: js.UndefOr[String] = js.undefined
  var name: js.UndefOr[String] = js.undefined
  var checked: js.UndefOr[Boolean] = js.undefined
  var disabled: js.UndefOr[Boolean] = js.undefined
  var block: js.UndefOr[Boolean] = js.undefined
  var value: js.UndefOr[js.Any] = js.undefined
  var onChange: js.UndefOr[js.Function1[js.Any, Unit]] = js.undefined
}

@js.native
sealed trait ValidationState extends js.Any
object ValidationState {
  val success = "success".asInstanceOf[ValidationState]
  val warning = "warning".asInstanceOf[ValidationState]
  val error = "error".asInstanceOf[ValidationState]
}

trait IFormGroupProps extends js.Object {
  var controlId: js.UndefOr[String] = js.undefined
  var validationState: js.UndefOr[ValidationState | Null] = js.undefined
  var bsSize: js.UndefOr[Size] = js.undefined
  var bsClass: js.UndefOr[String] = js.undefined
}

@js.native
sealed trait FormControlType extends js.Any
object FormControlType {
  val text = "text".asInstanceOf[FormControlType]
}

@js.native
sealed trait As extends js.Any
object As {
  val input = "input".asInstanceOf[As]
  val textarea = "textarea".asInstanceOf[As]
}

trait IFormControlProps extends js.Object {
  var componentClass: js.UndefOr[String] = js.undefined

  /** Relevant if as = input */
  var `type`: js.UndefOr[FormControlType] = js.undefined
  var id: js.UndefOr[String] = js.undefined
  var size: js.UndefOr[Size] = js.undefined
  var className: js.UndefOr[String] = js.undefined
  var placeholder: js.UndefOr[String] = js.undefined
  var value: js.UndefOr[scala.Any] = js.undefined
  var onChange: js.UndefOr[js.Function1[js.Any, Unit]] = js.undefined
  var as: js.UndefOr[As | ReactJSComponent | String] = js.undefined

  var isValid: js.UndefOr[Boolean] = js.undefined
  var isInvalid: js.UndefOr[Boolean] = js.undefined
}

trait IFormLabelProps extends js.Object {
  var htmlFor: js.UndefOr[String] = js.undefined
  var srOnly: js.UndefOr[Boolean] = js.undefined
  var className: js.UndefOr[String] = js.undefined
  // innerRef?
  var column: js.UndefOr[Boolean] = js.undefined
  var bsPrefx: js.UndefOr[String] = js.undefined
}

// trait IHelpBlockProps extends js.Object {
// }

trait IFeedbackProps extends js.Object {}
