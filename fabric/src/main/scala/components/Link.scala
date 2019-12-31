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

object Link {
  @js.native
  @JSImport("office-ui-fabric-react/lib/Link", "Link")
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    React.createElement(JS, props)(children: _*)

  trait StyleProps extends js.Object {
    var className: js.UndefOr[String] = js.undefined
    var isButton: js.UndefOr[Boolean] = js.undefined
    var isDisabled: js.UndefOr[Boolean] = js.undefined
  }

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
  }

  @js.native
  trait ILink extends Focusable

  //export interface ILinkProps extends React.AllHTMLAttributes<HTMLAnchorElement | HTMLButtonElement | HTMLElement | Link>

  trait Props extends AllHTMLAttributes[dom.html.Anchor] with ComponentRef[ILink] {
    //var styles: js.UndefOr[IStyleFunctionOrObject[ILinkStyleProps, ILinkStyles]] = js.undefined
    var keytipProps: js.UndefOr[IKeytipProps] = js.undefined
  }
}

