// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js
import js.annotation._
import js.|
import js._
import js.Dynamic.{literal => lit}

import org.scalajs.dom

// https://github.com/chandu0101/sri/blob/a5fb8db2cc666299ecc863d6421994cce5d304e6/core/src/main/scala/sri/core/React.scala
@js.native
private[react] trait JSReact extends js.Object {

  /** Can take a wide variety of types for tpe: string | sfc | class (extending React.Component) */
  def createElement[P](
      el: js.Any | String,
      props: UndefOr[P],
      children: ReactNode*): ReactDOMElement                             = js.native
  def cloneElement(el: ReactElement, props: js.Dynamic): ReactDOMElement = js.native
  // symbol or number depending on browser/environment support for symbols
  val Fragment: js.Any = js.native

  /** v16.3 API. */
  def createContext[T](
      defaultValue: T,
      calculateChangedBits: js.UndefOr[js.Function2[T, T, Int]]): ReactContext[T] = js.native
}

@js.native
@JSImport("react", JSImport.Namespace)
private[react] object JSReact extends JSReact

object React {
  def createElement(tag: String, props: js.Object)(children: ReactNode*): ReactDOMElement =
    JSReact.createElement(tag, props, children: _*)

  def createElement(tag: ReactClass, props: js.Object)(children: ReactNode*): ReactDOMElement =
    JSReact.createElement(tag, props, children: _*)

  def createElement(tag: String)(children: ReactNode*): ReactDOMElement =
    JSReact.createElement(tag, js.undefined, children: _*)

  def createElement(
      c: ReactClass
  ): ReactDOMElement = JSReact.createElement(c, js.undefined)

  /**
    * Create a react fragment. Fragments are created as an "element" with a specific
    * tag (symbol or number if target does not support symbol) vs say, the string "div".
    */
  def createFragment(key: Option[String], children: ReactNode*): ReactDOMElement = {
    val props = js.Dictionary.empty[js.Any]
    key.foreach(props("key") = _)
    JSReact.createElement(JSReact.Fragment, props, children: _*)
  }
}

/**
  * We use create-react-class under the hood to create all classes
  */
@js.native
@JSImport("create-react-class", JSImport.Default)
private[react] object reactCreateClass extends js.Object {
  def apply(props: js.Object): ReactClass = js.native
}

/**
  * React's context object contains the consumer and provider "components".
  *
  * @todo validate that current/default value are part of the public API.
  */
@js.native
trait ReactContext[T] extends js.Object {
  type ValueType = T

  /** Only takes a single attribute value, "value" with the context . */
  val Provider: js.Any = js.native
  val Consumer: js.Any = js.native

  /** Not public API. */
  var currentValue: T = js.native

  /** Not public API. */
  val defaultValue: T = js.native
}

/**
  * Provide context using react 16.3+ context mechanism. Import this object to
  * obtain a syntax enhancement to create provider's and consumers. The syntax is
  * not included in the standard syntax module.
  *
  * @todo Consumer could take a bits argument but react's API is not settled yet.
  */
object context {

  /** v16.3 API. */
  def make[T](defaultValue: T): ReactContext[T] =
    JSReact.createContext[T](defaultValue, js.undefined)

  def makeProvider[T](ctx: ReactContext[T])(value: Option[T])(children: ReactNode*): ReactNode = {
    val v = lit("value" -> value.getOrElse(ctx.currentValue).asInstanceOf[js.Any])
    JSReact.createElement(ctx.Provider, v, children: _*)
  }

  def makeConsumer[T](ctx: ReactContext[T])(
      f: js.Function1[T, ReactNode],
      key: Option[String] = None): ReactNode = {
    val props = key.fold[js.Any](js.undefined)(k => lit("key" -> k))
    JSReact.createElement(ctx.Consumer, props, f.asInstanceOf[ReactNode])
  }

  /** `import context._` brings the syntax into scope. */
  implicit class ReactContextOps[T](ctx: ReactContext[T]) {
    def makeProvider(value: T)(children: ReactNode*) =
      context.makeProvider[T](ctx)(Some(value))(children: _*)
    def makeProvider(children: ReactNode*) = context.makeProvider[T](ctx)(None)(children: _*)
    def makeConsumer(f: js.Function1[T, ReactNode], key: Option[String] = None) =
      context.makeConsumer[T](ctx)(f)
  }
}
