// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package examples
package helloworld

import scala.scalajs.js
import js.annotation._
import org.scalajs.dom

import _root_.react._

import _root_.react.implicits._

import vdom._
import tags._

/** Props for make2 outside the HellowWorld object.  By using js.UndefOr for the
 * defintion, a SFC taking these props can easily interop with the js world.
 */
trait HelloWorldProps extends js.Object {
  var name: js.UndefOr[String] = js.undefined
}

/**
  * Demonstrates interop and general props management.
  */
object HelloWorld {
  val Name = "HelloWorld"

  trait Props extends js.Object {
    val name: Option[String]
  }

  def apply() = sfc(new Props { val name = None })
  def apply(name_ : Option[String]) = sfc(new Props { val name = name_ })

  /** No props data structure, just parameters. */
  val sfc = SFC1[Props] { props =>
    React.useDebugValue(Name)
    div("hello world " + props.name.map(": " + _).getOrElse(""))
  }

  trait Props2 extends js.Object {
    val name: Option[String]
  }

  def withMount(name_ : Option[String] = None) =
    sfcWithMount(new Props2 { val name = name_})

  val sfcWithMount = SFC1[Props2] { props =>
    React.useEffectMounting{ () =>
      println("HelloWorld.makeWithMount: didMount was called!")
    }
    div("hello world " + props.name.map(": " + _).getOrElse(""))
  }

  // Exported to javascript world under th nam <modulename>.HelloWorld
  @JSExportTopLevel("HelloWorld")
  val exported = sfc2.run

  def make2(props: HelloWorldProps) = sfc2(props)

  lazy val sfc2 = SFC1[HelloWorldProps]{ props =>
    div("hello world (2) " + props.name.toOption.map(": " + _).getOrElse(""))
  }

  trait Props3 extends js.Object {
    val content: String
  }

  def make3(props: Props3) = sfc3(props)
  def make3(c: String) = sfc3(new Props3 { val content = c })

  val sfc3 = SFC1[Props3]{ props =>
    val hwref = React.useRef[dom.html.Div](null)
    div(new DivProps {
      ref = hwref
    })(props.content)
  }
}
