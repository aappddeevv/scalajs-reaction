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

import scala.scalajs.js

import js.|

import org.scalajs.dom

object styling {

  /** Merge styles left to right. Right takes higher precedence. */
  @inline def merge(styles: StyleAttr*): StyleAttr = {
    val result = js.Dictionary.empty[String]
    for (source <- styles) {
      for ((key, value) <- source.asInstanceOf[js.Dictionary[String]])
        result(key) = value
    }
    result.asInstanceOf[StyleAttr]
  }

  /**
   * Add a single style prop but it returns a dictionary since it can't be a
   * StyleAttr anymore.
   */
  @inline def unsafeAddProp(style: StyleAttr, name: String, value: String): js.Dictionary[String] =
    react
      .mergeJSObjects(style.asInstanceOf[js.Dynamic], js.Dynamic.literal(name -> value))
      .asInstanceOf[js.Dictionary[String]]

  /**
   * Merge a StyleAttr and a js.Dyanmic assume the returned value is StyleAttr.
   */
  @inline def mergeUnsafePropsTo(style: StyleAttr, unsafe: js.Dynamic) =
    merge(style, unsafe.asInstanceOf[StyleAttr]).asInstanceOf[StyleAttr]

  /**
   * Obtain a style attribute value. Assumes variable is at :root element via
   * :root.  Cache the CSSStyleDeclaration from window.getComputedStyle if you
   * read frequently. Obtain an el via `document.querySelector(class)` or
   * `document.getElementById`. You can use the psuedo-selector name of `:root`
   * for the HTML element.
   */
  @inline def getCSSVar(vname: String, el: dom.Element = dom.document.documentElement): String =
    dom.window.getComputedStyle(el, null).getPropertyValue(vname)

  /**
   * Set CSS variable on the :root element. Cache the computed style if you
   * update frequently.
   */
  @inline def setCSSVar(
    vname: String,
    value: String,
    el: dom.Element = dom.document.documentElement,
    priority: Option[String] = None
  ): Unit =
    dom.document.documentElement.asInstanceOf[dom.html.Element].style.setProperty(vname, value)
}

trait RawFontStyle extends js.Object {
  var font: js.UndefOr[String]                    = js.undefined
  var fontFamily: js.UndefOr[String]              = js.undefined
  var fontSize: js.UndefOr[String | Double]       = js.undefined
  var fontSizeAdjust: js.UndefOr[String | Double] = js.undefined
  var fontStretch: js.UndefOr[String | Double]    = js.undefined
  var fontStyle: js.UndefOr[String | Double]      = js.undefined
  var fontVariant: js.UndefOr[String | Double]    = js.undefined
  var fontWeight: js.UndefOr[String | Double]     = js.undefined
}

trait FontFace extends RawFontStyle {
  var src: js.UndefOr[String]                = js.undefined
  var unicodeRange: js.UndefOr[String]       = js.undefined
  var fontFeatureSetting: js.UndefOr[String] = js.undefined
}

@js.native
abstract trait BoxSizing extends js.Any
object BoxSizing { 
   val borderBox = "border-box".asInstanceOf[BoxSizing]
   val contentBox = "content-box".asInstanceOf[BoxSizing]
}

/** No safe/unsafe. */
@js.native
abstract trait JustifyContent extends js.Any 
object JustifyContent {
  val flexStart = "flex-start".asInstanceOf[JustifyContent]
  val flexEnd = "flex-end".asInstanceOf[JustifyContent]
  val center = "center".asInstanceOf[JustifyContent]
  val spaceBetween = "space-between".asInstanceOf[JustifyContent]
  val spaceAround = "space-around".asInstanceOf[JustifyContent]
  val spaceEvenly = "space-evenly".asInstanceOf[JustifyContent]
  val start = "start".asInstanceOf[JustifyContent]
  val end = "end".asInstanceOf[JustifyContent]
  val left = "left".asInstanceOf[JustifyContent]
  val right = "right".asInstanceOf[JustifyContent]
}

@js.native
abstract trait AlignItems extends js.Any
object AlignItems {
}

/**
 * Use to create a mildly useful style object. It won't typecheck the values themselves but
 * it will check the field themselves to ensure they are valid.
 */
trait RawStyleBase extends RawFontStyle {
  var azimuth: js.UndefOr[String]                    = js.undefined
  var background: js.UndefOr[String]                 = js.undefined
  var backgroundAttachment: js.UndefOr[String]       = js.undefined
  var backgroundColor: js.UndefOr[String]            = js.undefined
  var backgroundImage: js.UndefOr[String]            = js.undefined
  var backgroundPosition: js.UndefOr[String]         = js.undefined
  var backgroundRepeat: js.UndefOr[String]           = js.undefined
  var border: js.UndefOr[String | Int]               = js.undefined
  var borderCollapse: js.UndefOr[String]             = js.undefined
  var borderColor: js.UndefOr[String]                = js.undefined
  var borderSpacing: js.UndefOr[String]              = js.undefined
  var borderStyle: js.UndefOr[String]                = js.undefined
  var borderTop: js.UndefOr[String | Double]         = js.undefined
  var borderRight: js.UndefOr[String | Double]       = js.undefined
  var borderBottom: js.UndefOr[String | Double]      = js.undefined
  var borderLeft: js.UndefOr[String | Double]        = js.undefined
  var borderTopColor: js.UndefOr[String]             = js.undefined
  var borderRightColor: js.UndefOr[String]           = js.undefined
  var borderBottomColor: js.UndefOr[String]          = js.undefined
  var borderLeftColor: js.UndefOr[String]            = js.undefined
  var borderTopStyle: js.UndefOr[String]             = js.undefined
  var borderRightStyle: js.UndefOr[String]           = js.undefined
  var borderBottomStyle: js.UndefOr[String]          = js.undefined
  var borderLeftStyle: js.UndefOr[String]            = js.undefined
  var borderTopWidth: js.UndefOr[String | Double]    = js.undefined
  var borderRightWidth: js.UndefOr[String | Double]  = js.undefined
  var borderBottomWidth: js.UndefOr[String | Double] = js.undefined
  var borderLeftWidth: js.UndefOr[String | Double]   = js.undefined
  var borderWidth: js.UndefOr[String | Double]       = js.undefined
  var bottom: js.UndefOr[String | Double]            = js.undefined
  var captionSide: js.UndefOr[String]                = js.undefined
  var clear: js.UndefOr[String]                      = js.undefined
  var clip: js.UndefOr[String]                       = js.undefined
  var color: js.UndefOr[String]                      = js.undefined
  var content: js.UndefOr[String]                    = js.undefined
  var counterIncrement: js.UndefOr[String]           = js.undefined
  var counterReset: js.UndefOr[String]               = js.undefined
  var cue: js.UndefOr[String]                        = js.undefined
  var cueAfter: js.UndefOr[String]                   = js.undefined
  var cueBefore: js.UndefOr[String]                  = js.undefined
  var cursor: js.UndefOr[String]                     = js.undefined
  var direction: js.UndefOr[String]                  = js.undefined
  var display: js.UndefOr[String]                    = js.undefined
  var elevation: js.UndefOr[String]                  = js.undefined
  var emptyCells: js.UndefOr[String]                 = js.undefined
  var `float`: js.UndefOr[String]                      = js.undefined
  var height: js.UndefOr[String | Double]        = js.undefined
  
  var inset: js.UndefOr[String | Double]       = js.undefined
  var insetBlock: js.UndefOr[String | Double]       = js.undefined
  var insetBlockEnd: js.UndefOr[String | Double]       = js.undefined
  var insetBlockStart: js.UndefOr[String | Double]       = js.undefined
  var insetInline: js.UndefOr[String | Double]       = js.undefined
  var insetInlineEnd: js.UndefOr[String | Double]       = js.undefined
  var insetInlineStart: js.UndefOr[String | Double]       = js.undefined
  
  var left: js.UndefOr[String]                   = js.undefined
  var letterSpacing: js.UndefOr[String]          = js.undefined
  var lineHeight: js.UndefOr[String | Double]    = js.undefined
  var listStyle: js.UndefOr[String]              = js.undefined
  var listStyleImage: js.UndefOr[String]         = js.undefined
  var listStylePosition: js.UndefOr[String]      = js.undefined
  var listStyleType: js.UndefOr[String]          = js.undefined
  var margin: js.UndefOr[String | Double]        = js.undefined
  var marginBlockStart: js.UndefOr[String | Double]        = js.undefined
  var marginBlockEnd: js.UndefOr[String | Double]        = js.undefined
  var marginInline: js.UndefOr[String | Double]        = js.undefined
  var marginInlineEnd: js.UndefOr[String | Double]        = js.undefined
  var marginInlineStart: js.UndefOr[String | Double]        = js.undefined
  var marginTop: js.UndefOr[String | Double]     = js.undefined
  var marginRight: js.UndefOr[String | Double]   = js.undefined
  var marginBottom: js.UndefOr[String | Double]  = js.undefined
  var marginLeft: js.UndefOr[String | Double]    = js.undefined
  var markerOffset: js.UndefOr[String | Double]  = js.undefined
  var marks: js.UndefOr[String]                  = js.undefined
  
  //var maxHeight: js.UndefOr[String | Int]        = js.undefined
  var maxBlockSize: js.UndefOr[String | Int]        = js.undefined
  var maxInlineSize: js.UndefOr[String | Int]        = js.undefined
  var minInlineSize: js.UndefOr[String | Int]        = js.undefined
  
  var maxHeight: js.UndefOr[String | Int]        = js.undefined
  var maxWidth: js.UndefOr[String | Int]         = js.undefined
  var minHeight: js.UndefOr[String | Int]        = js.undefined
  var minWidth: js.UndefOr[String | Int]         = js.undefined
  var orphans: js.UndefOr[String]                = js.undefined
  var outline: js.UndefOr[String]                = js.undefined
  var outlineColor: js.UndefOr[String]           = js.undefined
  var outlineStyle: js.UndefOr[String]           = js.undefined
  var outlineWidth: js.UndefOr[String]           = js.undefined
  var overflow: js.UndefOr[String]               = js.undefined
  var overflowX: js.UndefOr[String]              = js.undefined
  var overflowY: js.UndefOr[String]              = js.undefined
  var padding: js.UndefOr[String | Double]       = js.undefined
  var paddingBlock: js.UndefOr[String | Double]       = js.undefined
  var paddingBlockEnd: js.UndefOr[String | Double]       = js.undefined
  var paddingBlockStart: js.UndefOr[String | Double]       = js.undefined
  var paddingInline: js.UndefOr[String | Double]       = js.undefined
  var paddingInlineEnd: js.UndefOr[String | Double]       = js.undefined
  var paddingInlineStart: js.UndefOr[String | Double]       = js.undefined
  //var padding: js.UndefOr[String | Double]       = js.undefined
  var paddingTop: js.UndefOr[String | Double]    = js.undefined
  var paddingRight: js.UndefOr[String | Double]  = js.undefined
  var paddingBottom: js.UndefOr[String | Double] = js.undefined
  var paddingLeft: js.UndefOr[String | Double]   = js.undefined
  var page: js.UndefOr[String]                   = js.undefined
  var pageBreakAfter: js.UndefOr[String]         = js.undefined
  var pageBreakBefore: js.UndefOr[String]        = js.undefined
  var pageBreakInside: js.UndefOr[String]        = js.undefined
  var pause: js.UndefOr[String]                  = js.undefined
  var pauseAfter: js.UndefOr[String]             = js.undefined
  var pauseBefore: js.UndefOr[String]            = js.undefined
  var pitch: js.UndefOr[String]                  = js.undefined
  var pitchRange: js.UndefOr[String]             = js.undefined
  var playDuring: js.UndefOr[String]             = js.undefined
  var position: js.UndefOr[String]               = js.undefined
  var quotes: js.UndefOr[String]                 = js.undefined
  var richness: js.UndefOr[String]               = js.undefined
  var right: js.UndefOr[String]                  = js.undefined
  var size: js.UndefOr[String]                   = js.undefined
  var speak: js.UndefOr[String]                  = js.undefined
  var speakHeader: js.UndefOr[String]            = js.undefined
  var speakNumeral: js.UndefOr[String]           = js.undefined
  var speakPunctuation: js.UndefOr[String]       = js.undefined
  var speechRate: js.UndefOr[String]             = js.undefined
  var stress: js.UndefOr[String]                 = js.undefined
  var tableLayout: js.UndefOr[String]            = js.undefined
  var textAlign: js.UndefOr[String]              = js.undefined
  var textDecoration: js.UndefOr[String]         = js.undefined
  var textIndent: js.UndefOr[String]             = js.undefined
  var textShadow: js.UndefOr[String]             = js.undefined
  var textTransform: js.UndefOr[String]          = js.undefined
  var top: js.UndefOr[String]                    = js.undefined
  var unicodeBidi: js.UndefOr[String]            = js.undefined
  var verticalAlign: js.UndefOr[String]          = js.undefined
  var visibility: js.UndefOr[String]             = js.undefined
  var voiceFamily: js.UndefOr[String]            = js.undefined
  var volume: js.UndefOr[String]                 = js.undefined
  var whiteSpace: js.UndefOr[String]             = js.undefined
  var widows: js.UndefOr[String]                 = js.undefined
  var width: js.UndefOr[String | Double]         = js.undefined
  var wordSpacing: js.UndefOr[String]            = js.undefined
  var zIndex: js.UndefOr[String]                 = js.undefined
  /* Below properties based on https://www.w3.org/Style/CSS/all-properties */
  /* Color Level 3 - REC */
  var opacity: js.UndefOr[String | Double] = js.undefined
  /* Backgrounds and Borders Level 3 - CR */
  /* backgroundRepeat - already defined by CSS2Properties */
  /* backgroundAttachment - already defined by CSS2Properties */
  var backgroundOrigin: js.UndefOr[String]                 = js.undefined
  var backgroundSize: js.UndefOr[String]                   = js.undefined
  var backgroundClip: js.UndefOr[String]                   = js.undefined
  var borderRadius: js.UndefOr[String | Double]            = js.undefined
  var borderTopLeftRadius: js.UndefOr[String | Double]     = js.undefined
  var borderTopRightRadius: js.UndefOr[String | Double]    = js.undefined
  var borderBottomLeftRadius: js.UndefOr[String | Double]  = js.undefined
  var borderBottomRightRadius: js.UndefOr[String | Double] = js.undefined
  var borderImage: js.UndefOr[String]                      = js.undefined
  var borderImageSource: js.UndefOr[String]                = js.undefined
  var borderImageSlice: js.UndefOr[String]                 = js.undefined
  var borderImageWidth: js.UndefOr[String]                 = js.undefined
  var borderImageOutset: js.UndefOr[String]                = js.undefined
  var borderImageRepeat: js.UndefOr[String]                = js.undefined
  var boxShadow: js.UndefOr[String]                        = js.undefined
  /* Multi-column Layout - CR */
  var columns: js.UndefOr[String]         = js.undefined
  var columnCount: js.UndefOr[String]     = js.undefined
  var columnFill: js.UndefOr[String]      = js.undefined
  var columnGap: js.UndefOr[String]       = js.undefined
  var columnRule: js.UndefOr[String]      = js.undefined
  var columnRuleColor: js.UndefOr[String] = js.undefined
  var columnRuleStyle: js.UndefOr[String] = js.undefined
  var columnRuleWidth: js.UndefOr[String] = js.undefined
  var columnSpan: js.UndefOr[String]      = js.undefined
  var columnWidth: js.UndefOr[String]     = js.undefined
  var breakAfter: js.UndefOr[String]      = js.undefined
  var breakBefore: js.UndefOr[String]     = js.undefined
  var breakInside: js.UndefOr[String]     = js.undefined
  /* Speech - CR */
  var rest: js.UndefOr[String]          = js.undefined
  var restAfter: js.UndefOr[String]     = js.undefined
  var restBefore: js.UndefOr[String]    = js.undefined
  var speakAs: js.UndefOr[String]       = js.undefined
  var voiceBalance: js.UndefOr[String]  = js.undefined
  var voiceDuration: js.UndefOr[String] = js.undefined
  var voicePitch: js.UndefOr[String]    = js.undefined
  var voiceRange: js.UndefOr[String]    = js.undefined
  var voiceRate: js.UndefOr[String]     = js.undefined
  var voiceStress: js.UndefOr[String]   = js.undefined
  var voiceVolume: js.UndefOr[String]   = js.undefined
  /* Image Values and Replaced Content Level 3 - CR */
  var objectFit: js.UndefOr[String]        = js.undefined
  var objectPosition: js.UndefOr[String]   = js.undefined
  var imageResolution: js.UndefOr[String]  = js.undefined
  var imageOrientation: js.UndefOr[String] = js.undefined

  /* Flexible Box Layout - CR */
  var alignContent: js.UndefOr[String]     = js.undefined
  var alignItems: js.UndefOr[String|AlignItems]       = js.undefined
  var alignSelf: js.UndefOr[String]        = js.undefined
  var flex: js.UndefOr[String]             = js.undefined
  var flexBasis: js.UndefOr[String]        = js.undefined
  var flexDirection: js.UndefOr[String]    = js.undefined
  var flexFlow: js.UndefOr[String]         = js.undefined
  var flexGrow: js.UndefOr[String | Int]   = js.undefined
  var flexShrink: js.UndefOr[String | Int] = js.undefined
  var flexWrap: js.UndefOr[String]         = js.undefined
  var justifyContent: js.UndefOr[String|JustifyContent]   = js.undefined
  var order: js.UndefOr[String]            = js.undefined

  /* Text Decoration Level 3 - CR */
  /* textDecoration - already defined by CSS2Properties */
  var textDecorationColor: js.UndefOr[String]  = js.undefined
  var textDecorationLine: js.UndefOr[String]   = js.undefined
  var textDecorationSkip: js.UndefOr[String]   = js.undefined
  var textDecorationStyle: js.UndefOr[String]  = js.undefined
  var textEmphasis: js.UndefOr[String]         = js.undefined
  var textEmphasisColor: js.UndefOr[String]    = js.undefined
  var textEmphasisPosition: js.UndefOr[String] = js.undefined
  var textEmphasisStyle: js.UndefOr[String]    = js.undefined
  /* textShadow - already defined by CSS2Properties */
  var textUnderlinePosition: js.UndefOr[String] = js.undefined
  /* Fonts Level 3 - CR */
  var fontFeatureSettings: js.UndefOr[String]  = js.undefined
  var fontKerning: js.UndefOr[String]          = js.undefined
  var fontLanguageOverride: js.UndefOr[String] = js.undefined
  /* fontSizeAdjust - already defined by CSS2Properties */
  /* fontStretch - already defined by CSS2Properties */
  var fontSynthesis: js.UndefOr[String]          = js.undefined
  var forntVariantAlternates: js.UndefOr[String] = js.undefined
  var fontVariantCaps: js.UndefOr[String]        = js.undefined
  var fontVariantEastAsian: js.UndefOr[String]   = js.undefined
  var fontVariantLigatures: js.UndefOr[String]   = js.undefined
  var fontVariantNumeric: js.UndefOr[String]     = js.undefined
  var fontVariantPosition: js.UndefOr[String]    = js.undefined
  /* Cascading and Inheritance Level 3 - CR */
  var all: js.UndefOr[String] = js.undefined
  /* Writing Modes Level 3 - CR */
  //var glyphOrientationVertical: js.UndefOr[String] = js.undefined
  var textCombineUpright: js.UndefOr[String] = js.undefined
  var textOrientation: js.UndefOr[String]    = js.undefined
  var writingMode: js.UndefOr[String]        = js.undefined
  /* Shapes Level 1 - CR */
  var shapeImageThreshold: js.UndefOr[String] = js.undefined
  var shapeMargin: js.UndefOr[String]         = js.undefined
  var shapeOutside: js.UndefOr[String]        = js.undefined
  /* Masking Level 1 - CR */
  //var clipPath: js.UndefOr[String] = js.undefined
  //var clipRule: js.UndefOr[String] = js.undefined
  var mask: js.UndefOr[String]             = js.undefined
  var maskBorder: js.UndefOr[String]       = js.undefined
  var maskBorderMode: js.UndefOr[String]   = js.undefined
  var maskBorderOutset: js.UndefOr[String] = js.undefined
  var maskBorderRepeat: js.UndefOr[String] = js.undefined
  var maskBorderSlice: js.UndefOr[String]  = js.undefined
  var maskBorderSource: js.UndefOr[String] = js.undefined
  var maskBorderWidth: js.UndefOr[String]  = js.undefined
  var maskClip: js.UndefOr[String]         = js.undefined
  var maskComposite: js.UndefOr[String]    = js.undefined
  var maskImage: js.UndefOr[String]        = js.undefined
  var maskMode: js.UndefOr[String]         = js.undefined
  var maskOrigin: js.UndefOr[String]       = js.undefined
  var maskPosition: js.UndefOr[String]     = js.undefined
  var maskRepeat: js.UndefOr[String]       = js.undefined
  var maskSize: js.UndefOr[String]         = js.undefined
  var maskType: js.UndefOr[String]         = js.undefined
  /* Compositing and Blending Level 1 - CR */
  var backgroundBlendMode: js.UndefOr[String] = js.undefined
  var isolation: js.UndefOr[String]           = js.undefined
  var mixBlendMode: js.UndefOr[String]        = js.undefined
  /* Fragmentation Level 3 - CR */
  var boxDecorationBreak: js.UndefOr[String] = js.undefined
  /* breakAfter - already defined by Multi-column Layout */
  /* breakBefore - already defined by Multi-column Layout */
  /* breakInside - already defined by Multi-column Layout */
  /* Basic User Interface Level 3 - CR */
  var boxSizing: js.UndefOr[String|BoxSizing]     = js.undefined
  var caretColor: js.UndefOr[String]    = js.undefined
  var navDown: js.UndefOr[String]       = js.undefined
  var navLeft: js.UndefOr[String]       = js.undefined
  var navRight: js.UndefOr[String]      = js.undefined
  var navUp: js.UndefOr[String]         = js.undefined
  var outlineOffset: js.UndefOr[Int|String] = js.undefined
  var resize: js.UndefOr[String]        = js.undefined
  var textOverflow: js.UndefOr[String]  = js.undefined
  /* Grid Layout Level 1 - CR */
  var grid: js.UndefOr[String]                = js.undefined
  var gridArea: js.UndefOr[String]            = js.undefined
  var gridAutoColumns: js.UndefOr[Int|String]     = js.undefined
  var gridAutoFlow: js.UndefOr[Int|String]        = js.undefined
  var gridAutoRows: js.UndefOr[Int|String]        = js.undefined
  var gridColumn: js.UndefOr[Int|String]          = js.undefined
  var gridColumnEnd: js.UndefOr[Int|String]       = js.undefined
  var gridColumnGap: js.UndefOr[Int|String]       = js.undefined
  var gridColumnStart: js.UndefOr[Int|String]     = js.undefined
  var gridGap: js.UndefOr[Int|String]             = js.undefined
  var gridRow: js.UndefOr[Int|String]             = js.undefined
  var gridRowEnd: js.UndefOr[Int|String]          = js.undefined
  var gridRowGap: js.UndefOr[Int|String]          = js.undefined
  var gridRowStart: js.UndefOr[Int|String]        = js.undefined
  var gridTemplate: js.UndefOr[String]        = js.undefined
  var gridTemplateAreas: js.UndefOr[String]   = js.undefined
  var gridTemplateColumns: js.UndefOr[Int|String] = js.undefined
  var gridTemplateRows: js.UndefOr[Int|String]    = js.undefined
  /* Will Change Level 1 - CR */
  var willChange: js.UndefOr[String] = js.undefined
  /* Text Level 3 - LC */
  var hangingPunctuation: js.UndefOr[String] = js.undefined
  var hyphens: js.UndefOr[String]            = js.undefined
  /* letterSpacing - already defined by CSS2Properties */
  var lineBreak: js.UndefOr[String]    = js.undefined
  var overflowWrap: js.UndefOr[String] = js.undefined
  var tabSize: js.UndefOr[String]      = js.undefined
  /* textAlign - already defined by CSS2Properties */
  var textAlignLast: js.UndefOr[String] = js.undefined
  var textJustify: js.UndefOr[String]   = js.undefined
  var wordBreak: js.UndefOr[String]     = js.undefined
  var wordWrap: js.UndefOr[String]      = js.undefined
  /* Animations - WD */
  var animation: js.UndefOr[String]               = js.undefined
  var animationDelay: js.UndefOr[String]          = js.undefined
  var animationDirection: js.UndefOr[String]      = js.undefined
  var animationDuration: js.UndefOr[String]       = js.undefined
  var animationFillMode: js.UndefOr[String]       = js.undefined
  var animationIterationCount: js.UndefOr[String] = js.undefined
  var animationName: js.UndefOr[String]           = js.undefined
  var animationPlayState: js.UndefOr[String]      = js.undefined
  var animationTimingFunction: js.UndefOr[String] = js.undefined
  /* Transitions - WD */
  var transition: js.UndefOr[String]               = js.undefined
  var transitionDelay: js.UndefOr[String]          = js.undefined
  var transitionDuration: js.UndefOr[String]       = js.undefined
  var transitionProperty: js.UndefOr[String]       = js.undefined
  var transitionTimingFunction: js.UndefOr[String] = js.undefined
  /* Transforms Level 1 - WD */
  var backfaceVisibility: js.UndefOr[String] = js.undefined
  var perspective: js.UndefOr[String]        = js.undefined
  var perspectiveOrigin: js.UndefOr[String]  = js.undefined
  var transform: js.UndefOr[String]          = js.undefined
  var transformOrigin: js.UndefOr[String]    = js.undefined
  var transformStyle: js.UndefOr[String]     = js.undefined
  /* Box Alignment Level 3 - WD */
  /* alignContent - already defined by Flexible Box Layout */
  /* alignItems - already defined by Flexible Box Layout */
  var justifyItems: js.UndefOr[String] = js.undefined
  var justifySelf: js.UndefOr[String]  = js.undefined
  var placeContent: js.UndefOr[String] = js.undefined
  var placeItems: js.UndefOr[String]   = js.undefined
  var placeSelf: js.UndefOr[String]    = js.undefined
  /* Basic User Interface Level 4 - FPWD */
  var appearance: js.UndefOr[String]     = js.undefined
  var caret: js.UndefOr[String]          = js.undefined
  var caretAnimation: js.UndefOr[String] = js.undefined
  var caretShape: js.UndefOr[String]     = js.undefined
  var userSelect: js.UndefOr[String]     = js.undefined
  /* Overflow Level 3 - WD */
  var maxLines: js.UndefOr[String] = js.undefined
  /* Basix Box Model - WD */
  var marqueeDirection: js.UndefOr[String] = js.undefined
  var marqueeLoop: js.UndefOr[String]      = js.undefined
  var marqueeSpeed: js.UndefOr[String]     = js.undefined
  var marqueeStyle: js.UndefOr[String]     = js.undefined
  var overflowStyle: js.UndefOr[String]    = js.undefined
  var rotation: js.UndefOr[String]         = js.undefined
  var rotationPoint: js.UndefOr[String]    = js.undefined
  /* SVG 1.1 - REC */
  var alignmentBaseline: js.UndefOr[String] = js.undefined
  var baselineShift: js.UndefOr[String]     = js.undefined
  //var clip: js.UndefOr[String] = js.undefined
  var clipPath: js.UndefOr[String]                  = js.undefined
  var clipRule: js.UndefOr[String]                  = js.undefined
  var colorInterpolation: js.UndefOr[String]        = js.undefined
  var colorInterpolationFilters: js.UndefOr[String] = js.undefined
  var colorProfile: js.UndefOr[String]              = js.undefined
  var colorRendering: js.UndefOr[String]            = js.undefined
  //var cursor: js.UndefOr[String] = js.undefined
  var dominantBaseline: js.UndefOr[String]           = js.undefined
  var fill: js.UndefOr[String]                       = js.undefined
  var fillOpacity: js.UndefOr[String]                = js.undefined
  var fillRule: js.UndefOr[String]                   = js.undefined
  var filter: js.UndefOr[String]                     = js.undefined
  var floodColor: js.UndefOr[String]                 = js.undefined
  var floodOpacity: js.UndefOr[String]               = js.undefined
  var glyphOrientationHorizontal: js.UndefOr[String] = js.undefined
  var glyphOrientationVertical: js.UndefOr[String]   = js.undefined
  var imageRendering: js.UndefOr[String]             = js.undefined
  var kerning: js.UndefOr[String]                    = js.undefined
  var lightingColor: js.UndefOr[String]              = js.undefined
  var markerEnd: js.UndefOr[String]                  = js.undefined
  var markerMid: js.UndefOr[String]                  = js.undefined
  var markerStart: js.UndefOr[String]                = js.undefined
  var pointerEvents: js.UndefOr[String]              = js.undefined
  var shapeRendering: js.UndefOr[String]             = js.undefined
  var stopColor: js.UndefOr[String]                  = js.undefined
  var stopOpacity: js.UndefOr[String]                = js.undefined
  var stroke: js.UndefOr[String]                     = js.undefined
  var strokeDasharray: js.UndefOr[String]            = js.undefined
  var strokeDashoffset: js.UndefOr[String]           = js.undefined
  var strokeLinecap: js.UndefOr[String]              = js.undefined
  var strokeLinejoin: js.UndefOr[String]             = js.undefined
  var strokeMiterlimit: js.UndefOr[String]           = js.undefined
  var strokeOpacity: js.UndefOr[String]              = js.undefined
  var strokeWidth: js.UndefOr[String]                = js.undefined
  var textAnchor: js.UndefOr[String]                 = js.undefined
  var textRendering: js.UndefOr[String]              = js.undefined
  /* Ruby Layout Level 1 - WD */
  var rubyAlign: js.UndefOr[String]    = js.undefined
  var rubyMerge: js.UndefOr[String]    = js.undefined
  var rubyPosition: js.UndefOr[String] = js.undefined
}

/**
 * A style trait that can provide some protection that the
 * values you provide *are* valid styling keywords e.g.
 * backgroundColor. Use this like
 * `new StyleAttr{ backgroundColor = "red" }`.
 */
trait StyleAttr extends RawStyleBase
