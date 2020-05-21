package apollo
package client3
package inmemory

import scala.scalajs.js

package object core {
    import core._
    type Transaction[T] = js.Function1[ApolloCache[T], Unit]
}