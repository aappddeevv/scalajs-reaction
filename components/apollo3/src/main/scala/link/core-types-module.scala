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

import graphql._

trait GraphQLRequest extends js.Object {
  val query: DocumentNode
  var variables: js.UndefOr[js.Object] = js.undefined
  var operationName: js.UndefOr[String] = js.undefined
  var context: js.UndefOr[js.Object | js.Dynamic] = js.undefined
  var extensions: js.UndefOr[js.Object | js.Dynamic] = js.undefined
}

@js.native
trait Operation extends js.Object {
  val query: DocumentNode = js.native
  var variables: js.Object = js.native
  var operationName: String = js.native
  var extensions: js.Dictionary[Any] = js.native
  var setContext: js.Function1[js.Dictionary[Any], js.Dictionary[Any]] = js.native
  var getContext: js.Function0[js.Dictionary[Any]] = js.native
}

@js.native
trait FetchResult[T, C, E] extends ExecutionResultBase[T, E] {
  val data: js.UndefOr[T | Null] = js.native
  val context: js.UndefOr[C] = js.native
}

/** scalajs variation that makes C and E dictionaries */
@js.native
trait StandardFetchResult[T] extends FetchResult[T, js.Dictionary[Any], js.Dictionary[Any]]
//   val data: js.UndefOr[T | Null] = js.native
//   val extensions: js.UndefOr[js.Dictionary[Any]] = js.native
//   val context: js.UndefOr[js.Dictionary[Any]] = js.native
// }
