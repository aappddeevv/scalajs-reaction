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
import js.annotation.*
import jshelpers.syntax.*

import graphql.*
import client3.link.*

trait UpdateQueryOptionsSD[SubscriptionData] extends js.Object {
  val data: SubscriptionData
}

trait UpdateQueryFnOptions[SubscriptionData, SubscriptionVariables] extends js.Object {
  val subscriptionData: UpdateQueryOptionsSD[SubscriptionData]
  var variables: js.UndefOr[SubscriptionVariables] = js.undefined
}

trait PureQueryOptions extends js.Object {
  val query: DocumentNode
  var variables: js.UndefOr[js.Object | js.Dynamic] = js.undefined
  var context: js.UndefOr[Any] = js.undefined
}

@js.native
trait ApolloQueryResult[T] extends js.Object {
  val data: js.UndefOr[T] = js.native
  val errors: js.UndefOr[js.Array[GraphQLError]] = js.native
  val loading: Boolean = js.native
  val netowkrStatus: NetworkStatus = js.native
}

trait MutationQueryReducerOptions[T] extends js.Object {
  val mutationResult: FetchResult[T, ?, ?]
  var queryName: js.UndefOr[String] = js.undefined
  val queryVariables: js.Object | js.Dictionary[Any] | js.Dynamic
}

trait QueryBaseOptions[TVars] extends js.Object {
  val query: DocumentNode
  var variables: js.UndefOr[TVars] = js.undefined
  var errorPolicy: js.UndefOr[ErrorPolicy] = js.undefined
  var context: js.UndefOr[Any] = js.undefined
}

trait QueryOptions[TVars] extends QueryBaseOptions[TVars] {
  var fetchPolicy: js.UndefOr[FetchPolicy] = js.undefined
}

object QueryOptions {
  def apply[TVars](
    query: DocumentNode,
    fetchPolicy: js.UndefOr[FetchPolicy] = js.undefined,
    errorPolicy: js.UndefOr[ErrorPolicy] = js.undefined,
    variables: js.UndefOr[TVars | js.Object | js.Dynamic] = js.undefined,
    context: js.UndefOr[Any] = js.undefined
  ) =
    js.Dynamic
      .literal(
        "query" -> query.asJSAny,
        "fetchPolicy" -> fetchPolicy.asJSAny,
        "errorPolicy" -> errorPolicy.asJSAny,
        "variables" -> variables.unsafeAsJSAny,
        "context" -> context.unsafeAsJSAny
      )
      .asInstanceOf[QueryOptions[TVars]]
}

trait ModifiableWatchQueryOptions[TVars] extends QueryBaseOptions[TVars] {
  var pollInterval: js.UndefOr[Int] = js.undefined
  var notifyOnNetwokrStatusChange: js.UndefOr[Boolean] = js.undefined
  var returnPartialData: js.UndefOr[Boolean] = js.undefined
  var partialRefetch: js.UndefOr[Boolean] = js.undefined
}

trait WatchQueryOptions[TVars] extends ModifiableWatchQueryOptions[TVars] {
  var fetchPolicy: js.UndefOr[WatchQueryFetchPolicy] = js.undefined
}

trait FetchMoreQueryOptions[TVars] extends js.Object {
  var query: js.UndefOr[DocumentNode] = js.undefined
  var variables: js.UndefOr[TVars | js.Object | js.Dynamic] = js.undefined
  var context: js.UndefOr[Any] = js.undefined
}

trait SubscriptionOptions[TVars] extends js.Object {
  val query: DocumentNode
  var variables: js.UndefOr[TVars | js.Object | js.Dynamic] = js.undefined
  var fetchPolicy: js.UndefOr[WatchQueryFetchPolicy] = js.undefined
  var context: js.UndefOr[Any] = js.undefined
}

trait MutationBaseOptions[T, TVars] extends js.Object {
  var optimisticResponse: js.UndefOr[T | js.Function1[TVars, T]] = js.undefined
  var updateQueries: js.UndefOr[MutationQueryReducersMap[T]] = js.undefined
  var refetchQueries
    : js.UndefOr[RefetchQueryDescription | js.Function1[FetchResult[T, ?, ?], RefetchQueryDescription]] = js.undefined
  var awaitRefetchQueries: js.UndefOr[Boolean] = js.undefined
  var update: js.UndefOr[MutationUpdaterFn[T]] = js.undefined
  var errorPolicy: js.UndefOr[ErrorPolicy] = js.undefined
  var variables: js.UndefOr[TVars] = js.undefined
}

trait MutationOptions[T, TVars] extends MutationBaseOptions[T, TVars] {
  val mutation: DocumentNode
  var context: js.UndefOr[Any] = js.undefined
  var fetchPolicy: js.UndefOr[MutationFetchPolicy] = js.undefined
}

object MutationOptions {
  def apply[T, TVars](
    mutation: DocumentNode,
    context: js.UndefOr[js.Object] = js.undefined,
    fetchPolicy: js.UndefOr[FetchPolicy] = js.undefined,
    errorPolicy: js.UndefOr[ErrorPolicy] = js.undefined,
    variables: js.UndefOr[TVars | js.Object | js.Dynamic] = js.undefined,
    update: js.UndefOr[MutationUpdaterFn[T]] = js.undefined,
    awaitRefetchQueries: js.UndefOr[Boolean] = js.undefined,
    updateQueries: js.UndefOr[MutationQueryReducersMap[T]] = js.undefined,
    optimisticResponse: js.UndefOr[T | js.Function1[TVars, T]] = js.undefined,
    //refetechQueries
    //awaitRefetchQueries
  ) =
    js.Dynamic
      .literal(
        "mutation" -> mutation.asJSAny,
        "context" -> context.asJSAny,
        "fetchPolicy" -> fetchPolicy.asJSAny,
        "errorPolicy" -> errorPolicy.asJSAny,
        "variables" -> variables.unsafeAsJSAny,
        "update" -> update.asJSAny,
        "awaitRefetchQueries" -> awaitRefetchQueries.asJSAny,
        "updateQueries" -> updateQueries.unsafeAsJSAny,
        "optimisticResponse" -> optimisticResponse.unsafeAsJSAny
      )
      .asInstanceOf[MutationOptions[T, TVars]]
}
