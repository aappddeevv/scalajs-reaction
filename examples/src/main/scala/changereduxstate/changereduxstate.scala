// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react.examples
package changereduxstate

import scala.scalajs.js

import ttg.react._
import elements._
import ttg.react.implicits._
import vdom._
import prefix_<^._
import redux._
import fabric._
import fabric.components._

object ChangeReduxStateC {
  private val c = statelessComponent("ChangeReduxState")

  trait ChangeReduxProps extends js.Object {
    var className: js.UndefOr[String] = js.undefined
    var label: js.UndefOr[String] = js.undefined
    var onLabelChange: js.UndefOr[js.Function1[String, Unit]] = js.undefined
  }

  private def _make(props: ChangeReduxProps) =
    c.
      withRender{self =>
        <.div(^.className := "blah")(
          props.label.getOrElse[String]("no redux label")
        )
      }

  private val jsComponent = c.wrapScalaForJs { (jsProps: ChangeReduxProps) =>
    _make(jsProps)
  }
  private val reduxJsComponent = {
    val mapStateToProps =
      (state: js.Object, ownProps: js.Object) => new ChangeReduxProps {
        label = state.asDyn.view.label.asUndefOr
      }
    val mapDispatchToProps =
      (dispatch: Dispatcher, ownProps: js.Object) => new ChangeReduxProps {
        onLabelChange = js.defined((label: String) => dispatch(ActionsNS.ViewActions.setLabel(label)))
      }
    redux.connect(jsComponent, Some(mapStateToProps), Some(mapDispatchToProps))
  }

  def make(props: ChangeReduxProps = noProps()) =
    wrapJsForScala(reduxJsComponent, props)
}
