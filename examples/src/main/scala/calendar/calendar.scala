// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
package examples

import scala.scalajs.js
import js.annotation._
import js.Dynamic.{literal => lit}

import ttg.react.components.reactbigcalendar._
import moment._

package object calendar {

  val mlocalizer = ReactBigCalendar.JS.momentLocalizer(moment.Moment)

  val sampleEvents = js.Array[js.Dynamic](
    lit (
      "title" -> "lunch with fred",
      "timestart" -> Moment().hour(12).minute(0).toDate(),
      "end" -> Moment().hour(12).add(1, "hour").toDate(),
      "allDay" -> false,
      "resource" -> null
    ),
    lit (
      "title" -> "meet with sally",
      "timestart" -> Moment().hour(4).minute(0).toDate(),
      "end" -> Moment().hour(4).add(1, "hour").toDate(),
      "allDay" -> false,
      "resource" -> null
    ),
  )

  @js.native
  @JSImport("!!style-loader!css-loader!react-big-calendar/lib/css/react-big-calendar.css",
    JSImport.Namespace)
  object bstyles extends js.Object

  // stop dead code removal from removing it
  private val _bstyles = bstyles
}


