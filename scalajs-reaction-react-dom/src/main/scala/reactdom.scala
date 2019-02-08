// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js
import js.annotation._
import js.JSConverters._

import org.scalajs.dom

/** scala.js entry point of react-dom but using reason-react API. */
package object reactdom {

  /** Render el into the html element 'id'. react 16.3 */
  def createAndRenderWithId(el: ReactNode, id: String, opts: Option[CreateRootOptions] = None): Either[Throwable, Unit] = {
    val target = Option(dom.document.getElementById(id))
    target.fold[Either[Throwable,Unit]](
      Left(new Exception(s"createAndRenderWithId: No element with id $id found in the HTML."))
    )(
      htmlel => Right(JSReactDOM.render(el, htmlel))
    )
  }

  /** Render into the DOM given an element id. */
  def renderToElementWithId(el: ReactNode, id: String): Either[Throwable, Unit] = {
    val target = Option(dom.document.getElementById(id))
    target.fold[Either[Throwable,Unit]](
      Left(new Exception(s"renderToElementWithId: No element with id $id found in the HTML."))
    )(
      htmlel => Right(JSReactDOM.render(el, htmlel)))
  }

  /** Render the DOM given an element id using react's portal. */
  def createPortalInElementWithId(node: ReactNode, id: String): Either[Throwable, ReactPortal] = {
    val target = Option(dom.document.getElementById(id))
    target.fold[Either[Throwable,ReactPortal]](
      Left(new Exception(s"createPortalInElemeentWithId: No element with id $id found in the HTML."))
    )(
      htmlel => Right(JSReactDOM.createPortal(node, htmlel))
    )
  }
}
