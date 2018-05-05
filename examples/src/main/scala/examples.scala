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
import ttg.react._
import ttg.react.elements._
import ttg.react.implicits._
import ttg.react.redux
import vdom._
import ttg.react.fabric
import fabric._
import fabric.components._
import fabric.styling._
import Styling._

import cats._
import cats.implicits._

@js.native
@JSImport("Examples/examples.css", JSImport.Namespace)
private object componentStyles extends js.Object

object styles {
  val estyles = componentStyles.asInstanceOf[js.Dynamic]
}

import styles._

object Pages {

  import JSAppImports._
  import todo._
  import addressmanager._
  import graphs._

  val defaultTodos = Seq(ToDo(1, "Call Fred"))

  def labelAndChild(name: String, c: Component) = {
    PivotItem(new IPivotItemProps {
      linkText = s"$name"
      itemKey = s"$name-tab"
      className = estyles.scrollme.asString
    })(
      LabelAndChild(new LabelAndChildProps { label = "Wrapped in typescript" })(c)
    )
  }

  val graphPage =
    PivotItem(new IPivotItemProps {
      linkText = "Graph"
      itemKey = "graph"
      // don't scroll this!?!?
    })(Graph.make())

  val pressurePage =
    PivotItem(new IPivotItemProps {
      linkText = "Weather"
      itemKey = "weather"
      className = estyles.scrollme.asString
    })(atmoache.app.make())

  val todoPage = PivotItem(new IPivotItemProps {
    linkText = "To Do"
    itemKey = "todo"
    className = estyles.scrollme.asString
  })(
    Label()("Note: The To Do manager's data is reset each time you switch tabs."),
    todo.ToDosC.make(Some("Your To Do List"), todo.fakedata.initialToDos)
  )

  val helloWorldPage = PivotItem(new IPivotItemProps {
    linkText = "Hello World"
    itemKey = "helloworld"
    className = estyles.scrollme.asString
  })(
    helloworld.HelloWorld.make("make".some),
    helloworld.HelloWorld.make2(new helloworld.HelloWorldProps { name = "make2" }),
    helloworld.HelloWorld.makeWithMount("makeWithMount".some),
  )

  // the view model will come from redux to illustrate how it can be split up.
  def addressPage(adao: AddressDAO) = {
    val opts = new AddressManagerProps {
      val dao = adao
    }
    PivotItem(new IPivotItemProps {
      linkText = "Address Manager"
      itemKey = "addressmanager"
    })(
      Label()(
        "Note: Selection state and addresses are stored one level up from the tab so it is preserved between tab changes."),
      AddressManagerC.makeWithRedux(opts)
    )
  }

  def changeReduxStatePage() = {
    PivotItem(new IPivotItemProps {
      linkText = "Change Redux State"
      itemKey = "changeReduxState"
      className = estyles.scrollme.asString
    })(
      examples.changereduxstate.ChangeReduxStateC.make()
    )
  }

  def readme(text: String) = {
    PivotItem(new IPivotItemProps {
      linkText = "README"
      itemKey = "readme"
      className = cx(estyles.scrollme, estyles.readme)
    })(
      ReactMarkdownC.make(new ReactMarkdownProps { source = text })
    )
  }

  def tagTest() = {
    PivotItem(new IPivotItemProps {
      linkText = "Tag Test"
      itemKey = "tagTest"
      className = estyles.scrollme.asString
    })(
      TagTestC.make()
    )
  }

  def movies() = {
    PivotItem(new IPivotItemProps {
      linkText = "Movies"
      itemKey = "movies"
      //className = estyles.scrollme.asString
    })(
      movie.MoviesImpl.make()
    )
  }
}

import Pages._
import addressmanager.fakedata._

object Examples {
  sealed trait Action
  case class State()
  val c = reducerComponent[State, Action]("Examples")
  import c.ops._
  def make(headerTarget: String) =
    c.copy(new methods {
      val initialState = _ => State()
      val reducer = (self, state, gen) => {
        gen.skip
      }
      val render = self =>
        fragmentElement()(
          Pivot()(
            readme(examples.readmetext),
            addressPage(addressDAO),
            todoPage,
            helloWorldPage,
            changeReduxStatePage(),
            labelAndChild("Typescript Wrapping Scala.js", helloworld.HelloWorld.make()),
            tagTest(),
            pressurePage,
            graphPage,
            movies(),
          ),
          HeaderC.make("header", headerTarget)
      )
    })
}

object Contexts {
  import react.context
  type ConsoleLog = js.Function1[js.Any, Unit]
  val logContext = context.make[ConsoleLog](js.Dynamic.global.console.log.asInstanceOf[ConsoleLog])
}

object Main {
  import react.context._
  import Contexts._

  val portalElementId = "portalContainer"
  val container       = "container"

  /**
    * This will be exported from the ES module that scala.js outputs.  How you
    *  access it depends on your bundler. webpack can be configured to output a
    *  "library", say named, "Scala" so you would call this function as
    *  `Scala.App()`.
    */
  @JSExportTopLevel("App")
  def App(): Unit = {
    println("Running examples app")
    // use append so we can read/change the CSS in the browser debugger
    Stylesheet.getInstance.setConfig(new IStylesheetConfig {
      injectionMode = InjectMode.appendChild
    })
    uifabric_icons.initializeIcons()

    // init message for store, although it does not do anything :-)
    StoreNS.store.dispatch(ActionsNS.Actions.View.init())

    reactdom.createAndRenderWithId(
      TimeoutWithFallback.make(2000)(
        "Examples What?",
        React.createElement(redux.ReactRedux.Provider, new redux.ProviderProps {
          store = StoreNS.store
        })(
          Fabric(new IFabricProps {
            className = estyles.toplevel.asString
          })(
            logContext.makeProvider(ExamplesApp.make())
          ))
      ),
      "container"
    )
  }
}
