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

package react

import scala.scalajs.js

package object native {

  type Runnable          = js.Function1[js.Any, Unit]
  type Task              = js.Function1[js.Any, js.Promise[Unit]]
  type TaskProvider      = () => Task
  type ComponentProvider = () => react.ReactJsComponent
  type NodeHandle        = Int

  /** For use in Platform.select */
  def choices[T](items: (String, T)*): js.Dictionary[T] =
    js.Dictionary[T](items: _*)

  type AnimatedValue   = Value
  type AnimatedValueXY = ValueXY
  type ValueListenerCb = js.Function1[ValueListenerCbArgs, Unit]
  type EndCallback     = js.Function1[EndResult, Unit]
}
