// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package fabric
package components

import ttg.react._
import scala.scalajs.js
import js.annotation._
import js.|
import org.scalajs.dom

import vdom._
import fabric.styling._

object Toggle {
  @js.native
  @JSImport("office-ui-fabric-react/lib/Toggle", "Toggle")
  object JS extends ReactJsComponent

  def apply(props: Props = null) = React.createElement0(JS, props)

  @js.native
  trait IToggle extends Focusable

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
    var label: js.UndefOr[IStyle] = js.undefined
    var container: js.UndefOr[IStyle] = js.undefined
    var pill: js.UndefOr[IStyle] = js.undefined
    var thumb: js.UndefOr[IStyle] = js.undefined
    var text: js.UndefOr[IStyle] = js.undefined
  }

  trait StyleProps extends js.Object{
    var theme: js.UndefOr[Theme] = js.undefined
    var className: js.UndefOr[String]              = js.undefined
    var disabled: js.UndefOr[Boolean] = js.undefined
    var checked: js.UndefOr[Boolean] = js.undefined
    var inlineLabel: js.UndefOr[Boolean] = js.undefined
    var onOffMissing: js.UndefOr[Boolean] = js.undefined
  }

  trait Props
      extends KeyAndRef
      with ComponentRef[IToggle]
      with Theme
  {
    var label: js.UndefOr[String]                  = js.undefined
    var onText: js.UndefOr[String]                  = js.undefined
    var offText: js.UndefOr[String]                  = js.undefined
    var className: js.UndefOr[String]              = js.undefined
    var ariaLabel: js.UndefOr[String]              = js.undefined
    var checked: js.UndefOr[Boolean] = js.undefined
    var defaultChecked: js.UndefOr[Boolean] = js.undefined
    var disabled: js.UndefOr[Boolean] = js.undefined
    var inlineLabel: js.UndefOr[Boolean] = js.undefined
    var onChange: js.UndefOr[js.Function2[ReactMouseEvent[dom.html.Element], Boolean, Unit]] = js.undefined
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
    var keytipProps: js.UndefOr[IKeytipProps] = js.undefined
  }

  @js.native
  abstract sealed trait AriaLive extends js.Any
  object AriaLive {
    var assertive = "assertive".asInstanceOf[AriaLive]
    var polite = "polite".asInstanceOf[AriaLive]
    var off = "off".asInstanceOf[AriaLive]
  }

  @js.native
  abstract sealed trait Size extends js.Any
  object Size {
    var xSmall = 0.asInstanceOf[Size]
    var small  = 1.asInstanceOf[Size]
    var medium = 2.asInstanceOf[Size]
    var large  = 3.asInstanceOf[Size]
  }

  @js.native
  sealed trait LabelPosition extends js.Any
  object LabelPosition {
    val top = "top".asInstanceOf[LabelPosition]
    val right = "right".asInstanceOf[LabelPosition]
    val bottom = "bottom".asInstanceOf[LabelPosition]
    val left = "left".asInstanceOf[LabelPosition]
  }

}

