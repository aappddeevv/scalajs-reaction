// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package apollo_client

import scala.scalajs.js
import js.|
import js.annotation._

import graphql._

@js.native
trait ApolloCache extends js.Object

trait ApolloClientOptions extends js.Object {
  val link:  apollo_link.ApolloLink
  val cache: ApolloCache
}

@js.native
@JSImport("apollo-client", "ApolloClient")
class ApolloClient() extends apollo_cache.DataProxy {
  import apollo_cache._
  def stop(): Unit = js.native

  def readQuery[QueryType <: js.Object, TVars <: js.Object](options: Query[TVars], optimistic: js.UndefOr[Boolean] = js.undefined): QueryType | Null = js.native
  def readFragment[TVars <: js.Object, FragmentType <: js.Object](options: Fragment[TVars],
    optimistic: js.UndefOr[Boolean] = js.undefined): FragmentType | Null = js.native
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
  var variables: js.UndefOr[TVars|js.Dynamic] = js.undefined
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
  val data: T = js.native
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

/** Class but we keep as a trait. Extends observable which we do not have
 * yet.
 */
@js.native
trait ObservableQuery[T <: js.Any, TVars <: js.Object] extends js.Object {
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
  def fetchMore(fetchMoreOptions: FetchMoreQueryOptions[TVars] with FetchMoreOptions[T, TVars]):
      js.Promise[ApolloQueryResult[T]] = js.native
  // subscribeToMore
  // setOptions
  // setVariables
  // updateQuery
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
  val networkError: js.Error= js.native//ApolloNetworkError = js.native
}
