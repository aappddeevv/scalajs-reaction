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

package react_router6
package dom

import scala.scalajs.js
import js.annotation._
import org.scalajs.{ dom => sdom }
import react.*
import react.syntax.*
import react.conversions.*
import vdom._


object MemoryRouter:
  @js.native
  @JSImport("react-router", "MemoryRouter")
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) =
    createElement(JS, props, children*)

  def apply(children: ReactNode*) =
    createElement(JS, null, children*)

  trait Props extends js.Object:
    var initialEntries: js.UndefOr[js.Array[InitialEntry[?]]] = js.undefined
    var initialIndex: js.UndefOr[Int] = js.undefined
    var timeout: js.UndefOr[Int] = js.undefined

object Navigate:
  @js.native
  @JSImport("react-router", "Navigate")
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) =
    createElement(JS, props, children*)

  def to(to: To) =
    createElement(
      JS,
      js.Dynamic.literal(
        "to" -> to.asInstanceOf[js.Any],
      ))

  def to(to: To, state: js.Any) =
    createElement(
      JS,
      js.Dynamic.literal(
        "to" -> to.asInstanceOf[js.Any],
        "state" -> state
      ))

  def replace(to: To) =
    createElement(
      JS,
      js.Dynamic.literal(
        "to" -> to.asInstanceOf[js.Any],
        "replace" -> true,
      ))

  def replace(to: To, state: js.Any) =
    createElement(
      JS,
      js.Dynamic.literal(
        "to" -> to.asInstanceOf[js.Any],
        "replace" -> true,
        "state" -> state
      ))

  trait Props extends js.Object:
    val to: To
    var replace: js.UndefOr[Boolean] = js.undefined
    var state: js.UndefOr[Any] = js.undefined

object Outlet:
  @js.native
  @JSImport("react-router", "Outlet")
  object JS extends ReactJSComponent

  def apply(): ReactNode = createElement(JS, null)

object Route:
  @js.native
  @JSImport("react-router", "Route")
  object JS extends ReactJSComponent

  def apply(props: Props) = createElement(JS, props)

  /** Use `Route` children. */
  def nested(props: Props)(children: ReactNode*) = createElement(JS, props, children*)

  /** Non-specialized version. */
  def apply(path: String, element: ReactNode) =
    createElement(JS, js.Dynamic.literal("path" -> path, "element" -> element))

  def withKey(key: String, path: String, element: ReactNode) =
    createElement(JS, js.Dynamic.literal("key" -> key, "path" -> path, "element" -> element))

  /** Children should be `Route` nodes. `element` should use `Outlet` to show selected child. */
  def nested(path: String, element: ReactNode)(children: ReactNode*) =
    createElement(JS, js.Dynamic.literal("path" -> path, "element" -> element), children*)

  /** Path is `*`. Use last in `Route` stack. */
  def always(element: ReactNode) =
    createElement(JS, js.Dynamic.literal("path" -> "*", "element" -> element))

  /** Appends `slash asterisk` to the path. Your element should *not* use `Outlet`. */
  def descendant(path: String, element: ReactNode) =
    createElement(JS, js.Dynamic.literal("path" -> (path + "/*"), "element" -> element))

  /** Index element shown when at base of URL. */
  def index(element: ReactNode) =
    createElement(JS, js.Dynamic.literal("index" -> true, "element" -> element))

  /** Use `slash asterisk` as the path. `element` should use `Outlet`. */
  def indexNested(element: ReactNode)(children: ReactNode*) =
    createElement(JS, js.Dynamic.literal("path" -> "/*", "element" -> element), children*)

  /** Path = `/`. */
  def root(child: ReactNode)(children: ReactNode*) =
    createElement(JS, js.Dynamic.literal("path" -> "/", "element" -> child.asInstanceOf[js.Any]), children*)

  /** Alias for `apply`. */
  def withPath(p: String | js.Array[String], child: ReactNode) =
    createElement(JS, js.Dynamic.literal("path" -> p.asInstanceOf[js.Any], "element" -> child.asInstanceOf[js.Any]))

  /** Use this as a parent for `Route` components and the matched route will be embedded in the element's `Outlet` position. */
  def layout(element: ReactNode)(children: ReactNode*) =
    createElement(JS, js.Dynamic.literal("element" -> element.asInstanceOf[js.Any]), children*)

  trait Props extends MaybeHasStrKey:
    var caseSensitive: js.UndefOr[Boolean] = js.undefined
    var path: js.UndefOr[String] = js.undefined
    var element: js.UndefOr[ReactNode] = js.undefined

end Route

object Router:
  @js.native
  @JSImport("react-router", "Router")
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) =
    createElement(JS, props, children*)

  def apply(children: ReactNode*) =
    createElement(JS, null, children*)

  trait Props extends js.Object:
    var action: js.UndefOr[Action] = js.undefined
    val location: Location[?]
    val navigator: Navigator[?]
    var pending: js.UndefOr[Boolean] = js.undefined
    var static: js.UndefOr[Boolean] = js.undefined
end Router

object Routes:
  @js.native
  @JSImport("react-router", "Routes")
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) =
    createElement(JS, props, children*)

  def apply(children: ReactNode*) =
    createElement(JS, null, children*)


  trait Props extends js.Object:
    var location: js.UndefOr[LocationInit[?]] = js.undefined
end Routes

object BrowserRouter:
  @js.native
  @JSImport("react-router-dom", "BrowserRouter")
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) = createElement(JS, props, children*)
  def apply(children: ReactNode*) = createElement(JS, null, children*)

  def basename(bname: js.UndefOr[String], children: ReactNode*) =
    createElement(JS, new Props { this.basename = bname }, children*)

  trait Props extends js.Object:
    var window: js.UndefOr[sdom.Window] = js.undefined
    var basename: js.UndefOr[String] = js.undefined
end BrowserRouter

object HashRouter:
  @js.native
  @JSImport("react-router-dom", "HashRouter")
  object JS extends ReactJSComponent

  def apply(child: ReactNode) =
    createElementN(JS, null)(child)

  def apply(props: Props)(child: ReactNode) =
    createElementN(JS, props)(child)

  def withBasename(base: String, child: ReactNode) = 
    createElement(JS, new Props { basename = base }, child)

  trait Props extends js.Object:
    var window: js.UndefOr[sdom.Window] = js.undefined
    var basename: js.UndefOr[String] = js.undefined
end HashRouter

object Link:
  @js.native
  @JSImport("react-router-dom", "Link")
  object JS extends ReactJSComponent

  def apply[P <: Props](props: P)(children: ReactNode*) = createElement(JS, props, children*)
  def apply[P <: Props](props: P) = createElement0(JS, props)

  // should drop href according react-router-dom docs because its
  // set by the component
  trait Props extends AnchorHTMLAttributes[sdom.html.Anchor]:
    var replace: js.UndefOr[Boolean] = js.undefined
    var state: js.UndefOr[js.Object | Null] = js.undefined
    var to: js.UndefOr[To] = js.undefined

object NavLink:
  @js.native
  @JSImport("react-router-dom", "NavLink")
  object JS extends ReactJSComponent

  def apply(props: Props) = createElement0(JS, props)

  trait Props extends Link.Props:
    //var `aria-current`: js.UndefOr[String] = js.undefined
    var activeClassName: js.UndefOr[String] = js.undefined
    var activeStyle: js.UndefOr[js.Object] = js.undefined
    //var className: js.UndefOr[String] = js.undefined
    //var to: js.UndefOr[LocationInit[_] | String] = js.undefined
    var caseSensitive: js.UndefOr[Boolean] = js.undefined
    var end: js.UndefOr[Boolean] = js.undefined
  
object Prompt:
  @js.native
  @JSImport("react-router-dom", "Prompt")
  object JS extends ReactJSComponent

  def apply(props: Props) = createElement0(JS, props)

  def apply(m: String, w: Boolean) = createElement0(JS, new Props { message = m; when = w })

  trait Props extends js.Object:
    var when: js.UndefOr[Boolean] = js.undefined
    var message: js.UndefOr[String] = js.undefined

object StaticRouter:
  @js.native
  @JSImport("react-router-dom", "StaticRouter")
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) = createElement(JS, props, children*)

  trait Props extends js.Object:
    var location: js.UndefOr[LocationInit[?]] = js.undefined
