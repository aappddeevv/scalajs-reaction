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

package apollo_client

import scala.scalajs.js

import js.annotation._
import js.|

import apollo_link.FetchResult
import graphql._

trait PureQueryOptions extends js.Object {
  val query: DocumentNode
  var context: js.UndefOr[js.Any] = js.undefined
}

@js.native
trait MutationQueryReducerOptions[T] extends js.Object {
  val mutationResult: FetchResult[T]
  var queryName: js.UndefOr[String] = js.undefined
  val queryVariables: js.Object // Record<string,any>
}

// can also have other properties
trait MutationBaseOptions[T, TVars <: js.Object] extends js.Object {
  var optimisticResponse: js.UndefOr[T | js.Function1[TVars, T]] = js.undefined
  // dict can take other string=>any args as well
  var updateQueries: js.UndefOr[MutationQueryReducersMap[T, js.Object]] = js.undefined
  var refetchQueries: js.UndefOr[js.Function1[ExecutionResult[T], RefetchQueryDescription] | RefetchQueryDescription] =
    js.undefined
  var awaitRefetchQueries: js.UndefOr[Boolean] = js.undefined
  var update: js.UndefOr[MutationUpdaterFn[T]] = js.undefined
  var errorPolicy: js.UndefOr[ErrorPolicy] = js.undefined
  var variables: js.UndefOr[TVars | js.Dynamic] = js.undefined
}

trait MutationOptions[T, TVars <: js.Object] extends MutationBaseOptions[T, TVars] {
  var mutation: js.UndefOr[DocumentNode] = js.undefined
  var context: js.UndefOr[js.Object] = js.undefined
  var fetchPolicy: js.UndefOr[FetchPolicy] = js.undefined
}

object MutationOptions {
  def apply[T, TVars <: js.Object](
    mutation: DocumentNode,
    context: js.UndefOr[js.Object] = js.undefined,
    fetchPolicy: js.UndefOr[FetchPolicy] = js.undefined,
    errorPolicy: js.UndefOr[ErrorPolicy] = js.undefined,
    variables: js.UndefOr[TVars | js.Dynamic] = js.undefined,
    update: js.UndefOr[MutationUpdaterFn[T]] = js.undefined,
    awaitRefetchQueries: js.UndefOr[Boolean] = js.undefined,
    updateQueries: js.UndefOr[MutationQueryReducersMap[T, js.Object]] = js.undefined,
    optimisticResponse: js.UndefOr[T | js.Function1[TVars, T]] = js.undefined,
  ) =
    js.Dynamic
      .literal(
        "mutation" -> mutation,
        "context" -> context,
        "fetchPolicy" -> fetchPolicy,
        "errorPolicy" -> errorPolicy,
        "variables" -> variables.asInstanceOf[js.Any],
        "update" -> update,
        "awaitRefetchQueries" -> awaitRefetchQueries,
        "updateQueries" -> updateQueries,
        "optimisticResponse" -> optimisticResponse.asInstanceOf[js.Any],
      )
      .asInstanceOf[MutationOptions[T, TVars]]
}

// the actual TVars and T can vary by query so use _
trait DefaultOptions extends js.Object {
  var watchQuery: js.UndefOr[WatchQueryOptions[_]] = js.undefined
  var query: js.UndefOr[QueryOptions[_]] = js.undefined
  var mutate: js.UndefOr[MutationOptions[_, _]] = js.undefined
}

@js.native
trait ApolloCache extends js.Object

trait ApolloClientOptions extends js.Object {
  val cache: ApolloCache
  var link: js.UndefOr[apollo_link.ApolloLink] = js.undefined
  var name: js.UndefOr[String] = js.undefined
  var version: js.UndefOr[String] = js.undefined

  var ssrForceFetchDelay: js.UndefOr[Int] = js.undefined
  var ssrMode: js.UndefOr[Boolean] = js.undefined
  var connectToDevTools: js.UndefOr[Boolean] = js.undefined
  var queryDeduplication: js.UndefOr[Boolean] = js.undefined
  var defaultOptions: js.UndefOr[DefaultOptions] = js.undefined
  var assumeImmutableResults: js.UndefOr[Boolean] = js.undefined
  /*    resolvers?: Resolvers | Resolvers[];
   */
  var typeDefs: js.UndefOr[String | js.Array[String] | DocumentNode | js.Array[DocumentNode]] = js.undefined
  /*
  fragmentMatcher?: FragmentMatcher;
 */
}

@js.native
@JSImport("apollo-client", "ApolloClient")
class ApolloClient(options: js.UndefOr[ApolloClientOptions] = js.undefined) extends apollo_cache.DataProxy {
  import apollo_cache._
  def stop(): Unit = js.native

  def query[T, TVars <: js.Object](options: QueryOptions[TVars]): js.Promise[ApolloQueryResult[T]] = js.native

  def mutate[T, TVars <: js.Object](options: MutationOptions[T, TVars]): js.Promise[FetchResult[T]] =
    js.native

  def readQuery[QueryType <: js.Object, TVars <: js.Object](
    options: Query[TVars],
    optimistic: js.UndefOr[Boolean] = js.undefined
  ): QueryType | Null = js.native

  def readFragment[TVars <: js.Object, FragmentType <: js.Object](
    options: Fragment[TVars],
    optimistic: js.UndefOr[Boolean] = js.undefined
  ): FragmentType | Null = js.native

  def writeQuery[T, TVars <: js.Object](options: WriteQueryOptions[T, TVars]): Unit = js.native
  def writeFragment[T, TVars <: js.Object](options: WriteFragmentOptions[T, TVars]): Unit = js.native
  def writeData[T](options: WriteDataOptions[T]): Unit = js.native
}

@js.native
@JSImport("apollo-client", "ApolloClient")
class ApolloBaseClient(config: js.UndefOr[ApolloClientOptions] = js.undefined) extends ApolloClient()

@js.native
sealed trait NetworkStatus extends js.Any
object NetworkStatus {
  val loading = 1.asInstanceOf[NetworkStatus]
  val setVariables = 2.asInstanceOf[NetworkStatus]
  val fetchMore = 3.asInstanceOf[NetworkStatus]
  val refetch = 4.asInstanceOf[NetworkStatus]
  val poll = 6.asInstanceOf[NetworkStatus]
  val ready = 7.asInstanceOf[NetworkStatus]
  val error = 8.asInstanceOf[NetworkStatus]
}

@js.native
sealed trait ErrorPolicy extends js.Any
object ErrorPolicy {
  val none = "none".asInstanceOf[ErrorPolicy]
  val ignore = "ignore".asInstanceOf[ErrorPolicy]
  val all = "all".asInstanceOf[ErrorPolicy]
}

@js.native
sealed trait FetchPolicy extends js.Any
object FetchPolicy {
  val cacheFirst = "cache-first".asInstanceOf[FetchPolicy]
  val networkOnly = "network-only".asInstanceOf[FetchPolicy]
  val cacheOnly = "cache-only".asInstanceOf[FetchPolicy]
  val noCache = "no-cache".asInstanceOf[FetchPolicy]
  val standby = "standby".asInstanceOf[FetchPolicy]
}

@js.native
sealed trait WatchQueryFetchPolicy extends js.Any
object WatchQueryFetchPolicy {
  val cacheFirst = "cache-first".asInstanceOf[WatchQueryFetchPolicy]
  val networkOnly = "network-only".asInstanceOf[WatchQueryFetchPolicy]
  val cacheOnly = "cache-only".asInstanceOf[WatchQueryFetchPolicy]
  val noCache = "no-cache".asInstanceOf[WatchQueryFetchPolicy]
  val standby = "standby".asInstanceOf[WatchQueryFetchPolicy]
  val cacheAndNetwork = "cache-and-network".asInstanceOf[WatchQueryFetchPolicy]
}

trait QueryBaseOptions[TVars <: js.Object] extends js.Object {
  val query: DocumentNode
  var variables: js.UndefOr[TVars | js.Dynamic] = js.undefined
  var errorPolicy: js.UndefOr[ErrorPolicy] = js.undefined
  var context: js.UndefOr[js.Object] = js.undefined
  var fetchResults: js.UndefOr[Boolean] = js.undefined
  var metadata: js.UndefOr[js.Object] = js.undefined
}

trait QueryOptions[TVars <: js.Object] extends QueryBaseOptions[TVars] {
  var fetchPolicy: js.UndefOr[FetchPolicy] = js.undefined
}

object QueryOptions {
  def apply[TVars <: js.Object](
    query: DocumentNode,
    fetchPolicy: js.UndefOr[FetchPolicy] = js.undefined,
    errorPolicy: js.UndefOr[ErrorPolicy] = js.undefined,
    variables: js.UndefOr[TVars | js.Dynamic] = js.undefined,
    context: js.UndefOr[js.Object] = js.undefined,
    fetchResults: js.UndefOr[Boolean] = js.undefined,
    metadata: js.UndefOr[js.Object] = js.undefined) =
    js.Dynamic
      .literal(
        "query" -> query,
        "fetchPolicy" -> fetchPolicy,
        "errorPolicy" -> errorPolicy,
        "variables" -> variables.asInstanceOf[js.Any],
        "context" -> context,
        "fetchResults" -> fetchResults,
        "metadata" -> metadata
      )
      .asInstanceOf[QueryOptions[TVars]]
}

trait ModifiableWatchQueryOptions[TVars <: js.Object] extends QueryOptions[TVars] {
  var pollInterval: js.UndefOr[Int] = js.undefined
  var notifyOnNEtworkStatusChange: js.UndefOr[Boolean] = js.undefined
  var returnPartialData: js.UndefOr[Boolean] = js.undefined
}

trait WatchQueryOptions[TVars <: js.Object] extends ModifiableWatchQueryOptions[TVars] with QueryBaseOptions[TVars] {
  //var fetchPolicy: js.UndefOr[FetchPolicy] = js.undefined
}

trait FetchMoreOptions[T, TVars <: js.Object] extends js.Object {
  trait Options extends js.Object {
    var fetchMoreResult: js.UndefOr[T] = js.undefined
    var variables: js.UndefOr[TVars] = js.undefined
  }
  var updateQuery: js.UndefOr[js.Function2[T, Options, T]] = js.undefined
}

trait FetchMoreQueryOptions[TVars <: js.Object] extends js.Object {
  var query: js.UndefOr[DocumentNode] = js.undefined
  // this can take a subset of TVars
  var variables: js.UndefOr[TVars] = js.undefined
  var context: js.UndefOr[js.Any] = js.undefined
}

@js.native
trait ApolloQueryResult[T] extends js.Object {
  // is this null or undefined when it is not present? ts defs just say data: T
  val data: T | Null = js.native
  val errors: js.UndefOr[js.Array[GraphQLError]] = js.native
  val loading: Boolean = js.native
  val networkStatus: NetworkStatus = js.native
  val stale: Boolean = js.native
}

@js.native
trait ApolloCurrentResult[T] extends js.Object {
  // T | {}
  val data: T = js.native
  val errors: js.UndefOr[js.Array[GraphQLError]] = js.native
  val loading: Boolean = js.native
  val networkStatus: NetworkStatus = js.native
  val error: js.UndefOr[ApolloError] = js.native
  val partial: js.UndefOr[Boolean] = js.native
}

trait UpdateQueryOptions[TVars <: js.Object] {
  var variables: js.UndefOr[TVars] = js.undefined
}

trait SubscribeToMoreOptions[A, AVars <: js.Object, T] extends js.Object {
  val document: DocumentNode
  var variables: js.UndefOr[AVars] = js.undefined
  // apollo-client/core/watchQueryOptions
  // UpdateQueryFn[A, AVars, T]
  var updateQuery: js.UndefOr[js.Any] = js.undefined
  var onError: js.UndefOr[js.Function1[js.Error, Unit]] = js.undefined
}

/** Class but we keep as a trait since we do not create these directly. */
@js.native
trait ObservableQuery[T, TVars <: js.Object] extends apollo_observable.Observable[T] {
  val queryId: String = js.native
  val options: WatchQueryOptions[TVars] = js.native
  val queryName: js.UndefOr[String] = js.native
  val variables: TVars = js.native

  def result(): js.Promise[ApolloQueryResult[T]] = js.native
  def currentResult(): ApolloCurrentResult[T] = js.native

  def getLastResult(): ApolloQueryResult[T]
  def getLastError(): ApolloError = js.native
  def resetLastResults(): Unit = js.native
  def resetQueryStoreErrors(): Unit = js.native
  def refetch(variables: TVars): js.Promise[ApolloQueryResult[T]] = js.native
  def fetchMore(
    fetchMoreOptions: FetchMoreQueryOptions[TVars] with FetchMoreOptions[T, TVars]
  ): js.Promise[ApolloQueryResult[T]] = js.native
  // subscribeToMore
  def subscribeToMore[A, AVars <: js.Object](
    options: SubscribeToMoreOptions[T, AVars, A]
  ): js.Function0[Unit] = js.native
  def setOptions(opts: WatchQueryOptions[TVars]): Unit | js.Promise[ApolloQueryResult[T]] = js.native
  def setVariables(
    variables: TVars,
    tryFetch: js.UndefOr[Boolean] = js.undefined
  ): Unit | js.Promise[ApolloQueryResult[T]] = js.native
  def updateQuery(f: js.Function2[T, UpdateQueryOptions[TVars], T]): Unit = js.native
  def stopPolling(): Unit = js.native
  def startPolling(pollInterval: Int): Unit = js.native
}

@js.native
trait ApolloNetworkError extends js.Object {
  val name: String = js.native
  val response: js.Object = js.native
  val statusCode: Int = js.native
  val bodyText: String = js.native
}

@js.native
trait ApolloError extends js.Error {
  //val stack: String = js.native
  //val operation: js.Object = js.native
  //val response: js.Object = js.native
  val graphQLErrors: js.Array[GraphQLError] = js.native
  val networkError: js.Error | Null = js.native //ApolloNetworkError = js.native
  val extraInfo: js.UndefOr[js.Any] = js.native
}

@js.native
@JSImport("apollo-client", JSImport.Namespace)
object module extends js.Object {
  def isApolloError(error: js.Error): Boolean = js.native
}
