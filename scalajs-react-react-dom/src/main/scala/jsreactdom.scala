// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package reactdom

import scala.scalajs.js
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

/** js API for react-dom. */
@js.native
trait JSReactDOM extends js.Object {
  def render(node: ReactNode, target: dom.Element): Unit               = js.native
  def createPortal(node: ReactNode, target: dom.Element): ReactElement = js.native
  def unmountComponentAtNode(el: dom.Element): Unit                    = js.native
  def findDOMNode(componentOrElement: js.Any): dom.Element             = js.native
  //def unstable_deferredUpdates(f: js.Function0[Unit]): Unit            = js.native

  /** 16.3 */
  @JSName("unstable_createRoot")
  def createRoot(target: dom.Element, options: js.UndefOr[CreateRootOptions]): Root = js.native
}

/** react-dom scala.js import. */
@js.native
@JSImport("react-dom", JSImport.Namespace)
object JSReactDOM extends JSReactDOM
