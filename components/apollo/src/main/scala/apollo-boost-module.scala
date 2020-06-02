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

package apollo_boost

import scala.scalajs.js

import js.annotation._
import js.|

import apollo_client._
import apollo_link._
import graphql._

@js.native
@JSImport("apollo-boost", JSImport.Namespace)
object module extends js.Object {
  def gql(query: js.Array[String], placeholders: js.Any*): DocumentNode = js.native
}

@js.native
sealed trait FetchType extends js.Any
object FetchType {
  val normal = 1.asInstanceOf[FetchType]
  val refetch = 2.asInstanceOf[FetchType]
  val poll = 3.asInstanceOf[FetchType]
}

trait InMemoryCacheOptions extends js.Object {
  var addTypename: js.UndefOr[Boolean] = js.undefined
  var dataIdFromObject: js.UndefOr[js.Function1[js.Object, String | Null]] = js.undefined
  var fragmentMatcher: js.UndefOr[js.Object | js.Dynamic] = js.undefined
  var cacheRedirects: js.UndefOr[js.Object | js.Dynamic] = js.undefined
}

@js.native
@JSImport("apollo-boost", "InMemoryCache")
class InMemoryCache(options: js.UndefOr[InMemoryCacheOptions] = js.undefined) extends ApolloCache

trait ClientStateConfig extends js.Object {
  var cache: js.UndefOr[ApolloCache] = js.undefined
  var defaults: js.UndefOr[js.Object] = js.undefined
  //var resolvers
  //var typeDefs
  //var fragmentMatcher
}

/** This client has a slightly different config than the base ApolloClient. Most
 * imports import this as ApolloClient which is confusing.
 */
@js.native
@JSImport("apollo-boost", JSImport.Default)
class DefaultClient(config: js.UndefOr[PresetConfig[_]] = js.undefined) extends apollo_client.ApolloClient

trait PresetConfig[TVars <: js.Object] extends js.Object {
  var request: js.UndefOr[js.Function1[Operation[TVars], Unit]] = js.undefined
  @JSName("request")
  var requestF: js.UndefOr[js.Function1[Operation[TVars], js.Thenable[Unit]]] = js.undefined
  var uri: js.UndefOr[String] = js.undefined
  var credentials: js.UndefOr[String] = js.undefined
  var headers: js.UndefOr[js.Any] = js.undefined
  //fetch
  var clientState: js.UndefOr[ClientStateConfig] = js.undefined
  //onError
  //cacheRedirects
  var cache: js.UndefOr[ApolloCache] = js.undefined
  var name: js.UndefOr[String] = js.undefined
  var version: js.UndefOr[String] = js.undefined
  //var resolvers
  //var typeDefs
  //fragmentMatcher
  var assumeImmutableResults: js.UndefOr[Boolean] = js.undefined
}
