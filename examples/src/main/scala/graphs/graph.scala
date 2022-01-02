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
package graphs

import scala.scalajs.js
import js.Dynamic.{ literal => jsobj }
import org.scalajs.dom
import react.*
import react.syntax.*
import react.conversions.given
import jshelpers.syntax.*
import vdom.*
import fabric.*
import fabric.components.*
import cytoscape.*

/**
 * Treat the graph and ref as instance vars in State since State in
 * reason-react is really just instance vars and the component definition is
 * really a closure around that so you should feel free to mutate it.
 */
object Graph:

  Cytoscape.use(Dagre)
  Cytoscape.use(Cola)
  //Cytoscape.use(CoseBilkent)
  //Cytoscape.use(Arbor)
  //Cytoscape.use(Spread)
  //Cytoscape.use(Klay)

  val coseLayout = jsobj(
    "name"    -> "cose",
    "animate" -> true,
    "fit"     -> false
  )

  val colaLayout = jsobj(
    "name"     -> "cola",
    "animate"  -> true,
    "infinite" -> true,
    "fit"      -> false
  )

  val dagreLayout = jsobj(
    "name" -> "dagre"
  )

  val coseBilkentLayout = jsobj(
    "name"              -> "cose-bilkent",
    "animate"           -> "end",
    "animationEasing"   -> "ease-out",
    "animationDuration" -> 1000,
    "randomize"         -> true
  )

  val Name = "Graph"

  def mkGraph(el: dom.html.Element) =
    Cytoscape(new CytoscapeOptions {
      boxSelectionEnabled = false
      autounselectify = true
      container = el
      layout = colaLayout
      style = js.Array(
        jsobj(
          "selector" -> "node",
          "css" -> jsobj(
            "content"     -> js.Any.fromFunction1((e: Element) => e.data("id")),
            "text-valign" -> "center",
            "text-halign" -> "center"
          )
        ),
        jsobj(
          "selector" -> "$node > node",
          "css" -> jsobj(
            "padding-top"      -> "10px",
            "padding-left"     -> "10px",
            "padding-bottom"   -> "10px",
            "padding-right"    -> "10px",
            "text-valign"      -> "top",
            "text-halign"      -> "center",
            "background-color" -> "#bbb"
          )
        ),
        jsobj(
          "selector" -> "edge",
          "css"      -> jsobj("target-arrow-shape" -> "triangle")
        ),
        jsobj(
          "selector" -> ":selected",
          "css" -> jsobj(
            "background-color"   -> "black",
            "line-color"         -> "black",
            "target-arrow-color" -> "black",
            "source-arrow-color" -> "black"
          )
        )
      )
      elements = jsobj(
        "nodes" -> js.Array(
          jsobj("data" -> jsobj("id" -> "a", "parent" -> "b"), "position" -> jsobj("x" -> 215, "y" -> 85)),
          jsobj("data" -> jsobj("id" -> "b")),
          jsobj("data" -> jsobj("id" -> "c", "parent" -> "b"), "position" -> jsobj("x" -> 300, "y" -> 85)),
          jsobj("data" -> jsobj("id" -> "d")),
          jsobj("data" -> jsobj("id" -> "e")),
          jsobj("data" -> jsobj("id" -> "f", "parent" -> "e"), "position" -> jsobj("x" -> 300, "y" -> 175))
        ),
        "edges" -> js.Array(
          jsobj("data" -> jsobj("id" -> "ad", "source" -> "a", "target" -> "d")),
          jsobj("data" -> jsobj("id" -> "eb", "source" -> "e", "target" -> "b"))
        )
      )
    })

  trait Props extends js.Object {}

  def apply() = createElement(render, null)

  val render: ReactFC0 = () => {
    val refR = useRefWithNull[dom.html.Div]()
    var cyR  = useRef[Option[Graph]](None)

    useEffectMounting(() => {
      println(s"$Name: didMount, building graph")
      // this feels messy, need to explore the API surface more
      cyR.current = refR.current.toNonNullOption.flatMap(g => Option(mkGraph(g.nn)))
      (() => {
        println(s"$Name: willUnmount")
        cyR.current.foreach(_.destroy())
      })
    })

    // didUpdate = js.defined { onSelf =>
    //   println(s"$Name: didUpdate")
    //   destroy(onSelf.newSelf.state)
    //   build(onSelf.newSelf.state)
    //   onSelf.newSelf.state.cy.foreach(dom.console.log(_))
    // }

    div(new DivProps {
      id = "cy"
      style = new StyleAttr {
        minWidth = "300px"
        minHeight = "500px"
        height = "100%"
        width = "100%"
        display = "block"
      }
      ref = refR
    })()
  }
  render.displayName(Name)
  
end Graph