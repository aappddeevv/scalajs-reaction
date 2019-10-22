// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package components
package form

import scala.scalajs.js
import js.Dynamic.{literal => jsobj}
import js.annotation._
import org.scalajs.dom
import concurrent._
import ExecutionContext.Implicits.global
import js.JSConverters._
import util.Success

import ttg.react
import react._
import implicits._
import elements._

import vdom._

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
 * @todo Remove dependency on Future. Make general F or use js.Promise.
  */
trait FormControllerBase extends HasValues with HasTouches with HasErrors {

  val Name: String

  import _errors.{Errors, EmptyErrors}
  import _values.Value
  import _touches.{Touches, EmptyTouches}

  /** THIS ASSUMES A SPECIFIC ERRROR MODEL, BROKE ABSTRACTION */
  /** Validate a single value, return user message. Used for field level validations. */
  type Validator = Option[scala.Any] => Future[String]
  /** Validators indexed by attribute name. */
  type Validators = js.Dictionary[Validator]
  val EmptyValidators = js.Dictionary.empty[Validator]

  /** Validate values, only checking Seq[String] fields, or all fields (form level
   * validation) if Nil. Return errors.
   */
  type FormValidator = (Value, Seq[String]) => Future[Errors]
  type ResetCallback = (Value, FormActions) => Future[Unit]
  type SubmitCallback = (Value, FormActions) => Future[Unit]

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
    touched: Touches = EmptyTouches,
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
    initialValue: Value
  )

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
    validateField: (String => Future[Errors]) = _ =>
    Future.successful(EmptyErrors),
    validateForm: (Value => Future[Errors]) = _ =>
    Future.successful(EmptyErrors),
  )

  def setError(dispatch: Dispatch[Action], field: String, message: String) = {
    val anerror = _errors.combine(EmptyErrors, _errors.pure(field, message))
    dispatch(FieldErrors(anerror, true))
  }

  def resetForm(
    dispatch: Dispatch[Action],
    initialValue: MutableRef[Value],
    nextValues: Option[Value]) = {
    initialValue.current = nextValues.getOrElse(initialValue.current)
    dispatch(Reset(initialValue.current))
  }

  def makeFormActions(
    currentValue: Value,
    dispatch: Dispatch[Action],
    initialValue: MutableRef[Value],
    validate: js.UndefOr[FormValidator]) =
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
        .getOrElse(Future.successful(EmptyErrors)),
      validateField = f =>
      validate
        .map(_(currentValue, Seq(f)))
        .getOrElse(Future.successful(EmptyErrors)),
    )

  /** Handlers for controls. These are close to what's needed for most html
   * controls.
   */
  case class FormHandlers(
    /** feld, value */
    handleChange: ((String, Value) => Unit) = (_, _) => (),
    /** field, value */
    handleBlur: (String => Unit) = _ => (),
    /** Calls the reset callback handler.  Note that spec says returning false means
     * the form will not submit.
     */
    handleSubmit: FormEventHandler[dom.html.Form] = _ => (),
    handleReset: FormEventHandler[dom.html.Form] = _ => (),
  )

  def handleReset(
    currentValue: Value,
    dispatch: Dispatch[Action],
    initialValue: MutableRef[Value],
    factions: FormActions,
    reset: js.UndefOr[ResetCallback]): FormEventHandler[dom.html.Form] =
    e =>
  reset
    .fold(
      Future.unit
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
    reset: js.UndefOr[ResetCallback]) =
    FormHandlers(
      handleChange = (f, v) => dispatch(FieldValue(f, v, true)),
      handleBlur = (f) => dispatch(Touched(f, true)),
      handleSubmit = handleSubmit(dispatch),
      handleReset = handleReset(currentValue, dispatch, initialValue, factions, reset)
    )

  /** Dummy for now. Always return EmptyErrors. */
  def runFieldLevelValidations(
    validators: js.Dictionary[Validator],
    values: Value): Future[Errors] = {
    Future.successful(EmptyErrors)
  }

  /**
   * Run validation handlers. Merge resulting errors. Maybe the return value
   * should be Future[Option[Errors]]. If the validators fail, return the
   * exception in the Future's error channel.
   *
   * TODO: Keep all errors by using an applicative non-empty list.
   */
  def runValidations(
    values: Value,
    fieldLevelValidators: js.Dictionary[Validator], // use EmptyValidators if have none
    handler: js.UndefOr[FormValidator]
  ): Future[Errors] = {
    val fieldF = runFieldLevelValidations(fieldLevelValidators, values)
    val hF = handler.fold(Future.successful(EmptyErrors)) { _(values, Nil) }
    // if either fail, the overall result fails
    val combined = for {
      ferrs <- fieldF
      herrs <- hF
    } yield _errors.combine(ferrs, herrs)
    combined
  }

  /** The context is slightly duplicative in that initialValues and validate are
   * already in FormProps.
   */
  case class Context(
    props: FormProps,
    initialValue: Value,
    validate: js.UndefOr[FormValidator]
  )

  import react.context._
  val FormContext = react.context.make[Context](null)

  trait Props extends js.Object {
    var initialValue: Value
    var children: FormProps => ReactNode
    var submit: js.UndefOr[SubmitCallback] = js.undefined
    var reset: js.UndefOr[ResetCallback] = js.undefined
    var validate: js.UndefOr[FormValidator] = js.undefined
    var validateOnBlur: js.UndefOr[Boolean] = js.undefined
    var validateOnChange: js.UndefOr[Boolean] = js.undefined
    var isInitialValid: js.UndefOr[() => Boolean] = js.undefined
    var onChange: js.UndefOr[(Value, Boolean) => Unit] = js.undefined
    var touched: js.UndefOr[Seq[String] => Unit] = js.undefined
  }

  /**
   * Create component. Child is a render prop.
   */
  def apply(props: Props)(c: FormProps => ReactNode) =
    sfc(props.combine(jsobj("children" -> c)))

  val sfc = SFC1[Props]{ props =>
    React.useDebugValue(Name)
    val initialValue = React.useRef[Value](props.initialValue)
    val mounted = React.useRef[Boolean](false)
    val submitCount = React.useRef[Int](0)

    React.useEffectMounting(() => {
      mounted.current = true
      (() => mounted.current = false)
    })

    val (state, dispatch) = React.useReducer[State, Action, Null]((s, a) => {
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
            postAction = if(f) Option(x) else None
          )
        case x @ FieldValue(_, newvalues, doValidate) =>
          s.copy(
            value = newvalues,
            postAction = if(doValidate) Option(x) else None
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
            postAction = if(doValidate) Option(x) else None
          )
        case x @ FieldErrors(errs, merge) =>
          s.copy(
            errors =
              if (merge) _errors.combine(s.errors, errs)
              else errs
          )
      }},
      null,
      _ => State(value = props.initialValue)
    )

    // effects after state change
    // TODO: Set "validating" flag at start and end correctly.
    React.useEffect(dependencies(
      state.postAction,
      props.validate,
      props.submit,
      props.touched,
      props.validateOnChange,
      props.validateOnBlur,
      props.onChange.isDefined
    )){() =>
      state.postAction match {
        case Some(Reset(nextvalues)) =>
          props.onChange.foreach(_(nextvalues, true))

        case Some(Touched(field, doValidate)) =>
          (if(doValidate && props.validateOnBlur.getOrElse(true))
              runValidations(state.value, EmptyValidators, props.validate)
           else Future.successful(EmptyErrors))
            .onComplete {
              case _ => props.touched.foreach(_(Seq(field)))
            }

        case Some(FieldValue(field, newvalues, doValidate)) =>
          val fut =
            if (doValidate && props.validateOnChange.getOrElse(true))
              runValidations(state.value, EmptyValidators, props.validate)
                .map(errs => _errors.count(errs) == 0)
            else Future.successful(false)
          fut.onComplete {
            case Success(validationStatus) =>
              props.onChange.foreach(_(newvalues, validationStatus))
            case _ =>
              // eats a thrown exception
              props.onChange.foreach(_(newvalues, false))
          }

        case Some(Submitting(f)) =>
          if(f)
            runValidations(state.value, EmptyValidators, props.validate)
              .flatMap { errs =>
                // call user callback
                if (_errors.count(errs) == 0)
                  props.submit.fold(
                    Future.unit
                  )(
                    _(state.value, makeFormActions(state.value, dispatch, initialValue,
                      props.validate))
                  )
                else Future.unit
              }
              .onComplete {
                case _ => dispatch(Submitting(false))
              }
        case _ => // should never happen
      }
    }

    val cvalues = state.value //props.value.getOrElse(state.value)
    val factions = makeFormActions(cvalues, dispatch, initialValue, props.validate)
    // compare initialValue to current values
    val dirty =
      !_values.eq(initialValue.current, cvalues)

    val _isValid =
      // do easy check before calling thunk
      if (dirty && _errors.count(state.errors) > 0) true
      else props.isInitialValid.map(_()).getOrElse(true)

    val fprops = FormProps(
      initialValue = initialValue.current,
      values = cvalues,
      isValid = _isValid,
      dirty = dirty,
      errors = state.errors,
      touched = state.touched,
      handlers = makeFormHandlers(cvalues, dispatch, initialValue,
        factions, props.reset),
      actions = factions,
      isValidating = state.validating,
      isSubmitting = state.submitting,
      submitCount = submitCount.current,
      didMount = mounted.current
    )
    val context = Context(fprops, initialValue.current, props.validate)
    FormContext.provider(context)(
      props.children(fprops)
    )
  }
}
