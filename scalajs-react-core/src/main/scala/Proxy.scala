// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js

/**
  * Data structure used to create js side react class. It's only used once in
  * scalareact.createClass. Most of the methods below recover the scala side
  * "element" (the component spec) and then forwards the call on this "class" to
  * the scala side "element." Thees methods are only called by the proxy/shim object
  * to hook up the internal API.
  */
trait Proxy[S, RP, A] extends js.Object {

  /** The type of SLF to provide to the scala-side, client visible API. */
  type SLF

  /** The object stored in reactjs this.state. */
  type State <: TotalState[S, RP, A, SLF]

  /** The object stored in reactjs this.props.scalaProps. */
  type PropsType

  /** The object stored in reactjs this.props. */
  type ThisSelfProps <: JsComponentThisProps[PropsType]

  /** What scala sees as "this" when our scala-side component proxy is called from reactjs. */
  type ThisSelf <: JsComponentThis[ThisSelfProps, State, PropsType]

  val displayName: String

  /**
    * Make a special record for the single parameter used when calling
    * client-API lifecycle methods on the scala side.
    */
  def mkSelf(self: ThisSelf, sopt: Option[S], popt: Option[RP]): SLF

  /**
    * Calculate the next TotalState given the current TotalState and a StateUpdate directive.
    * This function could live almost anywhere and has no need for "this".
    */
  def transitionNextTotalState(curTotalState: State, scalaStateUpdate: StateUpdate[S, SLF]): State

  /** Send an action. */
  def sendMethod(self: ThisSelf, action: A): Unit

  /** Handle a callback. Curry your component's callback method to accept only `this`. */
  def handleMethod(self: ThisSelf, cb: SLF => Unit): Unit

  /** Subscription "unmount" callbacks. */
  var subscriptions: Seq[() => Unit]

  /** react js method. */
  val getInitialState: js.ThisFunction0[ThisSelf, State]

  /** react js method. */
  val render: js.ThisFunction0[ThisSelf, ReactNode]

  /** react js method. */
  val componentWillReceiveProps: js.ThisFunction1[ThisSelf, ThisSelfProps, Unit]

  /** react js method. */
  val componentWillUnmount: js.ThisFunction0[ThisSelf, Unit]

  /** react js method. */
  val componentDidMount: js.ThisFunction0[ThisSelf, Unit]

  /** react js method. */
  val shouldComponentUpdate: js.ThisFunction2[ThisSelf, ThisSelfProps, State, Boolean]

  /** React js method. */
  val componentWillUpdate: js.ThisFunction2[ThisSelf, ThisSelfProps, State, Unit]

  /** react js method. */
  val componentDidUpdate: js.ThisFunction2[ThisSelf, ThisSelfProps, State, Unit]

  /** This should be removed. */
  val contextTypes: js.UndefOr[js.Object]

  /** react js method. v16+ */
  val componentDidCatch: js.ThisFunction2[ThisSelf, js.Error, ErrorInfo, Unit]
}
