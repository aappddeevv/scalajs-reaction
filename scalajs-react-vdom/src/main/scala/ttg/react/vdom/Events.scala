// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package vdom

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
  type EventHandler[T <: dom.EventTarget, E <: SyntheticEvent[T]] = E => Unit

  /** General event handler. */
  type ReactEventHandler[T <: dom.EventTarget] = EventHandler[T, SyntheticEvent[T]]
  type MouseEventHandler[T <: dom.EventTarget] = EventHandler[T, SyntheticMouseEvent[T]]
  type KeyboardEventHandler[T <: dom.EventTarget] = EventHandler[T, SyntheticKeyboardEvent[T]]
  type UIEventHandler[T <: dom.EventTarget] = EventHandler[T, SyntheticUIEvent[T]]
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
trait SynthenticFocusEvent[+T <: dom.EventTarget] extends SyntheticEvent[T] {
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

/**
  * Mostly handlers for DOM nodes.
  */
trait DOMAttributes[+T <: dom.EventTarget] extends js.Object {
  val children: js.UndefOr[ReactNode] = js.undefined
  val dangerouslySetInnerHTML: js.UndefOr[SetInnerHTML] = js.undefined

  // keyboard events
  def onKeyDown[U >: T <: dom.EventTarget]: js.UndefOr[KeyboardEventHandler[U]] = js.undefined
  def onKeyPress[U >: T <: dom.EventTarget]: js.UndefOr[KeyboardEventHandler[U]] = js.undefined
  def onKeyPressCapture[U >: T <: dom.EventTarget]: js.UndefOr[KeyboardEventHandler[U]] = js.undefined
  def onKeyUp[U >: T <: dom.EventTarget]: js.UndefOr[KeyboardEventHandler[U]] = js.undefined
  def onKeyUpCapture[U >: T <: dom.EventTarget]: js.UndefOr[KeyboardEventHandler[U]] = js.undefined

  // mouse events
  def onClick[U >: T <: dom.EventTarget]: js.UndefOr[MouseEventHandler[U]] = js.undefined
  def onClickCapture[U >: T <: dom.EventTarget]: js.UndefOr[MouseEventHandler[U]] = js.undefined
  def onDoubleClick[U >: T <: dom.EventTarget]: js.UndefOr[MouseEventHandler[U]] = js.undefined
  def onDoubleClickCapture[U >: T <: dom.EventTarget]: js.UndefOr[MouseEventHandler[U]] = js.undefined

  def onMouseDown[U >: T <: dom.EventTarget]: js.UndefOr[MouseEventHandler[U]] = js.undefined
  def onMouseEnter[U >: T <: dom.EventTarget]: js.UndefOr[MouseEventHandler[U]] = js.undefined
  def onMouseLeave[U >: T <: dom.EventTarget]: js.UndefOr[MouseEventHandler[U]] = js.undefined
  def onMouseMove[U >: T <: dom.EventTarget]: js.UndefOr[MouseEventHandler[U]] = js.undefined
  def onMouseOut[U >: T <: dom.EventTarget]: js.UndefOr[MouseEventHandler[U]] = js.undefined
  def onMouseOver[U >: T <: dom.EventTarget]: js.UndefOr[MouseEventHandler[U]] = js.undefined
  def onMouseUp[U >: T <: dom.EventTarget]: js.UndefOr[MouseEventHandler[U]] = js.undefined

  // lots more to type...
}

trait HTMLAttributes[+T <: dom.EventTarget] extends DOMAttributes[T] {
  // react specific
  val defaultChecked: js.UndefOr[Boolean] = js.undefined
  val defaultValue: js.UndefOr[String | js.Array[String]] = js.undefined
  val suppressContentEditableWarning: js.UndefOr[Boolean] = js.undefined

  val accessKey: js.UndefOr[String] = js.undefined
  val className: js.UndefOr[String] = js.undefined
  val contentEditable: js.UndefOr[Boolean] = js.undefined
  val dir: js.UndefOr[String] = js.undefined
  val draggable: js.UndefOr[Boolean] = js.undefined
  val hidden: js.UndefOr[Boolean] = js.undefined
  val id: js.UndefOr[String] = js.undefined
  val lang: js.UndefOr[String] = js.undefined
  val slot: js.UndefOr[String] = js.undefined
  val spellCheck: js.UndefOr[Boolean] = js.undefined
  // should be js.Object|js.Dynamic or other
  val style: js.UndefOr[js.Any] = js.undefined
  val tabIndex: js.UndefOr[Int] = js.undefined
  val title: js.UndefOr[String] = js.undefined

  val inputMode: js.UndefOr[String] = js.undefined
  val is: js.UndefOr[String] = js.undefined
  val radioGroup: js.UndefOr[String] = js.undefined

  // WAI-ARIA
  val role: js.UndefOr[String] = js.undefined

  // non-standard
  val autoCapitalize: js.UndefOr[String] = js.undefined
  val autoCorrect: js.UndefOr[String] = js.undefined
  val autoSave: js.UndefOr[String] = js.undefined
  val color: js.UndefOr[String] = js.undefined
  val itemProp: js.UndefOr[String] = js.undefined
  val itemScope: js.UndefOr[Boolean] = js.undefined
  val itemType: js.UndefOr[String] = js.undefined
  val itemID: js.UndefOr[String] = js.undefined
  val itemRef: js.UndefOr[String] = js.undefined
  // or Number?
  val results: js.UndefOr[Int] = js.undefined
  val security: js.UndefOr[String] = js.undefined
  val nselectable: js.UndefOr[Boolean] = js.undefined
}

trait AllHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  // Standard HTML Attributes
  val accept: js.UndefOr[String] = js.undefined
  val acceptCharset: js.UndefOr[String] = js.undefined
  val action: js.UndefOr[String] = js.undefined
  val allowFullScreen: js.UndefOr[Boolean] = js.undefined
  val allowTransparency: js.UndefOr[Boolean] = js.undefined
  val alt: js.UndefOr[String] = js.undefined
  val as: js.UndefOr[String] = js.undefined
  val async: js.UndefOr[Boolean] = js.undefined
  val autComplete: js.UndefOr[String] = js.undefined
  val autoFocus: js.UndefOr[Boolean] = js.undefined
  val autoPlay: js.UndefOr[Boolean] = js.undefined
  val capture: js.UndefOr[Boolean] = js.undefined

  val content: js.UndefOr[String] = js.undefined
  val height: js.UndefOr[String | Int] = js.undefined

  val width: js.UndefOr[String | Int] = js.undefined

  /*
        accept?: string;
        acceptCharset?: string;
        action?: string;
        allowFullScreen?: boolean;
        allowTransparency?: boolean;
        alt?: string;
        as?: string;
        async?: boolean;
        autoComplete?: string;
        autoFocus?: boolean;
        autoPlay?: boolean;
        capture?: boolean;
        cellPadding?: number | string;
        cellSpacing?: number | string;
        charSet?: string;
        challenge?: string;
        checked?: boolean;
        cite?: string;
        classID?: string;
        cols?: number;
        colSpan?: number;
        content?: string;
        controls?: boolean;
        coords?: string;
        crossOrigin?: string;
        data?: string;
        dateTime?: string;
        default?: boolean;
        defer?: boolean;
        disabled?: boolean;
        download?: any;
        encType?: string;
        form?: string;
        formAction?: string;
        formEncType?: string;
        formMethod?: string;
        formNoValidate?: boolean;
        formTarget?: string;
        frameBorder?: number | string;
        headers?: string;
        height?: number | string;
        high?: number;
        href?: string;
        hrefLang?: string;
        htmlFor?: string;
        httpEquiv?: string;
        integrity?: string;
        keyParams?: string;
        keyType?: string;
        kind?: string;
        label?: string;
        list?: string;
        loop?: boolean;
        low?: number;
        manifest?: string;
        marginHeight?: number;
        marginWidth?: number;
        max?: number | string;
        maxLength?: number;
        media?: string;
        mediaGroup?: string;
        method?: string;
        min?: number | string;
        minLength?: number;
        multiple?: boolean;
        muted?: boolean;
        name?: string;
        nonce?: string;
        noValidate?: boolean;
        open?: boolean;
        optimum?: number;
        pattern?: string;
        placeholder?: string;
        playsInline?: boolean;
        poster?: string;
        preload?: string;
        readOnly?: boolean;
        rel?: string;
        required?: boolean;
        reversed?: boolean;
        rows?: number;
        rowSpan?: number;
        sandbox?: string;
        scope?: string;
        scoped?: boolean;
        scrolling?: string;
        seamless?: boolean;
        selected?: boolean;
        shape?: string;
        size?: number;
        sizes?: string;
        span?: number;
        src?: string;
        srcDoc?: string;
        srcLang?: string;
        srcSet?: string;
        start?: number;
        step?: number | string;
        summary?: string;
        target?: string;
        type?: string;
        useMap?: string;
        value?: string | string[] | number;
        width?: number | string;
        wmode?: string;
        wrap?: string;
 */
}

trait AnchorHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  val download: js.UndefOr[js.Any] = js.undefined
  val href: js.UndefOr[String] = js.undefined
  val hrefLang: js.UndefOr[String] = js.undefined
  val media: js.UndefOr[String] = js.undefined
  val rel: js.UndefOr[String] = js.undefined
  val target: js.UndefOr[String] = js.undefined
  val `type`: js.UndefOr[String] = js.undefined
  val as: js.UndefOr[String] = js.undefined
}

trait ButtonHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  val autoFocus: js.UndefOr[Boolean] = js.undefined
  val disabled: js.UndefOr[Boolean] = js.undefined
  val form: js.UndefOr[String] = js.undefined
  val formAction: js.UndefOr[String] = js.undefined
  val formEncType: js.UndefOr[String] = js.undefined
  val formMethod: js.UndefOr[String] = js.undefined
  val formNoValidate: js.UndefOr[Boolean] = js.undefined
  val formTarget: js.UndefOr[String] = js.undefined
  val name: js.UndefOr[String] = js.undefined
  val `type`: js.UndefOr[String] = js.undefined
  val value: js.UndefOr[String | js.Array[String] | Int] = js.undefined
}

trait HtmlHTMLAtributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  val mainfest: js.UndefOr[String] = js.undefined
}

trait LabelHTMLAttributes[+T <: dom.EventTarget] extends HTMLAttributes[T] {
  val form: js.UndefOr[String] = js.undefined
  val htmlFor: js.UndefOr[String] = js.undefined
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

    interface InputHTMLAttributes<T> extends HTMLAttributes<T> {
        accept?: string;
        alt?: string;
        autoComplete?: string;
        autoFocus?: boolean;
        capture?: boolean; // https://www.w3.org/TR/html-media-capture/#the-capture-attribute
        checked?: boolean;
        crossOrigin?: string;
        disabled?: boolean;
        form?: string;
        formAction?: string;
        formEncType?: string;
        formMethod?: string;
        formNoValidate?: boolean;
        formTarget?: string;
        height?: number | string;
        list?: string;
        max?: number | string;
        maxLength?: number;
        min?: number | string;
        minLength?: number;
        multiple?: boolean;
        name?: string;
        pattern?: string;
        placeholder?: string;
        readOnly?: boolean;
        required?: boolean;
        size?: number;
        src?: string;
        step?: number | string;
        type?: string;
        value?: string | string[] | number;
        width?: number | string;

        onChange?: ChangeEventHandler<T>;
    }

    interface LinkHTMLAttributes<T> extends HTMLAttributes<T> {
        as?: string;
        crossOrigin?: string;
        href?: string;
        hrefLang?: string;
        integrity?: string;
        media?: string;
        rel?: string;
        sizes?: string;
        type?: string;
    }
    interface MenuHTMLAttributes<T> extends HTMLAttributes<T> {
        type?: string;
    }
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
    interface StyleHTMLAttributes<T> extends HTMLAttributes<T> {
        media?: string;
        nonce?: string;
        scoped?: boolean;
        type?: string;
    }

    interface TableHTMLAttributes<T> extends HTMLAttributes<T> {
        cellPadding?: number | string;
        cellSpacing?: number | string;
        summary?: string;
    }

    interface TextareaHTMLAttributes<T> extends HTMLAttributes<T> {
        autoComplete?: string;
        autoFocus?: boolean;
        cols?: number;
        dirName?: string;
        disabled?: boolean;
        form?: string;
        maxLength?: number;
        minLength?: number;
        name?: string;
        placeholder?: string;
        readOnly?: boolean;
        required?: boolean;
        rows?: number;
        value?: string | string[] | number;
        wrap?: string;

        onChange?: ChangeEventHandler<T>;
    }

    interface TdHTMLAttributes<T> extends HTMLAttributes<T> {
        colSpan?: number;
        headers?: string;
        rowSpan?: number;
        scope?: string;
    }

    interface ThHTMLAttributes<T> extends HTMLAttributes<T> {
        colSpan?: number;
        headers?: string;
        rowSpan?: number;
        scope?: string;
    }
 */
