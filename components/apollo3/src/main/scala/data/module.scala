package apollo
package client3
package data

import scala.scalajs.js
import js.annotation._
import js.!

import graphql.DocumentNode

@js.native
@JSImport("@apollo/client/data", "MutationStore")
class MutationStore() extends js.Object {
    def getStore(): js.Dictionary[MutationStoreValue] = js.native
    def get(id: String): MutationStoreValue = js.native
    def initMutation(id: String, mutation: DocumentNode, variables: js.Object|undefined): Unit = js.native
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