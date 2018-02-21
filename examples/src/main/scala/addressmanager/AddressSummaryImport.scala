// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react.examples
package addressmanager

import scala.scalajs.js
import js.annotation.JSImport
import ttg.react._
import ttg.react.elements._
import js.Dynamic.literal
import js.JSConverters._

@js.native
@JSImport("JSExamples/AddressSummary", JSImport.Namespace)
private object AddressSummaryNS extends js.Object {
  val AddressSummary: ReactJsComponent = js.native
}

object AddressSummaryC {
  def make(className: Option[String] = None, address: Option[Address] = None) = {
    val props = literal(
      "className" -> className.orUndefined,
      "address"   -> address.orUndefined
    )
    elements.wrapJsForScala(AddressSummaryNS.AddressSummary, props)
  }
}
