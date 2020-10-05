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

trait InMemoryCacheConfig extends js.Object {
  var resultCaching: js.UndefOr[Boolean] = js.undefined
  var possiblyTypes: js.UndefOr[PossibleTypesMap] = js.undefined
  var typePolicies: js.UndefOr[TypePolicies] = js.undefined
}

@js.native
@JSImport("@apollo/client/cache/inmemory/inMemoryCache", "InMemoryCache")
class InMemoryCache(config: js.UndefOr[InMemoryCacheConfig] = js.undefined) extends ApolloCache[NormalizedCacheObject] {

  def read[T, TVars](query: Cache.ReadOptions[TVars]): T | Null = js.native
  def write[TResult, TVars](write: Cache.WriteOptions[TResult, TVars]): Unit = js.native
  def diff[T](query: Cache.DiffOptions[T]): Cache.DiffResult[T] = js.native
  def watch[TVars](watch: Cache.WatchOptions[TVars]): js.Function0[Unit] = js.native
  def reset(): js.Promise[Unit] = js.native
  def evict(options: Cache.EvictOptions): Boolean = js.native
  def evict(
    id: String,
    field: js.UndefOr[String] = js.undefined,
    args: js.UndefOr[js.Object | js.Dynamic] = js.undefined): Boolean = js.native
  def restore(serializedState: NormalizedCacheObject): ApolloCache[NormalizedCacheObject] = js.native
  def extract(optimistic: js.UndefOr[Boolean]): NormalizedCacheObject = js.native
  def removeOptimistic(id: String): Unit = js.native
  def performTransaction(transaction: Transaction[NormalizedCacheObject]): Unit = js.native
  def recordOptimisticTransaction(transaction: Transaction[NormalizedCacheObject], id: String): Unit = js.native
  /*
    def restore(data: NormalizedCacheObject): this;
    def extract(optimistic?: boolean): NormalizedCacheObject;
    def read<T>(options: Cache.ReadOptions): T | null;
    def write(options: Cache.WriteOptions): void;
    diff<T>(options: Cache.DiffOptions): Cache.DiffResult<T>;
    watch(watch: Cache.WatchOptions): () => void;
    def gc(): js.Array[String] = js.native
    retain(rootId: string, optimistic?: boolean): number;
    release(rootId: string, optimistic?: boolean): number;
    identify(object: StoreObject): string | undefined;
    evict(idOrOptions: string | Cache.EvictOptions, fieldName?: string, args?: Record<string, any>): boolean;
    reset(): Promise<void>;
    removeOptimistic(idToRemove: string): void;
    performTransaction(transaction: (cache: InMemoryCache) => any, optimisticId?: string): void;
    recordOptimisticTransaction(transaction: Transaction<NormalizedCacheObject>, id: string): void;
    transformDocument(document: DocumentNode): DocumentNode;
    makeVar<T>(value: T): ReactiveVar<T>;
 */
}
