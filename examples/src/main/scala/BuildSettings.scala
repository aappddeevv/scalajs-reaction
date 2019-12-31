// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package examples

import scala.scalajs.js
import js.annotation._

@js.native
@JSImport("BuildSettings", JSImport.Namespace)
object BuildSettings extends js.Object {
  val routePrefix: js.UndefOr[String] = js.native
}
