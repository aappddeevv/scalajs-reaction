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

package forms

import scala.scalajs.js
import js.Dynamic.{ literal => jsobj }
import org.scalajs.dom
import react._
import react.implicits._
import vdom._
import util.Success

trait HasValues {
  val _values: HasValues.Service
}

object HasValues {
  trait Service {
    // Value colud be a single value or an object with multiple properties.
    type Value
    def eq(lhs: Value, rhs: Value): Boolean
  }
}

trait HasTouches {
  val _touches: HasTouches.Service
}

object HasTouches {
  trait Service {
    type Touches
    def EmptyTouches: Touches
    def touch(id: String, touches: Touches): Touches
    def touched(id: String, touches: Touches): Boolean
  }
}

trait HasErrors {
  val _errors: HasErrors.Service
}

object HasErrors {
  trait Service {
    type Errors
    def EmptyErrors: Errors
    def combine(errors: Errors*): Errors
    def pure(id: String, message: String): Errors
    def count(errors: Errors): Int
  }
}

/**
 * A component to manage data editing lifecycle. Supply the initial values and
 * optionally track fine-grained changes. Very few assumptions are made in this
 * base class leaving the details to subclasses. A react context is provided
 * for child components, if desired.
 *
 * The form can be used as controlled or uncontrolled. Provide a "value"
 * parameter to ake it controlled. Controlled is a better model if you have
 * components (such as a reset button) outside the form's structure that can
 * alter the value being edited.
 *
 * Specific types have been extracted into services so you can plugin
 * any kind of data structure as long is it follows the service definition.
 * Most form libraries assume a js.Object but that's not always the case
 * in scala.js.
 *
 */
trait FormControllerBase extends HasValues with HasTouches with HasErrors {

  val Name: String
  // abstract out the F subtley, not using cats
  type F[A] = js.Promise[A]
  def Fsuccessful[A](a: A) = JSPromise(a)
  val Funit = JSPromise.unit
  // flatMap, map needed as well

  import _errors.{ EmptyErrors, Errors }
  import _touches.{ EmptyTouches, Touches }
  import _values.Value

  /** THIS ASSUMES A SPECIFIC ERRROR MODEL, BROKE ABSTRACTION */
  /** Validate a single value, return user message. Used for field level validations. */
  type Validator = Option[scala.Any] => F[String]

  /** Validators indexed by attribute name. */
  type Validators = js.Dictionary[Validator]
  val EmptyValidators = js.Dictionary.empty[Validator]

  /** Validate values, only checking Seq[String] fields, or all fields (form level
   * validation) if Nil. Return errors.
   */
  type FormValidator = (Value, Seq[String]) => F[Errors]
  type ResetCallback = (Value, FormActions) => F[Unit]
  type SubmitCallback = (Value, FormActions) => F[Unit]

  /** Actions that are sent by child form elements. */
  sealed trait Action

  /** Send the attibute that changed and an updated Value object. */
  case class FieldValue(field: String, value: Value, validate: Boolean) extends Action

  /** Set field errors. Either replacing all of the existing or merging this into
   * the existing field errors.
   */
  case class FieldErrors(errors: Errors, merge: Boolean) extends Action

  /** Touch a field potentially forcing validation. */
  case class Touched(field: String, validate: Boolean) extends Action

  /** Indicate whether we are validating, performed asynchronously. This action
   * does not perform validation itself, it just provides the status or the
   * result of validation if f = false.
   * @param f validating status "is or is not validating"
   * @param  errors validation errors from the validation ffort, if any
   */
  case class Validating(f: Boolean, errors: Option[Errors]) extends Action

  /** Reset using the values provided including initialValues. */
  case class Reset(values: Value) extends Action

  /** Submit the form.
   * @param f Whether submission should start or if it has ended.
   */
  case class Submitting(f: Boolean) extends Action
  //case class ResetField(field: String) extends Action

  case class State(
    /** Current value, given changes, always stored even if its controlled. */
    value: Value,
    /** If an action needs validation to run after general state changes, this is it. */
    postAction: Option[Action] = None,
    submitting: Boolean = false,
    validating: Boolean = false,
    resetting: Boolean = false,
    errors: Errors = EmptyErrors,
    touched: Touches = EmptyTouches
    /** This is here in case we allow child components to add validators. */
    //validators: Box[Validators] = Box(EmptyValidators)
  )

  /** Props passed to the child component. */
  case class FormProps(
    /** No errors and factors in isInitialValid. */
    isValid: Boolean,
    /* Derived. */
    dirty: Boolean,
    /** Current value. */
    values: Value,
    errors: Errors,
    touched: Touches,
    isValidating: Boolean,
    isSubmitting: Boolean,
    submitCount: Int,
    didMount: Boolean,
    handlers: FormHandlers,
    actions: FormActions,
    initialValue: Value)

  /**
   * API to change this this component's state, church encoding of Actions.
   * Most of these send state changing messages to the reducer. This API is
   * should be easier to use then sending raw Action messages.
   */
  case class FormActions(
    /* Given a specific field, pass a total Value with that field updated. */
    setFieldValue: ((String, Value, Boolean) => Unit) = (_, _, _) => (),
    /** field, message */
    setError: ((String, String) => Unit) = (_, _) => (),
    setErrors: ((Errors, Boolean) => Unit) = (_, _) => (),
    /** field, should validate */
    setTouched: ((String, Boolean) => Unit) = (_, _) => (),
    submit: (() => Unit) = () => (),
    /** Does not call the reset callback. */
    reset: (Option[Value] => Unit) = _ => (),
    validateField: (String => F[Errors]) = _ => Fsuccessful(EmptyErrors),
    validateForm: (Value => F[Errors]) = _ => Fsuccessful(EmptyErrors))

  def setError(dispatch: Dispatch[Action], field: String, message: String) = {
    val anerror = _errors.combine(EmptyErrors, _errors.pure(field, message))
    dispatch(FieldErrors(anerror, true))
  }

  def resetForm(dispatch: Dispatch[Action], initialValue: MutableRef[Value], nextValues: Option[Value]) = {
    initialValue.current = nextValues.getOrElse(initialValue.current)
    dispatch(Reset(initialValue.current))
  }

  def makeFormActions(
    currentValue: Value,
    dispatch: Dispatch[Action],
    initialValue: MutableRef[Value],
    validate: js.UndefOr[FormValidator]
  ) =
    FormActions(
      setFieldValue = (f, v, s) => dispatch(FieldValue(f, v, s)),
      setError = setError(dispatch, _, _),
      setErrors = (e, m) => dispatch(FieldErrors(e, m)),
      setTouched = (f, s) => dispatch(Touched(f, s)),
      reset = resetForm(dispatch, initialValue, _),
      submit = () => dispatch(Submitting(true)),
      validateForm = v =>
        validate
          .map(_(v, Nil))
          .getOrElse(Fsuccessful(EmptyErrors)),
      validateField = f =>
        validate
          .map(_(currentValue, Seq(f)))
          .getOrElse(Fsuccessful(EmptyErrors))
    )

  /** Handlers for controls. These are close to what's needed for most html
   * controls but you will need to adapt them to the specific element.
   */
  case class FormHandlers(
    /** feld, value */
    change: ((String, Value) => Unit) = (_, _) => (),
    /** field, value */
    blur: (String => Unit) = _ => (),
    /** Calls the reset callback handler.  Note that spec says returning false means
     * the form will not submit.
     */
    submit: FormEventHandler[dom.html.Form] = _ => (),
    reset: FormEventHandler[dom.html.Form] = _ => ())

  def handleReset(
    currentValue: Value,
    dispatch: Dispatch[Action],
    initialValue: MutableRef[Value],
    factions: FormActions,
    reset: js.UndefOr[ResetCallback]
  ): FormEventHandler[dom.html.Form] =
    e =>
      reset
        .fold(
          Funit
        )(_(currentValue, factions))
        .onComplete {
          case _ => resetForm(dispatch, initialValue, None)
        }

  def handleSubmit(dispatch: Dispatch[Action]): FormEventHandler[dom.html.Form] = e => {
    e.preventDefault()
    dispatch(Submitting(true))
  }

  def makeFormHandlers(
    currentValue: Value,
    dispatch: Dispatch[Action],
    initialValue: MutableRef[Value],
    factions: FormActions,
    reset: js.UndefOr[ResetCallback]
  ) =
    FormHandlers(
      change = (f, v) => dispatch(FieldValue(f, v, true)),
      blur = (f) => dispatch(Touched(f, true)),
      submit = handleSubmit(dispatch),
      reset = handleReset(currentValue, dispatch, initialValue, factions, reset)
    )

  /** Dummy for now. Always return EmptyErrors. */
  def runFieldLevelValidations(validators: js.Dictionary[Validator], values: Value): F[Errors] =
    Fsuccessful(EmptyErrors)

  /**
   * Run validation handlers. Merge resulting errors. Maybe the return value
   * should be F[Option[Errors]]. If the validators fail, return the
   * exception in the Future's error channel.
   *
   * TODO: Keep all errors by using an applicative non-empty list.
   */
  def runValidations(
    values: Value,
    fieldLevelValidators: js.Dictionary[Validator], // use EmptyValidators if have none
    handler: js.UndefOr[FormValidator]
  ): F[Errors] = {
    val fieldF = runFieldLevelValidations(fieldLevelValidators, values)
    val hF = handler.fold(Fsuccessful(EmptyErrors))(_(values, Nil))
    // if either fail, the overall result fails
//     val combined = for {
//       ferrs <- fieldF
//       herrs <- hF
//     } yield _errors.combine(ferrs, herrs)
//     combined
    runFieldLevelValidations(fieldLevelValidators, values)
      .flatMap(errs =>
        handler
          .fold(Fsuccessful(EmptyErrors))(_(values, Nil))
          .map(_errors.combine(ferrs, herrs)))
  }

  /** The context is slightly duplicative in that initialValues and validate are
   * already in FormProps.
   */
  case class Context(props: FormProps, initialValue: Value, validate: js.UndefOr[FormValidator])

  val FormContext = react.createContext[Context](null)

  trait PropsBase extends js.Object {
    val initialValue: Value
    var submit: js.UndefOr[SubmitCallback] = js.undefined
    var reset: js.UndefOr[ResetCallback] = js.undefined
    var validate: js.UndefOr[FormValidator] = js.undefined
    var validateOnBlur: js.UndefOr[Boolean] = js.undefined
    var validateOnChange: js.UndefOr[Boolean] = js.undefined
    var isInitialValid: js.UndefOr[() => Boolean] = js.undefined
    var onChange: js.UndefOr[(Value, Boolean) => Unit] = js.undefined
    var touched: js.UndefOr[Seq[String] => Unit] = js.undefined
  }

  trait PropsInit extends PropsBase {
    var children: js.UndefOr[js.Function1[FormProps, ReactNode]] = js.undefined
  }

  trait Props extends PropsBase {
    val children: js.Function1[FormProps, ReactNode]
  }

  def apply(props: Props) = render.elementWith(props)

  def apply(props: PropsInit)(children: js.Function1[FormProps, ReactNode]) =
    render.elementWith(props.asInstanceOf[Props].combineDynamic(jsobj("children" -> children)))

  val render: ReactFC[Props] = props => {
    val initialValue = useRef[Value](props.initialValue)
    val mounted = useRef[Boolean](false)
    val submitCount = useRef[Int](0)

    useEffectMounting { () =>
      mounted.current = true
      (() => mounted.current = false)
    }

    val (state, dispatch) = useReducer[State, Action, Null](
      (s, a) =>
        a match {
          case x @ Reset(nextvalues) =>
            submitCount.current = 0
            State(
              value = nextvalues,
              postAction = Option(x)
            )
          case x @ Submitting(f) =>
            // Formik sets all touched value to true, why?
            submitCount.current = submitCount.current + 1
            s.copy(
              submitting = f,
              postAction = if (f) Option(x) else None
            )
          case x @ FieldValue(_, newvalues, doValidate) =>
            s.copy(
              value = newvalues,
              postAction = if (doValidate) Option(x) else None
            )
          case Validating(f, eopt) =>
            s.copy(
              validating = f,
              errors = eopt.fold(s.errors)(identity)
            )
          // If you validate on change then blur on the form, you may run validations twice.
          case x @ Touched(field, doValidate) =>
            val newtouched = _touches.touch(field, s.touched)
            s.copy(
              touched = newtouched,
              postAction = if (doValidate) Option(x) else None
            )
          case x @ FieldErrors(errs, merge) =>
            s.copy(
              errors =
                if (merge) _errors.combine(s.errors, errs)
                else errs
            )
        },
      null,
      _ => State(value = props.initialValue)
    )

    // effects after state change
    // TODO: Set "validating" flag at start and end correctly.
    useEffect(
      unsafeDeps(
        state.postAction,
        props.validate,
        props.submit,
        props.touched,
        props.validateOnChange,
        props.validateOnBlur,
        props.onChange.isDefined
      )
    ) { () =>
      state.postAction match {
        case Some(Reset(nextvalues)) =>
          props.onChange.foreach(_(nextvalues, true))

        case Some(Touched(field, doValidate)) =>
          (if (doValidate && props.validateOnBlur.getOrElse(true))
             runValidations(state.value, EmptyValidators, props.validate)
           else Fsuccessful(EmptyErrors)).onComplete {
            case _ => props.touched.foreach(_(Seq(field)))
          }

        case Some(FieldValue(field, newvalues, doValidate)) =>
          val fut =
            if (doValidate && props.validateOnChange.getOrElse(true))
              runValidations(state.value, EmptyValidators, props.validate)
                .map(errs => _errors.count(errs) == 0)
            else Fsuccessful(false)
          fut.onComplete {
            case Success(validationStatus) =>
              props.onChange.foreach(_(newvalues, validationStatus))
            case _ =>
              // eats a thrown exception
              props.onChange.foreach(_(newvalues, false))
          }

        case Some(Submitting(f)) =>
          if (f)
            runValidations(state.value, EmptyValidators, props.validate).flatMap { errs =>
              // call user callback
              if (_errors.count(errs) == 0)
                props.submit.fold(
                  Funit
                )(
                  _(state.value, makeFormActions(state.value, dispatch, initialValue, props.validate))
                )
              else Funit
            }.onComplete {
              case _ => dispatch(Submitting(false))
            }
        case _ => // should never happen
      }
    }

    val cvalues = state.value //props.value.getOrElse(state.value)
    val factions = makeFormActions(cvalues, dispatch, initialValue, props.validate)
    // compare initialValue to current values
    val dirty = !_values.eq(initialValue.current, cvalues)

    val _isValid =
      // do easy check before calling thunk
      if (dirty && _errors.count(state.errors) > 0) true
      else props.isInitialValid.map(_()).getOrElse(true)

    // memoize ???
    val fprops = FormProps(
      initialValue = initialValue.current,
      values = cvalues,
      isValid = _isValid,
      dirty = dirty,
      errors = state.errors,
      touched = state.touched,
      handlers = makeFormHandlers(cvalues, dispatch, initialValue, factions, props.reset),
      actions = factions,
      isValidating = state.validating,
      isSubmitting = state.submitting,
      submitCount = submitCount.current,
      didMount = mounted.current
    )
    val context = Context(fprops, initialValue.current, props.validate)
    FormContext.provide(context)(
      props.children(fprops)
    )
  }
  render.displayName(Name)
}
