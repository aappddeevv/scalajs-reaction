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
import ttg.react.redux
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
case object Refresh extends Actions

object AddressDetailC {
  val AddressDetail = statelessComponent("AddressDetail")
  def make(address: Option[Address]) =
    AddressDetail
      .withRender { self =>
        <.div(
          ^.className := amstyles.detail,
        )(
          Label()(s"""Name: ${address.flatMap(_.name.toOption).getOrElse("")}"""),
          Label()(s"""City: ${address.flatMap(_.city.toOption).getOrElse("")}"""),
          Label()(s"""State/Province: ${address.flatMap(_.stateorprovince.toOption).getOrElse("")}"""),
          Label()(s"""Zipcode: ${address.flatMap(_.postalcode.toOption).getOrElse("")}"""),
          Label()(s"""Country: ${address.flatMap(_.country.toOption).getOrElse("")}"""),
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
          val items = addresses.toJSArray
          selectionPreservedOnEmptyClick = true
          columns = icolumns
          getKey = getAddressKey
          selection = sel
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
    addresses: AddressList = emptyAddressList,
    selectedAddress: Option[Address] = None,
    selection: ISelection[Address] = null // its a js thing
  )

  val AddressManager = reducerComponentWithRetainedProps[State, NoRetainedProps, Actions]("AddressManager")

  def cbopts(self: AddressManager.Self) = new ICommandBarProps {
    val isFetching = self.state.map(_.fetching).getOrElse(false)
    val items = js.Array(
      new IContextualMenuItem {
        val key = "new"
        name = "New"
        disabled = isFetching
        iconProps = lit("iconName" -> "Add")
      },
      new IContextualMenuItem {
        val key = "delete"
        name = "Delete"
        disabled = isFetching
        iconProps = lit("iconName" -> "Delete")
      },
    )
    farItems = js.Array(
      if(self.state.map(_.fetching).getOrElse(false))
        new IContextualMenuItem {
          val key = "refresh"
          name = "Fetching..."
        }
        else
        new IContextualMenuItem {
          val key = "refresh"
          name = "Refresh"
          onClick = { ()=> self.send(Refresh) } : OC0
          iconProps = lit("iconName" -> "Refresh")
        }
    )
  }

  def applyData(addresses: AddressList, ids: IdList, sel: ISelection[Address]): Unit = {
    println(s"appling selection ${ids}")
    sel.setItems(addresses, true)
    sel.setChangeEvents(false)
    ids.foreach(id => sel.setKeySelected(id, true, false))
    sel.setChangeEvents(true)
  }

  def fetchData(self: AddressManager.Self, dao: AddressDAO): Unit = {
    self.send(FetchRequest)
    dao.fetch("no id")
      .then[Unit] { addresses => self.send(FetchResult(Right(addresses))) }
  }

  def make(dao: AddressDAO, vm: AddressesViewModel, label: Option[String]) =
    AddressManager
      .withInitialState { () => Some(State()) }
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
                  .map{state =>
                    gen.updateAndEffect(Some(state.copy(fetching = false, addresses = addresses)),
                      _ => applyData(addresses, vm.getSelectedIds(), state.selection))
                  }
                  .getOrElse(gen.skip)
            }
          case SelectionChanged(sel) =>
            val selections = sel.getSelection()
            val stateUpdate = if (selections.length == 1 && selections(0).customeraddressid.isDefined)
              sopt.map(state => gen.updateAndEffect(Some(state.copy(selectedAddress = Some(selections(0)))),
                _ => vm.setSelectedIds(selections.map(address => address.customeraddressid.get))))
            else
              sopt.map(state => gen.updateAndEffect(Some(state.copy(selectedAddress = None)),
                _ => vm.setSelectedIds(js.Array())))
            stateUpdate.getOrElse(gen.skip)
          case Refresh =>
            // clear the address cache, not selection, fetch data
            gen.updateAndEffect(
              sopt.map(state => state.copy(addresses = emptyAddressList)),
              fetchData(_, dao))
        }
      }
  // self is needed to init the full state since "selection" requires a callback
  // so we do it in mount vs initialState
      .withDidMount { (self, gen) =>
        val cb = () =>
          self.handle { cbself =>
            cbself.state.foreach(state => self.send(SelectionChanged(state.selection)))
          }
        val selection = new Selection(js.defined(new ISelectionOptions[Address] {
            getKey = getAddressKey
            selectionMode = SelectionMode.single
            onSelectionChanged = js.defined(cb)
        }))
        val sopt = self.state.map(state => state.copy(selection = selection))
        gen.updateAndEffect(sopt, fetchData(_, dao))
      }
      .withRender { self =>
        <.div()(
          CommandBar(cbopts(self))(
            ),
          <.div(
            ^.className := amstyles.component
          )(
            Label()(label.getOrElse[String]("no redux label provided")),
            AddressListC.make(self.state.map(_.selection).get,
              self.state.map(_.addresses).getOrElse[AddressList](emptyAddressList)),
            AddressDetailC.make(self.state.flatMap(_.selectedAddress)),
          )
        )
      }

  trait AddressManagerProps extends js.Object {
    val dao: AddressDAO
    val viewModel: AddressesViewModel
  }

  @JSExportTopLevel("AddressManager")
  private val exportedComponent = AddressManager.wrapScalaForJs { (jsProps: ReduxAddressManagerProps) =>
    make(jsProps.dao, jsProps.viewModel, jsProps.reduxLabel.toOption)
  }

  // component that has been connected to redux
  val AddressManagerReduxJSComponent = {
    val msp = (state: js.Object, nextProps: js.Object) => {
      val stated = state.asInstanceOf[js.Dynamic]
      lit("reduxLabel" -> stated.view.label)
    }
    redux.connect(AddressManager, Some(msp))
  }

  trait ReduxAddressManagerProps extends AddressManagerProps {
    var reduxLabel: js.UndefOr[String] = js.undefined
   }

  // While you could set the redux part of the "props" they will get automatically
  // filled in by react-redux.
  def makeWithRedux(props: ReduxAddressManagerProps) =
    wrapJsForScala(AddressManagerReduxJSComponent, props)

}
