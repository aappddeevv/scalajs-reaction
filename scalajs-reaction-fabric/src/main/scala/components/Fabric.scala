// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package fabric
package components

import scala.scalajs.js
import js.annotation._
import js.|
import org.scalajs.dom

import ttg.react._
import vdom._
import fabric.styling._

object Fabric {

  @js.native
  @JSImport("office-ui-fabric-react/lib/Fabric", "Fabric")
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    React.createElement(JS, props)(children: _*)

  trait Props extends HTMLAttributes[dom.html.Div] with Theme {
    var componentRef: js.UndefOr[js.Function0[Unit]] = js.undefined
  }

}

