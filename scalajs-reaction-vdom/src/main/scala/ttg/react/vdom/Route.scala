// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package vdom

import scala.scalajs.js
import org.scalajs.dom

import ttg.react
import react._
import elements._

import react.router
import router._

/**
 * Simple Route component that matches on reactjs-reaction's basic history API
 * abstraction and a simple predicate to turn render a child or null. If you
 * want something that can handle more complex rules, use ReactionRouter. This
 * component can both redirect and render a child if the predicate is true.
 */
object Route {

  case class State(
    /** If last test was true, parts matched on. */
    lastMatchOn: Option[PathParts] = None
  )

  sealed trait Action
  case class RouteChanged(path: PathParts) extends Action

  /** Action that the "child" takes. Use currying or composition to push your own
   * props into the child.
   */
  sealed trait ChildAction
  /** Redirect. */
  case class RedirectTo(path: String, method: RedirectMethod) extends ChildAction
  /** Given the current route and a navigation callback, render a child. */
  case class Render(thunk: (PathParts, (String, RedirectMethod) => Unit) => ReactNode) extends ChildAction

  def redirect(to: String, method: RedirectMethod) =
    method match {
      case RedirectMethod.Push => push(to)
      case RedirectMethod.Replace => replace(to)
      case RedirectMethod.Reload => dom.window.location.href = to
    }

  val Name = "Route"
  val c = reducerComponent[State, Action](Name)
  import c.ops._

  def apply[P](
    test: PathParts => Boolean,
    child: ChildAction
  ) = c.copyWith(new methods {

    val initialState = _ => State()

    /** Redirects are handled directly in the router. */
    val reducer = (action, state, gen) =>
    action match {
      case RouteChanged(parts) =>
        val matched = test(parts)
        val last = if(matched) Option(parts) else None
        child match {
          case RedirectTo(to, method) =>
            gen.updateAndEffect(State()) { _ =>
              redirect(to, method)
            }
          case _ =>
            gen.update(state.copy(lastMatchOn = last))
        }
    }

    // run predicate when URL changes and on initial mount
    didMount = js.defined { self =>
      val runchange: PathParts => Unit = pp => self.send(RouteChanged(pp))
      val id = watchUrl(runchange)
      runchange(dangerouslyGetUrl())
      self.onUnmount(() => unwatchUrl(id))
    }

    val render = self => {
      when(self.state.lastMatchOn.isDefined)(child match {
        case Render(thunk) =>
          thunk(router.dangerouslyGetUrl(), (to, method) => redirect(to, method))
        case _ =>
          // this should have been handled in the router
          null
      })
    }
  })
}
