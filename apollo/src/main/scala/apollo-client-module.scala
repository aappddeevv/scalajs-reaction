// Copyright (c) 2019 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package apollo_client

import scala.scalajs.js
import js.|
import js.annotation._

import graphql._
import apollo_link.FetchResult

trait PureQueryOptions extends js.Object {
  val query: DocumentNode
  var context: js.UndefOr[js.Any] = js.undefined
}

@js.native
trait MutationQueryReducerOptions[T <: js.Any] extends js.Object {
  val mutationResult: FetchResult[T]
  var queryName: js.UndefOr[String] = js.undefined
  val queryVariables: js.Object // Record<string,any>
}

// can also have other properties
trait MutationBaseOptions[T <: js.Any, TVars <: js.Object] extends js.Object {
  var optimisticResponse: js.UndefOr[T | js.Function1[TVars, T]] = js.undefined
  // dict can take other string=>any args as well
  var updateQueries: js.UndefOr[MutationQueryReducersMap[T, js.Object]] = js.undefined
  var refetchQueries: js.UndefOr[js.Function1[ExecutionResult[T], RefetchQueryDescription] | RefetchQueryDescription] =
    js.undefined
  var awaitRefetchQueries: js.UndefOr[Boolean] = js.undefined
  var update: js.UndefOr[MutationUpdaterFn[T]] = js.undefined
  var errorPolicy: js.UndefOr[ErrorPolicy] = js.undefined
  var variables: js.UndefOr[TVars] = js.undefined
}

trait MutationOptions[T <: js.Any, TVars <: js.Object] extends MutationBaseOptions[T, TVars] {
  var mutation: js.UndefOr[DocumentNode] = js.undefined
  var context: js.UndefOr[js.Object] = js.undefined
  var fetchPolicy: js.UndefOr[FetchPolicy] = js.undefined
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

  def query[T <: js.Any, TVars <: js.Object](options: QueryOptions[TVars]): js.Promise[ApolloQueryResult[T]] = js.native
  def mutate[T <: js.Any, TVars <: js.Object](options: MutationOptions[T, TVars]): js.Promise[FetchResult[T]] = js.native

  def readQuery[QueryType <: js.Object, TVars <: js.Object](
      options: Query[TVars],
      optimistic: js.UndefOr[Boolean] = js.undefined
  ): QueryType | Null = js.native
  def readFragment[TVars <: js.Object, FragmentType <: js.Object](
      options: Fragment[TVars],
      optimistic: js.UndefOr[Boolean] = js.undefined
  ): FragmentType | Null = js.native
  def writeQuery[T <: js.Any, TVars <: js.Object](options: WriteQueryOptions[T, TVars]): Unit = js.native
  def writeFragment[T <: js.Any, TVars <: js.Object](options: WriteFragmentOptions[T, TVars]): Unit = js.native
  def writeData[T <: js.Any](options: WriteDataOptions[T]): Unit = js.native
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

trait ModifiableWatchQueryOptions[TVars <: js.Object] extends QueryOptions[TVars] {
  var pollInterval: js.UndefOr[Int] = js.undefined
  var notifyOnNEtworkStatusChange: js.UndefOr[Boolean] = js.undefined
  var returnPartialData: js.UndefOr[Boolean] = js.undefined
}

trait WatchQueryOptions[TVars <: js.Object] extends ModifiableWatchQueryOptions[TVars] with QueryBaseOptions[TVars] {
  //var fetchPolicy: js.UndefOr[FetchPolicy] = js.undefined
}

trait FetchMoreOptions[T <: js.Any, TVars <: js.Object] extends js.Object {
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
trait ApolloQueryResult[T <: js.Any] extends js.Object {
  // is this null or undefined when it is not present? ts defs just say data: T
  val data: T | Null = js.native
  val errors: js.UndefOr[js.Array[GraphQLError]] = js.native
  val loading: Boolean = js.native
  val networkStatus: NetworkStatus = js.native
  val stale: Boolean = js.native
}

@js.native
trait ApolloCurrentResult[T <: js.Any] extends js.Object {
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

trait SubscribeToMoreOptions[A <: js.Any, AVars <: js.Object, T <: js.Any] extends js.Object {
  val document: DocumentNode
  var variables: js.UndefOr[AVars] = js.undefined
  // apollo-client/core/watchQueryOptions
  // UpdateQueryFn[A, AVars, T]
  var updateQuery: js.UndefOr[js.Any] = js.undefined
  var onError: js.UndefOr[js.Function1[js.Error, Unit]] = js.undefined
}

/** Class but we keep as a trait since we do not create these directly. */
@js.native
trait ObservableQuery[T <: js.Any, TVars <: js.Object] extends apollo_observable.Observable[T] {
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
  def subscribeToMore[A <: js.Any, AVars <: js.Object](
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
  //val message: String = js.native
  val operation: js.Object = js.native
  val response: js.Object = js.native
  val graphQLErrors: js.Object = js.native
  val networkError: js.Error = js.native //ApolloNetworkError = js.native
}