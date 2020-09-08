package apollo
package client3
package cache_module
package inmemory_module
package inMemoryCache_module

import scala.scalajs.js
import js.|
import js.annotation._ 

import policies_module._

trait InMemoryCacheConfig extends js.Object {
    var resultCaching: js.UndefOr[Boolean] = js.undefined
    var possiblyTypes: js.UndefOr[PossibleTypesMap] = js.undefined
    var typePolicies: js.UndefOr[TypePolicies] = js.undefined
}

@js.native
@JSImport("@apollo/client/cache/inmemory/inMemoryCache", "InMemoryCache")
class InMemoryCache(config: InMemoryCacheConfig = emptyInMemoryCacheConfig) extends core_module.cache_module.ApolloCache[js.Object] {


}