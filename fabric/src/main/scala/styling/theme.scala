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

import fabric.styling._

/** Color values, typically hex. */
@js.native
trait IPalette extends js.Object {
  val themeDarker: js.UndefOr[String]          = js.native
  val themeDark: js.UndefOr[String]            = js.native
  val themeDarkAlt: js.UndefOr[String]         = js.native
  val themePrimary: js.UndefOr[String]         = js.native
  val themeSecondary: js.UndefOr[String]       = js.native
  val themeTertiary: js.UndefOr[String]        = js.native
  val themeLight: js.UndefOr[String]           = js.native
  val themeLighter: js.UndefOr[String]         = js.native
  val themeLighterAlt: js.UndefOr[String]      = js.native
  val black: js.UndefOr[String]                = js.native
  val blackTranslucent40: js.UndefOr[String]   = js.native
  val neutralDark: js.UndefOr[String]          = js.native
  val neutralPrimary: js.UndefOr[String]       = js.native
  val neutralPrimaryAlt: js.UndefOr[String]    = js.native
  val neutralSecondary: js.UndefOr[String]     = js.native
  val neutralSecondaryAlt: js.UndefOr[String]  = js.native
  val neutralTertiary: js.UndefOr[String]      = js.native
  val neutralTertiaryAlt: js.UndefOr[String]   = js.native
  val neutralQuaternary: js.UndefOr[String]    = js.native
  val neutralQuaternaryAlt: js.UndefOr[String] = js.native
  val neutralLight: js.UndefOr[String]         = js.native
  //val neutralLighter:js.UndefOr[String] = js.native
  val neutralLighter: String                 = js.native
  val neutralLighterAlt: js.UndefOr[String]  = js.native
  val accent: js.UndefOr[String]             = js.native
  val white: js.UndefOr[String]              = js.native
  val whiteTranslucent40: js.UndefOr[String] = js.native
  val yellow: js.UndefOr[String]             = js.native
  val yellowLight: js.UndefOr[String]        = js.native
  val orange: js.UndefOr[String]             = js.native
  val orangeLight: js.UndefOr[String]        = js.native
  val orangeLighter: js.UndefOr[String]      = js.native
  val redDark: js.UndefOr[String]            = js.native
  val red: js.UndefOr[String]                = js.native
  val magentaDark: js.UndefOr[String]        = js.native
  val magenta: js.UndefOr[String]            = js.native
  val magentaLight: js.UndefOr[String]       = js.native
  val purpleDark: js.UndefOr[String]         = js.native
  val purple: js.UndefOr[String]             = js.native
  val purpleLight: js.UndefOr[String]        = js.native
  val blueDark: js.UndefOr[String]           = js.native
  val blueMid: js.UndefOr[String]            = js.native
  val blue: js.UndefOr[String]               = js.native
  val blueLight: js.UndefOr[String]          = js.native
  val tealDark: js.UndefOr[String]           = js.native
  val teal: js.UndefOr[String]               = js.native
  val tealLight: js.UndefOr[String]          = js.native
  val greenDark: js.UndefOr[String]          = js.native
  val green: js.UndefOr[String]              = js.native
  val greenLight: js.UndefOr[String]         = js.native
}

@js.native
trait IFontStyles extends js.Object {
  val tiny: js.UndefOr[IRawStyle]       = js.native
  val xSmall: js.UndefOr[IRawStyle]     = js.native
  val small: js.UndefOr[IRawStyle]      = js.native
  val smallPlus: js.UndefOr[IRawStyle]  = js.native
  val medium: js.UndefOr[IRawStyle]     = js.native
  val mediumPlus: js.UndefOr[IRawStyle] = js.native
  val large: js.UndefOr[IRawStyle]      = js.native
  val xLarge: js.UndefOr[IRawStyle]     = js.native
  val xxLarge: js.UndefOr[IRawStyle]    = js.native
  val superLarge: js.UndefOr[IRawStyle] = js.native
  val mega: js.UndefOr[IRawStyle]       = js.native
}

/** Color values, typically hex. */
@js.native
trait ISemanticTextColors extends js.Object {
  val bodyText: js.UndefOr[String]                  = js.native
  val bodyTextChecked: js.UndefOr[String]           = js.native
  val bodySubtext: js.UndefOr[String]               = js.native
  val actionLink: js.UndefOr[String]                = js.native
  val actionLinkHovered: js.UndefOr[String]         = js.native
  val link: js.UndefOr[String]                      = js.native
  val linkHovered: js.UndefOr[String]               = js.native
  val disabledText: js.UndefOr[String]              = js.native
  val disabledBodyText: js.UndefOr[String]          = js.native
  val disabledSubtext: js.UndefOr[String]           = js.native
  val errorText: js.UndefOr[String]                 = js.native
  val warningText: js.UndefOr[String]               = js.native
  val inputPlaceholderText: js.UndefOr[String]      = js.native
  val buttonText: js.UndefOr[String]                = js.native
  val buttonTextHovered: js.UndefOr[String]         = js.native
  val buttonTextChecked: js.UndefOr[String]         = js.native
  val buttonTextCheckedHovered: js.UndefOr[String]  = js.native
  val buttonTextPressed: js.UndefOr[String]         = js.native
  val buttonTextDisabled: js.UndefOr[String]        = js.native
  val primaryButtonText: js.UndefOr[String]         = js.native
  val primaryButtonTextHovered: js.UndefOr[String]  = js.native
  val primaryButtonTextPressed: js.UndefOr[String]  = js.native
  val primaryButtonTextDisabled: js.UndefOr[String] = js.native
  val listText: js.UndefOr[String]                  = js.native
}

@js.native
trait ISemanticColors extends ISemanticTextColors {
  val bodyBackground: js.UndefOr[String]                  = js.native
  val bodyStandoutBackground: js.UndefOr[String]          = js.native
  val bodyFrameBackground: js.UndefOr[String]             = js.native
  val bodyFrameDivider: js.UndefOr[String]                = js.native
  val bodyDivider: js.UndefOr[String]                     = js.native
  val disabledBackground: js.UndefOr[String]              = js.native
  val focusBorder: js.UndefOr[String]                     = js.native
  val variantBorder: js.UndefOr[String]                   = js.native
  val variantBorderHovered: js.UndefOr[String]            = js.native
  val defaultStateBackground: js.UndefOr[String]          = js.native
  val errorBackground: js.UndefOr[String]                 = js.native
  val blockingBackground: js.UndefOr[String]              = js.native
  val warningBackground: js.UndefOr[String]               = js.native
  val warningHighlight: js.UndefOr[String]                = js.native
  val successBackground: js.UndefOr[String]               = js.native
  val inputBorder: js.UndefOr[String]                     = js.native
  val smallInputBorder: js.UndefOr[String]                = js.native
  val inputBorderHovered: js.UndefOr[String]              = js.native
  val inputBackground: js.UndefOr[String]                 = js.native
  val inputBackgroundChecked: js.UndefOr[String]          = js.native
  val inputBackgroundCheckedHovered: js.UndefOr[String]   = js.native
  val inputForegroundChecked: js.UndefOr[String]          = js.native
  val inputFocusBorderAlt: js.UndefOr[String]             = js.native
  val buttonBackground: js.UndefOr[String]                = js.native
  val buttonBackgroundChecked: js.UndefOr[String]         = js.native
  val buttonBackgroundHovered: js.UndefOr[String]         = js.native
  val buttonBackgroundCheckedHovered: js.UndefOr[String]  = js.native
  val buttonBackgroundDisabled: js.UndefOr[String]        = js.native
  val buttonBackgroundPressed: js.UndefOr[String]         = js.native
  val buttonBorder: js.UndefOr[String]                    = js.native
  val buttonBorderDisabled: js.UndefOr[String]            = js.native
  val primaryButtonBackground: js.UndefOr[String]         = js.native
  val primaryButtonBackgroundHovered: js.UndefOr[String]  = js.native
  val primaryButtonBackgroundDisabled: js.UndefOr[String] = js.native
  val primaryButtonBorder: js.UndefOr[String]             = js.native
  val menuBackground: js.UndefOr[String]                  = js.native
  val menuDivider: js.UndefOr[String]                     = js.native
  val menuIcon: js.UndefOr[String]                        = js.native
  val menuHeader: js.UndefOr[String]                      = js.native
  val menuItemBackgroundHovered: js.UndefOr[String]       = js.native
  val menuItemBackgroundPressed: js.UndefOr[String]       = js.native
  val menuItemText: js.UndefOr[String]                    = js.native
  val menuItemTextHovered: js.UndefOr[String]             = js.native
  val listBackground: js.UndefOr[String]                  = js.native
  //val  listText: js.UndefOr[String] = js.native
  val listItemBackgroundHovered: js.UndefOr[String]        = js.native
  val listItemBackgroundChecked: js.UndefOr[String]        = js.native
  val listItemBackgroundCheckedHovered: js.UndefOr[String] = js.native
  val listHeaderBackgroundHovered: js.UndefOr[String]      = js.native
  val listHeaderBackgroundPressed: js.UndefOr[String]      = js.native
}

@js.native
trait IScheme extends js.Object {
  val palette: IPalette               = js.native
  val fonts: IFontStyles              = js.native
  val semanticColors: ISemanticColors = js.native
  val isInverted: Boolean             = js.native
}

@js.native
trait ITheme extends IScheme

@js.native
trait ThemeLike extends js.Object {
  /** Reurn the singleton theme object. */
  def getTheme(depComments: Boolean = false): ITheme          = js.native
  def registerOnThemeChangeCallback(cb: js.Function1[ITheme, Unit]): Unit = js.native
  def removeOnThemeChangeCallback(cb: js.Function1[ITheme, Unit]): Unit   = js.native
  def loadTheme(theme: ITheme, depComments: Boolean = false): ITheme                                    = js.native
  def createTheme(partial: ITheme, depComments: Boolean = false): ITheme                                = js.native
  val ThemeSettingName: js.UndefOr[String]                                = js.native
}
