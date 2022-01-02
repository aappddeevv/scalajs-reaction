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
package link

import scala.scalajs.js
import js.|
import js.annotation._

trait HttpOptions extends js.Object {
  var uri: js.UndefOr[String | UriFunction] = js.undefined
  var includeExtensions: js.UndefOr[Boolean] = js.undefined
  //var fetch, browser or node.js native fetch...
  var headers: js.UndefOr[js.Object | js.Dictionary[Any] | js.Dynamic] = js.undefined
  var credentials: js.UndefOr[String] = js.undefined
  // RequsetInit?
  var fetchOptions: js.UndefOr[js.Dynamic | js.Object] = js.undefined
  var usGETForQueries: js.UndefOr[Boolean] = js.undefined
}

trait HttpQueryOptions extends js.Object {
  var includeQuery: js.UndefOr[Boolean] = js.undefined
  var includeExtensions: js.UndefOr[Boolean] = js.undefined
}

trait HttpConfig extends js.Object {
  var http: js.UndefOr[HttpQueryOptions] = js.undefined
  var options: js.UndefOr[Any] = js.undefined
  var headers: js.UndefOr[js.Object | js.Dictionary[Any] | js.Dynamic] = js.undefined
  var credentials: js.UndefOr[String] = js.undefined
}
@js.native
@JSImport("@apollo/client/link/http/HttpLink", "HttpLink")
class HttpLink[T](val options: HttpOptions) extends ApolloLink[T] {
  val requester: RequestHandler[Any] = js.native
}
