// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react

import scala.scalajs.js
import js.|

package object redux {

  /** Keys should be properties, values, js.Anys. Extend your Action trait from
   * this trait to ensure it has a `type` property.
   */
  trait Action extends js.Object {
    val `type`: String
  }

  /** A listener in redux is very coarse grained. You receive all events. */
  type Listener     = js.Function0[Unit]
  type Unsubscriber = js.Function0[Unit]
  type Dispatch[A <: Action]   = js.Function1[A | js.Dynamic, Unit]

  /** Helper to make type a dispatch function into js. */
  def dispatch[A <: Action](f: A|js.Dynamic => Unit): Dispatch[A] = f
}
