// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

import scala.scalajs.js
import js.|

package object react_redux {

  /** Keys should be properties, values, js.Anys. Extend your Action trait from
    * this trait to ensure it has a `type` property.
    */
  trait Action extends js.Object {
    val `type`: String
  }

  /** A listener in redux is very coarse grained. You receive all events. */
  type Listener = js.Function0[Unit]
  type Unsubscriber = js.Function0[Unit]

  /** Dispatch an action via the store. */
  type Dispatch[A <: Action] = js.Function1[A | js.Dynamic, Unit]

  /** Helper to make type a dispatch function into js. */
  def dispatch[A <: Action](f: A | js.Dynamic => Unit): Dispatch[A] = f


  import react_redux.module

  def createProvider(storeKey: String) = module.createProvider(storeKey)
  val Provider = module.Provider
  def shallowEqual(lhs: js.Any, rhs: js.Any) = module.shallowEqual(lhs, rhs)
  def useDispatch[A <: Action]() = module.useDispatch[A]()
  def useSelector[S, A](selector: js.Function1[S, A]) = module.useSelector[S,A](selector)
  def useSelector[S, A](selector: js.Function1[S, A], equalityFn: js.Function2[A, A, Boolean]) =
    module.useSelector[S,A](selector, equalityFn)
  def useStore[State, A <: Action]() = module.useStore[State,A]()
}
