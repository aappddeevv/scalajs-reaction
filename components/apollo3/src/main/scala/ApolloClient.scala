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

import scala.scalajs.js
import js.annotation._
import js.|
import graphql._

import client3.link._
import client3.core._
import client3.cache._
import rxjs._

trait DefaultOptions extends js.Object {
  var watchQuery: js.UndefOr[WatchQueryOptions[?]] = js.undefined
  var query: js.UndefOr[QueryOptions[?]] = js.undefined
  var mutate: js.UndefOr[MutationOptions[?, ?]] = js.undefined
}

trait ApolloClientOptions[Shape] extends js.Object {
  var uri: js.UndefOr[String | UriFunction] = js.undefined
  var credentials: js.UndefOr[String] = js.undefined
  var headers: js.UndefOr[js.Object | js.Dictionary[String]] = js.undefined
  var link: js.UndefOr[ApolloLink[?]] = js.undefined
  val cache: ApolloCache[Shape]
  var ssrForceFetchDelay: js.UndefOr[Int] = js.undefined
  var ssrMode: js.UndefOr[Boolean] = js.undefined
  var connectToDevTools: js.UndefOr[Boolean] = js.undefined
  var queryDeduplication: js.UndefOr[Boolean] = js.undefined
  var defaultOptions: js.UndefOr[DefaultOptions] = js.undefined
  var assumeImmutableResults: js.UndefOr[Boolean] = js.undefined
  var resolvers: js.UndefOr[Resolvers | js.Array[Resolvers]] = js.undefined
  var typeDefs: js.UndefOr[String | js.Array[String] | DocumentNode | js.Array[DocumentNode]] = js.undefined
  var name: js.UndefOr[String] = js.undefined
  var version: js.UndefOr[String] = js.undefined
}

@js.native
@JSImport("@apollo/client", "ApolloClient")
class ApolloClient[Shape](options: ApolloClientOptions[Shape]) extends DataProxy {

  val link: ApolloLink[?] = js.native
  val cache: ApolloCache[Shape] = js.native
  val disableNetworkFetches: Boolean = js.native
  val version: String = js.native
  val queryDeduplication: Boolean = js.native
  val defaultOptions: DefaultOptions = js.native
  //val typeDefs: ApolloClientOptions<TCacheShape>['typeDefs'];
  def stop(): Unit = js.native

  def watchQuery[T, TVars](options: WatchQueryOptions[TVars]): ObservableQuery[T, TVars] = js.native
  def query[T, TVars](options: QueryOptions[TVars]): js.Promise[ApolloQueryResult[T]] = js.native
  def mutate[T, TVars](options: MutationOptions[T, TVars]): js.Promise[StandardFetchResult[T]] = js.native
  def subscribe[T, TVars](options: SubscriptionOptions[TVars]): Observable[StandardFetchResult[T]] = js.native

  def readQuery[T, TVars](options: DataProxy.Query[TVars], optimistic: js.UndefOr[Boolean] = js.undefined): T | Null =
    js.native
  def readFragment[T, TVars](
    options: DataProxy.Fragment[TVars],
    optimistic: js.UndefOr[Boolean] = js.undefined): T | Null = js.native
  def writeQuery[T, TVars](options: DataProxy.WriteQueryOptions[T, TVars]): Unit = js.native
  def writeFragment[T, TVars](options: DataProxy.WriteFragmentOptions[T, TVars]): Unit = js.native

  def resetStore(): js.Promise[js.Array[ApolloQueryResult[Any]] | Null] = js.native
  def clearStore(): js.Promise[js.Array[Any]] = js.native
  // def onResetStore(cb: js.Function0[js.Promise[js.Any]]: js.Function0[Unit] = js.native
  // def onClearStore(cb: js.Function0[js.Promise[js.Any]]: js.Function0[Unit] = js.native
  // def reFetchObservableQueries(includeStandby: js.UndefOr[Boolean] = js.undefined): Promise<ApolloQueryResult<any>[]>;
  def extract(optimistic: js.UndefOr[Boolean] = js.undefined): Shape = js.native
  def restore(serializedState: Shape): ApolloCache[Shape] = js.native
  // def addResolvers(resolvers: Resolvers | Resolvers[]): Unit = js.native
  // def setResolvers(resolvers: Resolvers | Resolvers[]): Unit = js.native
  // def getResolvers(): Resolvers = js.native
  // def setLocalStateFragmentMatcher(fragmentMatcher: FragmentMatcher): Unit = js.native
  def setLink(newLink: ApolloLink[?]): Unit = js.native
}
