// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package vdom

import org.scalajs.dom

import ttg.react
import react._
import elements._

import react.router._

/**
 * Routing using this facade's router and targeted for the DOM and browser URLs.
 * 
 * You may also want to look at the code behind
 * [react-router-navigation-prompt](https://github.com/ZacharyRSmith/react-router-navigation-prompt)
 * in order to see how to take an user confirmed action such as saving changes
 * before routing away. You'll need to register an "beforeonload" handler with
 * the window when the user types in a new URL and throw up a dialog. You can
 * also register with the document's history object to indepdently listen to
 * history changes and if the URLs are different, throw up a dialog.
 */
trait ReactionRouterComponent extends RouterComponent[PathParts, String] {

  override val Name = "ReactionRoutingComonent"
  type Control = DOMControl

  trait DOMControl extends super.ControlLike {
    /** Consume the event via preventDefault. */
    val processEvent: ReactEvent[dom.EventTarget] => Unit
  }

  val control = new DOMControl with super.ControlLike {
    val processEvent = _.preventDefault()

    // this is the "push from app" routing
    val navigate = (url, method) => {
      val paths = router.dangerouslyGetUrl()
      // only route if its a different pathname (url + query parameters)
      if (shouldRoute(paths, url)) performRoutingAction(url, method)
    }
  }

  def shouldRoute(p: PathParts, to: String) = p.pathname != to

  val routing = new RoutingSource[PathParts, String] {
    val subscribe = cb => {
      val id = watchUrl(cb(_))
      () => unwatchUrl(id)
    }
    val push = router.push(_)
    val replace = router.replace(_)
    val reload = (href: String) => dom.window.location.href = href
    val run = _(router.dangerouslyGetUrl())
  }

}

object ReactionRouter extends ReactionRouterComponent {

  type Config = ReactionConfig

  // case class for convenience, easy to copy and change
  case class ReactionConfig(
    rules: Rules,
    postRender: Option[PathParts => Unit] = None,
    render: (Control, Control => ReactNode) => ReactNode = (c, f) => f(c)
  ) extends super.ConfigLike {

    /** Add a post renderer to this config, runs before existing postRender thunk. */
    def withPostRender(f: PathParts => Unit) = copy(
      postRender = postRender.map{previous => (info: PathParts) => { f(info); previous(info)}} orElse Some(f)
    )

  }
}

object PostRenderer {
  val scrollToTop: PathParts => Unit = _ => dom.window.scrollTo(0,0)
  val setTitle: String => PathParts => Unit = t => _ => dom.document.title = t
}
