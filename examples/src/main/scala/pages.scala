// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT
package ttg
package react
package examples

import scala.scalajs.js
import js.annotation._
import js.JSConverters._
import js.Dynamic.{literal => lit}

import org.scalajs.dom
import ttg.react
import react._
import elements._
import react.implicits._
import redux._
import vdom._
import vdom.tags._
import ttg.react.fabric
import fabric._
import fabric.components._
import fabric.styling._
import Styling._
import react.mui._

import cats._
import cats.implicits._

import ttg.react.components.reactbigcalendar._
import moment._
import router._

import styles._

object Pages {

  import JSAppImports._
  import todo._
  import addressmanager._
  import graphs._

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

  def labelAndChild(name: String, c: Component) =
    page(LabelAndChild(new LabelAndChildProps { label = "Wrapped in typescript" })(c))

  def graphPage() =
    page(Graph())

  def pressurePage() =
    page(atmoache.app.make())

  def todoPage() =
    page(Fragment(
      Label()("Note: The To Do manager's data is reset each time you switch tabs."),
      todo.ToDos(new todo.ToDos.Props {
        val title = "Your To Do List"
        val todos = todo.fakedata.initialToDos
      })
    ))

  def helloWorldPage() =
    page(
      p("This uses a bunch of different methods calls approaches."),
      helloworld.HelloWorld("apply".some),
      helloworld.HelloWorld.make2(
        new helloworld.HelloWorldProps {
          name = "make2"
        }),
      helloworld.HelloWorld.withMount("withMount".some),
      helloworld.HelloWorld.make3("test ref content"),
      div(12),
      div(12.0),
      div(true), // this will not display anything
      div(null), // this will not display anything
      div("this is a helloworld string"),
      helloworld.SuspenseTest.blah(),
      helloworld.EqualityTest.doit()
    )

  // the view model will come from redux to illustrate how it can be split up.
  def addressPage(adao: AddressDAO) = {
    val opts = new AddressManagerProps {
      val dao = adao
    }
    page(
      Label()(
        "Note: Selection state and addresses are stored one level up from the tab so it is preserved between tab changes. NOT IMPLEMENTED :-) New and delete are not hooked up!"),
      AddressManager.withRedux(opts)
    )
  }

  def changeReduxStatePage() = {
    page(examples.changereduxstate.ChangeReduxState())
  }

  def readme(text: String) =
    page(
      div(s"Build with React ${ReactJS.version}"),
      ReactMarkdownC.make(new ReactMarkdownProps { source = text })
    )

  def tagTest() = page(tagtest.TagTest())

  import examples.bootstrap.BootstrapPage
  def bootstrapPage() = page(BootstrapPage(new BootstrapPage.Props{}))

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
