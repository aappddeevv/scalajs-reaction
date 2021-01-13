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

package jshelpers

import scala.scalajs.js
import js.|

final class JSArrayOps[T](private val arr: js.Array[T]) extends AnyVal {

  /** Easier cast of elements. Would be nice to do some structural
   * checking here but maybe not...
   */
  def as[B] = arr.asInstanceOf[js.Array[B]]
}

final class RichJSArrayTuple3[T, U, V](private val tuvs: js.Array[((T, U), V)]) extends AnyVal {
  def toTuple3 = tuvs.map(tuv => (tuv._1._1, tuv._1._2, tuv._2))
}

final class RichJSArrayTuple4[T, U, V, W](private val tuvs: js.Array[(((T, U), V), W)]) extends AnyVal {
  def toTuple4 = tuvs.map(tuv => (tuv._1._1._1, tuv._1._1._2, tuv._1._2, tuv._2))
}

/** @todo Use sbt-boilerplace to replacite the tuple functions. */
trait LowerOrderJSArrayImplicits {
//   @inline implicit def toTuple3[T,U,V](tuvs: js.Array[((T,U),V)]): js.Array[(T,U,V)] =
//     tuvs.map(tuv => (tuv._1._1, tuv._1._2, tuv._2))
  @inline implicit def mixedTupel3ToTuple[T, U, V](arr: js.Array[((T, U), V)]): RichJSArrayTuple3[T,U,V] = new RichJSArrayTuple3[T, U, V](arr)
  @inline implicit def mixedTupel4ToTuple[T, U, V, W](arr: js.Array[(((T, U), V), W)]): RichJSArrayTuple4[T,U,V,W] =
    new RichJSArrayTuple4[T, U, V, W](arr)
}

trait JSArraySyntax extends LowerOrderJSArrayImplicits {
  @inline implicit def jsArrayOpsSyntax[T](arr: js.Array[T]): JSArrayOps[T] = new JSArrayOps[T](arr)

}
