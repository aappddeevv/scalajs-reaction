// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package router
package browser

import scala.scalajs.js
import org.scalajs.dom

/** Specification of a specific set of information for routing. This one is
  * mostly based on the DOM but its contents can be generically used in most
  * cases.
 */
case class PathParts(
  /** Segments, left to right. */
  segments: Seq[String],
  /** Everything after the hash. */
  hash: Option[String],
  /** Query parameters, unparsed. */
  search: Option[String],
  /** Always starts with a slash, does not include origin. */
  pathname: String
) {

  /** Drop path segments. */
  def drop(n: Int) = this.copy(segments = this.segments.drop(n))

  /** Parse the query parameters. */
  def parse: ParsedPathParts = {
    val x = search.map(new URLSearchParams(_))
    ParsedPathParts(
      segments,
      hash,
      x.map(_.entries().map(sarray => (sarray(0), sarray.drop(1).toArray)).toMap)
        .getOrElse(collection.immutable.Map[String,Array[String]]()),
      pathname
    )
  }
}

/** Query parameters broken out. */
case class ParsedPathParts(
  segments: Seq[String],
  hash: Option[String],
  params: collection.immutable.Map[String,Array[String]],
  pathname: String
)

/**
  * Simple router as in ReasonReact. Use pattern matching in the callbacok to
  * match on the url structure `(path seq, hash, search)` and then call "send"
  * on your component's router. This is not meant to be a robust router
  * implementation, just some abstraction over the `window.history` api.  You
  * could define your own e.g. a stream of history changes.
 * 
 * @todo Add PathParts type parameter.
 * @todo Flexibly determine of window and history API exists before calling
 * methods.
  */
trait Router {

  private[ttg] def makeEvent(t: String): dom.Event = {
    js.Dynamic.newInstance(js.Dynamic.global.Event)(t).asInstanceOf[dom.Event]
  }

  private[ttg] def search(): Option[String] = {
    dom.window.location.search match {
      case "" | "?" => None
      case x        => Option(x.drop(1)) // drop ?
    }
  }

  private[ttg] def hash(): Option[String] = {
    dom.window.location.hash match {
      case "" | "#" => None
      case x        => Option(x.drop(1)) // drop #
    }
  }

  private[ttg] def path(): Seq[String] = {
    dom.window.location.pathname match {
      case "" | "/" => Nil
      case x        =>
        val start = 1 // always seems to be a leading /, so drop it
        val end   = x.length - (if (x.endsWith("/")) 1 else 0) // chop last /
        x.slice(start, end).split("/")
    }
  }

  private[ttg] def url() = {
    // dom.window.location.pathname
    PathParts(path(), hash(), search(), dom.window.location.pathname)
  }

  def push(path: String): Unit = {
    // should dmake sure history and window exist
    dom.window.history.pushState(/*dom.window.history*/null, "", path)
    dom.window.dispatchEvent(makeEvent("popstate"))
  }

  // not part of reasonreact
  def replace(path: String): Unit = {
    // ???
    //dom.document.body.scrollTop = 0
    // should make sure history and window exist 
    dom.window.history.replaceState(null, "", path)
    dom.window.dispatchEvent(makeEvent("popstate"))    
  }

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

  /** Get the current url. It's dangerous because it could be out of date. */
  def dangerouslyGetUrl() = url()
}

