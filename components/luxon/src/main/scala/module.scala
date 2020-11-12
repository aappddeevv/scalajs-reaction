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

package luxon

import scala.scalajs.js
import js.|
import js.annotation._
import react._

trait LocaleInfo extends js.Object {}
trait Zone extends js.Object {}

@js.native
abstract trait DurationUnit extends js.Any
object DurationUnit {
  val year = "year".asInstanceOf[DurationUnit]
  val years = "years".asInstanceOf[DurationUnit]
  val quarter = "quarter".asInstanceOf[DurationUnit]
  val quarters = "quarters".asInstanceOf[DurationUnit]
  val month = "month".asInstanceOf[DurationUnit]
  val months = "months".asInstanceOf[DurationUnit]
  val week = "week".asInstanceOf[DurationUnit]
  val weeks = "weeks".asInstanceOf[DurationUnit]
  val day = "day".asInstanceOf[DurationUnit]
  val days = "days".asInstanceOf[DurationUnit]
  val hour = "hour".asInstanceOf[DurationUnit]
  val hours = "hours".asInstanceOf[DurationUnit]
  val minute = "minute".asInstanceOf[DurationUnit]
  val minutes = "minutes".asInstanceOf[DurationUnit]
  val second = "second".asInstanceOf[DurationUnit]
  val seconds = "seconds".asInstanceOf[DurationUnit]
  val millisecond = "millisecond".asInstanceOf[DurationUnit]
  val milliseconds = "milliseconds".asInstanceOf[DurationUnit]
}
@jsenrich trait DurationInit extends js.Object {
  var days: js.UndefOr[Double] = js.undefined
  var months: js.UndefOr[Double] = js.undefined
  var quarters: js.UndefOr[Double] = js.undefined
  var years: js.UndefOr[Double] = js.undefined
  var weeks: js.UndefOr[Double] = js.undefined
  var hours: js.UndefOr[Double] = js.undefined
  var minutes: js.UndefOr[Double] = js.undefined
  var seconds: js.UndefOr[Double] = js.undefined
  var milliseconds: js.UndefOr[Double] = js.undefined
  var locale: js.UndefOr[String] = js.undefined
  var numberingSystem: js.UndefOr[String] = js.undefined
  var conversionAccuracy: js.UndefOr[String] = js.undefined
}

@js.native
trait Duration extends js.Object {
  val days: Double = js.native
  val months: Double = js.native
  val quarters: Double = js.native
  val years: Double = js.native
  val weeks: Double = js.native
  val hours: Double = js.native
  val minutes: Double = js.native
  val seconds: Double = js.native
  def as(unit: DurationUnit|String): Double = js.native
  def get(unit: DurationUnit|String): Double = js.native
  def mapUnits(fn: js.Function1[Duration, Duration]): Duration = js.native
  def equals(that: Duration): Boolean = js.native
  def toObject(): DurationInit = js.native
  def toISO(): String = js.native
  def toJSON(): String = js.native
//   def toString(): String = js.native
  @JSName("valueOf")
  def valueOfNumber(): Double = js.native
  def negate(): Duration = js.native
  def plus(that: Duration): Duration = js.native
  def minus(that: Duration): Duration = js.native
  def shiftTo(units: js.Array[DurationUnit]*): Duration = js.native
  def fromISO(text: String, options: DurationOptions): Duration = js.native
  val isValid: Boolean = js.native
  val invalidExplanation: String | Null = js.native
  val invalidReason: String | Null = js.native
  val locale: String = js.native
  val numberingSystem: String = js.native
}

@js.native
abstract trait ConversionAccuracy extends js.Any
object ConversionAccuracy {
  val casual = "casual".asInstanceOf[ConversionAccuracy]
  val longterm = "longterm".asInstanceOf[ConversionAccuracy]
}

@jsenrich trait DurationOptions extends js.Object {
  var locale: js.UndefOr[String] = js.undefined
  var numberingSystem: js.UndefOr[String] = js.undefined
  var conversionAccuracy: js.UndefOr[ConversionAccuracy] = js.undefined
}

@jsenrich trait DiffOptions extends js.Object {
  var conversionAccuracy: js.UndefOr[ConversionAccuracy] = js.undefined
}

@js.native
@JSImport("luxon", "Duration")
object Duration extends js.Object {
  def fromISO(text: String, options: js.UndefOr[DurationOptions] = js.undefined): Duration = js.native
  def fromMillis(count: Double, options: js.UndefOr[DurationOptions] = js.undefined): Duration = js.native
  def fromObject(arg: DurationInit | js.Dynamic): Duration = js.native
  def invalid(reason: String, explanation: String): Duration = js.native
  def isDuration(o: js.Object): Boolean = js.native
  def isDUration(o: js.Any): Boolean = js.native
}

@jsenrich trait DateTimeUnits extends js.Object {
  var year: js.UndefOr[Int] = js.undefined
  var month: js.UndefOr[Int] = js.undefined
  var day: js.UndefOr[Int] = js.undefined
  var ordinal: js.UndefOr[Int] = js.undefined
  var weekYear: js.UndefOr[Int] = js.undefined
  var weekNumber: js.UndefOr[Int] = js.undefined
  var weekday: js.UndefOr[Int] = js.undefined
  var hour: js.UndefOr[Int] = js.undefined
  var minute: js.UndefOr[Int] = js.undefined
  var second: js.UndefOr[Int] = js.undefined
  var millisecond: js.UndefOr[Int] = js.undefined
}

@jsenrich trait DateTimeInit extends js.Object {
  var year: js.UndefOr[Int] = js.undefined
  var month: js.UndefOr[Int] = js.undefined
  var day: js.UndefOr[Int] = js.undefined
  var ordinal: js.UndefOr[Int] = js.undefined
  var weekYear: js.UndefOr[Int] = js.undefined
  var weekNumber: js.UndefOr[Int] = js.undefined
  var weekday: js.UndefOr[Int] = js.undefined
  var hour: js.UndefOr[Int] = js.undefined
  var minute: js.UndefOr[Int] = js.undefined
  var second: js.UndefOr[Int] = js.undefined
  var millisecond: js.UndefOr[Int] = js.undefined

  var zone: js.UndefOr[String] = js.undefined
  var locale: js.UndefOr[String] = js.undefined
  var numberingSystem: js.UndefOr[String] = js.undefined
  var outputCalendar: js.UndefOr[String] = js.undefined
}

@jsenrich trait ToISOTimeOptions extends js.Object {
  var suppressMilliseconds: js.UndefOr[Boolean] = js.undefined
  var suppressSeconds: js.UndefOr[Boolean] = js.undefined
  var includeOffset: js.UndefOr[Boolean] = js.undefined
  var format: js.UndefOr[String] = js.undefined
}

@js.native
trait DateTime extends js.Object {
  val isValid: Boolean = js.native
  val invalidReason: String | Null = js.native
  val invalidExplanation: String | Null = js.native
  val year: Int = js.native
  val quarter: Int = js.native
  val month: Int = js.native
  val day: Int = js.native
  val second: Int = js.native
  val weekday: Int = js.native
  val weekNumber: Int = js.native
  val weekYear: Int = js.native
  val weekdayLong: String = js.native
  val weekdayShort: String = js.native
  val weeksInWeekYear: Int = js.native
  val millisecond: Int = js.native
  val zoneName: String = js.native
  val zone: Zone = js.native
  val offset: Int = js.native
  val daysInMonth: Int = js.native
  val daysInYear: Int = js.native
  val isInDST: Boolean = js.native
  val isInLeapYear: Boolean = js.native
  val isOffsetFixed: Boolean = js.native
  val locale: String = js.native
  val monthLong: String = js.native
  val monthShort: String = js.native
  val numberingSystem: String = js.native
  val offsetNameLong: String = js.native
  val offsetNameShort: String = js.native
  val ordinal: Int = js.native
  val outputCalendar: String = js.native
  def plus(dur: Duration | DurationInit | Double): DateTime = js.native
  def minus(dur: Duration | DurationInit | Double): DateTime = js.native
  def startOf(part: DurationUnit): DateTime = js.native
  def endOf(part: DurationUnit): DateTime = js.native
  def set(parts: DateTimeUnits): DateTime = js.native
  def get(unit: String): Int = js.native
  def hasSame(that: DateTime, unit: String): Boolean = js.native
  def diff(
    other: DateTime,
    unit: js.UndefOr[DurationUnit | js.Array[DurationUnit]] = js.undefined,
    options: js.UndefOr[DiffOptions] = js.undefined): Duration = js.native
  def diffNow(
    unit: js.UndefOr[DurationUnit | js.Array[DurationUnit]] = js.undefined,
    options: js.UndefOr[DiffOptions] = js.undefined): Duration = js.native
  def setLocale(loc: String): DateTime = js.native
  def setZone(zone: String): DateTime = js.native
  def toBSON(): js.Date = js.native
  def toUTC(): DateTime = js.native
  def toObject(): DateTimeUnits = js.native
  def toFormat(fmt: String): String = js.native
  def toHTTP(): String = js.native
  def toISO(options: js.UndefOr[ToISOTimeOptions] = js.undefined): String = js.native
  def toISODate(): String = js.native
  def toISOTime(options: js.UndefOr[ToISOTimeOptions] = js.undefined): String = js.native
  def toISOWeekDate(): String = js.native
  def toJSDate(): js.Date = js.native
  def toJSON(): String = js.native
  def toLocal(): DateTime = js.native
  def toLocaleString(f: js.UndefOr[LocaleInfo] = js.undefined): String = js.native
  def toMillis(): Double = js.native
  def toRFC2822(): String
  def toRelative(): String = js.native
  def toRelativeCalendar(): String = js.native
  def toSQL(options: js.UndefOr[DateTimeOptions] = js.undefined): String = js.native
  def toSQLDate(): String = js.native
  def toSQLTime(): String = js.native
  def toSeconds(): Double = js.native
//   def toString(): String = js.native
  def toUTC(offset: Int, options: js.UndefOr[DateTimeOptions] = js.undefined): DateTime = js.native
  def until(other: DateTime): Interval = js.native
  def equals(that: DateTime): Boolean = js.native
  //def valueOf(): Double = js.native
}

@jsenrich trait SQLOptions extends js.Object {
  var zone: js.UndefOr[Zone | String] = js.undefined
  var setZone: js.UndefOr[Boolean] = js.undefined
  val locale: js.UndefOr[String] = js.undefined
  var numberingSystem: js.UndefOr[String] = js.undefined
  var outputCalendar: js.UndefOr[String] = js.undefined
}

@jsenrich trait DateTimeOptions extends js.Object {
  var includeZone: js.UndefOr[Boolean] = js.undefined
  var includeOffset: js.UndefOr[Boolean] = js.undefined
}

@js.native
@JSImport("luxon", "DateTime")
object DateTime extends js.Object {
  def local(): DateTime = js.native
  def local(
    year: js.UndefOr[Int] = js.undefined,
    month: js.UndefOr[Int] = js.undefined,
    day: js.UndefOr[Int] = js.undefined,
    hour: js.UndefOr[Int] = js.undefined,
    min: js.UndefOr[Int] = js.undefined,
    second: js.UndefOr[Int] = js.undefined,
    millisecond: js.UndefOr[Int] = js.undefined
  ): DateTime = js.native
  def fromObject(dt: DateTimeInit): DateTime = js.native
  def fromRFC2822(text: String, options: js.UndefOr[DateTimeOptions] = js.undefined): DateTime = js.native
  def fromSQL(text: String, options: js.UndefOr[SQLOptions] = js.undefined): DateTime = js.native
  def fromJSDate(dt: js.Date, options: js.UndefOr[DateTimeOptions] = js.undefined): DateTime = js.native
  def fromISO(arg: String, options: js.UndefOr[DateTimeOptions] = js.undefined): DateTime = js.native
  def utc(): DateTime = js.native
  @JSName("utc")
  def utcFrom(
    year: js.UndefOr[Int] = js.undefined,
    month: js.UndefOr[Int] = js.undefined,
    day: js.UndefOr[Int] = js.undefined,
    hour: js.UndefOr[Int] = js.undefined,
    minute: js.UndefOr[Int] = js.undefined,
    second: js.UndefOr[Int] = js.undefined,
    millisecond: js.UndefOr[Int] = js.undefined
  ): DateTime = js.native
  def fromHTTP(arg: String): DateTime = js.native
  def fromMillis(arg: Double): DateTime = js.native
  def fromInvalid(reason: String, explanation: String): DateTime = js.native
  def fromSeconds(arg: Double, options: js.UndefOr[DateTimeOptions] = js.undefined): DateTime = js.native
  def fromFormat(arg: String, format: String, options: js.UndefOr[DateTimeOptions] = js.undefined): DateTime =
    js.native
  def fromFormatExplain(arg: String, format: String, options: js.UndefOr[DateTimeOptions] = js.undefined): js.Object =
    js.native
  def isDateTime(o: js.Any): Boolean = js.native
  def max(values: DateTime*): DateTime = js.native
  def min(values: DateTime*): DateTime = js.native
  val DATETIME_FULL: LocaleInfo = js.native
  val DATETIME_FULL_WITH_SECONDS: LocaleInfo = js.native
  val DATETIME_HUGE: LocaleInfo = js.native
  val DATETIME_HUGE_WITH_SECONDS: LocaleInfo = js.native
  val DATETIME_MED: LocaleInfo = js.native
  val DATETIME_MED_WITH_SECONDS: LocaleInfo = js.native
  val DATETIME_MED_WITH_WEEKDAY: LocaleInfo = js.native
  val DATETIME_SHORT: LocaleInfo = js.native
  val DATETIME_SHORT_WITH_SECONDS: LocaleInfo = js.native
  val DATE_FULL: LocaleInfo = js.native
  val DATE_MED: LocaleInfo = js.native
  val TIME_24_SIMPLE: LocaleInfo = js.native
  val TIME_24_WITH_LONG_OFFSET: LocaleInfo = js.native
  val TIME_24_WITH_SECONDS: LocaleInfo = js.native
  val TIME_24_WITH_SHORT_OFFSET: LocaleInfo = js.native
  val TIME_SIMPLE: LocaleInfo = js.native
  val TIME_WITH_LONG_OFFSET: LocaleInfo = js.native
  val TIME_WITH_SECONDS: LocaleInfo = js.native
  val TIME_WITH_SHORT_OFFSET: LocaleInfo = js.native
}

@jsenrich trait IntervalInit extends js.Object {
  var start: js.UndefOr[DateTime] = js.undefined
  var stop: js.UndefOr[DateTime] = js.undefined
}

@js.native
trait Interval extends js.Object {
  val end: DateTime = js.native
  val start: DateTime = js.native
  val isValid: Boolean = js.native
  val invalidExplanation: String | Null = js.native
  val invalidReason: String | Null = js.native
  def abutsEnd(that: Interval): Boolean = js.native
  def abutsStart(that: Interval): Boolean = js.native
  def contains(dt: DateTime): Boolean = js.native
  def count(unit: DurationUnit): Int = js.native
  def difference(intervals: Interval*): js.Array[Interval] = js.native
  def divideEqually(nparts: Int): js.Array[Interval] = js.native
  def engulfs(other: Interval): Boolean = js.native
  def equals(that: Interval): Boolean = js.native
  def hasSame(unit: DurationUnit): Boolean = js.native
  def intersection(that: Interval): Interval = js.native
  def isAfter(dt: DateTime): Boolean = js.native
  def isBefore(dt: DateTime): Boolean = js.native
  def isEmpyt(): Boolean = js.native
  def length(unit: js.UndefOr[DurationUnit] = js.undefined): Double = js.native
  @JSName("length")
  def lengthOf(part: String): Int = js.native
  //def mapEndpoints
  def overlaps(other: Interval): Boolean = js.native
  def set(values: IntervalInit): Boolean = js.native
  def splitAt(dts: js.Array[DateTime]*): js.Array[Interval] = js.native
  def splitBy(duration: Duration | DurationInit | Int): js.Array[Interval] = js.native
  def toDuration(
    unit: DurationUnit | js.Array[DurationUnit],
    options: js.UndefOr[IntervalOptions] = js.undefined): Duration =
    js.native
  def toFormat(dateFromat: String, options: js.UndefOr[IntervalOptions] = js.undefined): String = js.native
  def toISO(options: js.UndefOr[IntervalOptions] = js.undefined): String = js.native
  def toISODate(): String = js.native
  def toISOTime(): String = js.native
  //def toString(): String = js.native
  def union(other: Interval): Interval = js.native
}

trait IntervalOptions extends js.Object {}

@js.native
@JSImport("luxon", "Interval")
object Interval extends js.Object {
  def after(start: DateTime | js.Date | DateTimeUnits, duration: Duration | DurationInit | Double): Interval = js.native
  def before(end: DateTime | js.Date | DateTimeUnits, duration: Duration | DurationInit | Double): Interval = js.native
  def fromDateTimes(start: DateTime | js.Date | DateTimeInit, end: DateTime | js.Date | DateTimeInit): Interval =
    js.native
  def invalid(reason: String, explanation: String): Interval = js.native
  def isInterval(o: js.Any): Boolean = js.native
  def fromISO(text: String, options: js.UndefOr[IntervalOptions] = js.undefined): Interval = js.native
  def merge(first: Interval, second: Interval): Interval = js.native
  // xor

}

@js.native
@JSImport("luxon", "Settings")
object Settings extends js.Object {
  var defaultLocale: String = js.native
  var defaultNumberingSystem: String = js.native
  var defaultOutputCalendar: String = js.native
  var defaultZone: String = js.native
  var defaultZoneName: String = js.native
  // return epoch ms count
  var now: js.Function0[Double] = js.native
  var throwOnInvalid: Boolean = js.native
  def resetCaches(): Unit = js.native
}

trait InfoOptions extends js.Object {
  var locale: js.UndefOr[String] = js.undefined
  var numberingSystem: js.UndefOr[String] = js.undefined
  var outputCalendar: js.UndefOr[String] = js.undefined
}

@js.native
trait Features extends js.Object {
  val intl: Boolean = js.native
  val intlTokens: Boolean = js.native
  val zones: Boolean = js.native
  val relative: Boolean = js.native
}

@js.native
trait Indicator extends js.Any
object Indicator {
  val short = "short".asInstanceOf[Indicator]
  val numeric = "numeric".asInstanceOf[Indicator]
  val long = "long".asInstanceOf[Indicator]
}

@js.native
@JSImport("luxon", "Info")
object Info extends js.Object {
  def features(): Features = js.native
  //def hasDST
  def isValidIANAZone(zone: String): Boolean = js.native
  //def meridiems()
  def months(
    length: js.UndefOr[Indicator | String] = js.undefined,
    options: js.UndefOr[InfoOptions] = js.undefined
  ): js.Array[String] = js.native
  //def normalizeZone
  def weekdays(
    length: js.UndefOr[Indicator | String] = js.undefined,
    options: js.UndefOr[InfoOptions] = js.undefined
  ): js.Array[String] = js.native
  def weekdaysFormat(
    length: js.UndefOr[Indicator | String] = js.undefined,
    options: js.UndefOr[InfoOptions] = js.undefined
  ): js.Array[String] = js.native

}
