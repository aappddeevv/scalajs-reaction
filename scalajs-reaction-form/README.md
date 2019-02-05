# Example
Here's a *not* very good example using this library. It will get refactored as
I get time to update this README.

```scala
object TestFormController {
  import DictFormController._
  import errors._

  val values = js.Dictionary[scala.Any](
    "firstname" -> "gregory",
    "lastname" -> "lampshire",
    "age" -> 51
  )

  def copy(value: js.Dictionary[scala.Any]) =
    react.copy[js.Object](value.asJsObj).asDict[scala.Any]

  def set(field: String, value: scala.Any, values: js.Dictionary[scala.Any]) = {
    val v = copy(values)
    v(field) = value
    v
  }

  def apply(): ReactElement = {
    DictFormController(
      submit = Some((_, _) => Future(println("submitted"))),
      reset = Some((_, _) => Future(println("reset"))),
      validate = Option { (values, fields) =>
        dom.console.log("validate", values)
        val fname = values
          .get("firstname")
          .map(_ == "greg")
          .filter(identity) // means error
          .map(_ =>
            collection.immutable.Map("firstname" -> "Name cannot be 'greg'"))
          .getOrElse(EmptyErrors)

        // simulate lastname checking
        val lname = EmptyErrors

        Future.successful(fname ++ lname)
      },
      initialValues = values
    ) { props =>
      form(new tags.FormProps {
        onSubmit = props.handlers.handleSubmit
        onReset = props.handlers.handleReset
        id = "testform1"
      })(
        Label()("firstname"),
        TextField(new ITextFieldProps {
          errorMessage = props.errors.get("firstname")
          value = props.values.get("firstname").getOrElse("").asString
          onChangeInput = js.defined { (_, v) =>
            val str = js.defined(v).filterTruthy
            props.handlers.handleChange("firstname",
                                        set("firstname",
                                            str.getOrElse(null),
                                            props.values))
          }
          onBlur = js.defined { ev =>
            props.handlers.handleBlur("firstname")
          }
          onKeyUp = js.defined { kevent =>
            if (kevent.keyCode == dom.ext.KeyCode.Escape) {
              props.actions
                .setFieldValue(
                  "firstname",
                  set(
                    "firstname",
                    props.initialValues.get("firstname").getOrElse("").asString,
                    values),
                  true)
            }
          }
        })(),
        Label()("lastname"),
        TextField(new ITextFieldProps {
          value = props.values.get("lastname").getOrElse("").asString
          onChangeInput = js.defined { (e, v) =>
            e.preventDefault()
            props.handlers.handleChange(
              "lastname",
              set("lastname",
                  js.defined(v).filterTruthy.getOrElse(null),
                  props.values))
          }
          onBlur = js.defined { ev =>
            props.handlers.handleBlur("lastname")
          }
        })(),
        Label()("age"),
        TextField(new ITextFieldProps {
          value = props.values
            .get("age")
            .map(_.toString())
            .getOrElse("")
            .asString
          onChangeInput = js.defined { (e, v) =>
            e.preventDefault()
            props.handlers.handleChange(
              "age",
              set("age", js.defined(v).filterTruthy.getOrElse(null), values))
          }
          onBlur = js.defined { ev =>
            props.handlers.handleBlur("age")
          }
        })(),
        Label()(s"Dirtiness: ${props.dirty}"),
        Label()(s"Validating: ${props.isValidating}"),
        Label()(s"Submitting: ${props.isSubmitting}"),
        // these can go outside the form
        DefaultButton(new IButtonProps {
          form = "testform1"
          `type` = "reset"
        })("Revert All"),
        DefaultButton(new IButtonProps {
          form = "testform1"
          `type` = "submit"
        })("Submit"),
        DefaultButton(new IButtonProps {
          form = "testform1"
        })("Cancel"),
      )
    }
  }
}
```
