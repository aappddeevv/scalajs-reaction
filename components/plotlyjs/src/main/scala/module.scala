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

package plotlyjs

import scala.scalajs.js
import js.annotation._
import js.|
import vdom._
import org.scalajs.dom
import react._

/** Typeclass to convert some arrays to datum arrays */
class PlotlyJSArrayOps[T](private val arr: js.Array[T]) extends AnyVal {
    def asDatumArr: js.Array[Datum] = arr.asInstanceOf[js.Array[Datum]]
}

trait syntax {
  /*implicit def intArrayToDatumArray(arr: js.Array[Int]) = new JSArrayOps[Int](arr)
  implicit def longArrayToDatumArray(arr: js.Array[Long]) = new JSArrayOps[Long](arr)
  implicit def floatArrayToDatumArray(arr: js.Array[Float]) = new JSArrayOps[Float](arr)
  implicit def doubleArrayToDatumArray(arr: js.Array[Double]) = new JSArrayOps[Double](arr)
  implicit def stringArrayToDatumArray(arr: js.Array[String]) = new JSArrayOps[String](arr)
  implicit def dateArrayToDatumArray(arr: js.Array[js.Date]) = new JSArrayOps[js.Date](arr)
  implicit def nullArrayToDatumArray(arr: js.Array[Null]) = new JSArrayOps[Null](arr)
  implicit def anyArrayToDatumArray(arr: js.Array[Any]) = new JSArrayOps[Any](arr)
  */
  implicit def arrayToDatumArray[T](arr: js.Array[T]) = new PlotlyJSArrayOps[T](arr)
}

object syntax extends syntax

@jsenrich trait Font extends js.Object {
  var family: js.UndefOr[String] = js.undefined
  var size: js.UndefOr[Int] = js.undefined
  var color: js.UndefOr[String | Int] = js.undefined
}

@jsenrich trait Title extends js.Object {
  var text: js.UndefOr[String] = js.undefined
  var font: js.UndefOr[Font] = js.undefined
  var xref: js.UndefOr[String] = js.undefined // string or paper
  var x: js.UndefOr[Double] = js.undefined
  var xanchor: js.UndefOr[Double] = js.undefined
  var y: js.UndefOr[Double] = js.undefined
  var yanchor: js.UndefOr[Double] = js.undefined
}

@jsenrich trait DataTitle extends js.Object {
 var text: js.UndefOr[String] = js.undefined
 var font: js.UndefOr[Font] = js.undefined
 var position: js.UndefOr[String] = js.undefined
}

trait Margin extends js.Object {
  var l: js.UndefOr[Int] = js.undefined
  var t: js.UndefOr[Int] = js.undefined
  var b: js.UndefOr[Int] = js.undefined
  var pad: js.UndefOr[Int] = js.undefined
  var autoexpand: js.UndefOr[Int] = js.undefined
}

@js.native
abstract trait Mode extends js.Any
object Mode {
  var hide = "hide".asInstanceOf[Mode]
  var show = "show".asInstanceOf[Mode]
  var `false` = false.asInstanceOf[Mode]
}

@jsenrich trait UniformText extends js.Object {
  var mode: js.UndefOr[Mode] = js.undefined
  var minsize: js.UndefOr[Int] = js.undefined
}

@jsenrich trait ColorScale extends js.Object {
  var sequential: js.UndefOr[js.Array[ColorScaleItem]] = js.undefined
  var sequentialminus: js.UndefOr[js.Array[ColorScaleItem]] = js.undefined
  var diverging: js.UndefOr[js.Array[ColorScaleItem]] = js.undefined
}

@js.native
abstract trait ThicknessMode extends js.Any
object ThicknessMode {
  val fraction = "fraction".asInstanceOf[ThicknessMode]
  val pixels = "pixels".asInstanceOf[ThicknessMode]
}
@js.native
abstract trait LenMode extends js.Any
object LenMode {
  val fraction = "fraction".asInstanceOf[LenMode]
  val pixels = "pixels".asInstanceOf[LenMode]
}

trait ColorBar extends js.Object {
  var len: js.UndefOr[Int] = js.undefined
  var lenmode: js.UndefOr[LenMode] = js.undefined
  var thicknessmode: js.UndefOr[ThicknessMode] = js.undefined
  var thickness: js.UndefOr[Int] = js.undefined
  var X: js.UndefOr[Double] = js.undefined

}

trait ColorAxis extends js.Object {
  var autocolorscale: js.UndefOr[Double] = js.undefined
  var cauto: js.UndefOr[Boolean] = js.undefined
  var cmin: js.UndefOr[Double] = js.undefined
  var cmax: js.UndefOr[Double] = js.undefined
  var cmid: js.UndefOr[Double] = js.undefined
  var colorscale: js.UndefOr[js.Array[ColorScaleItem]] = js.undefined
  var showscale: js.UndefOr[Double] = js.undefined
  var reversescale: js.UndefOr[Double] = js.undefined
  var colorbar: js.UndefOr[ColorBar] = js.undefined
}

@js.native
abstract trait HoverMode extends js.Any
object HoverMode {
  val x = "x".asInstanceOf[HoverMode]
  val y = "y".asInstanceOf[HoverMode]
  val closest = "closest".asInstanceOf[HoverMode]
  val `false` = false.asInstanceOf[HoverMode]
}

@js.native
abstract trait DragMode extends js.Any
object DragMode {
  val zoom = "zoom".asInstanceOf[DragMode]
  val pan = "pan".asInstanceOf[DragMode]
  val select = "select".asInstanceOf[DragMode]
  val lasso = "lasso".asInstanceOf[DragMode]
  val orbit = "orbit".asInstanceOf[DragMode]
  val turntable = "turntable".asInstanceOf[DragMode]
  val `false` = false.asInstanceOf[DragMode]
}

@js.native
abstract trait SelectDirection extends js.Any
object SelectDirection {
  val h = "h".asInstanceOf[SelectDirection]
  val v = "v".asInstanceOf[SelectDirection]
  val d = "d".asInstanceOf[SelectDirection]
  val any = "any".asInstanceOf[SelectDirection]
}

@js.native
abstract trait Align extends js.Any
object Align {
  val left = "left".asInstanceOf[Align]
  val right = "right".asInstanceOf[Align]
  val auto = "auto".asInstanceOf[Align]
}

abstract trait Ordering extends js.Any
object Ordering {
  val layoutFirst = "layout first".asInstanceOf[Ordering]
  val tracesFirst = "traces first".asInstanceOf[Ordering]
}

trait Label extends js.Object {
  var bgcolor: js.UndefOr[Color] = js.undefined
  var bordercolor: js.UndefOr[Color] = js.undefined
  var font: js.UndefOr[Font] = js.undefined

}

trait HoverLabel extends Label {
  var align: js.UndefOr[Align] = js.undefined
  var namelength: js.UndefOr[Int] = js.undefined
}

trait Annotation extends Label {
  var visible: js.UndefOr[Boolean] = js.undefined
  var text: js.UndefOr[String] = js.undefined
  var textangle: js.UndefOr[String] = js.undefined
  var width: js.UndefOr[Double] = js.undefined
  var height: js.UndefOr[Double] = js.undefined
  var opacity: js.UndefOr[Double] = js.undefined
  var align: js.UndefOr[Align] = js.undefined
  var valign: js.UndefOr[Align] = js.undefined
  var borderpad: js.UndefOr[Double] = js.undefined
  var borderwidth: js.UndefOr[Double] = js.undefined
  var showarrow: js.UndefOr[Boolean] = js.undefined
  var arrowcolor: js.UndefOr[String] = js.undefined
  var arrowhead: js.UndefOr[Double] = js.undefined
  var startarrowhead: js.UndefOr[Double] = js.undefined
  var arrowsize: js.UndefOr[Double] = js.undefined
  var startarrowsize: js.UndefOr[Double] = js.undefined
  var arrowwidth: js.UndefOr[Double] = js.undefined
  var standoff: js.UndefOr[Double] = js.undefined
  var startstandoff: js.UndefOr[Double] = js.undefined
  var ax: js.UndefOr[Double] = js.undefined
  var ay: js.UndefOr[Double] = js.undefined
//var axref
//var ayref
//var xref
  var x: js.UndefOr[Double | String] = js.undefined
  var xanchor: js.UndefOr[String] = js.undefined
  var xshift: js.UndefOr[Double] = js.undefined
//var yref
  var y: js.UndefOr[Double | String] = js.undefined
  var yanchor: js.UndefOr[String] = js.undefined
  var yshift: js.UndefOr[Double] = js.undefined
  var clicktoshow: js.UndefOr[Boolean] = js.undefined
  var xclick: js.UndefOr[js.Any] = js.undefined
  var yclick: js.UndefOr[js.Any] = js.undefined
  var hovertext: js.UndefOr[String] = js.undefined
//var hoverlabel
  var captureevents: js.UndefOr[Boolean] = js.undefined

}

trait Transition extends js.Object {
  var duration: js.UndefOr[Int] = js.undefined
  var easing: js.UndefOr[String] = js.undefined // lots of options
  var ordering: js.UndefOr[Ordering] = js.undefined
}

@js.native
abstract trait RowOrder extends js.Any
object RowOrder {
  val topToBottom = "top to bottom".asInstanceOf[RowOrder]
  val bottomToTop = "bottom to top".asInstanceOf[RowOrder]
}

@js.native
abstract trait Pattern extends js.Any
object Pattern {
  val independent = "independent".asInstanceOf[Pattern]
  val coupled = "coupled".asInstanceOf[Pattern]
}

trait Domain extends js.Object {
  var x: js.UndefOr[js.Tuple2[Double, Double]] = js.undefined
  var y: js.UndefOr[js.Tuple2[Double, Double]] = js.undefined
}

@js.native
abstract trait XSide extends js.Any
object XSide {
  val bottom = "bottom".asInstanceOf[XSide]
  val bottomPlot = "bottom plot".asInstanceOf[XSide]
  val topPlot = "top plot".asInstanceOf[XSide]
  val top = "top".asInstanceOf[XSide]
}

@js.native
abstract trait YSide extends js.Any
object YSide {
  val left = "left".asInstanceOf[YSide]
  val leftPlot = "left plot".asInstanceOf[YSide]
  val rightPlot = "right plot".asInstanceOf[YSide]
  val right = "right".asInstanceOf[YSide]
}

trait Grid extends js.Object {
  var columns: js.UndefOr[Int] = js.undefined
  var domain: js.UndefOr[Domain] = js.undefined
  var pattern: js.UndefOr[Pattern] = js.undefined
  var rows: js.UndefOr[Int] = js.undefined
  var roworder: js.UndefOr[RowOrder] = js.undefined
  var subplots: js.UndefOr[js.Array[String]] = js.undefined
  var xaxes: js.UndefOr[js.Array[String]] = js.undefined
  var xgap: js.UndefOr[Double] = js.undefined
  var xside: js.UndefOr[XSide] = js.undefined
  var ygap: js.UndefOr[Double] = js.undefined
  var yaxes: js.UndefOr[js.Array[String]] = js.undefined
  var yside: js.UndefOr[YSide] = js.undefined
}

@js.native
abstract trait Calendar extends js.Any
object Calendar {
  val gregorian = "gregorian".asInstanceOf[Calendar]
  val chinese = "chinese".asInstanceOf[Calendar]
  val coptic = "coptic".asInstanceOf[Calendar]
  val discworld = "discworld".asInstanceOf[Calendar]
  val ethiopian = "ethiopian".asInstanceOf[Calendar]
  val hebrew = "hebrew".asInstanceOf[Calendar]
  val islamic = "islamic".asInstanceOf[Calendar]
  val julian = "julian".asInstanceOf[Calendar]
  val mayan = "mayan".asInstanceOf[Calendar]
  val nanakshahi = "nanakshahi".asInstanceOf[Calendar]
  val nepali = "nepali".asInstanceOf[Calendar]
  val persian = "persion".asInstanceOf[Calendar]
  val jalali = "jalai".asInstanceOf[Calendar]
  val taiwan = "taiwan".asInstanceOf[Calendar]
  val thai = "thai".asInstanceOf[Calendar]
  val ummalqura = "ummalura".asInstanceOf[Calendar]
}

@jsenrich trait TickTitle extends js.Object {
  var text: js.UndefOr[String] = js.undefined
  var font: js.UndefOr[Font] = js.undefined
  var standoff: js.UndefOr[Int] = js.undefined
}

@js.native
abstract trait AxisType extends js.Any
object AxisType {
  val `-` = "-".asInstanceOf[AxisType]
  val linear = "linear".asInstanceOf[AxisType]
  val log = "log".asInstanceOf[AxisType]
  val date = "date".asInstanceOf[AxisType]
  val category = "category".asInstanceOf[AxisType]
  val multicategory = "multicategory".asInstanceOf[AxisType]
}

@js.native
abstract trait RangeMode extends js.Any
object RangeMode {
  val normal = "normal".asInstanceOf[RangeMode]
  val tozero = "tozero".asInstanceOf[RangeMode]
  val nonnegative = "nonnegative".asInstanceOf[RangeMode]
}

@js.native
abstract trait Constrain extends js.Any
object Constrain {
  val range = "range".asInstanceOf[Constrain]
  val domain = "domain".asInstanceOf[Constrain]
}

@js.native
abstract trait ConstrainToward extends js.Any
object ConstrainToward {
  val left = "left".asInstanceOf[ConstrainToward]
  val center = "center".asInstanceOf[ConstrainToward]
  val right = "right".asInstanceOf[ConstrainToward]
  val top = "top".asInstanceOf[ConstrainToward]
  val middle = "middle".asInstanceOf[ConstrainToward]
}

@js.native
abstract trait TickMode extends js.Any
object TickMode {
  val auto = "auto".asInstanceOf[TickMode]
  val linear = "linear".asInstanceOf[TickMode]
  val array = "array".asInstanceOf[TickMode]
}

@js.native
abstract trait Tick extends js.Any
object Tick {
  val outside = "outside".asInstanceOf[Tick]
  val inside = "dinsedoutside".asInstanceOf[Tick]
  val none = "".asInstanceOf[Tick]
}

@js.native
abstract trait TicksOn extends js.Any
object TicksOn {
  val labels = "labels".asInstanceOf[TicksOn]
  val boundaries = "boundaries".asInstanceOf[TicksOn]
}

@js.native
abstract trait Mirror extends js.Any
object Mirror {
  val ticks = "ticks".asInstanceOf[Mirror]
  val all = "all".asInstanceOf[Mirror]
  val allticks = "alltacks".asInstanceOf[Mirror]
}

@js.native
abstract trait SpikeSnap extends js.Any
object SpikeSnap {
  val data = "data".asInstanceOf[SpikeSnap]
  val cursor = "cursor".asInstanceOf[SpikeSnap]
}

@js.native
abstract trait ShowTickPrefix extends js.Any
object ShowTickPrefix {
  val all = "all".asInstanceOf[ShowTickPrefix]
  val first = "first".asInstanceOf[ShowTickPrefix]
  val last = "last".asInstanceOf[ShowTickPrefix]
  val none = "none".asInstanceOf[ShowTickPrefix]
}

@js.native
abstract trait ShowExponent extends js.Any
object ShowExponent {
  val all = "all".asInstanceOf[ShowExponent]
  val first = "first".asInstanceOf[ShowExponent]
  val last = "last".asInstanceOf[ShowExponent]
  val none = "none".asInstanceOf[ShowExponent]
}

@js.native
abstract trait ExponentFormat extends js.Any
object ExponentFormat {
  val none = "none".asInstanceOf[ExponentFormat]
  val e = "e".asInstanceOf[ExponentFormat]
  val E = "E".asInstanceOf[ExponentFormat]
  val power = "power".asInstanceOf[ExponentFormat]
  val SI = "SI".asInstanceOf[ExponentFormat]
  val B = "B".asInstanceOf[ExponentFormat]
}

@js.native
abstract trait Side extends js.Any
object Side {
  val top = "top".asInstanceOf[Side]
  val bottom = "bottom".asInstanceOf[Side]
  val left = "left".asInstanceOf[Side]
  val right = "right".asInstanceOf[Side]
}

@js.native
abstract trait Dash extends js.Any
object Dash {
  val solid = "solid".asInstanceOf[Dash]
  val dot = "dot".asInstanceOf[Dash]
  val dash = "dash".asInstanceOf[Dash]
  val longdash = "longdash".asInstanceOf[Dash]
  val dashdot = "dashdot".asInstanceOf[Dash]
  val longdashdot = "longdashdot".asInstanceOf[Dash]
}


trait Line extends js.Object {
    var color: js.UndefOr[Color] = js.undefined
    var width: js.UndefOr[Double] = js.undefined
    var dash: js.UndefOr[Dash] = js.undefined
    var shape: js.UndefOr[String] = js.undefined
    var smoothing: js.UndefOr[Double] = js.undefined
    var simplify: js.UndefOr[Boolean] = js.undefined
}

trait ScatterLine extends Line

@js.native
abstract trait Layer extends js.Any
object Layer {
  val aboveTrace = "above traces".asInstanceOf[Layer]
  val belowTraces = "below traces".asInstanceOf[Layer]
}

@jsenrich trait TickFormatStops extends js.Object {
  var dtickrange: js.UndefOr[js.Tuple2[Double, Double]] = js.undefined
  var enabled: js.UndefOr[Boolean] = js.undefined
  var name: js.UndefOr[String] = js.undefined
  var templateitemname: js.UndefOr[String] = js.undefined
  var value: js.UndefOr[String] = js.undefined
}

@jsenrich trait Axis extends js.Object {
  var anchor: js.UndefOr[String] = js.undefined
  var autorange: js.UndefOr[Boolean | String] = js.undefined
  var automargin: js.UndefOr[Boolean] = js.undefined
  var categoryorder: js.UndefOr[String] = js.undefined
  var calendar: js.UndefOr[Calendar] = js.undefined
  var color: js.UndefOr[String] = js.undefined
  var constrain: js.UndefOr[Constrain] = js.undefined
  var constraintoward: js.UndefOr[ConstrainToward] = js.undefined
  var domain: js.UndefOr[js.Tuple2[Double, Double]] = js.undefined
  var dtick: js.UndefOr[Double | String] = js.undefined
  var dividercolor: js.UndefOr[Color] = js.undefined
  var dividerwidth: js.UndefOr[Int] = js.undefined
  var fixedrange: js.UndefOr[String] = js.undefined
  var exponentformat: js.UndefOr[ExponentFormat] = js.undefined
  var gridcolor: js.UndefOr[Color] = js.undefined
  var gridwidth: js.UndefOr[Int] = js.undefined
  var hoverformat: js.UndefOr[String] = js.undefined
  var layer: js.UndefOr[Layer] = js.undefined
  var linecolor: js.UndefOr[Color] = js.undefined
  var linewidth: js.UndefOr[Int] = js.undefined
  var matches: js.UndefOr[String] = js.undefined
  var mirror: js.UndefOr[Mirror | Boolean] = js.undefined
  var nticks: js.UndefOr[Int] = js.undefined
  var overlaying: js.UndefOr[String] = js.undefined
  var position: js.UndefOr[Double] = js.undefined
  var range: js.UndefOr[js.Array[AxisRange]] = js.undefined
  //var rangeslider
  //var rangeseselector
  var rangemode: js.UndefOr[RangeMode] = js.undefined
  var scaleanchor: js.UndefOr[String] = js.undefined
  var scaleratio: js.UndefOr[Double] = js.undefined
  var separatethousands: js.UndefOr[Boolean] = js.undefined
  var showspikes: js.UndefOr[Boolean] = js.undefined
  var showline: js.UndefOr[Boolean] = js.undefined
  var showexponent: js.UndefOr[ShowExponent] = js.undefined
  var showdividers: js.UndefOr[Boolean] = js.undefined
  var showticklabels: js.UndefOr[Boolean] = js.undefined
  var showtickprefix: js.UndefOr[ShowTickPrefix] = js.undefined
  var showgrid: js.UndefOr[Boolean] = js.undefined
  var side: js.UndefOr[Side] = js.undefined
  var spikecolor: js.UndefOr[Color] = js.undefined
  var spikethickness: js.UndefOr[Int] = js.undefined
  var spikedash: js.UndefOr[String] = js.undefined
  var spikemode: js.UndefOr[String] = js.undefined
  var spikesnap: js.UndefOr[SpikeSnap] = js.undefined
  var tick0: js.UndefOr[Double | String] = js.undefined
  var tickformat: js.UndefOr[String] = js.undefined
  var tickmode: js.UndefOr[TickMode] = js.undefined
  var tickfont: js.UndefOr[Font] = js.undefined
  var tickangle: js.UndefOr[Double] = js.undefined
  var tickformatstops: js.UndefOr[js.Array[TickFormatStops]] = js.undefined
  var tickprefix: js.UndefOr[String] = js.undefined
  var ticklen: js.UndefOr[Int] = js.undefined
  var tickwidth: js.UndefOr[Int] = js.undefined
  var tickcolor: js.UndefOr[Color] = js.undefined
  var tickvals: js.UndefOr[js.Array[Datum]] = js.undefined
  var ticksuffix: js.UndefOr[String] = js.undefined
  var ticktext: js.UndefOr[js.Array[String]] = js.undefined
  var ticks: js.UndefOr[Tick] = js.undefined
  var tickson: js.UndefOr[TicksOn] = js.undefined
  var title: js.UndefOr[TickTitle] = js.undefined
  var `type`: js.UndefOr[AxisType] = js.undefined
  var uirevision: js.UndefOr[String | Int] = js.undefined
  var visible: js.UndefOr[Boolean] = js.undefined
  var zeroline: js.UndefOr[Boolean] = js.undefined
  var zerolinecolor: js.UndefOr[Color] = js.undefined
  var zerolinewidth: js.UndefOr[Int] = js.undefined
}

@js.native
abstract trait BoxMode extends js.Any
object BoxMode {
  val group = "group".asInstanceOf[BoxMode]
  val overlay = "overlay".asInstanceOf[BoxMode]
}

@js.native
abstract trait BarMode extends js.Any
object BarMode {
  val stack = "stack".asInstanceOf[BarMode]
  val group = "group".asInstanceOf[BarMode]
  val overlay = "overlay".asInstanceOf[BarMode]
  val relative = "relative".asInstanceOf[BarMode]
}

@js.native
abstract trait BarNorm extends js.Any
object BarNorm {
  val empty = "".asInstanceOf[BarMode]
  val fraction = "fraction".asInstanceOf[BarMode]
  val percent = "percent".asInstanceOf[BarMode]
}

@jsenrich trait Layout extends js.Object {
  //var annotations
  var autosize: js.UndefOr[Boolean] = js.undefined
  //var angularaxis
  var barmode: js.UndefOr[BarMode] = js.undefined
  var barnorm: js.UndefOr[BarNorm] = js.undefined
  var bargap: js.UndefOr[Double] = js.undefined
  var bargroupgap: js.UndefOr[Double] = js.undefined
  var boxgap: js.UndefOr[Double] = js.undefined
  var boxgroupgap: js.UndefOr[Double] = js.undefined
  var calendar: js.UndefOr[Calendar] = js.undefined
  var clickmode: js.UndefOr[String] = js.undefined
  var coloraxis: js.UndefOr[ColorAxis] = js.undefined
  var colorscale: js.UndefOr[ColorScale] = js.undefined
  var colorway: js.UndefOr[String] = js.undefined // String => #1f77b4 string or Int?
  var datarevision: js.UndefOr[String | Int] = js.undefined
  //var direction
  var dragmode: js.UndefOr[DragMode] = js.undefined
  var editrevision: js.UndefOr[String | Int] = js.undefined
  var extendpiecolors: js.UndefOr[Boolean] = js.undefined
  var extendsunburstcolors: js.UndefOr[Boolean] = js.undefined
  var font: js.UndefOr[Font] = js.undefined
  //var geo
  var grid: js.UndefOr[Grid] = js.undefined
  var hidesources: js.UndefOr[Boolean] = js.undefined
  var hiddenlabels: js.UndefOr[Double] = js.undefined
  var height: js.UndefOr[Int] = js.undefined
  var hoverdistance: js.UndefOr[Int] = js.undefined
  var hoverlabel: js.UndefOr[HoverLabel] = js.undefined
  var hovermode: js.UndefOr[HoverMode] = js.undefined
  //var images
  var meta: js.UndefOr[String | Int] = js.undefined
  var margin: js.UndefOr[Margin] = js.undefined
  var orientation: js.UndefOr[Orientation] = js.undefined // v or h
  var paper_bgcolor: js.UndefOr[String] = js.undefined
  var plot_bgcolor: js.UndefOr[String] = js.undefined
  var piecolorway: js.UndefOr[js.Array[Color]] = js.undefined
  //var radialaxis
  //var scene
  //var shapes
  var selectdirection: js.UndefOr[SelectDirection] = js.undefined
  var separators: js.UndefOr[String] = js.undefined
  var selectionrevision: js.UndefOr[String | Int] = js.undefined
  //var sliders
  var spikedistance: js.UndefOr[Int] = js.undefined
  var sunburstcolorway: js.UndefOr[js.Array[Color]] = js.undefined
  var showlegend: js.UndefOr[Boolean] = js.undefined
  var template: js.UndefOr[js.Any] = js.undefined // ??? WTF?
  //var ternary
  var title: js.UndefOr[Title] = js.undefined
  var transition: js.UndefOr[Transition] = js.undefined
  //var updatemenus
  //var violinmode
  //var violingap
  //var violingroupgap
  //var waterfalllmode
  //var waterfallgap
  //var waterfallgroupgap
  var xaxis: js.UndefOr[Axis] = js.undefined
  var xaxis2: js.UndefOr[Axis] = js.undefined
  var xaxis3: js.UndefOr[Axis] = js.undefined
  var xaxis4: js.UndefOr[Axis] = js.undefined
  var xaxis5: js.UndefOr[Axis] = js.undefined
  var xaxis6: js.UndefOr[Axis] = js.undefined
  var xaxis7: js.UndefOr[Axis] = js.undefined
  var xaxis8: js.UndefOr[Axis] = js.undefined
  var xaxis9: js.UndefOr[Axis] = js.undefined
  var yaxis: js.UndefOr[Axis] = js.undefined
  var yaxis2: js.UndefOr[Axis] = js.undefined
  var yaxis3: js.UndefOr[Axis] = js.undefined
  var yaxis4: js.UndefOr[Axis] = js.undefined
  var yaxis5: js.UndefOr[Axis] = js.undefined
  var yaxis6: js.UndefOr[Axis] = js.undefined
  var yaxis7: js.UndefOr[Axis] = js.undefined
  var yaxis8: js.UndefOr[Axis] = js.undefined
  var yaxis9: js.UndefOr[Axis] = js.undefined
  var uniformtext: js.UndefOr[UniformText] = js.undefined
  var uirevision: js.UndefOr[String | Int] = js.undefined
  var width: js.UndefOr[Int] = js.undefined

}

@jsenrich trait Frame extends js.Object {
  var group: js.UndefOr[String] = js.undefined
  var name: js.UndefOr[String] = js.undefined
  var traces: js.UndefOr[js.Array[Int]] = js.undefined
  var baseframe: js.UndefOr[String] = js.undefined
  val data: js.Array[Data]
  var layout: js.UndefOr[Layout] = js.undefined
}

// trait Transition extends js.Object {
//   var duration:js.UndefOr[Int] = js.undefined
//   var easing: js.UndefOr[String] = js.undefined
// }

@jsenrich trait Config extends js.Object {
  var autosizable: js.UndefOr[Boolean] = js.undefined
  var displayModeBar: js.UndefOr[String | Boolean] = js.undefined // hover
  var displaylogo: js.UndefOr[Boolean] = js.undefined
  var editable: js.UndefOr[Boolean] = js.undefined
  var fillFrame: js.UndefOr[Boolean] = js.undefined
  var frameMargins: js.UndefOr[Int] = js.undefined
  var linkText: js.UndefOr[String] = js.undefined
  var locale: js.UndefOr[String] = js.undefined
  var logging: js.UndefOr[Boolean | Int] = js.undefined // 0,1,2
  var mapboxAccessToken: js.UndefOr[String] = js.undefined
  var plotGlPixelRatio: js.UndefOr[Double] = js.undefined
  var queueLength: js.UndefOr[Int] = js.undefined
  var responsive: js.UndefOr[Boolean] = js.undefined
  var scrollZoom: js.UndefOr[Boolean] = js.undefined
  var setBackground: js.UndefOr[js.Function0[String]] = js.undefined // string, opaque, transparent
  var showTips: js.UndefOr[Boolean] = js.undefined
  var showAxisDragHandles: js.UndefOr[Boolean] = js.undefined
  var showAxisRangeEntyrBoxes: js.UndefOr[Boolean] = js.undefined
  var showLink: js.UndefOr[Boolean] = js.undefined
  var sendData: js.UndefOr[Boolean] = js.undefined
  var showSources: js.UndefOr[Boolean] = js.undefined
  var staticPlot: js.UndefOr[Boolean] = js.undefined
  var topojsonURL: js.UndefOr[String] = js.undefined
}

@jsenrich trait Point extends js.Object {
  var x: js.UndefOr[Double] = js.undefined
  var y: js.UndefOr[Double] = js.undefined
  var z: js.UndefOr[Double] = js.undefined
}

trait PlotScatterDataPoint extends js.Object {}

trait PlotDatum extends js.Object {}

@js.native
trait PlotCoordinate extends js.Object {
  val x: Double = js.native
  val y: Double = js.native
  val pointnumber: Double = js.native
}

@js.native
trait PlotSelectionEvent extends js.Object {
  val points: js.Array[PlotDatum] = js.native
  val range: js.UndefOr[SelectionRange] = js.undefined
  val lassoPoints: js.UndefOr[SelectionRange] = js.undefined
}

@js.native
trait SelectionRange extends js.Object {
  val x: js.Array[Double]
  val y: js.Array[Double]
}

@js.native
trait PlotMouseEvent extends js.Object {
  val points: js.Array[PlotDatum]
  val event: dom.MouseEvent
}

@js.native
abstract trait HoverInfo extends js.Any
object HoverInfo { 
    val all = "all".asInstanceOf[HoverInfo]
    val none = "none".asInstanceOf[HoverInfo]
    val skip = "skip".asInstanceOf[HoverInfo]
    val name = "name".asInstanceOf[HoverInfo]
    val text = "text".asInstanceOf[HoverInfo]
    val x= "x".asInstanceOf[HoverInfo]
    val y= "y".asInstanceOf[HoverInfo]
    val z = "z".asInstanceOf[HoverInfo]
    
    implicit class RichHoverInfo(private val hi: HoverInfo) extends AnyVal {
        def and(that: HoverInfo) = s"$hi+$that"
    }
}

// need to add the rest here
@js.native
abstract trait PlotType extends js.Any
object PlotType {
  val bar = "bar".asInstanceOf[PlotType]
  val box = "box".asInstanceOf[PlotType]
  val heatmap = "heatmap".asInstanceOf[PlotType]
  val area = "area".asInstanceOf[PlotType]
  val pie = "pie".asInstanceOf[PlotType]
  val contour = "contour".asInstanceOf[PlotType]
  val surface = "surface".asInstanceOf[PlotType]
  val mesh = "mesh".asInstanceOf[PlotType]
  val histogram = "histogram".asInstanceOf[PlotType]
  val scatter = "scatter".asInstanceOf[PlotType]
  val splom = "splom".asInstanceOf[PlotType]
}

trait PlotMarker extends js.Object

trait Trace extends js.Object {
  /*@JSName("type")
  val plotType: PlotType
  */
  val `type`: PlotType|String
}

trait TraceInit extends js.Object {
  //@JSName("type")
  //var plotType: js.UndefOr[PlotType] = js.undefined
  var `type`: js.UndefOr[PlotType|String] = js.undefined
}

@js.native
abstract trait Orientation extends js.Any
object Orientation {
  val horizontal = "h".asInstanceOf[Orientation]
  val vertical = "v".asInstanceOf[Orientation]
}



trait PlotData extends js.Object {
  var boxmean: js.UndefOr[String | Boolean] = js.undefined
  //var branchvalues
  //var colorscale
  var connectgaps: js.UndefOr[Boolean] = js.undefined
  var customdata: js.UndefOr[js.Array[Datum]] = js.undefined
  //var delta
  //var domain
  var direction: js.UndefOr[String] = js.undefined
  var fill: js.UndefOr[String] = js.undefined
  var fillcolor: js.UndefOr[String] = js.undefined
  //var gauge
  var hole: js.UndefOr[Double] = js.undefined
  var hoveron: js.UndefOr[String] = js.undefined
  var hoverinfo: js.UndefOr[String|HoverInfo] = js.undefined
  var hoverlabel: js.UndefOr[Any] = js.undefined
  var hovertemplate: js.UndefOr[String | js.Array[String]] = js.undefined
  var hovertext: js.UndefOr[String | js.Array[String]] = js.undefined
  var labels: js.UndefOr[js.Array[Datum]] = js.undefined
  var lat: js.UndefOr[js.Array[Datum]] = js.undefined
  var legendgroup: js.UndefOr[String] = js.undefined
  var line: js.UndefOr[Line] = js.undefined
  var long: js.UndefOr[js.Array[Datum]] = js.undefined
  var marker: js.UndefOr[PlotMarker] = js.undefined
  var mode: js.UndefOr[String] = js.undefined
  var name: js.UndefOr[String] = js.undefined
  //var number
  var opacity: js.UndefOr[Double] = js.undefined
  var orientation: js.UndefOr[Orientation] = js.undefined
  var parents: js.UndefOr[js.Array[String]] = js.undefined
  var r: js.UndefOr[js.Array[Datum]] = js.undefined
  var rotation: js.UndefOr[Double] = js.undefined
  //var selectedpoints
  var showscale: js.UndefOr[Boolean] = js.undefined
  var showlegend: js.UndefOr[Boolean] = js.undefined
  var stackgroup: js.UndefOr[String] = js.undefined
  var text: js.UndefOr[String | js.Array[String]] = js.undefined
  var textinfo: js.UndefOr[String] = js.undefined
  var textposition: js.UndefOr[String] = js.undefined
  var textfont: js.UndefOr[Font] = js.undefined
  var title: js.UndefOr[DataTitle] = js.undefined
  var theta: js.UndefOr[js.Array[Datum]] = js.undefined
  var transpose: js.UndefOr[Boolean] = js.undefined
  //var transforms
  var visible: js.UndefOr[Boolean | String] = js.undefined // legendonly
  var width: js.UndefOr[Double | js.Array[Double]] = js.undefined // legendonly
  var xaxis: js.UndefOr[String] = js.undefined
  var xgap: js.UndefOr[Int] = js.undefined // legendonly
  var yaxis: js.UndefOr[String] = js.undefined
  var ygap: js.UndefOr[Int] = js.undefined // legendonly
  var value: js.UndefOr[Double] = js.undefined
  var values: js.UndefOr[js.Array[Datum]] = js.undefined
  //var visible: js.UndefOr[Boolean] = js.undefined
  //var zsmooth
}

trait PlotDataInit extends PlotData with TraceInit {
  var x: js.UndefOr[js.Array[Datum]|js.Array[js.Array[Datum]]|js.typedarray.TypedArray[_,_]] = js.undefined
  var y: js.UndefOr[js.Array[Datum]|js.Array[js.Array[Datum]]|js.typedarray.TypedArray[_,_]] = js.undefined
}

object PlotDataInit {
  implicit class RichPlotDataInit(private val p: PlotDataInit) extends AnyVal {
    def hasRequired: Data = p.asInstanceOf[Data]
  }
}

trait Data extends PlotData with Trace { 
  
}

trait BarTrace extends Data {
  val x: js.Array[Datum]|js.Array[js.Array[Datum]]|js.typedarray.TypedArray[_,_]
  val y: js.Array[Datum]|js.Array[js.Array[Datum]]|js.typedarray.TypedArray[_,_]
  var x0: js.UndefOr[Datum] = js.undefined
  var y0: js.UndefOr[Datum] = js.undefined
  var ids: js.UndefOr[js.Array[String]] = js.undefined
}

@js.native
trait PlotlyHTMLElement extends dom.html.Element {
  def removeAllListeners(handler: String): Unit = js.native
}

@js.native
@JSImport("plotly.js", JSImport.Namespace)
object module extends js.Object {
  // ...

  def plot(
    root: Root,
    data: DatumArray,
    layout: js.UndefOr[Layout] = js.undefined,
    config: js.UndefOr[Config] = js.undefined
  ): js.Promise[PlotlyHTMLElement] = js.native
  def newPlot(
    root: Root,
    data: js.Array[Data],
    layout: js.UndefOr[Layout] = js.undefined,
    config: js.UndefOr[Config] = js.undefined
  ): js.Promise[PlotlyHTMLElement] = js.native
  def redraw(root: Root): js.Promise[PlotlyHTMLElement] = js.native

  def purge(root: Root): Unit = js.native
}
