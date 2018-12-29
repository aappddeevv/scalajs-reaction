// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
package materialui

import scala.scalajs.js
import js.annotation._

@js.native
trait StylesJS extends js.Object {
  def install(): Unit = js.native
}

@js.native
@JSImport("@material-ui/styles", JSImport.Namespace)
object styles extends StylesJS
        
