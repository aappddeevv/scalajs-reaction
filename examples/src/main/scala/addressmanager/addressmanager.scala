// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package examples
package addressmanager

import scala.scalajs.js
import js.Dynamic.{literal => lit}
import js.annotation._
import js.JSConverters._

import org.scalajs.dom
import ttg.react._
import ttg.react.elements._
import ttg.react.reactdom._
import ttg.react.implicits._
import ttg.react.fabric
import vdom._
import prefix_<^._
import fabric._
import fabric.components._

@js.native
@JSImport("Examples/addressmanager/addressmanager.css", JSImport.Namespace)
object componentStyles extends js.Object

object styles {
  val amstyles = componentStyles.asInstanceOf[js.Dynamic]
}

import styles._

sealed trait Actions

object AddressDetailC {
  val AddressDetail = statelessComponent("AddressDetail")
  def make() =
    AddressDetail
      .withRender { self =>
        <.div(
          ^.className := amstyles.detail,
        )(
          Label()("Name"),
          Label()("City"),
          Label()("State/Province"),
          Label()("Zipcode"),
          Label()("Country"),
        )
      }
}

object AddressListC {
  import IColumn.toCol

  val icolumns = Seq[js.Dynamic](
    lit("key" -> "name", "name" -> "Name", "fieldName" -> "name", "minWidth" -> 150),
    lit("key" -> "id", "name" -> "Id", "fieldName" -> "customeraddressid", "minWidth" -> 150),
  ).toJSArray

  val AddressList = statelessComponent("AddressList")
  def make(items: Seq[Address] = Nil) =
    AddressList
      .withRender { self =>
        val listopts = new IDetailsListProps {
          override val columns = icolumns
          val items = js.Array()
        }
        <.div(
          ^.className := amstyles.master,
        )(DetailsList(listopts)())
      }
}

object AddressManagerC {
  case class State()

  val AddressManager = reducerComponentWithRetainedProps[State, NoRetainedProps, Actions]("AddressManager")
  def make() =
    AddressManager
      .withInitialState { self =>
        Some(State())
      }
      .withRender { self =>
        val cbopts = new ICommandBarProps {
          val items = js.Array(
            new IContextualMenuItem {
              val key = "new"
              override val name = "New"
              override val disabled = false
              override val iconProps = lit("iconName" -> "Add")
            },
            new IContextualMenuItem {
              val key = "delete"
              override val name = "Delete"
              override val disabled = false
              override val iconProps = lit("iconName" -> "Delete")
            },
          )
          val farItems = js.Array(
            new IContextualMenuItem {
              val key = "refresh"
              override val name = "Refresh"
              override val disabled = false
              override val onClick = (() => println("refresh me!")): js.Function0[Unit]
              override val iconProps = lit("iconName" -> "Refresh")
            },
          )
        }

        <.div()(
          CommandBar(cbopts)(
            ),
          <.div(
            ^.className := amstyles.component
          )(
            AddressListC.make(Seq()),
            AddressDetailC.make(),
          ))
      }

  @JSExportTopLevel("AddressManager")
  val exportedComponent = AddressManager.wrapScalaForJs((jsProps: js.Object) => make())
}
