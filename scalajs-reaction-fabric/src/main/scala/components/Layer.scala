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

object Layer {

  @js.native
  @JSImport("office-ui-fabric-react/lib/Layer", "Layer")
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    React.createElement(JS, props)(children: _*)

  trait Styles extends IStyleSetTag with Theme {
    var root: js.UndefOr[IStyle] = js.undefined
    var content: js.UndefOr[IStyle] = js.undefined
  }

  trait StyleProps extends js.Object {
    var theme: js.UndefOr[ITheme] = js.undefined
    var className: js.UndefOr[String] = js.undefined
    var isNotHost: js.UndefOr[Boolean] = js.undefined
  }

  @js.native
  trait ILayer extends js.Object

  trait Props extends HTMLAttributes[dom.html.Div] with ComponentRef[ILayer] {
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
    //var className: js.UndefOr[String] = js.undefined
    var onLayerMounted: js.UndefOr[js.Function0[Unit]] = js.undefined
    var onLayerDidMount: js.UndefOr[js.Function0[Unit]] = js.undefined
    var onLayerWillUnmount: js.UndefOr[js.Function0[Unit]] = js.undefined
    var hostId: js.UndefOr[String] = js.undefined
    var eventBubblingEnabled: js.UndefOr[Boolean] = js.undefined
  }

}

