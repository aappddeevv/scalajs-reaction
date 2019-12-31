// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package fabric
package components

import scala.scalajs.js
import js.annotation._
import js.|
import org.scalajs.dom.html

import react._
import vdom._
import fabric.styling._

object Label {
  @js.native
  @JSImport("office-ui-fabric-react/lib/Label", "Label")
  object JS extends ReactJsComponent

  def apply(props: Props)(children: ReactNode*) =
    React.createElement(JS, props)(children: _*)

  def apply(children: ReactNode*) =
    React.createElement(JS, null)(children: _*)


  trait Props extends LabelHTMLAttributes[html.Label] with ComponentRef[js.Any] with Disabled with Theme {
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
    var required: js.UndefOr[Boolean] = js.undefined
  }

  trait StyleProps extends js.Object {
    var className: js.UndefOr[String] = js.undefined
    var disabled: js.UndefOr[Boolean] = js.undefined
    var required: js.UndefOr[Boolean] = js.undefined
    var theme: js.UndefOr[Theme] = js.undefined
  }

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
  }
}
