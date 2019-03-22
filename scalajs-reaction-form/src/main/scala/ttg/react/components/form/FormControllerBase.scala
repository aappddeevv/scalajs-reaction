// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package components
package form

import scala.scalajs.js
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
  */
trait FormControllerBase extends HasValues with HasTouches with HasErrors {

  def Name: String

  import _errors.{Errors, EmptyErrors}
  import _values.Value
  import _touches.{Touches, EmptyTouches}

  /** Validate a single value. Used for field level validations. */
  type Validator = Option[scala.Any] => Future[String]
  /** Validators indexed by attribute. */
  type Validators = js.Dictionary[Validator]
  val EmptyValidators = js.Dictionary.empty[Validator]

  /** Validation values, only checking Seq[String] fields, or all fields if Nil. Return errors. */
  type FormValidator = (Value, Seq[String]) => Future[Errors]
  type ResetCallback = (Value, FormActions) => Future[Unit]
  type SubmitCallback = (Value, FormActions) => Future[Unit]

  sealed trait Action

  /** Send the attibute that changed and an updated Value object. */
  case class FieldValue(field: String, value: Value, validate: Boolean)
      extends Action
  case class FieldErrors(errors: Errors, merge: Boolean) extends Action
  case class Touched(field: String, validate: Boolean) extends Action
  case class Validating(f: Boolean, errors: Option[Errors]) extends Action
  case class Reset(values: Value) extends Action
  case object Submitting extends Action
  //case class ResetField(field: String) extends Action

  case class State(
    /** Current value, given changes, always stored even if controlled. */
      values: Value,
      errors: Errors = EmptyErrors, // combine with is* into a GDAT?
      touched: Touches = EmptyTouches,
      isValidating: Boolean = false,
    isSubmitting: Boolean = false,
    /** Number of times the submit action has been run. */
    submitCount: Int = 0,
    /** Initial values, for convenience. */
      initialValue: Box[Value],
    didMount: Box[Boolean] = Box(false),
    /** This is here in case we allow child components to add validators. */
    validators: Box[Validators] = Box(EmptyValidators)
  )

  val c = reducerComponent[State, Action](Name)
  import c.ops._

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

  def setError(self: Self, field: String, message: String) = {
    val anerror = _errors.combine(EmptyErrors, _errors.pure(field, message))
    self.send(FieldErrors(anerror, true))
  }

  def resetForm(self: Self, nextValues: Option[Value]) = {
    val newvalues = nextValues.getOrElse(self.state.initialValue.value)
    self.state.initialValue.value = newvalues
    self.send(Reset(newvalues))
  }

  def makeFormActions(self: Self, validate: Option[FormValidator]) =
    FormActions(
      setFieldValue = (f, v, s) => self.send(FieldValue(f, v, s)),
      setError = setError(self, _, _),
      setErrors = (e, m) => self.send(FieldErrors(e, m)),
      setTouched = (f, s) => self.send(Touched(f, s)),
      reset = resetForm(self, _),
      submit = () => self.send(Submitting),
      validateForm = v =>
        validate
          .map(_(v, Nil))
          .getOrElse(Future.successful(EmptyErrors)),
      validateField = f =>
        validate
          .map(_(self.state.values, Seq(f)))
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
      self: Self,
      factions: FormActions,
      reset: Option[ResetCallback]): FormEventHandler[dom.html.Form] =
    e =>
      reset
        .fold(
          Future.unit
        )(_(self.state.values, factions))
        .onComplete {
          case _ => resetForm(self, None)
      }

  def handleSubmit(self: Self): FormEventHandler[dom.html.Form] = e => {
    e.preventDefault() // prevents the actual submit i.e. page refresh
    self.send(Submitting)
  }

  def makeFormHandlers(self: Self,
                       factions: FormActions,
                       reset: Option[ResetCallback]) =
    FormHandlers(
      handleChange = (f, v) => self.send(FieldValue(f, v, true)),
      handleBlur = (f) => self.send(Touched(f, true)),
      handleSubmit = handleSubmit(self),
      handleReset = handleReset(self, factions, reset)
    )

  def runFieldLevelValidations(validators: js.Dictionary[Validator],
                               values: Value): Future[Errors] = {
    Future.successful(EmptyErrors)
  }

  /**
    * Run validation handlers. Merge resulting errors. Maybe the return value
    * should be Future[Option[Errors]].
    *
    * TODO: Keep all errors by using an applicative non-empty list.
    */
  def runValidations(
      self: Self,
      values: Value,
      fieldLevelValidators: js.Dictionary[Validator],
      handler: Option[FormValidator]
  ): Future[Errors] = {
    self.send(Validating(true, None))
    val fieldF = runFieldLevelValidations(fieldLevelValidators, values)
    val hF = handler.fold(Future.successful(EmptyErrors)) { _(values, Nil) }
    val combined = for {
      ferrs <- fieldF
      herrs <- hF
    } yield _errors.combine(ferrs, herrs)
    if (self.state.didMount.value)
      combined.foreach(errs => self.send(Validating(false, Option(errs))))
    combined
  }

  /** The context is slightly duplicative in that initialValues and validate are
   * already in FormProps.
   */
  case class Context(
      props: FormProps,
      initialValue: Value,
      validate: Option[FormValidator]
  )

  import react.context._
  val FormContext = react.context.make[Context](null)

  // trait Props extends js.Object {
  //   var initialValues: js.UndefOr[Value] = js.undefined
  //   var submit: js.UndefOr[SubmitCallback] = js.undefined
  //   var reset: js.UndefOr[ResetCallback] = js.undefined
  //   var validate: js.UndefOr[FormValidator] = js.undefined
  //   var validateOnBlur: js.UndefOr[Boolean] = js.undefined
  //   var validateOnChange: js.UndefOr[Boolean] = js.undefined
  //   var isInitialValid: js.UndefOr[() => Boolean] = js.undefined
  //   var onChange: js.UndefOr[(Value, Boolean) => Unit] = js.undefined
  //   var touched: js.UndefOr[Seq[String] => Unit] = js.undefined
  // }

  /**
   * Create component.
   * 
   * @param initialValues Initial values to be edited the form.
   * @param values If provided, component is controlled.
   * @param isInitialValid Use to set button state on mount, if needed.
   * @param onChange If a change passes validation (if requested), signal a
   * change and whether validations passed if validation occurred.
   * @param touched Touched callback.
   * 
   * @todo Should be able to get rid of initialValues. Let parent always pass-in
   * original value and be a controlled component instead of uncontrolled.
   */
  def apply(
    initialValue: Value,
    value: Option[Value] = None,
      submit: Option[SubmitCallback] = None,
      reset: Option[ResetCallback] = None,
      validate: Option[FormValidator] = None,
      validateOnBlur: Boolean = true,
      validateOnChange: Boolean = true,
      isInitialValid: () => Boolean = () => true,
      onChange: Option[(Value, Boolean) => Unit] = None,
      touched: Option[Seq[String] => Unit] = None
  )(
      child: FormProps => ReactNode
  ) =
    c.copy(new methods {
      val initialState = self => {
        val s = value.fold(
          State(values = initialValue, initialValue = Box(initialValue))
        )( v => State(values = v, initialValue = Box(initialValue)))
        s
      }
      didMount = js.defined { self =>
        self.state.didMount.value = true
      }
      willUnmount = js.defined { self =>
        self.state.didMount.value = false
      }

      val reducer = (action, state, gen) => {
        // do we need this?, we are always in sync with changes
        val cvalues = value.getOrElse(state.values)
        action match {
          case Reset(nextvalues) =>
            gen.updateAndEffect(
              state.copy(
                values = nextvalues,
                isSubmitting = false,
                isValidating = false,
                errors = EmptyErrors,
                touched = EmptyTouches,
                submitCount = 0
              )) { self =>
              onChange.foreach(_(nextvalues, true))
            }

          // If eopt is provided, it replaces all validations.
          case Validating(f, eopt) =>
            gen.update(
              state.copy(isValidating = f,
                         errors = eopt.fold(state.errors)(identity)))

          // If you validate on change then blur on the form, you may run validations twice.
          case Touched(f, s) =>
            val newtouched = _touches.touch(f, state.touched)
            gen.updateAndEffect(state.copy(touched = newtouched)) { self =>
              val fut =
                if (s && validateOnBlur)
                  runValidations(self,
                                 self.state.values,
                                 self.state.validators.value,
                                 validate)
                else Future.successful(EmptyErrors)
              fut.onComplete {
                case _ => touched.foreach(_(Seq(f)))
              }
            }

          case FieldErrors(errs, merge) =>
            if (merge) {
              val newerrors = _errors.combine(state.errors, errs)
              gen.update(state.copy(errors = newerrors))
            } else
              gen.update(state.copy(errors = errs))

          case FieldValue(f, newvalues, s) =>
            gen.updateAndEffect(state.copy(values = newvalues)) {
              self =>
                val fut =
                  if (s && validateOnChange)
                    runValidations(self,
                                   self.state.values,
                                   self.state.validators.value,
                                   validate)
                      .map(errs => _errors.count(errs) == 0)
                  else Future.successful(false)
                fut.onComplete {
                  case Success(validationStatus) =>
                    onChange.foreach(_(newvalues, validationStatus))
                  case _ =>
                    // eats a thrown exception
                    onChange.foreach(_(newvalues, false))
                }
            }

          // Formik sets all touched value to true, why?
          case Submitting =>
            gen.updateAndEffect(
              state.copy(isSubmitting = true,
                         submitCount = state.submitCount + 1)
            ) {
              self =>
                runValidations(self, state.values, EmptyValidators, validate)
                  .flatMap { errs =>
                    if (_errors.count(errs) == 0)
                      submit.fold(
                        Future.unit
                      )(
                        _(state.values, makeFormActions(self, validate))
                      )
                    else Future.unit
                  }
                  .onComplete {
                    case _ => gen.update(state.copy(isSubmitting = false))
                  }
            }
        }
      }
      val render = self => {
        val cvalues = value.getOrElse(self.state.values)

        val factions = makeFormActions(self, validate)

        // compare initialValue to current values
        val dirty =
          !_values.eq(self.state.initialValue.value, cvalues)

        val _isValid =
          // do easy check before calling thunk
          if (dirty && _errors.count(self.state.errors) > 0) true
          else isInitialValid()

        val props = FormProps(
          initialValue = initialValue, //self.state.initialValue.value,
          values = cvalues,
          isValid = _isValid,
          dirty = dirty,
          errors = self.state.errors,
          touched = self.state.touched,
          handlers = makeFormHandlers(self, factions, reset),
          actions = factions,
          isValidating = self.state.isValidating,
          isSubmitting = self.state.isSubmitting,
          submitCount = self.state.submitCount,
          didMount = self.state.didMount.value
        )
        // create context for child components, if they want it
        val context = Context(props, self.state.initialValue.value, validate)
        FormContext.provider(context)(
          child(props)
        )
      }
    })
}
