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

package react_redux

import scala.scalajs.js
import js.annotation.*

/** Keys should be properties, values, js.Anys. Extend your Action trait from
 * this trait to ensure it has a `type` property.
 */
trait Action extends js.Object:
  val `type`: String

/**
 * Create the store in javascript, import it as js.Any then you can set the
 * store in these props.
 */
trait ProviderProps extends js.Object:
  var store: js.UndefOr[js.Any] = js.undefined

/**
 * Client can obtain the state directly or dispatch an event directly
 * off a store.
 */
@js.native
trait Store[S, A <: Action] extends js.Object:
  def getState(): S = js.native
  val dispatch: Dispatch[A] = js.native
  val subscribe: js.Function1[Listener, Unsubscriber] = js.native

  /** Reducer replacement is untyped. */
  val replaceReducer: js.Function1[js.Any, Unit] = js.native

@js.native
trait ReactReduxContextValue[S, A <: Action] extends js.Object:
  val store: Store[S, A] = js.native
  val storeState: S = js.native

