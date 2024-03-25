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

object Cache {

  type WatchCallback = js.Function1[DiffResult[Any], Unit]

  trait ReadOptions[TVars] extends DataProxy.Query[TVars] {
    var rootId: js.UndefOr[String] = js.undefined
    var previousResult: js.UndefOr[Any] = js.undefined
    var optimistic: js.UndefOr[Boolean] = js.undefined
  }

  trait WriteOptions[TResult, TVars] extends DataProxy.Query[TVars] {
    var dataId: js.UndefOr[String] = js.undefined
    val result: TResult
    var broadcast: js.UndefOr[Boolean] = js.undefined
  }

  trait DiffOptions[TVars] extends ReadOptions[TVars] {
    var returnPartialData: js.UndefOr[Boolean] = js.undefined
  }

  trait WatchOptions[TVars] extends ReadOptions[TVars] {
    var immediate: js.UndefOr[Boolean] = js.undefined
    var callback: WatchCallback
  }

  trait EvictOptions {
    var id: js.UndefOr[String] = js.undefined
    var fieldName: js.UndefOr[String] = js.undefined
    var args: js.UndefOr[js.Object | js.Dynamic] = js.undefined
    var broadcast: js.UndefOr[Boolean] = js.undefined
  }

  trait ModifyOptions {
    var id: js.UndefOr[String] = js.undefined
    val fields: Modifiers | Modifier[?]
    var optimistic: js.UndefOr[js.Object | js.Dynamic] = js.undefined
    var broadcast: js.UndefOr[Boolean] = js.undefined
  }

  type DiffResult[T] = DataProxy.DiffResult[T]
  type WriteQueryOptions[T, TVars] = DataProxy.WriteQueryOptions[T, TVars]
  type WriteFragmentOptions[T, TVars] = DataProxy.WriteFragmentOptions[T, TVars]
  type Fragment[TVars] = DataProxy.Fragment[TVars]

}
