// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react.examples
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
case object FetchRequest extends Actions
case class FetchResult(result: Result) extends Actions
case class SelectionChanged(selection: ISelection[Address]) extends Actions

object AddressDetailC {
  val AddressDetail = statelessComponent("AddressDetail")
  def make(address: Option[Address]) =
    AddressDetail
      .withRender { self =>
        <.div(
          ^.className := amstyles.detail,
        )(
          fragmentElement()(
            Label()("Name"),
            Label()("City"),
          ),
          Label()("State/Province"),
          Label()("Zipcode"),
          Label()("Country"),
        )
      }
}

object AddressListC {
  import IColumn.toCol

  val icolumns = js.Array[js.Dynamic](
    lit("key" -> "name", "name" -> "Name", "fieldName" -> "name", "minWidth" -> 150),
    lit("key" -> "id", "name" -> "Id", "fieldName" -> "customeraddressid", "minWidth" -> 150),
  )

  val AddressList = statelessComponent("AddressList")
  def make(sel: ISelection[Address], addresses: AddressList = emptyAddressList) =
    AddressList
      .withRender { self =>
        val listopts = new IDetailsListProps[Address] {
          override val columns = icolumns
          val items = addresses.toJSArray
          override val getKey = getAddressKey
          override val selection = sel
        }
        <.div(
          ^.className := amstyles.master,
        )(DetailsList[Address](listopts)())
      }
}

object AddressManagerC {

  private[addressmanager] case class State(
      fetching: Boolean = false,
      message: Option[String] = None,
      addresses: js.Array[Address] = js.Array(),
      selection: ISelection[Address] = null,
      selectedKey: Option[String] = None,
  )

  val AddressManager = reducerComponentWithRetainedProps[State, NoRetainedProps, Actions]("AddressManager")

  def cbopts(self: AddressManager.Self) = new ICommandBarProps {
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

  def make(dao: AddressDAO) =
    AddressManager
      .withInitialState { () =>
        Some(State())
      }
      .withReducer { (action, sopt, gen) =>
        action match {
          case FetchRequest =>
            sopt
              .map(state => gen.update(Some(state.copy(fetching = true))))
              .getOrElse(gen.skip)
          case FetchResult(result) =>
            result match {
              case Left(msg) =>
                sopt
                  .map(state => gen.update(Some(state.copy(fetching = false, message = Some(msg)))))
                  .getOrElse(gen.skip)
              case Right(addresses) =>
                sopt
                  .map(state => gen.update(Some(state.copy(fetching = false, addresses = addresses))))
                  .getOrElse(gen.skip)
            }
          case SelectionChanged(sel) =>
            val selections = sel.getSelection()
            if (selections.length == 1 && selections(0).customeraddressid.isDefined)
              sopt
                .map(state => gen.update(Some(state.copy(selectedKey = Some(selections(0).customeraddressid.get)))))
                .getOrElse(gen.skip)
            else
              sopt
                .map(state => gen.update(Some(state.copy(selectedKey = None))))
                .getOrElse(gen.skip)
          case _ =>
            gen.skip
        }
      }
      .withDidMount { (self, gen) =>
        val cb = () =>
          self.handle { cbself =>
            cbself.state.foreach(state => self.send(SelectionChanged(state.selection)))
        }
        val sopt = Some(State(selection = new Selection(new ISelectionOptions[Address] {
          override val selectionMode = SelectionMode.single
          override val onSelectionChanged = js.defined(cb)
        })))
        gen.updateAndEffect(sopt, { cbself =>
          cbself.send(FetchRequest)
          dao.fetch("no id").then[Unit] { addresses =>
            cbself.send(FetchResult(Right(addresses)))
          }
        })
      }
      .withRender { self =>
        val selAddrOpt = for {
          state <- self.state
          id <- state.selectedKey
        } yield state.addresses.find(_.customeraddressid == id).head
        <.div()(
          CommandBar(cbopts(self))(
            ),
          <.div(
            ^.className := amstyles.component
          )(
            AddressListC.make(self.state.map(_.selection).get, self.state.map(_.addresses).getOrElse[AddressList](emptyAddressList)),
            AddressDetailC.make(selAddrOpt),
          )
        )
      }

  trait AddressManagerProps extends js.Object {
    val dao: js.UndefOr[AddressDAO]
  }

  @JSExportTopLevel("AddressManager")
  val exportedComponent = AddressManager.wrapScalaForJs { (jsProps: AddressManagerProps) =>
    require(jsProps.dao.isDefined)
    make(jsProps.dao.get)
  }
}
