// Copyright (c) 2018 The Trapelo Group LLC
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

object OverflowSet {
  @js.native
  @JSImport("office-ui-fabric-react/lib/OverflowSet", "OverflowSet")
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    React.createElement(JS, props)(children: _*)

  @js.native
  trait IOveflowSet extends js.Object {
    var focus: js.Function1[Boolean, Unit]
    var focusElement: js.Function1[dom.html.Element, Boolean]
  }

  trait StyleProps extends js.Object {
  }

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
    var item: js.UndefOr[IStyle] = js.undefined
    var overflowButton: js.UndefOr[IStyle] = js.undefined
  }

  /** Subclass to add your own properties. */
  trait ItemProps extends js.Object {
    val key: String
    var keytipProps: js.UndefOr[IKeytipProps] = js.undefined
  }

  trait Props extends js.Object {
    var className: js.UndefOr[String] = js.undefined
    var items:  js.UndefOr[Seq[ItemProps]] = js.undefined
    var vertical: js.UndefOr[Boolean] = js.undefined
    var overflowItems: js.UndefOr[Seq[ItemProps]] = js.undefined
    var doNotContainWithinFocusZone: js.UndefOr[Boolean] = js.undefined
    var role: js.UndefOr[String] = js.undefined
    /** Always make this a function. */
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps,Styles]] = js.undefined
  }

}

