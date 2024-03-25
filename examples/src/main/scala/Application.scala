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
import js.JSConverters.*
import org.scalajs.dom
import react.*
import react.conversions.given
import react.syntax.*
import vdom.*
import vdom.styling.*
import cats.*
import cats.implicits.*
import react_router6.dom.*

object Routes:

  val render: ReactFC0 = () => {    
    val readme = Pages.readme(readmetext)    
    react_router6.dom.Routes(
      Route.root(AppBody())(
        Route.index(readme),
        Route.withPath("addresses", Pages.addressPage(addressmanager.fakedata.addressDAO)),
        Route.withPath("todo", Pages.todoPage()),
        Route.withPath("helloworld", Pages.helloWorldPage()),
        Route.withPath("changeredux", Pages.changeReduxStatePage()),
        Route.withPath("labelandchild", Pages.labelAndChild("Typescript Wrapping Scala.js", helloworld.HelloWorld())),
        Route.withPath("tagtest", Pages.tagTest()),
        Route.withPath("pressure", Pages.pressurePage()),
        Route.withPath("graph", Pages.graphPage()),
        Route.withPath("calendar", Pages.calendarPage()),
        Route.withPath("bootstrap", Pages.bootstrapPage()),
        Route.withPath("mui", Pages.materialUIPage()),
        Route.always(readme)
    ))
  }
  render.displayName("Routes")
end Routes

object Application:

  val baseUrl =
    dom.document.location.origin + BuildSettings.routePrefix.getOrElse("")
  val nsegments =
    BuildSettings.routePrefix.map(_.split("/").nn.filterNot(_.nn.isEmpty).length).getOrElse(0)

  dom.console.log("baseURL", baseUrl, "nsegments to strip", nsegments)

  val Name = "Application"

  def apply() = render()

  val render: ReactFC0 = () => {
    HashRouter.withBasename(BuildSettings.routePrefix getOrElse "",
      div(new DivProps {
        className = "ttg-App"
        style = new StyleAttr {
          display = "flex"
          flexDirection = "column"
          alignItems = "stretch"
          height = "100%"
        }
      })(
        Header(),
        Routes.render
      ))
  }
end Application
