// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react

import scala.scalajs.js
import js.|
import js.JSConverters._

package object redux {

  type Listener = js.Function0[Unit]
  type Unsubscriber = js.Function0[Unit]
  type Dispatcher = js.Function1[js.Object|js.Dynamic, Unit]

  type MSTP = js.Function2[js.Object, js.Object, js.Object]
  type MDTP = js.Function2[Dispatcher, js.Object, js.Object]
  type MP = js.Function3[js.Object, js.Object, js.Object, js.Object]

  /** 
   * Connect a scala component to the redux store. Returns a new Component.
   * @param maker A "make" function taking javascript side merged redux props to a component.
   */
  def connect[S, RP, A, SLF](
    component: Component[S, RP, A, SLF],
    mapStateToProps: Option[(js.Object, js.Object) => js.Object]=None,
    mapDispatchToProps: Option[(Dispatcher, js.Object) => js.Object] = None,
    mergeProps: Option[(js.Object, js.Object, js.Object) => js.Object] = None,
    connectOpts: Option[ConnectOpts] = None): ReactJSComponent = {

    // convert to js functions via scala.js implicit conversions, I'm lazy
    val mstp: js.UndefOr[MSTP] = mapStateToProps.map{f =>
      val x: MSTP = f
      x
    }.orUndefined
    val mdtp: js.UndefOr[MDTP] = mapDispatchToProps.map {f =>
      val x: MDTP = f
      x
    }.orUndefined
    val mp: js.UndefOr[MP]= mergeProps.map{ f =>
      val x: MP = f
      x
    }.orUndefined

    // this is a new "proxy" comonent created by connectAdvanced
    //ReactRedux.connect(mstp, mdtp, mp, connectOpts.orUndefined)(component.reactClassInternal)
    ReactRedux.connect(mstp, mdtp, mp, connectOpts.orUndefined)(component.reactClassInternal)
  }
}

