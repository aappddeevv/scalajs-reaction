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
package data

import scala.scalajs.js
import js.annotation._
import js.|

import graphql.DocumentNode

@js.native
@JSImport("@apollo/client/data", "MutationStore")
class MutationStore() extends js.Object {
  def getStore(): js.Dictionary[MutationStoreValue] = js.native
  def get(id: String): MutationStoreValue = js.native
  def initMutation(
    id: String,
    mutation: DocumentNode,
    variables: js.UndefOr[js.Object | js.Dynamic] = js.undefined): Unit = js.native
  def markMutationError(id: String, error: js.Error): Unit = js.native
  def markMutationResult(id: String): Unit = js.native
  def reset(): Unit = js.native
}

trait MutationStoreValue extends js.Object {
  val mutation: DocumentNode
  val variables: js.Object
  val loading: Boolean
  val error: js.Error | Null
}
