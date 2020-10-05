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

import scala.scalajs.js
import js.|
import js.annotation._

import _root_.react._

import graphql._
import client3.errors._
import client3.core._

@js.native
trait ApolloContextValue extends js.Object {
  val client: js.UndefOr[ApolloClient[js.Object]] = js.undefined
  val renderPromises: js.UndefOr[js.Object] = js.undefined
}

trait CommonOptions extends js.Object {
  var client: js.UndefOr[ApolloClient[_]] = js.undefined
}

trait BaseQueryOptions[T, TVars] extends js.Object {
  var ssr: js.UndefOr[Boolean] = js.undefined
  var variables: js.UndefOr[TVars] = js.undefined
  var fetchPolicy: js.UndefOr[WatchQueryFetchPolicy] = js.undefined
  var errorPolicy: js.UndefOr[ErrorPolicy] = js.undefined
  var pollInterval: js.UndefOr[Int] = js.undefined
  var client: js.UndefOr[ApolloClient[_]] = js.undefined
  var notifyOnNetworkStatusChange: js.UndefOr[Boolean] = js.undefined
  var context: js.UndefOr[Context] = js.undefined
  var partialRefetch: js.UndefOr[Boolean] = js.undefined
  var returnPartialData: js.UndefOr[Boolean] = js.undefined
}

trait PartialQueryFunctionOptions[T, TVars] extends BaseQueryOptions[T, TVars] {
  var displayName: js.UndefOr[String] = js.undefined
  var onCompleted: js.UndefOr[js.Function1[T, Unit]] = js.undefined
  var onError: js.UndefOr[js.Function1[ApolloError, Unit]] = js.undefined
}

trait QueryFunctionOptions[T, TVars] extends PartialQueryFunctionOptions[T, TVars] {
  var skip: js.UndefOr[String] = js.undefined
}

@js.native
trait ObservableQueryFields[T, TVars] extends PartialObservableQuery[T, TVars] {
  // big tangle for fetchMore method
  def fetchMore(arg: js.Object): js.Promise[js.Dynamic] = js.native
}

@js.native
trait QueryResult[T, TVars] extends ObservableQueryFields[T, TVars] {
  val client: ApolloClient[_] = js.native
  var data: js.UndefOr[T] = js.native
  var error: js.UndefOr[ApolloError] = js.native
  var loading: Boolean = js.native
  var networkStatus: NetworkStatus = js.native
  /** Only true with lazy query. */
  var called: Boolean = js.native
}

object QueryResult {
  implicit final class RichQueryResult private[QueryResult] (private val qr: QueryResult[_, _]) extends AnyVal {
    def successfullyLoaded =
      !qr.loading &&
        qr.error.isEmpty &&
        qr.data.isDefined &&
        //qr.data != null &&
        qr.networkStatus == NetworkStatus.ready
    def finishedRemoteCall =
      !qr.loading && qr.networkStatus == NetworkStatus.ready
  }
}


trait QueryDataOptions[T, TVars] extends QueryFunctionOptions[T, TVars] {
  var query: DocumentNode
  var children: js.UndefOr[js.Function1[QueryResult[T, TVars], ReactNode]] = js.undefined
}

trait QueryHookOptions[T, TVars] extends QueryFunctionOptions[T, TVars] {
  var query: js.UndefOr[DocumentNode] = js.undefined
}

trait LazyQueryHookOptions[T, TVars] extends PartialQueryFunctionOptions[T, TVars] {
  var query: js.UndefOr[DocumentNode] = js.undefined
}

trait QueryPreviousData[T, TVars] {
  var client: js.UndefOr[ApolloClient[_]] = js.undefined
  var query: js.UndefOr[DocumentNode] = js.undefined
  var observableQueryOptions: js.UndefOr[js.Object | js.Dynamic] = js.undefined
  var result: js.UndefOr[QueryResult[T, TVars] | Null] = js.undefined
  var loading: js.UndefOr[Boolean] = js.undefined
  var options: js.UndefOr[QueryDataOptions[T, TVars]] = js.undefined
  var erorr: js.UndefOr[ApolloError] = js.undefined
}

trait QueryLazyOptions[TVars] extends js.Object {
  var variables: js.UndefOr[TVars] = js.undefined
  var context: js.UndefOr[Context] = js.undefined
}

@js.native
trait UnexecutedLazyFields extends js.Object {
  val loading: Boolean = js.native
  val networkStatus: Boolean = js.native
  val called: Boolean = js.native
  val data: js.UndefOr[Any] = js.native
}

trait AbsentLazyResultFields extends js.Object {
  // ???
}

/** Mutation types. */
trait BaseMutationOptions[T, TVars] extends js.Object {
  var variables: js.UndefOr[TVars] = js.undefined
  var optimisticResponse: js.UndefOr[T | js.Function1[TVars | js.Object | js.Dynamic, T]] = js.undefined
  var awaitRefetchQueries: js.UndefOr[Boolean] = js.undefined
  var errorPolicy: js.UndefOr[ErrorPolicy] = js.undefined
  var update: js.UndefOr[MutationUpdaterFn[T]] = js.undefined
  var notifyOnNetworkStatusChange: js.UndefOr[Boolean] = js.undefined
  var context: js.UndefOr[Context | js.Object | js.Dynamic] = js.undefined
  var onCompleted: js.UndefOr[js.Function1[T, Unit]] = js.undefined
  var onError: js.UndefOr[js.Function1[ApolloError, Unit]] = js.undefined
  var fetchPolicy: js.UndefOr[WatchQueryFetchPolicy] = js.undefined
  var ignoreResults: js.UndefOr[Boolean] = js.undefined
}

trait MutationFunctionOptions[T, TVars] extends js.Object {
  var variables: js.UndefOr[TVars] = js.undefined
  var optimisticResponse: js.UndefOr[T | js.Function1[TVars | js.Object | js.Dynamic, T]] = js.undefined
  var awaitRefetchQueries: js.UndefOr[Boolean] = js.undefined
  var update: js.UndefOr[MutationUpdaterFn[T]] = js.undefined
  var context: js.UndefOr[Context] = js.undefined
  var fetchPolicy: js.UndefOr[MutationFetchPolicy] = js.undefined
}

@js.native
trait MutationResult[T] extends js.Object {
  /** Unlike QueryResul,t this is js.UndefOr[T|Null] */
  val data: js.UndefOr[T | Null] = js.native
  val error: js.UndefOr[ApolloError] = js.native
  val loading: Boolean = js.native
  val called: Boolean = js.native
  val client: js.UndefOr[ApolloClient[_]] = js.native
}

trait MutationHookOptions[T, TVars] extends BaseMutationOptions[T, TVars] {
  var mutation: js.UndefOr[DocumentNode] = js.undefined
}

trait MutationDataOptions[T, TVars] extends BaseMutationOptions[T, TVars] {
  val mutation: DocumentNode
}

/* Subscription types */

trait OnSubscriptionDataOptions[T] extends js.Object {
  val client: ApolloClient[_]
  val subscriptionData: SubscriptionResult[T]
}

trait BaseSubscriptionOptions[T, TVars] extends js.Object {
  var variables: js.UndefOr[TVars] = js.undefined
  var fetchPolicy: js.UndefOr[FetchPolicy] = js.undefined
  var shouldResubscribe: js.UndefOr[Boolean | js.Function1[BaseSubscriptionOptions[T, TVars], Any]] = js.undefined
  var onSubscriptionComplete: js.UndefOr[js.Function0[Unit]] = js.undefined
}

@js.native
trait SubscriptionResult[T] extends js.Object {
  val loading: Boolean = js.native
  /** Matches QueryResult.data but not MutationResult.data. */
  val data: js.UndefOr[T] = js.native
  val error: js.UndefOr[ApolloError] = js.native
}

trait SubscriptionHookOptions[T, TVars] extends BaseSubscriptionOptions[T, TVars] {}

trait SubscriptionDataOptions[T, TVars] extends BaseSubscriptionOptions[T, TVars] {}

trait SubscriptionCurrentObservable extends js.Object {}
