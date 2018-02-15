// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react

import scala.scalajs.js
import js.|
import js.JSConverters._

package object redux {

  /** A listener in redux is very coarse grained. You receive all events. */
  type Listener = js.Function0[Unit]
  type Unsubscriber = js.Function0[Unit]
  type Dispatcher = js.Function1[js.Object | js.Dynamic, Unit]

  /** drive type inference */
  type MSTP[RS, P] = (RS, P) => P

  /** drive type inference */
  type MDTP[P] = (Dispatcher, P) => P

  /** drive type inference */
  type MP[P] = (P, P, P) => P

  /**
    * @tparam RS redux state shape
    * @tparam P shape of component props
    */
  def connect[RS <: js.Object, P <: js.Object](
      jsComponent: ReactJsComponent,
      mapStateToProps: Option[(RS, P) => P] = None,
      mapDispatchToProps: Option[(Dispatcher, P) => P] = None,
      mergeProps: Option[(P, P, P) => P] = None,
      connectOpts: Option[ConnectOpts] = None): ReactJsComponent = {

    type MSTP[RS <: js.Object, P <: js.Object] = js.Function2[RS, P, P]
    type MDTP[RS <: js.Object, P <: js.Object] = js.Function2[Dispatcher, P, P]
    type MP[P <: js.Object] = js.Function3[P, P, P, P]

    // convert to js functions via scala.js implicit conversions, I'm lazy
    val mstp: js.UndefOr[MSTP[RS, P]] = mapStateToProps.map { f =>
      val x: MSTP[RS, P] = f
      x
    }.orUndefined
    val mdtp: js.UndefOr[MDTP[RS, P]] = mapDispatchToProps.map { f =>
      val x: MDTP[RS, P] = f
      x
    }.orUndefined
    val mp: js.UndefOr[MP[P]] = mergeProps.map { f =>
      val x: MP[P] = f
      x
    }.orUndefined

    // this is a new "proxy" comonent created by connectAdvanced
    ReactRedux.connect(mstp, mdtp, mp, connectOpts.orUndefined)(jsComponent)
  }
}
