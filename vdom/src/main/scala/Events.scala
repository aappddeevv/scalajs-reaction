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
package vdom

// needed because I could not figure out the proper variance model
import org.scalajs.dom
import scalajs.js

/*
 We are kind-of using the implementation of events from
 http://github.com/japgolly/scalajs-react, many thanks.
 */

trait Events {

  /** Friendly name alias for SyntheticEvent */
  type ReactEvent[+T <: dom.EventTarget]          = SyntheticEvent[T]
  type ReactKeyboardEvent[+T <: dom.EventTarget]  = SyntheticKeyboardEvent[T]
  type ReactMouseEvent[+T <: dom.EventTarget]     = SyntheticMouseEvent[T]
  type ReactFocusEvent[+T <: dom.EventTarget]     = SyntheticFocusEvent[T]
  type ReactTouchEvent[+T <: dom.EventTarget]     = SyntheticTouchEvent[T]
  type ReactChangeEvent[+T <: dom.EventTarget]    = SyntheticChangeEvent[T]
  type ReactPointerEvent[+T <: dom.EventTarget]   = SyntheticPointerEvent[T]
  type ReactWheelEvent[+T <: dom.EventTarget]     = SyntheticWheelEvent[T]
  type ReactFormEvent[+T <: dom.EventTarget]      = SyntheticFormEvent[T]
  type ReactClipboardEvent[+T <: dom.EventTarget] = SyntheticClipboardEvent[T]
  // ...a few more...

  type ReactEventI         = SyntheticEvent[dom.html.Input]
  type ReactKeyboardEventI = SyntheticKeyboardEvent[dom.html.Input]
  type ReactMouseEventI    = SyntheticMouseEvent[dom.html.Input]

  /**
   * @tparam T node type typically dom.EventTarget but should be the
   *  EventTarget type e.g. HTMLButtonElement (from scalajs.dom.raw) or
   *  scalajs.dom.html.Button (which points to the raw type but is ergonomic).
   *  A popular generic choice for `T` is scalajs.dom.html.Element. `T`
   *  represents the type of the element that was the target of the event
   *  e.g. a click on a button.
   *
   * @tparam E Synthetic event subclass
   */
  type EventHandler[T <: dom.EventTarget, E <: SyntheticEvent[T]] = js.Function1[E, Unit]

  /** General event handler parameterized on the receiver. */
  type ReactEventHandler[T <: dom.EventTarget]       = EventHandler[T, SyntheticEvent[T]]
  type MouseEventHandler[T <: dom.EventTarget]       = EventHandler[T, SyntheticMouseEvent[T]]
  type KeyboardEventHandler[T <: dom.EventTarget]    = EventHandler[T, SyntheticKeyboardEvent[T]]
  type UIEventHandler[T <: dom.EventTarget]          = EventHandler[T, SyntheticUIEvent[T]]
  type FocusEventHandler[T <: dom.EventTarget]       = EventHandler[T, SyntheticFocusEvent[T]]
  type FormEventHandler[T <: dom.EventTarget]        = EventHandler[T, SyntheticFormEvent[T]]
  type ChangeEventHandler[T <: dom.EventTarget]      = EventHandler[T, SyntheticChangeEvent[T]]
  type ClipboardEventHandler[T <: dom.EventTarget]   = EventHandler[T, SyntheticClipboardEvent[T]]
  type DragEventHandler[T <: dom.EventTarget]        = EventHandler[T, SyntheticDragEvent[T]]
  type PointerEventHandler[T <: dom.EventTarget]     = EventHandler[T, SyntheticPointerEvent[T]]
  type TouchEventHandler[T <: dom.EventTarget]       = EventHandler[T, SyntheticTouchEvent[T]]
  type CompositionEventHandler[T <: dom.EventTarget] = EventHandler[T, SyntheticCompositionEvent[T]]
  type WheelEventHandler[T <: dom.EventTarget]       = EventHandler[T, SyntheticWheelEvent[T]]
}

@js.native
trait BaseSyntheticEvent[E, +C, +T] extends js.Object {
  val nativeEvent: E   = js.native
  val currentTarget: C = js.native
  val target: T        = js.native

  val bubbles: Boolean                = js.native
  val cancelable: Boolean             = js.native
  val defaultPrevented: Boolean       = js.native
  val eventPhase: Double              = js.native
  val isTrusted: Boolean              = js.native
  val timeStamp: js.Date              = js.native
  def preventDefault(): Unit          = js.native
  def stopPropagation(): Unit         = js.native
  def isPropagationStopped(): Boolean = js.native
  val `type`: String                  = js.native

  /** Persist the event to process asynchronously */
  def persist(): Unit = js.native
}

@js.native
trait SyntheticEvent[+DOMEventTarget <: dom.EventTarget]
    extends BaseSyntheticEvent[dom.Event, DOMEventTarget, DOMEventTarget] {
  //val nativeEvent: dom.Event          = js.native
  //val currentTarget: DOMEventTarget   = js.native
  //val target: DOMEventTarget          = js.native
}

@js.native
trait SyntheticUIEvent[+DOMEventTarget <: dom.EventTarget] extends SyntheticEvent[DOMEventTarget] {
  override val nativeEvent: dom.UIEvent = js.native
  val view: js.Object                   = js.native
  def detail: Double                    = js.native
}

@js.native
trait SyntheticKeyboardEvent[+DOMEventTarget <: dom.EventTarget] extends SyntheticUIEvent[DOMEventTarget] {
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
trait SyntheticMouseEvent[+DOMEventTarget <: dom.EventTarget] extends SyntheticUIEvent[DOMEventTarget] {
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
trait SyntheticPointerEvent[+T <: dom.EventTarget] extends SyntheticMouseEvent[T] {
  val pointerId: Int                         = js.native
  val pressure: Int                          = js.native
  val tiltX: Int                             = js.native
  val tiltY: Int                             = js.native
  val width: Int                             = js.native
  val height: Int                            = js.native
  val pointerType: String                    = js.native // mouse|pen|touch
  val isPrimary: Boolean                     = js.native
  override val nativeEvent: dom.PointerEvent = js.native
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
