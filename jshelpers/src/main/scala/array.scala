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
package syntax

import scala.scalajs.js
import scala.annotation.targetName

object array:
  extension [T](arr: js.Array[T])
    /** Easier cast of elements. Would be nice to do some structural
     * checking here but maybe not...
     */
    @targetName("asArray")
    def as[B] = arr.asInstanceOf[js.Array[B]]

  // extension [T, U, V](tuvs: js.Array[((T, U), V)])
  //   def toTuple3: js.Array[(T,U,V)] = tuvs.map(p => (p._1._1, p._1._2, p._2))

  // extension [T, U, V, W](tuvs: js.Array[(((T, U), V), W)])
  //   def toTuple4: js.Array[(T,U,V,W)] = tuvs.map(p => (p._1._1._1, p._1._1._2, p._1._2, p._2))


