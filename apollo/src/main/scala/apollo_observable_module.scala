// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package apollo_observable

import scala.scalajs.js
import js.|
import js.annotation._

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

trait Observer[T] extends js.Object{ 
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
    observerOrNext: js.Function1[T,Unit] | Observer[T],
    error: js.UndefOr[js.Function1[js.Any, Unit]] = js.undefined,
    complete: js.UndefOr[js.Function0[Unit]] = js.undefined): Subscription = js.native
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

