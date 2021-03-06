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

package apollo_cache

import scala.scalajs.js

import js.|

import graphql._

trait Query[TVars <: js.Object] extends js.Object {
  val query: DocumentNode
  var variables: js.UndefOr[TVars] = js.undefined
}

trait Fragment[TVars <: js.Object] extends js.Object {
  val id: String
  val fragment: graphql.DocumentNode
  var fragmentName: js.UndefOr[String] = js.undefined
  var variables: js.UndefOr[TVars] = js.undefined
}

trait WriteFragmentOptions[T, TVars <: js.Object] extends Fragment[TVars] {
  val data: T
}

trait WriteDataOptions[T] extends js.Object {
  val data: T
  var id: js.UndefOr[String] = js.undefined
}

trait WriteQueryOptions[T, TVars <: js.Object] extends Query[TVars] {
  val data: T
}

@js.native
trait DataProxy extends js.Object {
  def readQuery[QueryType <: js.Object, TVars <: js.Object](
    options: Query[TVars],
    optimistic: js.UndefOr[Boolean] = js.undefined
  ): QueryType | Null
  def readFragment[TVars <: js.Object, FragmentType <: js.Object](
    options: Fragment[TVars],
    optimistic: js.UndefOr[Boolean] = js.undefined
  ): FragmentType | Null
  def writeQuery[T, TVars <: js.Object](options: WriteQueryOptions[T, TVars]): Unit
  def writeFragment[T, TVars <: js.Object](options: WriteFragmentOptions[T, TVars]): Unit
  def writeData[T](options: WriteDataOptions[T]): Unit
}
