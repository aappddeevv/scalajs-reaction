// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package react

import scala.scalajs.js
import js.|

package object native {

  type Runnable = js.Function1[js.Any, Unit]
  type Task = js.Function1[js.Any, js.Promise[Unit]]
  type TaskProvider = () => Task
  type ComponentProvider = () => react.ReactJsComponent
  type NodeHandle = Int

  /** For use in Platform.select */
  def choices[T](items: (String, T)*): js.Dictionary[T] =
    js.Dictionary[T](items:_*)

  type AnimatedValue = Value
  type AnimatedValueXY = ValueXY
  type ValueListenerCb = js.Function1[ValueListenerCbArgs, Unit]
  type EndCallback = js.Function1[EndResult, Unit]
}
