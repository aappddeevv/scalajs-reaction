/*
 * Copyright (c) 2018 The Trapelo Group
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import scala.scalajs.js

import js.JSConverters._

import org.scalajs.dom

import react._

/** scala.js entry point of react-dom but using reason-react API. */
package object react_dom {

  /** Render into the DOM given an element id. */
  def renderToElementWithId(
    el: ReactNode,
    id: String,
    cb: Option[() => Unit] = None
  ): Either[Throwable, Unit] = {
    val target = Option(dom.document.getElementById(id))
    target.fold[Either[Throwable, Unit]](
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
    target.fold[Either[Throwable, ReactPortal]](
      Left(new Exception(s"No element with id $id found in the HTML."))
    )(
      htmlel => Right(ReactDOMJS.createPortal(node, htmlel, key.orUndefined))
    )
  }

  /** Render using concurrent mode. */
  def createRoot(id: String): Either[String, ReactNode => Unit] = {
    val target = Option(dom.document.getElementById(id))
    target.fold[Either[String, ReactNode => Unit]](Left(s"No element with id $id found."))(
      htmlel => Right(ReactDOMJS.createRoot(htmlel).render(_))
    )
  }

  /** Not sure what this is. */
  def createSyncRoot(id: String): Either[String, ReactNode => Unit] = {
    val target = Option(dom.document.getElementById(id))
    target.fold[Either[String, ReactNode => Unit]](Left(s"No element with id $id found."))(
      htmlel => Right(ReactDOMJS.createSyncRoot(htmlel).render(_))
    )
  }

}
