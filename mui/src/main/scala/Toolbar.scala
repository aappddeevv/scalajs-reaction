// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package mui
package components

import scala.scalajs.js
import js.annotation._
import react._

object Toolbar {
  @js.native
  @JSImport("@material-ui/core/Toolbar", JSImport.Default)
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    React.createElement(JS, props)(children:_*)

  @js.native
  sealed trait Variant extends js.Any
  object Variant {
    val regular = "regular".asInstanceOf[Variant]
    val dense = "dense".asInstanceOf[Variant]
  }
          
  trait Props extends js.Object {
    var className: js.UndefOr[String] = js.undefined
    var classes: js.UndefOr[js.Object] = js.undefined
    var disableGutters: js.UndefOr[Boolean] = js.undefined
    var key: js.UndefOr[String] = js.undefined
    var style: js.UndefOr[js.Object] = js.undefined
    var variant: js.UndefOr[Variant] = js.undefined
  }
}
