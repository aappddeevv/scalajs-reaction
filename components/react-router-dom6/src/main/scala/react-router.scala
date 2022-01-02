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
import js.annotation.*
import react.*
import org.scalajs.{dom => sdom}

// history
type Hash = String
type LocationKey = String
type Search = String
type Pathname = String
type Path = String
//type State = js.Object | Null
type Blocker[S] = js.Function1[Transition[S], Unit]
type To = Path | PathPieces
type InitialEntry[S] = Path | LocationPieces[S]

// not history
type PathPattern = String | PathPatternParts
type RoutePreloadFunction[S] = js.Function3[js.Dictionary[String] | js.Object | js.Dynamic, Location[S], Int, Unit]

type RouteSpec = js.Array[RouteSpecElement | RouteSpecRouteTo]

type URLSearchParamsInit = sdom.experimental.URLSearchParams | String | js.Array[js.Any] | js.Object

// ...
// in process--migrating defs to natives defined in the package object
// ...
@js.native @JSImport("react-router-dom", "useBlocker")
def useBlocker[S](blocker: Blocker[S], when: js.UndefOr[Boolean] = js.undefined): Unit = js.native

@js.native @JSImport("react-router-dom", "useHref")
def useHref[S](to: To): String = js.native

//def useHref[S](to: To): String = js.native
@js.native @JSImport("react-router-dom", "useInRouterContext")
def useInRouterContext(): Boolean = js.native

/** Should not need to use this according to docs. */
@js.native @JSImport("react-router-dom", "useLocation")
def useLocation[S](): Location[S] = js.native

@js.native @JSImport("react-router-dom", "useLocationPending")
def useLocationPending(): Boolean = js.native

trait LinkClickHandlerOptions[S] extends NavigateOptions[S]:
  var target: js.UndefOr[String] = js.undefined

@js.native @JSImport("react-router-dom", "useLinkClickHandler")
def useLinkClickHandler(to: To, context: js.UndefOr[LinkClickHandlerOptions[?]] = js.undefined): js.Function0[Unit] = js.native

/** Use this to avoid Route entries everywhere. */
@js.native @JSImport("react-router-dom", "useMatch")
def useMatch(to: PathPattern): PathMatch | Null = js.native

@js.native @JSImport("react-router-dom", "useNavigate")
def useNavigate(): NavigateFunction = js.native

// @js.native @JSImport("react-router-dom", "useNavigationType")
// def useNavigationType(): NavigateFunction = js.native

@js.native @JSImport("react-router-dom", "useOutlet")
def useOutlet(): ReactNode = js.native

@js.native @JSImport("react-router-dom", "useOutletContext")
def useOutletContext(): Unit = js.native

@js.native @JSImport("react-router-dom", "useParams")
def useParams[P <: js.Object](): P = js.native

@js.native @JSImport("react-router-dom", "uesParams")
def useParamsDict(): js.Dictionary[String] = js.native

//def useResolvedLocation(to: To): ResolvedLocation = js.native

@js.native @JSImport("react-router-dom", "useResolvedPath")
def useResolvedPath(to: To): ResolvedLocation = js.native

@js.native @JSImport("react-router-dom", "useRoutes")
def useRoutes(
  routes: js.Array[PartialRouteObject],
  basename: js.UndefOr[String] = js.undefined
  ): ReactNode = js.native

@js.native @JSImport("react-router-dom", "createRoutesFromChildren")
def createRoutesFromChildren(children: js.UndefOr[js.Any] = js.undefined): js.Array[RouteObject] = js.native

@js.native @JSImport("react-router-dom", "createRoutesFromChildren")
def createRoutesFromArray(array: js.Array[PartialRouteObject]): js.Array[RouteObject] = js.native

@js.native @JSImport("react-router-dom", "matchPath")
def matchPath(pattern: PathPattern, pathname: String): PathMatch | Null = js.native

@js.native @JSImport("react-router-dom", "matchRoutes")
def matchRoutes[S](
  routes: RouteSpec,
  location: Location[S] | String,
  basename: js.UndefOr[String] = js.undefined,
  caseSensitive: js.UndefOr[Boolean] = js.undefined): Location[S] | Null = js.native

@js.native @JSImport("react-router-dom", "resolveLocation")
def resolveLocation[S](to: Location[S] | String, fromPathName: String): Location[S] = js.native

@js.native @JSImport("react-router-dom", "generatePath")
def generatePath(pathname: String, params: js.Dictionary[String] | js.Object): String = js.native

@js.native @JSImport("react-router-dom", "usePrompt")
def usePrompt(message: String, when: js.UndefOr[Boolean] = js.undefined): Unit = js.native

@js.native @JSImport("react-router-dom", "useSearchParams")
def useSearchParams(init: js.UndefOr[URLSearchParamsInit] = js.undefined): sdom.experimental.URLSearchParams = js.native
    
@js.native @JSImport("react-router-dom", "createSearchParams")
def createSearchParams(init: URLSearchParamsInit): sdom.experimental.URLSearchParams = js.native

@js.native @JSImport("react-router", "resolvePath")
def resolvePath(to: To, fromPathname: js.UndefOr[String] = js.undefined): PathPieces = js.native
