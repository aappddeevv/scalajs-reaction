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
 * A specific abstraction of a "routers" source of routing events. You can use
 * reaction's router but you can also use something like
 * [history](https://www.npmjs.com/package/history) by defining your own
 * RoutingSource. The underlying routing subsystem should support the notion of
 * a stack of requested routes--yes, very DOM'ish. This API is a bit crude and
 * should be type of observable reactive object that bakes in failure semantics
 * more clearly. The burden of remembering the last routing info is put on the
 * RoutingComponent instead of the RoutingSource where it belongs :-(.
 * 
 * @tparam Info Routing event from an external thing such as an agent
 * e.g. someone types a URL into a class.
 * @tparam To Data needed to change the current route.
 * 
 * @todo Make this reactive and get rid of this awful API.
 */
trait RoutingSource[Info, To] {
  /** Subscribe to receiving routing notifications. Return unsubscribe thunk. */
  val subscribe: (Info => Unit) => (() => Unit)
  /** Push a route onto the conceptual set of routes. */
  val push: To => Unit
  /** Replace the top of the routing stack. */
  val replace: To => Unit
  /** Example, set window.href to force a reload. Means different things to
   * different sources. And it may interpret `To` differently e.g. expect a full
   * To specification.>
   */
  val reload: To => Unit
  /** Run a callaback with the current Info. */
  val run: (Info => Unit) => Unit
}

/**
 * Router component for your react tree. You can nest as many RouterComponents
 * as you want just recognize that RouterComponent is really designed to show
 * content based on the current "info" state, including the ability to switch
 * itself off--rendering to a null react node. So think of this component as a
 * switch based on "info". You could then use this component to handle rendering
 * and "redirects."  The component is agnostic to what it is routing and the
 * format/semantics of the routing information. Subclasses are responsible for
 * defining those semantics.
 * 
 * Defining your routing config statically works for static routes that have
 * components that stand on their own. If you need to accept props from a parent
 * component and have a child component of a router use those props, you can
 * compose a component with a Fragment or use a config created using the parent
 * props and and set the config into the router component when it is created.
 * In other words, your router config can be the result of a function call to
 * create it. These are all standard FP techniques.
 * 
 * @tparam Info The request info pushed from the routing source.
 * @tparam To A value representing where to go to.
 * 
 */
abstract trait RouterComponent[Info, To] { self =>

  type Config <: ConfigLike
  type Control <: ControlLike
  type Navigator = (To, Redirect.Method) => Unit

  val routing: RoutingSource[Info, To]
  //val control: Control // not per render! saves allocs

  /** Make a control. */
  def makeControl(config: Config): Control

  /** Callback arg used by components that hides router and navigation
   * implementation details. Not async! When your action is render, you receive
   * a Control as a parameter that you pass to your child component so they can
   * perform navigation actions, such as render a link element. You can curry
   * the navigate function so that components can be fed a simpler To => Unit
   * callback.
   */
  trait ControlLike {
    /** Navigate to "to" using a method. This is a callback into the router.
     */
    val navigate: Navigator
  }

  protected val noop = () => ()

  /** Take some action as the result of processing a rule. */
  sealed trait Action extends Product with Serializable {
  }

  /** Render a node allowing that node to use Controls to create "links". */
  case class Render(run: Control => ReactNode, effect: (() => Unit) = noop) extends Action {
    def apply(c: Control) = run(c)
  }

  /** Go to another page with a specific method to get there. */
  case class RedirectTo(to: To, method: Redirect.Method, effect: (() => Unit) = noop) extends Action {
  }

  /** Small rules DSL. Create a rule that can return no action. */
  case class rule(run: Info => Option[Action]) {
    def orElse(next: Info => Option[Action]) = rule { info =>
      run(info) orElse next(info)
    }
    def |(next: Info => Option[Action]) = orElse(next)
    def when(cond: Info => Boolean) = rule { info =>
      if(cond(info)) run(info) else None
    }
    def modify(change: Info => Info) = rule { info => run(change(info)) }
    def void = rule { _ => None }
    /** Modify this rule so it cannot fail. */
    def toRules(fallback: Info => Action) =
      rules(info => run(info).getOrElse(fallback(info)))
  }

  /**
   * If your rule needs a Control for rendering a `ReactNode`, the `Render`
   * action takes a `Control => ReactNode` so essentially you have, for
   * rendering, a `Info => Control => ReactNode`.
   */
  //case class Rules(run: Info => Action)
  type Rules = Info => Action

  /** Create rules, you don't need to use this. Provides semantic gesture. */
  def rules(run: Info => Action): Rules = run // = Rules(run)
  /** Create a rule that ignores the Info inpt. Provides semantic gesture. */
  def always(always: => Action): Rules = _ => always

  /**
   * Config holds the routing rules, an absolute function. There is no deferral to
   * a parent router but you can nest Router components with different configs.
   * The RouterComponent is not dependent on a specific history management
   * approach so it provides hooks to tie into the history mechanism of your
   * choice using `RoutingSource`.
   */
  trait ConfigLike {
    /** Route rules. You may want to use a router matcher library
     * e.g. `sparsetech.trail` or pattern match on the path parts. Return an
     * Action that renders to null to remove the router from react
     * rendering. This is a total function so you are responsible for returning
     * an Action even if its for an unplanned page (e.g. 404).
     */
    val rules: Rules

    /** Renders the content given a renderer from the rules. */
    val render: (Control, Control => ReactNode) => ReactNode

    /** Run after render loop, not immediately after `render`. */
    val postRender: Option[Info => Unit]
  }

  /** Override to extend. */
  protected def performRoutingAction(config: Config, to: To, method: Redirect.Method): Unit = {
    method match {
      case Redirect.Replace => routing.push(to)
      case Redirect.Push => routing.replace(to)
      case Redirect.Reload => routing.reload(to)
    }
  }

  protected sealed trait NavAction
  protected case class NavigateAndPerform(action: Action, perform: Info => Unit) extends NavAction
  protected case class PerformOnly(perform: Info => Unit) extends NavAction

  protected case class State(
    action: Action
  )

  /** Override to change the name. */
  val Name = "RouterComponent"
  protected val c = reducerComponent[State, NavAction](Name)
  import c.ops._

  def apply(config: Config) =
    c.copyWith(new methods {
      val initialState = self => State(Render(_ => null))

      // run rules when URL changes and on initial mount
      didMount = js.defined{ self =>
        def runchange(info: Info) = {
          val action = config.rules(info)
          action match {
            case Render(run, effect) => self.send(NavigateAndPerform(action, _ => effect()))
            case RedirectTo(to, method, effect) =>
              self.send(NavigateAndPerform(action, _ => { performRoutingAction(config, to, method); effect()}))
          }          
        }
        val unsubscribe = routing.subscribe(runchange)
        routing.run(runchange)
        self.onUnmount(unsubscribe)
      }

      // @todo Pull out the reducer and allow overriding
      val reducer = (action, state, gen) => {
        action match {
          case PerformOnly(perform) => gen.effect(_ => routing.run(perform))
          case NavigateAndPerform(naction, perform) =>
            gen.updateAndEffect(state.copy(action = naction))(_ => routing.run(perform))
        }
      }

      val render = self => {
        self.state.action match {
          case Render(run, _) =>
            // this will run *after* the DOM is updated
            config.postRender.foreach(f => self.send(PerformOnly(f)))
            // pull out makeControl to be memoized or retained across invocations via State ref
            config.render(makeControl(config)/*control*/, run)
            // The default is to render null, can we type this better? Does this
            // cause a flash of death? It destroys the tree and we may be
            // rendering back into the same page.
          case _ =>
            //println("RoutingComponent rendering null!")
            null
        }
      }
    })
}
