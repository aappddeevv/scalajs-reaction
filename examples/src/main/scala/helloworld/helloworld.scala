// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package examples
package helloworld

import scala.scalajs.js
import js.annotation._

import ttg.react._
import ttg.react.elements._
import ttg.react.reactdom._
import ttg.react.implicits._

import vdom._
import prefix_<^._

object HelloWorldC {
  val HelloWorld = statelessComponent("HelloWorld")
  def make() =
    HelloWorld
      .withRender { self =>
        <.div()(
          "hello world"
        )
      }
}

object helloworld_example {

  @JSExportTopLevel("helloworld")
  def helloworld(): Unit = {
    SourceMapSupport.install()
    renderToElementWithId(HelloWorldC.make().toEl, "container")
  }

}
