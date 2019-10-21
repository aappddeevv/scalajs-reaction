// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package react_router
package dom

import scala.scalajs.js
import js.annotation._
import js.|

@js.native
@JSImport("react-router-dom", JSImport.Namespace)
object hooks extends ReactRouterDOM

@js.native
trait ReactRouterDOM extends js.Object {
  def useHistory[S](): History[S] = js.native
  def useLocation[S](): Location[S] = js.native

  /** Values should be strings, can't enforce that though without using dict. */
  def useParams[T](): T = js.native
  @JSName("useParams")
  def useParamsDict(): js.Dictionary[String] = js.native
  @JSName("useRouteMatch")
  def useRouteMatchWithProps[S, T](arg: RouteProps[S]): Match[T] = js.native
  def useRouteMatch[T](arg: String | Array[String]): Match[T] = js.native
}

@js.native
trait Match[T] extends js.Object {
  val params: T = js.native
  val isExact: Boolean = js.native
  val path: String = js.native
  val url: String = js.native
}

/** History from package `history`. Allows you control navigation via "commands" */
@js.native
trait History[S] extends js.Object {
  val length: Int = js.native
  val action: Action = js.native
  val location: Location[S] = js.native
  def push(path: Path, state: js.UndefOr[S] = js.undefined): Unit = js.native
  def push[S](location: LocationDescriptor[S]): Unit = js.native
  def replace(path: Path, state: js.UndefOr[S] = js.undefined): Unit = js.native
  def replace[S](location: LocationDescriptor[S]): Unit = js.native
  def go(n: Int): Unit = js.native
  def goBack(): Unit = js.native
  def goForward(): Unit = js.native
  def createHref(location: LocationDescriptor[S]): Href = js.native
}

trait RouteProps[S] extends js.Object {
  var location: js.UndefOr[Location[S]] = js.undefined
  var component: js.UndefOr[js.Object] = js.undefined
  var render: js.UndefOr[js.Function1[js.Object, ReactNode]] = js.undefined
  var children: js.UndefOr[ReactNode] = js.undefined
  var path: js.UndefOr[String | Array[String]] = js.undefined
  var exact: js.UndefOr[Boolean] = js.undefined
  var sensitive: js.UndefOr[Boolean] = js.undefined
  var strict: js.UndefOr[Boolean] = js.undefined
}

trait LocationDescriptor[S] extends js.Object {
  var pathname: js.UndefOr[Pathname] = js.undefined
  var search: js.UndefOr[Search] = js.undefined
  var state: js.UndefOr[S] = js.undefined
  var hash: js.UndefOr[Hash] = js.undefined
  var key: js.UndefOr[LocationKey] = js.undefined
}

@js.native
trait Location[S] extends js.Object {
  val pathname: Pathname = js.native
  val search: Search = js.native
  val state: S = js.native
  val hash: js.UndefOr[Hash] = js.native
  val key: js.UndefOr[LocationKey] = js.native
}

@js.native
sealed trait Action extends js.Any
object Action {
  val PUSH = "PUSH".asInstanceOf[Action]
  val POP = "POP".asInstanceOf[Action]
  val REPLACE = "REPLACE".asInstanceOf[Action]
}

@js.native
trait RouterContext extends js.Object {
  // ugh, has router.histor and router.route
  var router: js.Dynamic = js.native
}

object Route {
  @js.native
  @JSImport("react-router-dom", "Route")
  object JS extends ReactJsComponent

  def always(child: ReactNode) = React.createElement(JS, null)(child)

  def apply(props: Props, child: ReactNode) =
    React.createElement(JS, props)(child)

  def apply(props: Props)(children: RouteComponentProps[_, _] => ReactNode) =
    React.createElement(JS, props)(js.Any.fromFunction1(children).asInstanceOf[ReactNode])

  def withPath(p: String, child: ReactNode) =
    React.createElement(JS, new Props { path=p })(child)

  def withExactPath(p: String, child: ReactNode) =
    React.createElement(JS, new Props { exact=true; path=p })(child)

  trait Props extends js.Object {
    var location: js.UndefOr[Location[_]] = js.undefined
    // component
    // skip component, not robust
    // render
    var render: js.UndefOr[js.Function1[RouteComponentProps[_, _], ReactNode]] = js.undefined
    // children thunk is in apply
    var path: js.UndefOr[String | Array[String]] = js.undefined
    var exact: js.UndefOr[Boolean] = js.undefined
    var sensitive: js.UndefOr[Boolean] = js.undefined
    var strict: js.UndefOr[Boolean] = js.undefined
  }

  /** P is params object. S is state stored in history. */
  @js.native
  trait RouteComponentProps[S, P] extends js.Object {
    val history: History[_] = js.native
    val location: Location[S] = js.native
    val `match`: Match[P] = js.native
  }
}

object HashRouter {
  @js.native
  @JSImport("react-router-dom", "HashRouter")
  object JS extends ReactJsComponent

  def apply(child: ReactNode) =
    React.createElement(JS, null)(child)

  def apply(props: Props)(child: ReactNode) =
    React.createElement(JS, props)(child)

  def withBasename(bn: String, child: ReactNode) =
    apply(new Props { basename = bn })(child)

  trait Props extends js.Object {
    var basename: js.UndefOr[String] = js.undefined
    var getUserConfirmation: js.UndefOr[js.Function2[String, js.Function1[Boolean, Unit], Unit]] = js.undefined
    var forceRefresh: js.UndefOr[Boolean] = js.undefined
    var keyLength: js.UndefOr[Int] = js.undefined
  }
}

object BrowserRouter {
  @js.native
  @JSImport("react-router-dom", "BrowserRouter")
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode) =
    React.createElement(JS, props)(children)

  trait Props extends js.Object {
    var basename: js.UndefOr[String] = js.undefined
    var getUserConfirmation: js.UndefOr[js.Function2[String, js.Function1[Boolean, Unit], Unit]] = js.undefined
    var forceRefresh: js.UndefOr[Boolean] = js.undefined
    var keyLength: js.UndefOr[Int] = js.undefined
  }
}

object Switch {
  @js.native
  @JSImport("react-router-dom", "Switch")
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    React.createElement(JS, props)(children: _*)

  def apply(children: ReactNode*) =
    React.createElement(JS, null)(children: _*)

  trait Props extends js.Object {
    var location: js.UndefOr[Location[_]] = js.undefined
  }
}

object Redirect {
  @js.native
  @JSImport("react-router-dom", "Redirect")
  object JS extends ReactJsComponent

  def to(t: String) = React.createElement0(JS, new Props {to = t})

  def apply(props: Props = null) =
    React.createElement0(JS, props)

  trait Props extends js.Object {
    var to: js.UndefOr[String] = js.undefined
    @JSName("to")    
    var toLocation: js.UndefOr[LocationDescriptor[_]] = js.undefined
    var push: js.UndefOr[Boolean] = js.undefined
    var from: js.UndefOr[String] = js.undefined
    // not sure this is there...
    //var path: js.UndefOr[String] = js.undefined
    var exact: js.UndefOr[Boolean] = js.undefined
    var strict: js.UndefOr[Boolean] = js.undefined
  }
}

object Prompt {
  @js.native
  @JSImport("react-router-dom", "Prompt")
  object JS extends ReactJsComponent

  def apply(props: Props = null) =
    React.createElement0(JS, props)

  trait Props extends js.Object {
    var when: js.UndefOr[Boolean] = js.undefined
    var message: js.UndefOr[js.Function1[String | Location[_], String | Boolean]] = js.undefined
  }
}
