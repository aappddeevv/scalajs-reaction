// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.annotation.unchecked.{uncheckedVariance => uv}

/**
  * Memoize the last value only. Since its only 1 arg, use `yourfunc _ tupled`
  * to tuple the arguments. Uses scala == for the test. js semantics for
  * equality are very different. scala provids semantic equality unlike js. An
  * optional `eq` can be used.
  *
  * @see https://medium.com/musings-on-functional-programming/scala-optimizing-expensive-functions-with-memoization-c05b781ae826
 * 
  */
case class MemoizeLast[-T, +R](
  f: T => R,
  eq: (T,T) => Boolean = (a:T,b:T) => a == b
) extends (T => R) {
  private var args: Option[T @uv] =
    None
  private var result: Option[R @uv] =
    None
  private var _hits: Long = 0

  def apply(x: T): R = {
    (args, result) match {
      case (Some(p), Some(r)) if eq(p,x) =>
        _hits = _hits + 1
        r
      case _ =>
        args = Some(x)
        val r = f(x)
        result = Some(r)
        r
    }
  }

  def hits = _hits
}

object MemoizeLast {
  import scala.scalajs.js
  //def apply[T, R](f: T => R) = new MemoizeLast(f)
  //def withEq[T, R](f: T => R, eq: (T,T) => Boolean) = new MemoizeLast(f, eq)
  def JS[T <: js.Any,R <: js.Any](f: T => R) = new MemoizeLast(f, ttg.react.jsEqual)
}
