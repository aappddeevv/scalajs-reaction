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
import js.Dynamic.{ literal => lit }
import js.JSConverters.*
import js.*

/**
 * Scala-side access to js react APIs. Some of these methods overlay the raw JS
 * version to provide the convenience of using scala types as inputs and saving
 * a few conversion nits.
 *
 * Some of the *N() hook functions like useCallback may be quite inefficient in
 * their conversion from scala functions to js functions and back. Perhaps we
 * need another parallel API function set with the js functions being assumed
 * for those code bases that already have the js versions. Note that useCallback
 * returns a js function so it can be used as a dependency.
 */
trait React:

  def createContext[T](defaultValue: T): ReactContext[T] =
    ReactJS.createContext(defaultValue, js.undefined)

  /** Clone a ReactElement and add new props. You should not use this if you can avoid it. */
  def cloneElement(
    element: ReactElement,
    props: js.Object
  ): ReactElement =
    ReactJS.cloneElement(element, props.asInstanceOf[js.Dynamic])

  /**
   * Given a js.Object (or subclass), find a "children" property and assume its
   * an array of ReactNodes. If not found, return an empty js array. This
   * function is used for interop where your scala "make" method takes children
   * as a separate parameter but your props (event JS friendly props) does not
   * contain the children property--it's there if there are children on the js
   * side. This is not needed in scalajs-react because the children are passed
   * and managed explicitly.
   *
   * @todo Seems like this is an expensive call. Can we do better?
   */
  @inline def extractChildren(item: js.UndefOr[js.Object]|Null): js.Array[ReactNode] =
    if item == null then js.Array() // need this since could be a "defined" null
    else
      item.toOption
        .flatMap(_.asInstanceOf[js.Dictionary[js.Array[ReactNode]]].get("children"))
        .getOrElse[js.Array[ReactNode]](js.Array())

  /** Create a DOM element using the string name e.g. "div". */
  def createDOMElement(
    tag: String,
    props: js.Object | js.Dynamic | Null
  )(
    children: ReactNode*
  ): ReactDOMElement =
    ReactJS.createElement(tag, props.asInstanceOf[js.Object], children*).asInstanceOf[ReactDOMElement]

  /** Create an element with props and 0 children. */
  def createElement0(
    tag: ReactType,
    props: js.Object | js.Dynamic | Null | Unit
  ): ReactElement =
    ReactJS.createElement(tag, props.asInstanceOf[js.Object])

  /** Create an elemnt with 1 child only. */
  def createElement1(
    tag: ReactType,
    props: js.Object | js.Dynamic | Null | Unit,
    child: ReactNode
  ): ReactElement =
    ReactJS.createElement(tag, props.asInstanceOf[js.Object], child)

  /** Create an element with props and children. */
  def createElementN(
    tag: ReactType,
    props: js.Object | js.Dynamic | Null | Unit
  )(
    children: ReactNode*
  ): ReactElement =
    ReactJS.createElement(tag, props.asInstanceOf[js.Object], children*)

  /** Create an element without props but with children as a separate parameter. */
  def createElementN(tag: ReactType)(children: ReactNode*): ReactElement =
    ReactJS.createElement(tag, js.undefined, children*)

  /** Create an element with some props. Children can be in the props. */
  def createElement[P <: js.Object](
    component: ReactType,
    props: P|Null
  ) =
    ReactJS.createElement(component, props)

  /** Create an element using the standard var args signature. */
  def createElement[P <: js.Object](component: ReactType, props: P|Null, children: ReactNode*): ReactElement =
    ReactJS.createElement(component, props, children*)

  /** For creating quick ReactElements using js.Dynamic. Input is a scala function
   * taking a single props argument.
   *s
   */
  @deprecated
  def unsafeCreateElement(component: () => ReactNode) = {
    val jsc = js.Any.fromFunction0(component).asInstanceOf[ReactType]
    createElement0(jsc, null)
  }

  /**
   *
   */
  @deprecated
  def unsafeCreateElement(component: js.Dynamic => ReactNode, props: js.Dynamic) = {
    val jsc = js.Any.fromFunction1(component).asInstanceOf[ReactType]
    createElement0(jsc, props)
  }

  /** Create a react fragment. Fragments are created as an "element" with a
   * specific tag (symbol or number if target does not support symbol) vs say,
   * the string "div".
   */
//   def createFragment(
//     key: Option[String],
//     children: ReactNode*
//   ): ReactElement = {
//     val props = js.Dictionary.empty[js.Any]
//     key.foreach(props("key") = _)
//     ReactJS.createElement(ReactJS.Fragment, props, children: _*)
//   }

  /** Create a `ReactRef`. If you are using function components
   * and hooks, use `useRef` to get a `MutableRef`. This returns
   * a different ref type.
   */
  def createRef[T](initialValue: T): react.ReactRef[T] = ReactJS.createRef[T](initialValue)

  /** Memoize a function. */
  def memo[P <: js.Object](fc: js.Function1[P, ReactNode]) =
    ReactJS.memo(js.Any.fromFunction1(fc)).asInstanceOf[js.Function1[P, ReactNode]]

  /** Memoize with a function and a comparator. */
  def memo[P <: js.Object](
    fc: js.Function1[P, ReactNode],
    compare: js.Function2[js.UndefOr[P], js.UndefOr[P], Boolean]
  ) =
    ReactJS
      .memo(fc, compare.asInstanceOf[js.Function2[js.Any, js.Any, Boolean]])
      .asInstanceOf[js.Function1[P, ReactNode]]

  def useContext[T](context: ReactContext[T]): T = ReactJS.useContext(context)

  /** Initial value is strict. Setter is an updater. */
  def useStateStrict[T](initial: T): (T, StateSetterOrUpdater[T]) =
    ReactJS.useState[T](initial.asInstanceOf[js.Any]).asInstanceOf[js.Tuple2[T, StateSetterOrUpdater[T]]]

  /** Initial value is a "lazy". Update thunk is a setter or updater. Use this one or
   * useReducer.
   */
  def useState[T](initial: () => T): (T, StateSetterOrUpdater[T]) =
    ReactJS
      .useState[T](js.Any.fromFunction0[T](initial))
      .asInstanceOf[js.Tuple2[T, StateSetterOrUpdater[T]]]

  /** Initial value is strict. Set new value directly. */
  def useStateStrictDirect[T](initial: T): (T, js.Function1[T, Unit]) =
    ReactJS.useState[T](initial.asInstanceOf[js.Any]).asInstanceOf[js.Tuple2[T, js.Function1[T, Unit]]]

  /** Initial value is a "lazy". Set new value directly. */
  def useStateDirect[T](initial: () => T): (T, js.Function1[T, Unit]) =
    ReactJS.useState[T](js.Any.fromFunction0[T](initial)).asInstanceOf[js.Tuple2[T, js.Function1[T, Unit]]]

  def useReducer[S, A](
    reducer: (S, A) => S,
    initialState: S
  ): (S, Dispatch[A]) = ReactJS.useReducer(reducer, initialState)

  def useReducer[S, A, I](
    reducer: (S, A) => S,
    initialArg: I,
    init: I => S
  ): (S, Dispatch[A]) = ReactJS.useReducer(reducer, initialArg, init)

  /** Effect is always run on render, which is probably too much. */
  @deprecated("Use useEffectEveryRender")
  def useEffectAlways(didUpdate: EffectArg) =
    ReactJS.useEffect(didUpdate, undefinedDependencies)

  /** Effect is always run on render, which is probably too much. */    
  def useEffectEveryRender(didUpdate: EffectArg) =
    ReactJS.useEffect(didUpdate, undefinedDependencies)

  /** Effect is run when dependencies change. */
  def useEffectA(dependencies: Dependencies)(didUpdate: EffectArg) =
    ReactJS.useEffect(didUpdate, dependencies)

  // def useEffect(didUpdate: EffectArg, dependencies: js.UndefOr[Dependencies]) =
  //   ReactJS.useEffect(didUpdate, dependencies)

  /** Effect is run when dependencies change. Unlike the JS version, the dependencies
   * come first.
  */
  def useEffect(dependencies: AllType*)(didUpdate: EffectArg) =
    ReactJS.useEffect(
      didUpdate,
      if dependencies.length == 0 then undefinedDependencies
      else dependencies.toJSArray
    )

  /** Effect is run at mount/unmount time via `useEffect` with an implied `[]` per the react docs. */
  def useEffectMounting(didUpdate: EffectArg) =
    ReactJS.useEffect(didUpdate, emptyDependencies)

  def useEffectUnmounting(thunk: js.Function0[Unit]) =
    ReactJS.useEffect(js.Any.fromFunction0(() => thunk()), emptyDependencies)

  /** Run before browser updates the screen. Kinda a "sync" effect. */
  def useLayoutEffect(
    didUpdate: EffectArg,
    dependencies: js.UndefOr[Dependencies]
  ) =
    ReactJS.useLayoutEffect(didUpdate, dependencies)

  def useMemo[T](value: () => T): T =
    ReactJS.useMemo[T](value, undefinedDependencies)

  def useMemoA[T](dependencies: Dependencies)(value: js.Function0[T]): T =
    ReactJS.useMemo[T](value, dependencies)

  def useMemo[T](dependencies: AllType*)(value: js.Function0[T]): T =
    ReactJS.useMemo[T](
      value,
      if dependencies.length == 0 then undefinedDependencies
      else dependencies.toJSArray
    )

  def useMemoMounting[T](value: () => T): T = ReactJS.useMemo[T](value, emptyDependencies)

  def useDebugValue[T](value: T): Unit =
    ReactJS.useDebugValue[T](value, js.undefined)

  def useDebugValue[T](
    value: T,
    format: T => String
  ): Unit =
    ReactJS.useDebugValue[T](value, js.Any.fromFunction1[T, String](format))

  // 
  // The useCallback functions have a few varieties. Only callbacks up to arity 5
  // are included here so if you need higher arity, write your own.
  //

  def useCallbackMounting[T](callback: js.Function0[T]): js.Function0[T] =
    ReactJS.useCallback(callback, emptyDependencies).asInstanceOf[js.Function0[T]]
  def useCallbackA[T](dependencies: Dependencies | Unit)(callback: js.Function0[T]): js.Function0[T] =
    ReactJS.useCallback(callback, dependencies).asInstanceOf[js.Function0[T]]
  def useCallback[T](dependencies: AllType*)(callback: js.Function0[T]): js.Function0[T] =
    if dependencies.length == 0 then ReactJS.useCallback(callback).asInstanceOf[js.Function0[T]]
    else ReactJS.useCallback(callback, dependencies.toJSArray).asInstanceOf[js.Function0[T]]

  def useCallbackMounting1[A1, T](callback: js.Function1[A1, T]): js.Function1[A1, T] =
    ReactJS.useCallback(callback, emptyDependencies).asInstanceOf[js.Function1[A1, T]]
  def useCallback1A[A1, T](dependencies: Dependencies)(callback: js.Function1[A1, T]): js.Function1[A1, T] =
    ReactJS.useCallback(callback, dependencies).asInstanceOf[js.Function1[A1, T]]
  def useCallback1[A1, T](dependencies: AllType*)(callback: js.Function1[A1, T]): js.Function1[A1, T] =
    ReactJS
      .useCallback(
        callback,
        if dependencies.length == 0 then undefinedDependencies
        else dependencies.toJSArray)
      .asInstanceOf[js.Function1[A1, T]]

  def useCallbackMounting2[A1, A2, T](callback: js.Function2[A1, A2, T]): js.Function2[A1, A2, T] =
    ReactJS
      .useCallback(js.Any.fromFunction2[A1, A2, T](callback), emptyDependencies)
      .asInstanceOf[js.Function2[A1, A2, T]]
  def useCallback2[A1, A2, T](dependencies: AllType*)(callback: js.Function2[A1, A2, T]): js.Function2[A1, A2, T] =
    ReactJS
      .useCallback(
        callback,
        if dependencies.length == 0 then undefinedDependencies
        else dependencies.toJSArray)
      .asInstanceOf[js.Function2[A1, A2, T]]
  def useCallback2A[A1, A2, T](dependencies: Dependencies)(callback: js.Function2[A1, A2, T]): js.Function2[A1, A2, T] =
    ReactJS.useCallback(callback, dependencies).asInstanceOf[js.Function2[A1, A2, T]]

  def useCallbackMounting3[A1, A2, A3, T](callback: js.Function3[A1, A2, A3, T]): js.Function3[A1, A2, A3, T] =
    ReactJS
      .useCallback(js.Any.fromFunction3[A1, A2, A3, T](callback), emptyDependencies)
      .asInstanceOf[js.Function3[A1, A2, A3, T]]
  def useCallback3A[A1, A2, A3, T](
    dependencies: Dependencies
  )(
    callback: (A1, A2, A3) => T
  ): js.Function3[A1, A2, A3, T] =
    ReactJS
      .useCallback(js.Any.fromFunction3[A1, A2, A3, T](callback), dependencies)
      .asInstanceOf[js.Function3[A1, A2, A3, T]]
  def useCallback3[A1, A2, A3, T](dependencies: AllType*)(callback: (A1, A2, A3) => T): js.Function3[A1, A2, A3, T] =
    ReactJS
      .useCallback(
        js.Any.fromFunction3[A1, A2, A3, T](callback),
        if dependencies.length == 0 then undefinedDependencies
        else dependencies.toJSArray
      )
      .asInstanceOf[js.Function3[A1, A2, A3, T]]

  def useCallbackMounting4[A1, A2, A3, A4, T](
    callback: js.Function4[A1, A2, A3, A4, T]): js.Function4[A1, A2, A3, A4, T] =
    ReactJS
      .useCallback(js.Any.fromFunction4[A1, A2, A3, A4, T](callback), emptyDependencies)
      .asInstanceOf[js.Function4[A1, A2, A3, A4, T]]

  def useCallback4A[A1, A2, A3, A4, T](
    dependencies: Dependencies
  )(
    callback: (A1, A2, A3, A4) => T
  ): js.Function4[A1, A2, A3, A4, T] =
    ReactJS
      .useCallback(js.Any.fromFunction4[A1, A2, A3, A4, T](callback), dependencies)
      .asInstanceOf[js.Function4[A1, A2, A3, A4, T]]

  def useCallback4[A1, A2, A3, A4, T](
    dependencies: AllType*
  )(
    callback: (A1, A2, A3, A4) => T
  ): js.Function4[A1, A2, A3, A4, T] =
    ReactJS
      .useCallback(
        js.Any.fromFunction4[A1, A2, A3, A4, T](callback),
        if dependencies.length == 0 then undefinedDependencies
        else dependencies.toJSArray
      )
      .asInstanceOf[js.Function4[A1, A2, A3, A4, T]]

  def useCallbackMounting5[A1, A2, A3, A4, A5, T](
    callback: js.Function5[A1, A2, A3, A4, A5, T]
  ): js.Function5[A1, A2, A3, A4, A5, T] =
    ReactJS
      .useCallback(js.Any.fromFunction5[A1, A2, A3, A4, A5, T](callback), emptyDependencies)
      .asInstanceOf[js.Function5[A1, A2, A3, A4, A5, T]]

  def useCallback5[A1, A2, A3, A4, A5, T](
    dependencies: Dependencies
  )(
    callback: (A1, A2, A3, A4, A5) => T
  ): js.Function5[A1, A2, A3, A4, A5, T] =
    ReactJS
      .useCallback(js.Any.fromFunction5[A1, A2, A3, A4, A5, T](callback), dependencies)
      .asInstanceOf[js.Function5[A1, A2, A3, A4, A5, T]]

  def useCallback5[A1, A2, A3, A4, A5, T](
    dependencies: AllType*
  )(
    callback: (A1, A2, A3, A4, A5) => T
  ): js.Function5[A1, A2, A3, A4, A5, T] =
    ReactJS
      .useCallback(
        js.Any.fromFunction5[A1, A2, A3, A4, A5, T](callback),
        if dependencies.length == 0 then undefinedDependencies
        else dependencies.toJSArray
      )
      .asInstanceOf[js.Function5[A1, A2, A3, A4, A5, T]]

  /** Use this version when you have the initial value when first called. 
   * If you are using scala constructs for representing "unknown", use this
   * hook e.g. `Option[String]`. You typically use this variant when
   * you want to have "local vars" in your function component.
  */
  def useRef[T](initialValue: T): MutableRef[T] = ReactJS.useRef[T](initialValue).asInstanceOf[MutableRef[T]]

  /** `useRef` variant when the initial value and subsequent values may be null. 
   * You typically use this when you want to pass it along to a child component
   * to obtain a link to a DOM element via the `ref` attribute. Since a `ReactRef` is returned, 
   * it cannot be modified in its defining function component.
  */
  def useRefWithNull[T](initialValue: T|Null = null): ReactRef[T] = 
      ReactJS.useRef[T](initialValue).asInstanceOf[ReactRef[T]]

  /** `useRef` variant when the initial value and subsequent values may be undefined. 
   * You could use this version when you want an "optional" local var in your function
   * component but prefer `useRef[T]` to this one and use a scala Option if the value
   * is used for scala based business logic.
  */
  def useRefWithUndef[T](initialValue: js.UndefOr[T] = js.undefined): MutableRef[js.UndefOr[T]] = 
      ReactJS.useRef[js.UndefOr[T]](initialValue).asInstanceOf[MutableRef[js.UndefOr[T]]]

  /** Expose imperative functions in R to refs.
   * @tparam T Ref type
   * @tparam R js object whose properties are functions. This is not enforced in
   * the type.
   */
  def useImperativeHandle[T, R <: js.Object](
    ref: MutableRef[T],
    thunk: () => R,
    dependencies: js.UndefOr[Dependencies]): Unit =
    ReactJS.useImperativeHandle[T, R](ref, thunk, dependencies)

  /** Lazy load a module. */
  def `lazy`(lazyComponent: js.Function0[js.Promise[DynamicImport]]): ReactJSComponent =
    ReactJS.`lazy`(lazyComponent:js.Any).asInstanceOf[ReactJSComponent]

  /** Lazy loaded component with zero object args, less common so the 0 suffix is here. */
  /*def `lazy0`(lazyComponent: js.Function0[js.Promise[ReactNode]]): ReactJSLazyComponent =
    ReactJS.`lazy`(lazyComponent:js.Any).asInstanceOf[ReactJSLazyComponent]
  */  
  def useTransition(config: TransitionConfig): (js.Function1[js.Function0[Unit], Unit], Boolean) =
    ReactJS.useTransition(config)

  def useTransition(t: Int): (js.Function1[js.Function0[Unit], Unit], Boolean) =
    ReactJS.useTransition(new TransitionConfig { timeoutMs = t })

  /** Use a deferred value. Config indicates how long the value is good for. */
  def useDeferredValue[T](value: T, config: DeferredValueConfig): T =
    ReactJS.useDeferredValue(value.asInstanceOf[js.Any], config).asInstanceOf[T]

  def useDeferredValue[T](value: T, t: Int): T =
    ReactJS.useDeferredValue(value.asInstanceOf[js.Any], new DeferredValueConfig { timeoutMs = t }).asInstanceOf[T]

  def createMutableSource[S](source: scala.Any, getVersion: js.Function0[scala.Any]) =
    ReactJS.createMutableSource[S](source, getVersion)

  /** subscribe 2nd arg is to be called whenever the value changes and is stable. Last is an unsubscribe for unmounting. */
  def useMutableSource[S, A](
    source: MutableSource[S],
    getSnapshot: js.Function1[S, A],
    subscribe: js.Function2[S, js.Function0[Unit], js.Function0[Unit]]): A =
    ReactJS.useMutableSource[S, A](source, getSnapshot, subscribe)

  //val Children = ReactJS.Children

  /** Create a React.fragment element. */
  object Fragment {
    def apply(key: String)(children: ReactNode*) =
      createElement(ReactJS.Fragment, js.Dynamic.literal("key" -> key), children*)

    /** Preferred creation function. */
    def apply(children: ReactNode*) =
      createElement(ReactJS.Fragment, null, children*)
  }

  /** Strict element. Wraps your root component typically. */
  object StrictMode {

    /** Wrap some children with a Strict component. */
    def apply(children: ReactNode*): ReactNode =
      ReactJS.createElement(ReactJS.StrictMode, null, children*)
  }

  /** Catch a thrown js.Promise from the child. Show fallback until the promise is
   * resolved.
   */
  object Suspense {
    def apply(fallback: => ReactNode /* | Null = null*/ )(children: ReactNode*) =
      ReactJS.createElement(ReactJS.Suspense, lit("fallback" -> fallback.asInstanceOf[js.Any]), children*)
  }
  
  opaque type RevealOrder = String
  object RevealOrder:
    val forwards: RevealOrder = "forwards"
    val backwards: RevealOrder = "backwards"
    val together: RevealOrder = "together"

  opaque type Tail = String
  object Tail:
    val hidden: RevealOrder = "hidden"
    val collapsed: RevealOrder = "collapsed"

  object SuspenseList {
    trait Props extends js.Object:
      def revealOrder: js.UndefOr[RevealOrder] = js.undefined
      def tail: js.UndefOr[Tail] = js.undefined

    def apply(items: ReactNode*) =
      ReactJS.createElement(ReactJS.SuspenseList, null, items*)

    def apply(props: Props|Null)(items: ReactNode*) =
      ReactJS.createElement(ReactJS.SuspenseList, props, items*)      
  }

  val version = ReactJS.version
end React


/**
 * React's context object contains the consumer and provider "components".
 */
@js.native
trait ReactContext[T] extends js.Object {

  /** Convenience = type parameter. */
  type ValueType = T

  /** Mutable var per https://reactjs.org/docs/context.html#reactcreatecontext */
  //var displayName: String = js.native

  /** Props take an object with a single property, `value`, with the context value. */
  val Provider: ReactJSComponent = js.native

  /** Requires function as a childe. */
  val Consumer: ReactJSComponent = js.native

  /** Not public API. */
  //var currentValue: T = js.native

  /** Not public API. */
  //val defaultValue: T = js.native
}

/** Instantiate this to sync types needed for both funcs. */
case class UseMutableSource[S, A]() {

  def create(source: scala.Any, getVersion: js.Function0[scala.Any]) =
    ReactJS.createMutableSource[S](source, getVersion)

  /** subscribe 2nd arg is to be called whenever the value changes and is stable. Last is an unsubscribe for unmounting. */
  def use(
    source: MutableSource[S],
    getSnapshot: js.Function1[S, A],
    subscribe: js.Function2[S, js.Function0[Unit], js.Function0[Unit]]): A =
    ReactJS.useMutableSource[S, A](source, getSnapshot, subscribe)
}
