// Copyright (c) 2019 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package fabric
package utilities

import scala.scalajs.js
import js.|
import js.annotation._
import js.Dynamic.{literal => lit}
import react.vdom._
import js.JSConverters._

// this is very confusing...this is not @uifabric/utilities
// but office-ui-fabric-react/utilities

@js.native
@JSImport("office-ui-fabric-react/utilities/dateMath", JSImport.Namespace)
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
@JSImport("office-ui-fabric-react/utilities/colors", JSImport.Namespace)
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
