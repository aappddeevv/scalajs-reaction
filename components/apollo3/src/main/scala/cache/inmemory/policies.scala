package apollo
package client3
package cache_module
package inmemory_module
package policies_module

import scala.scalajs.js
import js.|

trait TypePolicy extends js.Object {
    var keyFields: js.UndefOr[js.Any|Boolean] = js.undefined
    var queryType: js.UndefOr[true] = js.undefined
    var mutationType: js.UndefOr[true] = js.undefined
    var subscriptionType: js.UndefOr[true] = js.undefined
    var fields: js.UndefOr[js.Object] = js.undefined
}
