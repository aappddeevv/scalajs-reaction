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

import scala.scalajs.js

import js.annotation._
import js.|

import react._

/** Very similar to what you provide to the Route component. */
trait RouteSpecElement extends js.Object {
  val path: String
  val element: js.UndefOr[ReactNode] = js.undefined
  val children: js.UndefOr[RouteSpec] = js.undefined
}

trait RouteSpecRouteTo extends js.Object {
  val path: String
  // not sure this is right, some docs list redirectTo:
  val location: js.UndefOr[Location[_] | String] = js.undefined
}

@js.native
trait UnblockingTx extends js.Object {}

trait NavigateOptions[S] extends js.Object {
  var replace: js.UndefOr[Boolean] = js.undefined
  var state: js.UndefOr[S] = js.undefined
}

@js.native
trait NavigateAction[S] extends js.Object {
   @JSName("apply")
   def toLocation(to: LocationInit[S]): Unit = js.native
   @JSName("apply")
   def toPath(to: String): Unit = js.native
   @JSName("apply")
   def toLocation(to: LocationInit[S], options: NavigateOptions[S]): Unit = js.native
   @JSName("apply")
   def toPath(to: String, options: NavigateOptions[S]): Unit = js.native
   def apply(to: LocationInit[S]|String): Unit = js.native
   def apply(to: LocationInit[S]|String, options: NavigateOptions[S]): Unit = js.native
}

@js.native
trait Hooks extends js.Object {
  def useBlocker(blocker: js.Function1[UnblockingTx, Unit], when: js.UndefOr[Boolean] = js.undefined): Unit = js.native
  def useHref[S](to: LocationInit[S] | String): String = js.native

  /** Should not need to use this according to docs. */
  def useLocation[S](): Location[S] = js.native
  /** Use this to avoid Route entries everywhere. */
  def useMatch[S](to: LocationInit[S] | String): Boolean = js.native
  def useNavigate[S](): NavigateAction[S] = js.native
  def useOutlet(): ReactNode = js.native
  def useParams[P](): P = js.native
  def useResolvedLocation[S](to: LocationInit[S] | String): Location[S] = js.native
  def useRoutes(
    routes: RouteSpec,
    basename: js.UndefOr[String] = js.undefined,
    caseSensitive: js.UndefOr[Boolean] = js.undefined): ReactNode = js.native

}

@js.native
trait Utils extends js.Object {
  def createRoutesFromChildren(children: js.UndefOr[js.Any] = js.undefined): js.Array[js.Any] = js.native
  def matchRoutes[S](
    routes: RouteSpec,
    location: Location[S] | String,
    basename: js.UndefOr[String] = js.undefined,
    caseSensitive: js.UndefOr[Boolean] = js.undefined): Location[S] | Null = js.native
  def resolveLocation[S](to: Location[S] | String, fromPathName: String): Location[S] = js.native
  def generatePath(pathname: String, params: js.Dictionary[String] | js.Object): String = js.native
}

@js.native
@JSImport("react-router", JSImport.Namespace)
object module extends Hooks with Utils


trait NavigateProps extends js.Object {
  var pathname: js.UndefOr[Pathname] = js.undefined
  var search: js.UndefOr[Search] = js.undefined
  var hash: js.UndefOr[Hash] = js.undefined
}

/** Like Location but everything is optional. */
trait LocationInit[+S] extends NavigateProps {
  var state: js.UndefOr[S @scala.annotation.unchecked.uncheckedVariance] = js.undefined
  var key: js.UndefOr[LocationKey] = js.undefined
}

object LocationInit {
  def apply[S](pathname: String) = 
    js.Dynamic.literal("pathname" -> pathname).asInstanceOf[LocationInit[S]]
}

/** A simplified but overlapping version from the DOM lib per the js package
 * 'history'
 */
@js.native
trait Location[+S] extends js.Object {
  val pathname: Pathname = js.native
  val search: Search = js.native
  def state[U >: S]: U = js.native
  val hash: js.UndefOr[Hash] = js.native
  val key: js.UndefOr[LocationKey] = js.native
}

object MemoryRouter {
  @js.native
  @JSImport("react-router", "MemoryRouter")
  object JS extends ReactJSComponent

  def apply(props: Props = null)(children: ReactNode*) =
    createElement(JS, props, children: _*)

  def apply(children: ReactNode*) =
    createElement(JS, null, children: _*)

  trait Props extends js.Object {
    var initialEntries: js.UndefOr[js.Any] = js.undefined
    var initialIndex: js.UndefOr[js.Any] = js.undefined
    var timeout: js.UndefOr[Int] = js.undefined
  }
}

object Navigate {
  @js.native
  @JSImport("react-router", "Navigate")
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) =
    createElement(JS, props, children: _*)

  def to(to: NavigateProps | String) =
    createElement(
      JS,
      js.Dynamic.literal(
        "to" -> to.asInstanceOf[js.Any],
      ))

  def to(to: NavigateProps | String, state: js.Any) =
    createElement(
      JS,
      js.Dynamic.literal(
        "to" -> to.asInstanceOf[js.Any],
        "state" -> state
   ))
    
  def replace(to: NavigateProps | String) =
    createElement(
      JS,
      js.Dynamic.literal(
        "to" -> to.asInstanceOf[js.Any],
        "replace" -> true,
      ))
    
  def replace(to: NavigateProps | String, state: js.Any) =
    createElement(
      JS,
      js.Dynamic.literal(
        "to" -> to.asInstanceOf[js.Any],
        "replace" -> true,
        "state" -> state
      ))

  trait Props extends js.Object {
    val to: NavigateProps | String
    var replace: js.UndefOr[Boolean] = js.undefined
    var state: js.UndefOr[_] = js.undefined
  }
}

object Outlet {
  @js.native
  @JSImport("react-router", "Outlet")
  object JS extends ReactJSComponent

  def apply(): ReactNode = createElement(JS, null)
}

object Route {
  @js.native
  @JSImport("react-router", "Route")
  object JS extends ReactJSComponent

  def apply(props: Props) = createElement(JS, props)
  
  /** Use `Route` children. */
  def nested(props: Props)(children: ReactNode*) = createElement(JS, props, children:_*)
  
  /** Non-specialized version. */
  def apply(path: String, element: ReactNode) =
    createElement(JS, js.Dynamic.literal("path" -> path, "element" -> element))

  /** Children should be `Route` nodes. `element` should use `Outlet` to show selected child. */
  def nested(path: String, element: ReactNode)(children: ReactNode*) =
    createElement(JS, js.Dynamic.literal("path" -> path, "element" -> element), children:_*)
    
  /** Path is `*`. */
  def always(element: ReactNode) =
    createElement(JS, js.Dynamic.literal("path" -> "*", "element" -> element))

  /** Appends `slash asterisk` to the path. Your element should *not* use `Outlet`. */
  def descendant(path: String, element: ReactNode) =
    createElement(JS, js.Dynamic.literal("path" -> (path + "/*"), "element" -> element))
    
  /** Uses `/` as the path. Typically used with nested route parent to render at the
   * root of the parent.
   */
  def index(element: ReactNode) =
    createElement(JS, js.Dynamic.literal("path" -> "/", "element" -> element))
    
  /** Use `slash asterisk` as the path. `element` should use `Outlet`. */
  def indexNested(element: ReactNode)(children: ReactNode*) = 
    createElement(JS, js.Dynamic.literal("path" -> "/*", "element" -> element), children:_*)
    
    
  trait Props extends js.Object {
    val path: js.UndefOr[String] = js.undefined
    var element: js.UndefOr[ReactNode] = js.undefined
  }
}

object Router {
  @js.native
  @JSImport("react-router", "Router")
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) =
    createElement(JS, props, children: _*)

  def apply(children: ReactNode*) =
    createElement(JS, null, children: _*)

  trait Props extends js.Object {
    var history: js.UndefOr[js.Any] = js.undefined
    var timeout: js.UndefOr[Int] = js.undefined
  }
}

object Routes {
  @js.native
  @JSImport("react-router", "Routes")
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) =
    createElement(JS, props, children: _*)

  def apply(children: ReactNode*) =
    createElement(JS, null, children: _*)

  def basename(bname: js.UndefOr[String], children: ReactNode*) =
     createElement(JS, new Props { basename = bname }, children:_*)
    
  trait Props extends js.Object {
    var caseSensitive: js.UndefOr[Boolean] = js.undefined
    var basename: js.UndefOr[String] = js.undefined
  }
}

