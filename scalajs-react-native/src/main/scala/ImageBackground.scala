// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package native

import react.elements._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

object ImageBackground {

  @js.native
  @JSImport("react-native", "ImageBackground")
  object JS extends ReactJsComponent

  def apply(props: Image.Props = null)(children: ReactNode*) =
    wrapJsForScala(JS, props, children:_*)
}
