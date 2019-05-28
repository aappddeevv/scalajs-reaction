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
private[react] trait ReactJS extends js.Object {

  /** We are not importing this to be used in a class oriented way. */
  val Component: js.Dynamic = js.native

  /** Can take a wide variety of types for tpe: string | sfc | class (extending React.Component) */
  def createElement[P](
      el: js.Any | ReactType, // any means when using this raw interop, anything goes!
      props: UndefOr[P],
      children: ReactNode*): ReactElement = js.native

  def cloneElement(el: ReactElement, props: js.Dynamic): ReactDOMElement = js.native

  val Fragment: js.Any = js.native

  def createContext[T](
      defaultValue: T,
      calculateChangedBits: js.UndefOr[js.Function2[T, T, Int]]): ReactContext[T] = js.native

  val StrictMode: ReactJsComponent = js.native
  val Suspense: ReactJsComponent = js.native

  /** Create a ref to be assigned to a "ref" property on a component. */
  def createRef[T](): react.ReactRef[T]  = js.native

  val version: String = js.native

  /** Returns a "lazy" react component i.e. $$type = react.lazy */
  def `lazy`(lazyComponent: DynamicImportThunk): ReactJsLazyComponent = js.native
}

/** Opquae type.*/
@js.native
trait DynamicImport extends js.Object {
  // If default is defined in the exports
  // other exports will be here as well
  val `default`: ReactJsComponent
}

@js.native
private [react] trait Hooks extends js.Object {

  def useImperativeHandle[T, R <: js.Object](ref: Ref[T], thunk: js.Function0[R]): Unit = js.native

  def useContext[T](context: ReactContext[T]): T = js.native

  def useDebugValue[T](value: T, format: js.UndefOr[js.Function1[T, String]] = js.undefined): Unit = js.native

  def useCallback[T <: js.Any](callback: js.Function0[T],
    dependencies: js.UndefOr[js.Array[js.Any]] = js.undefined): js.Function0[T] = js.native

  def useMemo[T <: js.Any](value: js.Function0[T], dependencies: js.UndefOr[js.Array[js.Any]] = js.undefined): T = js.native

  def useRef[T <: js.Any](value: js.UndefOr[T | Null] = js.undefined): Ref[T] = js.native

  // Blah..uses Object.is for state comparisons!
  def useReducer[S, A](
    reducer: js.Function2[S, A, S],
    initialState: S
  ): js.Tuple2[S, js.Function1[A, Unit]] = js.native

  def useReducer[S, A, I](
    reducer: js.Function2[S, A, S],
    initialArg: I,
    init: js.Function1[I,S]
  ): js.Tuple2[S, js.Function1[A, Unit]] = js.native

  // js.Tuple2 is a shortcut to a 2 element array
  def useState[T](initialValue: T | js.Function0[T]): js.Tuple2[T, js.Any] = js.native

  // dependencies can be the empty array
  def useEffect(didUpdate: js.Function0[js.Function0[Unit]],
    dependencies: js.UndefOr[js.Array[js.Any]] = js.undefined): Unit = js.native

  // sync after render and DOM mutations are completed
  def useLayoutEffect(didUpdate: js.Function0[js.Function0[Unit]],
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
 */
trait React {

  /** Create a DOM element via its string name. */
  def createDOMElement(tag: String, props: js.Object)(
    children: ReactNode*): ReactDOMElement = 
    ReactJS.createElement(tag, props, children:_*).asInstanceOf[ReactDOMElement]

  /** Create an element with props and children. */
  def createElement(tag: ReactType, props: js.Object)(
      children: ReactNode*): ReactElement =
    ReactJS.createElement(tag, props, children: _*)

  /** Create an element without props but with children. */
  def createElement(tag: ReactType)(
    children: ReactNode*): ReactElement =
    ReactJS.createElement(tag, js.undefined, children: _*)

  /**
    * Create a react fragment. Fragments are created as an "element" with a specific
    * tag (symbol or number if target does not support symbol) vs say, the string "div".
    */
  def createFragment(key: Option[String], children: ReactNode*): ReactElement = {
    val props = js.Dictionary.empty[js.Any]
    key.foreach(props("key") = _)
    ReactJS.createElement(ReactJS.Fragment, props, children: _*)
  }

  /** A more convenient hook model. 2nd arg will be a simple updater T => Unit. */
  def useState[T](default: T): (T, T => Unit) = {
    val c = ReactJS.useState[T](default)
    (c._1, t => c._2.asInstanceOf[js.Function1[T, Unit]](t))
  }

  /** typescript has this but am not sure it works. */
  def useState[T](default: () => T): (T, T => Unit) = {
    val c = ReactJS.useState[T](js.Any.fromFunction0[T](default))
    (c._1, t => c._2.asInstanceOf[js.Function1[T, Unit]](t))
  }

  /** 2nd arg will be an updater function T => T. */
  def useStateUpdater[T](default: T): (T, (T => T) => Unit) = {
    val c = ReactJS.useState[T](default)
    (c._1, (updater_thunk => c._2.asInstanceOf[js.Function1[js.Function1[T,T], Unit]](updater_thunk)))
  }

  def useReducer[S, A](reducer: (S,A)=>S, initialState: S): (S, A => Unit) = {
    val c = ReactJS.useReducer(reducer, initialState)
    (c._1, c._2)
  }

  def useReducer[S, A, I](reducer: (S,A)=>S, initialArg: I, init: I => S): (S, A => Unit) = {
    val c = ReactJS.useReducer(reducer, initialArg, init)
    (c._1, c._2)
  }

  /** Improve type inference by providing the scala function version.  Effect is
   * run on every render which is probably too much.
   */
  def useEffectAlways(didUpdate: () => (() => Unit)): Unit =
    ReactJS.useEffect(
      { () => js.Any.fromFunction0[Unit](didUpdate()) }: js.Function0[js.Function0[Unit]],
      js.undefined
    )

  /** Effect is run when dependencies change. */
  def useEffect(didUpdate: () => (() => Unit), dependencies: js.Array[js.Any]): Unit =
    ReactJS.useEffect(
      { () => js.Any.fromFunction0[Unit](didUpdate()) }: js.Function0[js.Function0[Unit]],
      dependencies
    )

  /** Effect is run at mount/unmounut time with `[]` as the 2nd argument to the basic useEffect function. */
  def useEffectMounting(didUpdate: () => (() => Unit)): Unit =
    ReactJS.useEffect(
      { () => js.Any.fromFunction0[Unit](didUpdate()) }: js.Function0[js.Function0[Unit]],
      js.Array[js.Any]())

  /** Improve type inference by providing the scala function version. */
  def useLayoutEffect(didUpdate: () => (() => Unit), dependencies: js.UndefOr[js.Array[js.Any]]): Unit =
    ReactJS.useLayoutEffect(
      { () => js.Any.fromFunction0[Unit](didUpdate()) }: js.Function0[js.Function0[Unit]],
      dependencies
    )

  /** Use dependencies(yourScalaIterable)` to crate the watch array of values from
   * a scala value.
   */
  def useMemo[T <: js.Any](value: () => T, dependencies: js.UndefOr[js.Array[js.Any]] = js.undefined): T =
    ReactJS.useMemo[T](value, dependencies)

  def useDebugValue[T](value: T): Unit =
    ReactJS.useDebugValue[T](value, js.undefined)

  def useDebugValue[T](value: T, format: T => String): Unit =
    ReactJS.useDebugValue[T](value, js.Any.fromFunction1[T, String](format))

  /** Use dependencies(yourScalaIterable)` to crate the watch array of values from
   * a scala value.
   */
  def useCallback[T <: js.Any](callback: () => T, dependencies: js.Array[js.Any]): () => T =
    ReactJS.useCallback[T](callback, dependencies)

  /** ??? I'm not sure this is valid at all... */
  def useCallback[T](context: ReactContext[T]): T = ReactJS.useContext[T](context)

  def `lazy`(lazyComponent: DynamicImportThunk): ReactJsLazyComponent =
    ReactJS.`lazy`(lazyComponent)

  def useRef[T <: js.Any](value: js.UndefOr[T | Null] = js.undefined): Ref[T] =
    ReactJS.useRef[T](value)

  def useImperativeHandle[T, R <: js.Object](ref: Ref[T], thunk: () => R): Unit =
    ReactJS.useImperativeHandle[T,R](ref, thunk)

  /** Create a watchlist from a scala iterable. Helps with type inference.  The
   * function takes an array of any type of scala.Any which may or may not make
   * sense for how hooks calculates changes in dependencies.
   */
  def dependencies(watchList: Iterable[scala.Any]): js.Array[js.Any] =
    watchList.toJSArray.asInstanceOf[js.Array[js.Any]]
}

object React extends React

// /**
//   * We use create-react-class under the hood to create all classes
//   */
// @js.native
// @JSImport("create-react-class", JSImport.Default)
// private[react] object reactCreateClass extends js.Object {
//   def apply(props: js.Object): ReactClass = js.native
// }

/**
  * React's context object contains the consumer and provider "components".
  */
@js.native
trait ReactContext[T] extends js.Object {
  type ValueType = T

  /** Only takes a single attribute value, "value" with the context . */
  val Provider: js.Any = js.native
  val Consumer: js.Any = js.native

  /** Not public API. */
  var currentValue: T = js.native

  /** Not public API. */
  val defaultValue: T = js.native
}
