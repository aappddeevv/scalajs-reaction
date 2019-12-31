// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package react
package native

import scala.scalajs.js
import js.annotation._

@js.native
trait EventSubscription extends js.Object {
  var eventType: String = js.native
  var key: Int = js.native
  var subscriber: js.Object = js.native
  def remove(): Unit = js.native
}

@js.native
trait EmitterSubscription extends EventSubscription {
  var emitter: EventsEmitter = js.native
  var listener: js.Function1[Unit, js.Any] = js.native
  var context: js.Any = js.native
}

@js.native
trait EventEmitterListener extends js.Object {
  def addListener(eventType: String, listiner: js.Any,
    context: js.UndefOr[js.Any] = js.undefined): EmitterSubscription
}

@js.native
trait EventsEmitter extends EventEmitterListener {
  def once(eventType: String, listener: js.Any, context: js.Any): EmitterSubscription = js.native
  def removeAllListeners(eventType: js.UndefOr[String] = js.undefined): Unit = js.native
  def removeCurrentListener(): Unit = js.native
  def removeSubscription(subscription: EmitterSubscription): Unit = js.native
  def listeners(eventType: String): js.Array[EmitterSubscription] = js.native
  def emit(eventType: String, args: js.Any*): Unit = js.native
  def removeListener(eventType: String, listener: js.Any): Unit = js.native
}

@js.native
trait NativeTouchEvent extends js.Object {
  var changedTouches: js.Array[NativeTouchEvent] = js.native
  var identfier: String = js.native
  var locationX: Int = js.native
  var locationY: Int = js.native
  var pageX: Int = js.native
  var pageY: Int = js.native
  var target: String = js.native
  var timestamp: Double = js.native
  var touches: js.Array[NativeTouchEvent] = js.native
}

@js.native
trait NativeSyntheticEvent[T <: js.Object]
    extends react.vdom.BaseSyntheticEvent[T, NodeHandle, NodeHandle]

@js.native
trait GestureResponderEvent extends NativeSyntheticEvent[NativeTouchEvent]
