// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react.examples

import scala.scalajs.js
import js.annotation._
import js.JSConverters._
import js.Dynamic.{literal => lit}

import org.scalajs.dom
import ttg.react._
import ttg.react.elements._
import ttg.react.reactdom._
import ttg.react.implicits._
import ttg.react.redux
import vdom._
import prefix_<^._
import ttg.react.fabric
import fabric._
import fabric.components._

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

  val defaultTodos = Seq(ToDo(1, "Call Fred"))

  def labelAndChild(name: String, c: ComponentAny) = {
    PivotItem(new IPivotItemProps {
      linkText = s"$name"
      itemKey = s"$name-tab"
    })(
      LabelAndChild(new LabelAndChildProps { label = "Wrapped in typescript" })(c)
    )
  }

  val todoPage = PivotItem(new IPivotItemProps {
    linkText = "To Do"
    itemKey = "todo"
  })(
    Label()("Note: The To Do manager's data is reset each time you switch tabs."),
    todo.ToDosC.make(Some("Your To Do List"), todo.fakedata.initialToDos)
  )

  val helloWorldPage = PivotItem(new IPivotItemProps {
    linkText = "Hello World"
    itemKey = "helloworld"
  })(
    helloworld.HelloWorldC.make()
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
      Label()("Note: Selection state and addresses are stored one level up from the tab so it is preserved between tab changes."),
      AddressManagerC.makeWithRedux(opts)
    )
  }

  def changeReduxStatePage() = {
    PivotItem(new IPivotItemProps {
      linkText = "Change Redux State"
      itemKey = "changeReduxState"
    })(
      examples.changereduxstate.ChangeReduxStateC.make()
    )
  }

  def readme(text: String) = {
    PivotItem(new IPivotItemProps {
      linkText = "README"
      itemKey = "readme"
    })(
      ReactMarkdownC.make(new ReactMarkdownProps { source = text })
    )
  }
}

object Main {
  import Pages._
  import addressmanager.fakedata._

  /**
    * This will be exported from the ES module that scala.js outputs.  How you
    *  access it depends on your bundler. webpack can be configured to output a
    *  "library", say named, "Scala" so you would call this function as
    *  `Scala.App()`.
    */
  @JSExportTopLevel("App")
  def App(): Unit = {
    println("Running examples app")
    uifabric_icons.initializeIcons()

    // init message for store, although it does not do anything :-)
    StoreNS.store.dispatch(ActionsNS.Actions.View.init())

    renderToElementWithId(
      React.createElement(redux.ReactRedux.Provider, new redux.ProviderProps { store = StoreNS.store })(
        Fabric(new IFabricProps {
          className = estyles.root.asString
        })(
          Pivot()(
            readme(examples.readmetext),
            addressPage(addressDAO),
            todoPage,
            helloWorldPage,
            changeReduxStatePage(),
            labelAndChild("Typescript Wrapping Scala.js", helloworld.HelloWorldC.make()),
          ) //
        )),
      "container"
    )
  }
}
