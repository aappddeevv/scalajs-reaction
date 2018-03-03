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

/**
  * You can create styling for css-in-js fabric style using literals but its not
  * typesafe. Use the types and imports below to be more, but not completely,
  * typesafe. Importing the object's contents brings a few simple, implicit type
  * converters into scope so you can use js.Array and StyleAttr directly from the
  * vdom package.
  */
object styling {

  /*
  type ICSSRule = String

  /** "number"% */
  type ICSSPercentageRuleType = String

  type ICSSPercentageRule = String

  /** "number"px */
  type ICSSPixelUnitRule = String | Int

  /** normal|bold|bolder|lighter, 100|200|... */
  type IFontWeight = ICSSRule
   */

  // no property checks and untyped, but concise
  // val test2 = lit(
  //   "background" -> "red",
  //   "selectors" -> lit(
  //     ":hover" -> lit(
  //       "background" -> "green"
  //     )
  //   ))

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

  /**
    * @todo: Remove boolean from this and in merge* func sigs. Added js.Dynamic so you can
    * add anything dynamically. Should we add js.Object?
    */
  type IStyleBase = IRawStyle | String | Boolean | Null | Unit | js.UndefOr[Nothing] | js.Dynamic
  type IStyle     = IStyleBase | IRawStyleArray

  /**
    * Keys are usually logical names of your component, e.g. root, header, footer.
    * Could just use a non-native JS trait.
    */
  type IStyleSet = js.Dictionary[IStyle]

  object styleset {
    def apply(stylePairs: (String, IStyle)*): IStyleSet = js.Dictionary[IStyle](stylePairs: _*)
  }

  /** Allows you to write js.Array(...styles...) but have it map to IRawStyleArray. */
  implicit def jsArr2RawStyleArray(arr: js.Array[IStyle]): IRawStyleArray =
    arr.asInstanceOf[IRawStyleArray]

  /** Allows you to use StyleAttr as a IRawStyleBase. */
  implicit def styleAttr2IRawStyleBase(arr: StyleAttr): IRawStyle = arr.asInstanceOf[IRawStyle]
}

import styling._

@js.native
@JSImport("office-ui-fabric-react/lib/Styling", JSImport.Namespace)
object FabricStyling extends js.Object {

  /**
    * Merge styles and register in stylesheet. Return the "css" mangled name you
    * use as a classname in your components.
    */
  def mergeStyles(args: (IStyle | Array[IStyle] | Boolean | Null | js.UndefOr[Nothing])*): String =
    js.native

  /**
    * Merge style sets and register in stylesheet. js.Object is included to allow
    * "partial" T's to be passed in. Note that it is not possble to ensure that
    * all keys in T are defined based on the input parameters due to type
    * differences between scala and typescript. T is typically a native trait
    * with *no* "js.UndefOr" declarations because it is assumed that the inputs
    * will properly fill out the T structure correctly.
    */
  def mergeStyleSets[T <: js.Object](
      cssSets: (js.Object | IStyleSet | Null | js.UndefOr[Nothing])*): T = js.native

  /**
    * Combine styles together but do not register. This is useful for combining
    * different sets of styles, like a base and something more specific, inside
    * of a function that computes some styles for use in a `getClassNames`
    * function you define to create your component's stylesheet.
    */
  def concatStyleSets[T <: js.Object](args: (T | Boolean | Null | js.UndefOr[Nothing])*): T =
    js.native

  /** Register a font face */
  def fontFace(font: FontFace): Unit = js.native

  /** Convert styles to classname and register. You should be using mergeStyleSets/mergeStyles. */
  def styleToClassName(args: IStyle*): String = js.native
}

@js.native
@JSImport("office-ui-fabric-react/lib/Utilities", JSImport.Namespace)
object UtilitiesNS extends js.Object {
  def memoizeFunction(): Unit = js.native
}

object InjectMode {
  val none        = 0
  val insertMode  = 1
  val appendChild = 2
}

trait IStylesheetConfig extends js.Object {
  var injectionMode: js.UndefOr[Int]                       = js.undefined
  var defaultPrefix: js.UndefOr[String]                    = js.undefined
  var onInsertRule: js.UndefOr[js.Function1[String, Unit]] = js.undefined
}

@js.native
@JSImport("@uifabric/merge-styles/lib/Stylesheet", JSImport.Namespace)
object StylesheetNS extends js.Object {

  @js.native
  class Stylesheet extends js.Object {
    def getInstance(): Stylesheet                                       = js.native
    def setConfig(config: js.UndefOr[IStylesheetConfig]): Unit          = js.native
    def reset(): Unit                                                   = js.native
    def getRules(): String                                              = js.native
    def argsFromClassName(className: String): js.Array[IStyle]          = js.native
    def insertedRulesFromClassName(className: String): js.Array[String] = js.native
    def insertRule(rule: String): Unit                                  = js.native
  }

  val Stylesheet: Stylesheet = js.native
}
