
package ttg
package examples

import scala.scalajs.js
import js.|

import _root_.react.implicits._
import react_redux.Dispatch
import js.JSConverters._

package object addressmanager {
  type Id          = String
  type AddressList = js.Array[Address]
  type IdList      = js.Array[String]
  type Result      = Either[String, AddressList]
  type CRUDResult  = Either[String, Unit]
  val emptyAddressList = js.Array[Address]()
  val emptyIdList      = js.Array[Id]()
  import fabric.components.IObjectWithKey
  val getAddressKey: js.Function1[Address, String] =
    (item: Address) => item.customeraddressid.getOrElse("")
}
