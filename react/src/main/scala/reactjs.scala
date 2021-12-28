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

package react

import scala.scalajs.js
import js.annotation._

@js.native
trait Children extends js.Object:
  def map(children: ReactChildren, f: js.Function1[ReactElement, ReactElement]): ReactChildren = js.native
  def map(children: ReactChildren, f: js.Function2[ReactElement, Int, ReactElement]): ReactChildren = js.native
  def forEach(children: ReactChildren, f: js.Function1[ReactElement, Unit]): Unit = js.native
  def forEach(children: ReactChildren, f: js.Function2[ReactElement, Int, Unit]): Unit = js.native
  def only(children: ReactChildren): ReactElement = js.native
  def count(children: ReactChildren): Int = js.native
  def toArray(children: ReactChildren): js.Array[ReactElement] = js.native

trait TransitionConfig extends js.Object:
  var timeoutMs: js.UndefOr[Int] = js.undefined

trait DeferredValueConfig extends js.Object:
  var timeoutMs: js.UndefOr[Int] = js.undefined

/** Concurrent features targeted at v18, I recall. */
@js.native
trait Concurrent extends js.Object:
  def useTransition(config: TransitionConfig): js.Tuple2[js.Function1[js.Function0[Unit], Unit], Boolean] = js.native
  def useDeferredValue(value: js.Any, config: DeferredValueConfig): js.Any = js.native
  val SuspenseList: ReactJSComponent = js.native
  // unstable_withSuspenseConfig
  def createMutableSource[S](source: scala.Any, getVersion: js.Function0[scala.Any]): MutableSource[S] = js.native

  /** subscribe 2nd arg is to be called whenever the value changes and is stable. Last is an unsubscribe for unmounting. */
  def useMutableSource[S, A](
    source: MutableSource[S],
    getSnapshot: js.Function1[S, A],
    subscribe: js.Function2[S, js.Function0[Unit], js.Function0[Unit]]): A = js.native

/** Opaque type. */
@js.native
trait MutableSource[T] extends js.Object

@js.native
trait ReactJS extends js.Object with Concurrent:

  val Children: Children = js.native

  /** We are *not* importing this to be used in a class oriented way. */
  val Component: js.Dynamic = js.native

  /** Can take a wide variety of types for tpe: string | sfc | class (extending
   * React.Component). P is kept very general here and public APIs should
   * customize as needed. Props is js.Any to force public APIs to think about
   * the props type before casting. Children can be in the props as well
   * if I recall correctly so the varags is a convenience.
   */
  def createElement(el: ReactType, props: js.Any, children: ReactNode*): ReactElement = js.native

  /** Avoid. */
  def cloneElement(el: ReactElement, props: js.Dynamic): ReactDOMElement = js.native

  val Fragment: ReactJSComponent = js.native
  val StrictMode: ReactJSComponent = js.native
  val Suspense: ReactJSComponent = js.native

  def createContext[T](defaultValue: T, calculateChangedBits: js.UndefOr[js.Function2[T, T, Int]]): ReactContext[T] =
    js.native

  /** Create a ref to be assigned to a "ref" property on a component. If you are using
   * function components and hooks, use `useRef` to get a `MutableRef`.
   */
  def createRef[T](defaultValue: T): react.ReactRef[T] = js.native

  /** Create a component that forwards a ref. You need to ensure your P (props)
   * has a `ref` variable in it so its available when passed along. The variable
   * should be a `react.ReactRef` instead of a `MutableRef`.
   */
  def forwardRef[P <: js.Object, T](inner: js.Function2[P, react.MutableRef[T], ReactNode]): ReactFC[P] = js.native

  val version: String = js.native

  /** Returns a "lazy" react component i.e. $$type = react.lazy. This is untyped
   * so it needs typing in the public API.
   */
  def `lazy`(lazyComponent: js.Any): js.Any = js.native

  /** Takes a function component and optional props comparison func. This returns
   * a function component as this is an HOC. The returned component still needs
   * to be wrapped properly to use with this facade. `P` is kept general here
   * but should be customized in the public API.
   */
  def memo[P](
    f: js.Function1[P, ReactNode],
    compare: js.UndefOr[js.Function2[js.Any, js.Any, Boolean]] = js.undefined
  ): js.Function1[P, ReactElement] = js.native

  def isValidElement(obj: js.Object): Boolean = js.native

/** Opaque type. */
trait DynamicImport extends js.Object:
  // If default is defined in the exports
  // other exports will be here as well
  val `default`: js.Function1[_ <: js.Object, ReactNode]

object DynamicImport:
  def apply(f: js.Function1[_ <: js.Object, ReactNode]) = new DynamicImport { val `default` = f }

/** Magnet pattern to create a friendly arg converter for effect hooks. As much
 * as possible these need to be casts vs allocations.
 */
@js.native
trait EffectArg extends js.Object

/** Implicit conversions that are very careful on the return value.
 * If the return value is a parameterless function, it is assumed
 * to be the reactjs "after-effect" callback. Otherwise, the return
 * value is discarded.
 */
object EffectArg:

  /** Convert a scala EffectCallbackArg to js using a proxy approach.
   * Use a general return of A vs unit to be more friendly. Requires
   * 2 scala => js function conversions. Ugh!
   */
  @inline def convertEffectCallbackArg[A](arg: () => (() => A)): js.Any = { () =>
    val rthunk = arg()
    js.Any.fromFunction0 { () => rthunk(); () }
  }: js.Function0[js.Function0[Unit]]

  /** No final callback.
   *
   *  @todo Not sure inner definition is needed.
   */
  @inline implicit def fromThunkJS[U](f: js.Function0[U]): EffectArg =
    js.Any.fromFunction0[Unit] { () => f(); () }.asInstanceOf[EffectArg]

  /** No final callback. */
  @inline implicit def fromThunk[U](f: () => U): EffectArg =
    js.Any.fromFunction0[Unit] { () => f(); () }.asInstanceOf[EffectArg]

  /** Return value from the callback is discarded. */
  @inline implicit def fromThunkCbA[A](f: () => (() => A)): EffectArg =
    convertEffectCallbackArg(f).asInstanceOf[EffectArg]

  /** Return value form the callback is discarded. */
  @inline implicit def fromThunkCbJS[A](f: () => js.Function0[A]): EffectArg =
    ((() => { val rthunk = f(); js.Any.fromFunction0 { () => rthunk(); () } }): js.Function0[js.Function0[Unit]])
      .asInstanceOf[EffectArg]

@js.native
trait StateSetterOrUpdater[T] extends js.Object:
  def apply(t: T): Unit = js.native
  def apply(f: js.Function1[T, T]): Unit = js.native

@js.native
trait Hooks extends js.Object:

  // expose imperative code on ref.current
  def useImperativeHandle[T, R <: js.Object](
    ref: MutableRef[T],
    thunk: js.Function0[R],
    dependencies: js.UndefOr[Dependencies]
  ): Unit = js.native

  def useContext[T](context: ReactContext[T]): T = js.native

  def useDebugValue[T](value: T, format: js.UndefOr[js.Function1[T, String]] = js.undefined): Unit = js.native

  // callback and the return value will require extensive casting so this is a template
  def useCallback(cb: js.Function, deps: js.UndefOr[Dependencies] = js.undefined): js.Any = js.native

  def useMemo[T](value: js.Function0[T], dependencies: Dependencies | Unit): T = js.native

  def useRef[T](initialValue: T): MutableRef[T] = js.native

  // Uses Object.is for state comparisons!
  def useReducer[S, A](reducer: js.Function2[S, A, S], initialState: S): js.Tuple2[S, Dispatch[A]] = js.native

  def useReducer[S, A, I](
    reducer: js.Function2[S, A, S],
    initialArg: I,
    init: js.Function1[I, S]
  ): js.Tuple2[S, Dispatch[A]] = js.native

  // js.Tuple2 is a shortcut to a 2 element array
  def useState[T](initialValue: js.Any): js.Tuple2[T, StateSetterOrUpdater[T]] = js.native

  // didUpdate is () => Unit or () => (() => Unit)
  def useEffect(didUpdate: js.Any, dependencies: Dependencies | Unit): Unit = js.native

  // didUpdate is () => Unit or () => (() => Unit)
  def useLayoutEffect(didUpdate: js.Any, dependencies: Dependencies | Unit): Unit = js.native

/** The raw js import of the react namespace. Kept private to this 
 * package so you must go through the public interface.
 */
@js.native
@JSImport("react", JSImport.Namespace)
private[react] object ReactJS extends ReactJS with Hooks

/** The module "object" that you import when you use react. */
@js.native
trait ReactModule extends js.Object

/** A publically accessible react namespace import for those
 *  interop scenarios where you need this. There are no
 *  scala visible methods.
 */
@js.native
@JSImport("react", JSImport.Namespace)
object ReactPublic extends ReactModule
