package recoil

import scala.scalajs.js
import js.|

package object utils {

  type Primitive = Boolean | Int | Float | Double | String | Null | Unit
  type SerializableParameter = Boolean | Int | Float | Double | String | Null | Unit | js.Array[Primitive]
}
