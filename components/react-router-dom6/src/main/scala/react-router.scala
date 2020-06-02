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

import scala.scalajs.js
import js.|

package object react_router6 {
  import react_router6._

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
  type RoutePreloadFunction[S] = js.Function3[js.Dictionary[String]|js.Object|js.Dynamic, Location[S], Int, Unit]
  
  type RouteSpec = js.Array[RouteSpecElement | RouteSpecRouteTo]
  
  /** LocationDescriptor is Location but all vars are optional. */
  implicit class Location2LocationDescriptor[S](location: Location[S]) {
    def toLocationInit: LocationInit[S] = location.asInstanceOf[LocationInit[S]]
  }

  def useBlocker[S](blocker: Blocker[S], when: js.UndefOr[Boolean] = js.undefined) = module.useBlocker(blocker, when)
  def useHref[S](to: To) = module.useHref[S](to)
  def useInRouterContext() = module.useInRouterContext()
  def useLocation[S]() = module.useLocation[S]()
  def useLocationPending() = module.useLocationPending()
  def useMatch(to: PathPattern) = module.useMatch(to)
  def useNavigate() = module.useNavigate()
  def useOutlet() = module.useOutlet()
  def useParams[P <: js.Object]() = module.useParams[P]()
  def useParamsDict() = module.useParamsDict()
  def useResolvedLocation(to: To) = module.useResolvedLocation(to)
  def useRoutes(
    routes: js.Array[PartialRouteObject],
    basename: js.UndefOr[String] = js.undefined) = module.useRoutes(routes, basename)
}
