// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package reactdom

import scala.scalajs.js
import js.|
import js.annotation._
import js.JSConverters._

import org.scalajs.dom

trait CreateRootOptions extends js.Object {
  var hydrate: js.UndefOr[Boolean] = js.undefined
}

@js.native
trait Root extends js.Object {
  def render(el: ReactNode): Unit = js.native
}

@js.native
private[react] trait ReactDOMJS extends js.Object {
  def render(node: ReactNode, target: dom.Element, callback: js.UndefOr[js.Function0[Unit]] = js.undefined): Unit               =
    js.native
  def createPortal(node: ReactNode, target: dom.Element|Null,
    key: js.UndefOr[String|Null] = js.undefined): ReactPortal = js.native
  def unmountComponentAtNode(el: dom.Element): Boolean                    = js.native
  def findDOMNode(componentOrElement: js.Any): dom.Element             = js.native
  def createRoot(target: dom.Element, options: js.UndefOr[CreateRootOptions]): Root = js.native
  val version: String = js.native
}

/** react-dom scala.js import. */
@js.native
@JSImport("react-dom", JSImport.Namespace)
object JSReactDOM extends ReactDOMJS with Root
