// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
package components
package reactbigcalendar

import scala.scalajs.js
import js.annotation._
import js.|
import org.scalajs.dom

import vdom._

// Don't forget...and it will be dead-code removed if you don't reference it elsewhere
//@JSImport("react-big-calendar/lib/css/react-big-calendar.css", JSImport.Namespace)
// if you have odd settings on your import, use !! syntax to revert to defaults
//@JSImport("!!style-loader!css-loader!react-big-calendar/lib/css/react-big-calendar.css", JSImport.Namespace)
//object styles extends js.Object

object ReactBigCalendar {
  import ttg.react.elements.wrapJsForScala

  @js.native
  @JSImport("react-big-calendar", JSImport.Default)
  object JS extends ReactJsComponent {
    /** Keep untyped so you can use your own moment library. */
    def momentLocalizer(m: js.Any): Localizer = js.native
    /** Keep untyped so you can use your own library. */
    def globalizeLocalizer(globalizeInstance: js.Any): Localizer = js.native
  }

  def apply[E <: js.Any](props: Props[E] = null)(children: ReactNode*) =
    wrapJsForScala(JS, props, children:_*)

  /**
   * @tparam E Event object type 
   */
  trait Props[E <: js.Any] extends js.Object {
    // required
    var localizer: js.UndefOr[Localizer] = js.undefined
    var elementProps: js.UndefOr[js.Object] = js.undefined
    var date: js.UndefOr[js.Date] = js.undefined
    var defaultDate: js.UndefOr[js.Date] = js.undefined
    var view: js.UndefOr[Views] = js.undefined
    var defaultView: js.UndefOr[Views] = js.undefined
    var events: js.UndefOr[js.Array[E]] = js.undefined

    var titleAccessor: js.UndefOr[String] = js.undefined
    @JSName("titleAccessor")
    var titleAccessorThunk: js.UndefOr[js.Function1[js.Dynamic,String]] = js.undefined
    var tooltipAccessor: js.UndefOr[String] = js.undefined
    @JSName("tolltipAccessor")
    var tooltipAccessorThunk: js.UndefOr[js.Function1[js.Dynamic,String]] = js.undefined    
    var allDayAccessor: js.UndefOr[String] = js.undefined
    @JSName("allDayAccessor")
    var allDayAccessorThunk: js.UndefOr[js.Function1[js.Dynamic,Boolean]] = js.undefined    
    var startAccessor: js.UndefOr[String] = js.undefined
    @JSName("startAccessor")
    var startAccessorThunk: js.UndefOr[js.Function1[js.Dynamic,js.Date]] = js.undefined
    var endAccessor: js.UndefOr[String] = js.undefined
    @JSName("endAccessor")
    var endAccessorThunk: js.UndefOr[js.Function1[js.Dynamic,js.Date]] = js.undefined
    var resourceAccessor: js.UndefOr[String] = js.undefined
    @JSName("resourceAccessor")
    var resourceAccessorThunk: js.UndefOr[js.Function1[js.Dynamic, js.Any]] = js.undefined      
    var resources: js.UndefOr[js.Array[js.Object]] = js.undefined
    var resourceIdAccessor: js.UndefOr[String] = js.undefined
    @JSName("resourceIdAccessor")
    var resourceIdAccessorThunk: js.UndefOr[js.Function1[js.Dynamic, js.Any]] = js.undefined    
    var resourceTitleAccessor: js.UndefOr[String] = js.undefined
    @JSName("resourceTitleAccessor")
    var resourceTitleAccessorThunk: js.UndefOr[js.Function1[js.Dynamic, js.Any]] = js.undefined

    var getNow: js.UndefOr[js.Function0[js.Date]] = js.undefined

    var onNavigate: js.UndefOr[js.Function2[Navigate, js.Date, Unit]] = js.undefined
    var onView: js.UndefOr[js.Function1[Views, Unit]] = js.undefined
    var onDrillDown: js.UndefOr[js.Function2[js.Date, Views, Unit]] = js.undefined
    var onRangeChange: js.UndefOr[js.Function0[js.Dynamic]] = js.undefined
    var onSelectSlot: js.UndefOr[js.Function1[SlotInfo, js.Any]] = js.undefined
    var onSelectEvent: js.UndefOr[js.Function2[E, ReactEvent[dom.html.Element], Unit]] = js.undefined
    var onDoubleClickEvent: js.UndefOr[js.Function2[E, ReactEvent[dom.html.Element], Unit]] = js.undefined
    var onSelecting: js.UndefOr[Range] = js.undefined
    var selected: js.UndefOr[E] = js.undefined
    @JSName("views")
    var viewsList: js.UndefOr[js.Array[Views]] = js.undefined
    @JSName("views")
    var views: js.UndefOr[ViewsSpecifier] = js.undefined
    var drilldownView: js.UndefOr[Views] = js.undefined
    var getDrilldownView: js.UndefOr[js.Function3[js.Date, Views, js.Array[Views], String]] = js.undefined
    var length: js.UndefOr[Int] = js.undefined
    var popup: js.UndefOr[Boolean] = js.undefined
    var popupOffset: js.UndefOr[Int|Offset] = js.undefined
    var selectable: js.UndefOr[Boolean|String] = js.undefined
    var longPressThreshold: js.UndefOr[Int] = js.undefined
    var step: js.UndefOr[Int] = js.undefined
    var rtl: js.UndefOr[Boolean] = js.undefined
    var eventPropGetter: js.UndefOr[js.Function4[
      E, js.Date, js.Date, Boolean, PropGetterResult
    ]] = js.undefined
    var slotPropGetter: js.UndefOr[js.Function1[js.Date, PropGetterResult]] = js.undefined
    var dayPropGetter: js.UndefOr[js.Function1[js.Date, PropGetterResult]] = js.undefined
    var showMultiDayTimes: js.UndefOr[Boolean] = js.undefined
    var min: js.UndefOr[js.Date] = js.undefined
    var max: js.UndefOr[js.Date] = js.undefined
    var scrollToTime: js.UndefOr[js.Date] = js.undefined
    var culture: js.UndefOr[String] = js.undefined
    var formats: js.UndefOr[Format] = js.undefined
    var components: js.UndefOr[Components] = js.undefined
    var className: js.UndefOr[String] = js.undefined
    var messages: js.UndefOr[Messages] = js.undefined
  }

  /** Mixin for ReactJsComponent. Create a `ReactJsComponent` using
   * `wrapScalaForJs`. Then merge that value with an object with the methods in
   * this trait and cast to this type. Use the final result in `ViewSpecifier`.
   * You can use the individual types for casting a scala function. This is very
   * message.
   */
  @js.native
  trait ReactJsComponentEnh extends ReactJsComponent {
    val navigate: NavigateThunk = js.native
    val title: TitleThunk = js.native
  }

  type NavigateThunk = js.Function2[js.Date, Action, js.Date]
  type TitleThunk = js.Function2[js.Date, js.Dynamic, js.Date]

  trait ViewsSpecifier extends js.Object {
    var month: js.UndefOr[Boolean|ReactJsComponentEnh] = js.undefined
    var week: js.UndefOr[Boolean|ReactJsComponentEnh] = js.undefined
    var workWeek: js.UndefOr[Boolean|ReactJsComponentEnh] = js.undefined
    var day: js.UndefOr[Boolean|ReactJsComponentEnh] = js.undefined
    var agenda: js.UndefOr[Boolean|ReactJsComponentEnh] = js.undefined
  }

  @js.native
  trait SlotInfo extends js.Object {
    val start: js.Date = js.native
    val end: js.Date = js.native
    val slots: js.Array[js.Date] = js.native
    val action: Action = js.native
    val bounds: js.UndefOr[Bounds] = js.native
    val box: js.UndefOr[Box] = js.native
  }

  @js.native
  trait Box extends js.Object {
    val clientX: Double = js.native
    val clientY: Double = js.native
    val x: Double = js.native
    val y: Double = js.native
  }

  @js.native
  trait Bounds extends js.Object {
    val x: Double = js.native
    val y: Double = js.native
    val top: Double = js.native
    val bottom: Double = js.native
    val right: Double = js.native
    val left: Double = js.native
  }

  @js.native
  sealed trait Action extends js.Any
  object Action {
    val select = "select".asInstanceOf[Action]
    val click = "click".asInstanceOf[Action]
    val doubleClick = "doubleClick".asInstanceOf[Action]
  }

  trait PropGetterResult extends js.Object {
    var className: js.UndefOr[String] = js.undefined
    var style: js.UndefOr[js.Object] = js.undefined
  }

  trait Offset extends js.Object {
    var x: Int
    var y: Int
  }

  @js.native
  trait Range extends js.Object {
    var start: js.Date
    var stop: js.Date
  }

  @js.native
  trait Localizer extends js.Any

  @js.native
  sealed trait Views extends js.Any
  object Views {
    val month = "month".asInstanceOf[Views]
    val week = "week".asInstanceOf[Views]
    val workWeek = "work_week".asInstanceOf[Views]
    val day = "day".asInstanceOf[Views]
    val agenda = "agenda".asInstanceOf[Views]
  }

  @js.native
  sealed trait Navigate extends js.Any
  object Navigate {
    val PREVIOUS = "PREV".asInstanceOf[Navigate]
    val NEXT = "NEXT".asInstanceOf[Navigate]
    val TODAY = "TODAY".asInstanceOf[Navigate]
    val DATE = "DATE".asInstanceOf[Navigate]
  }

  /** String formats for stringifying dates. */
  trait Format extends js.Object {
    var dateFormat: js.UndefOr[String] = js.undefined
    var dayFormat: js.UndefOr[String] = js.undefined
    var weekdayFormat: js.UndefOr[String] = js.undefined
    var timeGutterFormat: js.UndefOr[String] = js.undefined
    var monthHeaderFormat: js.UndefOr[String] = js.undefined
    var dayRangeHeaderFormat: js.UndefOr[String] = js.undefined
    var dayHeaderFormat: js.UndefOr[String] = js.undefined
    var agendaHeaderFormat: js.UndefOr[String] = js.undefined
    var selectRangeFormat: js.UndefOr[String] = js.undefined
    var agendaDateFormat: js.UndefOr[String] = js.undefined
    var agendaTimeFormat: js.UndefOr[String] = js.undefined
    var agendaTimeRangeFormat: js.UndefOr[String] = js.undefined
    var eventTimeRangeFormat: js.UndefOr[String] = js.undefined
    var eventTimeRangeStartFormat: js.UndefOr[String] = js.undefined
    var eventTimeRangeEndFormat: js.UndefOr[String] = js.undefined
  }

  trait Messages extends js.Object {
    var allDay: js.UndefOr[ReactNode] = js.undefined
    var previous: js.UndefOr[ReactNode] = js.undefined
    var next: js.UndefOr[ReactNode] = js.undefined
    var today: js.UndefOr[ReactNode] = js.undefined
    var month: js.UndefOr[ReactNode] = js.undefined
    var week: js.UndefOr[ReactNode] = js.undefined
    var day: js.UndefOr[ReactNode] = js.undefined
    var agenda: js.UndefOr[ReactNode] = js.undefined
    var date: js.UndefOr[ReactNode] = js.undefined
    var time: js.UndefOr[ReactNode] = js.undefined
    var event: js.UndefOr[ReactNode] = js.undefined
    var showMore: js.UndefOr[ReactNode] = js.undefined
  }

  trait Agenda extends js.Object {
    var date: js.UndefOr[ReactJsComponent] = js.undefined
    var time: js.UndefOr[ReactJsComponent] = js.undefined
    var event: js.UndefOr[ReactJsComponent] = js.undefined
  }

  trait Day extends js.Object {
    var header: js.UndefOr[ReactJsComponent] = js.undefined
    var event: js.UndefOr[ReactJsComponent] = js.undefined
  }

  trait Week extends js.Object {
    var header: js.UndefOr[ReactJsComponent] = js.undefined
    var event: js.UndefOr[ReactJsComponent] = js.undefined
  }

  trait Month extends js.Object {
    var header: js.UndefOr[ReactJsComponent] = js.undefined
    var dateHeader: js.UndefOr[ReactJsComponent] = js.undefined
    var event: js.UndefOr[ReactJsComponent] = js.undefined
  }

  trait Components extends js.Object {
    var event: js.UndefOr[ReactJsComponent] = js.undefined
    var eventWrapper: js.UndefOr[ReactJsComponent] = js.undefined
    var eventContainerWrapper: js.UndefOr[ReactJsComponent] = js.undefined
    var dayWrapper: js.UndefOr[ReactJsComponent] = js.undefined
    var dateCellWrapper: js.UndefOr[ReactJsComponent] = js.undefined
    var timesSlotWrapper: js.UndefOr[ReactJsComponent] = js.undefined
    var timesGutterHeader: js.UndefOr[ReactJsComponent] = js.undefined
    var toolbar: js.UndefOr[ReactJsComponent] = js.undefined
    var agenda: js.UndefOr[Agenda] = js.undefined
    var day: js.UndefOr[Day] = js.undefined
    var week: js.UndefOr[Week] = js.undefined
    var month: js.UndefOr[Month] = js.undefined
  }

}
