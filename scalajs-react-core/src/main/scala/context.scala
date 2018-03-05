// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js
import js.Dynamic.{literal => lit}

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

    def makeConsumer(f: T => ReactNode, key: Option[String] = None) =
      context.makeConsumer[T](ctx)(js.Any.toFunction1(f))
  }
}
