// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react.examples
package helloworld

import scala.scalajs.js
import js.annotation._
import org.scalajs.dom

import ttg.react
import react._
import elements._
import reactdom._
import react.implicits._

import vdom._
import tags._

/** Props for make2 outside the HellowWorld object. */
trait HelloWorldProps extends js.Object {
  var name: js.UndefOr[String] = js.undefined
}

/**
  * Demonstrates converting js side props to scala props. `make` could also take
  * HelloWorldProps as well directly but then you have to handle conversions
  * inside the `make` function.
  */
object HelloWorld {
  val c = statelessComponent("HelloWorld")
  import c.ops._

  /** No props data structure, just parameters. */
  def apply(name: Option[String] = None) =
    render { self =>
      div("hello world" + name.map(": " + _).getOrElse(""))
    }

  def withMount(name: Option[String] = None) =
    copyWith(new methods {
      didMount = js.defined { self =>
        println("HelloWorld.makeWithMount: didMount was called!")
      }
      val render = self => {
        div("hello world" + name.map(": " + _).getOrElse(""))
      }
    })

  // Exported to javascript world
  @JSExportTopLevel("HelloWorld")
  private val jsComponent =
    c.wrapScalaForJs((jsProps: HelloWorldProps) => apply(jsProps.name.toOption))

  /**
    * Alternative scala `make` definition, render must convert to scala objects
    * if needed and internal scala code would need to create a HelloWorldProps
    * object. To handle children use `extractChildren(props)` and pass to a
    * version of "make" that takes props.
    */
  def make2(props: HelloWorldProps) =
    render { self =>
      div("hello world" + props.name.toOption.map(": " + _).getOrElse(""))
    }


  // This totally the wrong place to put the ref. This should be put into State,
  // perhaps in a Box, which in reasonreact are really instance vars.
  val hwref = ReactJS.createRef[dom.html.Div]()

  def make3(content: String) =
    copyWith(new methods { 
      val render = self => {
        div(new DivProps {
          ref = hwref
        })(content)
      }
    })
}
