// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
package redux

import scala.scalajs.js
import js.annotation._

private[react] trait ConnectOpts extends js.Object {
  val pure: js.UndefOr[Boolean] = js.undefined
  val storeKey: js.UndefOr[String] = js.undefined
}

trait ProviderProps extends js.Object {
  var store: js.UndefOr[js.Any] = js.undefined
}

@js.native
trait Store extends js.Object {
  def getState[S <: js.Any](): S = js.native
  val dispatch: Dispatcher = js.native
  val subscribe: js.Function1[Listener, Unsubscriber] = js.native
}

@js.native
@JSImport("react-redux", JSImport.Namespace)
object ReactRedux extends js.Object {

  def createProvider(storeKey: String): ReactClass = js.native

  /** Default Provider component whose store's name is "store". */
  val Provider: ReactClass = js.native

  def connect(
    mapStateToProps: js.UndefOr[js.Any] = js.undefined,
    mapDispatchToProps: js.UndefOr[js.Any] = js.undefined,
    mergeProps: js.UndefOr[js.Any] = js.undefined,
    connectOpts: js.UndefOr[ConnectOpts] = js.undefined): js.Function1[js.Any, ReactJSComponent] = js.native
}
