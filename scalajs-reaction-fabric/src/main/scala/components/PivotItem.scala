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

object PivotItem {
  @js.native
  @JSImport("office-ui-fabric-react/lib/Pivot", "PivotItem")
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    React.createElement(JS, props)(children: _*)

  trait Props extends HTMLAttributes[dom.html.Div] {
    var headerText: js.UndefOr[String]                                   = js.undefined
    var itemKey: js.UndefOr[String]                                    = js.undefined
    var headerButtonProps: js.UndefOr[js.Object] = js.undefined
    var itemCount: js.UndefOr[Int]                                     = js.undefined
    var itemIcon: js.UndefOr[String]                                   = js.undefined
    var ariaLabel: js.UndefOr[String]                                  = js.undefined
    var onRenderItemLink: js.UndefOr[IRenderFunction[Props]] = js.undefined
    var keytipProps: js.UndefOr[IKeytipProps] = js.undefined
  }

}
