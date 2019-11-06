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

object Sticky {
  @js.native
  @JSImport("office-ui-fabric-react/lib/Sticky", "Sticky")
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    React.createElement(JS, props)(children: _*)

  @js.native
  trait ISticky extends js.Object

  trait Props extends ComponentRef[ISticky] {
    var stickyCassName: js.UndefOr[String] = js.undefined
    var stickyPosition: js.UndefOr[Int]    = js.undefined
  }

  @js.native
  sealed trait StickyPositionType extends js.Any
  object StickyPositionType {
    val Both   = 0.asInstanceOf[StickyPositionType]
    val Header = 1.asInstanceOf[StickyPositionType]
    val Footer = 2.asInstanceOf[StickyPositionType]
  }

}

