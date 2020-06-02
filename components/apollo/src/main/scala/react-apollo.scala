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
import js.JSConverters._
import apollo_boost._
import apollo_client._
import graphql._

/** Relies on @apollo/react-hooks, @apollo/react-hoc, @apollo/react-common,
 * @apollo/react-ssr and @apollo/react-component in the real world. We just
 * export the hook related parts for scala.js.
 */
package object react_apollo {

  def useQuery[T, TVars <: js.Object](
    query: DocumentNode,
    options: js.UndefOr[QueryHookOptions[T, TVars] | js.Dynamic] = js.undefined
  ): QueryResult[T, TVars] =
    react_apollo.module.useQuery(query, options)

  def useMutation[T, TVars <: js.Object](
    mutation: DocumentNode,
    options: js.UndefOr[MutationHookOptions[T, TVars] | js.Dynamic] = js.undefined
  ): (js.Function1[MutationFunctionOptions[T, TVars], js.Promise[ExecutionResult[T]]], MutationResult[T]) =
    react_apollo.module.useMutation[T, TVars](mutation, options)

  def useLazyQuery[T, TVars <: js.Object](
    query: DocumentNode,
    options: js.UndefOr[LazyQueryHookOptions[T, TVars] | js.Dynamic] = js.undefined
  ): (js.Function1[QueryLazyOptions[TVars], Unit], QueryResult[T, TVars]) =
    react_apollo.module.useLazyQuery[T, TVars](query, options)

  def useSubscription[T, TVars <: js.Object](
    subscription: DocumentNode,
    options: js.UndefOr[SubscriptionHookOptions[T, TVars]] = js.undefined
  ) =
    react_apollo.module.useSubscription[T, TVars](subscription, options)

  def useBaseQuery[T, TVars <: js.Object](
    query: DocumentNode,
    options: js.UndefOr[QueryHookOptions[T, TVars]] = js.undefined
  ) =
    react_apollo.module.useBaseQuery[T, TVars](query, options)

  def useApolloClient() = react_apollo.module.useApolloClient()

  // @apollo/react-common
  /** Often used as a default type for TVars */
  type OperationVariables = js.Dictionary[js.Any]
}
