/*
 * Copyright (c) 2018 The Trapelo Group
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
  type RegisteredStyleSet[T] = T & StyleSet & Registered
  // tagged T, does this make sense?, I don't think so...needs to opaque
  //type RegisteredStyle[T] = T

  /** Use this for style properties on components. */
  type StyleProp[T <: js.Object] =
    T | js.UndefOr[T] | js.Array[T] | RecursiveArray[T] | Null

  // type @@[+T, +U]     = T with U
  // type Tagged[+T, +U] = T with U
  // object implicits {
  //   implicit class Tagger[T](val t: T) extends AnyVal {
  //     def taggedWith[U]: T @@ U = t.asInstanceOf[T @@ U]
  //   }
  // }
}
