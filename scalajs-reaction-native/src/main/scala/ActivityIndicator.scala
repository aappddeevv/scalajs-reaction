// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package native

import react.elements._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import js.|

object ActivityIndicator {

  @js.native
  @JSImport("react-native", "ActivityIndicator")
  object JS extends ReactJsComponent

  def apply(props: Props = null) = React.createElement0(JS, props)

  trait Props extends View.Props {
    var animating: js.UndefOr[Boolean] = js.undefined
    var color: js.UndefOr[Boolean] = js.undefined
    var size: js.UndefOr[Size|Int] = js.undefined
    var hidesWhenStopped: js.UndefOr[Boolean] = js.undefined
  }

  @js.native
  sealed trait Size extends js.Any
  object Size {
    val large = "large".asInstanceOf[Size]
    val small = "large".asInstanceOf[Size]    
  }
}
