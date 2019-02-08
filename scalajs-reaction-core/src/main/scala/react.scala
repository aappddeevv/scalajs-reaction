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
    * component or a DOM element, both of which are js.Objects. You only use
    * this type with the `React.createRef()` machinery. You can also use a ref
    * callback (still supported) and skip this type and
    * `Reacat.createRef()`.
   * 
   * @todo Validate a null value is present if the ref is never set. typescript
   * says so.
   */
  @js.native
  trait ReactRef[T] extends js.Object {
    /** See react.syntax for syntax support on handling E|Null
     * e.g. myref.current.toNonNullOption.
     */
    val current: T | Null = js.native
  }

  ///** Callback for react ref "callback version". */
  //type RefCb = RefCbE[dom.html.Element]

  /** Callback for react ref with settable E. */
  type RefCb[E] = js.Function1[E|Null, Unit]

  /** Combine the callback and the createRef models. */
  type Ref[E] = RefCb[E] | ReactRef[E]

  /**
    * Something that can be rendered in reactjs. We need to restrict this a bit
    * more when returning values from the scala side API. reactjs allows this to
    * be quite flexible including strings, numbers, booleans in additon to
    * classes, functions, etc. This should probably be a js.Any as any API used
    * in this library will ensure that the right types are used and this would
    * get rid of the ugly `stringToElement` type functions below.  js.Object is
    * technically not correct.
    */
  @js.native
  trait ReactNode extends js.Object

  /** Output from `react.createElement` and is something you can render with key
    * and ref accessors. A ReactNode with a key. Keep the trait parameterless at
    * the expense of pushing the type parameter to `ref`.
    */
  @js.native
  trait ReactElement extends ReactNode {
    val key: UndefOr[String] = js.native
    def ref[E]: UndefOr[RefCb[E]] = js.native
    // ...add ref here...is it a string or a callback now?

    /** Pretty sure this is always here, don't use it. */
    val `type`: String = js.native
  }

  type KeyType = String | Int

  /** Use these to mix into your traits to ensure you have a a key and ref to
   * set. For example, add this to a Props class so that you can specify a key
   * when you create it.  These are not special or used for tags, you can not
   * use this trait and just define the key and/or ref in your Props trait
   * directly.
   */
  trait ReactPropsJs extends js.Object {
    var key: UndefOr[KeyType] = js.undefined
    def ref[E]: UndefOr[RefCb[E]] = js.undefined
  }

  /** Scala side verson of ReactPropsJs. It assumed that you use key and/or ref to
   * create your actual react or react dom components. Hence the use of Option.
   * It usually better to define them as UndefOr instead of Option so you can
   * directly place them into a DOM element's props in your render function.
   */
  trait ReactProps {
    val key: Option[String] = None
    def ref[E]: Option[RefCb[E]] = None
  }

  /**
    * A standard HTML element that has been created using React.createElement.
    * Props are optional of course.  We use this tag it to show that it came
    * from the standard DOM components vs a custom one.
    */
  @js.native
  trait ReactDOMElement extends ReactElement {
    //def `type`: String = js.native

    /** Raw JS props. You don't normally access this. Don't use. */
    def props: UndefOr[js.Object] = js.native
  }

  /**
    * A js-object that is returned from create-react-class. In reactjs a react
   * class is a js object created using the "class" construct.
    */
  @js.native
  trait ReactClass extends js.Object

  /** For creating a portal in react-dom. */
  @js.native
  trait ReactPortal extends ReactElement

  /** Something that can be used in ReactJS.createElement. Need to add js.Function
    * that returns a ReactNode.
    */
  type ReactType = ReactClass | String | ReactJsComponent

  /**
    * This type is used only for imported javascript authored components to
    * "tag" a component type or created via other js-interop mechanisms such as
    * redux integration. By using a separate type, you must use then
    * scalajs-react's API to create an element. You can use this to annotate a
    * js function react component as well e.g. () => ReactNode. If you import a
    * component from javascript, you must call `wrapJsForScala` to create an
    * element in scala.
    */
  @js.native
  trait ReactJsComponent extends js.Object

  /** Alias for internal use. @deprecated */
  type ReactClassInternal = ReactClass

  /** Return a "render nothing" element. React ignores nulls during rendering. */
  val nullElement = null.asInstanceOf[ReactElement]

  /** Convenience. Use implicits for syntax-based conversion. */
  @inline def arrayToElement[T <: ReactNode](arr: js.Array[T]) = arr.asInstanceOf[ReactNode]

  /** Convenience. Use implicits for automatic conversion. */
  @inline def arrayToElement[T <: ReactNode](s: Seq[T]) = (s.toJSArray).asInstanceOf[ReactNode]

  /** String to react node. Use implicits for automatic conversion and avoid
   * calling this function.
   */
  @inline def stringToElement(s: String): ReactNode = s.asInstanceOf[ReactNode]

  /**
    * Empty children value but non-empty array. Be careful as someone could
    * mutate this global instance on you.
    */
  val emptyChildrenVal = js.Array[ReactNode]()

  /** Allocate an empty child array. It's not shared like `emptyChildrenVal` */
  def emptyChildren = js.Array[ReactNode]()

  /**
    * Hidden field for scala components that are based directly on a js
    * component.  Args should be an optional key and ref. The scala.js interop
    * layer creates the function that is attached to the scala side, js proxy.
    */
  type JsElementWrapped =
    (Option[String], Option[Ref[js.Any]]) => ReactElement

  /** Alias for ComponentSpec. */
  type Component = ComponentSpec

  /** Make a callback. You don't need this but helps with type inference. There is
    * some dedicated syntax support for `E|Null` handling.
*/
  def refCallback[E](f: E|Null => Unit): RefCb[E] = f

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
    * Constant val of a single, empty js.Object. Use carefully since js allows
    * mutation and this will be a shared instance. Prefer `noProps()`.
    */
  val noPropsVal: js.Object = new js.Object()

  /** Type inferring empty js object. A new object is created with each call. */
  @inline def noProps[T <: js.Object](): T = (new js.Object()).asInstanceOf[T]

  /**
    * Turn a ReactRef to a js.Dynamic so you can call "component" methods
    * directly.  Most notably "select", "value" or "focus()". I don't think this
    * is right...
    */
  @inline def refToJs[T](ref: ReactRef[T]): js.Dynamic = ref.asInstanceOf[js.Dynamic]

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
    * attributes. This is like `Object.assign`. Last parameter wins.
   * Creates a new object.
    */
  @inline def merge[T <: js.Object](objs: js.Dynamic | js.Object | Null *): T = {
    val result = js.Dictionary.empty[Any]
    for (source <- objs) {
      if(source != null)
        for ((key, value) <- source.asInstanceOf[js.Dictionary[Any]])
          result(key) = value
    }
    result.asInstanceOf[T]
  }

  /** Shallow copy. Should we call Object.assign directly? Faster? */
  @inline def copy[T <: js.Object](obj: js.Dynamic | js.Object): T = merge[T](obj)

  /** Not sure this is useful. */
  @inline private[ttg] def mergeComponents[C](objs: (Component | js.Dynamic | js.Object)*): C = {
    mergeJSObjects(objs.asInstanceOf[Seq[js.Dynamic]]: _*).asInstanceOf[C]
  }

  /** Add a key. Mutates input object directly because hey, this is javascript! */
  @inline def withKey[T <: js.Object](element: T, key: String): ReactElement = {
    element.asInstanceOf[js.Dynamic].key = key
    return element.asInstanceOf[ReactElement]
  }

  /** Return None if undefined or null -> None, otherwise return a Some.  Syntax
   * support makes this easier so you don't have to use this function.
   */
  @inline def toSafeOption[T <: js.Any](t: js.Any): Option[T] = {
    if (js.isUndefined(t) || t == null) None
    else Option(t.asInstanceOf[T])
  }
}
