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

import org.scalajs.dom.html

import react._

import vdom._

import fabric.styling._

object TextField {

  @js.native
  @JSImport("office-ui-fabric-react/lib/TextField", "TextField")
  object JS extends ReactJSComponent

  def apply(props: Props = null)(children: ReactNode*) =
    createElementN(JS, props)(children: _*)

  @js.native
  trait ITextField extends Focusable with ClassAttributes[html.Input] {
    var value: js.UndefOr[String]                       = js.native
    var select: js.Function0[Unit]                      = js.native
    var blur: js.Function0[Unit]                        = js.native
    var setSelectionStart: js.Function1[Int, Unit]      = js.native
    var setSelectionEnd: js.Function1[Int, Unit]        = js.native
    var setSelectionRange: js.Function2[Int, Int, Unit] = js.native
    var selectionStart: Int                             = js.native
    var selectionEnd: Int                               = js.native
  }

  trait StyleProps extends js.Object {}

  trait Styles extends IStyleSetTag {}

  //export interface ITextFieldProps extends React.AllHTMLAttributes<HTMLInputElement | HTMLTextAreaElement> {
  // withDisabled

  // should also included attributes from dom.html.TextArea
  trait Props extends WithIconProps with Theme with ComponentRef[ITextField] with AllHTMLAttributes[html.Input] {
    var autoAdjustHeight: js.UndefOr[Boolean] = js.undefined
    var ariaLabel: js.UndefOr[String]         = js.undefined
    var addonString: js.UndefOr[String]       = js.undefined
    var borderless: js.UndefOr[Boolean]       = js.undefined
    var componentId: js.UndefOr[String]       = js.undefined
    //var label: js.UndefOr[String] = js.undefined
    var description: js.UndefOr[String]          = js.undefined
    var deferredValidationTime: js.UndefOr[Long] = js.undefined
    //var iconProps?: IIconProps;
    // defined in HTMLAttributes
    //var defaultValue: js.UndefOr[String] = js.undefined
    //var value: js.UndefOr[String] = js.undefined
    var errorMessage: js.UndefOr[String]                 = js.undefined
    var inputClassName: js.UndefOr[String]               = js.undefined
    var mask: js.UndefOr[String]                         = js.undefined
    var maskChar: js.UndefOr[String]                     = js.undefined
    var maskFormat: js.UndefOr[js.Dictionary[js.RegExp]] = js.undefined
    var multiline: js.UndefOr[Boolean]                   = js.undefined
    // should this be js.UndefOr[String] ?
    //var readOnly: js.UndefOr[Boolean] = js.undefined
    //var disabled: js.UndefOr[Boolean] = js.undefined
    @JSName("onChange")
    var onChangeInput: js.UndefOr[js.Function2[SyntheticFormEvent[html.Input], String, Unit]] = js.undefined
    @JSName("onChange")
    var onChangeInput1: js.UndefOr[js.Function1[SyntheticFormEvent[html.Input], Unit]] = js.undefined
    @JSName("onChange")
    var onChangeTextArea: js.UndefOr[js.Function2[SyntheticFormEvent[html.TextArea], String, Unit]] = js.undefined
    @JSName("onChange")
    var onChangeTextArea1: js.UndefOr[js.Function1[SyntheticFormEvent[html.TextArea], Unit]] = js.undefined
    // onChanged is deprecated
    var onBeforeChange: js.UndefOr[js.Function1[String, Unit]] = js.undefined

    /** error message, value */
    var onNotifyValidationResult: js.UndefOr[js.Function2[String, String, Unit]] = js.undefined
    var onRenderLabel: js.UndefOr[IRenderFunction[Props]]                        = js.undefined
    var onGetErrorMessage: js.UndefOr[String => js.Promise[String]]              = js.undefined
    //: string) => string | PromiseLike<string> | undefined;
    var onRenderAddon: js.UndefOr[IRenderFunction[Props]] = js.undefined
    var prefix: js.UndefOr[String]                        = js.undefined
    //var className: js.UndefOr[String] = js.undefined

    var resizable: js.UndefOr[Boolean] = js.undefined

    var underlined: js.UndefOr[Boolean]         = js.undefined
    var validateOnFocusIn: js.UndefOr[Boolean]  = js.undefined
    var validateOnFocusOut: js.UndefOr[Boolean] = js.undefined
    var validateOnLoad: js.UndefOr[Boolean]     = js.undefined

    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] =
      js.undefined
    //var autoComplete: js.UndefOr[String] = js.undefined
  }

}
