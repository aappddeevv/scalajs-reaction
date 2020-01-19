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

import scala.annotation.unchecked.{ uncheckedVariance => uv }

/**
 * Memoize the last value only. Since its only 1 arg, use `yourfunc _ tupled`
 * to tuple the arguments. Uses scala == for the test. js semantics for
 * equality are very different. scala provids semantic equality unlike js. An
 * optional `eq` can be used. In general, you will probably memoize js
 * functions using the react hooks so may not need this memoization utility
 * much.
 *
 * @see https://medium.com/musings-on-functional-programming/scala-optimizing-expensive-functions-with-memoization-c05b781ae826
 *
 */
case class MemoizeLast[-T, +R](
  f: T => R,
  eq: (T, T) => Boolean = (a: T, b: T) => a == b
) extends (T => R) {
  private var args: Option[T @uv] =
    None
  private var result: Option[R @uv] =
    None
  private var _hits: Long = 0

  def apply(x: T): R =
    (args, result) match {
      case (Some(p), Some(r)) if eq(p, x) =>
        _hits = _hits + 1
        r
      case _ =>
        args = Some(x)
        val r = f(x)
        result = Some(r)
        r
    }

  def hits = _hits
}

object MemoizeLast {
  import scala.scalajs.js
  //def apply[T, R](f: T => R) = new MemoizeLast(f)
  //def withEq[T, R](f: T => R, eq: (T,T) => Boolean) = new MemoizeLast(f, eq)
  def JS[T <: js.Any, R <: js.Any](f: T => R) = new MemoizeLast(f, react.jsEqual)
}
