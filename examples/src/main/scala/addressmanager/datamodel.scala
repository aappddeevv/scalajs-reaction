// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react.examples
package addressmanager

import scala.scalajs.js
import js.annotation._

trait Address extends js.Object {
  val customeraddressid: js.UndefOr[String] = js.undefined
  val name: js.UndefOr[String] = js.undefined
  val city: js.UndefOr[String] = js.undefined
  val stateorprovince: js.UndefOr[String] = js.undefined
  val createdon: js.UndefOr[String] = js.undefined
  val postalcode: js.UndefOr[String] = js.undefined
  val country: js.UndefOr[String] = js.undefined
}

trait AddressDAO extends js.Object {
  val fetch: js.Function1[Id, js.Promise[AddressList]]
  val add: js.Function1[Address, js.Promise[Id]]
  val remove: js.Function1[String, js.Promise[Unit]]
  val update: js.Function1[Address, js.Promise[Unit]],
}

/** Manage data access and selection state. */
trait AddressesViewModel extends js.Object {
  def setSelectedIds(ids: js.Array[Id]): Unit
  def getSelectedIds(): js.Array[Id]
}
