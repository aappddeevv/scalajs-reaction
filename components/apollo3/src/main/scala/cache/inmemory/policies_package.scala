package apollo
package client3
package cache_module
package inmemory_module

import scala.scalajs.js

package object policies_module { 
    type PossibleTypesMap = js.Dictionary[js.Array[String]]
    type TypePolicies = js.Dictionary[TypePolicy]
}