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

package mui
package components

import scala.scalajs.js
import js.annotation._
import react._

object Tooltip {
  @js.native
  @JSImport("@material-ui/core/Tooltip", JSImport.Default)
  object JS extends ReactJSComponent

  def apply(props: Props)(children: ReactNode*) =
    createElementN(JS, props)(children: _*)

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
