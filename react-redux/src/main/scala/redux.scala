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
  def useSelector[S, A](selector: js.Function1[S, A]) = module.useSelector[S, A](selector)
  def useSelector[S, A](selector: js.Function1[S, A], equalityFn: js.Function2[A, A, Boolean]) =
    module.useSelector[S, A](selector, equalityFn)
  def useStore[State, A <: Action]() = module.useStore[State, A]()
}
