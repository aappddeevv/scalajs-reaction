// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package examples

import scala.scalajs.js
import js.annotation._
import js.JSConverters._
import js.Dynamic.{literal => lit}

import org.scalajs.dom
import _root_.react._
import implicits._
import react_redux._
import vdom._
import vdom.tags._
import fabric._
import fabric.components._
import fabric.styling._
import Styling._
import mui._

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
  import context._
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
    val myjss = jss.create(jss.jssPreset())

    // init message for store, although it does not do anything :-)
    StoreNS.store.dispatch(ActionsNS.Actions.View.init().asInstanceOf[GlobalAppAction])

    react_dom.createRoot(Main.container) match {
      case Left(err) => println(s"Unable to render into dom: $err")
      case Right(render) =>
        render(
          createElement(react_redux.Provider, new ProviderProps {
            store = StoreNS.store.asInstanceOf[Store[js.Object, Action]]
          })(
            // mui
            StylesProvider(new StylesProvider.Props {
              _jss = myjss
            })(
              // fabric
              Fabric(new Fabric.Props {
              })(
                logContext.provider(Contexts.logger)(
                  Application()
                )
              )))
        )
    }
  }
}
