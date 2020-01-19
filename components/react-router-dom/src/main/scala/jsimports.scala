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

package react_router
package dom

import scala.scalajs.js

import js.annotation._
import js.|

import react._

@js.native
@JSImport("react-router-dom", JSImport.Namespace)
object hooks extends ReactRouterDOM

@js.native
trait ReactRouterDOM extends js.Object {
  def useHistory[S](): History[S]   = js.native
  def useLocation[S](): Location[S] = js.native

  /** Values should be strings, can't enforce that though without using dict. */
  def useParams[T](): T = js.native
  @JSName("useParams")
  def useParamsDict(): js.Dictionary[String] = js.native
  @JSName("useRouteMatch")
  def useRouteMatchWithProps[S, T](arg: RouteProps[S]): Match[T] = js.native
  def useRouteMatch[T](arg: String | Array[String]): Match[T]    = js.native
}

/**
 * Match object often passed to render props in the Route element.
 *
 * @tparam T Params object. If you do not need params you can use Null, js.Any, etc.
 */
@js.native
trait Match[T] extends js.Object {
  val params: T        = js.native
  val isExact: Boolean = js.native
  val path: String     = js.native
  val url: String      = js.native
}

/** History from package `history`. Allows you control navigation via "commands"
 *
 * @tparam S State, often js.Any, Null or Nothing if you do not need state.
 */
@js.native
trait History[S] extends js.Object {
  val length: Int                                                 = js.native
  val action: Action                                              = js.native
  val location: Location[S]                                       = js.native
  def push(path: Path, state: js.UndefOr[S] = js.undefined): Unit = js.native
  @JSName("push")
  def pushLocation[S](location: LocationDescriptor[S]): Unit = js.native
  @JSName("push")
  def pushLocation[S](location: Location[S]): Unit                   = js.native
  def replace(path: Path, state: js.UndefOr[S] = js.undefined): Unit = js.native
  def replace[S](location: LocationDescriptor[S]): Unit              = js.native
  def go(n: Int): Unit                                               = js.native
  def goBack(): Unit                                                 = js.native
  def goForward(): Unit                                              = js.native
  def createHref(location: LocationDescriptor[S]): Href              = js.native
}

trait RouteProps[S] extends js.Object {
  var location: js.UndefOr[Location[S]]                      = js.undefined
  var component: js.UndefOr[js.Object]                       = js.undefined
  var render: js.UndefOr[js.Function1[js.Object, ReactNode]] = js.undefined
  var children: js.UndefOr[ReactNode]                        = js.undefined
  var path: js.UndefOr[String | Array[String]]               = js.undefined
  var exact: js.UndefOr[Boolean]                             = js.undefined
  var sensitive: js.UndefOr[Boolean]                         = js.undefined
  var strict: js.UndefOr[Boolean]                            = js.undefined
}

/** Like Location but everything is optional. */
trait LocationDescriptor[S] extends js.Object {
  var pathname: js.UndefOr[Pathname] = js.undefined
  var search: js.UndefOr[Search]     = js.undefined
  var state: js.UndefOr[S]           = js.undefined
  var hash: js.UndefOr[Hash]         = js.undefined
  var key: js.UndefOr[LocationKey]   = js.undefined
}

/** A simplified but overlapping version from the DOM lib per the js package
 * 'history'
 */
@js.native
trait Location[S] extends js.Object {
  val pathname: Pathname           = js.native
  val search: Search               = js.native
  val state: S                     = js.native
  val hash: js.UndefOr[Hash]       = js.native
  val key: js.UndefOr[LocationKey] = js.native
}

@js.native
sealed trait Action extends js.Any
object Action {
  val PUSH    = "PUSH".asInstanceOf[Action]
  val POP     = "POP".asInstanceOf[Action]
  val REPLACE = "REPLACE".asInstanceOf[Action]
}

@js.native
trait RouterContext extends js.Object {
  // ugh, has router.histor and router.route
  var router: js.Dynamic = js.native
}

/*** Route, most commonly used router component. Type parameter `S` stands for
 * "state" which is state you can add to the history stack per push. `P` is a js
 * object for query parameters, hence, each property should have a string value.
 * Using the children props always renders regardless of path match. Typically,
 * use the render prop.
 */
object Route {
  @js.native
  @JSImport("react-router-dom", "Route")
  object JS extends ReactJsComponent

  def always(children: ReactNode) = createElement(JS, null)(children)

  def always[S, P](children: js.Function1[RouteComponentProps[S, P], ReactNode]) =
    createElement(JS, null)(children.asInstanceOf[ReactNode])

  def apply[S, P](props: Props[S, P], child: ReactNode) =
    createElement(JS, props)(child)

  /** Uses children prop. */
  def apply[S, P](props: Props[S, P])(children: js.Function1[RouteComponentProps[S, P], ReactNode]) =
    createElement(JS, props)(children.asInstanceOf[ReactNode])

  def withPath(p: String, child: ReactNode) =
    createElement(JS, new Props[Nothing, Nothing] { path = p })(child)

  /** Uses children prop. */
  def withPath[S, P](p: String)(children: js.Function1[RouteComponentProps[S, P], ReactNode]) =
    createElement(JS, new Props[S, P] { path = p })(children.asInstanceOf[ReactNode])

  /** Uses render prop. */
  def withPathRender[S, P](p: String)(thunk: js.Function1[RouteComponentProps[S, P], ReactNode]) =
    createElement0(JS, new Props[S, P] { path = p; render = thunk })

  def withExactPath(p: String, child: ReactNode) =
    createElement(JS, new Props[Nothing, Nothing] { exact = true; path = p })(child)

  /** Uses render prop. */
  def withExactPathRender[S, P](p: String)(thunk: js.Function1[RouteComponentProps[S, P], ReactNode]) =
    createElement0(JS, new Props[S, P] { exact = true; path = p; render = thunk })

  trait Props[S, P] extends js.Object {
    var location: js.UndefOr[Location[_]] = js.undefined
    // component
    // skip component, not robust
    // render
    /** Only renders if route matched. children prop always renders regardless of match. */
    var render: js.UndefOr[js.Function1[RouteComponentProps[S, P], ReactNode]] = js.undefined
    // children thunk is in apply
    var path: js.UndefOr[String | Array[String]] = js.undefined
    var exact: js.UndefOr[Boolean]               = js.undefined
    var sensitive: js.UndefOr[Boolean]           = js.undefined
    var strict: js.UndefOr[Boolean]              = js.undefined
  }
}

/** P is params object. S is state stored in history. If you are not using either,
 * you can use `RouteComponentProps[Null, Nothing]`. */
@js.native
trait RouteComponentProps[S, P] extends js.Object {
  val history: History[S]   = js.native
  val location: Location[S] = js.native

  /** Could be null so we should add `|Null`. */
  val `match`: Match[P] = js.native
}

object HashRouter {
  @js.native
  @JSImport("react-router-dom", "HashRouter")
  object JS extends ReactJsComponent

  def apply(child: ReactNode) =
    createElement(JS, null)(child)

  def apply(props: Props)(child: ReactNode) =
    createElement(JS, props)(child)

  def withBasename(bn: String, child: ReactNode) =
    apply(new Props { basename = bn })(child)

  trait Props extends js.Object {
    var basename: js.UndefOr[String]                                                             = js.undefined
    var getUserConfirmation: js.UndefOr[js.Function2[String, js.Function1[Boolean, Unit], Unit]] = js.undefined
    var forceRefresh: js.UndefOr[Boolean]                                                        = js.undefined
    var keyLength: js.UndefOr[Int]                                                               = js.undefined
  }
}

object BrowserRouter {
  @js.native
  @JSImport("react-router-dom", "BrowserRouter")
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode) =
    createElement(JS, props)(children)

  trait Props extends js.Object {
    var basename: js.UndefOr[String]                                                             = js.undefined
    var getUserConfirmation: js.UndefOr[js.Function2[String, js.Function1[Boolean, Unit], Unit]] = js.undefined
    var forceRefresh: js.UndefOr[Boolean]                                                        = js.undefined
    var keyLength: js.UndefOr[Int]                                                               = js.undefined
  }
}

object Switch {
  @js.native
  @JSImport("react-router-dom", "Switch")
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    createElement(JS, props)(children: _*)

  def apply(children: ReactNode*) =
    createElement(JS, null)(children: _*)

  trait Props extends js.Object {
    var location: js.UndefOr[Location[_]] = js.undefined
  }
}

object Redirect {
  @js.native
  @JSImport("react-router-dom", "Redirect")
  object JS extends ReactJsComponent

  def to(t: String)                        = createElement0(JS, new Props { to = t         })
  def toLocation(t: LocationDescriptor[_]) = createElement0(JS, new Props { toLocation = t })

  def apply(props: Props = null) =
    createElement0(JS, props)

  trait Props extends js.Object {
    var to: js.UndefOr[String] = js.undefined
    @JSName("to")
    var toLocation: js.UndefOr[LocationDescriptor[_]] = js.undefined
    var push: js.UndefOr[Boolean]                     = js.undefined
    var from: js.UndefOr[String]                      = js.undefined
    // not sure this is there...
    //var path: js.UndefOr[String] = js.undefined
    var exact: js.UndefOr[Boolean]  = js.undefined
    var strict: js.UndefOr[Boolean] = js.undefined
  }
}

object Prompt {
  @js.native
  @JSImport("react-router-dom", "Prompt")
  object JS extends ReactJsComponent

  def apply(props: Props = null) =
    createElement0(JS, props)

  trait Props extends js.Object {
    var when: js.UndefOr[Boolean]                                                 = js.undefined
    var message: js.UndefOr[js.Function1[String | Location[_], String | Boolean]] = js.undefined
  }
}
