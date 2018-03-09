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
    * Opaque type returned from a ref callback. It's really a js component.
    */
  trait ReactRef extends js.Object

  /**
    * Output from createElement and something that can be rendered in
    * reactjs. We need to restrict this a bit more when returning values from
    * the scala side API. reactjs allows this to be quity flexible including
    * strings, numbers, booleans in additon to classes, functions, etc. This
    * should probably be a js.Any as any API used in this library will ensure
    * that the right types are used and this would get rid of the ugly
    * `stringToElement` type functions below.  js.Object is technically not
    * correct.
    */
  @js.native
  trait ReactNode extends js.Object

  /** Output from createElement and something you can render reactjs wth key and ref
    * accessors. */
  @js.native
  trait ReactElement extends js.Object with ReactNode {
    val key: UndefOr[String]
    // ...add ref here...is it a string or a callback?
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
   * returns a ReadNode.
   */
  type ReactType = ReactClass | String

  /**
    * Something to be used in some jsinterop functions that are the equivalent
    * of createElement. A type used only to imported javascript side
    * components. Typically this is used to annotate a component type imported
    * from javascript or created via other js-interop mechanisms such as redux
    * integration. By using a separate type, you must use then scalajs-react's
    * API to create an element.
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

  /** A subscription called after mount and after unmount. */
  type Subscription = () => (() => Unit)

  /**
    * Constant val of a single, empty js.Object. Use carefully since js mutates and
    * this will be a shared instance. Prefer noProps.
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

  /** Merge objects and Ts together. Good for merging props with data- attributes. */
  @inline def merge[T <: js.Object](objs: T | js.Dynamic *): T = {
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

  /** Safely wrap a value which could be undefined or null -> None, otherwise Some. */
  @inline def toSafeOption[T <: js.Any](t: js.Any): Option[T] = {
    if (js.isUndefined(t)) None
    else Option(t.asInstanceOf[T])
  }
}
