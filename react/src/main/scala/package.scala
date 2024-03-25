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

import scala.scalajs.js
import js.JSConverters.*

/**
 * A scala.js react facaded in the spirit of ReasonReact.
 */
package object react extends react.React with When {

  /** A js dispatch function for side effects. Used in useState. */
  type Dispatch[A] = js.Function1[A, Unit]

  /** General purpose type that grabs AnyVal and some js types. Using just js.Any is not ergonomic. */
  type AllType =
    Boolean | Byte | Short | Float | String | Long | Double | Int | 
    js.Any | 
    js.Object |
    js.Dictionary[?] | 
    js.Symbol | 
    js.Array[scala.Any] | Null | Unit

  /** Hook dependencies data structure. Hooks require a list of objects that when changed, re-runs the evaluation process. */
  type Dependencies = js.Array[AllType]

  /** Empty array which is different than undefinedDependencies. Typically
   * indicates mount/unmount hook processing. `=== []`
   * 
   * Use `useEffectMounting` instead.
   */
  val emptyDependencies: Dependencies = js.Array()

  /** Undefined array typically indicates per render. Typically *not* what you want. 
   * Use `useEffectEveryRender` instead.
  */
  val undefinedDependencies: js.UndefOr[Dependencies] = js.undefined

  /** Create a dependencies array. With no args, its a zero-length array.
   * Use this to declare dependencies for effects if the dependency
   * is an single array because otherwise the array elements are
   * interpreted to be the dependencies vs the array itself.
   */
  def deps(values: AllType*): Dependencies = values.toJSArray

  /** Escape hatch. Create a dependencies array from *any* list of scala
   * values. Make sure you think about each value type in the list of
   * dependencies. Use this inference helper to remind you to think about the
   * types and scala-js interop.
   */
  def unsafeDeps(values: scala.Any*): Dependencies =
    values.toJSArray.asInstanceOf[Dependencies]

  /** Noop for `useEffect`-like callbacks. */
  val noCleanUp: js.Function0[Unit] = () => ()

  /**
   * Non-mutable ref. Object returned from `createRef()` and some variants of `useRef` hook. 
   It's typically either js component or a
   * DOM element, both of which are js.Objects. You only use this type with the
   * `React.createRef()` machinery. Use this instead of a string or ref
   * callback. Introduced in 16.3. `useRef` uses a slightly different flavor
   * of `current`. The current value is immutable.
   */
  @js.native
  trait ReactRef[T] extends js.Object:
    /** See react.syntax for syntax support on handling E|Null
     * e.g. myref.current.toNonNullOption.
     */
    val current: T | Null = js.native

  extension [T](r: ReactRef[T])
    /** If needed to satisfy API constraints, convert to a mutable ref whose current
      * value can be changed.
      */
    def asMutableRef = r.asInstanceOf[MutableRef[T]]

  /** Callback for react ref with settable E. */
  type RefCb[E] = js.Function1[E | Null, Unit]

  /** For use with useRef() hook which is slightly different than the mutable
   * Ref. `current` is designed to be set directly or can be used on the `ref`
   * attribute for a component. Since you can provide an initial value, if you
   * want `null` to be valid, either use `null` directly or model it explicitly
   * as `T|Null` to force client's to deal with the explict null value (otherwise
   * they need to check for the `null` value directly).
   *
   * Reconcile with ReactRef perhaps rename the member to something like `unsafe`.
   * 
   * @tparam T Ref type. Could be `T|Null` as well.
   */
  trait MutableRef[T] extends js.Object:
    var current: T

  extension [T](r: MutableRef[T])
    /** If needed to satisfy API such as the ref associated with DOM
     * elements, convert to a ReactRef. This is messy in typescript as well.
     */
    def asReactRef = r.asInstanceOf[ReactRef[T]]

  /** Combine all possible variations of `Ref` models. */
  type Ref[E] = RefCb[E] | ReactRef[E] | MutableRef[E]

  /* Like `refCB` but better named. */
  def callbackAsRef[E](f: (E | Null => Unit)): Ref[E] =
    js.Any.fromFunction1(f).asInstanceOf[Ref[E]]

  /** Make Ref[E] from callback. See syntax for dealing with E|Null. */
  @deprecated("Use callbackAsRef")
  def refCB[E](f: E | Null => Unit): Ref[E] =
    js.Any.fromFunction1[E | Null, Unit](f).asInstanceOf[Ref[E]]

  /**
   * Something that can be rendered in reactjs. react allows this to
   * be quite flexible including strings, numbers, booleans in additon to
   * classes or functions. Classes and functions are convered to
   * a ReactNode by calling `createElement` on them however strings and
   * numbers can be rendered directly.
   */
  @js.native
  trait ReactNode extends js.Any

  /** Output from `react.createElement`. Subtype of ReactNode to indicate
   * that the output came from `createElement` vs say, a string or number.
   */
  @js.native
  trait ReactElement extends ReactNode
  /*{
    val key: UndefOr[String] = js.native
    def ref[E]: UndefOr[RefCb[E]] = js.native
    /** Internal react. Do not use. */
    val `type`: String = js.native
  }
   */

//   object ReactElement {
//     implicit class RichReactElement(el: ReactElement) {
//       def withKey(key: String): ReactElement = merge[ReactElement](el, js.Dynamic.literal("key" -> key))
//     }
//   }

  @js.native
  trait ReactChildren extends ReactElement

  /** Very un-ergonomic, but this is what FB has. */
  type KeyType = String | Int

  /** Use these to mix into your prop traits to ensure you have a a key and ref to
   * set. For example, add this as a supertrait to a Props class so that you can specify a key
   * when you create it.
   */
  @deprecated("Use ReactJSProps", "0.1.0")
  trait ReactPropsJs extends js.Object {
    var key: js.UndefOr[KeyType] = js.undefined
    def ref[E]: js.UndefOr[RefCb[E]] = js.undefined
  }

  /** You should probably should be using `MaybeHasStrKey` but generally
   * use this supertrait to allow callers to specify key and ref values
   * and have them typed.
   */
  trait ReactJSProps extends js.Object {
    var key: js.UndefOr[KeyType] = js.undefined
    def ref[E]: js.UndefOr[RefCb[E]] = js.undefined
  }

  /** Optional key but no ref. Key must be a string. */
  trait MaybeHasStrKey extends js.Object {
    def key: js.UndefOr[String] = js.undefined
  }

  /** Known to contain a key. You can force callers to incuded a key
   * using this supertrait so it has very limited use.
  */
  trait HasKey extends js.Object {
    def key: KeyType
  }

  /** With key but key is known to be a String which is the common scenario. */
  trait HasStrKey extends js.Object {
    val key: String
  }

  /** Scala side verson of ReactPropsJs. It assumed that you use key and/or ref to
   * create your actual react or react dom components. Hence the use of Option.
   * It usually better to define them as UndefOr instead of Option so you can
   * directly place them into a DOM element's props in your render function.
   */
  // trait ReactProps {
  //   val key: Option[String] = None
  //   def ref[E]: Option[RefCb[E]] = None
  // }

  /**
   * A standard HTML element that has been created using `createElement`.
   * Props are optional of course.  We use this tag it to show that it is
   * associated from the standard DOM tagset.
   */
  @js.native
  trait ReactDOMElement extends ReactElement {
    //def `type`: String = js.native

    /** Raw JS props. You don't normally access this. Don't use. */
    def props: js.UndefOr[js.Object] = js.native
  }

  /**
   * A js-object that is returned from create-react-class. In reactjs, a react
   * class is a js object created using the "class" construct. This should
   * really be ReactJSComponent but we keep this slightly different for those
   * who care about such things.
   */
  @js.native
  trait ReactClass extends js.Object

  /** For creating a portal in react-dom. */
  @js.native
  trait ReactPortal extends ReactElement

  /** Pure JS functions defined in scala are also components. */
  type ScalaJSFunctionComponent = js.Function0[ReactNode]

  /** Pure JS functions defined in scala are also components. */
  type ScalaJSFunctionComponent1 = js.Function1[? <: js.Object, ReactNode]

  type ScalaJSFunctionComponent1WithRef = js.Function2[? <: js.Object, ? <: js.Any, ReactNode]

  /** Some "primitive" types can also be converted. */
  type PrimitiveConvertibleToNode = String|Int|Long|Float|Double|Byte

  /** Something that can be used in `ReactJS.createElement()`. Given an object of
   * this type, you must call `createElement` on it to create the element
   * with optional props and children. If a parent is in a component, you use
   * this type to represent it.
   */
  type ReactType =
    ReactClass | 
    PrimitiveConvertibleToNode | 
    ReactJSComponent | 
    ReactJSFunctionComponent | 
    ReactJSLazyComponent1[?]| 
    ReactJSLazyComponent | 
    ScalaJSFunctionComponent | 
    ScalaJSFunctionComponent1 | 
    ScalaJSFunctionComponent1WithRef

  /** Component type that can be used in HOCs. */
  type ReactComponentType =
    ReactClass | 
    ReactJSComponent | 
    ReactJSFunctionComponent | 
    ReactJSLazyComponent1[?] | 
    ReactJSLazyComponent | 
    ScalaJSFunctionComponent | 
    ScalaJSFunctionComponent1 | 
    ScalaJSFunctionComponent1WithRef

  /**
   * This type is used only as a target for imported javascript authored components to
   * "tag" a component type or created via other js-interop mechanisms such as
   * redux integration. By using a separate type, you must use then
   * scalajs-react's API to create an element. You can use this to annotate a
   * js function react component as well e.g. () => ReactNode.
   */
  //@js.native
  //trait ReactJSComponent extends js.Object

  /**
   * This type is used only as a target for imported javascript authored components to
   * "tag" a component declare in scala-js land. By using a separate type, you must use the
   * this library's API to create an element. Use can this to annotate a
   * js function react component as well e.g. () => ReactNode if the specifi nature
   * of the component is not important.
   * 
   * Typical use is `@JSImport("some-lib", "BlahView") @js.native val BlahView: ReactJSComponent`
   * where `BlahView` is a JS react component in js library `some-lib`.
   */
  @js.native
  trait ReactJSComponent extends js.Object

  /** Import type target from js land that are functions and it is important to import 
   * them as such. May or may not make a difference to have this typed separately.
   * You could also import it as a jsFunctionN object.
   */
  @js.native
  trait ReactJSFunctionComponent extends js.Object
  
  /** Create a react node with a single object arg. */
  type ReactJSLazyComponent1[P <: js.Object] = js.Function1[P, ReactNode]
  
  /** Create a react node without args. */
  type ReactJSLazyComponent = ScalaJSFunctionComponent

  /** All the types of components that can be imported from JS. These must be
   * processed to be allowed to be used as Components in this facade. Keep in
   * sync with `ReactType`.
   */
  type ImportedJSComponent = ReactJSComponent | ReactJSFunctionComponent | ReactJSLazyComponent1[?] | ReactJSLazyComponent

  /** The type of `() => import("somecomponent")` which is used exclusively for
  * the argument to React.lazy.
  */
  type DynamicImportThunk = js.Function0[js.Promise[DynamicImport]]

  /** Alias for internal use.
   * @deprecated
   */
  type ReactClassInternal = ReactClass

  /** Return a "render nothing" element. React ignores nulls during rendering. */
  val nullElement = null.asInstanceOf[ReactElement]

  /** null element, which does not render, cast to a ReactNode for better type inference. */
  val nullNode = null.asInstanceOf[ReactNode]

  /** Convenience. Use implicits for syntax-based conversion. */
  def arrayToElement[T <: ReactNode](arr: js.Array[T]) = arr.asInstanceOf[ReactNode]

  /** Convenience. Use implicits for automatic conversion. */
  def iterableToElement[T <: ReactNode](s: Iterable[T]) = (s.toJSArray).asInstanceOf[ReactNode]

  /** String to react node. Use implicits for automatic conversion and avoid
   * calling this function.
   */
  def stringToElement(s: String): ReactNode = s.asInstanceOf[ReactNode]

  /**
   * Empty children value but non-empty array. Be careful as someone could
   * mutate this global instance on you.
   */
  val emptyChildrenVal = js.Array[ReactNode]()

  /** Allocate an empty child array. It's not shared like `emptyChildrenVal` */
  def emptyChildren = js.Array[ReactNode]()

  /** Make a callback. Helps with type inference. There is
   * some dedicated syntax support for `E|Null` handling.
   */
  def refCallback[E](f: E | Null => Unit): RefCb[E] = f

  /** Children type. */
  type PropsChildren = js.Array[ReactElement]

  /** No children value. */
  val noChildren: PropsChildren = js.Array()

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
   * Merge js.Dynamics into a fresh object. You should really use [[merge]].
   * https://stackoverflow.com/questions/36561209/is-it-possible-to-combine-two-js-dynamic-objects
   */
  def mergeJSObjects(objs: js.Dynamic*): js.Dynamic =
    js.Object.assign(js.Dynamic.literal(), objs.asInstanceOf[Seq[js.Object]]*).asInstanceOf[js.Dynamic]
  // // not js.Any? maybe keep js or scala values in here....
  // val result = js.Dictionary.empty[Any]
  // for (source <- objs) {
  //   if(source != null)
  //     for ((key, value) <- source.asInstanceOf[js.Dictionary[Any]])
  //       result(key) = value
  // }
  // result.asInstanceOf[js.Dynamic]

  /**
   * Shallow merge objects into a new object and cast. Useful when merging props
   * with data- attributes or component properties together. Last value wins. A
   * syntax version of this function is available on js objects called
   * `combine`. Creates and returns new object. Note, should we just call
   * js.Object.assign here?
   */
  def merge[T <: js.Object](objs: js.UndefOr[js.Object] | js.Dynamic | js.Object | Null*): T =
    js.Object.assign(js.Dynamic.literal(), objs.asInstanceOf[Seq[js.Object]]*).asInstanceOf[T]
  //objs.toJSArray.asInstanceOf[js.Array[js.Object]]:_*).asInstanceOf[T]
  // val result = js.Dictionary.empty[Any]
  // for (source <- objs) {
  //   if(source != null && source.asInstanceOf[js.UndefOr[js.Object]].isDefined)
  //     for ((key, value) <- source.asInstanceOf[js.Dictionary[Any]])
  //       result(key) = value
  // }
  // result.asInstanceOf[T]

  /** Shallow copy. Should we call Object.assign directly? Faster? */
  def copy[T <: js.Object](obj: js.Dynamic | js.Object): T = merge[T](obj)

  /** Add a key. Mutates input object directly because hey, this is javascript! This is
   * definitely not correct. DO NOT USE!
   */
  def withKey[T <: js.Object](element: T, key: String): ReactElement = {
    element.asInstanceOf[js.Dynamic].key = key
    return element.asInstanceOf[ReactElement]
  }

  // /** Return None if undefined or null -> None, otherwise return a Some.  Syntax
  //  * support makes this easier so you don't have to use this function.
  //  */
  // def toSafeOption[T <: js.Any](t: js.Any): Option[T] =
  //   if (js.isUndefined(t) || t == null) None
  //   else Option(t.asInstanceOf[T])

  /** Short version of `js.defined(blah)` */
  @inline def jsdef[A](a: A) = js.defined(a)

  /** Short version of `js.undefined`. */
  val jsundef = js.undefined

  /** Test equaliy with two js objects. Standard == in scala.js on 2 js objects
   * (e.g. non-native traits) will not test in the same way that standard scala
   * tests equality. Do not use this with react components (js objects) because
   * react component objects may have circular data structures.
   *
   *  You may want to look at some standard js equality libraries--there are
   *  many and are opimized in various ways. See
   *  https://github.com/FormidableLabs/react-fast-compare/blob/master/index.js
   *  in particular. This code is from
   *  https://stackoverflow.com/questions/38126349/how-to-deeply-compare-two-js-like-objects-in-scala-js.
   */
  def jsEqual(a: js.Any|Null, b: js.Any|Null): Boolean =
    (a, b) match {
      case (null, null)                     => true
      case (a: js.Array[_], b: js.Array[_]) =>
        // compare length and each individual item
        a.length == b.length &&
          a.asInstanceOf[js.Array[js.Any]].zip(b.asInstanceOf[js.Array[js.Any]]).forall((jsEqual).tupled)
      case _
          if a != null && a.asInstanceOf[js.Dynamic].constructor == js.constructorOf[js.Object] &&
            b != null && b.asInstanceOf[js.Dynamic].constructor == js.constructorOf[js.Object] =>
        val aDict = a.asInstanceOf[js.Dictionary[js.Any]]
        val bDict = b.asInstanceOf[js.Dictionary[js.Any]]
        (aDict.keySet == bDict.keySet) &&
        aDict.keySet.forall(key => jsEqual(aDict(key), bDict(key)))
      case _ if ((a == null & b != null) || (a != null && b == null)) => false
      case _                                                          =>
        // must be non-object values, this does a js style test
        a == b
    }

  /** A type for declaring an element using a tuple and an implicit conversion.
   * An implicit can pick this up and convert it to a react element.
   */
  type ReactElementTuple[P <: js.Object] = (js.Function1[P, ReactNode], P)

  /** A type used to drive type inference when declaring your component.
   * Generally, your render methods should be declared using this type
   * so that the function is automatically converted to a js function.
   * 
   * Use this pattern, `val render: ReactFC[Props] = props => { ... }`.
   */
  type ReactFC[P <: js.Object] = js.Function1[P, ReactNode]

  /** Memo a function component. */
  def memoWith[P <: js.Object](f: ReactFC[P]): ReactFC[P] =
    memo(f).asInstanceOf[ReactFC[P]]

  /** Memo a function component. */
  def memoWith[P <: js.Object](compare: js.Function2[js.UndefOr[P], js.UndefOr[P], Boolean])(
    f: ReactFC[P]): ReactFC[P] =
    memo(f, compare).asInstanceOf[ReactFC[P]]

  /** Memo a function component. */
  def memoWith[P <: js.Object](f: P => ReactNode): ReactFC[P] =
    memo(js.Any.fromFunction1(f)).asInstanceOf[ReactFC[P]]

  /** Memo a function component. */
  def memoWith[P <: js.Object](compare: (js.UndefOr[P], js.UndefOr[P]) => Boolean)(f: P => ReactNode): ReactFC[P] =
    memo(js.Any.fromFunction1(f), js.Any.fromFunction2(compare)).asInstanceOf[ReactFC[P]]

  /** A component that takes a ref as the second argument. */
  type RectFCWithRef[P <: js.Object, T <: js.Any] = js.Function2[P, ReactRef[T], ReactNode]

  /** A type used to drive type inference when declaring your component. 
   * 
   * Use this pattern, `val render: ReactFC0 = () => { ... }`. 
   */
  type ReactFC0 = js.Function0[ReactNode]

  /** Type inference hepler. */
  val nullString = null.asInstanceOf[String]

  /** Type inference helper. */
  val nullInt = null.asInstanceOf[Int]

  /** Type inference helper. */
  val nullLong = null.asInstanceOf[Long]

  /** Type inference helper. */
  val nullJSDate = null.asInstanceOf[js.Date]

  /** Type inference helper. */
  val nullFloat = null.asInstanceOf[Float]

  /** Type inference helper. */
  val nullDouble = null.asInstanceOf[Double]

  /** Type inference helper. */
  val nullJSObject = null.asInstanceOf[js.Object]

  /** Type inference helper. */
  val nullDate = null.asInstanceOf[js.Date]

  /** Empty array. Freshly allocated!
   * 
   * @todo Add `()` to communicate it is side effecting.
   */
  def emptyArray[T] = js.Array[T]()
}
