// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package examples

import scala.scalajs.js
import js.|

/** Actions for changing global app state in redux. */
trait GlobalAppAction extends react_redux.Action {
  // ...
}

/** JS definition in view.ts. These have UndefOr but they are never undefined.
 */
trait ViewState extends js.Object {
  val label: js.UndefOr[String | Null]
  val selectedTabKey: js.UndefOr[String | Null]
}

/** JS definition in .ts. Notice no UndefOr just "| Null". */
trait AddressManagerState extends js.Object {
  val activeId: Id | Null
  val active: addressmanager.Address | Null
  val lastActiveAddressId: Id | Null
}

/** Small global app state for demonstration purposes. */
trait GlobalAppState extends js.Object {
  val view: ViewState
  val addressManager: AddressManagerState
}
