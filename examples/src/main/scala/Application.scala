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
      todo.ToDos(
        Some("Your To Do List"),
        todo.fakedata.initialToDos
      )
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
      helloworld.SuspenseTest.blah()
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

  def bootstrapPage() = page(examples.bootstrap.BootstrapPage())

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

object RouterConfig {

  import trail._
  import shapeless._
  import ReactionRouter._

  val root = Root
  val examples = Root / Arg[String]

  def body(bodyContent: ReactNode): ReactionRouter.Control => ReactNode =
    c =>
  AppBody(new AppBody.Props {
    val nav = Nav(goto = c.navigate(_, RedirectMethod.Push))
    val content = bodyContent
  })

  // all static routes so define all in one place
  val config = ReactionConfig(rules(parts =>
    parts.pathname match {
      case examples("readme" :: HNil) =>
        Render(body(Pages.readme(readmetext)))

      case examples("addresses" :: HNil) =>
        Render(body(Pages.addressPage(addressmanager.fakedata.addressDAO)))

      case examples("todo" :: HNil) =>
        Render(body(Pages.todoPage()))

      case examples("helloworld" :: HNil) =>
        Render(body(Pages.helloWorldPage()))

      case examples("changeredux" :: HNil) =>
        Render(body(Pages.changeReduxStatePage()))

      case examples("labelandchild" :: HNil) =>
        Render(body(Pages.labelAndChild("Typescript Wrapping Scala.js", helloworld.HelloWorld())))

      case examples("tagtest" :: HNil) =>
        Render(body(Pages.tagTest()))

      case examples("pressure" :: HNil) =>
        Render(body(Pages.pressurePage()))

      case examples("graph" :: HNil) =>
        Render(body(Pages.graphPage()))

      case examples("calendar" :: HNil) =>
        Render(body(Pages.calendarPage()))

      case examples("bootstrap" :: HNil) =>
        Render(body(Pages.bootstrapPage()))

      case examples("mui" :: HNil) =>
        Render(body(Pages.materialUIPage()))

      case root(HNil) =>
        Redirect("/readme", RedirectMethod.Replace)

      case _ =>
        Render(_ => "Invalid URL: No route defined! Use the browser's back button.")
    }
  ))
}

import Pages._
import addressmanager.fakedata._

object Application {

  sealed trait Action

  case class State()

  val Name = "Application"
  val c = reducerComponent[State, Action](Name)
  import c.ops._

  def apply(
  ) =
    c.copyWith(new methods {
      val initialState = _ => State()
      val reducer = (self, state, gen) => {
        gen.skip
      }
      val render = self =>
        div(new DivProps {
          style = new IRawStyle {
            display = "flex"
            flexDirection = "column"
            alignItems = "stretch"
          }
        })(
          Header(),
          ReactionRouter(RouterConfig.config)
        )
    })
}

