package apollo
package client3
package cache
package inmemory

import scala.scalajs.js
import js.annotation._
import js.|

//export { InMemoryCache, InMemoryCacheConfig, ReactiveVar, } from './inmemory/inMemoryCache';
//export { defaultDataIdFromObject, TypePolicies, TypePolicy, FieldPolicy, FieldReadFunction, FieldMergeFunction, FieldFunctionOptions, PossibleTypesMap, } from './inmemory/policies';
export * from './inmemory/types';


@js.native
@JSImport("@apollo/client/core/inmemory", "InMemoryCache")
class InMemoryCache(config: js.UndefOr[InMemoryCacheConfig]=js.undefined) 
    extends ApolloCache<NormalizedCacheObject> {
    /*
        constructor(config?: InMemoryCacheConfig);
    restore(data: NormalizedCacheObject): this;
    extract(optimistic?: boolean): NormalizedCacheObject;
    read<T>(options: Cache.ReadOptions): T | null;
    write(options: Cache.WriteOptions): void;
    diff<T>(options: Cache.DiffOptions): Cache.DiffResult<T>;
    watch(watch: Cache.WatchOptions): () => void;
    gc(): string[];
    retain(rootId: string, optimistic?: boolean): number;
    release(rootId: string, optimistic?: boolean): number;
    identify(object: StoreObject): string | undefined;
    evict(idOrOptions: string | Cache.EvictOptions, fieldName?: string, args?: Record<string, any>): boolean;
    reset(): Promise<void>;
    removeOptimistic(idToRemove: string): void;
    private txCount;
    performTransaction(transaction: (cache: InMemoryCache) => any, optimisticId?: string): void;
    recordOptimisticTransaction(transaction: Transaction<NormalizedCacheObject>, id: string): void;
    transformDocument(document: DocumentNode): DocumentNode;
    protected broadcastWatches(): void;
    private maybeBroadcastWatch;
    private varDep;
    makeVar<T>(value: T): ReactiveVar<T>;
    */
}

trait InMemoryCacheConfig extends ApolloReducerConfig {
    var resultCaching: js.UndefOr[Boolean] = js.undefined
    var possibleTypes: js.UndefOr[PossibleTypesMap] = js.undefined
    var typePolicies: js.UndefOr[TypePolicies] = js.undefined
}

trait NormalizedCacheObject extends js.Object {
    def has(id: String): Boolean
    // def get(id: String, fieldName: String): StoreValue
    // def merge(id: STring, incoming: StorObject): StoreObject
    def clear(): Unit
    /**
     * toObject
     * replace
     * retain
     * release
     * getFieldValue
     * toReferenc
     */
}