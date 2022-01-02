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
import js.JSConverters.*
import js.annotation.*
import org.scalajs.dom
import react.*
import syntax.*
import conversions.given
import vdom.*
import fabric.*
import fabric.components.*
import fabric.styling.*
import Pages.*
import Styling.*
import addressmanager.fakedata.*
import mui.*
import react_redux.*
import styles.*

@js.native
@JSImport("Examples/examples.css", JSImport.Namespace)
private object componentStyles extends js.Object

object styles {
  val estyles = componentStyles.asInstanceOf[js.Dynamic]
}

object Contexts {
  type ConsoleLog = js.Function1[js.Any, Unit]
  val logger     = js.Dynamic.global.console.log.asInstanceOf[ConsoleLog]
  val logContext = createContext[ConsoleLog](logger)
}

object Main {

    // inject global styles
  import fabric.merge_styles._
  mergeStyles(new IRawStyle {
    selectors = selectorset(":global(body), :global(html), :global(#container)" ->
      new IRawStyle {
        margin = 0
        padding = 0
        height = "100vh"
      }
    )})

  import Contexts._
  val container = "container"

  @JSExportTopLevel("settings")
  val settings = BuildSettings
  
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
    Stylesheet.getInstance().setConfig(new IStylesheetConfig {
      injectionMode = InjectionMode.appendChild
    })
    fabric.icons.initializeIcons()
    val myjss = jss.create(jss.jssPreset())

    // init message for store, although it does not do anything :-)
    StoreNS.store.dispatch(ActionsNS.Actions.View.init().asInstanceOf[GlobalAppAction])

    react_dom.createRoot(Main.container) match {
      case Left(err) => println(s"Unable to render into dom: $err")
      case Right(render) =>
        render(
          createElement(react_redux.Provider, new ProviderProps {
            store = StoreNS.store.asInstanceOf[Store[js.Object, Action]]
          },
          // mui
            StylesProvider(new StylesProvider.Props {
              _jss = myjss
            })(
              // fabric
              Fabric(new Fabric.Props {})(
                logContext.provide(Contexts.logger)(
                  Application()
                )
              )
            )
          )
        )
    }
  }
}
