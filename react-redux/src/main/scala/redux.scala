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
import react.*

/** A listener in redux is very coarse grained. You receive all events. */
type Listener = js.Function0[Unit]
type Unsubscriber = js.Function0[Unit]

/** Dispatch an action via the store. */
type Dispatch[A <: Action] = js.Function1[A | js.Dynamic, Unit]

/** Helper to make type a dispatch function into js. */
def dispatch[A <: Action](f: A | js.Dynamic => Unit): Dispatch[A] = f


@js.native @JSImport("react-redux", "createProvider")
def createProvider(storeKey: String): ReactJSComponent = js.native

/** Default Provider component whose store's name is "store". */
@js.native @JSImport("react-redux", "Provider")
val Provider: ReactJSComponent = js.native

/** Must have same properties and Object.is used to compared the values of the
 * keys.
 */
@js.native @JSImport("react-redux", "shallowEqual")
def shallowEqual(lhs: js.Any, rhs: js.Any): Boolean = js.native

/** Wrap these in React.useCallback to avoid unnecessary renders. */
@js.native @JSImport("react-redux", "useDispatch")
def useDispatch[A <: Action](): Dispatch[A] = js.native

/** Read notes on this at https://react-redux.js.org/next/api/hooks. */
@js.native @JSImport("react-redux", "useSelector")
def useSelector[S, A](selector: js.Function1[S, A]): A = js.native

/** Read notes on this at https://react-redux.js.org/next/api/hooks. */
@js.native @JSImport("react-redux", "useSelector")
def useSelector[S, A](selector: js.Function1[S, A], equalityFn: js.Function2[A, A, Boolean]): A = js.native

@js.native @JSImport("react-redux", "useStore")
def useStore[State, A <: Action](): Store[State, A] = js.native
