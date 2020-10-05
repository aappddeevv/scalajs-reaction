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
package core

import scala.scalajs.js
import js.annotation._
import js.|

import graphql._
import client3.errors._

@js.native
trait QueryInfoBase extends js.Object {
  var variables: js.UndefOr[js.Object] = js.native;
  var networkStatus: NetworkStatus = js.native
  var networkError: js.Error | Null = js.native
  var graphQLErrors: js.Array[GraphQLError] = js.native

}

@js.native
trait QueryStoreValue extends QueryInfoBase

@js.native
@JSImport("@apollo/client/core/QueryInfo", "QueryInfo")
class QueryInfo() extends QueryStoreValue {
  /*
      listeners = new Set<QueryListener>();
  document: DocumentNode | null = null;
  lastRequestId = 1;
  subscriptions = new Set<ObservableSubscription>();
 */
}
