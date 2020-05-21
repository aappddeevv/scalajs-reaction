package apollo
package client3

import scala.scalajs.js
import js.annotation._

package object errors {
    @js.native
    @JSImport("@apollo/client/errors", "isApolloError")
    def isApolloError(err: js.Error): Boolean = js.native
}