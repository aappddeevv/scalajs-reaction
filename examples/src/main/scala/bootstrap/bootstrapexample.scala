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

package ttg.examples
package bootstrap

import scala.scalajs.js
import js.|
import org.scalajs.dom
import react._
import react.implicits._
import vdom._
import vdom.styling._
import bootstrap._
import bootstrap.components._

object BootstrapPage {
    val Name = "BootstrapPage"
  trait Props extends js.Object {
    var rootClassName: js.UndefOr[String] = js.undefined
  }

  def apply(props: Props) = render.elementWith(props)

  val render: ReactFC[Props] = props => {
    div(new DivProps {
      className = props.rootClassName
    })(
      "react-bootstrap 1.x-betas seem very rough right now...delaying implementation",
      BootstrapExample(new BootstrapExample.Props {
        val children =
          ButtonToolbar()(
            Button()("Default"),
            Button(new IButtonProps { `type` = ButtonType.`null`; variant = Variant.primary })("Primary"),
            Button(new IButtonProps { `type` = ButtonType.`null`; variant = Variant.success })("Success"),
            Button(new IButtonProps { `type` = ButtonType.`null`; variant = Variant.info    })("Info"),
            Button(new IButtonProps { `type` = ButtonType.`null`; variant = Variant.warning })("Warning"),
            Button(new IButtonProps { `type` = ButtonType.`null`; variant = Variant.danger  })("Danger"),
            Button(new IButtonProps { `type` = ButtonType.`null`; variant = Variant.link    })("Link")
          )
      }),
      BootstrapExample(new BootstrapExample.Props {
        val children = FormExample()
      })
    )
  }
  render.displayName(Name)
}

object BootstrapExample {

  trait Props extends js.Object {
    val children: ReactElement
  }

  def apply(props: Props) = render.elementWith(props)

  val render: ReactFC[Props] = props => {
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
  render.displayName("BootstrapExample")
}

object FormExample {
  sealed trait Action
  case class Change(value: Option[String]) extends Action

  case class State(value: Option[String] = None)

  def apply() = createElement(render, null)

  val render: ReactFC0 = () => {
    val (state, update) = useReducer[State, Action](
      (s, a) =>
        a match {
          case Change(vopt) => s.copy(value = vopt)
        },
      State()
    )
    form(
      FormGroup(new IFormGroupProps {
        controlId = "formBasicText"
        //validationState = getValidationState(self.state.value).getOrElse(null.asInstanceOf[ValidationState])
      })(
        FormLabel()("Working example with validation"),
        FormControl(new IFormControlProps {
          `type` = FormControlType.text
          as = As.input
          value = state.value.getOrElse("")
          placeholder = "Enter text"
          onChange = js.defined { v =>
            dom.console.log("FormControl: change value", v, js.typeOf(v))
          //self.send(Option(v))
          }
        })()
        //FormControl.Feedback()(),
        //HelpBlock()("Validation is based on string length"),
      )
    )
  }
  render.displayName("FormExample")

  def getValidationState(value: Option[String] = None) = {
    val len = value.map(_.length).getOrElse(-1)
    value match {
      case Some(x) if len > 10 => Option("success")
      case Some(x) if len > 5  => Option("warning")
      case Some(x) if len > 0  => Option("error")
      case _                   => None
    }
  }

}
