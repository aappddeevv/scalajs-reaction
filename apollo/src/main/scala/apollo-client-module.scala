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
trait ApolloClient extends js.Object {
  def stop(): Unit = js.native
  def readQuery[T <: js.Object](query: DocumentNode, optimistic: js.UndefOr[Boolean] = js.undefined): T | Null = js.native
}

@js.native
@JSImport("apollo-client", "ApolloClient")
class ApolloBaseClient(config: js.UndefOr[ApolloClientOptions] = js.undefined) extends ApolloClient

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


