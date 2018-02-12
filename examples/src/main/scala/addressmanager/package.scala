// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT
package ttg.react.examples

import scala.scalajs.js
import js.|

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
}
