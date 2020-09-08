package apollo
package client3
package cache_module
package core_module
package cache_module

import scala.scalajs.js
import js.|
import js.annotation._
import graphql._

@js.native
@JSImport("@apollo/client/cache/core/cache", "ApolloCache")
class ApolloCache[T]() extends types_module.DataProxy_module.DataProxy {
    /*
     abstract read<T, TVariables = any>(query: Cache.ReadOptions<TVariables>): T | null;
    abstract write<TResult = any, TVariables = any>(write: Cache.WriteOptions<TResult, TVariables>): void;
    abstract diff<T>(query: Cache.DiffOptions): Cache.DiffResult<T>;
    abstract watch(watch: Cache.WatchOptions): () => void;
    abstract reset(): Promise<void>;
    abstract evict(options: Cache.EvictOptions): boolean;
    abstract evict(id: string, field?: string, args?: Record<string, any>): boolean;
    abstract restore(serializedState: TSerialized): ApolloCache<TSerialized>;
    abstract extract(optimistic?: boolean): TSerialized;
    abstract removeOptimistic(id: string): void;
    abstract performTransaction(transaction: Transaction<TSerialized>): void;
    abstract recordOptimisticTransaction(transaction: Transaction<TSerialized>, id: string): void;
    transformDocument(document: DocumentNode): DocumentNode;
    identify(object: StoreObject): string | undefined;
    gc(): string[];
    transformForLink(document: DocumentNode): DocumentNode;
    readQuery<QueryType, TVariables = any>(options: DataProxy.Query<TVariables>, optimistic?: boolean): QueryType | null;
    private getFragmentDoc;
    readFragment<FragmentType, TVariables = any>(options: DataProxy.Fragment<TVariables>, optimistic?: boolean): FragmentType | null;
    writeQuery<TData = any, TVariables = any>(options: Cache.WriteQueryOptions<TData, TVariables>): void;
    writeFragment<TData = any, TVariables = any>(options: Cache.WriteFragmentOptions<TData, TVariables>): void;
    */
}
