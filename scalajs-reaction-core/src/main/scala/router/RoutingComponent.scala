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
 * A specific router's approach to routing typeclass. The default uses this
 * facade's approach but you can also use something like
 * [history](https://www.npmjs.com/package/history). By abstracting the
 * underlying strategy out, you can use the Config for other non-DOM
 * environments since they do not have document history. The underlying routing
 * subsystem should support the notion of a stack of requested routes--I know
 * this is very DOM'ish. This API is a bit crude and should be type of
 * observable reactive object that bakes in failure semantics more clearly. The
 * burden of remembering the last routing info is put on the RoutingComponent
 * instead of the source :-(
 * 
 * @tparam Info Routing event from an external thing such as an agent
 * e.g. someone types a URL into a class.
 * @tparam To Data needed to change the current route.
 * 
 * @todo Make this reactive and get rid of this awful API.
 */
trait RoutingSource[Info, To] {
  /** Subscribe to receiving routing notifications. */
  val subscribe: (Info => Unit) => () => Unit
  /** Push a route onto the conceptual set of routes. */
  val push: To => Unit
  /** Replace the top of the routing stack. */
  val replace: To => Unit
}

/**
 * Router component for your react tree. You can nest as many RouterComponents
 * as you want just recognize that RouterComponent is really designed to show
 * content based on the current history state, including the ability to swich
 * itself off rendering to a null react node. So think of this component as a
 * switch based on document location where the document location is a global
 * variable.
 * 
 */
abstract trait RouterComponent[Info, To] { self =>

  type Config <: ConfigLike
  type Control <: ControlLike

  val routing: RoutingSource[Info, To]
  val control: Control // not per render! saves allocs

  /** Callback arg used by components that hides router and navigation
   * implementation details. Not async!
   */
  trait ControlLike {
    /** Navigate to a url fragment via the router. If the push is the same as the
     * current path, don't push.
     */
    val navigate: To => Unit

    /** Redirect to another URL if its different than thte current URL. */
    val redirect: To => Unit
  }

  /** Render action that is a result of the rules. */
  sealed trait Action
  case class Render(run: Control => ReactNode) extends Action

  /**
   * Config holds the routing rules, an absolute function. There is no deferral to
   * a parent router but you can nest Router components with different configs.
   * The RouterComponent is not dependent on a specific history management
   * approach so it provides hooks to tie into the history mechanism of your
   * choice.
   */
  trait ConfigLike {
    /** Route rules. You may want to use a router matcher library
     * e.g. `sparsetech.trail` or pattern match on the path parts. Return an
     * action that renders to null to blank out the router component.
     */
    val rules: Info => Action
  }

  protected sealed trait NavAction
  protected case class Navigate(action: Action) extends NavAction

  protected case class State(action: Action)

  /** Override to change the name. */
  val Name = "RouterComponent"
  protected val c = reducerComponent[State, NavAction](Name)
  import c.ops._

  def apply(config: Config) =
    c.copy(new methods {
      val initialState = self => State(Render(_ => null))

      didMount = js.defined{ self =>
        val cb = routing.subscribe(info => self.send(Navigate(config.rules(info))))
        self.onUnmount(cb)
      }

      val reducer = (action, state, gen) => {
        action match {
          case Navigate(naction) =>
            gen.updateAndEffect(state.copy(action = naction)) { self =>
              // anything else to do?
            }
        }
      }

      val render = self => {
        self.state.action match {
          case Render(run) => run(control)
        }
      }
    })
}
