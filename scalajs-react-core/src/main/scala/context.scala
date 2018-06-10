// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js
import js.Dynamic.{literal => lit}

/**
  * Provide context using react 16.3+ context mechanism. Import this object to
  * obtain a syntax enhancement to create provider's and consumers. The syntax
  * is not included in the standard syntax module. Not using an "optional"
  * concept in the below forces a zero to be created for every element you want
  * to pass in the context, which may be burdonsome. Considering using
  * js.UndefOr or Option as a wrapper around your value.
  */
object context {

  def make[T](defaultValue: T): ReactContext[T] =
    JSReact.createContext(defaultValue, js.undefined)

  def makeProvider[T](ctx: ReactContext[T])(value: T)(children: ReactNode*): ReactNode = {
    // not sure this is right, use previous value if current is missing?
    val v = lit("value" -> value.asInstanceOf[js.Any])
    JSReact.createElement(ctx.Provider, v, children: _*)
  }

  def makeConsumer[T](ctx: ReactContext[T])(
      f: js.Function1[T, ReactNode],
      key: Option[String] = None): ReactNode = {
    val props = key.fold[js.Any](js.undefined)(k => lit("key" -> k))
    JSReact.createElement(ctx.Consumer, props, f.asInstanceOf[ReactNode])
  }

  implicit class ReactContextOps[T](ctx: ReactContext[T]) {

    /** Create provider that provides an optional value. */
    def makeProvider(value: T)(children: ReactNode*) =
      context.makeProvider[T](ctx)(value)(children: _*)

    ///** Create provider that provides None. */
    //def makeProvider(children: ReactNode*) = context.makeProvider[T](ctx)(js.undefined)(children: _*)

    /** Create a consumer that takes an Option[T] */
    def makeConsumer(f: T => ReactNode, key: Option[String] = None) =
      context.makeConsumer[T](ctx)(js.Any.toFunction1(f))
  }
}
