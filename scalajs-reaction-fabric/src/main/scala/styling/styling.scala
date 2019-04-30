// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package fabric

import scala.scalajs.js
import js.|
import js.annotation._
import js.Dynamic.{literal => lit}
import ttg.react.vdom._
import js.JSConverters._

/**
  * You can create styling for css-in-js fabric style using literals but its not
  * typesafe. Use the types and imports below to be *more*, but *not*
  * completely, typesafe. Importing the object's contents brings a few implicit
  * type converters into scope so you can use some "type inference driving"
  * functions to create your style parts.
  */
package object styling {

  type IRawStyleBase = RawStyleBase

  type ISelectorSet = js.Dictionary[IStyle]

  trait IRawStyle extends IRawStyleBase {
    var displayName: js.UndefOr[String]     = js.undefined
    var selectors: js.UndefOr[ISelectorSet] = js.undefined
  }

  /** Helper to create entries for the selectors propert on IRawStyle. */
  object selectorset {
    def apply(selects: (String, IStyle)*): ISelectorSet = js.Dictionary[IStyle](selects: _*)
  }

  trait IRawStyleArray extends js.Array[IStyle]

  /** Create an array of styles. This should really have IStyleBase as input. */
  object stylearray {
    def apply(styles: IStyle*): IStyle = js.Array[IStyle](styles: _*).asInstanceOf[IStyle]
    def apply(): IStyle                = js.Array[IStyle]().asInstanceOf[IStyle]
  }

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
   * set as a potentially hierarchical specification of stylesets. When you want
   * to be specific, use a trait instead of IStyleSet. Most of your code should
   * use IStyleSetTag js objects with explicitly defined data members and rely
   * on implicit conversions from IStyleSetTag to IStyleSet defined for
   * convenience when calling the fabric style functions.
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
     * secific `Styles` trait.  You should not really need this function because
     * if you have a IStyleSetTag derived JS trait, just instantitate that trait but
     * if you prefer the "list of pairs" model, use this.
     */
    @inline def make(stylePairs: (String, IStyle)*): IStyleSet = // was IStyleBase|IRawStyleArray
      js.Dictionary[IStyle](stylePairs:_*)
  }

  /** Tag your style trait to help drive style inference. You *must* promise to
   * only have specific types of values in your trait. Generally, your member
   * types will all be `var stylename: js.UndefOr[IStyle]` so that you can
   * specific a subset of the members based on your need. This is for use to
   * create a statically typed version of `IStyleSet` (a dictionary) where the
   * members are statically declared as trait data members. Implicit conversions
   * are available to convert a js.Object to a IStyleSet to call the interop
   * functions.
   */
  trait IStyleSetTag extends js.Object

  /** Tag for an object whose data memebers all returns strings, as in classnames.
   * You *must* promise to only have these type of members in the object.
   */
  trait IClassNamesTag extends js.Object

  /** 
   * Convert style props to a style set. Declare your props=>styleset functions
   * with this type e.g. `val getStyles: IStyleFunction[SP, SST] = props => ???`.
   * However, its easier to use the smart constructor `stylingFunction`.
   * 
   * @tparam SP style props type
   * @tparam style set type e.g. IStyleSet or IStyleSetTag
   */
  //type IStyleFunction[SP <: js.Any, SS <: StyleSetType] = js.Function1[SP, SS]
  type IStyleFunction[SP <: js.Any, SS <: IStyleSetTag] = js.Function1[SP, SS]

  /** Shortcut to define a IStyleFunction. */
  def stylingFunction[SP <: js.Any, SS <: IStyleSetTag](f: SP => SS): IStyleFunction[SP, SS] =
    js.Any.fromFunction1(f)

  /**
   * A style set or a style function. This is the fabric signature, normally we
   * would use a coproduct, perhaps a nice shapeless coproduct.
   * 
   * @tparam SP style props type
   * @tparam SST style set type e.g. IStyleSet or IStyleSetTag
   */
  type IStyleFunctionOrObject[SP <: js.Any, SS <: IStyleSetTag] =
    IStyleFunction[SP, SS] | SS

  /**
   * Given some props and a list of IStyleFunctionOrObjects, resolve to a single
   * (string->IStyle) by either calling the style function with the props or
   * just using the props. Calls `Styling.concatStyleSheets` to properly
   * recursively merge the data. This function is kept js oriented to match
   * `_resolve` in the fabric `styled` HOC module (@uifabric/utilities/styled).
   */
  def resolve[SP <: js.Any, SS <: IStyleSetTag](props: SP,
    styles: js.UndefOr[IStyleFunctionOrObject[SP, SS]]*): SS = {
    val x = js.Array[IStyleSet]()
    for(s <- styles) {
      s.foreach{ style =>
        x.push(
          if(js.typeOf(style.asInstanceOf[js.Object]) == "function")
            s.asInstanceOf[js.Function1[SP, IStyleSet]](props)
          else style.asInstanceOf[IStyleSet]
        )}}
    Styling.concatStyleSets[SS](x:_*)
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
  /** @group converters */
  implicit def jsObject2IStyleSet[T <: js.Object](u: T): IStyleSet = u.asInstanceOf[IStyleSet]

  /** @group converters */
  implicit def jsUndefOrJsObject2IStyleSet[T <: js.Object](u: js.UndefOr[T]): IStyleSet =
    u.asInstanceOf[IStyleSet]

  /** @grop converters */
  implicit def iStyleSetTagToIStyleSet[SS <: IStyleSetTag](s: SS): IStyleSet = s.asInstanceOf[IStyleSet]
}
