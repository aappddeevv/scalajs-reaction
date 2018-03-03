// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react.examples
package graphs

import scala.scalajs.js
import ttg._
import react._
import vdom._
import tags._
import elements._
import implicits._

import fabric._
import components._

object Graph {
  sealed trait Action
  case class State()
  val c = reducerComponent[State, Action]("Graph")
  import c.ops._

  def make() =
    c.copy(new methods {
      val initialState = _ => State()

      val reducer = (action, state, gen) => {
        gen.skip
      }

      val render = self => {
        div("hello world")
      }
    })

}
