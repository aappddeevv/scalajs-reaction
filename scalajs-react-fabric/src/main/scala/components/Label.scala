// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
package fabric
package components

import scala.scalajs.js
import js.annotation._
import js.|
import org.scalajs.dom.html

import vdom._
import fabric.styling._

object Label {
  import ttg.react.elements.wrapJsForScala

  @js.native
  @JSImport("office-ui-fabric-react/lib/Label", "Label")
  object LabelJS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    wrapJsForScala(LabelJS, props, children: _*)

  trait Props
      extends LabelHTMLAttributes[html.Label]
      with ComponentRef[js.Any]
      with Disabled
      with Theme {
    var required: js.UndefOr[Boolean] = js.undefined
  }
}

