package apollo
package client3
package link
package context

import scala.scalajs.js
import js.|
import js.annotation._

trait context_module {
    type ContextSetter[C] = js.Function2[GraphQLRequest, C, js.Promise[_]|Any]
}