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

import scala.scalajs.js

import js.|

package object apollo_client {
  import apollo_cache._
  import apollo_client._
  import apollo_link.FetchResult

  // apollo-client/core/types
  type MutationQueryReducer[T, R <: js.Object] = js.Function2[js.Object, MutationQueryReducerOptions[T], R]
  type RefetchQueryDescription = js.Array[String] | PureQueryOptions
  type MutationUpdaterFn[T] = js.Function2[DataProxy, FetchResult[T], Unit]
  type MutationQueryReducersMap[T, R <: js.Object] = js.Dictionary[MutationQueryReducer[T, R]]
}
