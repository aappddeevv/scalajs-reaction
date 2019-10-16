// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
package redux

import scala.scalajs.js
import js.annotation._

/**
  * Create the store in javascript, import it as js.Any then you can set the
  * store in these props.
  */
trait ProviderProps extends js.Object {
  var store: js.UndefOr[js.Any] = js.undefined
}

/**
  * Client can obtain the state directly or dispatch an event directly
  * off a store.
  */
@js.native
trait Store[S, A <: redux.Action] extends js.Object {
  def getState(): S = js.native
  val dispatch: Dispatch[A] = js.native
  val subscribe: js.Function1[Listener, Unsubscriber] = js.native

  /** Reducer replacement is untyped. */
  val replaceReducer: js.Function1[js.Any, Unit] = js.native
}

@js.native
trait ReactReduxContextValue[S, A <: redux.Action] extends js.Object {
  val store: Store[S, A] = js.native
  val storeState: S = js.native
}

@js.native
@JSImport("react-redux", JSImport.Namespace)
object ReactRedux extends js.Object {

  def createProvider(storeKey: String): ReactClass = js.native

  /** Default Provider component whose store's name is "store". */
  val Provider: ReactClass = js.native

  //def RactReduxContext[S, A <: Action]: ReactContext[ReactReduxContextValue[S,A]] = js.native

  /** Must have same properties and Object.is used to compared the values of the
    * keys.
    */
  def shallowEqual(lhs: js.Any, rhs: js.Any): Boolean = js.native

  /** Wrap these in React.useCallback to avoid unnecessary renders. */
  def useDispatch[A <: Action](): Dispatch[A] = js.native

  /** Read notes on this at https://react-redux.js.org/next/api/hooks. */
  def useSelector[S, A](selector: js.Function1[S, A]): A = js.native

  /** Read notes on this at https://react-redux.js.org/next/api/hooks. */
  def useSelector[S, A](selector: js.Function1[S, A], equalityFn: js.Function2[A, A, Boolean]): A = js.native

  def useStore[State, A <: Action](): Store[State, A] = js.native
}
