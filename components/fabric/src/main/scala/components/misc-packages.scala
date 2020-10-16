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
package components
package utilities

import scala.scalajs.js

import js.annotation._

// this is very confusing...this is not @uifabric/utilities
// but office-ui-fabric-react/utilities

@js.native
@JSImport("office-ui-fabric-react/lib/utilities/dateMath", JSImport.Namespace)
object dateMath extends js.Object {
  def addDays(d: js.Date, days: Double): js.Date = js.native
  def addWeeks(d: js.Date, weeks: Double): js.Date = js.native
  def addMonths(d: js.Date, months: Double): js.Date = js.native
  def addYears(d: js.Date, years: Double): js.Date = js.native
  def getMonthStart(d: js.Date): js.Date = js.native
  def getMonthEnd(d: js.Date): js.Date = js.native
  def getYearStart(d: js.Date): js.Date = js.native
  def getYearEnd(d: js.Date): js.Date = js.native
  def setMonth(d: js.Date, month: Int): js.Date = js.native
  def compareDates(d1: js.Date, d2: js.Date): Int = js.native
  def compareDateParts(d1: js.Date, d2: js.Date): Int = js.native
  //def getDateRange
  def isInDateRange(d1: js.Date, range: js.Array[js.Date]): Boolean = js.native
  //def getWeekNumberInMonth
  //def getWeekNumber
  def getDatePart(d: js.Date): js.Date = js.native
}
@js.native
@JSImport("office-ui-fabric-react/lib/utilities/colors", JSImport.Namespace)
object colors extends js.Object {
  //def cssColor
  //def rgb2hex
  //def hsv2hex
  //def rgb2hsv
  //def hsl2hsv
  //def hsv2hsl
  //def hsl2rgb
  //def hsv2rgb
  //def getColorFromString(v: String): js.UndefOr[IColor] = js.native
  // more...
}
