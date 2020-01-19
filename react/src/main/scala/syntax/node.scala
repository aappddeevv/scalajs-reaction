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

/** Convert any scala object to a ReactNode via `blah.toNode`. */
final case class ValueOps[T <: scala.Any](v: T) extends AnyVal {
  def toNode: ReactNode = v.asInstanceOf[ReactNode]
}

/** Enable `value.toNode` syntax. `ValueConverters` enable direct conversion to
 * ReactNode if you want to avoid the syntax and have magic.
 */
trait ValueSyntax {
  implicit def stringValueOpsSyntax(v: String)   = ValueOps[String](v)
  implicit def intValueOpsSyntax(v: Int)         = ValueOps[Int](v)
  implicit def floatValueOpsSyntax(v: Float)     = ValueOps[Float](v)
  implicit def doubleValueOpsSyntax(v: Double)   = ValueOps[Double](v)
  implicit def booleanValueOpsSyntax(v: Boolean) = ValueOps[Boolean](v)
}
