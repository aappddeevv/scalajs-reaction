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

package fluentui
package react_hooks

import scala.scalajs.js
import js.annotation._

@js.native
trait BooleanCallbacks extends js.Object {
  def setTrue(): Unit = js.native
  def setFalse(): Unit = js.native
  def toggle(): Unit = js.native
}

@js.native @JSImport("@uifabric/react-hooks", "useBoolean")
def useBoolean(initialValue: Boolean): js.Tuple2[Boolean, BooleanCallbacks] = js.native

@js.native @JSImport("@uifabric/react-hooks", "useConst")
def useConstStrict[T](initialValue: T): T = js.native

@js.native @JSImport("@uifabric/react-hooks", "useConst")
def useConst[T](initialValue: js.Function0[T]): T = js.native

// @js.native @JSImport("@uifabric/react-hooks", "useConstCallback")
// def useConstCallback[T](cb: js.Function0[T]) = js.native

// @js.native @JSImport("@uifabric/react-hooks", "useConstCallback")
// def useConstCallback[A1, T](cb: js.Function1[A1, T]) = js.native

// @js.native @JSImport("@uifabric/react-hooks", "useConstCallback")
// def useConstCallback[A1, A2, T](cb: js.Function2[A1, A2, T]) = js.native

// @js.native @JSImport("@uifabric/react-hooks", "useConstCallback")
// def useConstCallback[A1, A2, A3, T](cb: js.Function3[A1, A2, A3, T]) = js.native

// @js.native @JSImport("@uifabric/react-hooks", "useConstCallback")
// def useConstCallback[A1, A2, A3, A4, T](cb: js.Function4[A1, A2, A3, A4, T]) = js.native

@js.native @JSImport("@uifabric/react-hooks", "useId")
def useId(prefix: js.UndefOr[String] = js.undefined, providedId: js.UndefOr[String] = js.undefined): String = js.native

@js.native @JSImport("@uifabric/react-hooks", "usePrevious")
def usePrevious[A](prefix: js.UndefOr[A] = js.undefined): js.UndefOr[A] = js.native

@js.native @JSImport("@uifabric/react-hooks", "useForceUpdate")
def useForceUpdate(): js.Function0[Unit] = js.native

// ....more...

