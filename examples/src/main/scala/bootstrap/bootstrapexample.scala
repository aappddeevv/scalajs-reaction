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

  trait Props extends js.Object {
   var rootClassName: js.UndefOr[String] = js.undefined
  }

  def apply(props: Props) = sfc(props)

  val sfc = SFC1[Props]{ props =>
    div(new DivProps {
      className = props.rootClassName
    })(
      "react-bootstrap 1.x-betas seem very rough right now...delaying implementation",
      BootstrapExample( new BootstrapExample.Props {
        val children = 
        ButtonToolbar()(
          Button()("Default"),
          Button(new IButtonProps{ `type`=ButtonType.`null`; variant=Variant.primary})("Primary"),
          Button(new IButtonProps{ `type`=ButtonType.`null`; variant=Variant.success})("Success"),
          Button(new IButtonProps{ `type`=ButtonType.`null`; variant=Variant.info})("Info"),
          Button(new IButtonProps{ `type`=ButtonType.`null`; variant=Variant.warning})("Warning"),
          Button(new IButtonProps{ `type`=ButtonType.`null`; variant=Variant.danger})("Danger"),
          Button(new IButtonProps{ `type`=ButtonType.`null`; variant=Variant.link})("Link")
        )
      }),
      BootstrapExample(new BootstrapExample.Props {
        val children = FormExample()
      })
    )
  }
}

object BootstrapExample {

  trait Props extends js.Object {
    val children: ReactElement
  }

  def apply(props: Props) = sfc(props)

  val sfc = SFC1[Props]{props =>
    div(new DivProps {
      className = "ttg-bootstraExample"
      style = new StyleAttr {
        marginTop = 10
        marginBottom = 10
      }
    })(
      props.children
    )
  }
}

object FormExample {
  sealed trait Action
  case class Change(value: Option[String]) extends Action

  case class State(value: Option[String] = None)

  def apply() = sfc

  val sfc = SFC0 {
    val (state, update) = React.useReducer[State,Action](
      (s,a) => a match {
        case Change(vopt) => s.copy(value = vopt)
      },
      State()
    )
    form(
      FormGroup(new IFormGroupProps {
        controlId="formBasicText"
        //validationState = getValidationState(self.state.value).getOrElse(null.asInstanceOf[ValidationState])
      })(
        FormLabel()("Working example with validation"),
        FormControl(new IFormControlProps {
          `type`= FormControlType.text
          as = As.input
          value = state.value.getOrElse("")
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
