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
package syntax 

import scala.scalajs.js
import scala.annotation.targetName

/** Convert values to `ReactNode` via casts. */
object node:
  extension [T <: scala.Any](v: T)
    @targetName("toNodeJSAny")
    def unsafeToNode: ReactNode = v.asInstanceOf[ReactNode]


  /** Convert common "primitive" types to `ReactNode`. */
  extension [T <: String|Long|Int|Double|Float|Byte|Boolean](v: T)
    @targetName("toNodePrimitive")
    def toNode: ReactNode = v.asInstanceOf[ReactNode]

  // extension (v: () => ReactNode)
  //   @targetName("thunkToSFC")
  //   def toNode: ReactNode = js.Any.fromFunction0(v).asInstanceOf[ReactNode]
