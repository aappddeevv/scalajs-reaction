// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package vdom

import scala.annotation.unchecked.{uncheckedVariance => uv}
import scalajs.js
import js.|
import org.scalajs.dom
import dom.html

/*
 We using the implementation of events from http://github.com/japgolly/scalajs-react,
 many thanks to him and japgolly.
 */

trait Events {

  /** Friendly name alias for SyntheticEvent */
  type ReactEvent[+T <: dom.EventTarget]         = SyntheticEvent[T]
  type ReactKeyboardEvent[+T <: dom.EventTarget] = SyntheticKeyboardEvent[T]
  type ReactMouseEvent[+T <: dom.EventTarget]    = SyntheticMouseEvent[T]
  // add the rest of these....someday when I get time

  type ReactEventI         = SyntheticEvent[html.Input]
  type ReactKeyboardEventI = SyntheticKeyboardEvent[html.Input]
  type ReactMouseEventI    = SyntheticMouseEvent[html.Input]

  /**
    * @tparam T node type typically dom.EventTarget but should be the EventTarget type
    *  e.g. HTMLButtonElement from scalajs.dom.raw or scalajs.dom.html.Button.
    * A popular general choice is scalajs.dom.html.Element. T represents the type
    * of the element that was the target of the event e.g. a click on a button.
    *
    * @tparam E Synthetic event subclass
    */
  type EventHandler[T <: dom.EventTarget, E <: SyntheticEvent[T]] = js.Function1[E, Unit]

  /** General event handler. */
  type ReactEventHandler[T <: dom.EventTarget]    = EventHandler[T, SyntheticEvent[T]]
  type MouseEventHandler[T <: dom.EventTarget]    = EventHandler[T, SyntheticMouseEvent[T]]
  type KeyboardEventHandler[T <: dom.EventTarget] = EventHandler[T, SyntheticKeyboardEvent[T]]
  type UIEventHandler[T <: dom.EventTarget]       = EventHandler[T, SyntheticUIEvent[T]]
  type FocusEventHandler[T <: dom.EventTarget]    = EventHandler[T, SyntheticFocusEvent[T]]
  type FormEventHandler[T <: dom.EventTarget]     = EventHandler[T, SyntheticFormEvent[T]]
  type ChangeEventHandler[T <: dom.EventTarget]   = EventHandler[T, SyntheticChangeEvent[T]]
}

@js.native
trait SyntheticEvent[+DOMEventTarget <: dom.EventTarget] extends js.Object {
  val bubbles: Boolean                = js.native
  val cancelable: Boolean             = js.native
  val currentTarget: DOMEventTarget   = js.native
  def defaultPrevented: Boolean       = js.native
  val eventPhase: Double              = js.native
  val isTrusted: Boolean              = js.native
  val nativeEvent: dom.Event          = js.native
  val target: DOMEventTarget          = js.native
  val timeStamp: js.Date              = js.native
  def preventDefault(): Unit          = js.native
  def stopPropagation(): Unit         = js.native
  def isPropagationStopped(): Boolean = js.native
  val `type`: String                  = js.native
  def persist(): Unit                 = js.native
}

@js.native
trait SyntheticUIEvent[+DOMEventTarget <: dom.EventTarget] extends SyntheticEvent[DOMEventTarget] {
  override val nativeEvent: dom.UIEvent = js.native
  val view: js.Object                   = js.native
  def detail: Double                    = js.native
}

@js.native
trait SyntheticKeyboardEvent[+DOMEventTarget <: dom.EventTarget]
    extends SyntheticUIEvent[DOMEventTarget] {
  override val nativeEvent: dom.KeyboardEvent   = js.native
  val location: Double                          = js.native
  val altKey: Boolean                           = js.native
  val ctrlKey: Boolean                          = js.native
  val metaKey: Boolean                          = js.native
  val shiftKey: Boolean                         = js.native
  val repeat: Boolean                           = js.native
  val locale: String                            = js.native
  def getModifierState(keyArg: String): Boolean = js.native
  val key: String                               = js.native
  val charCode: Int                             = js.native
  val keyCode: Int                              = js.native
  val which: Int                                = js.native
}

@js.native
trait SyntheticMouseEvent[+DOMEventTarget <: dom.EventTarget]
    extends SyntheticUIEvent[DOMEventTarget] {
  override val nativeEvent: dom.MouseEvent      = js.native
  val ctrlKey: Boolean                          = js.native
  val shiftKey: Boolean                         = js.native
  val altKey: Boolean                           = js.native
  val metaKey: Boolean                          = js.native
  val button: Short                             = js.native
  val buttons: Short                            = js.native
  val clientX: Double                           = js.native
  val clientY: Double                           = js.native
  val pageX: Double                             = js.native
  val pageY: Double                             = js.native
  val screenX: Double                           = js.native
  val screenY: Double                           = js.native
  val relatedTarget: DOMEventTarget             = js.native
  def getModifierState(keyArg: String): Boolean = js.native
}

@js.native
trait SyntheticClipboardEvent[+T <: dom.EventTarget] extends SyntheticEvent[T] {
  val clipboardData: dom.DataTransfer          = js.native
  override val nativeEvent: dom.ClipboardEvent = js.native
}

@js.native
trait SyntheticCompositionEvent[+T <: dom.EventTarget] extends SyntheticEvent[T] {
  val data: String                               = js.native
  override val nativeEvent: dom.CompositionEvent = js.native
}

@js.native
trait SyntheticDragEvent[+T <: dom.EventTarget] extends SyntheticMouseEvent[T] {
  val dataTransfer: dom.DataTransfer      = js.native
  override val nativeEvent: dom.DragEvent = js.native
}

@js.native
trait SyntheticFocusEvent[+T <: dom.EventTarget] extends SyntheticEvent[T] {
  override val nativeEvent: dom.FocusEvent = js.native
  val relatedTarget: dom.EventTarget       = js.native
}

@js.native
trait SyntheticFormEvent[+T <: dom.EventTarget] extends SyntheticEvent[T] {}

@js.native
trait SyntheticInvalidEvent[+T <: dom.EventTarget] extends SyntheticEvent[T] {
  override val target: T = js.native
}

@js.native
trait SyntheticChangeEvent[+T <: dom.EventTarget] extends SyntheticEvent[T] {
  override val target: T = js.native
}

@js.native
trait SyntheticTouchEvent[+T <: dom.EventTarget] extends SyntheticEvent[T] {
  val altKey: Boolean               = js.native
  val changedTouches: dom.TouchList = js.native
  val ctrlKey: Boolean              = js.native

  /**
    * See [DOM Level 3 Events spec](https://www.w3.org/TR/uievents-key/#keys-modifier). for a list of valid (case-sensitive) arguments to this method.
    */
  def getModifierState(key: String): Boolean = js.native
  val metaKey: Boolean                       = js.native
  override val nativeEvent: dom.TouchEvent   = js.native
  val shiftKey: Boolean                      = js.native
  val targetTouches: dom.TouchList           = js.native
  val touches: dom.TouchList                 = js.native
}

@js.native
trait SyntheticWheelEvent[+T <: dom.EventTarget] extends SyntheticMouseEvent[T] {
  val deltaMode: Double                    = js.native
  val deltaX: Double                       = js.native
  val deltaY: Double                       = js.native
  val deltaZ: Double                       = js.native
  override val nativeEvent: dom.WheelEvent = js.native
}

@js.native
trait SyntheticAnimationEvent[+T <: dom.EventTarget] extends SyntheticEvent[T] {
  val animationName: String                    = js.native
  val elapsedTime: Double                      = js.native
  override val nativeEvent: dom.AnimationEvent = js.native
  val pseudoElement: String                    = js.native
}

@js.native
trait SyntheticTransitionEvent[+T <: dom.EventTarget] extends SyntheticEvent[T] {
  val elapsedTime: Double                       = js.native
  override val nativeEvent: dom.TransitionEvent = js.native
  val propertyName: String                      = js.native
  val pseudoElement: String                     = js.native
}
