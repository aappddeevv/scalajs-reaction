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
package react
package hooks
package types

import scala.scalajs.js
import js.annotation._
import js.|

import client3.core._
import client3.link._

trait types_module {

  type Context = js.Dictionary[Any] | js.Object | js.Dynamic

  type MutationTuple[T, TVars] =
    js.Tuple2[
      js.Function1[MutationFunctionOptions[T, TVars], js.Promise[StandardFetchResult[T]]],
      MutationResult[T]
    ]

  type MutationFunction[T, TVars] =
    js.Function1[MutationFunctionOptions[T, TVars], js.Promise[StandardFetchResult[T]]]

  /** This is not right, arbitrary args for this. */
  type RefetchQueriesFunction = js.Function1[Any, js.Array[String | PureQueryOptions]]

  type QueryTuple[T, TVars] =
    js.Tuple2[js.Function1[QueryLazyOptions[TVars], Unit], LazyQueryResult[T, TVars]]

  type UnexecutedLazyResult = UnexecutedLazyFields & AbsentLazyResultFields

  type LazyQueryResult[T, TVars] = UnexecutedLazyResult | QueryResult[T, TVars]

}
