// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react.examples
package bootstrap

import scala.scalajs.js
import js.|
import org.scalajs.dom
import ttg.react
import react._
import elements._
import react.implicits._
import vdom._
import vdom.styling._
import vdom.tags._

import react.bootstrap._
import react.bootstrap.components._

object BootstrapPage {

  val c = statelessComponent("BootstrapPage")
  import c.ops._

  def apply(
    rootClassName: js.UndefOr[String] = js.undefined
  ) = render { self =>
    div(new DivProps {
      className = rootClassName
    })(
      "react-bootstrap 1.x-betas seem very rough right now...delaying implementation",
      BootstrapExample(
        ButtonToolbar()(
          Button()("Default"),
          Button(new IButtonProps{ `type`=ButtonType.`null`; variant=Variant.primary})("Primary"),
          Button(new IButtonProps{ `type`=ButtonType.`null`; variant=Variant.success})("Success"),
          Button(new IButtonProps{ `type`=ButtonType.`null`; variant=Variant.info})("Info"),
          Button(new IButtonProps{ `type`=ButtonType.`null`; variant=Variant.warning})("Warning"),
          Button(new IButtonProps{ `type`=ButtonType.`null`; variant=Variant.danger})("Danger"),
          Button(new IButtonProps{ `type`=ButtonType.`null`; variant=Variant.link})("Link")
        )
      ),
      BootstrapExample(
        FormExample()
      )
    )
  }
}

object BootstrapExample {
  val c = statelessComponent("BootstrapExample")
  import c.ops._

  def apply(
    child: ReactElement
  ) = render { self =>
    div(new DivProps {
      className = "ttg-bootstraExample"
      style = new StyleAttr {
        marginTop = 10
        marginBottom = 10
      }
    })(
      child
    )
  }
}

object FormExample {
  sealed trait Action
  case class Change(value: Option[String]) extends Action
  case class State(value: Option[String] = None)

  val c = reducerComponent[State,Action]("FormExample")
  import c.ops._

  def apply(
  ) = c.copy(new methods {
    val initialState = _ => State()
    val reducer = (action, state, gen) => action match {
      case Change(vopt) => gen.update(state.copy(value = vopt))
    }

    val render = self => {
      form(
        FormGroup(new IFormGroupProps {
          controlId="formBasicText"
          //validationState = getValidationState(self.state.value).getOrElse(null.asInstanceOf[ValidationState])
        })(
          FormLabel()("Working example with validation"),
          FormControl(new IFormControlProps {
            `type`= FormControlType.text
            as = As.input
            value = self.state.value.getOrElse("")
            placeholder="Enter text"
            onChange=js.defined{ v =>
              dom.console.log("FormControl: change value", v, js.typeOf(v))
              //self.send(Option(v))
            }
          })(),
          //FormControl.Feedback()(),
          //HelpBlock()("Validation is based on string length"),
        ))
    }
  })

  def getValidationState(value: Option[String] = None) = {
    val len = value.map(_.length).getOrElse(-1)
    value match {
      case Some(x) if len > 10 => Option("success")
      case Some(x) if len > 5 => Option("warning")
      case Some(x) if len > 0 => Option("error")
      case _ => None
    }
  }

}
