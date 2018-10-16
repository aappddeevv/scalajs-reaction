// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg

import scala.scalajs.js
import js.|
import js.UndefOr
import js.JSConverters._

/**
  * A scalajs. facade for facebook's react in the spirit of ReasonReact.
  */
package object react {

  /**
    * Opaque type returned from a ref callback. It's typically either js
    * component or a DOM element both of which are js.Objects. You only use this
    * type with the `React.createRef()` machinery. You can also use a ref
    * callback and skip this type and `Reacat.createRef()`.
   */
  @js.native
  trait ReactRef extends js.Object {
    val current: js.Any | Null = js.native
  }

  /**
    * Output from scalajs' `react.createElement` and is something that can be
    * rendered in reactjs. We need to restrict this a bit more when returning
    * values from the scala side API. reactjs allows this to be quite flexible
    * including strings, numbers, booleans in additon to classes, functions,
    * etc. This should probably be a js.Any as any API used in this library will
    * ensure that the right types are used and this would get rid of the ugly
    * `stringToElement` type functions below.  js.Object is technically not
    * correct.
    */
  @js.native
  trait ReactNode extends js.Object

  /** Output from createElement and something you can render reactjs wth key and
    * ref accessors. Essentially its a node but its known to have a key and ref.
    * An element, like a node, can be rendered.
    */
  @js.native
  trait ReactElement extends js.Object with ReactNode {
    val key: UndefOr[String]
    // ...add ref here...is it a string or a callback now?
  }

  /**
    * Something to be used in createElement. A standard HTML element that has
    * been created using React.createElement.  Props are optional of course.  We
    * use this tag it to show that it came from the standard DOM components vs a
    * custom one.
    */
  @js.native
  trait ReactDOMElement extends ReactElement {
    def `type`: String = js.native

    /** Raw JS props. */
    def props: UndefOr[js.Object] = js.native
  }

  /**
    * Something to be used in createElement. A js-object that is returned from
    * create-react-class. Represents class component that can be passed to the
    * javascript React.createElement function.
    */
  @js.native
  trait ReactClass extends js.Object

  /** Something that can be used in createElement. Need to add js.Function that
    * returns a ReactNode.
    */
  type ReactType = ReactClass | String

  /**
    * Something to be used in some jsinterop functions where the functions are
    * the equivalent of createElement. This type is used only to imported
    * javascript side components. Typically this is used to annotate a component
    * type imported from javascript or created via other js-interop mechanisms
    * such as redux integration. By using a separate type, you must use then
    * scalajs-react's API to create an element. Since `createElement` is used to
    * create elements in scalajs-react and createElement can take both a
    * function as well as an js object, you can use `ReactJsComponent` to
    * annotate scalajs imports that are a function (such as a stateless function
    * component) or a regular class specfication.
    */
  @js.native
  trait ReactJsComponent extends js.Object

  /** Alias for internal use. @deprecated */
  type ReactClassInternal = ReactClass

  /** Return a "non render" element. */
  val nullElement = null.asInstanceOf[ReactNode]

  /** Convenience. Use implicits for automatic conversion. */
  @inline def arrayToElement[T <: ReactNode](arr: js.Array[T]) = arr.asInstanceOf[ReactNode]

  /** Convenience. Use implicits for automatic conversion. */
  @inline def arrayToElement[T <: ReactNode](s: Seq[T]) = (s.toJSArray).asInstanceOf[ReactNode]

  /** Convenience. Use implicits for automatic conversion. */
  @inline def stringToElement(s: String): ReactNode = s.asInstanceOf[ReactNode]

  /**
    * Empty children value but non-empty array. Be careful as someone could
    * mutate this global instance on you.
    */
  val emptyChildrenVal = js.Array[ReactNode]()

  /** Allocate an empty childe array. It's not shared like `emptyChildrenVal` */
  def emptyChildren = js.Array[ReactNode]()

  /**
    * Hidden field for scala components that are based directly on a js component.
    * Args should be an optional key and ref callback.
    */
  type JsElementWrapped = (Option[String], Option[RefCb]) => ReactElement

  /** Alias for ComponentSpec. */
  type Component = ComponentSpec

  /** Callback for react ref. Pure string refs are not supported. */
  type RefCb = js.Function1[ReactElement, Unit]

  type RefCbE[E] = js.Function1[E, Unit]

  /** noop ref callback */
  val noopRefCb = () => ()

  /** Children type. */
  type PropsChildren = js.Array[ReactElement]

  /** No children value. */
  val noChildren: PropsChildren = js.Array()

  /** A function (effect) called (a callback) when a component unmounts. */
  type OnUnmount = () => Unit

  /**
    * A subscription called after mount and then its return value called after
    * unmount.
    */
  type Subscription = () => OnUnmount

  /**
    * Constant val of a single, empty js.Object. Use carefully since js mutates and
    * this will be a shared instance. Prefer `noProps()`.
    */
  val noPropsVal: js.Object = new js.Object()

  /** Type inferring empty js object. A new object is created with each call. */
  @inline def noProps[T <: js.Object](): T = (new js.Object()).asInstanceOf[T]

  /**
    * Turn a ReactRef to a js.Dynamic so you can call "component" methods
    * directly.  Most notably "select", "value" or "focus()".
    */
  @inline def refToJs[T <: ReactRef](ref: T): js.Dynamic = ref.asInstanceOf[js.Dynamic]

  /**
    * Merge js.Dynamic. See [[merge]] and use that.
    * https://stackoverflow.com/questions/36561209/is-it-possible-to-combine-two-js-dynamic-objects
    */
  @inline def mergeJSObjects(objs: js.Dynamic*): js.Dynamic = {
    // not js.Any? maybe keep js or scala values in here....
    val result = js.Dictionary.empty[Any]
    for (source <- objs) {
      for ((key, value) <- source.asInstanceOf[js.Dictionary[Any]])
        result(key) = value
    }
    result.asInstanceOf[js.Dynamic]
  }

  /**
    * Merge objects and Ts together. Good for merging props with data-
    * attributes. This is like `Object.assign`.
    */
  @inline def merge[T <: js.Object](objs: js.Dynamic | js.Object*): T = {
    val result = js.Dictionary.empty[Any]
    for (source <- objs) {
      for ((key, value) <- source.asInstanceOf[js.Dictionary[Any]])
        result(key) = value
    }
    result.asInstanceOf[T]
  }

  @inline private[ttg] def mergeComponents[C](objs: (Component | js.Dynamic | js.Object)*): C = {
    mergeJSObjects(objs.asInstanceOf[Seq[js.Dynamic]]: _*).asInstanceOf[C]
  }

  /** Return None if undefined or null -> None, otherwise return a Some. */
  @inline def toSafeOption[T <: js.Any](t: js.Any): Option[T] = {
    if (js.isUndefined(t) || t == null) None
    else Option(t.asInstanceOf[T])
  }
}
