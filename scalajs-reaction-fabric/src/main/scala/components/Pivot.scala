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

object Pivot {
  import ttg.react.elements.wrapJsForScala

  @js.native
  @JSImport("office-ui-fabric-react/lib/Pivot", "Pivot")
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    wrapJsForScala(JS, props, children: _*)

  @js.native
  trait IPivot extends Focusable

  trait StyleProps extends Theme {
    var className: js.UndefOr[String] = js.undefined
  }

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
    var link: js.UndefOr[IStyle] = js.undefined
    var linkContent: js.UndefOr[IStyle] = js.undefined
    var text: js.UndefOr[IStyle] = js.undefined
    var count: js.UndefOr[IStyle] = js.undefined
    var icon: js.UndefOr[IStyle] = js.undefined
  }

  trait Props extends Attributes with Theme with ComponentRef[IPivot] {
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
    var className: js.UndefOr[String] = js.undefined

    var initialSelectedKey: js.UndefOr[String] = js.undefined
    var initialSelectedIndex: js.UndefOr[Int]  = js.undefined
    var selectedKey: js.UndefOr[String]        = js.undefined
    var onLinkClick: js.UndefOr[
      ((js.Object, js.UndefOr[ReactMouseEvent[dom.html.Element]]) => Unit) |(() => Unit)] =
      js.undefined
    var linkSize: js.UndefOr[LinkSize] = js.undefined
    var headersOnly: js.UndefOr[Boolean] = js.undefined
    var getTableId: js.UndefOr[js.Function2[String, Int, String]] = js.undefined
  }

  @js.native
  sealed trait LinkSize extends js.Any
  object LinkSize {
    var normal = 0.asInstanceOf[LinkSize]
    val large = 1.asInstanceOf[LinkSize]
  }

}
