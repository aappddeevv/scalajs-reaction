// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react.examples
package changereduxstate

import scala.scalajs.js
import js.annotation._

import ttg.react._
import elements._
import ttg.react.implicits._
import vdom._
import prefix_<^._
import redux._
import fabric._
import fabric.components._
import prefix_F._

@js.native
@JSImport("Examples/changereduxstate/changereduxstate.css", JSImport.Namespace)
private object componentStyles extends js.Object

object styles {
  val cstyles = componentStyles.asInstanceOf[js.Dynamic]
}
import styles._

object ChangeReduxStateC {
  val c = statelessComponent("ChangeReduxState")
  import c.ops._

  trait ChangeReduxProps extends js.Object {
    var className: js.UndefOr[String] = js.undefined
    var label: js.UndefOr[String] = js.undefined
    var onLabelChange: js.UndefOr[js.Function1[String, Unit]] = js.undefined
  }

  private def _make(props: ChangeReduxProps) =
    c.copy(new methods {
      render = js.defined { self =>
        <.div(^.className := cstyles.component)(
          Label()("Redux Label"),
          TextField(new ITextFieldProps {
            className = cstyles.label.asString
            onChanged = js.defined((v: String) => props.onLabelChange.foreach(h => self.handle(_ => h(v))))
            value = props.label.getOrElse[String]("no redux label")
          })()
        )
      }
    })

  private val jsComponent = c.wrapScalaForJs { (jsProps: ChangeReduxProps) =>
    _make(jsProps)
  }
  private val reduxJsComponent = {
    val mapStateToProps =
      (state: js.Object, ownProps: ChangeReduxProps) =>
        new ChangeReduxProps {
          label = state.asDyn.view.label.asUndefOr
      }
    val mapDispatchToProps =
      (dispatch: Dispatcher, ownProps: ChangeReduxProps) =>
        new ChangeReduxProps {
          onLabelChange = js.defined((label: String) => dispatch(ActionsNS.ViewActions.setLabel(label)))
      }
    redux.connect(jsComponent, Some(mapStateToProps), Some(mapDispatchToProps))
  }

  def make(props: ChangeReduxProps = noProps()) =
    wrapJsForScala(reduxJsComponent, props)
}
