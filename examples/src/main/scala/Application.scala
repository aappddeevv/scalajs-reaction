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

import js.JSConverters._

import org.scalajs.dom

import react._

import implicits._

import vdom._
import vdom.styling._
import vdom.tags._

import cats._
import cats.implicits._
import react_router.dom._
import react_router.dom.hooks._

object routes {

  val sfc = SFC0 {
    val history = useHistory[js.Any]()
    def body(bodyContent: ReactNode): ReactNode =
      AppBody(new AppBody.Props {
        val nav = Nav(new Nav.Props {
          var goto = { arg =>
            println(s"Request to navigate to $arg")
            history.push(s"/$arg")
          }
        }) //c.navigate(_, router.Redirect.Push) })
        val content = bodyContent
      })

    body(
      Switch(
        Route.withExactPath("/", Redirect.to("/readme")),
        Route.withPath("/readme", Pages.readme(readmetext)),
        Route.withPath("/address", Pages.addressPage(addressmanager.fakedata.addressDAO)),
        Route.withPath("/todo", Pages.todoPage()),
        Route.withPath("/helloworld", Pages.helloWorldPage()),
        Route.withPath("/changeredux", Pages.changeReduxStatePage()),
        Route.withPath("/labelandchild", Pages.labelAndChild("Typescript Wrapping Scala.js", helloworld.HelloWorld())),
        Route.withPath("/tagtest", Pages.tagTest()),
        Route.withPath("/pressure", Pages.pressurePage()),
        Route.withPath("/graph", Pages.graphPage()),
        Route.withPath("/calendar", Pages.calendarPage()),
        Route.withPath("/bootstrap", Pages.bootstrapPage()),
        Route.withPath("/mui", Pages.materialUIPage()),
        Route.always(Redirect.to("/readme"))
      )
    )
  }
}

object Application {

  val baseUrl =
    dom.document.location.origin.asString + BuildSettings.routePrefix.getOrElse("")
  val nsegments =
    BuildSettings.routePrefix.map(_.split("/").filterNot(_.isEmpty).length).getOrElse(0)

  dom.console.log("baseURL", baseUrl, "nsegments to strip", nsegments)

  val Name = "Application"

  def apply() = sfc

  val sfc = SFC0 {
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
      HashRouter(
        routes.sfc
        // ReactionRouter.Route(RouterConfig.config(
        //   n = nsegments,
        //   baseUrl = baseUrl
        // ))
      )
    )
  }
}
