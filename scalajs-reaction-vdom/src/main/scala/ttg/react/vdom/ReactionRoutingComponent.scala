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

trait ReactionRoutingComponent extends RouterComponent[PathParts, String] {

  override val Name = "ReactionRoutingComonent"
  type Config  = super.ConfigLike
  type Control = DOMControl

  trait DOMControl extends super.ControlLike {
    /** Consume the event via preventDefault call callback handler. */
    val eventHandler: ReactEvent[dom.EventTarget] => Unit
  }

  val control = new DOMControl {
    val eventHandler = _.preventDefault()

    // this is the "push from app" routing
    val navigate = url => {
      val paths = router.dangerouslyGetUrl()
      if (shouldRoute(paths, url)) routing.push(url)
    }

    val redirect = url => {
      val paths = router.dangerouslyGetUrl()
      if(shouldRoute(paths, url)) routing.replace(url)
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
  }

  def makeConfig(_rules: PathParts => Action): Config = new Config { val rules = _rules }

}

object ReactionRoutingComponent extends ReactionRoutingComponent {}
