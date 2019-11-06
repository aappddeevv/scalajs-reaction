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

object MessageBar {
  @js.native
  @JSImport("office-ui-fabric-react/lib/MessageBar", "MessageBar")
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    React.createElement(JS, props)(children: _*)

  trait IMessageBar extends js.Object

  @js.native
  sealed abstract trait Type extends js.Any

  object Type {
    val info = 0.asInstanceOf[Type]
    val error = 1.asInstanceOf[Type]
    val blocked = 2.asInstanceOf[Type]
    val severeWarning = 3.asInstanceOf[Type]
    val success = 4.asInstanceOf[Type]
    val warning = 5.asInstanceOf[Type]
  }

  trait StyleProps extends js.Object {
    var className : js.UndefOr[IStyle] = js.undefined
    var messageBarType: js.UndefOr[Type] = js.undefined
    var onDismiss: js.UndefOr[Boolean] = js.undefined
    var truncated: js.UndefOr[Boolean] = js.undefined
    var isMultiline: js.UndefOr[Boolean] = js.undefined
    var expandSingleLine: js.UndefOr[Boolean] = js.undefined
    var actions: js.UndefOr[Boolean] = js.undefined
  }

  trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
    var content: js.UndefOr[IStyle] = js.undefined
    var iconContainer: js.UndefOr[IStyle] = js.undefined
    var icon: js.UndefOr[IStyle] = js.undefined
    var text: js.UndefOr[IStyle] = js.undefined
    var innerText: js.UndefOr[IStyle] = js.undefined
    var dismissal: js.UndefOr[IStyle] = js.undefined
    var expand: js.UndefOr[IStyle] = js.undefined
    var dismissSingleLine: js.UndefOr[IStyle] = js.undefined
    var expandSingleLine: js.UndefOr[IStyle] = js.undefined
    var actions: js.UndefOr[IStyle] = js.undefined
  }

  trait Props extends HTMLAttributes[dom.html.Element]
      with ComponentRef[IMessageBar] with Theme with Attributes {
    var messageBarType: js.UndefOr[Type] = js.undefined
    var actions: js.UndefOr[ReactElement] = js.undefined
    var arialLabel: js.UndefOr[String] = js.undefined
    var onDimiss: js.UndefOr[js.Function1[js.Any, js.Any]] = js.undefined
    var isMultiline: js.UndefOr[Boolean] = js.undefined
    var dismissButtonAriaLabel: js.UndefOr[String] = js.undefined
    var truncated: js.UndefOr[Boolean] = js.undefined
    var overflowButtonAriaLabel: js.UndefOr[String] = js.undefined
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
  }
}

