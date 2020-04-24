import scala.scalajs.js
import js.|

package object react_router6 {
  import react_router6._

  type Hash = String
  type LocationKey = String
  type Search = String
  type Pathname = String

  type RouteSpec = js.Array[RouteSpecElement | RouteSpecRouteTo]
  
  /** LocationDescriptor is Location but all vars are optional. */
  implicit class Location2LocationDescriptor[S](location: Location[S]) {
    def toLocationInit: LocationInit[S] = location.asInstanceOf[LocationInit[S]]
  }

  def useBlocker(blocker: js.Function1[UnblockingTx, Unit], when: js.UndefOr[Boolean] = js.undefined) =
    module.useBlocker(blocker, when)
  def useHref[S](to: LocationInit[S] | String) = module.useHref[S](to)
  def useLocation[S](): Location[S] = module.useLocation[S]()
  def useMatch[S](to: LocationInit[S] | String) = module.useMatch[S](to)
  def useNavigate[S]() = module.useNavigate[S]()
  def useOutlet() = module.useOutlet()
  def useParams[P]() = module.useParams[P]()
  def useResolvedLocation[S](to: LocationInit[S] | String) = module.useResolvedLocation[S](to)
  def useRoutes(
    routes: RouteSpec,
    basename: js.UndefOr[String] = js.undefined,
    caseSensitive: js.UndefOr[Boolean] = js.undefined) = module.useRoutes(routes, basename, caseSensitive)

}
