
package ttg
package examples

import scala.scalajs.js
import js.annotation._

@js.native
trait address extends js.Object {
  def zipCode(): String    = js.native
  def city(): String       = js.native
  def cityPrefix(): String = js.native
  def state(): String      = js.native
  def country(): String    = js.native
}

@js.native
trait name extends js.Object {
  def lastName(): String  = js.native
  def firstName(): String = js.native
}

@js.native
@JSImport("faker", JSImport.Namespace)
object faker extends js.Object {
  val address: address = js.native
  val name: name       = js.native
}
