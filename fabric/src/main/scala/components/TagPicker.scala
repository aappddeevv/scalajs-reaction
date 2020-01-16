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

object TagPicker {
  @js.native
  @JSImport("office-ui-fabric-react/lib/TagPicker", "TagPicker")
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    React.createElement(JS, props)(children: _*)

  trait ITag extends js.Object {
    var key: String
    var name: String
  }

  trait Props extends IBasePickerProps[ITag] {}

}

