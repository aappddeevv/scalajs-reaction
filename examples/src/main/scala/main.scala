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

@js.native
@JSImport("Examples/examples.css", JSImport.Namespace)
private object componentStyles extends js.Object

object styles {
  val estyles = componentStyles.asInstanceOf[js.Dynamic]
}

import styles._

import Pages._
import addressmanager.fakedata._

object Contexts {
  type ConsoleLog = js.Function1[js.Any, Unit]
  val logger     = js.Dynamic.global.console.log.asInstanceOf[ConsoleLog]
  val logContext = context.make[ConsoleLog](logger)
}

object Main {
  import react.context._
  import Contexts._

  val container       = "container"

  /**
    * This will be exported from the ES module that scala.js outputs.  How you
    * access it depends on your bundler. webpack can be configured to output a
    * "library", say named, "Scala" so you would call this function as
    * `Scala.App()`.
    */
  @JSExportTopLevel("App")
  def App(): Unit = {
    println("Running scalajs-reaction demo app")
    // use append so we can read/change the CSS in the browser debugger
    Stylesheet.getInstance.setConfig(new IStylesheetConfig {
      injectionMode = InjectionMode.appendChild
    })
    uifabric_icons.initializeIcons()
    react.mui.install()
    val myjss = JSS.create(jssPreset())

    // init message for store, although it does not do anything :-)
    StoreNS.store.dispatch(ActionsNS.Actions.View.init())

    reactdom.renderToElementWithId(
      React.createElement(redux.ReactRedux.Provider, new redux.ProviderProps {
        store = StoreNS.store
      })(
        // mui
        StylesProvider(new StylesProvider.Props {
          jss = myjss
        })(
          // fabric
          Fabric(new Fabric.Props {
          })(
            logContext.makeProvider(Contexts.logger)(Application())
        ))),
      Main.container
        // start the SPA router receiving pages...start with the current page!
        // I think react.router.push("") does the same thing with using href.
        //,Option(() => react.router.push(dom.document.location.href))
        ,Option(() => react.router.browser.push(""))
    )
  }
}
