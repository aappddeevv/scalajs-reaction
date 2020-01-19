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

@js.native
trait EventSubscription extends js.Object {
  var eventType: String     = js.native
  var key: Int              = js.native
  var subscriber: js.Object = js.native
  def remove(): Unit        = js.native
}

@js.native
trait EmitterSubscription extends EventSubscription {
  var emitter: EventsEmitter               = js.native
  var listener: js.Function1[Unit, js.Any] = js.native
  var context: js.Any                      = js.native
}

@js.native
trait EventEmitterListener extends js.Object {
  def addListener(eventType: String, listiner: js.Any, context: js.UndefOr[js.Any] = js.undefined): EmitterSubscription
}

@js.native
trait EventsEmitter extends EventEmitterListener {
  def once(eventType: String, listener: js.Any, context: js.Any): EmitterSubscription = js.native
  def removeAllListeners(eventType: js.UndefOr[String] = js.undefined): Unit          = js.native
  def removeCurrentListener(): Unit                                                   = js.native
  def removeSubscription(subscription: EmitterSubscription): Unit                     = js.native
  def listeners(eventType: String): js.Array[EmitterSubscription]                     = js.native
  def emit(eventType: String, args: js.Any*): Unit                                    = js.native
  def removeListener(eventType: String, listener: js.Any): Unit                       = js.native
}

@js.native
trait NativeTouchEvent extends js.Object {
  var changedTouches: js.Array[NativeTouchEvent] = js.native
  var identfier: String                          = js.native
  var locationX: Int                             = js.native
  var locationY: Int                             = js.native
  var pageX: Int                                 = js.native
  var pageY: Int                                 = js.native
  var target: String                             = js.native
  var timestamp: Double                          = js.native
  var touches: js.Array[NativeTouchEvent]        = js.native
}

@js.native
trait NativeSyntheticEvent[T <: js.Object] extends react.vdom.BaseSyntheticEvent[T, NodeHandle, NodeHandle]

@js.native
trait GestureResponderEvent extends NativeSyntheticEvent[NativeTouchEvent]
