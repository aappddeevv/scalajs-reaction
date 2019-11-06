// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package components
package fetch

import scala.scalajs.js
import js.JSConverters
import org.scalajs.dom

import ttg.react
import react._
import implicits._
import elements._

/**
  * Fetch data and render a child with a fetch status.  Child can process the
  * data and typically memoizes it if it transforms it e.g. sorts it or converts
  * the values. Fetch provides a generic `F` that must be an `Effect` so a
  * result can be "fetched." You can create the Fetcher and provide the fetch
  * "recipe" in `F` as a parameter or let the child initiate a fetch--you have a
  * choice. Allowing the child to initiate a "fetch" makes the API messy.  See
  * this
  * [blog](https://appddeevvmeanderings.blogspot.com/2018/12/abstracting-react-scalajs-react-fetcher.html)
  * for more details on how to define your Runner. Any type of cancellation
  * aspect should be handled in Runner.
 * 
 * Once you define your element by creating an instance of this class, you will
 * want to import the FetchState types. Import the dependent value types using
 * `import myFetcher._`.
 * 
 * @todo Bake in cancellable when unmounting.
 * 
 * @tparam F Fetch effect. Produces a P. F may also hold an error, an implied
 * Throwable. There are no constraints on F in this class because Runner
 * expresses an optionally synchronous computation.
 * 
 * @tparam P Result inside F. Generally can be broken out into E and T i.e. P is
 * often a coproduct of E and T. P exists in the type signature so that we do
 * not have to add a context constraint to F.
 * @tparam E Error data to be delivered to child. It is often a Throwable but is
 * dependent on the effect you are using and how you map your errors from that
 * effect e.g. convert a Throwable to another type.
 * @tparam T Resulting data to be delivered to child.
  */
class Fetcher[F[_], P, E, T](Name: String) {
  /** Load state passed to a child. */
  sealed trait FetchState

  /** Load was successful, hold item. */
  case class Success(item: T) extends FetchState

  /** Load resulted in an error. */
  case class Error(content: E) extends FetchState

  /** Loading still in progress. */
  case object Fetching extends FetchState

  /** Initial state until a fetch request is made. */
  case object NotRequested extends FetchState

  /** Initiate a fetch for P. */
  type FetchCallback = F[P] => Unit
  /** Given a fetch request `F[P]` and a callback, run the F and call the callback
   * to process the results. The results have to be split into an error part and
   * a "value" part so that the proper fetch state can be passed to the child.
   */
  type Runner = F[P] => (Either[E, T] => Unit) => Unit

  trait Props extends js.Object {
    val children: (FetchState, FetchCallback) => ReactNode
    val run: Runner
    val initialValue: Option[F[P]]
  }

  /** Provide data loading status to a child.
   * @param children Callback when fetch state changes. Convenience thunk to
   *  initiate fetch. Return child.
   * @param run Run a F[T] to obtain an error or a result.
   * @param initialValue Optional initial fetch, to kick things off.
   */
  def apply(props: Props) = sfc(props)

  val sfc = SFC1[Props] { props =>
    import props._
    React.useDebugValue(Name)
    val (fstate, setFState) = React.useStateStrictDirect[FetchState](NotRequested)
    // setFState is guaranteed stable
    val makeRequest = React.useCallback[F[P], Unit](fstate.asJsAny){f =>
      if(fstate != Fetching) {
        setFState(Fetching)
        props.run(f){ _ match {
          case Right(item) => setFState(Success(item))
          case Left(e) => setFState(Error(e))
        }}}
    }
        
    children(fstate, makeRequest)
  }
}

/** A Fetcher that keeps the runner at the class level. */
class Fetcher2[F[_],P,E,T](Name: String, runner: F[P] => (Either[E, T] => Unit) => Unit)
    extends Fetcher[F, P, E, T](Name) {
  def apply(
    children: (FetchState, FetchCallback) => ReactNode,
    initialValue: Option[F[P]] = None
  ) = sfc(js.Dynamic.literal(
    "children" -> children.asJsAny,
    "run" -> runner.asJsAny,
    "initialValue" -> initialValue.asJsAny
  ))
}

class FetcherHook[F[_], P, E](Name: String, runner: F[P] => (Either[E, P] => Unit) => Unit) {
  /** Load state passed to a child. */
  sealed trait FetchState

  /** Load was successful, hold item. */
  case class Success(item: P) extends FetchState

  /** Load resulted in an error. */
  case class Error(content: E) extends FetchState

  /** Loading still in progress. */
  case object Fetching extends FetchState

  /** Initial state until a fetch request is made. */
  case object NotRequested extends FetchState

  /** Initiate a fetch for P. */
  type FetchCallback = F[P] => Unit

  def useFetcher(remember: Boolean = false) = {
    React.useDebugValue(Name)
    val (fstate, setFState) = React.useStateStrictDirect[FetchState](NotRequested)
    // setFState is guaranteed stable
    val makeRequest = React.useCallback[F[P], Unit](fstate.asJsAny){f =>
      if(fstate != Fetching) {
        setFState(Fetching)
        runner(f){ _ match {
          case Right(item) => setFState(Success(item))
          case Left(e) => setFState(Error(e))
        }}}
    }
    (fstate, makeRequest)
  }
}
