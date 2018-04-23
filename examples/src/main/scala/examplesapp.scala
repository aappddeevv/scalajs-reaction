// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package examples

import scala.scalajs.js
import ttg.react._
import vdom._
import implicits._
import fabric._
import elements._

sealed trait Route
case object Home extends Route
case object ToDo extends Route

object ExamplesApp {

  case class State(route: Route)

  val component = reducerComponent[State, Route]("ExamplesApp")
  import component.ops._

  def make() =
    component.copy(new methods {
      val initialState = _ => State(Home)
      val reducer = (action, state, gen) => {
        // match..process..whatever
        // just stick it into the state...
        gen.update(state.copy(route = action))
      }
      subscriptions = js.defined { self =>
        js.Array(
          () => {
            val token = router.watchUrl { url =>
              println(s"url: $url")
              url._2.toLowerCase() match {
                case "examples" => self.send(Home)
                case "todo"     => self.send(ToDo)
                case _          => self.send(Home)
              }
            }
            () =>
              router.unwatchUrl(token)
          }
        )
      }
      val render = self => {
        self.state.route match {
          case Home => Examples.make(Main.portalElementId)
          case ToDo =>
            todo.ToDosC
              .make(Some("Your ToDo List - Courtesy of the Router!"), todo.fakedata.initialToDos)
        }
      }
    })

}
