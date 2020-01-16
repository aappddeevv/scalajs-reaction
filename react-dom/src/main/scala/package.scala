// Copyright (c) 2019 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

import scala.scalajs.js
import js.annotation._
import js.JSConverters._

import react._
import org.scalajs.dom

/** scala.js entry point of react-dom but using reason-react API. */
package object react_dom {

  /** Render into the DOM given an element id. */
  def renderToElementWithId(
    el: ReactNode,
    id: String,
    cb: Option[() => Unit] = None
  ): Either[Throwable, Unit] = {
    val target = Option(dom.document.getElementById(id))
    target.fold[Either[Throwable,Unit]](
      Left(new Exception(s"No element with id $id found in the HTML."))
    )(
      htmlel => Right(ReactDOMJS.render(el, htmlel, cb.orUndefined.map(js.Any.fromFunction0(_))))
    )
  }

  /** Render the DOM given an element id using react's portal. */
  def createPortalInElementWithId(
    node: ReactNode,
    id: String,
    key: Option[String] = None
  ): Either[Throwable, ReactPortal] = {
    val target = Option(dom.document.getElementById(id))
    target.fold[Either[Throwable,ReactPortal]](
      Left(new Exception(s"No element with id $id found in the HTML."))
    )(
      htmlel => Right(ReactDOMJS.createPortal(node, htmlel, key.orUndefined))
    )
  }

  /** Render using concurrent mode. */
  def createRoot(id: String): Either[String, ReactNode=>Unit] = {
    val target = Option(dom.document.getElementById(id))
    target.fold[Either[String,ReactNode=>Unit]](
      Left(s"No element with id $id found."))(
      htmlel => Right(ReactDOMJS.createRoot(htmlel).render(_))
      )
  }

  /** Not sure what this is. */
  def createSyncRoot(id: String): Either[String, ReactNode=>Unit] = {
    val target = Option(dom.document.getElementById(id))
    target.fold[Either[String,ReactNode=>Unit]](
      Left(s"No element with id $id found."))(
      htmlel => Right(ReactDOMJS.createSyncRoot(htmlel).render(_))
    )
  }

}
