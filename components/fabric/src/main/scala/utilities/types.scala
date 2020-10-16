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

package fabric
package utilities

import scala.scalajs.js
import js.|
import js.annotation._
import org.scalajs.dom

trait Size extends js.Object {
  val width: Int
  val height: Int
}

trait Rectangle extends js.Object {
  val left: Int
  val top: Int
  val width: Int
  val height: Int
  val right: js.UndefOr[Int]
  val bottom: js.UndefOr[Int]
}

trait Point extends js.Object {
  val x: Int
  val y: Int
}

@js.native
trait Cancelable[T] extends js.Object {
  def flush(): T = js.native
  def cancel(): Unit = js.native
  def pending(): Unit = js.native
}

@js.native
trait CancelableAndCallable[T] extends Cancelable[T] {
  def apply(): Unit = js.native
}

trait DebounceOptions extends js.Object {
  var leading: js.UndefOr[Boolean] = js.undefined
  var maxWait: js.UndefOr[Int] = js.undefined
  var trailing: js.UndefOr[Boolean] = js.undefined
}

@js.native
@JSImport("@uifabric/utilities", "Async")
class Async(
  parent: js.UndefOr[js.Object] = js.undefined,
  onError: js.UndefOr[js.Function1[js.Any, Unit]] = js.undefined)
    extends js.Object {
  def dispose(): Unit = js.native
  def setTimeout(callback: js.Function0[Unit], duration: Int): Int = js.native
  def clearTimeout(id: Int): Unit = js.native
  def setImmediate(
    callback: js.Function0[Unit],
    targetElement: js.UndefOr[dom.html.Element | Null] = js.undefined): Int = js.native
  def clearImmediate(id: Int, targetElement: js.UndefOr[dom.html.Element | Null] = js.undefined): Unit = js.native
  def setInterval(callback: js.Function0[Unit], duration: Int): Int = js.native
  def clearInterval(id: Int): Unit = js.native
  def throttle[T](
    f: js.Function0[Unit],
    wait: js.UndefOr[Int] = js.undefined,
    options: js.UndefOr[DebounceOptions] = js.undefined): T | js.Function0[Unit] = js.native
  def debounce[T](
    f: js.Function0[Unit],
    wait: js.UndefOr[Int] = js.undefined,
    options: js.UndefOr[DebounceOptions] = js.undefined): CancelableAndCallable[T] = js.native
  def requestAnimationFrame(
    callback: js.Function0[Unit],
    targetElement: js.UndefOr[dom.html.Element | Null] = js.undefined): Int = js.native
  def cancelAnimationFrame(id: Int, targetElement: js.UndefOr[dom.html.Element | Null] = js.undefined): Unit = js.native
}
