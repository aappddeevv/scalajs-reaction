// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package examples
package changereduxstate

import scala.scalajs.js
import js.annotation._
import js.|

import _root_.react._
import implicits._
import vdom._
import tags._
import react_redux._
import fabric._
import fabric.components._

@js.native
@JSImport("Examples/changereduxstate/changereduxstate.css", JSImport.Namespace)
private object componentStyles extends js.Object

object styles {
  val cstyles = componentStyles.asInstanceOf[js.Dynamic]
}
import styles._

object ChangeReduxState {
  val Name = "ChangeReduxState"

  def apply() = sfc

  /** We are a bit wasteful with renders, should be more careful per the
    * redux-react docs.
   */
  val sfc = SFC0 {
    React.useDebugValue(Name)
    val label_ = useSelector[GlobalAppState, String](
      _.view.label.flatMap(_.toUndefOr).getOrElse("no redux label"))
    val dispatch = useDispatch[GlobalAppAction]()

    println(s"label from redux state: $label_")
    div(new DivProps {
      className = cstyles.component.asString
    })(
      TextField(new TextField.Props {
        label = "Redux Label in Global App State"
        description = "The label typed here is displayed elsewhere in the examples."
        className = cstyles.label.asString
        onChangeInput = js.defined((_, v) => dispatch(ActionsNS.ViewActions.setLabel(v).asInstanceOf[GlobalAppAction]))
        defaultValue = label_
      })()
    )
  }
}
