package apollo
package client3
package core_module

import scala.scalajs.js
import js.|
import js.annotation._

package object types_module {

    type MutationQueryReduce[T] = js.Function2[
        js.Object,
        MutationQueryReducerOptions[T], 
        js.Object
    ]
}