// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js
import js.annotation._
import js.|
import js._
import js.Dynamic.{literal => lit}

import org.scalajs.dom

@js.native
trait JSReactDOM extends js.Object {
  def render(node: ReactNode, target: dom.Element): Unit = js.native
  def createPortal(node: ReactNode, target: dom.Element): ReactElement = js.native
  def unmountComponentAtNode(el: dom.Element): Unit = js.native
  //def findDOMNode(??? .reactRef): dom.element = js.native
}

@js.native
@JSImport("react-dom", JSImport.Namespace)
object JSReactDOM extends JSReactDOM

object reactdom {

  /** Render into the DOM given an element id. */
  def renderToElementWithId(el: ReactNode, id: String) = {
    val target = Option(dom.document.getElementById(id))
    target.fold(throw new Exception(s"renderToElementWithId: No element with id $id found in the HTML."))(htmlel => JSReactDOM.render(el, htmlel))
  }

  /** Render the DOM given an element id using react's portal. */
  def createPortalInElementWithId(node: ReactNode, id: String) = {
    val target = Option(dom.document.getElementById(id))
    target.fold(throw new Exception(s"createPortalInElemeentWithId: No element with id $id founud in the HTML."))(htmlel =>
      JSReactDOM.createPortal(node, htmlel))
  }
}
