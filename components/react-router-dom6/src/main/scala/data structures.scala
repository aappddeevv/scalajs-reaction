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
import react._

// history
@js.native
abstract trait Action extends js.Any
object Action {
  val Pop = "POP".asInstanceOf[Action]
  val Push = "PUSH".asInstanceOf[Action]
  val Replace = "REPLACE".asInstanceOf[Action]
}

// history
trait PathPieces extends js.Object {
  var pathname: js.UndefOr[String] = js.undefined
  var search: js.UndefOr[String] = js.undefined
  var hash: js.UndefOr[String] = js.undefined
}

// history
trait LocationPieces[S] extends PathPieces {
  var state: js.UndefOr[S @scala.annotation.unchecked.uncheckedVariance] = js.undefined
  var key: js.UndefOr[LocationKey] = js.undefined
}

// history
/** Like Location but everything is optional. */
trait LocationInit[S] extends LocationPieces[S]

// history
object LocationInit {

  /** Create LocationInit from a pathname. */
  def apply[S](p: String) =
    new LocationInit[S] {
      pathname = p
    }

  implicit class RichLocationInit[S](private val li: LocationInit[S]) extends AnyVal {

    /** Attesting for LocationInit having all required fields to be a Location. */
    def hasRequired: Location[S] = li.asInstanceOf[Location[S]]
  }
}

// history
/** A simplified but overlapping version from the DOM lib per the js package
 * 'history'
 */
/*
@js.native
trait Location[+S] extends js.Object {
  val pathname: Pathname = js.native
  val search: Search = js.native
  def state[U >: S]: U = js.native
  val hash: Hash = js.native
  val key: LocationKey = js.native
}
 */

/** More specific types when returning from `useLocation`. */
trait Location[S] extends js.Object:
  val pathname: Pathname
  val search: Search
  def state[U >: S]: U
  val hash: Hash
  val key: LocationKey

object Location:
  implicit class RichLocation[S](private val location: Location[S]) extends AnyVal {
    def toLocationInit: LocationInit[S] = location.asInstanceOf[LocationInit[S]]
    def toLocationPieces: LocationPieces[S] = location.asInstanceOf[LocationPieces[S]]
  }


@js.native
trait ResolvedLocation extends js.Object:
  val pathname: Pathname
  val search: Search
  val hash: Hash

// history
@js.native
trait Update[S] extends js.Object:
  val action: Action = js.native
  val location: Location[S] = js.native

// history
@js.native
trait Transition[S] extends Update[S]:
  def retry(): Unit = js.native

// history
@js.native
trait History[S] extends js.Object:
  val action: Action = js.native
  val location: Location[S] = js.native
  def createHref(to: To): String = js.native
  def push(to: To, state: js.UndefOr[S] = js.undefined): Unit = js.native
  def replace(to: To, state: js.UndefOr[S] = js.undefined): Unit = js.native
  def go(n: Int): Unit = js.native
  def back(): Unit = js.native
  def forward(): Unit = js.native
  //def listen(listener: Listener[S]): js.Function0[Unit] = js.native
  def block(blocker: Blocker[S]): js.Function0[Unit] = js.native

@js.native
trait Navigator[S] extends js.Object:
  val action: Action = js.native
  def createHref(to: To): String = js.native
  def push(to: To, state: js.UndefOr[S] = js.undefined): Unit = js.native
  def replace(to: To, state: js.UndefOr[S] = js.undefined): Unit = js.native
  def go(n: Int): Unit = js.native
  def block(blocker: Blocker[S]): js.Function0[Unit] = js.native

/** Very similar to what you provide to the Route component. */
trait RouteSpecElement extends js.Object:
  val path: String
  val element: js.UndefOr[ReactNode] = js.undefined
  val children: js.UndefOr[RouteSpec] = js.undefined

trait RouteSpecRouteTo extends js.Object:
  val path: String
  // not sure this is right, some docs list redirectTo:
  val location: js.UndefOr[Location[?] | String] = js.undefined

@js.native
trait UnblockingTx extends js.Object

trait NavigateOptions[S] extends js.Object:
  var replace: js.UndefOr[Boolean] = js.undefined
  var state: js.UndefOr[S] = js.undefined

@js.native
trait NavigateFunction extends js.Object:
  def apply(to: To): Unit = js.native

  @JSName("apply")
  def apply(delta: Int): Unit = js.native

  /** to includes Path(string) and PathPieces! */
  @JSName("apply")
  def to(to: To): Unit = js.native

  @JSName("apply")
  def toPath(to: Path): Unit = js.native

  @JSName("apply")
  def toPieces(to: PathPieces, options: NavigateOptions[?]): Unit = js.native

  @JSName("apply")
  def toPath(to: String, options: NavigateOptions[?]): Unit = js.native

trait PathPatternParts:
  val path: String
  var caseSensitive: js.UndefOr[Boolean] = js.undefined
  var end: js.UndefOr[Boolean] = js.undefined

@js.native
trait PathMatch extends js.Object:
  val path: String = js.native
  val pathname: String = js.native
  def params[P <: js.Object]: P = js.native
  @JSName("params")
  val paramsDict: js.Dictionary[String] = js.native

trait PartialRouteObject extends js.Object:
  var path: js.UndefOr[String] = js.undefined
  var caseSensitive: js.UndefOr[Boolean] = js.undefined
  var element: js.UndefOr[ReactNode] = js.undefined
  //var preload: js.UndefOr[RoutePreloadFunction[_]] = js.undefined
  var children: js.UndefOr[js.Array[PartialRouteObject]] = js.undefined

@js.native
trait RouteObject extends js.Object:
  var path: String = js.native
  var caseSensitive: Boolean = js.native
  var element: ReactNode = js.native
  var children: js.UndefOr[js.Array[PartialRouteObject]] = js.native
