// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
package fabric
package components

import scala.scalajs.js
import js.annotation._
import js.|
import org.scalajs.dom

import vdom._
import fabric.styling._

object MarqueeSelection {
  import ttg.react.elements.wrapJsForScala

  @js.native
  @JSImport("office-ui-fabric-react/lib/MarqueeeSelection", "MarqueeSelection")
  object JS extends ReactJsComponent

  def apply[T <: js.Object](props: Props[T] = null)(children: ReactNode*) =
    wrapJsForScala(JS, props, children: _*)

  @js.native
  trait IMarqueeSelection extends js.Object

  trait Props[T <: js.Object] extends ComponentRef[IMarqueeSelection] with Attributes {
    var selection: js.UndefOr[ISelection[T]]                              = js.undefined
    var rootProps: js.UndefOr[HTMLAttributes[dom.html.Div]]               = js.undefined
    var onShouldStartSelection: js.UndefOr[js.Function1[js.Any, Boolean]] = js.undefined
    var isEnabled: js.UndefOr[Boolean]                                    = js.undefined
    var isDraggingConstrainedToRoot: js.UndefOr[Boolean]                  = js.undefined

  }
}

