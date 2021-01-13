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

package fabric

import scala.scalajs.js
import js.JSConverters._
import js.|

import react.vdom._

/**
 * You can create styling for css-in-js fabric style using literals but its not
 * typesafe. Use the types and imports below to be *more*, but *not*
 * completely, typesafe. Importing the object's contents brings a few implicit
 * type converters into scope so you can use some "type inference driving"
 * functions to create your style parts.
 */
package object styling {

  type IRawStyleBase = RawStyleBase

  //type ISelectorSet = js.Dictionary[IStyle]
  /** Selector sets typically have styles but you could be defining a :global css
   * var which may only have a value e.g. a string color or number.
   */
  type ISelectorSet = js.Dictionary[js.Any]

  trait IRawStyle extends IRawStyleBase {
    var displayName: js.UndefOr[String] = js.undefined
    /** @deprecated Use combineDynamic or variant. */
    var selectors: js.UndefOr[ISelectorSet] = js.undefined
    var subComponentStyles: js.UndefOr[js.Object|js.Dynamic|js.Dictionary[js.Any]] = js.undefined
  }

  private[styling] trait MakeSelectors {

    /** Create a selector set of styles. */
    def apply(selects: (String, IStyle)*): ISelectorSet =
      js.Dictionary[js.Any](selects.asInstanceOf[Seq[(String, js.Any)]]: _*)

    /** Create a selector set out of any values. Experts only! */
    def any(selects: (String, js.Any)*): ISelectorSet =
      js.Dictionary[js.Any](selects: _*)

    /** Create an undefined selector set. */
    def apply(): js.UndefOr[ISelectorSet] = js.undefined

    /** Create an empty selector set. You can add or remove from it. */
    def empty: ISelectorSet = js.Dictionary.empty[js.Any]
  }

  /** Helper to create entries for the selectors propert on IRawStyle. */
  object selectorset extends MakeSelectors

  private[styling] trait MakeSubcomponentStyles {

    /** Create a selector set of styles. */
    def apply(selects: (String, IStyle)*): ISelectorSet =
      js.Dictionary[js.Any](selects.asInstanceOf[Seq[(String, js.Any)]]: _*)

    /** Create a selector set out of any values. Experts only! */
    def any(selects: (String, js.Any)*): ISelectorSet =
      js.Dictionary[js.Any](selects: _*)

    /** Create an undefined selector set. */
    def apply(): js.UndefOr[ISelectorSet] = js.undefined

    /** Create an empty selector set. You can add or remove from it. */
    def empty: ISelectorSet = js.Dictionary.empty[js.Any]
  }

  /** Helper to create entries for the selectors propert on IRawStyle. */
  object subcomponentstyles extends MakeSubcomponentStyles
  
  trait IRawStyleArray extends js.Array[IStyle]

  private[styling] trait MakeStyles {
    def apply(styles: IStyle*): IStyle = js.Array[IStyle](styles: _*).asInstanceOf[IStyle]
    def apply(): IStyle = js.Array[IStyle]().asInstanceOf[IStyle]
  }

  /** Create an array of styles. This should really have IStyleBase as input. */
  object stylearray extends MakeStyles

  /** Create an array of styles. This should really have IStyleBase as input. Same as stylearray. */
  object styles extends MakeStyles

  /**
   * Added js.Dynamic so you can add anything dynamically, which is not
   * typesafe but it is convenient! Should we keep js.Object to the union?
   * Allows us to define and use some non-native JS traits but is also a bit
   * loosely typed.
   */
  type IStyleBase = IRawStyle | String | js.Dynamic | js.Object

  /** A simple style object/string/thing or an array of those. A plain string becomes a classname in mergestyles. */
  type IStyle = IStyleBase | IRawStyleArray

  /**
   * Keys are usually the logical names of your component, e.g. root, header,
   * footer. Fabric also desginates `subComponentStyles` as a property that is
   * an object with string->istyle mappings or a function that takes properties
   * (an object) and returns string->istyle mappings. So fabric defines a style
   * set as a potentially hierarchical specification of stylesets. When you
   * want to be specific, use a trait instead of IStyleSet. Most of your code
   * should use IStyleSetTag js objects with explicitly defined data members
   * and rely on implicit conversions from IStyleSetTag to IStyleSet defined
   * for convenience when calling the fabric style functions. Should this
   * really be `js.Dictonary[js.UndefOr[IStyle]`?
   */
  type IStyleSet = js.Dictionary[IStyle]

  /**
   * Create a style set. You can use this to help drive type inference or you
   * can use a JS trait directly (see `make` and `IStyleSetTag`). Use this
   * helper when you want an explicit IStyleSet but generally you should create
   * style objects and tag them with IStyleSetTag.
   *
   * @example {{{
   *  mergeStyleSets[SomeClassNames](
   *  styleset(
   *    "root" -> stylearray(
   *      "xx-PartName",
   *      new IRawStyle { ... },
   *      if(something) null else new IRawStyle { ... },
   *      customStyles.flatMap(_.root)
   *  )
   * )
   * }}}
   */
  object styleset {

    /** Create an IStyleSet from pairs. Useful for mergeStyleSets */
    @inline def apply(stylePairs: (String, IStyle)*): IStyleSet = // was IStyleBase|IRawStyleArray
      js.Dictionary[IStyle](stylePairs: _*)

    /** Same as `apply` but cast to your final T type. Tyically T is the component's
     * specific `Styles` trait.  You should not really need this function
     * because if you have a IStyleSetTag derived JS trait, just instantitate
     * that trait but if you prefer the "list of pairs" model, use this.
     */
    @inline def make(stylePairs: (String, IStyle)*): IStyleSet = // was IStyleBase|IRawStyleArray
      js.Dictionary[IStyle](stylePairs: _*)

    /** Assume that a IStyleSetTag is a IStyleSet. */
    @inline def assume(o: IStyleSetTag): IStyleSet = o.asInstanceOf[IStyleSet]
  }

  /** Tag your style trait to help drive style inference. You *must* promise to
   * only have specific types of values in your trait. Generally, your member
   * types will all be `var stylename: js.UndefOr[IStyle]` so that you can
   * specific a subset of the members based on your need. This is for use to
   * create a statically typed version of `IStyleSet` (a dictionary) where the
   * members are statically declared as trait data members. Implicit
   * conversions are available to convert a js.Object to a IStyleSet to call
   * the interop functions. You must promise to only have IStyle or
   * js.UndefOr[IStyle] objects as values.
   */
  trait IStyleSetTag extends js.Object

  /** Tag for an object whose data memebers all returns strings (= classnames).
   * You *must* promise to only have these type of members in the object.
   */
  trait IClassNamesTag extends js.Object

  /**
   * Convert style props to a style set. Declare your props=>styleset functions
   * with this type e.g. `val getStyles: IStyleFunction[SP, SST] = props =>
   * new Styles { ... }`.  However, its easier to use the smart constructor `stylingFunction`.
   *
   * @tparam SP style props type
   * @tparam SS Style set type e.g. IStyleSet or js object derived from IStyleSetTag
   */
  //type IStyleFunction[SP <: js.Any, SS <: StyleSetType] = js.Function1[SP, SS]
  type IStyleFunction[SP <: js.Any, SS <: IStyleSetTag] = js.Function1[SP, SS]

  /** @deprecated("Use IStyleFunction") */
  type IStyleFn[SP <: js.Any, SS <: IStyleSetTag] = js.Function1[SP, SS]

  /** Smart constructor with better type inference to define a IStyleFunction as a val.
   * Use this whene you need to keep the type as a function.
   */
  def stylingFunction[SP <: js.Any, SS <: IStyleSetTag](f: SP => SS): IStyleFunction[SP, SS] = f

  /** Smart constructor with better type inference to define a IStyleFunctionOrObject as a val.
   * Use this when you need to pass your styling function to fabric's functions but don't need
   * to call it yourself.
   */
  def stylingFunctionOr[SP <: js.Any, SS <: IStyleSetTag](f: SP => SS): IStyleFunctionOrObject[SP, SS] =
    stylingFunction[SP, SS](f)

  /** Type for a logical `getClassNames` function declared as a val. Use this if you need
   * to memoize the function and use js memoization. If all you do is call `getClassNames`
   * inside a `useMemo` hook, then you can define your component's `getClassNames` as a standard
   * scala function.
   *
   * @tparam SP Style props
   * @tparam S Styles js object, derived from IStyleSetTag
   * @tparam C Class names object derived from IClassNamesTag.
   */
  type GetClassNamesFn[SP <: js.Any, S <: IStyleSetTag, C <: IClassNamesTag] =
    js.Function2[SP, js.UndefOr[IStyleFunctionOrObject[SP, S]], C]

  // /** Like stylingFunction, shortcut to define a getClassNames function as a val
  //  * that takes a style props object and an optional style function/object. Your
  //  * program may not use this pattern so you will want to define your own
  //  * version. Unlike stylingFunction, getClassNames has a signatue of your own
  //  * choosing.
  //  */
  // def getClassNamesFunction[SP <: js.Any, S <: IStyleSetTag, C <: IClassNamesTag](
  //     f: (SP, js.UndefOr[IStyleFunctionOrObject[SP, S]]) => C
  // ): GetClassNamesFn[SP, S, C] = f

  /**
   * A style set or a style function. This mirrors the fabric signature.
   *
   * @tparam SP style props type
   * @tparam SST style set type e.g. IStyleSet or IStyleSetTag
   */
  type IStyleFunctionOrObject[SP <: js.Any, SS <: IStyleSetTag] =
    IStyleFunction[SP, SS] | SS

  /** Combine IStyleFunction, IStyleFunctionOrObject into a single array of type StyleSetArg
   * needed for the args in concatStyleSets or concatStyleSetsWithProps. Helps with type inference
   * and composing args for varargs.
   */
  def toStyleSetArgs[StyleProps <: js.Object, Styles <: IStyleSetTag](
    args: Seq[js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]]]) = args.asInstanceOf[Seq[StyleSetArg]]

  /**
   * Given some props and a list of IStyleFunctionOrObjects, resolve to a single
   * (string->IStyle) by either calling the style function with the props or
   * just using the props. Calls `Styling.concatStyleSheets` to properly
   * recursively merge the data. This function is kept js oriented to match
   * `_resolve` in the fabric `styled` HOC module (@uifabric/utilities/styled).
   * Note that @merge-styles/concatStylesSetsWithProps can perform this processing.
   */
  @deprecated("Use concatStyleSetsWithProps")
  def resolve[SP <: js.Any, SS <: IStyleSetTag](props: SP, styles: js.UndefOr[IStyleFunctionOrObject[SP, SS]]*): SS = {
    val x = js.Array[IStyleSet]()
    for (s <- styles) {
      s.foreach { style =>
        x.push(
          if (js.typeOf(style.asInstanceOf[js.Object]) == "function")
            s.asInstanceOf[js.Function1[SP, IStyleSet]](props)
          else style.asInstanceOf[IStyleSet]
        )
      }
    }
    Styling.concatStyleSets[SS](x.toSeq: _*)
  }

  //
  // Automatic converters to make this bearable syntax-wise. Should these be
  // optional and intentionally imported if desired? e.g. make in implicits object
  // that needs to be imported.
  //

  /** Convert null to style. @group converters */
  implicit def null2IStyle(n: Null): IStyle = n.asInstanceOf[IStyle]

  /** Convert unit, which is "nothing" to a null. */
  //implicit def unit2IStyle(n: Null): IStyle = null.asInstanceOf[IStyle]

  /** Any old dynamic maps to a style since we don't know what's in it so be hopeful. @group converters */
  implicit def dyn2IStyle(d: js.Dynamic): IStyle = d.asInstanceOf[IStyle]

  /** Map a UndefOr <stuff> to IStyle directly. @group converters */
  implicit def undefOrJsObject2IStyle(u: js.UndefOr[js.Object]): IStyle =
    u.asInstanceOf[IStyle]

  /** @group converters */
  implicit def undefOrIRawyStyle2IStyle(u: js.UndefOr[IRawStyle]): IStyle =
    u.asInstanceOf[IStyle]

  /** Unwrap the option or return null. @group converters */
  implicit def styleOpt2IStyle(vopt: Option[IStyle]): IStyle =
    vopt.getOrElse[IStyle](null)

  /** Directly convert to a style since undefined is valid. @group coneverters */
  implicit def undef2IStyle(vopt: js.UndefOr[IStyle]): IStyle =
    vopt.asInstanceOf[IStyle]

  /** Unwrap the string or return null. @group converters */
  implicit def stringOpt2IStyle(sopt: Option[String]): IStyle =
    sopt.getOrElse(null).asInstanceOf[IStyle]

  /** Unwrap the string or return null. @group converters */
  implicit def stringUndefOr2IStyle(sopt: js.UndefOr[String]): IStyle =
    sopt.asInstanceOf[IStyle]

  /** Convert standard vdom StyleAttr to IStyle. @grop converters */
  implicit def styleAttr2IRawStyleBase(arr: StyleAttr): IStyle = arr.asInstanceOf[IStyle]

  //
  // Support for mergeStyleSets/concatStyleSets. Convert IStyleSetTags to the
  // canonical IStyleSet needed for the fabric functions.
  //
  implicit def jsObject2IStyleSet[T <: js.Object](u: T): IStyleSet = u.asInstanceOf[IStyleSet]
  implicit def jsUndefOrJsObject2IStyleSet[T <: js.Object](u: js.UndefOr[T]): IStyleSet =
    u.asInstanceOf[IStyleSet]
  implicit def iStyleSetTagToIStyleSet[SS <: IStyleSetTag](s: SS): IStyleSet = s.asInstanceOf[IStyleSet]
}
