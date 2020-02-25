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
package addressmanager

import scala.scalajs.js

import js.Dynamic.{ literal => lit }
import js.JSConverters._
import js.annotation._
import js.|

import org.scalajs.dom

import react._

import implicits._

import vdom._
import vdom.tags._

import fabric._
import fabric.components._

import ReactContentLoaderComponents._
import react_redux._

@js.native
@JSImport("Examples/addressmanager/addressmanager.css", JSImport.Namespace)
object componentStyles extends js.Object

object styles {
  val amstyles = componentStyles.asInstanceOf[js.Dynamic]
}

import styles._

object AddressManager {
  trait Props extends js.Object {
    val dao: AddressDAO
    val className: js.UndefOr[String] = js.undefined
  }

  val Name = "AddressManager"

  private def cbopts(isFetching: Boolean, refresh: () => Unit): CommandBar.Props =
    new CommandBar.Props {
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
          }): IContextualMenuItem.OC0
          iconProps = lit("iconName" -> "Add")
        }
      )
      farItems = js.Array(
        if (isFetching)
          new IContextualMenuItem {
            val key = "refresh"
            name = "Fetching..."
          } else
          new IContextualMenuItem {
            val key = "refresh"
            name = "Refresh"
            onClick = (() => refresh()): IContextualMenuItem.OC0
            iconProps = lit("iconName" -> "Refresh")
          }
      )
    }

  case class FetchState(
    loading: Boolean = false,
    error: Option[String] = None,
    data: AddressList = emptyAddressList
  )

  // hook to fetch data, todo convert to using components.fetch.Fetcher
  val useFetch: js.Function2[AddressDAO, AddressList => Unit, (FetchState, () => Unit)] = (dao, cb) => {
    // track # of requests and drive refresh
    val (request, setRequest) = useStateStrictDirect[Int](0)
    val (state, setState)     = useStateStrictDirect[FetchState](FetchState())

    useEffect(request) { () =>
      println("Fetching address data...")
      setState(state.copy(loading = true))
      dao
        .fetch("no id")
        .`then`[Unit](
          d => {
            setState(state.copy(loading = false, error = None, data = d))
            cb(d)
          },
          js.defined(_ match {
            case e: js.Error =>
              setState(state.copy(loading = false, data = emptyAddressList, error = Option(e.message)))
              cb(emptyAddressList)
            case _ =>
              setState(state.copy(loading = false, data = emptyAddressList, error = Option("unknown error")))
              cb(emptyAddressList)
          })
        )
      ()
    }
    // fetch state, "make request"
    (state, () => setRequest(request + 1))
  }

  // Side effect setting the new selection. Turn off notification
  // change events because this can be called inside a render and that's
  // totally what's wrong with this approach.
  private def changeSelection(selection: ISelection[Address], item: Option[Address]): Unit =
    item.foreach(
      address =>
        try {
          selection.setChangeEvents(false, true)
          selection.setItems(js.Array(address), false)
        } finally {
          selection.setChangeEvents(true, false)
        }
    )

  // create lazily. Callback just logs selection to console.
  def createSelection(cb: () => Unit) =
    new Selection[Address](js.defined(new ISelectionOptions[Address] {
      getKey = getAddressKey
      selectionMode = SelectionMode.single
      onSelectionChanged = js.defined(cb)
    }))

  /**
   * Instead of a non-native JS trait, we use explicit parameters. Our interop
   * wrappers must take this into account. Note that we cerated the "ViewModel"
   * as a test as we would not normally break up the parameters this way.
   */
  def apply(props: Props) = sfc(props)

  val sfc = SFC1[Props] { props =>
    // redux hooks
    val label = useSelector[GlobalAppState, js.UndefOr[String]](_.view.label.flatMap(_.toUndefOr))
    val lastActiveAddressId =
      useSelector[GlobalAppState, js.UndefOr[Id]](_.addressManager.lastActiveAddressId.toUndefOr)
    val activeId  = useSelector[GlobalAppState, js.UndefOr[Id]](_.addressManager.activeId.toUndefOr)
    val active    = useSelector[GlobalAppState, js.UndefOr[Address]](_.addressManager.active.toUndefOr)
    val dispatchG = useDispatch[GlobalAppAction]()
    val setActive = useCallback[Id | Null, Address | Null, Unit](dispatchG)(
      (id, addr) =>
        dispatchG(ActionsNS.AddressManagerActions.setActive(id.asJsAny, addr.asJsAny).asInstanceOf[GlobalAppAction])
    )

    // react hooks
    // see https://github.com/OfficeDev/office-ui-fabric-react/issues/9882
    // we solve it via a lazy val
    lazy val selection: ISelection[Address] =
      useMemo[ISelection[Address]](deps(setActive))(
        () =>
          createSelection { () =>
            dom.console.log(
              s"$Name: Selection.onSelectionChanged(): notification via Selection object!",
              selection.getSelection()
            )
            val selected = selection.getSelection().headOption
            selected.fold(
              // None
              setActive(null, null)
            )(
              // Some
              addr => setActive(addr.customeraddressid.get, addr) // :-) with get
            )
          }
      )
    val (fetchState, doFetch) = useFetch(props.dao, selection.setItems(_, true))
    dom.console.log(s"$Name: loading", fetchState.loading)
    useEffectMounting { () =>
      doFetch()
      (() => setActive(null, null))
    }

    val ifx_ = lastActiveAddressId.map { id =>
      fetchState.data.indexWhere(_.customeraddressid.map(_ == id).getOrElse(false))
    }.toOption

    val addressStuff = useMemo(active)(() => {
      val t = active.toNonNullOption
      (AddressDetail(t), AddressSummary(amstyles.footer.asUndefOr[String].toOption, t))
    })

    val commandBar = useMemo(unsafe_deps(fetchState.loading, doFetch, setActive))(() => {
      dom.console.log(s"$Name: Calculating props", fetchState.loading)
      CommandBar(cbopts(fetchState.loading, () => { setActive(null, null); doFetch() }))
    })

    divWithClassname(
      cx(amstyles.component, props.className.getOrElse(null)),
      commandBar,
      divWithClassname(
        amstyles.masterAndDetail.asString,
        AddressList(new AddressList.Props {
          var sel       = selection
          var addresses = fetchState.data
          var ifx       = ifx_
          var shimmer   = fetchState.loading
        }),
        addressStuff._1
      ),
      divWithClassname(
        amstyles.footer.asString,
        addressStuff._2,
        Label("Redux sourced label: " + label.getOrElse[String]("<no redux label provided>"))
      )
    )
  }
}
