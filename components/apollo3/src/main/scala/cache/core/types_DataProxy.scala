package apollo
package client3
package cache_module
package types_module
package DataProxy_module

import scala.scalajs.js
import js.annotation._
import js.|

import graphql._

trait Query[TVars] extends js.Object {
    val query: DocumentNode
    var variables: js.UndefOr[DocumentNode] = js.undefined
    var id: js.UndefOr[String] = js.undefined
}

trait Fragment[TVars] extends js.Object {
    var id: js.UndefOr[String] = js.undefined
    val fragment: DocumentNode
    var fragmentName: js.UndefOr[String] = js.undefined
    var variables: js.UndefOr[TVars] = js.undefined
}

trait WriteQueryOptions[TD, TVars] extends Query[TVars] {
    val data: TD
    var broadcast: js.UndefOr[Boolean] = js.undefined
}

//@js.native
trait DiffResult[T] extends js.Object {
    var result: js.UndefOr[T] = js.undefined
    var complete: js.UndefOr[Boolean] = js.undefined
    var missing: js.UndefOr[js.Array[MissingFieldError]] = js.undefined
}


@js.native
@JSImport("@apollo/client/cache/core/types/DataProxy", "DataProxy")
abstract trait DataProxy extends js.Object {
    def readQuery[QueryType, TVars](options: Query[TVars], optimistic: js.UndefOr[Boolean] = js.undefinde): QueryType|Null = js.native
    def readFragment[FragmentType, TVar](options: Fragment[TVars], optimistic: js.UndefOr[Boolean] = js.undefinde): FragmentType|Null = js.native
    def writeQuery[TD, TVars](options: WriteQueryOptions[TD, TVars]): Unit = js.native
    def writeFragment[TD, TVars](options: WriteFragmentOptions[TD,TVars]): Unit = js.native
}