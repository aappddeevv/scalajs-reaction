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
import js.annotation._
import js.|

import graphql._

@js.native
abstract trait DataProxy extends js.Object {
  import DataProxy._
  def readQuery[QueryType, TVars](
    options: Query[TVars],
    optimistic: js.UndefOr[Boolean] = js.undefined): QueryType | Null
  def readFragment[FragmentType, TVars](
    options: Fragment[TVars],
    optimistic: js.UndefOr[Boolean] = js.undefined): FragmentType | Null
  def writeQuery[T, TVars](options: WriteQueryOptions[T, TVars]): Unit
  def writeFragment[T, TVars](options: WriteFragmentOptions[T, TVars]): Unit
}

object DataProxy {
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

  trait WriteQueryOptions[T, TVars] extends Query[TVars] {
    val data: T
    var broadcast: js.UndefOr[Boolean] = js.undefined
  }

  trait WriteFragmentOptions[T, TVars] extends Fragment[TVars] {
    val data: T
    var broadcast: js.UndefOr[Boolean] = js.undefined
  }

//@js.native
  trait DiffResult[T] extends js.Object {
    var result: js.UndefOr[T] = js.undefined
    var complete: js.UndefOr[Boolean] = js.undefined
    var missing: js.UndefOr[js.Array[MissingFieldError]] = js.undefined
  }

}
