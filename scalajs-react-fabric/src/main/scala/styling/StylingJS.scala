// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package fabric
package styling

import scala.scalajs.js
import js.|
import js.annotation._
import js.Dynamic.{literal => lit}
import ttg.react.vdom._
import js.JSConverters._

@js.native
@JSImport("office-ui-fabric-react/lib/Styling", JSImport.Namespace)
object Styling extends js.Object {

  /**
   * Merge styles and register in stylesheet. Return the "css" mangled name you
   * use as the attribute "className" in your components.
   *
   * @todo Work on the typing of inputs, not sure this is right.
   */
  def mergeStyles(args: (IStyle | Array[IStyle] | Boolean | Null | js.UndefOr[String] | js.UndefOr[Nothing])*): String =
    js.native

  /**
   * Merge style sets and register in stylesheet. js.Object is included to
    * allow "partial" T's to be passed in. Note that it is not possble to ensure
    * that all keys in T are defined based on the input parameters due to type
    * differences between scala and typescript. T is typically a native trait
    * with *no* "js.UndefOr" declarations because it is assumed that the inputs
    * will properly fill out the T structure correctly.
    */
  def mergeStyleSets[T <: js.Object](
      cssSets: (js.Object | IStyleSet | Null | js.UndefOr[js.Object|IStyleSet|Null])*): T = js.native

  /**
    * Combine styles together but do not register. This is useful for combining
    * different sets of styles, like a base and something more specific, inside
    * of a function that computes some styles for use in a `getClassNames`
    * function you define to create your component's stylesheet.
    */
  def concatStyleSets[T <: js.Object](args: (T | Null | IStyleSet | js.UndefOr[T])*): T =
    js.native

  /** Register a font face */
  def fontFace(font: FontFace): Unit = js.native

  /** Convert styles to classname and register. You should be using mergeStyleSets/mergeStyles. */
  def styleToClassName(args: IStyle*): String = js.native

  /** Access the stylesheet created by fabric. */
  val Stylesheet: Stylesheet = js.native
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
trait Stylesheet extends js.Object {
  def getInstance(): Stylesheet                                       = js.native
  def setConfig(config: js.UndefOr[IStylesheetConfig]): Unit          = js.native
  def reset(): Unit                                                   = js.native
  def getRules(): String                                              = js.native
  def argsFromClassName(className: String): js.Array[IStyle]          = js.native
  def insertedRulesFromClassName(className: String): js.Array[String] = js.native
  def insertRule(rule: String): Unit                                  = js.native
}

// @js.native
// @JSImport("@uifabric/merge-styles/lib/Stylesheet", JSImport.Namespace)
// object Stylesheet extends js.Object {
//   val Stylesheet: Stylesheet = js.native
// }
