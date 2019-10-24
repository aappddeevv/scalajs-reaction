// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
package examples

import scala.scalajs.js
import js.annotation._
import js.Dynamic.{literal => lit}

import react_big_calendar._
import moment._

package object calendar {

  val mlocalizer = ReactBigCalendar.JS.momentLocalizer(moment.Moment)

  val sampleEvents = js.Array[js.Dynamic](
    lit (
      "title" -> "lunch with fred",
      "timestart" -> Moment().hour(12).minute(0).toDate(),
      "end" -> Moment().hour(12).add(1, "hour").toDate(),
      "allDay" -> false,
      "resourceId" -> 4
    ),
    lit (
      "title" -> "meet with sally",
      "timestart" -> Moment().hour(4).minute(0).toDate(),
      "end" -> Moment().hour(4).add(1, "hour").toDate(),
      "allDay" -> false,
      "resourceId" -> 2,
    ),
  )

  trait Resource extends js.Object {
    val resourceId: Int
    val title: String
  }

  val resourceMap = js.Array[js.Object](
    new Resource { val resourceId = 1; val title = "Board room" },
    new Resource { val resourceId = 2; val title = "Training room" },
    new Resource { val resourceId = 3; val title = "Meeting room 1" },
    new Resource { val resourceId = 4; val title = "Meeting room 2" },
  )

  @js.native
  @JSImport("!!style-loader!css-loader!react-big-calendar/lib/css/react-big-calendar.css",
    JSImport.Namespace)
  object bstyles extends js.Object

  // stop dead code removal from removing it
  private val _bstyles = bstyles
}


