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

object IconButton {
  import ttg.react.elements.wrapJsForScala

  @js.native
  @JSImport("office-ui-fabric-react/lib/Button", "IconButton")
  object JS extends ReactJsComponent

  def apply(props: IButtonProps = null)(children: ReactNode*) =
    wrapJsForScala(JS, props, children: _*)
}

