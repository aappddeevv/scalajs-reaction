// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package examples
package addressmanager

import scala.scalajs.js
import js.annotation._

trait Address extends js.Object {
  val customeraddressid: String
  val name: js.UndefOr[String] = js.undefined
  val city: js.UndefOr[String] = js.undefined
  val stateorprovnce: js.UndefOr[String] = js.undefined
  val createdon: js.UndefOr[String] = js.undefined
}
