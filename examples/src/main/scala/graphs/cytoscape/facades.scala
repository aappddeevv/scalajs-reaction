// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react.examples
package graphs
package cytoscape

import scala.scalajs.js
import js.|
import js.annotation._
import org.scalajs.dom

trait CytoscapeOptions extends js.Object {
  var container: js.UndefOr[dom.html.Element]      = js.undefined
  var elements: js.UndefOr[js.Dynamic | js.Object] = js.undefined
  var style
    : js.UndefOr[js.Array[js.Dynamic] | js.Array[js.Object] | String | js.Object | js.Dynamic] =
    js.undefined
  var layout: js.UndefOr[LayoutOptions] = js.undefined
  var zoom: js.UndefOr[Int]             = js.undefined
  //pan: js.UndefOr[Double] = js.undefined,

  var minZoom: js.UndefOr[Double]               = js.undefined
  var maxZoom: js.UndefOr[Double]               = js.undefined
  var zoomingEnabled: js.UndefOr[Boolean]       = js.undefined
  var userZoomingEnabled: js.UndefOr[Boolean]   = js.undefined
  var userPanning: js.UndefOr[Boolean]          = js.undefined
  var boxSelectionEnabled: js.UndefOr[Boolean]  = js.undefined
  var selectionType: js.UndefOr[String]         = js.undefined
  var touchTapThreshold: js.UndefOr[Int]        = js.undefined
  var desktopTapThreshold: js.UndefOr[Int]      = js.undefined
  var autolock: js.UndefOr[Boolean]             = js.undefined
  var autoungrabify: js.UndefOr[Boolean]        = js.undefined
  var autounselectify: js.UndefOr[Boolean]      = js.undefined
  var headless: js.UndefOr[Boolean]             = js.undefined
  var styleEnabled: js.UndefOr[Boolean]         = js.undefined
  var hideEdgesOnViewport: js.UndefOr[Boolean]  = js.undefined
  var hideLabelsOnViewport: js.UndefOr[Boolean] = js.undefined
  var textureOnViewport: js.UndefOr[Boolean]    = js.undefined
  var motionBlur: js.UndefOr[Boolean]           = js.undefined
  var motionBlurOpacity: js.UndefOr[Double]     = js.undefined
  var wheelSensitivity: js.UndefOr[Int]         = js.undefined
  var pixelRation: js.UndefOr[String]           = js.undefined
}

@js.native
trait LayoutManipulation extends js.Object {
  def run(): Unit   = js.native
  def start(): Unit = js.native
  def stop(): Unit  = js.native
}

@js.native
trait LayoutEvents extends js.Object {
  def on(events: String, cb: js.Function1[js.Any, Unit]): Unit = js.native
  def on(events: String, data: js.Any, cb: js.Function1[js.Any, Unit]): Unit = js.native
  def removeListener(events: String, cb: js.UndefOr[js.Function1[js.Any, Unit]] = js.undefined): Unit = js.native
}


@js.native
trait BaseLayout extends js.Object {
  //var name: String
}

@js.native
trait Layout extends js.Object {
  def layout(layout: LayoutOptions): LayoutManipulation
  def makeLayout(options: LayoutOptions): LayoutManipulation
}

@js.native
trait Graph extends js.Object with Layout {
  def container(): js.UndefOr[dom.html.Element] = js.native
  def center(): Unit                   = js.native
  def destroy(): Unit                  = js.native
  def fit(): Unit                      = js.native
  def reset(): Unit                    = js.native
  def pan(): Position                  = js.native
  def panningEnabled(): Boolean        = js.native
  def userPanningEnabled(): Boolean    = js.native
  def zoom(level: Double): Unit        = js.native
  def zoom(options: ZoomOptions): Unit = js.native
  def zoomingEnabled(): Boolean        = js.native
  def userZoomingEnabled(): Boolean    = js.native

  def minZoom(): Double        = js.native
  def minZoom(v: Double): Unit = js.native
  def maxZoom(): Double        = js.native
  def maxZoom(v: Double): Unit = js.native

  def viewport(zoom: Double, pan: Position): Unit = js.native
  def boxSelectionEnabled(): Boolean              = js.native
  def boxSelectionEnnabled(v: Boolean): Unit      = js.native

  def width(): Int  = js.native
  def height(): Int = js.native

  def autolock(): Boolean        = js.native
  def autolock(v: Boolean): Unit = js.native

  def resize(): Unit      = js.native
  def animated(): Boolean = js.native
}

class ZoomOptions(
    val level: Double,
    val position: Position,
    val renderedPosition: js.UndefOr[Position] = js.undefined
) extends js.Object

class Position(val x: Double, val y: Double) extends js.Object
@js.native
@JSImport("cytoscape", JSImport.Default)
object Cytoscape extends js.Object {

  /** Create a graph. */
  def apply(options: js.UndefOr[CytoscapeOptions] = js.undefined): Graph = js.native

  /** Register extensions. */
  def apply(typ: String, name: String, extensions: js.Any): Unit = js.native

  /** Register extension (the extension should be a cytoscap "registration" function underneath. */
  def use(extension: js.Any*): Unit = js.native

  val stylesheet: js.Dynamic = js.native

  val version: String = js.native
}

@js.native
trait Event extends js.Object{
  val cy: Graph = js.native
  val cyTarget : Element = js.native
  val `type`: String = js.native
  val namespace: String = js.native
  val data: js.Object = js.native
  val timeStamp: Long = js.native

  // for input device events only
  val position: Position = js.native
  val renderedPosition: Position = js.native
  val originalEvent: js.Any = js.native

  // layout events only
  val layout: js.Any = js.native
}

@js.native
trait Element extends js.Object {
  def scratch(): js.Object                               = js.native
  def scratch(namespace: String): js.Object              = js.native
  def scratch(namespace: String, value: js.Object): Unit = js.native
  def removeScratch(): Unit                              = js.native
  def id(): String                                       = js.native
  //def json(): ElementObject = js.native
  def group(): String                                      = js.native
  def isNode(): Boolean                                    = js.native
  def isEdge(): Boolean                                    = js.native
  def isLoop(): Boolean                                    = js.native
  def isSimple(): Boolean                                  = js.native
  def data(): js.Object                                    = js.native
  def data(key: String): js.Object                         = js.native
  def data(key: String, value: String): Unit               = js.native
  def data(key: String, value: js.Any): Unit               = js.native
  def data(dict: js.Dictionary[js.Object]): Unit           = js.native
  def addClass(string: String): Unit                       = js.native
  def removeClass(string: String): Unit                    = js.native
  def toggleClass(string: String, toggle: Boolean): Unit   = js.native
  def classes(string: String = ""): Unit                   = js.native
  def flashClass(string: String, duration: Long = 0): Unit = js.native
  def hasClass(string: String): Boolean                    = js.native
  def layout(options: js.Object): Unit                     = js.native
  def style(style: js.Object): Unit                        = js.native
}

trait CoseOptions extends js.Object {

  /** Must be cose. */
  val name: String
  var animate: js.UndefOr[Boolean]        = js.undefined
  var animationEasing: js.UndefOr[String] = js.undefined
  var animationDuration: js.UndefOr[Int]  = js.undefined
  var animationThreshold: js.UndefOr[Int] = js.undefined
  var refresh: js.UndefOr[Int]            = js.undefined
  var fit: js.UndefOr[Boolean]            = js.undefined
  var padding: js.UndefOr[Int]            = js.undefined
  var randomize: js.UndefOr[Boolean]      = js.undefined
  var componentSpacing: js.UndefOr[Int]   = js.undefined
  var nodeOverlap: js.UndefOr[Int]        = js.undefined
  var nestingFactor: js.UndefOr[Double]   = js.undefined
  var gravity: js.UndefOr[Double]         = js.undefined
  var numIter: js.UndefOr[Int]            = js.undefined
  var initialTemp: js.UndefOr[Int]        = js.undefined
  var coolingFactor: js.UndefOr[Double]   = js.undefined
  var minTemp: js.UndefOr[Double]         = js.undefined
  var weaver: js.UndefOr[Boolean]         = js.undefined

  var ready: js.UndefOr[js.Function0[Unit]]                             = js.undefined
  var stop: js.UndefOr[js.Function0[Unit]]                              = js.undefined
  var animateFilter: js.UndefOr[js.Function2[js.Dynamic, Int, Boolean]] = js.undefined
  var nodeRepulsion: js.UndefOr[js.Function1[js.Dynamic, Int]]          = js.undefined
  var idealEdgeLength: js.UndefOr[js.Function1[js.Dynamic, Int]]        = js.undefined
  var edgeElasticity: js.UndefOr[js.Function1[js.Dynamic, Int]]         = js.undefined
}

@js.native
@JSImport("cytoscape-dagre", JSImport.Default)
object Dagre extends js.Object

@js.native
@JSImport("cytoscape-cola", JSImport.Default)
object Cola extends BaseLayout

@js.native
@JSImport("cytoscape-euler", JSImport.Default)
object Euler extends BaseLayout

@js.native
@JSImport("cytoscape-klay", JSImport.Default)
object Klay extends BaseLayout

@js.native
@JSImport("cytoscape-spread", JSImport.Default)
object Spread extends BaseLayout

@js.native
@JSImport("cytoscape-springy", JSImport.Default)
object Springy extends BaseLayout

@js.native
@JSImport("cytoscape-arbor", JSImport.Default)
object Arbor extends BaseLayout

@js.native
@JSImport("cytoscape-cose-bilkent", JSImport.Default)
object CoseBilkent extends BaseLayout
