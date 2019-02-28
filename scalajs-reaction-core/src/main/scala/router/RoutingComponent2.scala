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
 * Like react-router. Establishes async callaback to a routing source then
 * provides a routing react context to children. A more functional way would be
 * to just provide a reactive var to a child that receives routing updates and
 * let that component decide what to do.
 * 
 * @tparam Info The request info pushed from the routing source.
 * @tparam To A value representing where to go to.
 * 
 */
abstract trait RouterComponent2[Info <: scala.AnyRef, To] { self =>

  case class RouterInfo(
    info: Option[Info],
  )

  val RouterContext: ReactContext[RouterInfo] = context.make[RouterInfo](null)

  type Config <: ConfigLike
  type Control <: ControlLike
  type Navigator = (To, Redirect.Method) => Unit

  val routing: RoutingSource[Info, To]

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

  /** Take some action as the result of processing a rule. */
  sealed trait Action extends Product with Serializable
  /** Render a node allowing that node to use Controls to create "links". Can render to null! */
  case class Render(run: Control => ReactNode, effect: (() => Unit) = noop) extends Action {
    def apply(c: Control) = run(c)
  }
  /** Go to another page with a specific method to get there. */
  case class RedirectTo(to: To, method: Redirect.Method, effect: (() => Unit) = noop) extends Action

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

    /** Renders the content if the rules return a `Render` action. */
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
  protected case object Subscribe extends NavAction
  protected case class NewInfo(info: Info) extends NavAction

  protected case class State(
    info: Option[Info] = None,
    // instance vars
    var unsubscribe: Option[() => Unit] = None
  )

  /** Override to change the name. */
  val Name = "RouterComponent2"
  protected val c = reducerComponent[State, NavAction](Name)
  import c.ops._

  def apply(child: ReactNode) = c.copyWith(new methods {

    val initialState = self => State()

    // subscribe to routing source on mount
    didMount = js.defined { self =>
      self.state.unsubscribe match {
        case None => self.send(Subscribe)
        case _ => // subscription already active
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
        gen.updateAndEffect(state.copy(unsubscribe=None)){ self =>
          self.state.unsubscribe = Option(
            routing.subscribe{ info =>
              self.send(NewInfo(info))
            })
          // should we prime the pump? we need to somewhere
          routing.run(i => self.send(NewInfo(i)))
        }
    }

    val render = self => {
      context.provider(RouterContext)(RouterInfo(self.state.info))(
        child
      )
    }
  })
}
