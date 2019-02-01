// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react.native

import scala.scalajs.js
import js.|

trait FlexCommon[T] {
  val flexStart = "flexStart".asInstanceOf[T]
  val flexEnd = "flexEnd".asInstanceOf[T]
  val center = "center".asInstanceOf[T]
}

@js.native
sealed trait FlexAlignType extends js.Any
object FlexAlignType extends FlexCommon[FlexAlignType] {
  val stretch = "stretch".asInstanceOf[FlexAlignType]
  val baseline = "baseline".asInstanceOf[FlexAlignType]
}

@js.native
sealed trait AlignContent extends js.Any
object AlignContent extends FlexCommon[AlignContent] {
  val spaceBetween = "space-between".asInstanceOf[FlexAlignType]
  val spaceAround = "space-around".asInstanceOf[FlexAlignType]
}

@js.native
sealed trait AlignSelf extends js.Any
object AlignSelf extends FlexCommon[AlignSelf] {
  val stretch = "stretch".asInstanceOf[AlignSelf]
  val baseline = "baseline".asInstanceOf[AlignSelf]
  val auto = "auto".asInstanceOf[AlignSelf]
}

@js.native
sealed trait Display extends js.Any
object Display {
  val none = "none".asInstanceOf[Display]
  val flex = "flex".asInstanceOf[Display]  
}

@js.native
sealed trait FlexDirection extends js.Any
object FlexDirection {
  val row = "row".asInstanceOf[FlexDirection]
  val column = "column".asInstanceOf[FlexDirection]
  val rowReverse = "row-reverse".asInstanceOf[FlexDirection]
  val columnReverse = "column-reverse".asInstanceOf[FlexDirection]
}

@js.native
sealed trait FlexWrap extends js.Any
object FlexWrap {
  var wrap = "wrap".asInstanceOf[FlexWrap]
  var noWrap = "nowrap".asInstanceOf[FlexWrap]
  var wrapRevers = "wrap-reverse".asInstanceOf[FlexWrap]  
}

@js.native
sealed trait JustifyContent extends js.Any
object JustifyContent {
  val flexStart = "flex-start".asInstanceOf[JustifyContent]
  val flexEnd = "flex-end".asInstanceOf[JustifyContent]
  val center = "center".asInstanceOf[JustifyContent]
  val spaceBetween = "space-between".asInstanceOf[JustifyContent]
  val spaceArounud = "space-around".asInstanceOf[JustifyContent]
  val spaceEvenly = "space-evenly".asInstanceOf[JustifyContent]
}

@js.native
sealed trait Overflow extends js.Any
object Overflow {
  val visible = "visible".asInstanceOf[Overflow]
  val hidden = "hidden".asInstanceOf[Overflow]
  val scroll = "scroll".asInstanceOf[Overflow]
}

@js.native
sealed trait Position extends js.Any
object Position {
  val visible = "visible".asInstanceOf[Position]
  val hidden = "visible".asInstanceOf[Position]
  val scroll = "visible".asInstanceOf[Position]
}

@js.native
sealed trait Direction extends js.Any
object Direction {
  val inherit = "inherit".asInstanceOf[Direction]
  val ltr = "ltr".asInstanceOf[Direction]
  val rtl = "rtl".asInstanceOf[Direction]
}

trait FlexStyle extends js.Object {
  var alignContent: js.UndefOr[AlignContent] = js.undefined
  var alignItems: js.UndefOr[FlexAlignType] = js.undefined
  var alignSelf: js.UndefOr[AlignSelf] = js.undefined
  var aspectRatio: js.UndefOr[Double] = js.undefined
  var borderBottomWidth: js.UndefOr[Double] = js.undefined
  var borderEndWidth: js.UndefOr[Double|String] = js.undefined
  var borderLeftWidth: js.UndefOr[Double] = js.undefined
  var borderRightWidth: js.UndefOr[Double] = js.undefined
  var borderStartWidth: js.UndefOr[Double|String] = js.undefined
  var borderTopWidth: js.UndefOr[Double] = js.undefined
  var borderWidth: js.UndefOr[Double] = js.undefined
  var bottom: js.UndefOr[Double|String] = js.undefined

  // ios only
  var direction: js.UndefOr[Direction] = js.undefined
  // display?

  var end: js.UndefOr[String] = js.undefined
  var flex: js.UndefOr[Double] = js.undefined
  var flexBasis: js.UndefOr[String|Double] = js.undefined
  var flexDirection: js.UndefOr[FlexDirection] = js.undefined
  var flexGrow: js.UndefOr[Double] = js.undefined
  var flexShrink: js.UndefOr[Double] = js.undefined
  var flexWrap: js.UndefOr[FlexWrap] = js.undefined
  
  var height: js.UndefOr[String|Double] = js.undefined

  // primary axis
  var justifyContent: js.UndefOr[JustifyContent] = js.undefined

  var left: js.UndefOr[String|Double] = js.undefined
  var margin: js.UndefOr[String|Double] = js.undefined
  var marginBottom: js.UndefOr[String|Double] = js.undefined
  var marginEnd: js.UndefOr[String|Double] = js.undefined
  var marginHorizontal: js.UndefOr[String|Double] = js.undefined
  var marginLeft: js.UndefOr[String|Double] = js.undefined
  var marginRight: js.UndefOr[String|Double] = js.undefined
  var marginStart: js.UndefOr[String|Double] = js.undefined
  var marginTop: js.UndefOr[String|Double] = js.undefined
  var marginVertical: js.UndefOr[String|Double] = js.undefined
  var maxHeight: js.UndefOr[String|Double] = js.undefined
  var maxWidth: js.UndefOr[String|Double] = js.undefined
  var minWidth: js.UndefOr[String|Double] = js.undefined
  var minHeight: js.UndefOr[String|Double] = js.undefined
  var overflow: js.UndefOr[Overflow] = js.undefined
  var padding: js.UndefOr[String|Double] = js.undefined
  var paddingBottom: js.UndefOr[String|Double] = js.undefined
  var paddingEnd: js.UndefOr[String|Double] = js.undefined
  var paddingHorizontal: js.UndefOr[String|Double] = js.undefined
  var paddingLeft: js.UndefOr[String|Double] = js.undefined
  var paddingRight: js.UndefOr[String|Double] = js.undefined
  var paddingStart: js.UndefOr[String|Double] = js.undefined
  var paddingTop: js.UndefOr[String|Double] = js.undefined
  var paddingVertical: js.UndefOr[String|Double] = js.undefined
  var position: js.UndefOr[Position] = js.undefined
  var right: js.UndefOr[String|Double] = js.undefined
  var start: js.UndefOr[String|Double] = js.undefined
  var top: js.UndefOr[String|Double] = js.undefined
  var width: js.UndefOr[String|Double] = js.undefined
  var zIndex: js.UndefOr[Double] = js.undefined  
}

trait ShadowStyleIOS extends js.Object {
  val shadowColor: js.UndefOr[String] = js.undefined
  val shadowOffset: js.UndefOr[Offset] = js.undefined
  val shadowOpacity: js.UndefOr[Double] = js.undefined
  val shadowRadius: js.UndefOr[Double] = js.undefined
}

// trait FlexProps extends js.Object {
//   // var alignContent: js.UndefOr[AlignContent] = js.undefined
//   // var alignItems: js.UndefOr[FlexAlignType] = js.undefined
//   // var alignSelf: js.UndefOr[AlignSelf] = js.undefined

//   // var aspectRatio: js.UndefOr[Double] = js.undefined
//   // var borderBottomWidth: js.UndefOr[Double] = js.undefined
//   // var borderEndWidth: js.UndefOr[Double|String] = js.undefined
//   // var borderLeftWidth: js.UndefOr[Double] = js.undefined
//   // var borderRightWidth: js.UndefOr[Double] = js.undefined
//   // var borderStartWidth: js.UndefOr[Double|String] = js.undefined
//   // var borderTopWidth: js.UndefOr[Double] = js.undefined
//   // var borderWidth: js.UndefOr[Double] = js.undefined
//   // var bottom: js.UndefOr[Double|String] = js.undefined
//   // var display: js.UndefOr[Display] = js.undefined
//   // var end: js.UndefOr[String] = js.undefined

//   var flex: js.UndefOr[Double] = js.undefined
//   var flexBasis: js.UndefOr[String|Double] = js.undefined
//   var flexDirection: js.UndefOr[FlexDirection] = js.undefined
//   var flexGrow: js.UndefOr[Double] = js.undefined
//   var flexShrink: js.UndefOr[Double] = js.undefined
//   var flexWrap: js.UndefOr[FlexWrap] = js.undefined

//   // var height: js.UndefOr[String|Double] = js.undefined

//   // // primary axis
//   // var justifyContent: js.UndefOr[JustifyContent] = js.undefined


//   // var left: js.UndefOr[String|Double] = js.undefined
//   // var margin: js.UndefOr[String|Double] = js.undefined
//   // var marginBottom: js.UndefOr[String|Double] = js.undefined
//   // var marginEnd: js.UndefOr[String|Double] = js.undefined
//   // var marginHorizontal: js.UndefOr[String|Double] = js.undefined
//   // var marginLeft: js.UndefOr[String|Double] = js.undefined
//   // var marginRight: js.UndefOr[String|Double] = js.undefined
//   // var marginStart: js.UndefOr[String|Double] = js.undefined
//   // var marginTop: js.UndefOr[String|Double] = js.undefined
//   // var marginVertical: js.UndefOr[String|Double] = js.undefined
//   // var maxHeight: js.UndefOr[String|Double] = js.undefined
//   // var maxWidth: js.UndefOr[String|Double] = js.undefined
//   // var minWidth: js.UndefOr[String|Double] = js.undefined
//   // var minHeight: js.UndefOr[String|Double] = js.undefined
//   // var overflow: js.UndefOr[Overflow] = js.undefined
//   // var padding: js.UndefOr[String|Double] = js.undefined
//   // var paddingBottom: js.UndefOr[String|Double] = js.undefined
//   // var paddingEnd: js.UndefOr[String|Double] = js.undefined
//   // var paddingHorizontal: js.UndefOr[String|Double] = js.undefined
//   // var paddingLeft: js.UndefOr[String|Double] = js.undefined
//   // var paddingRight: js.UndefOr[String|Double] = js.undefined
//   // var paddingStart: js.UndefOr[String|Double] = js.undefined
//   // var paddingTop: js.UndefOr[String|Double] = js.undefined
//   // var paddingVertical: js.UndefOr[String|Double] = js.undefined
//   // var position: js.UndefOr[Position] = js.undefined
//   // var right: js.UndefOr[String|Double] = js.undefined
//   // var start: js.UndefOr[String|Double] = js.undefined
//   // var top: js.UndefOr[String|Double] = js.undefined
//   // var width: js.UndefOr[String|Double] = js.undefined
//   // var zIndex: js.UndefOr[Double] = js.undefined  

//   // // ios only
//   // var direction: js.UndefOr[Direction] = js.undefined
// }
