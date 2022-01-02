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
import js.annotation._
import js.|

import rxjs._

@js.native
@JSImport("apollo3/client/link/core/ApolloLink", "LinkError")
class LinkError(
  message: js.UndefOr[String] = js.undefined,
  val link: js.UndefOr[ApolloLink[?]] = js.undefined
) extends js.Error {
  //val link: js.UndefOr[ApolloLink]
}

@js.native
@JSImport("apollo3/client/link/core", "ApolloLink")
class ApolloLink[T](request: js.UndefOr[RequestHandler[T]] = js.undefined) extends js.Object {
  def request[A](operation: Operation, forward: js.UndefOr[NextLink[A]]): Observable[StandardFetchResult[T]] | Null =
    js.native
  def setOnError(fn: js.Function1[Any, Any]): ApolloLink[T] = js.native
  def concat(other: ApolloLink[T] | RequestHandler[T]): ApolloLink[T] = js.native
  def split(
    test: js.Function1[Operation, Boolean],
    left: ApolloLink[T] | RequestHandler[T],
    right: js.UndefOr[ApolloLink[T] | RequestHandler[T]] = js.undefined): ApolloLink[T] = js.native
  def empty(): ApolloLink[T] = js.native
  def from(links: js.Array[ApolloLink[T] | RequestHandler[T]]): ApolloLink[T] = js.native
  def execute(link: ApolloLink[T], operation: GraphQLRequest): Observable[StandardFetchResult[T]] = js.native
}

@js.native
@JSImport("apollo3/client/link/core", "ApolloLink")
object ApolloLink extends js.Object {
  def empty[T](): ApolloLink[T] = js.native
  def from[T](links: js.Array[ApolloLink[T] | RequestHandler[T]]): ApolloLink[T] = js.native
  def execute[T](link: ApolloLink[T], operation: GraphQLRequest): Observable[StandardFetchResult[T]] = js.native
  def concat[T](first: ApolloLink[T] | RequestHandler[T], second: ApolloLink[T] | RequestHandler[T]): ApolloLink[T] = js.native
  def split[T](
    test: js.Function1[Operation, Boolean],
    left: ApolloLink[T] | RequestHandler[T],
    right: js.UndefOr[ApolloLink[T] | RequestHandler[T]] = js.undefined): ApolloLink[T] = js.native

}
