// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package react_dom

import scala.scalajs.js
import js.|
import js.annotation._
import js.JSConverters._
import react._

import org.scalajs.dom

@js.native
trait Renderable extends js.Object {
  def render(node: ReactNode): Unit = js.native
}

trait CreateRootOptions extends js.Object {
  var hydrate: js.UndefOr[Boolean] = js.undefined
}

@js.native
trait Concurrent extends js.Object {
  def createRoot(
    rootElement: dom.Element,
    options: js.UndefOr[CreateRootOptions] = js.undefined
  ): Renderable = js.native

  def createSyncRoot(
    rootElement: dom.Element,
    options: js.UndefOr[CreateRootOptions] = js.undefined
  ): Renderable = js.native  
}

@js.native
private[react_dom] trait ReactDOMJS extends js.Object with Concurrent {
  def render(el: ReactNode): Unit = js.native

  def render(
    node: ReactNode,
    target: dom.Element,
    callback: js.UndefOr[js.Function0[Unit]] = js.undefined): Unit               =
    js.native

  def createPortal(node: ReactNode, target: dom.Element|Null,
    key: js.UndefOr[String|Null] = js.undefined): ReactPortal = js.native

  def unmountComponentAtNode(el: dom.Element): Boolean                    = js.native

  def findDOMNode(componentOrElement: js.Any): dom.Element             = js.native

  val version: String = js.native

  def hydrate(element: dom.html.Element, container: dom.html.Element, callback: () => Unit): Unit = js.native
}

/** react-dom scala.js import. */
@js.native
@JSImport("react-dom", JSImport.Namespace)
object ReactDOMJS extends ReactDOMJS
