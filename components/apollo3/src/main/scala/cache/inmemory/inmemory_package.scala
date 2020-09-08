package apollo
package client3
package cache

import scala.scalajs.js
import js.annotation._
import js.|

package object inmemory {

    type ReactiveVar[T] = js.Function1[js.UndefOr[T], T]
}