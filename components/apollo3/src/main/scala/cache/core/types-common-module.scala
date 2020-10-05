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

import graphql.DocumentNode
import client3.utilities._

@js.native
@JSImport("@apollo/client/core/types/common", "MissingFieldError")
class MissingFieldError(
  val message: String,
  val path: js.Array[String | Int],
  val query: DocumentNode,
  val variables: js.UndefOr[js.Dictionary[Any]]
) extends js.Object {}

trait FieldSpecifier extends js.Object {
  var typename: js.UndefOr[String] = js.undefined
  val fieldName: String
  var field: js.UndefOr[Any] = js.undefined
  var args: js.UndefOr[js.Object | js.Dynamic] = js.undefined
  var variables: js.UndefOr[js.Object | js.Dynamic] = js.undefined
}

trait ReadFieldOptions extends FieldSpecifier {
  var from: js.UndefOr[StoreObject | Reference] = js.undefined
}

@js.native
trait ReadFieldFunction extends js.Object {
  def apply[V <: StoreValue](options: ReadFieldOptions): Any = js.native
  //def apply[V <: StoreValue](fieldName: String, from: js.UndefOr[StoreObject|Reference] = js.undefined): Any = js.native
}

@js.native
trait Modifier[T] extends js.Object {
  val fieldName: String
  val storeFieldName: String
  val readField: ReadFieldFunction
  val canRead: CanReadFunction
  //val isReference
  //val toReference: ToReferenceFunction
}
