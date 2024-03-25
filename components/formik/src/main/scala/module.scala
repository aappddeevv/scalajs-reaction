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

package formik

import scala.scalajs.js
import js.annotation.*
import org.scalajs.dom
import react.*
import react.syntax.*
import vdom.*

@js.native
trait SubmitForm extends js.Object {
  def apply(): js.Promise[Any] = js.native
}

trait HasFieldValidator extends js.Object {
  val validate: js.UndefOr[FieldValidator] = js.undefined
}

@js.native
trait Registration extends js.Object {
  def registerField(name: String, fns: js.Any): Unit = js.native
  def unregisterField(name: String): Unit
}

@js.native
trait ChildProps[P]
    extends SharedConfig
    with StateReturnValue[P]
    with Helpers[P]
    with Handlers[P]
    with ComputedProps[P]
    with Registration
    with SubmitForm

/** For return values. */
@js.native
trait StateReturnValue[P] extends js.Object {
  val values: P = js.native
  @JSName("errors")
  val errors: Errors = js.native
  @JSName("errors")
  val errorsAsDyn: js.Dynamic = js.native
  @JSName("errors")
  def errorsAs[T <: js.Object]: T = js.native
  @JSName("errors")
  val errorsAsStringDict: js.Dictionary[String] = js.native
  @JSName("touched")
  val touched: Touched = js.native
  @JSName("touched")
  val touchedAsDyn: js.Dynamic = js.native
  @JSName("touched")
  val touchedAsDict: js.Dictionary[Boolean] = js.native
  @JSName("touched")
  def touchedAs[T <: js.Object]: T = js.native
  val isSubmitting: Boolean = js.native
  val isValidating: Boolean = js.native
  val status: Any = js.native
  val submitCount: Int = js.native
}

trait State[P] extends js.Object {
  val values: P
  val errors: Errors
  val errorsAsStringDict: js.Dictionary[String]
  val touched: Touched
  val isSubmitting: Boolean
  val isValidating: Boolean
  val status: Any
  val submitCount: Int
}

@js.native
trait ComputedProps[P] extends js.Object {
  val dirty: Boolean = js.native
  val isValid: Boolean = js.native
  val initialValues: P = js.native
  val initialErrors: Errors = js.native
  val initialTouched: Touched = js.native
  val initialStatus: js.UndefOr[Any] = js.native
}

@js.native
trait Helpers[P] extends js.Object {
  def setStatus(status: js.UndefOr[Any] = js.undefined): Unit = js.native
  def setErrors(errors: ErrorsInit): Unit = js.native
  def setSubmitting(isSubmitting: Boolean): Unit = js.native
  def setTouched(touched: TouchedInit, shouldValidate: js.UndefOr[Boolean] = js.undefined): Unit = js.native
  def setValues(values: P, shouldValidate: js.UndefOr[Boolean] = js.undefined): Unit = js.native
  def setFieldValue(field: String, value: Any, shouldValidate: js.UndefOr[Boolean] = js.undefined): Unit = js.native
  def setFieldError(field: String, message: String): Unit = js.native
  def setFieldTouched(
    field: String,
    isTouched: js.UndefOr[Boolean] = js.undefined,
    shouldValidate: js.UndefOr[Boolean] = js.undefined): Unit = js.native
  def validateForm(values: Any): js.Promise[Errors] = js.native
  def validateField(field: String): Unit = js.native
  def resetForm(nextState: js.UndefOr[js.Object | js.Dynamic | js.Dictionary[Any]]): Unit = js.native
  def submitForm(): js.Promise[Unit] = js.native
  def setFormikState(f: SetOrUpdateArg[State[P]], cb: js.UndefOr[js.Function0[Unit]] = js.undefined): Unit = js.native
}

/** Designed to be put plugged into HTML elements but I find mostly useless
 * because widget sets provide other callback APIs.
 */
@js.native
trait Handlers[P] extends js.Object {
  def handleSubmit(e: ReactFormEvent[dom.html.Form]): Unit = js.native
  def handleReset(e: SyntheticEvent[dom.html.Element]): Unit = js.native
  def handleBlur(e: ReactFocusEvent[dom.html.Element]): Unit = js.native
  //def handleBlur...
  def handleChange(e: ReactChangeEvent[dom.html.Element]): Unit = js.native
  //def handleChange
  def getFieldProps[A](props: Any): FieldInputProps[A] = js.native
  def getFieldMeta[A](name: String): FieldMetaProps[A] = js.native
  def getFieldHelpers[A](props: Any): FieldHelperProps[A] = js.native
}

trait SharedConfig extends js.Object {
  var validateOnChange: js.UndefOr[Boolean] = js.undefined
  var validateOnBlur: js.UndefOr[Boolean] = js.undefined
  var validateOnMount: js.UndefOr[Boolean] = js.undefined
  // gets all of props
  var isInitialValid: js.UndefOr[Boolean | js.Function1[js.Object, Boolean]] = js.undefined
  var enableReinitialize: js.UndefOr[Boolean] = js.undefined
}

object Formik {
  @js.native
  @JSImport("formik", "Formik")
  object JS extends ReactJSComponent

  def apply[P](props: Props[P])(children: js.Function1[ChildProps[P], ReactNode]) =
    createElement(JS, props, children.asInstanceOf[ReactNode])

  trait Props[P] extends SharedConfig {
    var component: js.UndefOr[String] = js.undefined
    //var enableReinitialize: js.UndefOr[Boolean] = js.undefined
    //var isInitialValid: js.UndefOr[Boolean] = js.undefined
    // values are string error messages
    var initialErrors: js.UndefOr[js.Object | js.Dynamic] = js.undefined
    var initialStatus: js.UndefOr[Any] = js.undefined
    // values are booleans
    var initialTouched: js.UndefOr[js.Object | js.Dynamic] = js.undefined
    var initialValues: js.UndefOr[P | js.Dynamic] = js.undefined
    var onReset: js.UndefOr[js.Function2[P, Helpers[P], Unit]] = js.undefined
    var onSubmit: js.UndefOr[js.Function2[P, Helpers[P], js.UndefOr[js.Thenable[Any]]]] = js.undefined
    // yup specific
    //var validateSchema
    var validate: js.UndefOr[js.Function1[P, Unit | js.Object | js.Dictionary[Any] | js.Thenable[Errors]]] =
      js.undefined
    var innerRef: js.UndefOr[ReactRef[Props[P]]] = js.undefined
  }
}

object ErrorMessage {
  @js.native
  @JSImport("formik", "ErrorMessage")
  object JS extends ReactJSComponent

  def apply(props: Props)(children: js.Function1[String, ReactNode]) =
    createElement(JS, props, children.asInstanceOf[ReactNode])

  def apply(props: Props) = createElement(JS, props)

  def apply(n: String, c: ReactType) =
    createElement(JS, new Props { val name = n; component = c })

  trait Props extends js.Object {
    val name: String
    var className: js.UndefOr[String] = js.undefined
    var component: js.UndefOr[ReactType] = js.undefined
    var render: js.UndefOr[js.Function1[String, ReactNode]] = js.undefined
  }
}

object FastField {
  @js.native
  @JSImport("formik", "FastField")
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) =
    createElement(JS, props, children*)

  trait Props extends js.Object {}
}

object Field {
  @js.native
  @JSImport("formik", "Field")
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) =
    createElement(JS, props, children*)

  trait Props extends js.Object {}

  @js.native
  trait ChildProps[P] extends js.Object {
    val field: FieldInputProps[P]
    val form: Formik.Props[P]
    val meta: FieldMetaProps[P]
  }
}

object FieldArray {
  @js.native
  @JSImport("formik", "FieldArray")
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) =
    createElement(JS, props, children*)

  trait Props extends js.Object {}
}

object Form:
  @js.native
  @JSImport("formik", "Form")
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) =
    createElement(JS, props, children*)

  trait Props extends js.Object {}

@js.native
trait ContextType[P] extends ChildProps[P]:
  // Keep in sync with Formik.Props
  var validate: js.UndefOr[js.Function1[P, Unit | js.Object | js.Promise[Errors]]] = js.native

@js.native
trait FieldMetaProps[A] extends js.Object:
  val value: A = js.native
  val error: js.UndefOr[String] = js.native
  val touched: Boolean = js.native
  val initialValue: js.UndefOr[A] = js.native
  val initialTouched: js.UndefOr[Boolean] = js.native
  val initialError: js.UndefOr[String] = js.native

@js.native
trait FieldHelperProps[A] extends js.Object:
  def setValue(value: A, shouldValidate: js.UndefOr[Boolean] = js.undefined): Unit = js.native
  def setTouched(value: Boolean, shouldValidate: js.UndefOr[Boolean] = js.undefined): Unit = js.native
  def setError(value: A): Unit = js.native

@js.native
trait FieldInputProps[A] extends js.Object {
  val value: A = js.native
  val name: String = js.native
  val multiple: js.UndefOr[Boolean] = js.native
  val checked: js.UndefOr[Boolean] = js.native
  val onChange: Handlers["handelChange"] = js.native
  val onBlur: Handlers["handleBlur"] = js.native
}

trait UseFieldProps[P] extends HTMLAttributes[dom.html.Element] {
  var component: js.UndefOr[ReactType] = js.undefined
  var as: js.UndefOr[ReactType] = js.undefined
  @JSName("children")
  var onRender: js.UndefOr[js.Function1[Field.ChildProps[P], ReactNode]] = js.undefined
  @JSName("children")
  var onRenderUsing: js.UndefOr[ReactNode] = js.undefined
  var validate: js.UndefOr[FieldValidator] = js.undefined
  val name: String

  /** HTML input type. */
  var `type`: js.UndefOr[String] = js.undefined
  var value: js.UndefOr[Any] = js.undefined
  var innerRef: js.UndefOr[js.Function1[Any, Unit]] = js.undefined
}
