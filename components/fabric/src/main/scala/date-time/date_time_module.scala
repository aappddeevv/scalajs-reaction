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
package date_time

import scala.scalajs.js
import js.|
import js.annotation._
import react._
import react.implicits._
import org.scalajs.dom
import vdom._
import fabric._
import fabric.styling._
import fabric.components._

object Calendar {
@js.native
  @JSImport("@uifabric/date-time/lib", "Calendar")
  object JS extends ReactJSComponent

  @js.native
  trait ICalendar extends js.Object {
    def focus(): Unit = js.native
  }
  
  trait Props extends js.Object {
  
  }
  
  def apply(props: Props) = createElement0(JS, props)
  
  trait StyleProps extends js.Object {
  }
  
  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
    var divider: js.UndefOr[IStyle] = js.undefined
    var goTodayButton: js.UndefOr[IStyle] = js.undefined
    var monthPickerWrapper: js.UndefOr[IStyle] = js.undefined
    var liveRegion: js.UndefOr[IStyle] = js.undefined
  }
  
  trait IconStrings extends js.Object {
    var leftNavigation: js.UndefOr[String] = js.undefined
    var rightNavigation: js.UndefOr[String] = js.undefined
    var closeIcon: js.UndefOr[String] = js.undefined
  }
  
  @js.native
  abstract trait AnimationDirection extends js.Any
  object AnimationDirection {
    val Horizontal = 0.asInstanceOf[AnimationDirection]
    val Vertical = 1.asInstanceOf[AnimationDirection]
  }
   
  trait CalendarStrings extends js.Object {
    var months: js.UndefOr[js.Array[String]] = js.undefined
    var shortMonths: js.UndefOr[js.Array[String]] = js.undefined
    var days: js.UndefOr[js.Array[String]] = js.undefined
    var shortDays: js.UndefOr[js.Array[String]] = js.undefined
    var gotoToday: js.UndefOr[String] = js.undefined
    var prevMonthAriaLabel: js.UndefOr[String] = js.undefined
    var nextMonthAriaLabel: js.UndefOr[String] = js.undefined
    var prevYearAriaLabel: js.UndefOr[String] = js.undefined
    var nextYearAriaLabel: js.UndefOr[String] = js.undefined
    var prevYearRangeAriaLabel: js.UndefOr[String] = js.undefined
    var nextYearRangeAriaLabel: js.UndefOr[String] = js.undefined
    var monthPickerHeaderAriaLabel: js.UndefOr[String] = js.undefined
    var yearPickerHeaderAriaLabel: js.UndefOr[String] = js.undefined
    var closeButtonAriaLabel: js.UndefOr[String] = js.undefined
    var weekNumberFormatString: js.UndefOr[String] = js.undefined
    var selectedDateFormatString: js.UndefOr[String] = js.undefined
    var todayDateFormatString: js.UndefOr[String] = js.undefined
  }
}

object DatePicker {
  @js.native
  @JSImport("@uifabric/date-time/lib", "DatePicker")
  object JS extends ReactJSComponent

  @js.native
  trait IDatePicker extends js.Object {
    def focus(): Unit = js.native
    def reset(): Unit = js.native
  }

  trait Props extends ComponentRef[IDatePicker] with HTMLAttributes[dom.html.Div] with ReactJSProps with HasTheme {
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
    var calloutProps: js.UndefOr[CalloutProps] = js.undefined
    var calendarProps: js.UndefOr[Calendar.Props] = js.undefined
    var textField: js.UndefOr[TextField.Props] = js.undefined
    //var calendarAs
    var onSelectDate: js.UndefOr[js.Function1[js.UndefOr[js.Date | Null], Unit]] = js.undefined
    var label: js.UndefOr[String] = js.undefined
    //var disabled
    //var ariaLabel
    var underlined: js.UndefOr[Boolean] = js.undefined
    var pickerAriaLabel: js.UndefOr[String] = js.undefined
    var isMonthPickeVisible: js.UndefOr[Boolean] = js.undefined
    var showMonthPickerAsOverlay: js.UndefOr[Boolean] = js.undefined
    var allowTextInput: js.UndefOr[Boolean] = js.undefined
    var disableAutoFocus: js.UndefOr[Boolean] = js.undefined
    var placeholder: js.UndefOr[String] = js.undefined
    var today: js.UndefOr[js.Date] = js.undefined
    var value: js.UndefOr[js.Date] = js.undefined
    var formatDate: js.UndefOr[js.Function1[js.UndefOr[js.Date], String]] = js.undefined
    var parseDateFromString: js.UndefOr[js.Function1[String, js.Date | Null]] = js.undefined
    // DayOfWeek
    var firstDayOfWeek: js.UndefOr[Int] = js.undefined
    var strings: js.UndefOr[DatePickerStrings] = js.undefined
    var highlightCurrentMonth: js.UndefOr[Boolean] = js.undefined
    var highlightSelectedMonth: js.UndefOr[Boolean] = js.undefined
    var showWeekNumbers: js.UndefOr[Boolean] = js.undefined
    // FirstWeekOfYear
    var firstWeekOfYear: js.UndefOr[js.Any] = js.undefined
    var showGoToToday: js.UndefOr[Boolean] = js.undefined
    var borderless: js.UndefOr[Boolean] = js.undefined
    // className
    //dateTimeFormatter
    var minDate: js.UndefOr[js.Date] = js.undefined
    var maxDate: js.UndefOr[js.Date] = js.undefined
    var initialPickerDate: js.UndefOr[js.Date] = js.undefined
    var allowFocusable: js.UndefOr[Boolean] = js.undefined
    var onAfterMenuDismiss: js.UndefOr[js.Function0[Unit]] = js.undefined
    var showCloseButton: js.UndefOr[Boolean] = js.undefined
    //var tabIndex: js.UndefOr[Int] = js.undefined
  }

  def apply(props: Props) = createElement0(JS, props)

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
    var textField: js.UndefOr[IStyle] = js.undefined
    var callout: js.UndefOr[IStyle] = js.undefined
    var icon: js.UndefOr[IStyle] = js.undefined
  }

  trait StyleProps extends HasTheme {
    var className: js.UndefOr[String] = js.undefined
    //disabled
    //label
    //isDatePickerShown
  }

  trait DatePickerStrings extends js.Object {
    var isRequiredErrorMessage: js.UndefOr[String] = js.undefined
    var invalidInputErrorMessage: js.UndefOr[String] = js.undefined
    var isOutOfBoundsErrorMessage: js.UndefOr[String] = js.undefined
  }

}

object WeeklyDatePicker {}

object CalendarDayGrid {}
