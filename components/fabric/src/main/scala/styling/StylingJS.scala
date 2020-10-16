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
package styling

import scala.scalajs.js

import js.annotation._
import js.|

import react.vdom._

/** Magnet pattern for concatStyleSetsWithProps. */
@js.native
trait StyleSetArg extends js.Object

object StyleSetArg {
  @inline implicit def from1[SS <: IStyleSetTag](s: SS): StyleSetArg = s.asInstanceOf[StyleSetArg]
  @inline implicit def from2[SP <: js.Any, SS <: IStyleSetTag](s: IStyleFunctionOrObject[SP, SS]): StyleSetArg =
    s.asInstanceOf[StyleSetArg]
  @inline implicit def from3[SP <: js.Any, SS <: IStyleSetTag](s: IStyleFunction[SP, SS]): StyleSetArg =
    s.asInstanceOf[StyleSetArg]
  @inline implicit def from4[SP <: js.Any, SS <: IStyleSetTag](
    s: js.UndefOr[IStyleFunctionOrObject[SP, SS]]
  ): StyleSetArg = s.asInstanceOf[StyleSetArg]
  @inline implicit def from5[SP <: js.Any, SS <: IStyleSetTag](s: js.UndefOr[IStyleFunction[SP, SS]]): StyleSetArg =
    s.asInstanceOf[StyleSetArg]
  @inline implicit def from6[SS <: IStyleSetTag](s: SS): StyleSetArg = s.asInstanceOf[StyleSetArg]
  @inline implicit def from7[SS <: IStyleSetTag](s: js.UndefOr[SS]): StyleSetArg = s.asInstanceOf[StyleSetArg]
  @inline implicit def from8(s: js.Dynamic): StyleSetArg = s.asInstanceOf[StyleSetArg]
}

/** uifabric/styling == office-ui-fabric-react/lib/Styling. @uifabric/styling
 * also exports most of @uifabic/merge-styles.
 * This should really be the module.
 *
 *
 */
@deprecated("Use fabric.styling.module")
@js.native
@JSImport("@uifabric/styling", JSImport.Namespace)
object Styling extends js.Object with ThemeLike with StylingLike with MergeStyles

//@js.native
//@JSImport("@uifabric/styling", JSImport.Namespace)
//object module extends ThemeLike with StylingLike with MergeStyles

/** uifabric/merge-styles. */
@js.native
trait MergeStyles extends js.Object {

  /**
   * Merge styles and register in a stylesheet. Return the "css" mangled name
   * you use as the attribute "className" in your components. You probably want
   * to use a style set and `mergeStyleSets` to push a bunch of styles to the
   * stylesheet at one time.
   */
  def mergeStyles(styles: (IStyle | js.Array[IStyle])*): String = js.native

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
   *
   * @tparam T js object with mappings between properties and "string" class names.
   *   See IProcessedStyleSet in typescript.
   */
  def concatStyleSets[T <: js.Object](styleSets: IStyleSet*): T = js.native

  /**
   * Combine styles together but allow style functions or props. IStyleSet refers
   * to a js object that has properties whose values are IStyle objects.
   *
   * @tparam S Style props.
   */
  def concatStyleSetsWithProps[SP <: js.Any](props: SP, styleSets: StyleSetArg*): IStyleSet = js.native

  @JSName("concatStyleSetsWithProps")
  def unsafeConcatStyleSetsWithProps[SP <: js.Any](props: SP, styleSets: scala.Any*): IStyleSet = js.native

  /** Register a font face. */
  def fontFace(font: FontFace): Unit = js.native

  /** Register key frames. Keys are typically "from" and "to". */
  def keyframes(timeline: js.Object): String = js.native

  /** Access the stylesheet created by fabric. */
  val Stylesheet: Stylesheet = js.native

  def getVendorSettings(): IVendorSettings = js.native
}

@js.native
trait IVendorSettings extends js.Object {
  val isWebkit: js.UndefOr[Boolean] = js.native
  val isMoz: js.UndefOr[Boolean] = js.native
  val isMs: js.UndefOr[Boolean] = js.native
  val isOpera: js.UndefOr[Boolean] = js.native
}

trait IIconSubset extends js.Object {
  // string to string|JSX.Element
  var icons: js.UndefOr[js.Object] = js.undefined
  var fontFace: js.UndefOr[js.Object] = js.undefined // IFontFace
  var style: js.UndefOr[IRawStyle] = js.undefined
}

trait IIconOptions extends js.Object {
  var disableWarnings: js.UndefOr[Boolean] = js.undefined
  var warnOnMissingIcons: js.UndefOr[Boolean] = js.undefined
}

@js.native
trait StylingLike extends js.Object {
  def getIconClassName(name: String): String = js.native
  def registerIcons(r: IIconSubset, options: js.UndefOr[IIconOptions] = js.undefined): Unit = js.native
}

trait IStyleOptions extends js.Object {
  var rtl: js.UndefOr[Boolean] = js.undefined
}

/** Totally odd that this is not included in the overall Styling exports. */
@js.native
@JSImport("@uifabric/merge-styles/lib/styleToClassName", JSImport.Namespace)
object StyleToClassName extends js.Object {

  /**
   * Convert styles to classname and register. You should be using
   * mergeStyleSets/mergeStyles.
   */
  def styleToClassName(options: IStyleOptions | js.Dynamic, args: IStyle*): String = js.native
}
