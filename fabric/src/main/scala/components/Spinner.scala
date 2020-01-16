// Copyright (c) 2019 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package fabric
package components

import scala.scalajs.js
import js.annotation._
import js.|
import org.scalajs.dom

import react._
import vdom._
import fabric.styling._

object Spinner {
  @js.native
  @JSImport("office-ui-fabric-react/lib/Spinner", "Spinner")
  object JS extends ReactJsComponent

  def apply(props: Props = null) = React.createElement0(JS, props)

  @js.native
  trait ISpinner extends js.Object

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
    var circle: js.UndefOr[IStyle] = js.undefined
    var label: js.UndefOr[IStyle] = js.undefined
    var screenReaderText: js.UndefOr[IStyle] = js.undefined
  }

  trait StyleProps extends js.Object{
    var theme: js.UndefOr[Theme] = js.undefined
    var className: js.UndefOr[String]              = js.undefined
    var size: js.UndefOr[Size] = js.undefined
    var labelPosition: js.UndefOr[LabelPosition] = js.undefined
  }

  trait Props
      extends KeyAndRef
      with ComponentRef[ISpinner]
      with Theme
  {
    var size: js.UndefOr[Size]                      = js.undefined
    var label: js.UndefOr[String]                  = js.undefined
    var className: js.UndefOr[String]              = js.undefined
    var ariaLabel: js.UndefOr[String]              = js.undefined
    var ariaLive: js.UndefOr[AriaLive] = js.undefined
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
    var labelPosition: js.UndefOr[LabelPosition] = js.undefined
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

