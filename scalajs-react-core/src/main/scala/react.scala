// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg

import scala.scalajs.js
import js.|
import js.UndefOr
import js.JSConverters._

/**
 * A scalajs. facade for facebook's react in the spirit of ReasonReact.
 */
package object react {

  /** Opaque type returned from a ref callback. */
  trait ReactRef extends js.Object

  /**
    * Something that can be rendered in reactjs. We need to restrict this a bit
    * more when returning values from the scala side API. reactjs allows this to
    * be quity flexible including strings, numbers, booleans in additon to
    * classes, functions, etc. This should probably be a js.Any as any API
    * used in this library will ensure that the right types are used and
    * this would get rid of the ugly `stringToElement` type functions below.
    * js.Object is technically not correct.
    */
  @js.native
  trait ReactNode extends js.Object

  /** Something you can render reactjs wth key and ref accessors. */
  @js.native
  trait ReactElement extends js.Object with ReactNode {
    val key: UndefOr[String]
    // ...add ref here...is it a string or a callback?
  }

  /** An element that has been created via createElement. Props are optional of course. */
  @js.native
  trait ReactDOMElement extends ReactElement {
    def `type`: String = js.native

    /** Raw JS props. */
    def props: UndefOr[js.Object] = js.native
  }

  /** A js-object that is returned from create-react-class. We treat as opaque. */
  @js.native
  trait ReactClass extends js.Object

  /** Alias for internal use. @deprecated */
  type ReactClassInternal = ReactClass

  /** Return a "non render" element. */
  val nullElement = null.asInstanceOf[ReactNode]

  def arrayToElement[T <: ReactNode](arr: js.Array[T]) = arr.asInstanceOf[ReactNode]

  def arrayToElement[T <: ReactNode](s: Seq[T]) = (s.toJSArray).asInstanceOf[ReactNode]

  def stringToElement(s: String): ReactNode = s.asInstanceOf[ReactNode]

  /**
    * Hidden field for scala components that are based directly on a js component.
    * Args should be an optional key and ref callback.
    */
  type JsElementWrapped = (Option[String], Option[RefCb]) => ReactElement

  /** Simplified ComponentSpec. */
  type Component[S, RP, A, SLF] = ComponentSpec[S, RP, A, SLF]

  type NoRetainedProps = Unit
  type Stateless = Unit
  type Actionless = Unit

  /** Callback for react ref. Pure string refs are not supported. */
  type RefCb = js.Function1[ReactElement, Unit]

  /** noop ref callback */
  val noopRefCb = () => ()

  /** Children type. */
  type PropsChildren = js.Array[ReactElement]

  /** No children value. */
  val noChildren: PropsChildren = js.Array()

  /** A subscription called after mount and after unmount. */
  type Subscription = () => (() => Unit)

  /** Indicates no props for createDomElement. */
  val noProps = new js.Object()
}
