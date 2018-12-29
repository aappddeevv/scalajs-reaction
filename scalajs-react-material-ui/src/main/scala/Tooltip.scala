// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
package materialui
package components

import scala.scalajs.js
import js.annotation._

object Tooltip {
  import ttg.react.elements.wrapJsForScala

  @js.native
  @JSImport("@material-ui/core/Tooltip", JSImport.Default)
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    wrapJsForScala(JS, props, children:_*)

  @js.native
  sealed trait Placement extends js.Any
  object Placement {
    val bottomStart = "bottom-start".asInstanceOf[Placement]
    val leftEnd = "left-end".asInstanceOf[Placement]
    val topEnd = "top-end".asInstanceOf[Placement]
    val bottomEnd = "bottom-out".asInstanceOf[Placement]
    val top = "top".asInstanceOf[Placement]
    val rightEnd = "right-end".asInstanceOf[Placement]
    val leftStart = "left-start".asInstanceOf[Placement]
    val right = "right".asInstanceOf[Placement]
    val topStart = "top-start".asInstanceOf[Placement]
    val bottom = "bottom".asInstanceOf[Placement]
    val left = "left".asInstanceOf[Placement]
    val rightStart = "right-start".asInstanceOf[Placement]
  }
          
  trait Props extends js.Object {
    var PopperProps: js.UndefOr[js.Object] = js.undefined
    var TransitionComponent: js.UndefOr[js.Any] = js.undefined
    var TransitionProps: js.UndefOr[js.Object] = js.undefined
    var classes: js.UndefOr[js.Object] = js.undefined
    var disableFocusListener: js.UndefOr[Boolean] = js.undefined
    var disableHoverListener: js.UndefOr[Boolean] = js.undefined
    var disableTouchListener: js.UndefOr[Boolean] = js.undefined
    var enterDelay: js.UndefOr[Double] = js.undefined
    var enterTouchDelay: js.UndefOr[Double] = js.undefined
    var id: js.UndefOr[String] = js.undefined
    var interactive: js.UndefOr[Boolean] = js.undefined
    var key: js.UndefOr[String] = js.undefined
    var leaveDelay: js.UndefOr[Double] = js.undefined
    var leaveTouchDelay: js.UndefOr[Double] = js.undefined
    var onClose: js.UndefOr[scalajs.js.Function0[Unit]] = js.undefined
    var onOpen: js.UndefOr[scalajs.js.Function0[Unit]] = js.undefined
    var open: js.UndefOr[Boolean] = js.undefined
    var placement: js.UndefOr[Placement] = js.undefined
    var style: js.UndefOr[js.Object] = js.undefined
    var theme: js.UndefOr[js.Object] = js.undefined
    var title: ReactNode
  }
}
