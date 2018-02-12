// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT
package ttg
package react
package examples

import scala.scalajs.js
import js.|

import react.implicits._
import redux.Dispatcher

package object addressmanager {
  type Id = String
  type AddressList = js.Array[Address]
  type IdList = js.Array[String]
  type Result = Either[String, AddressList]
  type CRUDResult = Either[String, Unit]
  val emptyAddressList = js.Array[Address]()
  import ttg.react.fabric.IObjectWithKey
  val getAddressKey: js.Function1[Address, String] =
    (item: Address) => item.customeraddressid.getOrElse("")

  /** 
   * View model backed by redux dispatcher. Note that react-redux separates
   * property getting from dispach setting for some reason make integrated view
   * models more difficult.
   * 
   * @param getState Return the overall redux state.
   */
  def mkReduxAddressesViewModel(getState: => js.Object, dispatch: Dispatcher) = {
    new AddressesViewModel {
      def getSelectedIds() = getState.asDyn.addressManager.selectedIds.asInstanceOf[IdList]
      def setSelectedIds(ids: IdList): Unit = dispatch(ActionsNS.AddressManagerActions.setSelectedIds(ids))
    }
  }

}
