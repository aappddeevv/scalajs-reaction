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
import ttg.react
import ttg.react._
import elements._
import reactdom._
import react.implicits._
import redux._
import react.redux
import vdom._
import vdom.tags._
import fabric._
import fabric.components._

import ReactContentLoaderComponents._

@js.native
@JSImport("Examples/addressmanager/addressmanager.css", JSImport.Namespace)
object componentStyles extends js.Object

object styles {
  val amstyles = componentStyles.asInstanceOf[js.Dynamic]
}

import styles._

sealed trait Actions
case object FetchRequest                                    extends Actions
case class FetchResult(result: Result)                      extends Actions
case class ActiveChanged(active: Option[(String, Address)]) extends Actions
case object Refresh                                         extends Actions

object AddressDetail {
  import examples.Contexts._
  import context._

  val c = statelessComponent("AddressDetail")
  import c.ops._

  def apply(address: Option[Address]) = render{ self =>
    logContext.makeConsumer {
      log =>
      //js.Dynamic.global.console.log("context: log function:", log)
      log(address.getOrElse("<no detail address provided>"))
      div(new DivProps { className = amstyles.detail.asString })(
        Label()(s"""Name: ${address.flatMap(_.name.toOption).getOrElse("")}"""),
        Label()(s"""City: ${address.flatMap(_.city.toOption).getOrElse("")}"""),
        Label()(s"""State/Province: ${address
                  .flatMap(_.stateorprovince.toOption)
                  .getOrElse("")}"""),
        Label()(s"""Zipcode: ${address.flatMap(_.postalcode.toOption).getOrElse("")}"""),
        Label()(s"""Country: ${address.flatMap(_.country.toOption).getOrElse("")}"""),
      )
    }
  }
}

object AddressList {
  import IColumn.toCol

  val icolumns = js.Array[js.Dynamic](
    lit("key" -> "name", "name" -> "Name", "fieldName" -> "name", "minWidth"              -> 150),
    lit("key" -> "id", "name"   -> "Id", "fieldName"   -> "customeraddressid", "minWidth" -> 150),
  )

  val c = statelessComponent("AddressList")
  import c.ops._

  def apply(
      sel: ISelection[Address],
      addresses: AddressList = emptyAddressList,
      activeCB: Option[Address] => Unit,
      ifx: Option[Int] = None) =
    render { self =>
      println(s"AddressListC.render: ifx ${ifx}")
      val listopts = new IDetailsListProps[Address] {
        items = addresses.toJSArray
        className = amstyles.list.asString
        selectionPreservedOnEmptyClick = true
        columns = icolumns
        getKey = getAddressKey
        //initialFocusedIndex = 2 //ifx.orUndefined
        onActiveItemChanged = js.defined({ (aundef, _, _) =>
          activeCB(aundef.toNonNullOption)
        })
        /* or
         onActiveItemChanged = { (aundef, _, _) =>
         activeCB(aundef.toNonNullOption)
         }: OAIC
         */
        selection = sel
        layoutMode = DetailsListLayoutMode.fixedColumns
        constrainMode = ConstrainMode.unconstrained
        // onRenderDetailsHeader = js.defined { (props, defaultRender) =>
        //   Sticky()(defaultRender.fold[ReactNode]("...render me...")(_(props)))
        // }
        onShouldVirtualize = js.defined(_ => false)
      }
      div.merge(lit("data-is-scrollable" -> true))(new DivProps {
        className = amstyles.master.asString
      })(ScrollablePane()(DetailsList[Address](listopts)()))
    }
}

/**
  * A props trait. We actually don't use this in the "make" to show how one needs
  * to break out parameters if your "make" does not take an unified props object.
  * But having this trait makes writing some interop a little easier.
  */
//@simplecreate
trait AddressManagerProps extends js.Object {
  val dao: AddressDAO
  // derived from redux
  var reduxLabel: js.UndefOr[String] = js.undefined
  // derived from redux if not provided
  var viewModel: js.UndefOr[AddressesViewModel]           = js.undefined
  var lastActiveAddressId: js.UndefOr[AddressesViewModel] = js.undefined
}

/**
  * Helper trait for some redux fiddling, we don't expose these to the world.
  * The attributes here come from redux although one could pass them down from a
  * parent if you wanted to.
  */
private[addressmanager] trait AddressManagerPropsRedux extends AddressManagerProps {
  var rstate: js.UndefOr[js.Dynamic] = js.undefined
  // added by default to props unless mapDispatchToProps is provided
  var dispatch: js.UndefOr[Dispatcher] = js.undefined
  var address: js.UndefOr[Address]     = js.undefined
}

object AddressManager {

  private[addressmanager] case class State(
      /** ignore selection changes. */
      var ignoreChanges: Boolean = false,
      fetching: Boolean = false,
      message: Option[String] = None,
      addresses: AddressList = emptyAddressList,
      /** instance var, so make mutable, might as well, null it's a js thing */
      var selection: ISelection[Address] = null
  )

  val c = reducerComponent[State, Actions]("AddressManager")
  import c.ops._

  private def cbopts(self: Self) = new CommandBar.Props {
    val isFetching = self.state.fetching
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
      new IContextualMenuItem {
        val key = "footerSize"
        name = "Incr Footer Height (CSS Var)"
        onClick = (() => {
          val pattern    = "([0-9]+)px".r
          val pattern(h) = vdom.styling.getCSSVar("--footer").trim
          val hint       = h.toInt
          val newHeight  = if (hint > 300) 80 else hint + 10
          // This is really as side effect that should force a re-render.
          // So I should really call into the reducer, but I'm lazy.
          vdom.styling.setCSSVar("--footer", s"${newHeight}px")
        }):IContextualMenuItem.OC0
        iconProps = lit("iconName" -> "Add")
      }
    )
    farItems = js.Array(
      if (self.state.fetching)
        new IContextualMenuItem {
          val key = "refresh"
          name = "Fetching..."
        }
      else
        new IContextualMenuItem {
          val key = "refresh"
          name = "Refresh"
          onClick = (() => {
            self.send(Refresh)
          }):IContextualMenuItem.OC0
          iconProps = lit("iconName" -> "Refresh")
        }
    )
  }

  private def fetchData(self: Self, dao: AddressDAO): Unit = {
    self.send(FetchRequest)
    dao
      .fetch("no id")
      .`then`[Unit] { addresses =>
        self.send(FetchResult(Right(addresses)))
      }
  }

  // safely change the selection without triggering a cycle
  private def silentlyChangeSelection(state: State, selection: ISelection[Address])(
      thunk: ISelection[Address] => Unit) = {
    try {
      state.ignoreChanges = true
      selection.setChangeEvents(false)
      thunk(selection)
      selection.setChangeEvents(true)
    } finally {
      state.ignoreChanges = false
    }
  }

  private def activecb(self: Self)(address: Option[Address]): Unit = {
    self.handle { cbself =>
      cbself.send(ActiveChanged(address.map(a => (a.customeraddressid.get, a))))
    }
  }

  /**
    * Instead of a non-native JS trait, we use explicit parameters. Our interop
    * wrappers must take this into account. Note that we cerated the "ViewModel"
    * as a test as we would not normally break up the parameters this way.
    */
  def apply(
      dao: AddressDAO,
      vm: AddressesViewModel,
      label: Option[String] = None,
      lastActiveAddressId: Option[Id] = None,
      className: Option[String] = None) =
    c.copy(new methods {

      val initialState =
        self => {
          val selcb = () => {
            self.handle { cbself =>
              println(
                s"AddressManager.selectionChanged: ${PrettyJson.render(cbself.state.selection.getSelection())}")
            // just log it, but here's how we could change it if we had a SelectionChanged event
            //self.handle { cbself => cbself.send(SelectionChanged(cbself.state.selection)) }
            }
          }
          val selection = new Selection(js.defined(new ISelectionOptions[Address] {
            getKey = getAddressKey
            selectionMode = SelectionMode.single
            onSelectionChanged = js.defined(selcb)
          }))
          State(selection = selection)
        }

      subscriptions = js.defined({ self =>
        js.Array(() => () => vm.setActive(null, null))
      })

      val reducer =
        (action, state, gen) => {
          action match {
            case FetchRequest =>
              gen.update(state.copy(fetching = true))
            case FetchResult(result) =>
              result match {
                case Left(msg) =>
                  println(s"fetch failed $msg")
                  gen.update(
                    state.copy(fetching = false, addresses = emptyAddressList, message = Some(msg)))
                case Right(addresses) =>
                  gen.updateAndEffect(
                    state.copy(fetching = false, addresses = addresses)){
                    self =>
                      silentlyChangeSelection(state, state.selection) { s =>
                        println("setting active after data fetch, but may not be the right time!")
                        js.Dynamic.global.console.log("active id", vm.activeId, addresses)
                        s.setItems(addresses, false)
                      }
                  }
              }
            case ActiveChanged(p) =>
              // Tricky...the only change here is outside our state, which is kind of
              // like a side effect, but not really. This queues a render.
              p.fold {
                vm.setActive(null, null)
              } {
                case (i, a) => vm.setActive(i, a)
              }
              //p.map { case (id, address) => gen.effect(_ => vm.setActive(id, address)) }
              //  .getOrElse(gen.effect(_ => vm.setActive(null, null)))
              gen.skip
            case Refresh =>
              // redux state is like this component's state, so...
              // we can change it here *not* as an effect
              vm.setActive(null, null) // this forces a render to be queued via redux
                                       // this forces a render as well
              gen.updateAndEffect(state.copy(addresses = emptyAddressList)){self =>
                fetchData(self, dao)
              }
            case _ => gen.skip
          }
        }

      didMount = js.defined({ self =>
        self.send(Refresh)
      })

      val render =
        self => {
          val ifx = lastActiveAddressId.map { id =>
            self.state.addresses.indexWhere(_.customeraddressid.map(_ == id).getOrElse(false))
          }
          println(s"AddressManager.render:initial focused index: ${ifx}")
          val selAddrOpt = toSafeOption(vm.active)

          div(new DivProps { className = cx(amstyles.component, className.getOrElse(null)) })(
            CommandBar(cbopts(self))(),
            div(new DivProps { className = amstyles.masterAndDetail.asString })(
              if (self.state.fetching)
                ReactContentLoaderComponents.BulletList(
                  new ReactContentLoaderOptions(
                    animate = true,
                    speed = 1,
                    className = amstyles.master.asString,
                    height = 500,
                    width = 500,
                    primaryColor = "#f3f3f3",
                    secondaryColor = "#ecebeb",
                  ))()
              else
                AddressList(self.state.selection, self.state.addresses, activecb(self), ifx),
              AddressDetail(selAddrOpt),
            ),
            div(new DivProps { className = amstyles.footer.asString })(
              AddressSummary(amstyles.footer.asUndefOr[String].toOption, selAddrOpt),
              Label()(
                "Redux sourced label: " + label.getOrElse[String]("<no redux label provided>")),
            )
          )
        }
    })

  /**
    * We illustrate using a "view model" that requires something from both
    * mapStateToProps and and mapDispatchToProps to show that you can do that
    * inside this function. This is rougghly equivalent to providing a
    * "mergeProps" argument to redux.connect.
    */
  @JSExportTopLevel("AddressManager")
  private val jsComponent = c.wrapScalaForJs { (jsProps: AddressManagerPropsRedux) =>
    val viewModel = jsProps.viewModel.getOrElse {
      mkReduxAddressesViewModel(jsProps.rstate.get.asJsObj, jsProps.dispatch.get)
    }
    apply(jsProps.dao,
         viewModel,
         jsProps.reduxLabel.toOption,
         toSafeOption(jsProps.lastActiveAddressId))
  }

  /**
    * Component that has been connected to redux. Note that since we are *not*
    * providing a mapDispatchToProps, react-redux automatically adds a "dispatch"
    * function to the props which we must model explicitly in
    * AddressManagerPropsRedux.
    */
  private val reduxJsComponent = {
    // we could also cast as AddressManagerProps, but here we just use a literal
    val mapStateToProps: MSTP[js.Object, AddressManagerPropsRedux] =
      (rstate, nextProps) => {
        //println(s"mapStateToProps (s,own): ${PrettyJson.render(rstate)}, ${PrettyJson.render(nextProps)}")
        lit(
          // everything is redundent with tis included, but for illustration purposes
          "rstate"              -> rstate,
          "reduxLabel"          -> rstate.asDyn.view.label,
          "lastActiveAddressId" -> rstate.asDyn.addressManager.lastActiveAddressId,
        ).asJsObjSub[AddressManagerPropsRedux]
      }
    redux.connect[js.Object, AddressManagerPropsRedux](jsComponent, Some(mapStateToProps))
  }

  /**
    * Using this "make" will us the component connected to redux. While you
    * could set the redux part of the "props" they will get automatically filled
    * in by redux so we only expose the base trait. Here, we only expose the
    * non-redux parts of the props interface which is why we split it up.
    */
  def withRedux(props: AddressManagerProps = noProps()) =
    wrapJsForScala(reduxJsComponent, props)

}
