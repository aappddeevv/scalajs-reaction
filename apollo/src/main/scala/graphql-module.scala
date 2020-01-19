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

package graphql

import scala.scalajs.js

import js.annotation._
import js.|

@js.native
trait SourceLocation extends js.Object {
  val line: Int   = js.native
  val column: Int = js.native
}

@js.native
trait Source extends js.Object {
  val body: String                   = js.native
  val name: String                   = js.native
  val locationOffset: SourceLocation = js.native
}

@js.native
trait Location extends js.Object {
  val start: String = js.native
  val end: String   = js.native
  // startToken, endToken
  val source: Source = js.native
}

/** A big union in graphql/language/ast.ts */
@js.native
trait ASTNode extends js.Object

// AST element
@js.native
trait DocumentNode extends ASTNode {
  val kind: String       = js.native // always "Document"
  val location: Location = js.native
}

@js.native
trait GraphQLError extends js.Error {
  val locations: js.UndefOr[js.Array[SourceLocation]] = js.native
  val path: js.UndefOr[js.Array[String | Int]]        = js.native
  val nodes: js.UndefOr[js.Array[ASTNode]]            = js.native
  val source: js.UndefOr[Source]                      = js.undefined
  val positions: js.UndefOr[Int]                      = js.undefined
  val originalError: js.UndefOr[js.Error | Null]      = js.undefined
  val extensions: js.UndefOr[js.Dictionary[js.Any]]   = js.undefined
}

/** Return type T, Ext is not used alot so it remains member local. */
@js.native
trait ExecutionResult[T <: js.Any] extends js.Object {
  val data: js.UndefOr[T] = js.native
  // could also just have this be a js.Dictionary[js.Any]
  def extensions[Ext <: js.Object]: js.UndefOr[Ext] = js.native
  val errors: js.UndefOr[js.Array[GraphQLError]]    = js.native
}

object ExecutionResult {
  implicit final class RichExecutionResult[T <: js.Any] private[ExecutionResult] (private val er: ExecutionResult[T])
      extends AnyVal {
    // do we need to check the data as well, what if there is non returned?
    def successful = er.errors.isEmpty
  }
}

@js.native
@JSImport("graphql", JSImport.Namespace)
object module extends js.Object {
  def formatError(error: GraphQLError): FormattedError = js.native
  def printError(error: GraphQLError): String          = js.native
}

@js.native
trait FormattedError extends js.Object {
  val message: String                               = js.native
  val locations: js.UndefOr[SourceLocation]         = js.native
  val path: js.UndefOr[js.Array[String]]            = js.native
  val extensions: js.UndefOr[js.Dictionary[js.Any]] = js.native
}
