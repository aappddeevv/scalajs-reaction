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

/** scala.js entry point of react-dom. There may be functions here that are only present
 *  on experimental builds of react. The functions `renderToElementWithId`,
 *  `createPortalInElementWithId`, and `render` should always be available.
 */
package object react_dom {

  /** Render into the DOM given an element id. */
  def renderToElementWithId(
    el: ReactNode,
    id: String,
    cb: Option[() => Unit] = None
  ): Either[Throwable, Unit] = {
    val target = Option(dom.document.getElementById(id))
    target.fold[Either[Throwable, Unit]](
      Left(new Exception(s"Element with id $id not found."))
    )(htmlel => Right(ReactDOMJS.render(el, htmlel, cb.orUndefined.map(js.Any.fromFunction0(_)))))
  }

  /* Render into an elemen given by its id. Prefer this over `renderToElementWithId`. */
  def renderToElement(id: String, cb: Option[() => Unit]=None): Either[String, ReactNode => Unit] = {
    Option(dom.document.getElementById(id))
      .fold[Either[String, ReactNode => Unit]](
       Left(s"Element with id $id not found.")
    )(htmlel => Right(node => ReactDOMJS.render(node, htmlel, cb.map(js.Any.fromFunction0(_)).orUndefined)))
  }

  /** Render the DOM given an element id using react's portal. */
  def createPortalInElementWithId(
    node: ReactNode,
    id: String,
    key: Option[String] = None
  ): Either[Throwable, ReactPortal] = {
    val target = Option(dom.document.getElementById(id))
    target.fold[Either[Throwable, ReactPortal]](
      Left(new Exception(s"Element with id $id not found."))
    )(htmlel => Right(ReactDOMJS.createPortal(node, htmlel, key.orUndefined)))
  }

  /** Render using concurrent mode. Left is an error message and right is a render function. */
  def createRoot(id: String): Either[String, ReactNode => Unit] = {
    val target = Option(dom.document.getElementById(id))
    target.fold[Either[String, ReactNode => Unit]](Left(s"No element with id $id found."))(htmlel =>
      Right(ReactDOMJS.createRoot(htmlel).render(_))
    )
  }

  def createSyncRoot(id: String): Either[String, ReactNode => Unit] = {
    val target = Option(dom.document.getElementById(id))
    target.fold[Either[String, ReactNode => Unit]](Left(s"No element with id $id found."))(htmlel =>
      Right(ReactDOMJS.createSyncRoot(htmlel).render(_))
    )
  }

  def render(node: ReactNode, target: dom.Element) = ReactDOMJS.render(node, target)

  def renderCB(node: ReactNode, target: dom.Element, cb: js.Function0[Unit]) =
    ReactDOMJS.render(node, target, cb)

  def hydrate(element: dom.html.Element, container: dom.html.Element, done: js.Function0[Unit]) =
    ReactDOMJS.hydrate(element, container, done)

  def unmountComponentAtNode(el: dom.Element): Boolean = ReactDOMJS.unmountComponentAtNode(el)

  def findDOMNode(componentOrElement: js.Any): dom.Element = ReactDOMJS.findDOMNode(componentOrElement)

}
