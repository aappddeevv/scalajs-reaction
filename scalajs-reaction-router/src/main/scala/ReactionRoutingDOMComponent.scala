// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package router
package browser

import org.scalajs.dom

import ttg.react
import react._
import elements._

import react.vdom._

/**
 * Routing using this facade's router context provider and targeted for the DOM
 * and browser URLs. You don't need this class, you could just listen to the
 * history changes using a browser's history API or the "history" js
 * package. Use a instance of this component as an ancestor to provide the
 * latest "history" location in a react context then use the `Route` component,
 * a value dependent type, to perform routing anywhere in the application.  The
 * F context is Id so there is no effect wrapping a rule's Action.
 * 
 * You may also want to look at the code behind the [history
 * github](https://github.com/ReactTraining/history) package or
 * [react-router-navigation-prompt](https://github.com/ZacharyRSmith/react-router-navigation-prompt)
 * in order to see how to take an user confirmed action such as saving changes
 * before routing away. If you roll your own, you will need to register an
 * "beforeonload" handler with the window when the user types in a new URL and
 * throw up a dialog. You can also register with the document's history object
 * to indepdently listen to history changes and if the URLs are different, throw
 * up a dialog.
 */
trait ReactionRouterDOMComponent
    extends RoutingSourceComponent[PathParts, String]
    with RoutingConfig[PathParts, String] {

  override val Name = "ReactionRoutingComponent"
  type Control = DOMControl
  type Config <: ConfigLike
  type RouterInfo = RouterInfoLike

  /** Context type. */
  case class RouterInfoLike(
    info: Option[PathParts] = None
  ) extends super.RouterInfoLike

  def makeContextValue(info: Option[PathParts]) = RouterInfoLike(info)

  trait ConfigLike extends super.ConfigLike {
    val prefixPath: Option[String]
  }

  case class DOMControl(config: Config)
      extends super.ControlLike {
    val processEvent: ReactEvent[dom.EventTarget] => Unit  = _.preventDefault()

    // this is the "push from app" routing
    val navigate = (url, method) => {
      val paths = dangerouslyGetUrl()
      // only route if its a different pathname (url + query parameters)
      if (shouldRoute(paths, url)) performRoutingAction(routing, config, url, method)
    }
  }

  protected def shouldRoute(p: PathParts, to: String) = p.pathname != to

  protected val routing = new RoutingSource[PathParts, String] {
    val subscribe = cb => {
      val id = watchUrl(cb(_))
        () => unwatchUrl(id)
    }
    val push = browser.push(_)
    val replace = browser.replace(_)
    val reload = (href: String) => dom.window.location.href = href
    val run = _(dangerouslyGetUrl())
  }

  protected def makeControl(config: Config)  = DOMControl(config)

  override protected def performRoutingAction(
    routing: RoutingSource[PathParts, String],
    config: Config,
    to: String,
    method: Redirect.Method
  ): Unit = {
    method match {
      case Redirect.Replace => routing.push(config.prefixPath.map(_ + "/").getOrElse("") + to)
      case Redirect.Push => routing.replace(config.prefixPath.map(_ + "/").getOrElse("") + to)
      // must be absolute
      case Redirect.Reload => routing.reload(to)
    }
  }

  /** A component that uses the context provider and a Config to route.
   * 
   * @todo Schedule effect to be run after rule action using a reducerComponent.
   */
  object Route {
    val Name = "Route"
    val c = statelessComponent(Name)
    import c.ops._

    /** Create a component instance. If no parent context value is provided, render
      * null.
     * @param config Routing config that maps the current route info to a react
     * node.
     */
    def apply(
      config: Config
    ) = render { self =>
      context.consumer(RouterContext){ctx =>
        ctx.info match {
          case Some(info) =>
            config.rules(info) match {
              case Render(thunk, effect) =>
                config.render(makeControl(config), thunk)
              case RedirectTo(to, method, effect) =>
                performRoutingAction(routing, config, to, method)
                null
            }
          case _ =>
            null
        }
      }
    }
  }

}
