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
import org.scalajs.dom
import react.*
import react.syntax.*
import react.conversions.given
import jshelpers.syntax.*
import react.extras._
import vdom._
import fabric._
import fabric.components._
import fabric.utilities._
import ReactContentLoaderComponents._
import react_redux._

object styles {
  @js.native
  @JSImport("Examples/addressmanager/addressmanager.css", JSImport.Namespace)
  val amstyles: js.Object with js.Dynamic = js.native
}

import styles._

object AddressManager:
  trait Props extends js.Object:
    val dao: AddressDAO
    val className: js.UndefOr[String] = js.undefined

  val Name = "AddressManager"

  private def cbopts(isFetching: Boolean, refresh: () => Unit): CommandBar.Props =
    new CommandBar.Props {
      val items = js.Array(
        new IContextualMenuItem {
          val key = "new"
          text= "New"
          disabled = isFetching
          iconProps = lit("iconName" -> "Add")
        },
        new IContextualMenuItem {
          val key = "delete"
          text = "Delete"
          disabled = isFetching
          iconProps = lit("iconName" -> "Delete")
        },
        new IContextualMenuItem {
          val key = "footerSize"
          text = "Incr Footer Height (CSS Var)"
          onClick = IContextualMenuItem.OnClick(() => {
            val pattern    = "([0-9]+)px".r
            val pattern(h) = vdom.styling.getCSSVar("--footer").trim
            val hint       = h.toInt
            val newHeight  = if (hint > 300) 80 else hint + 10
            // This is really as side effect that should force a re-render.
            // So I should really call into the reducer, but I'm lazy.
            vdom.styling.setCSSVar("--footer", s"${newHeight}px")
          })
          iconProps = lit("iconName" -> "Add")
        }
      )
      farItems = js.Array(
        if (isFetching)
          new IContextualMenuItem {
            val key = "refresh"
            text = "Fetching..."
          } else
          new IContextualMenuItem {
            val key = "refresh"
            text = "Refresh"
            onClick = IContextualMenuItem.OnClick(() => refresh())
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

  /**
   * Instead of a non-native JS trait, we use explicit parameters. Our interop
   * wrappers must take this into account. Note that we cerated the "ViewModel"
   * as a test as we would not normally break up the parameters this way.
   */
  def apply(props: Props) = render.elementWith(props)

  val render: ReactFC[Props] = props => {
    // redux hooks
    val label = useSelector[GlobalAppState, js.UndefOr[String]](_.view.label)
    val lastActiveAddressId =
      useSelector[GlobalAppState, js.UndefOr[Id]](_.addressManager.lastActiveAddressId.toUndefOr)
    val activeId  = useSelector[GlobalAppState, js.UndefOr[Id]](_.addressManager.activeId.toUndefOr)
    val active    = useSelector[GlobalAppState, js.UndefOr[Address]](_.addressManager.active.toUndefOr)
    val dispatchG = useDispatch[GlobalAppAction]()
    val setActive = useCallback2[Id | Null, Address | Null, Unit](dispatchG)((id, addr) =>
        dispatchG(ActionsNS.AddressManagerActions.setActive(id.asJsAny, addr.asJsAny).asInstanceOf[GlobalAppAction])
    )

    val sref = useExpensiveRef[ISelection[Address]]{
        new Selection[Address](new Selection.Options[Address] {
            getKey = Selection.GetKey(getAddressKey)
            selectionMode = SelectionMode.single
        })
    }

    useEffect(sref()){() => 
        val events = new EventGroup()
            events.on(sref(), "change", _ => { 
                sref().getSelection().headOption.fold(
                    setActive(null,null))(
                    addr => setActive(addr.customeraddressid.get, addr) // :-) with get
                    )
                
        })
        () => events.dispose()
    }
    
    val (fetchState, doFetch) = useFetch(props.dao, sref().setItems(_, true))
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
      val footer = amstyles.selectDynamic("footer").asInstanceOf[js.UndefOr[String]]
      (AddressDetail(t), AddressSummary(footer.toOption, t))
    })

    val commandBar = useMemo(unsafeDeps(fetchState.loading, doFetch, setActive))(() => {
      dom.console.log(s"$Name: Calculating props", fetchState.loading)
      CommandBar(cbopts(fetchState.loading, () => { setActive(null, null); doFetch() }))
    })

    divWithClassname(
      cx(amstyles.component, props.className.getOrElse(null)),
      commandBar,
      divWithClassname(
        amstyles.masterAndDetail.asString,
        AddressList(new AddressList.Props {
          var sel       = sref()
          var addresses = fetchState.data
          var ifx       = ifx_
          var shimmer   = fetchState.loading
        }),
        addressStuff._1
      ),
      divWithClassname(
        amstyles.footer.asString,
        addressStuff._2,
        Label("Redux sourced label: " + label.getOrElse("<no redux label provided>"))
      )
    )
  }
  render.displayName(Name)

end AddressManager
