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

import js.annotation._
import js.|

/**
 * @see https://gist.github.com/zygm0nt/2abd22e585a1c665ef8f7ab1ab473daa
 * @see https://github.com/wrotte/scala-js-cytoscape-js
 */
package object cytoscape {
  type LayoutOptions = js.Dynamic | js.Object

  /** Use .name to get the layout name. */
  // val allLayouts = Vector[BaseLayout](
  //   Dagre,
  //   Cola,
  //   Euler,
  //   Klay,
  //   Spread,
  //   Springy,
  //   Arbor,
  //   CoseBilkent,
  // )

  val InputDeviceEvents = Seq(
    "mousedown",
    "mouseup",
    "click",
    "mouseover",
    "mouseout",
    "mousemove",
    "touchstart",
    "touchmove",
    "touchend"
  )

  val OtherEvents = Seq(
    "tapstart",
    "vmousedown",
    "tapdrag",
    "vmousemove",
    "tapdragover",
    "tapdrougout",
    "tapend",
    "vmouseup",
    "taphold",
    "ctxtapstart",
    "ctxtapend",
    "ctxtap",
    "ctxdrag",
    "cxtdragover",
    "boxstart",
    "boxend",
    "boxselect",
    "box"
  )

  val CollectionEvents = Seq(
    "add",
    "remove",
    "select",
    "unselect",
    "lock",
    "unlock",
    "grabon",
    "grab",
    "drag",
    "free",
    "position",
    "data",
    "scratch",
    "style",
    "background"
  )

  val GraphEvents = Seq(
    "layoutstart",
    "layoutready",
    "layoutstop",
    "ready",
    "destroy",
    "render",
    "pan",
    "zoom",
    "viewport",
    "resize"
  )

}
