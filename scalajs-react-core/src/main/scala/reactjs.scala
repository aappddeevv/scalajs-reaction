// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js
import js.annotation._
import js.|
import js._
import js.Dynamic.{literal => lit}

import org.scalajs.dom

@js.native
private[react] trait ReactJS extends js.Object {

  /** Can take a wide variety of types for tpe: string | sfc | class (extending React.Component) */
  def createElement[P](
      el: js.Any | ReactType, // any means when using this raw interop, anything goes!
      props: UndefOr[P],
      children: ReactNode*): ReactDOMElement = js.native

  def cloneElement(el: ReactElement, props: js.Dynamic): ReactDOMElement = js.native

  /** Symbol or number depending on browser/environment support for symbols */
  val Fragment: js.Any = js.native

  /** v16.3 API. */
  def createContext[T](
      defaultValue: T,
      calculateChangedBits: js.UndefOr[js.Function2[T, T, Int]]): ReactContext[T] = js.native

  /** Experimental. */
  val StrictMode: ReactClass = js.native

  /** Create a ref to be assigned to a "ref" property on a component. */
  def createRef(): react.ReactRef  = js.native

  val Suspense: ReactClass = js.native

  /** forwardRef */
  // def forwardRef(): js.Any = js.native

  val version: String = js.native
}

@js.native
trait Hooks extends js.Object {
  /*
  React.useCallback = useCallback;
   *   React.useContext = useContext;
   *   React.useEffect = useEffect;
  React.useImperativeMethods = useImperativeMethods;
  React.useLayoutEffect = useLayoutEffect;
  React.useMemo = useMemo;
  React.useMutationEffect = useMutationEffect;
  React.useReducer = useReducer;
  React.useRef = useRef;
   *   React.useState = useState;
   */
  def useState[T](initialValue: T): js.Tuple2[T, js.Function1[T, Unit]] = js.native
  def useEffect(effect: js.Function1[js.Function0[Unit], Unit]): Unit = js.native
  def useContext[T](context: ReactContext[T]): T = js.native
}

/** Deprecated for ReactJS */
@js.native
@JSImport("react", JSImport.Namespace)
private[react] object JSReact extends ReactJS with Hooks

/** Public access to react API. */
@js.native
@JSImport("react", JSImport.Namespace)
object ReactJS extends ReactJS with Hooks

/**
 * Scala-side access to JSReact APIs.
 */
object React {

  /** Create an element with props and children. */
  @inline def createElement(tag: ReactType, props: js.Object)(
      children: ReactNode*): ReactDOMElement =
    ReactJS.createElement(tag, props, children: _*)

  /** Create an element without props but with children. */
  @inline def createElement(tag: ReactType)(children: ReactNode*): ReactDOMElement =
    ReactJS.createElement(tag, js.undefined, children: _*)

  /**
    * Create a react fragment. Fragments are created as an "element" with a specific
    * tag (symbol or number if target does not support symbol) vs say, the string "div".
    */
  @inline def createFragment(key: Option[String], children: ReactNode*): ReactDOMElement = {
    val props = js.Dictionary.empty[js.Any]
    key.foreach(props("key") = _)
    ReactJS.createElement(JSReact.Fragment, props, children: _*)
  }

  @inline def useState[T](default: T): (T, T => Unit) = {
    val c = JSReact.useState(default)
    (c._1, c._2)
  }
}

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
