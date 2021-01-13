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

import scala.scalajs.js
import js.annotation._

import js.Dynamic.literal

/** Removing only the "r" on the end may be confusing... */
final class ReactContextOps2[T](private val ctx: ReactContext[T]) extends AnyVal {

  def provide(value: T)(children: ReactNode*) =
    ReactJS.createElement(ctx.Provider, literal("value" -> value.asInstanceOf[js.Any]), children: _*)

  def provide1(value: T, child: ReactNode) =
    ReactJS.createElement(ctx.Provider, literal("value" -> value.asInstanceOf[js.Any]), child)

  def consume(f: T => ReactNode, key: String) =
    ReactJS.createElement(
      ctx.Consumer,
      literal("key" -> key),
      createElement(js.Any.toFunction1(f).asInstanceOf[ReactJSFunctionComponent], null))

  def consume(f: T => ReactNode) =
    ReactJS.createElement(
      ctx.Consumer,
      literal(),
      createElement(js.Any.toFunction1(f).asInstanceOf[ReactJSFunctionComponent], null))
}

trait ContextSyntax {
  implicit def contextToRichContext[T](ctx: ReactContext[T]): ReactContextOps2[T] =
    new ReactContextOps2[T](ctx)
}
