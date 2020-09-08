package apollo
package client3
package core_module
package types_module

import scala.scalajs.js
import js.|
import js.annotation._

import graphql._
import networkStatus_module._

@js.native
trait ApolloQueryResult[T] extends js.Object {
    var data: js.UndefOr[T] = js.native
    var errors: js.UndefOr[js.Array[GraphQLError]] = js.native
    val loading: Boolean = js.native
    val netowkrStatus: NetworkStatus = js.native
}

trait MutationQueryReducerOptions[T] extends js.Object {
    val mutationResult: FetchResul[T]
    var queryName: js.UndefOr[String] = js.undefined
    val queryVariables: js.Object|js.Dictionary[Any]
}
