// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package native

import scala.scalajs.js
import scala.scalajs.js.|

package object styling {
  type ComponentStyle = ViewStyle | TextStyle | ImageStyle
  type DynamicStyle = js.Object | js.Dynamic 
  type StyleAny = ComponentStyle | DynamicStyle 
  /** Individual stytle object or an array of them. */
  type Style = StyleAny | js.Array[StyleAny]

  trait RecursiveArray[T] extends js.Array[T | RecursiveArray[T]]
  //type RecursiveArray[T] = js.Array[T | js.Array[T]]

  // tagged T
  type RegisteredStyleSet[T] = T with StyleSet with Registered
  // tagged T, does this make sense?, I don't think so...needs to opaque
  //type RegisteredStyle[T] = T

  /** Use this for style properties on components. */
  type StyleProp[T <: js.Object] =
    T | js.UndefOr[T] | js.Array[T] |
  RecursiveArray[T] |
  Null

  // type @@[+T, +U]     = T with U
  // type Tagged[+T, +U] = T with U
  // object implicits {
  //   implicit class Tagger[T](val t: T) extends AnyVal {
  //     def taggedWith[U]: T @@ U = t.asInstanceOf[T @@ U]
  //   }
  // }
}
