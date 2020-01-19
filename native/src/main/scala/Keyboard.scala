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
package native

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
sealed trait KeyboardEventName extends js.Any
object KeyboardEventName {
  val keyboardWillShow        = "keyboardWillShow".asInstanceOf[KeyboardEventName]
  val keyboardDidShow         = "keyboardDidShow".asInstanceOf[KeyboardEventName]
  val keyboardWillHide        = "keyboardWillHide".asInstanceOf[KeyboardEventName]
  val keyboardDidHide         = "keyboardDidHide".asInstanceOf[KeyboardEventName]
  val keyboardWillChangeFrame = "keyboardWillChangeFrame".asInstanceOf[KeyboardEventName]
  val keyboardDidChangeFrame  = "keyboardDidChangeFrame".asInstanceOf[KeyboardEventName]
}

@js.native
trait NativeEventListener extends js.Object {
  def remove(): Unit = js.native
}

@js.native
@JSImport("react-native", "Keyboard")
object Keyboard extends js.Object {
  def addListener(eventName: KeyboardEventName, cb: js.Function0[Unit]): NativeEventListener = js.native
  // use Listener.remove()
  def removeListener(eventName: KeyboardEventName, cb: js.Function0[Unit]): Unit = js.native
  def removeAllListeners(): Unit                                                 = js.native
  def dismiss(): Unit                                                            = js.native
}
