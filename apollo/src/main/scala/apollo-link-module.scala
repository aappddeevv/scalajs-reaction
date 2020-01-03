// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package apollo_link

// combined apollo-link, apollo-link-http, apollo-link-http-common, apollo-link-error, apollo-link-context

import scala.scalajs.js
import js.|
import js.annotation._

import graphql._

// from apollo-link-http-common
trait HttpLinkOptions extends js.Object {
  var uri: js.UndefOr[String] = js.undefined
  @JSName("uri")
  var uriThunk: js.UndefOr[js.Function1[Operation[js.Object], String]] = js.undefined
  var includeExtensions: js.UndefOr[Boolean] = js.undefined
  var fetch: js.UndefOr[js.Any] = js.undefined
  var headers: js.UndefOr[js.Any] = js.undefined
  var credentials: js.UndefOr[String] = js.undefined
  var fetchOption: js.UndefOr[js.Any] = js.undefined
}

// apollo-link-http
trait Options extends HttpLinkOptions {
  var useGETForQueries: js.UndefOr[Boolean] = js.undefined
}

trait FetchOptions extends Options

@js.native
trait GraphQLRequest[TVars <: js.Object] extends js.Object {
  val query: DocumentNode = js.native
  val variables: js.UndefOr[TVars] = js.native
  val operationName: js.UndefOr[String] = js.native
  val context: js.UndefOr[js.Object] = js.native
  val extensions: js.UndefOr[js.Object] = js.native
}

// Looks like GraphQLRequest but most members are guaranteed to exist. */
@js.native
trait Operation[TVars <: js.Object] extends js.Object {
  val query: DocumentNode = js.native
  val variables: TVars = js.native
  val operationName: String = js.native
  val extensions: js.Object = js.native
  def setContext(context: js.Object|js.Dynamic): js.Object = js.native
  def getContext(): js.Object = js.native
  def toKey(): String = js.native
}

@js.native
trait ApolloLink extends js.Object {
  def concat(next: ApolloLink): ApolloLink = js.native
}

/** Static methods on the HttpLink class. */
@js.native
@JSImport("apollo-link", "ApolloLink")
object ApolloLink extends js.Object {
  val empty: ApolloLink = js.native
  def from(links: js.Array[ApolloLink]): ApolloLink = js.native
}

@js.native
@JSImport("apollo-link-http", "HttpLink")
class HttpLink(options: js.UndefOr[HttpLinkOptions] = js.undefined) extends ApolloLink {
  // should return RequestHandler
  val requester: js.Any = js.native
}

@js.native
@JSImport("apollo-link-http", "createHttpLink")
object createHttpLink extends js.Object {
  def apply(linkOptions: js.UndefOr[FetchOptions|js.Dynamic] = js.undefined): ApolloLink = js.native
}

object HttpLink {
  def apply(target: String) = new HttpLink(
   new HttpLinkOptions {
      uri = target
    }
  )
}

@js.native
@JSImport("apollo-link-context", "setContext")
object setContext extends js.Object {
  def apply[TVars <: js.Object, C <: js.Object](setter: js.Function2[GraphQLRequest[TVars], C, js.Any]): ApolloLink = js.native  
}

@js.native
@JSImport("apollo-link-context", "setContext")
object setContextF extends js.Object {
  /** (QL request, previous context in chain of context changers) => promise new context. Using "updater" pattern. */
  def apply[TVars <: js.Object, C <: js.Object](setter: js.Function2[GraphQLRequest[TVars], C, js.Thenable[_]]): ApolloLink = js.native
}
