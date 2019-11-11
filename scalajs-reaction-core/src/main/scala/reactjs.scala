// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js
import js.annotation._
import js.|
import js._
import js.JSConverters._
import js.Dynamic.{literal => lit}

import org.scalajs.dom

@js.native
trait Children extends js.Object {
 def map(children: ReactChildren, f: js.Function1[ReactElement, ReactElement]): ReactChildren = js.native
    def map(children: ReactChildren, f: js.Function2[ReactElement, Int, ReactElement]): ReactChildren = js.native
    def forEach(children: ReactChildren, f: js.Function1[ReactElement, Unit]): Unit = js.native
    def forEach(children: ReactChildren, f: js.Function2[ReactElement, Int, Unit]): Unit = js.native
    def only(children: ReactChildren): ReactElement = js.native
    def count(children: ReactChildren): Int = js.native
    def toArray(children: ReactChildren): js.Array[ReactElement] = js.native
}

trait TransitionConfig extends js.Object {
  var timoutMs: js.UndefOr[Int] = js.undefined
}

trait DeferredValueConfig extends js.Object {
  var timoutMs: js.UndefOr[Int] = js.undefined
}

@js.native
trait Concurrent extends js.Object {
  def useTransition(config: TransitionConfig): js.Tuple2[js.Any, js.Any] = js.native
  def useDeferredValue(value: js.Any, config: DeferredValueConfig): js.Any = js.native
  val SuspenseList: ReactJsComponent = js.native
  // unstable_withSuspenseConfig
}

@js.native
private[react] trait ReactJS extends js.Object with Concurrent {

  val Children: Children = js.native

  /** We are not importing this to be used in a class oriented way. */
  val Component: js.Dynamic = js.native

  /** Can take a wide variety of types for tpe: string | sfc | class (extending
   * React.Component). P is kept very general here an is not even forced to be a
   * js object.
   */
  def createElement[P](
      el: ReactType,
      props: UndefOr[P],
      children: ReactNode*): ReactElement = js.native

  // should not use rally ever...
  def cloneElement(el: ReactElement, props: js.Dynamic): ReactDOMElement = js.native

  val Fragment: ReactJsComponent = js.native
  val StrictMode: ReactJsComponent = js.native
  val Suspense: ReactJsComponent = js.native

  def createContext[T](
      defaultValue: T,
      calculateChangedBits: js.UndefOr[js.Function2[T, T, Int]]): ReactContext[T] = js.native

  /** Create a ref to be assigned to a "ref" property on a component. */
  def createRef[T](): react.ReactRef[T]  = js.native

  // needs better typing :-)
  def forwardRef[T](): js.Object = js.native

  val version: String = js.native

  /** Returns a "lazy" react component i.e. $$type = react.lazy */
  def `lazy`(lazyComponent: DynamicImportThunk): ReactJsLazyComponent = js.native

  /** Takes a function component and optional props comparison func. This returns
   * a function component as this is an HOC. The returned component still needs
   * to be wrapped properly to use with this facade. `P` is kept very general
   * here.
   */
  def memo[P](f: js.Function1[P,ReactNode],
    compare: js.UndefOr[js.Function2[P,P,Boolean]] = js.undefined):
      js.Function1[P, ReactElement] = js.native

  def isValidElement(obj: js.Object): Boolean = js.native
}

/** Opaque type.*/
@js.native
trait DynamicImport extends js.Object {
  // If default is defined in the exports
  // other exports will be here as well
  val `default`: ReactJsComponent
}

/** Magnet pattern to create a friendly arg converter for effect hooks. As much 
 * as possible these need to be casts vs creating new functions.
 */
@js.native
trait EffectArg extends js.Object

object EffectArg {

  @inline implicit def fromThunkJS(f: js.Function0[Unit]): EffectArg =
    f.asInstanceOf[EffectArg]
  
  @inline implicit def fromThunk(f: () => Unit): EffectArg =
    js.Any.fromFunction0[Unit](f).asInstanceOf[EffectArg]

  @inline implicit def fromAny[A](f: () => A): EffectArg =
    js.Any.fromFunction0[A](f).asInstanceOf[EffectArg]

  @inline implicit def fromThunkCb(f: () => (() => Unit)): EffectArg =
    convertEffectCallbackArg(f).asInstanceOf[EffectArg]

  @inline implicit def fromThunkCbA[A](f: () => (() => A)): EffectArg =
    convertEffectCallbackArg(f).asInstanceOf[EffectArg]

  @inline implicit def fromThunkCbJS[A](f: () => js.Function0[A]): EffectArg =
    ({() => f()}:js.Function0[js.Function0[A]]).asInstanceOf[EffectArg]
}

@js.native
private [react] trait Hooks extends js.Object {

  // expose imperative code on ref.current
  def useImperativeHandle[T, R <: js.Object](ref: MutableRef[T], thunk: js.Function0[R],
    dependencies: Dependencies): Unit = js.native

  def useContext[T](context: ReactContext[T]): T = js.native

  def useDebugValue[T](value: T, format: js.UndefOr[js.Function1[T, String]] = js.undefined): Unit = js.native

  // callback and the return value will require extensive casting
  def useCallback(cb: js.Any, deps: Dependencies): js.Any = js.native

  def useMemo[T](value: js.Function0[T], dependencies: Dependencies): T = js.native

  def useRef[T](initialValue: T): MutableRef[T] = js.native

  // Uses Object.is for state comparisons!
  def useReducer[S, A](
    reducer: js.Function2[S, A, S],
    initialState: S
  ): js.Tuple2[S, Dispatch[A]] = js.native

  def useReducer[S, A, I](
    reducer: js.Function2[S, A, S],
    initialArg: I,
    init: js.Function1[I,S]
  ): js.Tuple2[S, Dispatch[A]] = js.native

  // js.Tuple2 is a shortcut to a 2 element array
  def useState[T](initialValue: js.Any): js.Tuple2[T, js.Any] = js.native

  // didUpdate is () => Unit or () => (() => Unit)
  def useEffect(didUpdate: js.Any, dependencies: Dependencies): Unit = js.native

  // didUpdate is () => Unit or () => (() => Unit)
  def useLayoutEffect(didUpdate: js.Any,
    dependencies: js.UndefOr[js.Array[js.Any]] = js.undefined): Unit = js.native    
}

/** Public access to react API. */
@js.native
@JSImport("react", JSImport.Namespace)
object ReactJS extends ReactJS with Hooks
