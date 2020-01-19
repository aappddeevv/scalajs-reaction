
package router
package browser

import react._
import react.implicits._
import scala.scalajs.js

/** 
 * Subscribe to a RoutingSource and establish a react routing
 * context. Unsubscribe on unmount. Children can be anything but look at
 * `ShowIfMatch` or an inside component in any subclasses e.g. `Route` in
 * `ReactionRoutingDOMComponent`.
 */
abstract trait RoutingSourceComponent[Info <: scala.AnyRef, To] { self =>

  type RouterInfo <: RouterInfoLike

  trait RouterInfoLike {
    val info: Option[Info]
  }

  def makeContextValue(info: Option[Info]): RouterInfo

  val RouterContext: ReactContext[RouterInfo] =
    createContext[RouterInfo](makeContextValue(None))

  // specific to the class
  protected val routing: RoutingSource[Info, To]

  protected sealed trait NavAction
  protected case object Subscribe extends NavAction
  protected case class NewInfo(info: Info) extends NavAction

  type State = Option[Info]

  /** Override to change the name. */
  val Name = "RoutingSourceComponent"

  trait Props extends js.Object {
    var children: ReactNode
  }

  def apply(children_ : ReactNode) = sfc(new Props {
    var children = children_
  })

  val sfc = SFC1[Props] { props =>
    React.useDebugValue(Name)
    val (state, setState) = React.useStateStrictDirect[State](None)
    // subscribe to routing source on mount
    React.useEffectMounting{() =>
      val unsubscribe = routing.subscribe{ info => setState(Option(info))}
      // prime the pump
      routing.run(info => setState(Option(info)))
      (() => {
        unsubscribe()
      })
    }
    RouterContext.provide(makeContextValue(state))(
      props.children
    )
  }

  /**
   * If the predicate is true, show the child component and optionally alter the
   * context the child sees to reflect the match e.g. strip off the front
   * segment of the URL.
   */
  object ShowIfMatch {
    val Name = "ShowIfMatch"

    /**
     * @param child Delay child until needed.
     * @param cond Render if the cond results in true.
     * @param modify Modify the RouterInfo before using. Runs after the con check.
     */
    trait Props extends js.Object {
      var child: () => ReactNode
      var cond: RouterInfo => Boolean
      var modify: RouterInfo => RouterInfo // = identity
    }

    def apply(props: Props) = sfc(props)

    def apply(
      child_ : () => ReactNode,
      cond_ : RouterInfo => Boolean,
      modify_ : RouterInfo => RouterInfo = identity
    ) = sfc(new Props {
      var child = child_
      var cond = cond_
      var modify = modify_
    })

    val sfc = SFC1[Props]{ props =>
      import props._
      React.useDebugValue(Name)
      RouterContext.consume(routerInfo => {
        if(cond(routerInfo))
          RouterContext.provide(modify(routerInfo)) {
            child()
          }
          else null
      })
    }
  }
}








































































































