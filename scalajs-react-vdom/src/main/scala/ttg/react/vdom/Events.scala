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
  type ReactEvent[+T <: dom.EventTarget] = SyntheticEvent[T]
  type ReactKeyboardEvent[+T <: dom.EventTarget] = SyntheticKeyboardEvent[T]
  type ReactMouseEvent[+T <: dom.EventTarget] = SyntheticMouseEvent[T]
  // add the rest of these....someday when I get time

  type ReactEventI = SyntheticEvent[html.Input]
  type ReactKeyboardEventI = SyntheticKeyboardEvent[html.Input]
  type ReactMouseEventI = SyntheticMouseEvent[html.Input]

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
  type ReactEventHandler[T <: dom.EventTarget] = EventHandler[T, SyntheticEvent[T]]
  type MouseEventHandler[T <: dom.EventTarget] = EventHandler[T, SyntheticMouseEvent[T]]
  type KeyboardEventHandler[T <: dom.EventTarget] = EventHandler[T, SyntheticKeyboardEvent[T]]
  type UIEventHandler[T <: dom.EventTarget] = EventHandler[T, SyntheticUIEvent[T]]
  type ChangeEventHandler[T <: dom.EventTarget] = EventHandler[T, SyntheticChangeEvent[T]]
}

@js.native
trait SyntheticEvent[+DOMEventTarget <: dom.EventTarget] extends js.Object {
  val bubbles: Boolean = js.native
  val cancelable: Boolean = js.native
  val currentTarget: DOMEventTarget = js.native
  def defaultPrevented: Boolean = js.native
  val eventPhase: Double = js.native
  val isTrusted: Boolean = js.native
  val nativeEvent: dom.Event = js.native
  val target: DOMEventTarget = js.native
  val timeStamp: js.Date = js.native
  def preventDefault(): Unit = js.native
  def stopPropagation(): Unit = js.native
  def isPropagationStopped(): Boolean = js.native
  val `type`: String = js.native
  def persist(): Unit = js.native
}

@js.native
trait SyntheticUIEvent[+DOMEventTarget <: dom.EventTarget] extends SyntheticEvent[DOMEventTarget] {
  override val nativeEvent: dom.UIEvent = js.native
  val view: js.Object = js.native
  def detail: Double = js.native
}

@js.native
trait SyntheticKeyboardEvent[+DOMEventTarget <: dom.EventTarget] extends SyntheticUIEvent[DOMEventTarget] {
  override val nativeEvent: dom.KeyboardEvent = js.native
  val location: Double = js.native
  val altKey: Boolean = js.native
  val ctrlKey: Boolean = js.native
  val metaKey: Boolean = js.native
  val shiftKey: Boolean = js.native
  val repeat: Boolean = js.native
  val locale: String = js.native
  def getModifierState(keyArg: String): Boolean = js.native
  val key: String = js.native
  val charCode: Int = js.native
  val keyCode: Int = js.native
  val which: Int = js.native
}

@js.native
trait SyntheticMouseEvent[+DOMEventTarget <: dom.EventTarget] extends SyntheticUIEvent[DOMEventTarget] {
  override val nativeEvent: dom.MouseEvent = js.native
  val ctrlKey: Boolean = js.native
  val shiftKey: Boolean = js.native
  val altKey: Boolean = js.native
  val metaKey: Boolean = js.native
  val button: Short = js.native
  val buttons: Short = js.native
  val clientX: Double = js.native
  val clientY: Double = js.native
  val pageX: Double = js.native
  val pageY: Double = js.native
  val screenX: Double = js.native
  val screenY: Double = js.native
  val relatedTarget: DOMEventTarget = js.native
  def getModifierState(keyArg: String): Boolean = js.native
}

@js.native
trait SyntheticClipboardEvent[+T <: dom.EventTarget] extends SyntheticEvent[T] {
  val clipboardData: dom.DataTransfer = js.native
  override val nativeEvent: dom.ClipboardEvent = js.native
}

@js.native
trait SyntheticCompositionEvent[+T <: dom.EventTarget] extends SyntheticEvent[T] {
  val data: String = js.native
  override val nativeEvent: dom.CompositionEvent = js.native
}

@js.native
trait SyntheticDragEvent[+T <: dom.EventTarget] extends SyntheticMouseEvent[T] {
  val dataTransfer: dom.DataTransfer = js.native
  override val nativeEvent: dom.DragEvent = js.native
}

@js.native
trait SyntheticFocusEvent[+T <: dom.EventTarget] extends SyntheticEvent[T] {
  override val nativeEvent: dom.FocusEvent = js.native
  val relatedTarget: dom.EventTarget = js.native
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
  val altKey: Boolean = js.native
  val changedTouches: dom.TouchList = js.native
  val ctrlKey: Boolean = js.native

  /**
    * See [DOM Level 3 Events spec](https://www.w3.org/TR/uievents-key/#keys-modifier). for a list of valid (case-sensitive) arguments to this method.
    */
  def getModifierState(key: String): Boolean = js.native
  val metaKey: Boolean = js.native
  override val nativeEvent: dom.TouchEvent = js.native
  val shiftKey: Boolean = js.native
  val targetTouches: dom.TouchList = js.native
  val touches: dom.TouchList = js.native
}

@js.native
trait SyntheticWheelEvent[+T <: dom.EventTarget] extends SyntheticMouseEvent[T] {
  val deltaMode: Double = js.native
  val deltaX: Double = js.native
  val deltaY: Double = js.native
  val deltaZ: Double = js.native
  override val nativeEvent: dom.WheelEvent = js.native
}

@js.native
trait SyntheticAnimationEvent[+T <: dom.EventTarget] extends SyntheticEvent[T] {
  val animationName: String = js.native
  val elapsedTime: Double = js.native
  override val nativeEvent: dom.AnimationEvent = js.native
  val pseudoElement: String = js.native
}

@js.native
trait SyntheticTransitionEvent[+T <: dom.EventTarget] extends SyntheticEvent[T] {
  val elapsedTime: Double = js.native
  override val nativeEvent: dom.TransitionEvent = js.native
  val propertyName: String = js.native
  val pseudoElement: String = js.native
}

trait SetInnerHTML extends js.Object {
  val __html: js.UndefOr[String] = js.undefined
}

trait Attributes extends js.Object {
  var key: js.UndefOr[String] = js.undefined
}

trait ClassAttributes[E] extends Attributes {
  var ref: js.UndefOr[RefCbE[E]] = js.undefined
}

/**
  * Mostly handlers for DOM nodes.
  */
trait DOMAttributes[+T <: dom.EventTarget] extends js.Object {

  /** react specific */
  var children: js.UndefOr[ReactNode] = js.undefined

  /** react specific */
  var dangerouslySetInnerHTML: js.UndefOr[SetInnerHTML] = js.undefined

  // keyboard events val onKeyDown: js.UndefOr[js.Function0[]] = js.undefined
  var onKeyDown: js.UndefOr[KeyboardEventHandler[T @uv]] = js.undefined
  var onKeyPress: js.UndefOr[KeyboardEventHandler[T @uv]] = js.undefined
  var onKeyPressCapture: js.UndefOr[KeyboardEventHandler[T @uv]] = js.undefined
  var onKeyUp: js.UndefOr[KeyboardEventHandler[T @uv]] = js.undefined
  var onKeyUpCapture: js.UndefOr[KeyboardEventHandler[T @uv]] = js.undefined

  // mouse events
  var onClick: js.UndefOr[MouseEventHandler[T @uv]] = js.undefined
  var onClickCapture: js.UndefOr[MouseEventHandler[T @uv]] = js.undefined
  var onDoubleClick: js.UndefOr[MouseEventHandler[T @uv]] = js.undefined
  var onDoubleClickCapture: js.UndefOr[MouseEventHandler[T @uv]] = js.undefined

  var onMouseDown: js.UndefOr[MouseEventHandler[T @uv]] = js.undefined
  var onMouseEnter: js.UndefOr[MouseEventHandler[T @uv]] = js.undefined
  var onMouseLeave: js.UndefOr[MouseEventHandler[T @uv]] = js.undefined
  var onMouseMove: js.UndefOr[MouseEventHandler[T @uv]] = js.undefined
  var onMouseOut: js.UndefOr[MouseEventHandler[T @uv]] = js.undefined
  var onMouseOver: js.UndefOr[MouseEventHandler[T @uv]] = js.undefined
  var onMouseUp: js.UndefOr[MouseEventHandler[T @uv]] = js.undefined

  // this won't work because once it's a val we can't put in type bounds
  //def onKeyDown[U >: T <: dom.EventTarget]: js.UndefOr[KeyboardEventHandler[U]] = js.undefined

  // lots more to type...
}

trait HTMLAttributes[+T <: dom.EventTarget] extends DOMAttributes[T] {

  /** react specific */
  var defaultChecked: js.UndefOr[Boolean] = js.undefined

  /** react specific */
  var defaultVarue: js.UndefOr[String | js.Array[String]] = js.undefined

  /** react specific */
  var suppressContentEditableWarning: js.UndefOr[Boolean] = js.undefined

  var accessKey: js.UndefOr[String] = js.undefined
  var className: js.UndefOr[String] = js.undefined
  var contentEditable: js.UndefOr[Boolean] = js.undefined
  var dir: js.UndefOr[String] = js.undefined
  var draggable: js.UndefOr[Boolean] = js.undefined
  var hidden: js.UndefOr[Boolean] = js.undefined
  var id: js.UndefOr[String] = js.undefined
  var lang: js.UndefOr[String] = js.undefined
  var slot: js.UndefOr[String] = js.undefined
  var spellCheck: js.UndefOr[Boolean] = js.undefined
  // was js.Any, but use js.Object|js.Dynamic or other, can use StyleAttr here!
  var style: js.UndefOr[js.Object | js.Dynamic] = js.undefined
  var tabIndex: js.UndefOr[Int] = js.undefined
  var title: js.UndefOr[String] = js.undefined

  var inputMode: js.UndefOr[String] = js.undefined
  var is: js.UndefOr[String] = js.undefined
  var radioGroup: js.UndefOr[String] = js.undefined

  // WAI-ARIA
  var role: js.UndefOr[String] = js.undefined

  // non-standard
  var autoCapitalize: js.UndefOr[String] = js.undefined
  var autoCorrect: js.UndefOr[String] = js.undefined
  var autoSave: js.UndefOr[String] = js.undefined
  var color: js.UndefOr[String] = js.undefined
  var itemProp: js.UndefOr[String] = js.undefined
  var itemScope: js.UndefOr[Boolean] = js.undefined
  var itemType: js.UndefOr[String] = js.undefined
  var itemID: js.UndefOr[String] = js.undefined
  var itemRef: js.UndefOr[String] = js.undefined
  // or Number?
  var results: js.UndefOr[Int] = js.undefined
  var security: js.UndefOr[String] = js.undefined
  var nselectable: js.UndefOr[Boolean] = js.undefined
}

trait AllHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  // Standard HTML Attributes
  var accept: js.UndefOr[String] = js.undefined
  var acceptCharset: js.UndefOr[String] = js.undefined
  var action: js.UndefOr[String] = js.undefined
  var allowFullScreen: js.UndefOr[Boolean] = js.undefined
  var allowTransparency: js.UndefOr[Boolean] = js.undefined
  var alt: js.UndefOr[String] = js.undefined
  var as: js.UndefOr[String] = js.undefined
  var async: js.UndefOr[Boolean] = js.undefined
  var autoComplete: js.UndefOr[String] = js.undefined
  var autoFocus: js.UndefOr[Boolean] = js.undefined
  var autoPlay: js.UndefOr[Boolean] = js.undefined
  var capture: js.UndefOr[Boolean] = js.undefined
  var cellPadding: js.UndefOr[String | Int] = js.undefined
  var cellSpacing: js.UndefOr[String | Int] = js.undefined
  var charSet: js.UndefOr[String] = js.undefined
  var challenge: js.UndefOr[String] = js.undefined
  var checked: js.UndefOr[Boolean] = js.undefined
  var cite: js.UndefOr[String] = js.undefined
  var classID: js.UndefOr[String] = js.undefined
  var cols: js.UndefOr[Int] = js.undefined
  var colSpan: js.UndefOr[Int] = js.undefined
  var content: js.UndefOr[String] = js.undefined
  var controls: js.UndefOr[Boolean] = js.undefined
  var coords: js.UndefOr[String] = js.undefined
  var crosOrigin: js.UndefOr[String] = js.undefined
  var data: js.UndefOr[String] = js.undefined
  var dataTime: js.UndefOr[String] = js.undefined
  var default: js.UndefOr[Boolean] = js.undefined
  var defer: js.UndefOr[Boolean] = js.undefined
  var disabled: js.UndefOr[Boolean] = js.undefined
  var download: js.UndefOr[js.Any] = js.undefined
  var encType: js.UndefOr[String] = js.undefined
  var form: js.UndefOr[String] = js.undefined
  var formAction: js.UndefOr[String] = js.undefined
  var formEncType: js.UndefOr[String] = js.undefined
  var formMethod: js.UndefOr[String] = js.undefined
  var formNoValidate: js.UndefOr[Boolean] = js.undefined
  var formTarget: js.UndefOr[String] = js.undefined
  var frameBorder: js.UndefOr[String | Int] = js.undefined
  var headers: js.UndefOr[String] = js.undefined
  var height: js.UndefOr[Int | String] = js.undefined
  var high: js.UndefOr[Int] = js.undefined
  var href: js.UndefOr[String] = js.undefined
  var hrefLang: js.UndefOr[String] = js.undefined
  var htmlFor: js.UndefOr[String] = js.undefined
  var httpEquiv: js.UndefOr[String] = js.undefined
  var integrity: js.UndefOr[String] = js.undefined
  var keyParams: js.UndefOr[String] = js.undefined
  var keyType: js.UndefOr[String] = js.undefined
  var kind: js.UndefOr[String] = js.undefined
  var label: js.UndefOr[String] = js.undefined
  var list: js.UndefOr[String] = js.undefined
  var loop: js.UndefOr[Boolean] = js.undefined
  var low: js.UndefOr[Int] = js.undefined
  var manifest: js.UndefOr[String] = js.undefined
  var marginHeight: js.UndefOr[Int] = js.undefined
  var marginWidth: js.UndefOr[Int] = js.undefined
  var max: js.UndefOr[Int | String] = js.undefined
  var maxLength: js.UndefOr[Int] = js.undefined
  var media: js.UndefOr[String] = js.undefined
  var mediaGroup: js.UndefOr[String] = js.undefined
  var method: js.UndefOr[String] = js.undefined
  var min: js.UndefOr[Int | String] = js.undefined
  var minLength: js.UndefOr[Int] = js.undefined
  var multiple: js.UndefOr[Boolean] = js.undefined
  var muted: js.UndefOr[Boolean] = js.undefined
  var name: js.UndefOr[String] = js.undefined
  var nonce: js.UndefOr[String] = js.undefined
  var noValidate: js.UndefOr[Boolean] = js.undefined
  var open: js.UndefOr[Boolean] = js.undefined
  var optimum: js.UndefOr[Int] = js.undefined
  var pattern: js.UndefOr[String] = js.undefined
  var placeholder: js.UndefOr[String] = js.undefined
  var playsInline: js.UndefOr[Boolean] = js.undefined
  var poster: js.UndefOr[String] = js.undefined
  var preload: js.UndefOr[String] = js.undefined
  var readOnly: js.UndefOr[Boolean] = js.undefined
  var rel: js.UndefOr[String] = js.undefined
  var required: js.UndefOr[Boolean] = js.undefined
  var reversed: js.UndefOr[Boolean] = js.undefined
  var rows: js.UndefOr[Int] = js.undefined
  var rowSpan: js.UndefOr[Int] = js.undefined
  var sandbox: js.UndefOr[String] = js.undefined
  var scope: js.UndefOr[String] = js.undefined
  var scoped: js.UndefOr[String] = js.undefined
  var scrolling: js.UndefOr[String] = js.undefined
  var seamless: js.UndefOr[Boolean] = js.undefined
  var selected: js.UndefOr[Boolean] = js.undefined
  var shape: js.UndefOr[String] = js.undefined
  var size: js.UndefOr[Int] = js.undefined
  var sizes: js.UndefOr[String] = js.undefined
  var span: js.UndefOr[Int] = js.undefined
  var src: js.UndefOr[String] = js.undefined
  var srcDoc: js.UndefOr[String] = js.undefined
  var srcLang: js.UndefOr[String] = js.undefined
  var srcSet: js.UndefOr[String] = js.undefined
  var start: js.UndefOr[Int] = js.undefined
  var step: js.UndefOr[Int | String] = js.undefined
  var summary: js.UndefOr[String] = js.undefined
  var target: js.UndefOr[String] = js.undefined
  var `type`: js.UndefOr[String] = js.undefined
  var useMap: js.UndefOr[String] = js.undefined
  var value: js.UndefOr[String | Array[String] | Int] = js.undefined
  var width: js.UndefOr[String | Int] = js.undefined
  var wmode: js.UndefOr[String] = js.undefined
  var wrap: js.UndefOr[String] = js.undefined
}

trait AnchorHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var download: js.UndefOr[js.Any] = js.undefined
  var href: js.UndefOr[String] = js.undefined
  var hrefLang: js.UndefOr[String] = js.undefined
  var media: js.UndefOr[String] = js.undefined
  var rel: js.UndefOr[String] = js.undefined
  var target: js.UndefOr[String] = js.undefined
  var `type`: js.UndefOr[String] = js.undefined
  var as: js.UndefOr[String] = js.undefined
}

trait ButtonHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var autoFocus: js.UndefOr[Boolean] = js.undefined
  var disabled: js.UndefOr[Boolean] = js.undefined
  var form: js.UndefOr[String] = js.undefined
  var formAction: js.UndefOr[String] = js.undefined
  var formEncType: js.UndefOr[String] = js.undefined
  var formMethod: js.UndefOr[String] = js.undefined
  var formNoVaridate: js.UndefOr[Boolean] = js.undefined
  var formTarget: js.UndefOr[String] = js.undefined
  var name: js.UndefOr[String] = js.undefined
  var `type`: js.UndefOr[String] = js.undefined
  var varue: js.UndefOr[String | js.Array[String] | Int] = js.undefined
}

trait HtmlHTMLAtributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var mainfest: js.UndefOr[String] = js.undefined
}

trait LabelHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var form: js.UndefOr[String] = js.undefined
  var htmlFor: js.UndefOr[String] = js.undefined
}

/*
    interface IframeHTMLAttributes<T> extends HTMLAttributes<T> {
        allowFullScreen?: boolean;
        allowTransparency?: boolean;
        frameBorder?: number | string;
        height?: number | string;
        marginHeight?: number;
        marginWidth?: number;
        name?: string;
        sandbox?: string;
        scrolling?: string;
        seamless?: boolean;
        src?: string;
        srcDoc?: string;
        width?: number | string;
    }
 */

trait InputHTMLAttirbutes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var accept: js.UndefOr[String] = js.undefined
  var alt: js.UndefOr[String] = js.undefined
  var autoComplete: js.UndefOr[String] = js.undefined
  var autoFocus: js.UndefOr[Boolean] = js.undefined
  var capture: js.UndefOr[Boolean] = js.undefined
  var checked: js.UndefOr[Boolean] = js.undefined
  var crossOrigin: js.UndefOr[String] = js.undefined
  var disabled: js.UndefOr[Boolean] = js.undefined
  var form: js.UndefOr[String] = js.undefined
  var formAction: js.UndefOr[String] = js.undefined
  var formEncType: js.UndefOr[String] = js.undefined
  var formNoValidate: js.UndefOr[Boolean] = js.undefined
  var formTarget: js.UndefOr[String] = js.undefined
  var height: js.UndefOr[String | Int] = js.undefined
  var list: js.UndefOr[String] = js.undefined
  var max: js.UndefOr[Int | String] = js.undefined
  var maxLength: js.UndefOr[Int] = js.undefined
  var min: js.UndefOr[Int | String] = js.undefined
  var minLength: js.UndefOr[Int] = js.undefined
  var multiple: js.UndefOr[Boolean] = js.undefined
  var name: js.UndefOr[String] = js.undefined
  var pattern: js.UndefOr[String] = js.undefined
  var placeholder: js.UndefOr[String] = js.undefined
  var readOnly: js.UndefOr[Boolean] = js.undefined
  var required: js.UndefOr[Boolean] = js.undefined
  var size: js.UndefOr[Int] = js.undefined
  var src: js.UndefOr[String] = js.undefined
  var step: js.UndefOr[String | Int] = js.undefined
  var `type`: js.UndefOr[String] = js.undefined
  var value: js.UndefOr[String | Array[String] | Int] = js.undefined
  var width: js.UndefOr[String | Int] = js.undefined
  var onChange: js.UndefOr[ChangeEventHandler[T @uv]] = js.undefined
}

trait InputHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var onChange: js.UndefOr[ChangeEventHandler[T @uv]] = js.undefined
}

trait LinkHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var as: js.UndefOr[String] = js.undefined
  var crossOrigin: js.UndefOr[String] = js.undefined
  var href: js.UndefOr[String] = js.undefined
  var hrefLang: js.UndefOr[String] = js.undefined
  var integrity: js.UndefOr[String] = js.undefined
  var media: js.UndefOr[String] = js.undefined
  var rel: js.UndefOr[String] = js.undefined
  var sizes: js.UndefOr[String] = js.undefined
  var `type`: js.UndefOr[String] = js.undefined
}

trait MenuHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var `type`: js.UndefOr[String] = js.undefined
}

/*
    interface ScriptHTMLAttributes<T> extends HTMLAttributes<T> {
        async?: boolean;
        charSet?: string;
        crossOrigin?: string;
        defer?: boolean;
        integrity?: string;
        nonce?: string;
        src?: string;
        type?: string;
    }
 */
/*
    interface SelectHTMLAttributes<T> extends HTMLAttributes<T> {
        autoFocus?: boolean;
        disabled?: boolean;
        form?: string;
        multiple?: boolean;
        name?: string;
        required?: boolean;
        size?: number;
        value?: string | string[] | number;
        onChange?: ChangeEventHandler<T>;
    }
 */
/*
    interface StyleHTMLAttributes<T> extends HTMLAttributes<T> {
        media?: string;
        nonce?: string;
        scoped?: boolean;
        type?: string;
    }
 */
/*
    interface TableHTMLAttributes<T> extends HTMLAttributes<T> {
        cellPadding?: number | string;
        cellSpacing?: number | string;
        summary?: string;
    }
 */

trait TextAreaHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  var autoComplete: js.UndefOr[String] = js.undefined
  var autoFocus: js.UndefOr[String] = js.undefined
  var cols: js.UndefOr[Int] = js.undefined
  var dirName: js.UndefOr[String] = js.undefined
  var disabled: js.UndefOr[Boolean] = js.undefined
  var form: js.UndefOr[String] = js.undefined
  var maxLength: js.UndefOr[Int] = js.undefined
  var minLength: js.UndefOr[Int] = js.undefined
  var name: js.UndefOr[String] = js.undefined
  var placeholder: js.UndefOr[String] = js.undefined
  var readOnly: js.UndefOr[Boolean] = js.undefined
  var required: js.UndefOr[Boolean] = js.undefined
  var rows: js.UndefOr[Int] = js.undefined
  var value: js.UndefOr[String | Array[String] | Int] = js.undefined
  var wrap: js.UndefOr[String] = js.undefined
  var onChange: js.UndefOr[ChangeEventHandler[T @uv]] = js.undefined
}

/*
    interface TdHTMLAttributes<T> extends HTMLAttributes<T> {
        colSpan?: number;
        headers?: string;
        rowSpan?: number;
        scope?: string;
    }
 */

/*
    interface ThHTMLAttributes<T> extends HTMLAttributes<T> {
        colSpan?: number;
        headers?: string;
        rowSpan?: number;
        scope?: string;
    }
 */
