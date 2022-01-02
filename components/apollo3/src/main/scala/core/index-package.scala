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
import js.|
import js.annotation._
import js.JSConverters._

import graphql.DocumentNode

package object core
    extends core.networkStatus_module
    with core.types_module
    with core.LocalState_module
    with core.ObservableQuery_module
    with core.QueryInfo_module {

  @js.native
  @JSImport("@apollo/client/core/ObservableQuery", "hasError")
  def hasError(storeValue: QueryStoreValue, policy: ErrorPolicy = "none"): QueryStoreValue = js.native

  @js.native
  @JSImport("@apollo/client/core/networkStatus", "isNetworkRequestInFlight")
  def isNetworkRequestInFlight(networkStatus: js.UndefOr[NetworkStatus] = js.undefined): Boolean = js.native

  @js.native
  @JSImport("graphql-tag", JSImport.Default)
  def gql_raw(op: js.Array[String], placeholders: js.Any*): DocumentNode = js.native

  def gql(query: String) = gql_raw(js.Array(query))

  implicit class GQLHelper(private val sc: StringContext) extends AnyVal {

    /** If gql is not well-formed, this may thrown an error that is hard to determine its location. */
    def gql(args: Any*) = gql_raw(sc.parts.toJSArray, args.map(_.asInstanceOf[js.Any]): _*)

    /** Return either. Using this makes using the hook less ergonomic. */
    def safe_gql(args: Any*): Either[js.JavaScriptException, DocumentNode] =
      try Right(gql_raw(sc.parts.toJSArray, args.map(_.asInstanceOf[js.Any]): _*))
      catch { case e: js.JavaScriptException => Left(e) }
  }
}
