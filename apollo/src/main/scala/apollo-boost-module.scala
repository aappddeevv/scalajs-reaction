// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package apollo_boost

import scala.scalajs.js
import js.|
import js.annotation._

import graphql._
import apollo_link._
import apollo_client._

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
  var dataIdFromObject: js.UndefOr[js.Function1[js.Object,String|Null]] = js.undefined
  var fragmentMatcher: js.UndefOr[js.Object|js.Dynamic] = js.undefined
  var cacheRedirects: js.UndefOr[js.Object|js.Dynamic] = js.undefined 
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
@JSImport("apollo-boost", "DefaultClient")
class DefaultClient(config: js.UndefOr[PresetConfig[_]]=js.undefined) extends apollo_client.ApolloClient

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
