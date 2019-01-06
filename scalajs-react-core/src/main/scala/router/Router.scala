// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package router

import scala.scalajs.js
import org.scalajs.dom

/**
  * Simple router as in ReasonReact. Use pattern matching in the callbacok to
  * match on the url structure `(path seq, hash, search)` and then call "send"
  * on your component's router. This is not meant to be a robust router
  * implementation, just some abstraction over the `window.history` api.  You
  * could define your own e.g. a stream of history changes.
 * 
 * TODO: Add replace?
  */
trait Router {

  private[react] def makeEvent(t: String): dom.Event = {
    js.Dynamic.newInstance(js.Dynamic.global.Event)(t).asInstanceOf[dom.Event]
  }

  private[react] def search(): Option[String] = {
    dom.window.location.search match {
      case "" | "?" => None
      case x        => Option(x.drop(1)) // drop ?
    }
  }

  private[react] def hash(): Option[String] = {
    dom.window.location.hash match {
      case "" | "#" => None
      case x        => Option(x.drop(1)) // drop #
    }
  }

  private[react] def path(): Seq[String] = {
    dom.window.location.pathname match {
      case "" | "/" => Nil
      case x        =>
        val start = 1 // always seems to be a leading /, so drop it
        val end   = x.length - (if (x.endsWith("/")) 1 else 0) // chop last /
        x.slice(start, end).split("/")
    }
  }

  private[react] def url() = {
    PathParts(path(), hash(), search(), dom.window.location.pathname)
  }

  def push(path: String): Unit = {
    dom.window.history.pushState(/*dom.window.history*/null, "", path)
    dom.window.dispatchEvent(makeEvent("popstate"))
  }

  case class PathParts(
    segments: Seq[String],
    hash: Option[String],
    search: Option[String],
    // always starts with a slash it seems and is / if at root
    pathname: String = dom.window.location.pathname 
  )
  type WatcherId   = js.Any

  private type CB = js.Function1[js.Any, js.Any]

  /**
   * Add your call back using `watchUrl{case (path, hash, search) => ... }`.
   */
  def watchUrl(cb: PathParts => Unit): WatcherId = {
    val watcherId: CB = _ => cb(url())
    dom.window.addEventListener("popstate", watcherId)
    watcherId
  }

  def unwatchUrl(watcherId: WatcherId): Unit =
    dom.window.removeEventListener("popstate", watcherId.asInstanceOf[CB])

  /** Get the current url. It's dangerous because it accesses the dom window location. */
  def dangerouslyGetUrl() = url()
}
