// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package react
package native



import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

object ImageBackground {

  @js.native
  @JSImport("react-native", "ImageBackground")
  object JS extends ReactJsComponent

  def apply(props: Image.Props = null)(children: ReactNode*) =
    React.createElement(JS, props)(children:_*)
}
