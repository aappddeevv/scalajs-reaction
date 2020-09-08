package apollo
package client3
package cache_module
package types_module
package common_module

import scala.scalajs.js
import js.|
import js.annotation._

import graphql.DocumentNode
import scala.annotation.compileTimeOnly

@js.native
@JSImport()
class MissingFieldError(
    val message: String,
    val path: js.Array[String|Int],
    val query: DocumentNode,
    val variables: js.UndefOr[js.Dictionary[Any]]
) {
}