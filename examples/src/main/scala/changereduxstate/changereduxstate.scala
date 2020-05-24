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

package ttg
package examples
package changereduxstate

import scala.scalajs.js

import js.annotation._
import js.|
import react._
import react.implicits._
import vdom._
import fabric._
import fabric.components._
import react_redux._

object styles {
  @js.native
  @JSImport("Examples/changereduxstate/changereduxstate.css", JSImport.Namespace)
  val cstyles: js.Object with js.Dynamic = js.native
}
import styles._

object ChangeReduxState {
  val Name = "ChangeReduxState"

  def apply() = render.element

  /** We are a bit wasteful with renders, should be more careful per the
   * redux-react docs.
   */
  val render: ReactFC0 = () => {
    val label_   = useSelector[GlobalAppState, String](_.view.label.flatMap(_.toUndefOr).getOrElse("no redux label"))
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
      })
    )
  }
  render.displayName(Name)
}
