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

package ttg
package examples

import scala.scalajs.js

import js.Dynamic.{ literal => lit }
import js.annotation._

@js.native
trait Date extends js.Object {
  def hour(value: Double): Date       = js.native
  def minute(value: Double): Date     = js.native
  def add(t: Int, u: String): Date    = js.native
  def add(t: Double, u: String): Date = js.native
  def toDate(): js.Date               = js.native
}

@js.native
trait Moment extends js.Object {
  def apply(): Date = js.native
}

@js.native
@JSImport("moment-timezone", JSImport.Namespace, "moment")
object Moment extends Moment

import react_big_calendar._
//import moment._

package object calendar {

  //val mlocalizer = ReactBigCalendar.JS.momentLocalizer(moment.Moment)
  val mlocalizer = ReactBigCalendar.JS.momentLocalizer(Moment)

  val sampleEvents = js.Array[js.Dynamic](
    lit(
      "title"      -> "lunch with fred",
      "timestart"  -> Moment().hour(12).minute(0).toDate(),
      "end"        -> Moment().hour(12).add(1, "hour").toDate(),
      "allDay"     -> false,
      "resourceId" -> 4
    ),
    lit(
      "title"      -> "meet with sally",
      "timestart"  -> Moment().hour(4).minute(0).toDate(),
      "end"        -> Moment().hour(4).add(1, "hour").toDate(),
      "allDay"     -> false,
      "resourceId" -> 2
    )
  )

  trait Resource extends js.Object {
    val resourceId: Int
    val title: String
  }

  val resourceMap = js.Array[js.Object](
    new Resource { val resourceId = 1; val title = "Board room"     },
    new Resource { val resourceId = 2; val title = "Training room"  },
    new Resource { val resourceId = 3; val title = "Meeting room 1" },
    new Resource { val resourceId = 4; val title = "Meeting room 2" }
  )

  @js.native
  @JSImport("!!style-loader!css-loader!react-big-calendar/lib/css/react-big-calendar.css", JSImport.Namespace)
  object bstyles extends js.Object

  // stop dead code removal from removing it
  private val _bstyles = bstyles
}
