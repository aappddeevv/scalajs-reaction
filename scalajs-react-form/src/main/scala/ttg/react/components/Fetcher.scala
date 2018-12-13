// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react.components
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
  * choice. Allowing the child to initiate a "fetch" makes the API messy.
 * 
 * @tparam F Fetch effect. Produces a P. F may also hold an error e.g. implied
 * Throwable.
 * @tparam P Result inside F. Generally can be broken out into E and T.
 * @tparam E Error data to be delivered to child.
 * @tparam T Resulting data to be delivered to child.
  */
class Fetcher[F[_], P, E, T](Name: String) {
  /** Load state passed to a child. Kept outside of Fetcher so avoid "crossing
   * streams." (mixing and matching FetchState and child).
   */
  sealed trait FetchState

  /** Load was successful, hold item. */
  case class Success(item: T) extends FetchState

  /** Load resulted in an error. */
  case class Error(content: E) extends FetchState

  /** Loading still in progress. */
  case object Fetching extends FetchState

  /** Initial state until a fetch request is made. */
  case object NotRequested extends FetchState

  // internal component state and actions
  case class State(loadState: FetchState = NotRequested)  
  sealed trait Action
  case class Fetched(item: T) extends Action
  case class FetchError(content: E) extends Action
  case class Request(fetch: F[P]) extends Action

  val c = reducerComponent[State, Action](Name)
  import c.ops._

  type FetchCallback = F[P] => Unit
  type Runner = F[P] => (Either[E, T] => Unit) => Unit

  /** Provide data loading status to the child. */
  def apply(
    /** Callback when fetch state changes. Convenience thunk to initiate fetch. */
    cb: (FetchState, FetchCallback) => ReactNode,
    /** Run a F[T] to obtain an error or a result. */
    run: Runner,
    /** Initial fetch. */
    initialValue: Option[F[P]]
  ) =
    c.copy(new methods {
      val initialState = _ => State()
      didMount = js.defined(self => initialValue.foreach(f => self.send(Request(f))))
      val reducer = (action, state, gen) => {
        action match {
          case Request(f) =>
            gen.updateAndEffect(state.copy(loadState = Fetching)) { self =>
              val process: Either[E, T] => Unit =  {
                case Right(item) => self.send(Fetched(item))
                case Left(e) => self.send(FetchError(e))
              }
              run(f)(process)
            }
          case FetchError(content) =>
            gen.update(state.copy(loadState = Error(content)))
          case Fetched(item) =>
            gen.update(state.copy(loadState = Success(item)))
        }
      }
      val render = self =>
      cb(
        self.state.loadState,
        f => self.send(Request(f))
      )
    })
}

