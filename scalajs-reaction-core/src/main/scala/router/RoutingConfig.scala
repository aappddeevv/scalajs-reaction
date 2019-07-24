// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package router

/**
 * A collection of routing config types so you can compose routing rules.  The
 * rules do not prescribe a specific "Info" to "Action" matching model so you
 * are free to choose your own. You can use sparse.tech
 * [trail](https://github.com/sparsetech) or something like
 * [path-to-regexp](https://github.com/pillarjs/path-to-regexp). Also, look at
 * [You might not need React
 * Router](https://medium.freecodecamp.org/you-might-not-need-react-router-38673620f3d)
 * for ideas. That article uses an array of regexp to async functions vs a total
 * function descibed in `Rules` below. Since the returned action is warpped in a
 * `F`, any router using this trait will need to know how to run `F`.
 * 
 * @tparam F The context wrapping Action when `Info => F[Action]`.
 * @tparam Info The request info pushed from the routing source.
 * @tparam To A value representing where to go to.
 * 
 */
abstract trait RoutingConfig[Info <: scala.AnyRef, To] { self =>

  type Control <: ControlLike
  type Config <: ConfigLike
  type Navigator = (To, Redirect.Method) => Unit

  val noop = () => ()

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

  /** Small rules DSL. Create a rule that can return an optional action. */
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
   * rendering, a `Info => Control => ReactNode`. Note that action does not
   * return an effectful action e.g. Action is not wrapped in a F context.
   */
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
  protected def performRoutingAction(
    routing: RoutingSource[Info, To],
    config: Config,
    to: To,
    method: Redirect.Method
  ): Unit = {
    method match {
      case Redirect.Replace => routing.push(to)
      case Redirect.Push => routing.replace(to)
      case Redirect.Reload => routing.reload(to)
    }
  }

}

