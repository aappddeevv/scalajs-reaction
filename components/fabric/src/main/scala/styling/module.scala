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

import react._
import scala.scalajs.js
import js.annotation._

@js.native
trait styles_CommonStyles extends js.Object {
  val HigHContrastSelector: String                    = js.native
  val HighContrastSelectorWhite: String               = js.native
  val HighContrastSelectorBlack: String               = js.native
  val SCreenWidthMinSmall: Int                        = js.native
  val ScreenWidthMinMedium: Int                       = js.native
  val ScreenWidthMinLarge: Int                        = js.native
  val ScreenWidthMinXLarge: Int                       = js.native
  val ScreenWidthMinXXLarge: Int                      = js.native
  val ScreenWidthMinXXXLarge: Int                     = js.native
  val ScreenWidthMaxSmall: Int                        = js.native
  val ScreenWidthMaxMedium: Int                       = js.native
  val ScreenWidthMaxLarge: Int                        = js.native
  val ScreenWidthMaxXLarge: Int                       = js.native
  val ScreenWidthMaxXXLarge: Int                      = js.native
  val ScreenWidthMinUhfMobile: Int                    = js.native
  def getScreenSelector(min: Int, max: Int): String = js.native
}

@js.native
@JSImport("@uifabric/styling", JSImport.Namespace)
object module extends js.Object with styles_CommonStyles with ThemeLike with StylingLike with MergeStyles {
  def buildClassMap(styles: js.Object): js.Dictionary[String] = js.native
  val normalize: IRawStyle                                    = js.native
  val noWrap: IRawStyle                                       = js.native
  val AnimationClassNames: IAnimationStyles                   = js.native
  val ColorClassNames: IColorClassNames                       = js.native
  val FontClassNames: IFontClassNames                         = js.native
  val FontSizes: IFontSizes                                   = js.native
  val FontWeightS: IFontWeights = js.native
val IconFontSizes: IIconFontSizes = js.native
val AnimationStyles: IAnimationStyles = js.native
val DefaultPalette: IPalette = js.native
val DefaultEffects: IEffects = js.native
}
