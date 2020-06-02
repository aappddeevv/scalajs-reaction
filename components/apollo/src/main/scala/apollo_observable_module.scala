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

package apollo_observable

import scala.scalajs.js

import js.annotation._
import js.|

trait Subscription extends js.Object {
  val closed: Boolean
  def unsubscribe(): Unit
}

trait SubscriptionObserver[T] extends js.Object {
  val closed: Boolean
  def next(value: T): Unit
  def error(errorValue: js.Any): Unit
  def complete(): Unit
}

trait Observer[T] extends js.Object {
  var start: js.UndefOr[js.Function1[Subscription, js.Any]] = js.undefined
  var next: js.UndefOr[js.Function1[T, Unit]] = js.undefined
  var error: js.UndefOr[js.Function1[js.Any, Unit]] = js.undefined
  var complete: js.UndefOr[js.Function0[Unit]] = js.undefined
}

@js.native
trait ObservableLike[T] extends js.Object {
  val subscribe: js.UndefOr[Subscriber[T]] = js.native
}

@js.native
trait ObservableBase[T] extends js.Object {
  def subscribe(
    observerOrNext: js.Function1[T, Unit] | Observer[T],
    error: js.UndefOr[js.Function1[js.Any, Unit]] = js.undefined,
    complete: js.UndefOr[js.Function0[Unit]] = js.undefined
  ): Subscription = js.native
  def forEach(f: T => Unit): js.Promise[Unit] = js.native
  def map[B](f: T => B): Observable[B] = js.native
  def filter(f: T => Boolean): Observable[T] = js.native
  //reduce
  //flatMap
  def from[R](observable: Observable[R] | js.Array[R]): Observable[R] = js.native
  def of[R](args: R*): Observable[R] = js.native
}

@js.native
@JSImport("zen-observable-ts", "Observable")
class Observable[T](subscriber: Subscriber[T]) extends ObservableBase[T]

@js.native
@JSImport("zen-observable-ts", "Observable")
object Observable extends js.Object {
  def from[R](observable: Observable[R] | js.Array[R]): Observable[R] = js.native
  def of[R](args: R*): Observable[R] = js.native
}
