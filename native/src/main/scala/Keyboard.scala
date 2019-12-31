// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package react
package native

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
sealed trait KeyboardEventName extends js.Any
object KeyboardEventName {
  val keyboardWillShow = "keyboardWillShow".asInstanceOf[KeyboardEventName]
  val keyboardDidShow = "keyboardDidShow".asInstanceOf[KeyboardEventName]
  val keyboardWillHide = "keyboardWillHide".asInstanceOf[KeyboardEventName]
  val keyboardDidHide = "keyboardDidHide".asInstanceOf[KeyboardEventName]
  val keyboardWillChangeFrame = "keyboardWillChangeFrame".asInstanceOf[KeyboardEventName]
  val keyboardDidChangeFrame = "keyboardDidChangeFrame".asInstanceOf[KeyboardEventName]
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
  def removeAllListeners(): Unit = js.native
  def dismiss(): Unit = js.native
}
