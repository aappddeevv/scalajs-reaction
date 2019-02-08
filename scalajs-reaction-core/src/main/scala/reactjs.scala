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
}

@js.native
private[react] trait HooksPrivate extends js.Object {
  def useState[T](initialValue: T | js.Function0[T]): js.Tuple2[T, js.Function1[T, Unit]] = js.native

  // dependencies can be the empty array
  def useEffect(didUpdate: js.Function1[js.Function0[Unit], Unit],
    dependencies: js.UndefOr[js.Array[js.Any]] = js.undefined): Unit = js.native

  // sync after render and DOM mutations are completed
  def useLayoutEffect(didUpdate: js.Function1[js.Function0[Unit], Unit],
    dependencies: js.UndefOr[js.Array[js.Any]] = js.undefined): Unit = js.native  
}

@js.native
private [react] trait Hooks extends HooksPrivate {
  /*
  React.useImperativeHandle
   */
  def useLayoutEffect[T](): Unit = js.native

  def useContext[T](context: ReactContext[T]): T = js.native

  def useDebugValue[T](value: T, format: js.UndefOr[js.Function1[T, String]] = js.undefined): Unit = js.native

  def useCallback[T <: js.Any](callback: js.Function0[T],
    dependencies: js.UndefOr[js.Array[js.Any]] = js.undefined): js.Function0[T] = js.native

  def useMemo[T <: js.Any](value: js.Function0[T], dependencies: js.UndefOr[js.Array[js.Any]] = js.undefined): T = js.native

  def useRef[T <: js.Any](value: js.UndefOr[T | Null] = js.undefined): ReactRef[T] = js.native

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
}

/** Public access to react API. */
@js.native
@JSImport("react", JSImport.Namespace)
object ReactJS extends ReactJS with Hooks

/**
 * Scala-side access to JSReact APIs.
 */
trait React {

  /** Create a DOM element via its string name. */
  @inline def createDOMElement(tag: String, props: js.Object)(
    children: ReactNode*): ReactDOMElement = 
    ReactJS.createElement(tag, props, children:_*).asInstanceOf[ReactDOMElement]

  /** Create an element with props and children. */
  @inline def createElement(tag: ReactType, props: js.Object)(
      children: ReactNode*): ReactElement =
    ReactJS.createElement(tag, props, children: _*)

  /** Create an element without props but with children. */
  @inline def createElement(tag: ReactType)(
    children: ReactNode*): ReactElement =
    ReactJS.createElement(tag, js.undefined, children: _*)

  /**
    * Create a react fragment. Fragments are created as an "element" with a specific
    * tag (symbol or number if target does not support symbol) vs say, the string "div".
    */
  @inline def createFragment(key: Option[String], children: ReactNode*): ReactElement = {
    val props = js.Dictionary.empty[js.Any]
    key.foreach(props("key") = _)
    ReactJS.createElement(ReactJS.Fragment, props, children: _*)
  }

  /** A more convenient hook model. */
  @inline def useState[T](default: T): (T, T => Unit) = {
    val c = ReactJS.useState[T](default)
    (c._1, c._2)
  }

  /** typescript has this but am not sure it works. */
  @inline def useState[T](default: () => T): (T, T => Unit) = {
    val c = ReactJS.useState[T](js.Any.fromFunction0[T](default))
    (c._1, c._2)
  }

  @inline def useReducer[S, A](reducer: (S,A)=>S, initialState: S): (S, A => Unit) = {
    val c = ReactJS.useReducer(reducer, initialState)
    (c._1, c._2)
  }

  @inline def useReducer[S, A, I](reducer: (S,A)=>S, initialArg: I, init: I => S): (S, A => Unit) = {
    val c = ReactJS.useReducer(reducer, initialArg, init)
    (c._1, c._2)
  }

  /** Improve type inference by providing the scala function version. */
  @inline def useEffect(didUpdate: () => (() => Unit), dependencies: Option[js.Array[js.Any]] = None): Unit =
    ReactJS.useEffect(
      { _ => js.Any.fromFunction0[Unit](didUpdate()) }: js.Function1[js.Function0[Unit],Unit],
      dependencies.orUndefined
    )

  /** Improve type inference by providing the scala function version. */
  @inline def useLayoutEffect(didUpdate: () => (() => Unit), dependencies: Option[js.Array[js.Any]] = None): Unit =
    ReactJS.useLayoutEffect(
      { _ => js.Any.fromFunction0[Unit](didUpdate()) }: js.Function1[js.Function0[Unit],Unit],
      dependencies.orUndefined
    )

  @inline def useMemo[T <: js.Any](value: () => T, dependencies: Option[js.Array[js.Any]] = None): T =
    ReactJS.useMemo[T](value, dependencies.orUndefined)

  @inline def useDebugValue[T](value: T, format: Option[T => String] = None): Unit =
    ReactJS.useDebugValue[T](value, format.map(js.Any.fromFunction1[T, String](_)).orUndefined)

  @inline def useCallback[T <: js.Any](callback: () => T, dependencies: Option[js.Array[js.Any]] = None): () => T =
    ReactJS.useCallback[T](callback, dependencies.orUndefined)

  @inline def useCallback[T](context: ReactContext[T]): T = ReactJS.useContext[T](context)

}

object React extends React

/**
  * We use create-react-class under the hood to create all classes
  */
@js.native
@JSImport("create-react-class", JSImport.Default)
private[react] object reactCreateClass extends js.Object {
  def apply(props: js.Object): ReactClass = js.native
}

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
