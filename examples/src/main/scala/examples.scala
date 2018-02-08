// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react.examples

import scala.scalajs.js
import js.annotation._
import js.JSConverters._

import org.scalajs.dom
import ttg.react._
import ttg.react.elements._
import ttg.react.reactdom._
import ttg.react.implicits._
import vdom._
import prefix_<^._
import ttg.react.fabric
import fabric._
import fabric.components._

object Pages {

  import todo._

  val defaultTodos = Seq(ToDo(1, "Call Fred"))

  val todoPage = PivotItem(new PivotItemProps {
    override val linkText = "To Do"
    override val itemKey = "todo"
  })(
    todo.AppC.make(Some("Your To Do List"), todo.Main.initialToDos)
  ).toEl

  val helloWorldPage = PivotItem(new PivotItemProps {
    override val linkText = "Hello World"
    override val itemKey = "helloworld"
  })(
    helloworld.HelloWorldC.make()
  ).toEl

  val addressPage = PivotItem(new PivotItemProps {
    override val linkText = "Address Manager"
    override val itemKey = "addressmanager"
  })(
    addressmanager.AddressManagerC.make(addressmanager.fakedata.addressDAO)
  ).toEl
}

object Main {
  import Pages._

  @JSExportTopLevel("app")
  def app(): Unit = {
    uifabric_icons.initializeIcons()
    renderToElementWithId(
      Fabric()(
        Pivot()(
          addressPage,
          todoPage,
          helloWorldPage,
        ).toEl
      ).toEl,
      "container")
  }
}
