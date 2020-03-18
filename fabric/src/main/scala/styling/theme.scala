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
trait IScheme extends js.Object {
  val rtl: js.UndefOr[Boolean] = js.native
  val palette: IPalette               = js.native
  val fonts: IFontStyles              = js.native
  val semanticColors: ISemanticColors = js.native
  val isInverted: Boolean             = js.native
  val disableGlobalClassNames: Boolean = js.native
  val spacing: ISpacing = js.native
  val effects: IEffects = js.native
}

@js.native
trait ITheme extends IScheme {
  val schemas: js.UndefOr[js.Dictionary[IScheme]] = js.native
}

@js.native
trait ThemeLike extends js.Object {

  /** Reurn the singleton theme object. */
  def getTheme(depComments: Boolean = false): ITheme                      = js.native
  def registerOnThemeChangeCallback(cb: js.Function1[ITheme, Unit]): Unit = js.native
  def removeOnThemeChangeCallback(cb: js.Function1[ITheme, Unit]): Unit   = js.native
  def loadTheme(theme: ITheme, depComments: Boolean = false): ITheme      = js.native
  def createTheme(partial: ITheme, depComments: Boolean = false): ITheme  = js.native
  val ThemeSettingName: String                                            = js.native
}
