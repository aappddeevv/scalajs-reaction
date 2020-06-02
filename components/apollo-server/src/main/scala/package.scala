import scala.scalajs.js
import js.annotation._
import js.|

package object apollo_server {
  import apollo_server._

  @js.native
  @JSImport("apollo-server-errors", "formatApolloErrors")
  def formatApolloErrors(
    errors: js.Array[js.Error],
    options: js.UndefOr[js.Object] = js.undefined): js.Array[ApolloError] = js.native

}
