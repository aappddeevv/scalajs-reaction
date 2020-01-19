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

package apollo_link

// combined apollo-link, apollo-link-http, apollo-link-http-common, apollo-link-error, apollo-link-context

import scala.scalajs.js

import js.annotation._
import js.|

import graphql._

// from apollo-link-http-common
trait HttpLinkOptions extends js.Object {
  var uri: js.UndefOr[String] = js.undefined
  @JSName("uri")
  var uriThunk: js.UndefOr[js.Function1[Operation[js.Object], String]] = js.undefined
  var includeExtensions: js.UndefOr[Boolean]                           = js.undefined
  var fetch: js.UndefOr[js.Any]                                        = js.undefined
  var headers: js.UndefOr[js.Any]                                      = js.undefined
  var credentials: js.UndefOr[String]                                  = js.undefined
  var fetchOption: js.UndefOr[js.Any]                                  = js.undefined
}

// apollo-link-http
trait Options extends HttpLinkOptions {
  var useGETForQueries: js.UndefOr[Boolean] = js.undefined
}

trait FetchOptions extends Options

@js.native
trait GraphQLRequest[TVars <: js.Object] extends js.Object {
  val query: DocumentNode               = js.native
  val variables: js.UndefOr[TVars]      = js.native
  val operationName: js.UndefOr[String] = js.native
  val context: js.UndefOr[js.Object]    = js.native
  val extensions: js.UndefOr[js.Object] = js.native
}

// Looks like GraphQLRequest but most members are guaranteed to exist. */
@js.native
trait Operation[TVars <: js.Object] extends js.Object {
  val query: DocumentNode                                    = js.native
  val variables: TVars                                       = js.native
  val operationName: String                                  = js.native
  val extensions: js.Object                                  = js.native
  def setContext(context: js.Object | js.Dynamic): js.Object = js.native
  def getContext(): js.Object                                = js.native
  def toKey(): String                                        = js.native
}

@js.native
trait ApolloLinkCombinators extends js.Object {
  def empty(): ApolloLink                                    = js.native
  def from(links: js.Array[ApolloLink]): ApolloLink          = js.native
  def split(): ApolloLink                                    = js.native
  def concat(lhs: ApolloLink, righs: ApolloLink): ApolloLink = js.native
  // execute???
}

@js.native
trait ApolloLink extends ApolloLinkCombinators

/** Static methods on the HttpLink class. */
@js.native
@JSImport("apollo-link", "ApolloLink")
object ApolloLink extends ApolloLinkCombinators

@js.native
@JSImport("apollo-link", JSImport.Namespace)
object apollo_link_module extends ApolloLinkCombinators

@js.native
@JSImport("apollo-link-http", "HttpLink")
class HttpLink(options: js.UndefOr[HttpLinkOptions] = js.undefined) extends ApolloLink {
  // should return RequestHandler
  val requester: js.Any = js.native
}

@js.native
@JSImport("apollo-link-http", "createHttpLink")
object createHttpLink extends js.Object {
  def apply(linkOptions: js.UndefOr[FetchOptions | js.Dynamic] = js.undefined): ApolloLink = js.native
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
  def apply[TVars <: js.Object, C <: js.Object](setter: js.Function2[GraphQLRequest[TVars], C, js.Any]): ApolloLink =
    js.native
}

@js.native
@JSImport("apollo-link-context", "setContext")
object setContextF extends js.Object {

  /** (QL request, previous context in chain of context changers) => promise new context. Using "updater" pattern. */
  def apply[TVars <: js.Object, C <: js.Object](
    setter: js.Function2[GraphQLRequest[TVars], C, js.Thenable[_]]
  ): ApolloLink = js.native
}

// apollo-link-http-common
@js.native
trait ServerError extends js.Error {
  val response: org.scalajs.dom.experimental.Response = js.native
  val result: js.Dictionary[js.Any]                   = js.native
  val statusCode: Int                                 = js.native
}

// apollo-link-http-common
@js.native
trait ServerParseError extends js.Error {
  val response: org.scalajs.dom.experimental.Response = js.native
  val statusCode: Int                                 = js.native
  val bodyText: String                                = js.native
}

@js.native
trait ErrorResponse extends js.Object {
  val graphQLErrors: js.UndefOr[js.Array[GraphQLError]]            = js.native
  val networkError: js.Error | ServerError | ServerParseError      = js.native
  def response[T <: js.Any]: js.UndefOr[ExecutionResult[T]]        = js.native
  val operation: Operation[js.Object]                              = js.native
  def forward[TVars <: js.Object, T <: js.Any]: NextLink[TVars, T] = js.native
}

@js.native
@JSImport("apollo-link-error", JSImport.Namespace)
object apollo_link_error_module extends js.Object {
  def onError[T <: js.Any](errorHandler: ErrorHandler[T]): ApolloLink =
    js.native
}

// apollo-link
@js.native
trait FetchResult[T <: js.Any] extends ExecutionResult[T] {
  def context[C <: js.Object]: js.UndefOr[C]
}
