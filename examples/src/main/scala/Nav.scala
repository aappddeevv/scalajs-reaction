// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package examples

import scala.scalajs.js
import js.Dynamic.{literal=>jsobj}
import js.annotation._
import js.JSConverters._

import ttg.react
import react._
import implicits._
import elements._

import vdom._
import tags._
import vdom.styling._

import fabric._
import fabric.components._
import fabric.experiments._
import fabric.styling._

import cats._
import cats.data._
import cats.implicits._

object Nav {

  trait Props extends js.Object {
    var className: js.UndefOr[String] = js.undefined
    val navigate: String => Unit
    var collapsible: js.UndefOr[Boolean] = js.undefined
    var isAdmin: js.UndefOr[Boolean]  = js.undefined
  }

  def makeItem(k: String, nm: String, nav: String, goto: String => Unit) =
    new Sidebar.ItemProps {
      val key = k
      name = nm
      active = false
      iconProps = jsobj("iconName" -> "Bullseye")
      onEmptyClick = js.defined(() => goto(nav))
    }

  val itemRoutes = Seq(
    ("readme","Readme","/readme"),
    ("addressPage", "Addresses", "/addresses"),
    ("todo", "To Do App", "/todo"),
    ("helloworld", "Hello World/Hooks", "/helloworld"),
    ("changeredux", "Change Redux", "/changeredux"),
    ("labelandchild", "Label and Child", "/labelandchild"),
    ("tagtest", "HTML Tag Test", "/tagtest"),
    ("pressure", "Pressure", "/pressure"),
    ("graph", "Graph", "/graph"),
    ("calendar", "React Big Calendar", "/calendar"),
    ("bootstrap", "Bootstrap", "/bootstrap"),
    ("mui", "Material UI", "/mui")
  )

  val Name = "Nav"
  val c = statelessComponent(Name)
  import c.ops._

  def apply(
    className: js.UndefOr[String] = js.undefined,
    goto: String => Unit
  ) = render { self =>
    Sidebar(new Sidebar.Props {
      theme = Styling.getTheme() // needed else exception!
      items = itemRoutes.map(p => makeItem(p._1,p._2,p._3,goto)).toJSArray
    })
  }
}
