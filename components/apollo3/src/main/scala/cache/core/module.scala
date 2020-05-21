package apollo
package client3
package inmemory
package core

import scala.scalajs.js
import js.annotation._
import js.|

import graphql.DocumentNode

@js.native
@JSImport("@apollo/client/cache/core", "ApolloCache")
class ApolloCache[T]() extends DataProxy {
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

trait Query[TVars] extends js.Object {
    val query: DocumentNode
    var variables: js.UndefOr[TVars] = js.undefined
    var id: js.UndefOr[String] = js.undefined
}

trait Fragment[TVars] extends js.Object {
    val id: String
    val fragment: DocumentNode
    var fragmentName: js.UndefOr[String] = js.undefined
    var variables: js.UndefOr[TVars] = js.undefined
}

trait WriteQueryOptions[T, TVars] extends Query[TVars] { 
    val data: T
    var broadcast: js.UndefOr[Boolean] = js.undefined
}

trait WriteFragmentOptions[T,TVars] extends Fragment[TVars] { 
    val data: T
    var broadcast: js.UndefOr[Boolean] = js.undefined
}

@js.native
trait DataProxy extends js.Object {
    def readQuery[QueryType, TVars](options: Query[TVars], optimistic: js.UndefOr[Boolean] = js.undefined): QueryType|Null = js.native
    def readFragment[FragmentType, TVars](options: Fragment[TVars], optimistic: js.UndefOr[Boolean] = js.undefined): FragmentType|Null = js.native
    def writeQuery[T, TVars](options: WriteQueryOptions[T, TVars]): Unit = js.native
    def writeFragment[T, TVars](options: WriteFragmentOptions[T,TVars]): Unit = js.native

}