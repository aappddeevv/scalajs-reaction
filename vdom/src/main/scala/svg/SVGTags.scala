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

package react
package vdom
package svgtags

import scala.annotation.unchecked.{ uncheckedVariance => uv }
import scala.scalajs.js
import js.|
import org.scalajs.dom

/** A props trait that takes all SVG props. */
trait AllSVGProps[+T <: dom.EventTarget] extends SVGAttributes[T @uv] with ClassAttributes[T @uv] {}

trait SVGTags {
  trait SVGProps extends SVGAttributes[dom.svg.SVG]
  final lazy val svg = tagt[SVGProps]("svg")
  trait AnimateProps extends SVGAttributes[dom.svg.SVG]
  final lazy val animate = tagt[AnimateProps]("animate")
  trait CircleProps extends SVGAttributes[dom.svg.Circle]
  final lazy val circle = tagt[CircleProps]("circle")
  trait DefsProps extends SVGAttributes[dom.svg.Defs]
  final lazy val defs = tagt[DefsProps]("defs")
  trait EllipseProps extends SVGAttributes[dom.svg.Ellipse]
  final lazy val ellipse = tagt[EllipseProps]("ellipse")
  trait GProps extends SVGAttributes[dom.svg.G]
  final lazy val g = tagt[GProps]("g")
  trait ImageProps extends SVGAttributes[dom.svg.Image]
  final lazy val image = tagt[ImageProps]("image")
  trait LineProps extends SVGAttributes[dom.svg.Line]
  final lazy val line = tagt[LineProps]("line")
  trait LinearGradientProps extends SVGAttributes[dom.svg.LinearGradient]
  final lazy val linearGradient = tagt[LinearGradientProps]("linearGradient")
  trait MaskProps extends SVGAttributes[dom.svg.Mask]
  final lazy val mask = tagt[MaskProps]("mask")
  trait PathProps extends SVGAttributes[dom.svg.Path]
  final lazy val path = tagt[PathProps]("path")
  trait PatternProps extends SVGAttributes[dom.svg.Pattern]
  final lazy val pattern = tagt[PatternProps]("pattern")
  trait PolygonProps extends SVGAttributes[dom.svg.Polygon]
  final lazy val polygon = tagt[PolygonProps]("polygon")
  trait PolylineProps extends SVGAttributes[dom.svg.Polyline]
  final lazy val polyline = tagt[PolylineProps]("polyline")
  trait RadialGradientProps extends SVGAttributes[dom.svg.RadialGradient]
  final lazy val radialGradient = tagt[RadialGradientProps]("radialGradient")
  // not sure why dom.svg.Rect does not work
  trait RectProps extends SVGAttributes[dom.svg.SVG]
  final lazy val rect = tagt[RectProps]("rect")
  trait StopProps extends SVGAttributes[dom.svg.Stop]
  final lazy val stop = tagt[StopProps]("stop")
  trait SymbolProps extends SVGAttributes[dom.svg.Symbol]
  final lazy val symbol = tagt[SymbolProps]("symbol")
  trait TextProps extends SVGAttributes[dom.svg.Text]
  final lazy val text = tagt[TextProps]("text")
  trait TspanProps extends SVGAttributes[dom.svg.TSpan]
  final lazy val tspan = tagt[TspanProps]("tspan")
  trait UseProps extends SVGAttributes[dom.svg.Use]
  final lazy val use = tagt[UseProps]("use")
}

trait SVGAttributesBase[+T <: dom.EventTarget] extends js.Object {
  var className: js.UndefOr[String] = js.undefined
  var color: js.UndefOr[String] = js.undefined
  var height: js.UndefOr[Int | String] = js.undefined
  var id: js.UndefOr[String] = js.undefined
  var lang: js.UndefOr[String] = js.undefined
  var max: js.UndefOr[Int | String] = js.undefined
  var method: js.UndefOr[String] = js.undefined
  var min: js.UndefOr[Int | String] = js.undefined
  var media: js.UndefOr[String] = js.undefined
  var name: js.UndefOr[String] = js.undefined
  var style: js.UndefOr[js.Object | js.Dynamic] = js.undefined
  var target: js.UndefOr[String] = js.undefined
  var `type`: js.UndefOr[String] = js.undefined
  var width: js.UndefOr[String | Int] = js.undefined
  var role: js.UndefOr[String] = js.undefined
  var tabIndex: js.UndefOr[Int] = js.undefined

  // SVG Specific attributes

  var accentHeight: js.UndefOr[String | Double] = js.undefined
  var accumulate: js.UndefOr[String] = js.undefined //?: "none" | "sum";
  var additive: js.UndefOr[String] = js.undefined //?: "replace" | "sum";
  var alignmentBaseline
    : js.UndefOr[String] = js.undefined //?: "auto" | "baseline" | "before-edge" | "text-before-edge" | "middle" | "central" | "after-edge" |
//   "text-after-edge" | "ideographic" | "alphabetic" | "hanging" | "mathematical" | "inherit";
  var allowReorder: js.UndefOr[String] = js.undefined //?: "no" | "yes";
  var alphabetic: js.UndefOr[String | Double] = js.undefined
  var amplitude: js.UndefOr[String | Double] = js.undefined
  var arabicForm: js.UndefOr[String] = js.undefined // ?: "initial" | "medial" | "terminal" | "isolated";
  var ascent: js.UndefOr[String | Double] = js.undefined
  var attributeName: js.UndefOr[String] = js.undefined;
  var attributeType: js.UndefOr[String] = js.undefined;
  var autoReverse: js.UndefOr[String | Double] = js.undefined
  var azimuth: js.UndefOr[String | Double] = js.undefined
  var baseFrequency: js.UndefOr[String | Double] = js.undefined
  var baselineShift: js.UndefOr[String | Double] = js.undefined
  var baseProfile: js.UndefOr[String | Double] = js.undefined
  var bbox: js.UndefOr[String | Double] = js.undefined
  var begin: js.UndefOr[String | Double] = js.undefined
  var bias: js.UndefOr[String | Double] = js.undefined
  var by: js.UndefOr[String | Double] = js.undefined
  var calcMode: js.UndefOr[String | Double] = js.undefined
  var capHeight: js.UndefOr[String | Double] = js.undefined
  var clip: js.UndefOr[String | Double] = js.undefined
  var clipPath: js.UndefOr[String] = js.undefined;
  var clipPathUnits: js.UndefOr[String | Double] = js.undefined
  var clipRule: js.UndefOr[String | Double] = js.undefined
  var colorInterpolation: js.UndefOr[String | Double] = js.undefined
  var colorInterpolationFilters: js.UndefOr[String] = js.undefined //?: "auto" | "sRGB" | "linearRGB" | "inherit";
  var colorProfile: js.UndefOr[String | Double] = js.undefined
  var colorRendering: js.UndefOr[String | Double] = js.undefined
  var contentScriptType: js.UndefOr[String | Double] = js.undefined
  var contentStyleType: js.UndefOr[String | Double] = js.undefined
  var cursor: js.UndefOr[String | Double] = js.undefined
  var cx: js.UndefOr[String | Double] = js.undefined
  var cy: js.UndefOr[String | Double] = js.undefined
  var d: js.UndefOr[String] = js.undefined;
  var decelerate: js.UndefOr[String | Double] = js.undefined
  var descent: js.UndefOr[String | Double] = js.undefined
  var diffuseConstant: js.UndefOr[String | Double] = js.undefined
  var direction: js.UndefOr[String | Double] = js.undefined
  var display: js.UndefOr[String | Double] = js.undefined
  var divisor: js.UndefOr[String | Double] = js.undefined
  var dominantBaseline: js.UndefOr[String | Double] = js.undefined
  var dur: js.UndefOr[String | Double] = js.undefined
  var dx: js.UndefOr[String | Double] = js.undefined
  var dy: js.UndefOr[String | Double] = js.undefined
  var edgeMode: js.UndefOr[String | Double] = js.undefined
  var elevation: js.UndefOr[String | Double] = js.undefined
  var enableBackground: js.UndefOr[String | Double] = js.undefined
  var end: js.UndefOr[String | Double] = js.undefined
  var exponent: js.UndefOr[String | Double] = js.undefined
  var externalResourcesRequired: js.UndefOr[String | Double] = js.undefined
  var fill: js.UndefOr[String] = js.undefined;
  var fillOpacity: js.UndefOr[String | Double] = js.undefined
  var fillRule: js.UndefOr[String] = js.undefined //?: "nonzero" | "evenodd" | "inherit";
  var filter: js.UndefOr[String] = js.undefined;
  var filterRes: js.UndefOr[String | Double] = js.undefined
  var filterUnits: js.UndefOr[String | Double] = js.undefined
  var floodColor: js.UndefOr[String | Double] = js.undefined
  var floodOpacity: js.UndefOr[String | Double] = js.undefined
  var focusable: js.UndefOr[String | Double] = js.undefined
  var fontFamily: js.UndefOr[String] = js.undefined;
  var fontSize: js.UndefOr[String | Double] = js.undefined
  var fontSizeAdjust: js.UndefOr[String | Double] = js.undefined
  var fontStretch: js.UndefOr[String | Double] = js.undefined
  var fontStyle: js.UndefOr[String | Double] = js.undefined
  var fontVariant: js.UndefOr[String | Double] = js.undefined
  var fontWeight: js.UndefOr[String | Double] = js.undefined
  var format: js.UndefOr[String | Double] = js.undefined
  var from: js.UndefOr[String | Double] = js.undefined
  var fx: js.UndefOr[String | Double] = js.undefined
  var fy: js.UndefOr[String | Double] = js.undefined
  var g1: js.UndefOr[String | Double] = js.undefined
  var g2: js.UndefOr[String | Double] = js.undefined
  var glyphName: js.UndefOr[String | Double] = js.undefined
  var glyphOrientationHorizontal: js.UndefOr[String | Double] = js.undefined
  var glyphOrientationVertical: js.UndefOr[String | Double] = js.undefined
  var glyphRef: js.UndefOr[String | Double] = js.undefined
  var gradientTransform: js.UndefOr[String] = js.undefined;
  var gradientUnits: js.UndefOr[String] = js.undefined;
  var hanging: js.UndefOr[String | Double] = js.undefined
  var horizAdvX: js.UndefOr[String | Double] = js.undefined
  var horizOriginX: js.UndefOr[String | Double] = js.undefined
  var ideographic: js.UndefOr[String | Double] = js.undefined
  var imageRendering: js.UndefOr[String | Double] = js.undefined
  var in2: js.UndefOr[String | Double] = js.undefined
  var in: js.UndefOr[String] = js.undefined;
  var intercept: js.UndefOr[String | Double] = js.undefined
  var k1: js.UndefOr[String | Double] = js.undefined
  var k2: js.UndefOr[String | Double] = js.undefined
  var k3: js.UndefOr[String | Double] = js.undefined
  var k4: js.UndefOr[String | Double] = js.undefined
  var k: js.UndefOr[String | Double] = js.undefined
  var kernelMatrix: js.UndefOr[String | Double] = js.undefined
  var kernelUnitLength: js.UndefOr[String | Double] = js.undefined
  var kerning: js.UndefOr[String | Double] = js.undefined
  var keyPoints: js.UndefOr[String | Double] = js.undefined
  var keySplines: js.UndefOr[String | Double] = js.undefined
  var keyTimes: js.UndefOr[String | Double] = js.undefined
  var lengthAdjust: js.UndefOr[String | Double] = js.undefined
  var letterSpacing: js.UndefOr[String | Double] = js.undefined
  var lightingColor: js.UndefOr[String | Double] = js.undefined
  var limitingConeAngle: js.UndefOr[String | Double] = js.undefined
  var local: js.UndefOr[String | Double] = js.undefined
  var markerEnd: js.UndefOr[String] = js.undefined;
  var markerHeight: js.UndefOr[String | Double] = js.undefined
  var markerMid: js.UndefOr[String] = js.undefined;
  var markerStart: js.UndefOr[String] = js.undefined;
  var markerUnits: js.UndefOr[String | Double] = js.undefined
  var markerWidth: js.UndefOr[String | Double] = js.undefined
  var mask: js.UndefOr[String] = js.undefined;
  var maskContentUnits: js.UndefOr[String | Double] = js.undefined
  var maskUnits: js.UndefOr[String | Double] = js.undefined
  var mathematical: js.UndefOr[String | Double] = js.undefined
  var mode: js.UndefOr[String | Double] = js.undefined
  var numOctaves: js.UndefOr[String | Double] = js.undefined
  var offset: js.UndefOr[String | Double] = js.undefined
  var opacity: js.UndefOr[String | Double] = js.undefined
  var operator: js.UndefOr[String | Double] = js.undefined
  var order: js.UndefOr[String | Double] = js.undefined
  var orient: js.UndefOr[String | Double] = js.undefined
  var orientation: js.UndefOr[String | Double] = js.undefined
  var origin: js.UndefOr[String | Double] = js.undefined
  var overflow: js.UndefOr[String | Double] = js.undefined
  var overlinePosition: js.UndefOr[String | Double] = js.undefined
  var overlineThickness: js.UndefOr[String | Double] = js.undefined
  var paintOrder: js.UndefOr[String | Double] = js.undefined
  var panose1: js.UndefOr[String | Double] = js.undefined
  var pathLength: js.UndefOr[String | Double] = js.undefined
  var patternContentUnits: js.UndefOr[String] = js.undefined;
  var patternTransform: js.UndefOr[String | Double] = js.undefined
  var patternUnits: js.UndefOr[String] = js.undefined;
  var pointerEvents: js.UndefOr[String | Double] = js.undefined
  var points: js.UndefOr[String] = js.undefined;
  var pointsAtX: js.UndefOr[String | Double] = js.undefined
  var pointsAtY: js.UndefOr[String | Double] = js.undefined
  var pointsAtZ: js.UndefOr[String | Double] = js.undefined
  var preserveAlpha: js.UndefOr[String | Double] = js.undefined
  var preserveAspectRatio: js.UndefOr[String] = js.undefined;
  var primitiveUnits: js.UndefOr[String | Double] = js.undefined
  var r: js.UndefOr[String | Double] = js.undefined
  var radius: js.UndefOr[String | Double] = js.undefined
  var refX: js.UndefOr[String | Double] = js.undefined
  var refY: js.UndefOr[String | Double] = js.undefined
  var renderingIntent: js.UndefOr[String | Double] = js.undefined
  var repeatCount: js.UndefOr[String | Double] = js.undefined
  var repeatDur: js.UndefOr[String | Double] = js.undefined
  var requiredExtensions: js.UndefOr[String | Double] = js.undefined
  var requiredFeatures: js.UndefOr[String | Double] = js.undefined
  var restart: js.UndefOr[String | Double] = js.undefined
  var result: js.UndefOr[String] = js.undefined;
  var rotate: js.UndefOr[String | Double] = js.undefined
  var rx: js.UndefOr[String | Double] = js.undefined
  var ry: js.UndefOr[String | Double] = js.undefined
  var scale: js.UndefOr[String | Double] = js.undefined
  var seed: js.UndefOr[String | Double] = js.undefined
  var shapeRendering: js.UndefOr[String | Double] = js.undefined
  var slope: js.UndefOr[String | Double] = js.undefined
  var spacing: js.UndefOr[String | Double] = js.undefined
  var specularConstant: js.UndefOr[String | Double] = js.undefined
  var specularExponent: js.UndefOr[String | Double] = js.undefined
  var speed: js.UndefOr[String | Double] = js.undefined
  var spreadMethod: js.UndefOr[String] = js.undefined;
  var startOffset: js.UndefOr[String | Double] = js.undefined
  var stdDeviation: js.UndefOr[String | Double] = js.undefined
  var stemh: js.UndefOr[String | Double] = js.undefined
  var stemv: js.UndefOr[String | Double] = js.undefined
  var stitchTiles: js.UndefOr[String | Double] = js.undefined
  var stopColor: js.UndefOr[String] = js.undefined;
  var stopOpacity: js.UndefOr[String | Double] = js.undefined
  var strikethroughPosition: js.UndefOr[String | Double] = js.undefined
  var strikethroughThickness: js.UndefOr[String | Double] = js.undefined
  var string: js.UndefOr[String | Double] = js.undefined
  var stroke: js.UndefOr[String] = js.undefined
  var strokeDasharray: js.UndefOr[String | Double] = js.undefined
  var strokeDashoffset: js.UndefOr[String | Double] = js.undefined
  var strokeLinecap: js.UndefOr[String] = js.undefined //?: "butt" | "round" | "square" | "inherit";
  var strokeLinejoin: js.UndefOr[String] = js.undefined //?: "miter" | "round" | "bevel" | "inherit";
  var strokeMiterlimit: js.UndefOr[String | Double] = js.undefined
  var strokeOpacity: js.UndefOr[String | Double] = js.undefined
  var strokeWidth: js.UndefOr[String | Double] = js.undefined
  var surfaceScale: js.UndefOr[String | Double] = js.undefined
  var systemLanguage: js.UndefOr[String | Double] = js.undefined
  var tableValues: js.UndefOr[String | Double] = js.undefined
  var targetX: js.UndefOr[String | Double] = js.undefined
  var targetY: js.UndefOr[String | Double] = js.undefined
  var textAnchor: js.UndefOr[String] = js.undefined
  var textDecoration: js.UndefOr[String | Double] = js.undefined
  var textLength: js.UndefOr[String | Double] = js.undefined
  var textRendering: js.UndefOr[String | Double] = js.undefined
  var to: js.UndefOr[String | Double] = js.undefined
  var transform: js.UndefOr[String] = js.undefined
  var u1: js.UndefOr[String | Double] = js.undefined
  var u2: js.UndefOr[String | Double] = js.undefined
  var underlinePosition: js.UndefOr[String | Double] = js.undefined
  var underlineThickness: js.UndefOr[String | Double] = js.undefined
  var unicode: js.UndefOr[String | Double] = js.undefined
  var unicodeBidi: js.UndefOr[String | Double] = js.undefined
  var unicodeRange: js.UndefOr[String | Double] = js.undefined
  var unitsPerEm: js.UndefOr[String | Double] = js.undefined
  var vAlphabetic: js.UndefOr[String | Double] = js.undefined
  var values: js.UndefOr[String] = js.undefined
  var vectorEffect: js.UndefOr[String | Double] = js.undefined
  var version: js.UndefOr[String] = js.undefined
  var vertAdvY: js.UndefOr[String | Double] = js.undefined
  var vertOriginX: js.UndefOr[String | Double] = js.undefined
  var vertOriginY: js.UndefOr[String | Double] = js.undefined
  var vHanging: js.UndefOr[String | Double] = js.undefined
  var vIdeographic: js.UndefOr[String | Double] = js.undefined
  var viewBox: js.UndefOr[String] = js.undefined
  var viewTarget: js.UndefOr[String | Double] = js.undefined
  var visibility: js.UndefOr[String | Double] = js.undefined
  var vMathematical: js.UndefOr[String | Double] = js.undefined
  var widths: js.UndefOr[String | Double] = js.undefined
  var wordSpacing: js.UndefOr[String | Double] = js.undefined
  var writingMode: js.UndefOr[String | Double] = js.undefined
  var x1: js.UndefOr[String | Double] = js.undefined
  var x2: js.UndefOr[String | Double] = js.undefined
  var x: js.UndefOr[String | Double] = js.undefined
  var xChannelSelector: js.UndefOr[String] = js.undefined
  var xHeight: js.UndefOr[String | Double] = js.undefined
  var xlinkActuate: js.UndefOr[String] = js.undefined
  var xlinkArcrole: js.UndefOr[String] = js.undefined
  var xlinkHref: js.UndefOr[String] = js.undefined
  var xlinkRole: js.UndefOr[String] = js.undefined
  var xlinkShow: js.UndefOr[String] = js.undefined
  var xlinkTitle: js.UndefOr[String] = js.undefined
  var xlinkType: js.UndefOr[String] = js.undefined
  var xmlBase: js.UndefOr[String] = js.undefined
  var xmlLang: js.UndefOr[String] = js.undefined
  var xmlns: js.UndefOr[String] = js.undefined
  var xmlnsXlink: js.UndefOr[String] = js.undefined
  var xmlSpace: js.UndefOr[String] = js.undefined
  var y1: js.UndefOr[String | Double] = js.undefined
  var y2: js.UndefOr[String | Double] = js.undefined
  var y: js.UndefOr[String | Double] = js.undefined
  var yChannelSelector: js.UndefOr[String] = js.undefined
  var z: js.UndefOr[String | Double] = js.undefined
}

trait SVGAttributes[+T <: dom.EventTarget] extends SVGAttributesBase[T] with DOMAttributes[T]
