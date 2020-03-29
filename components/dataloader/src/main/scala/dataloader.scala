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

package dataloader

import scala.scalajs.js
import js.|
import js.annotation._

/** Options */
trait Options extends js.Object {
  var bactchScheduleFn: js.UndefOr[js.Function1[js.Function0[Unit], Unit]] = js.undefined
  var cache: js.UndefOr[Boolean] = js.undefined
  var cacheSize: js.UndefOr[Int] = js.undefined

  /** Must have same api as javascript Map: set, get, clear, delete, keys, values, */
  var cacheMap: js.UndefOr[js.Object | Null] = js.undefined
  val cacheKeyFn: js.UndefOr[js.Function1[js.Any, js.Any]] = js.undefined
}

/** Basic cache. */
@js.native
@JSImport("dataloader", JSImport.Default)
class DataLoader[K, T](cb: js.Array[K] => js.Promise[T | Null], options: js.UndefOr[Options] = js.undefined)
    extends js.Object {
  def load(id: K): js.Promise[T] = js.native
  def loadMany(ids: js.Array[K]): js.Promise[js.Array[T | js.Error]] = js.native
  def clear(id: K): DataLoader[K, T] = js.native
  def clearAll(): DataLoader[K, T] = js.native
  def prime(k: K, v: T): DataLoader[K, T] = js.native
}
