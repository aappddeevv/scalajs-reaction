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

object ScrollablePane {

  @js.native
  @JSImport("office-ui-fabric-react/lib/ScrollablePane", "ScrollablePane")
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    React.createElement(JS, props)(children: _*)

  @js.native
  trait IScrollablePane extends js.Object {
    def forceLayoutUpdate(): Unit = js.native
    def getScrollPosition(): Double = js.native
  }

  trait Props
      extends HTMLAttributes[dom.html.Element]
      with ComponentRef[IScrollablePane] {
    val initialScrollPosition: js.UndefOr[Double] = js.undefined
    var scrollbarVisibility: js.UndefOr[Boolean] = js.undefined
  }

  trait StyleProps extends js.Object {
    val className: js.UndefOr[String] = js.undefined
  }

  trait Styles extends fabric.styling.IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
    var stickyAbove: js.UndefOr[IStyle] = js.undefined
    var stickyBelow: js.UndefOr[IStyle] = js.undefined
    var stickyBelowItems: js.UndefOr[IStyle] = js.undefined
    var contentContainer: js.UndefOr[IStyle] = js.undefined
  }

  @js.native
  sealed trait Visibility extends js.Any
  object Visibility {
    var auto = "auto"
    var always = "always"
  }

}

