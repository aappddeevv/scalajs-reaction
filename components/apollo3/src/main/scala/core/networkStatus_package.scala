package apollo
package client3
package core

import scala.scalajs.js
import js.annotation._

package object networkStatus_module {

@js.native
@JSImport("@apollo/client/core/networkStatus", "isNetworkRequestInFlight")
def isNetworkRequsetInFlight(networkStatus: js.UndefOr[NetworkStatus] = js.undefined): Boolean = js.native
}