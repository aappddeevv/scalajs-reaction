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

@js.native
private[react] trait ReactJS extends js.Object {

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
  val unstable_ConcurrentMode: ReactJsComponent = js.native
  val unstable_Profiler: ReactJsComponent = js.native

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

/**
 * Scala-side access to JSReact APIs. Some of these methods overlay the raw JS
 * version to provide the convenience of using scala types as inputs and saving
 * a few conversion nits.
 * 
 * Some of the *N() hook functions like useCallback may be quite inefficient in
 * their conversion from scala functions to js functions and back. Perhaps we
 * need another parallel API function set with the js functions being assumed
 * for those code bases that already have the js versions. Note that useCallback
 * returns a js function so it can be used as a dependency.
 */
trait React {
  /** Create a DOM element via its string name. */
  def createDOMElement(tag: String, props: js.Object|js.Dynamic)(
    children: ReactNode*): ReactDOMElement = 
    ReactJS.createElement(tag, props, children:_*).asInstanceOf[ReactDOMElement]

  /** Create an element with props and children. */
  def createElement(tag: ReactType, props: js.Object)(
      children: ReactNode*): ReactElement =
    ReactJS.createElement(tag, props, children: _*)

  /** Create an element with props and 0 children. */
  def createElement0(tag: ReactType, props: js.Object): ReactElement =
    ReactJS.createElement(tag, props)

  /** Create an elemnt with 1 child only. */
  def createElement1(tag: ReactType, props: js.Object, child: ReactNode): ReactElement =
    ReactJS.createElement(tag, props, child)

  /** Create an element without props but with children. */
  def createElement(tag: ReactType)(
    children: ReactNode*): ReactElement =
    ReactJS.createElement(tag, js.undefined, children: _*)

  /** Create a react fragment. Fragments are created as an "element" with a
    * specific tag (symbol or number if target does not support symbol) vs say,
    * the string "div".
    */
  def createFragment(key: Option[String], children: ReactNode*): ReactElement = {
    val props = js.Dictionary.empty[js.Any]
    key.foreach(props("key") = _)
    ReactJS.createElement(ReactJS.Fragment, props, children: _*)
  }

  /** Create a `ReactRef`. */
  def createRef[T](): react.ReactRef[T]  = ReactJS.createRef[T]()

  /** Memoize a functional component defined in scala. Standard js comparison
   * semantics using Object.is will be used on the props.
   */
  def memo[P <: js.Object](fc: SFC1[P]): SFC1[P] = new SFC1(ReactJS.memo(fc.run))

  /** Memoize a functional component defined in scala; provide a compare function
   * for the props.
   */
  def memo[P <: js.Object](fc: SFC1[P], compare: (P,P) => Boolean): SFC1[P] =
    new SFC1(ReactJS.memo(fc.run, js.Any.fromFunction2[P,P,Boolean](compare)))

  /** Use general P and scala equality comparisons to detect prop changes. */
  def memoScala[P](fc: SFC1[P]): SFC1[P] =
    new SFC1(ReactJS.memo(fc.run, js.Any.fromFunction2[P,P,Boolean]((l,r) => l == r)))

  def useContext[T](context: ReactContext[T]): T = ReactJS.useContext(context)

  /** Initial value is strict. Setter is an updater. Don't use this one. */
  def useStateStrict[T](initial: T) = {
    val c = ReactJS.useState[T](initial.asInstanceOf[js.Any])
    (c._1, c._2.asInstanceOf[js.Function1[js.Function1[T,T], Unit]])
  }

  /** Initial value is a "lazy". Setter is an updater. Use this one or
   * useReducer.
   */
  def useState[T](initial: () => T) = {
    val c = ReactJS.useState[T](js.Any.fromFunction0[T](initial))
    (c._1, c._2.asInstanceOf[js.Function1[js.Function1[T,T], Unit]])
  }

  /** Initial value is strict. Set new value directly. Don't use this. */
  def useStateStrictDirect[T](initial: T) = {
    val c = ReactJS.useState[T](initial.asInstanceOf[js.Any])
    //(c._1, (t:T) => c._2.asInstanceOf[js.Function1[T, Unit]](t))
    (c._1, c._2.asInstanceOf[js.Function1[T, Unit]])
  }

  /** Initial value is a "lazy". Set new value directly. Don't use this. */
  def useStateDirect[T](initial: () => T) = {
    val c = ReactJS.useState[T](js.Any.fromFunction0[T](initial))
    //(c._1, t => c._2.asInstanceOf[js.Function1[T, Unit]](t))
    (c._1, c._2.asInstanceOf[js.Function1[T, Unit]])
  }

  def useReducer[S, A](reducer: (S,A)=>S, initialState: S): (S, Dispatch[A]) = {
    val c = ReactJS.useReducer(reducer, initialState)
    (c._1, c._2)
  }

  def useReducer[S, A, I](reducer: (S,A)=>S, initialArg: I, init: I => S): (S, Dispatch[A]) = {
    val c = ReactJS.useReducer(reducer, initialArg, init)
    (c._1, c._2)
  }

  /** Effect is run on every render which is probably too much. */
  def useEffectAlways(didUpdate: EffectArg) =
    ReactJS.useEffect(didUpdate, undefinedDependencies)

  /** Effect is run when dependencies change. */
  def useEffect(didUpdate: EffectArg, dependencies: Dependencies) =
    ReactJS.useEffect(didUpdate, dependencies)

  /** Effect is run when dependencies change. */
  def useEffect(dependencies: js.Any*)(didUpdate: EffectArg) =
    ReactJS.useEffect(didUpdate, dependencies.toJSArray)

  /** Effect is run when dependencies change. */
  def useEffect(dependencies: Dependencies)(didUpdate: EffectArg) =
    ReactJS.useEffect(didUpdate, dependencies)

  /** Effect is run at mount/unmount time with `[]` as the 2nd argument. */
  def useEffectMounting(didUpdate: EffectArg) =
    ReactJS.useEffect(didUpdate, emptyDependencies)

  /** Run before browser updates the screen. Kinda a "sync" effect. */
  def useLayoutEffect(didUpdate: EffectArg, dependencies: Dependencies) =
    ReactJS.useLayoutEffect(didUpdate, dependencies)

  /** Memoize a lazy value. */
  def useMemo[T](value: () => T, dependencies: Dependencies): T =
    ReactJS.useMemo[T](value, dependencies)

  /** Memoize a lazy value. */
  def useMemo[T](dependencies: js.Any*)(value: () => T): T =
    ReactJS.useMemo[T](value, dependencies.toJSArray)

  /** Memoize a lazy value. */
  def useMemo[T](dependencies: Dependencies)(value: js.Function0[T]): T =
    ReactJS.useMemo[T](value, dependencies)

  /** Memoize a lazy value. Set on mounting. */
  def useMemoMounting[T](value: () => T): T = ReactJS.useMemo[T](value, emptyDependencies)

  def useDebugValue[T](value: T): Unit =
    ReactJS.useDebugValue[T](value, js.undefined)

  def useDebugValue[T](value: T, format: T => String): Unit =
    ReactJS.useDebugValue[T](value, js.Any.fromFunction1[T, String](format))

  /** Default API is for no-arg callback. Declare as val yourCB = useCallback... */
  def useCallback[T](callback: js.Function0[T], dependencies: Dependencies): js.Function0[T] =
    ReactJS.useCallback(callback, dependencies).asInstanceOf[js.Function0[T]]

  def useCallback[T](dependencies: Dependencies)(callback: js.Function0[T]): js.Function0[T] =
    ReactJS.useCallback(callback, dependencies).asInstanceOf[js.Function0[T]]

  def useCallback[T](dependencies: js.Any*)(callback: js.Function0[T]): js.Function0[T] =
    ReactJS.useCallback(callback, dependencies.toJSArray).asInstanceOf[js.Function0[T]]

  def useCallback[A1, T](callback: js.Function1[A1, T], dependencies: Dependencies): js.Function1[A1,T] =
    ReactJS.useCallback(callback, dependencies).asInstanceOf[js.Function1[A1,T]]

  def useCallback[A1, T](dependencies: js.Any*)(callback: js.Function1[A1, T]): js.Function1[A1,T] =
    ReactJS.useCallback(callback, dependencies.toJSArray).asInstanceOf[js.Function1[A1,T]]

  def useCallback[A1, A2, T](callback: (A1,A2) => T, dependencies: Dependencies): js.Function2[A1,A2,T] =
    ReactJS.useCallback(
      js.Any.fromFunction2[A1,A2,T](callback), dependencies).asInstanceOf[js.Function2[A1,A2,T]]

  def useCallback[A1, A2, T](dependencies: js.Any*)(callback: js.Function2[A1,A2,T]): js.Function2[A1,A2,T] =
    ReactJS.useCallback(callback, dependencies.toJSArray).asInstanceOf[js.Function2[A1,A2,T]]

  def useCallback[A1, A2, A3, T](callback: (A1,A2,A3) => T, dependencies: Dependencies): js.Function3[A1,A2,A3,T] =
    ReactJS.useCallback(
      js.Any.fromFunction3[A1,A2,A3,T](callback), dependencies).asInstanceOf[js.Function3[A1,A2,A3,T]]

  def useCallback[A1, A2, A3, A4, T](callback: (A1,A2,A3,A4) => T, dependencies: Dependencies): js.Function4[A1,A2,A3,A4,T] =
    ReactJS.useCallback(
      js.Any.fromFunction4[A1,A2,A3,A4,T](callback), dependencies).asInstanceOf[js.Function4[A1,A2,A3,A4,T]]

  def useCallback[A1, A2, A3, A4, A5, T](callback: (A1,A2,A3,A4,A5) => T, dependencies: Dependencies): js.Function5[A1,A2,A3,A4,A5,T] =
    ReactJS.useCallback(
      js.Any.fromFunction5[A1,A2,A3,A4,A5,T](callback), dependencies).asInstanceOf[js.Function5[A1,A2,A3,A4,A5,T]]

  def useRef[T](initialValue: T): MutableRef[T] = ReactJS.useRef[T](initialValue)

  /** Expose imperative functions in R to refs.
   * @tparam T Ref type
   * @tparam R js object whose properties are functions. This is not enforced in
   * the type.
   */
  def useImperativeHandle[T, R <: js.Object](ref: MutableRef[T],
    thunk: () => R, dependencies: Dependencies): Unit =
    ReactJS.useImperativeHandle[T,R](ref, thunk, dependencies)

  def `lazy`(lazyComponent: DynamicImportThunk): ReactJsLazyComponent =
    ReactJS.`lazy`(lazyComponent)

  val Fragment = ReactJS.Fragment
  val StrictMode = ReactJS.StrictMode
  val Suspense = ReactJS.Suspense
  val Children= ReactJS.Children
  val unstable_ConcurrentMode = ReactJS.unstable_ConcurrentMode
  val unstable_Profiler = ReactJS.unstable_Profiler

}

/** Primary entry point into the React API. */
object React extends React

/**
  * React's context object contains the consumer and provider "components".
  */
@js.native
trait ReactContext[T] extends js.Object {
  type ValueType = T

  /** Only takes a single attribute value, "value" with the context . */
  val Provider: ReactJsComponent = js.native
  val Consumer: ReactJsComponent = js.native

  /** Not public API. */
  var currentValue: T = js.native

  /** Not public API. */
  val defaultValue: T = js.native
}
