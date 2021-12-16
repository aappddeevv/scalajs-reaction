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

package apollo
package client3
package core

import scala.scalajs.js
import js.|
import js.annotation._

/** Common base for value. */
/*
trait FetchPolicies[T] {
  val cacheFirst = "cache-first".asInstanceOf[T]
  val networkOnly = "network-only".asInstanceOf[T]
  val cacheOnly = "cache-only".asInstanceOf[T]
  val standby = "standby".asInstanceOf[T]
}

@js.native
sealed trait MutationFetchPolicy extends js.Any
object MutationFetchPolicy extends FetchPolicies[MutationFetchPolicy]

@js.native
sealed trait FetchPolicy extends js.Any
object FetchPolicy extends FetchPolicies[FetchPolicy] {
  val noCache = "no-cache".asInstanceOf[FetchPolicy]
}

@js.native
sealed trait WatchQueryFetchPolicy extends js.Any
object WatchQueryFetchPolicy extends FetchPolicies[WatchQueryFetchPolicy] {
  val noCache = "no-cache".asInstanceOf[WatchQueryFetchPolicy]
  val cacheAndNetwork = "cache-and-network".asInstanceOf[WatchQueryFetchPolicy]
}

@js.native
sealed trait ErrorPolicy extends js.Any
object ErrorPolicy {
  val none = "none".asInstanceOf[ErrorPolicy]
  val ignore = "ignore".asInstanceOf[ErrorPolicy]
  val all = "all".asInstanceOf[ErrorPolicy]
}
*/
type FetchPolicies = "cache-first" | "network-only" | "cache-only" | "standby"
type MutationFetchPolicy = FetchPolicies
type FetchPolicy = FetchPolicies | "no-cache"
type WatchQueryFetchPolicy = FetchPolicies | "no-cache" | "cache-and-network"
type ErrorPolicy = FetchPolicies | "none" | "ignore" | "all"