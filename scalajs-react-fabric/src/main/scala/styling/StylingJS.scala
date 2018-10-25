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

/**
 * @uifabric/styling == office-ui-fabric-react/lib/Styling. @uifabric/styling
 * also pulls in most of @uifabic/merge-styles.
 */
@js.native
@JSImport("@uifabric/styling", JSImport.Namespace)
object Styling
    extends js.Object
    with ThemeLike
    with StylingLike

@js.native
trait StylingLike extends js.Object {
  /**
    * Merge styles and register in a stylesheet. Return the "css" mangled name
    * you use as the attribute "className" in your components. You probably want
    * to use a style set and `mergeStyleSets` to push a bunch of styles to the
    * stylesheet at one time.
    */
  def mergeStyles(styles: IStyle*): String = js.native

  /**
    * Merge style sets and register in a stylesheet. js.Object is included to
    * allow "partial" T's to be passed in. It is not possible to ensure that all
    * keys in T are defined based on the input parameters alone. This is the
    * primary way to convert "code" to "styles" in the DOM. Generally, this
    * function is called inside a `getClassNames(...)` type function specific to
    * each component. The return value is typically a js.native trait whose
    * member values are the string names of the styles that were registered
    * (string -> classname string).
    */
  def mergeStyleSets[T <: js.Any](styleSets: IStyleSet*): T = js.native

  /**
    * Combine styles together but do not register the styles in a
    * stylesheet. This is useful for combining different sets of styles, like a
    * base and something more specific, inside of a function that computes
    * styles but you want the output to be a (string -> style) mapping still.
    * Last argument has higher precedence.
    */
  def concatStyleSets[T <: js.Any](styleSets: IStyleSet*): T = js.native

  /** Register a font face. */
  def fontFace(font: FontFace): Unit = js.native

  /** Register key frames. Keys are typically "from" and "to". */
  def keyframes(timeline: js.Object): String = js.native

  /**
   * Convert styles to classname and register. You should be using
   * mergeStyleSets/mergeStyles.
   */
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
