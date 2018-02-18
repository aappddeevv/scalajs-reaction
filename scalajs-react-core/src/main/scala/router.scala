// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js
import org.scalajs.dom

/**
  * Short router. Use pattern matching in the cb to match on the url structure
  * `(path seq, hash, search)` and then call "send" on your component's
  * router. Add your router as a Subscription and profit!
  */
object router {

  private def makeEvent(t: String): dom.Event = {
    js.Dynamic.newInstance(js.Dynamic.global.Event)(t).asInstanceOf[dom.Event]
  }

  private def search(): String = {
    dom.window.location.search match {
      case "" | "?" => ""
      case x => x.drop(1)
    }
  }

  private def hash(): String = {
    dom.window.location.hash match {
      case "" | "#" => ""
      case x => x.drop(1)
    }
  }

  private def path(): Seq[String] = {
    dom.window.location.pathname match {
      case "" | "/" => Nil
      case x =>
        // always seems to be a leading /, so drop it
        val start = 1
        val end = x.length - (if (x.endsWith("/")) 1 else 0)
        x.slice(start, end).split("/")
    }
  }

  private def url() = {
    (path(), hash(), search())
  }

  def push(path: String): Unit = {
    dom.window.history.pushState(new js.Object(), "", path)
    dom.window.dispatchEvent(makeEvent("popstate"))
  }

  /** path, hash, search */
  type CallbackArg = (Seq[String], String, String)
  type WatcherId = js.Any

  private type CB = js.Function1[js.Any, js.Any]

  def watchUrl(cb: CallbackArg => Unit): WatcherId = {
    val watcherId: CB = _ => cb(url())
    dom.window.addEventListener("popstate", watcherId)
    watcherId
  }

  def unwatchUrl(watcherId: WatcherId): Unit =
    dom.window.removeEventListener("popstate", watcherId.asInstanceOf[CB])

  def dangerouslyGetUrl() = url()
}
