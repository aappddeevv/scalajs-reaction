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
import js.JSConverters._
import js.annotation.*
import org.scalajs.dom
import react.*
import react.syntax.*
import react.conversions.given
import vdom.*
import fabric.*
import fabric.components.*
import fabric.styling.*
import Styling.*
import bootstrap.*
import cats.*
import cats.implicits.*
import mui.*
import react_big_calendar.*
import react_redux.*
//import fabric.styles._

object Pages {

  import JSAppImports.*
  import addressmanager.*
  import graphs.*
  import todo.*

  //   className = estyles.scrollme.asString
  def page(n: ReactNode*) =
    div(new DivProps {
      style = new IRawStyle {
        display = "flex"
        flexDirection = "column"
        width = "100%" // should come from parent
        // this is definitely not right...
        height = "calc(100% - 31px)"
      }
    })(
      n
    )

  val defaultTodos = Seq(ToDo(1, "Call Fred"))

  def labelAndChild(name: String, c: ReactNode) =
    page(LabelAndChild(new LabelAndChildProps { label = "Wrapped in typescript" })(c))

  def graphPage() =
    page(Graph())

  def pressurePage() =
    page(atmoache.app())

  def todoPage() =
    page(
      Fragment(
        Label("Note: The To Do manager's data is reset each time you switch tabs."),
        todo.ToDos(new todo.ToDos.Props {
          val title = "Your To Do List"
          val todos = todo.fakedata.initialToDos
        })
      )
    )

  def helloWorldPage() =
    page(
      p("This uses a bunch of different methods calls approaches."),
      helloworld.HelloWorld("apply".some),
      helloworld.HelloWorld.make2(new helloworld.HelloWorldProps {
        name = "make2"
      }),
      helloworld.HelloWorld.withMount("withMount".some),
      helloworld.HelloWorld.make3("test ref content"),
      div(12),
      div(12.0),
      div(true), // this will not display anything
      div(nullNode), // this will not display anything
      div("this is a helloworld string"),
      helloworld.SuspenseTest.blah(),
      helloworld.EqualityTest.doit()
    )

  // the view model will come from redux to illustrate how it can be split up.
  def addressPage(adao: AddressDAO) = {
    val opts = new AddressManager.Props {
      val dao = adao
    }
    page(
      Label(
        "Note: Selection state and addresses are stored one level up from the tab so it is preserved between tab changes. NOT IMPLEMENTED :-) New and delete are not hooked up!"
      ),
      AddressManager(opts)
    )
  }

  def changeReduxStatePage() =
    page(examples.changereduxstate.ChangeReduxState())

  def readme(text: String) =
    page(
      div(s"Build with React ${react.version}"),
      ReactMarkdownC.make(new ReactMarkdownProps { source = text })
    )

  def tagTest() = page(tagtest.TagTest())

  import examples.bootstrap.BootstrapPage
  def bootstrapPage() = page(BootstrapPage(new BootstrapPage.Props {}))

  def materialUIPage() = page(examples.materialui.MaterialUIPage())

  import calendar._

  def calendarPage() =
    page(ReactBigCalendar[js.Dynamic](new ReactBigCalendar.Props[js.Dynamic] {
      events = sampleEvents
      localizer = mlocalizer
      defaultDate = new js.Date() // today
      startAccessor = "timestart"
      endAccessorThunk = js.defined(e => e.end.asInstanceOf[js.Date])
      resourceIdAccessor = "resourceId"
      resourceTitleAccessor = "title"
      resources = resourceMap
    })())
}
