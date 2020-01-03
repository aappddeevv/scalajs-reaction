// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package react

import scala.scalajs.js
import js.Dynamic.{literal => lit}

/**
  * Provide context using react 16.3+ context mechanism. Import this object to
  * obtain a syntax enhancement to create provider's and consumers. The syntax
  * is not included in the standard syntax module. Avoiding an "optional"
  * concept in the below forces a zero to be created for every element you want
  * to pass in the context, which may be burdonsome. Considering using
  * js.UndefOr or Option as a wrapper around your value or values within your
  * context object.
  */
object context {

  @deprecated("Use create()")
  def make[T](defaultValue: T): ReactContext[T] =
    ReactJS.createContext(defaultValue, js.undefined)

  def create[T](defaultValue: T): ReactContext[T] =
    ReactJS.createContext(defaultValue, js.undefined)

  def provider[T](ctx: ReactContext[T])(value: T)(children: ReactNode*): ReactElement = {
    // not sure this is right, use previous value if current is missing?
    val v = lit("value" -> value.asInstanceOf[js.Any])
    ReactJS.createElement(ctx.Provider, v, children: _*)
  }

  def consumer[T](ctx: ReactContext[T])(
    //f: js.Function1[T, ReactNode],
    f: T => ReactNode,
    key: Option[String] = None): ReactElement = {
    val props = key.fold[js.Any](js.undefined)(k => lit("key" -> k))
    ReactJS.createElement(ctx.Consumer, props, js.Any.fromFunction1(f).asInstanceOf[ReactNode])
  }

  implicit class ReactContextOps[T](ctx: ReactContext[T]) {

    /** Create provider that provides an optional value. */
    def provider(value: T)(children: ReactNode*) =
      context.provider[T](ctx)(value)(children: _*)

    /** Create a consumer that takes an Option[T] */
    def consumer(f: T => ReactNode, key: Option[String] = None) =
      context.consumer[T](ctx)(js.Any.toFunction1(f))
  }
}
