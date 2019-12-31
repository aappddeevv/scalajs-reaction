// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package examples
package addressmanager

import scala.scalajs.js
import js.annotation.JSImport
import _root_.react._
import js.Dynamic.literal
import js.JSConverters._

@js.native
@JSImport("JSExamples/AddressSummary", JSImport.Namespace)
private object AddressSummaryNS extends js.Object {
  val AddressSummary: ReactJsComponent = js.native
}

object AddressSummary {
  def apply(className: Option[String] = None, address: Option[Address] = None) = {
    val props = literal(
      "className" -> className.orUndefined,
      "address"   -> address.orUndefined
    )
    React.createElement0(AddressSummaryNS.AddressSummary, props)
  }
}
