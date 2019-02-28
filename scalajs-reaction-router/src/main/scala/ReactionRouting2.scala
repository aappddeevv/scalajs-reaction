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
 * Routing using this facade's router and targeted for the DOM and browser
 * URLs. You don't need this class, you could just listen to the history changes
 * using a browser's history API or the basic ReasonReact router that is
 * provided that is a simple layer on the history API.
 * 
 * You may also want to look at the code behind
 * [react-router-navigation-prompt](https://github.com/ZacharyRSmith/react-router-navigation-prompt)
 * in order to see how to take an user confirmed action such as saving changes
 * before routing away. You'll need to register an "beforeonload" handler with
 * the window when the user types in a new URL and throw up a dialog. You can
 * also register with the document's history object to indepdently listen to
 * history changes and if the URLs are different, throw up a dialog.
 */
trait ReactionRouterComponent2 extends RouterComponent2[PathParts, String] {

  override val Name = "ReactionRoutingComonent2"
  type Control = DOMControl
  type Config <: ConfigLike

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
      if (shouldRoute(paths, url)) performRoutingAction(config, url, method)
    }
  }

  def shouldRoute(p: PathParts, to: String) = p.pathname != to

  val routing = new RoutingSource[PathParts, String] {
    val subscribe = cb => {
      val id = watchUrl(cb(_))
      () => unwatchUrl(id)
    }
    val push = browser.push(_)
    val replace = browser.replace(_)
    val reload = (href: String) => dom.window.location.href = href
    val run = _(dangerouslyGetUrl())
  }

  def makeControl(config: Config) = DOMControl(config)

  override protected def performRoutingAction(
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

  // create a component to use that captures the routing context and some rules to run
  object Route {
    val Name = "Route"
    val c = statelessComponent(Name)
    import c.ops._

    def apply(
      config: Config
    ) = c.copyWith(new methods {
      val render = self => {
        context.consumer(RouterContext)(ctx => {
          ctx.info match {
            case Some(info) =>
              config.rules(info) match {
                case Render(thunk, effect) =>
                  config.render(makeControl(config), thunk)
                case RedirectTo(to, method, effect) =>
                  performRoutingAction(config, to, method)
                  null
              }
            case _ =>
              null
          }
        })
      }
    })
  }

}

object ReactionRouter2 extends ReactionRouterComponent2 {

  type Config = ReactionConfig

  // case class for convenience, easy to copy and change
  case class ReactionConfig(
    rules: Rules,
    prefixPath: Option[String] = None,
    postRender: Option[PathParts => Unit] = None,
    render: ((Control, Control => ReactNode) => ReactNode) = (c,f) => f(c)
  ) extends super.ConfigLike {

    /** Add a post renderer to this config, runs before existing postRender thunk. */
    def withPostRender(f: PathParts => Unit) = copy(
      postRender = postRender.map{previous => (info: PathParts) => { f(info); previous(info)}} orElse Some(f)
    )
  }
}
