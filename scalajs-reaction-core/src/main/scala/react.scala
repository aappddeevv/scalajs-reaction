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
   * callback (still supported), skip this type and the call to
   * `React.createRef()`.
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

  /** Callback for react ref with settable E. */
  type RefCb[E] = js.Function1[E|Null, Unit]

  /** Combine the callback and the createRef models. */
  type Ref[E] = RefCb[E] | ReactRef[E]

  /** Make Ref[E] from callback. See syntax for dealing with E|Null. */
  def refCB[E](f: E|Null => Unit): Ref[E] = js.Any.fromFunction1[E|Null,Unit](f)

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
   * class is a js object created using the "class" construct. This should
   * really be ReactJsComponent bt we keep this slightly different.
   */
  @js.native
  trait ReactClass extends js.Object

  /** For creating a portal in react-dom. */
  @js.native
  trait ReactPortal extends ReactElement

  /** Something that can be used in ReactJS.createElement. Need to add js.Function
   * that returns a ReactNode.
   */
  type ReactType = ReactClass | String | ReactJsComponent | ReactJsFunctionComponent | ReactJsLazyComponent

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

  /** Components from js land that are functions. */
  @js.native
  trait ReactJsFunctionComponent extends js.Object

  @js.native
  trait ReactJsLazyComponent extends ReactJsComponent

  /** All the types of components that can be imported from JS. These must be
   * processed to be allowed to be used as Components in this facade. Keep in
   * sync with `ReactType`.
   */
  type ImportedJsComponent = ReactJsComponent | ReactJsFunctionComponent | ReactJsLazyComponent

  /** The type of `() => import("somecomponent")` which is used exclusively for
   * the argument to React.lazy.
   */
  type DynamicImportThunk = js.Function0[js.Promise[DynamicImport]]

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
  def noProps[T <: js.Object](): T = (new js.Object()).asInstanceOf[T]

  /**
   * Turn a ReactRef to a js.Dynamic so you can call "component" methods
   * directly.  Most notably "select", "value" or "focus()". I don't think this
   * is right...
   */
  def refToJs[T](ref: ReactRef[T]): js.Dynamic = ref.asInstanceOf[js.Dynamic]

  /**
   * Merge js.Dynamic. See [[merge]] and use that.
   * https://stackoverflow.com/questions/36561209/is-it-possible-to-combine-two-js-dynamic-objects
   */
  def mergeJSObjects(objs: js.Dynamic*): js.Dynamic = {
    // not js.Any? maybe keep js or scala values in here....
    val result = js.Dictionary.empty[Any]
    for (source <- objs) {
      if(source != null)
        for ((key, value) <- source.asInstanceOf[js.Dictionary[Any]])
          result(key) = value
    }
    result.asInstanceOf[js.Dynamic]
  }

  /**
   * Merge objects and Ts together. Good for merging props with data-
   * attributes. This is like `Object.assign`. Last parameter wins.  Creates
   * and returns new object. Input objects are not modified.
   */
  def merge[T <: js.Object](objs: js.UndefOr[js.Object] | js.Dynamic | js.Object | Null *): T = {
    val result = js.Dictionary.empty[Any]
    for (source <- objs) {
      if(source != null && source.asInstanceOf[js.UndefOr[js.Object]].isDefined)
        for ((key, value) <- source.asInstanceOf[js.Dictionary[Any]])
          result(key) = value
    }
    result.asInstanceOf[T]
  }

  /** Shallow copy. Should we call Object.assign directly? Faster? */
  def copy[T <: js.Object](obj: js.Dynamic | js.Object): T = merge[T](obj)

  /** Not sure this is useful. */
  private[ttg] def mergeComponents[C](objs: (Component | js.Dynamic | js.Object)*): C = {
    mergeJSObjects(objs.asInstanceOf[Seq[js.Dynamic]]: _*).asInstanceOf[C]
  }

  /** Add a key. Mutates input object directly because hey, this is javascript! */
  def withKey[T <: js.Object](element: T, key: String): ReactElement = {
    element.asInstanceOf[js.Dynamic].key = key
    return element.asInstanceOf[ReactElement]
  }

  /** Return None if undefined or null -> None, otherwise return a Some.  Syntax
   * support makes this easier so you don't have to use this function.
   */
  def toSafeOption[T <: js.Any](t: js.Any): Option[T] = {
    if (js.isUndefined(t) || t == null) None
    else Option(t.asInstanceOf[T])
  }

  /** Render something or return a null element. */
  def when(cond: Boolean)(render: => ReactNode): ReactNode =
    if(cond) render else nullElement

  /** Render something if not cond or return a null element. */
  def whenNot(cond: Boolean)(render: => ReactNode): ReactNode =
    if(!cond) render else nullElement


  /** Test equaliy with two js objects. Standard == in scala.js on 2 js objects
   * (e.g. non-native traits) will not test in the same way that standard scala
   * tests equality. Do not use this with ract components (js objects) because
   * react component objects have circular data structures.
   *
   *  You may want to look at some standard js equality libraries--there are
   *  many and are opimized in various ways. See
   *  https://github.com/FormidableLabs/react-fast-compare/blob/master/index.js
   *  in particular. This code is from
   *  https://stackoverflow.com/questions/38126349/how-to-deeply-compare-two-js-like-objects-in-scala-js.
   */
  def jsEqual(a: js.Any, b: js.Any): Boolean = {
    (a, b) match {
      case (null, null) => true
      case (a: js.Array[_], b: js.Array[_]) =>
        // compare length and each individual item
        a.length == b.length &&
        a.asInstanceOf[js.Array[js.Any]].zip(b.asInstanceOf[js.Array[js.Any]]).forall((jsEqual _).tupled)
      case _ if a != null && a.asInstanceOf[js.Dynamic].constructor == js.constructorOf[js.Object] &&
          b != null && b.asInstanceOf[js.Dynamic].constructor == js.constructorOf[js.Object] =>
        val aDict = a.asInstanceOf[js.Dictionary[js.Any]]
        val bDict = b.asInstanceOf[js.Dictionary[js.Any]]
        (aDict.keySet == bDict.keySet) &&
        aDict.keySet.forall(key => jsEqual(aDict(key), bDict(key)))
      case _ if ((a == null & b != null) || (a != null && b == null)) => false
      case _ =>
        // must be non-object values, this does a js style test
        a == b
    }
  }
}
