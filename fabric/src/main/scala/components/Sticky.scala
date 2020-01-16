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

object Sticky {
  @js.native
  @JSImport("office-ui-fabric-react/lib/Sticky", "Sticky")
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    createElement(JS, props)(children: _*)

  @js.native
  trait ISticky extends js.Object

  trait Props extends ComponentRef[ISticky] {
    var stickyClassName: js.UndefOr[String] = js.undefined
    var stickyBackgroundColor: js.UndefOr[String] = js.undefined    
    var stickyPosition: js.UndefOr[Position]    = js.undefined
    var isScrollSynced: js.UndefOr[Boolean] = js.undefined
  }

  @js.native
  abstract trait Position extends js.Any
  object Position {
    val Both   = 0.asInstanceOf[Position]
    val Header = 1.asInstanceOf[Position]
    val Footer = 2.asInstanceOf[Position]
  }

}

