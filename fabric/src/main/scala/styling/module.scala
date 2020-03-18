package fabric
package styling

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
  def greenScreenSelector(min: Int, max: Int): String = js.native
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
