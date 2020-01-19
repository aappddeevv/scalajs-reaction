
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
    createElement0(AddressSummaryNS.AddressSummary, props)
  }
}
