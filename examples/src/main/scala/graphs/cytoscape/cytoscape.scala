// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package examples
package graphs

import scala.scalajs.js
import js.|
import js.annotation._

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
