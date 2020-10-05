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
package cache

import scala.scalajs.js
import js.|
import js.annotation._
import graphql._
import client3.utilities._
import rxjs._

@js.native
@JSImport("@apollo/client/cache/core/cache", "ApolloCache")
abstract class ApolloCache[TSerialized]() extends DataProxy {
  // abstract members
  def read[T, TVars](query: Cache.ReadOptions[TVars]): T | Null
  def write[TResult, TVars](write: Cache.WriteOptions[TResult, TVars]): Unit
  def diff[T](query: Cache.DiffOptions[T]): Cache.DiffResult[T]
  def watch[TVars](watch: Cache.WatchOptions[TVars]): js.Function0[Unit]
  def reset(): js.Promise[Unit]
  def evict(options: Cache.EvictOptions): Boolean
  def evict(
    id: String,
    field: js.UndefOr[String] = js.undefined,
    args: js.UndefOr[js.Object | js.Dynamic] = js.undefined): Boolean
  def restore(serializedState: TSerialized): ApolloCache[TSerialized]
  def extract(optimistic: js.UndefOr[Boolean]): TSerialized
  def removeOptimistic(id: String): Unit
  def performTransaction(transaction: Transaction[TSerialized]): Unit
  def recordOptimisticTransaction(transaction: Transaction[TSerialized], id: String): Unit

  def transformDocument(document: DocumentNode): DocumentNode = js.native
  def identify(obj: StoreObject): js.UndefOr[String] = js.native
  def gc(): js.Array[String] = js.native
  def transformForLink(document: DocumentNode): DocumentNode = js.native
  def readQuery[QueryType, TVars](
    options: DataProxy.Query[TVars],
    optimistic: js.UndefOr[Boolean] = js.undefined): QueryType | Null = js.native
  def readFragment[FragmentType, TVars](
    options: DataProxy.Fragment[TVars],
    optimistic: js.UndefOr[Boolean] = js.undefined): FragmentType | Null = js.native
  def writeQuery[T, TVars](options: Cache.WriteQueryOptions[T, TVars]): Unit = js.native
  def writeFragment[T, TVars](options: Cache.WriteFragmentOptions[T, TVars]): Unit = js.native
}
