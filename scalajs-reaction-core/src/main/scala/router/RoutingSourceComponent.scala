// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package router

import scala.scalajs.js

import ttg.react
import react._
import elements._

/** 
 * Subscribe to a RoutingSource and establish a react context. Unsubscribe on
 * unmount. Note that this model is not very FP friendly in that we cannot
 * guarantee that a descendent has a related ancestor providing the context.
 * But hey, this is the classic design. Children can be anything but look at
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
    context.make[RouterInfo](makeContextValue(None))

  // should be in state
  protected val routing: RoutingSource[Info, To]

  protected sealed trait NavAction
  protected case object Subscribe extends NavAction
  protected case class NewInfo(info: Info) extends NavAction

  protected case class State(
    info: Option[Info] = None,
    // instance vars
    var unsubscribe: Option[() => Unit] = None
  )

  /** Override to change the name. */
  val Name = "RoutingSourceComponent"
  protected val c = reducerComponent[State, NavAction](Name)
  import c.ops._

  /** Create a new element instance from this component..
   * @param child Child component.
   */
  def apply(child: ReactNode) = c.copyWith(new methods {

    val initialState = self => State()

    // subscribe to routing source on mount
    didMount = js.defined { self =>
      self.state.unsubscribe match {
        case None => self.send(Subscribe)
        case _ => // subscription already active, bug?
      }
    }

    willUnmount = js.defined{ self => self.state.unsubscribe.foreach(_()) }

    val reducer = (action, state, gen) =>
    action match {
      case NewInfo(info) =>
        gen.update(state.copy(info = Option(info)))
      case Subscribe =>
        // unsubscribe if subscribed for some reason
        state.unsubscribe.foreach(_())
        state.unsubscribe = None
        gen.effect{ self =>
          self.state.unsubscribe = Option(
            routing.subscribe{ info =>
              self.send(NewInfo(info))
            })
          // prime the pump
          routing.run(i => self.send(NewInfo(i)))
        }
    }

    val render = self => {
      //println(s"$Name.render: BLAH, rerendering parent context")
      context.provider(RouterContext)(makeContextValue(self.state.info))(
        child
      )
    }
  })

  /**
   * If the predicate is true, show the child component and optionally alter the
   * context the child sees to reflect the match e.g. strip off the front
   * segment of the URL.
   */
  object ShowIfMatch {
    val Name = "ShowIfMatch"
    val c = statelessComponent(Name)
    import c.ops._

    /**
     * @param child Delay child until needed.
     * @param cond Render if the cond results in true.
     * @param modify Modify the RouterInfo before using. Runs after the cond check.
     */
    def apply(
      child: () => ReactNode,
      cond: RouterInfo => Boolean,
      modify: RouterInfo => RouterInfo = identity
    ) = render{ self =>
      context.consumer(RouterContext)(routerInfo => {
        if(cond(routerInfo))
          context.provider(RouterContext)(modify(routerInfo)) {
            child()
          }
          else null
      })
    }
  }

}
