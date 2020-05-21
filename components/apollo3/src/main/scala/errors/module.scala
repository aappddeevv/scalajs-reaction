

package apollo
package client3
package error

import scala.scalajs.js
import js.|
import js.annotation._

@js.native
@JSImport("@apollo/client", "ApolloError")
class ApolloError() extends js.Error {
    val message: String = js.native
    val networkError: js.Error | Null = js.native
    val extraInfo: js.Any = js.native
    val graphQLErrors: js.Array[graphql.GraphQLError] = js.native
}
