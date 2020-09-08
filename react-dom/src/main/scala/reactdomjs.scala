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

package react_dom

import scala.scalajs.js
import js.annotation._
import js.|
import org.scalajs.dom
import react._

@js.native
trait Renderable extends js.Object {
  def render(node: ReactNode): Unit = js.native
}

trait CreateRootOptions extends js.Object {
  var hydrate: js.UndefOr[Boolean] = js.undefined
}

@js.native
trait Concurrent extends js.Object {
  @JSName("unstable_createRoot")
  def createRoot(
    rootElement: dom.Element,
    options: js.UndefOr[CreateRootOptions] = js.undefined
  ): Renderable = js.native

  @JSName("unstable_createBlockingRoot")
  def createBlockingRoot(
    rootElement: dom.Element,
    options: js.UndefOr[CreateRootOptions] = js.undefined
  ): Renderable = js.native

}

@js.native
@JSImport("react-dom/server", JSImport.Default)
object ReactDOMServer extends js.Object {
  def renderToString(element: ReactNode): String = js.native
  def renderToStaticMarkup(element: ReactNode): String = js.native
  /** Only available on a node server. */
  def renderToNodeStream(element: ReactNode): js.Any = js.native
  /** Only available on a node server. */
  def renderToStaticNodeStream(element: ReactNode): js.Any = js.native
}

@js.native
private[react_dom] trait ReactDOMJS extends js.Object with Concurrent {
  def render(el: ReactNode): Unit = js.native

  def render(node: ReactNode, target: dom.Element, callback: js.UndefOr[js.Function0[Unit]] = js.undefined): Unit =
    js.native

  def createPortal(
    node: ReactNode,
    target: dom.Element | Null,
    key: js.UndefOr[String | Null] = js.undefined
  ): ReactPortal = js.native

  def unmountComponentAtNode(el: dom.Element): Boolean = js.native

  def findDOMNode(componentOrElement: js.Any): dom.Element = js.native

  def hydrate(element: dom.html.Element, container: dom.html.Element, callback: () => Unit): Unit = js.native
}

/** react-dom scala.js import. */
@js.native
@JSImport("react-dom", JSImport.Namespace)
private[react_dom] object ReactDOMJS extends ReactDOMJS
