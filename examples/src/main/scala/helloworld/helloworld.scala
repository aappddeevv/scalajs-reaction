// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react.examples
package helloworld

import scala.scalajs.js
import js.annotation._

import ttg.react._
import ttg.react.elements._
import ttg.react.reactdom._
import ttg.react.implicits._

import vdom._
import tags._

/** Props for make2. */
trait HelloWorldProps extends js.Object {
  val name: js.UndefOr[String] = js.undefined
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
  def make(name: Option[String] = None) =
    render { self =>
      div("hello world" + name.map(" and welcome " + _).getOrElse(""))
    }

  /**
    * The "apply" version.
    */
  def apply(name: Option[String] = None) = make(name)

  // Exported to javascript world
  @JSExportTopLevel("HelloWorld")
  private val jsComponent =
    c.wrapScalaForJs((jsProps: HelloWorldProps) => make(jsProps.name.toOption))

  /**
    * Alternative scala `make` definition, render must convert to scala objects
    * if needed and internal scala code would need to create a HelloWorldProps
    * object. To handle children use `extractChildren(props)` and pass to a
    * version of "make" that takes props.
    */
  def make2(props: HelloWorldProps) =
    render { self =>
      div("hello world" + props.name.toOption.map(" and welcome " + _).getOrElse(""))
    }
}
