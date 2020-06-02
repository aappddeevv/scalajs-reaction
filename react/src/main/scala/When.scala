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

/**
 *  Conditional rendering support. Uses by-name parameters to delay creating
 *  a react node if the condition idicates not to render.
 */
trait When {

  /** Render something or return a null element. Render is by name. Could just use fold. */
  def when[T <: Boolean](cond: js.UndefOr[T])(render: => ReactNode)(implicit ev: T =:= Boolean): ReactNode =
    if (cond.getOrElse(false)) render else nullNode

  /** Render something or return a null element. Render is by name. Could just use fold. */
  def when[T <: Boolean](cond: Option[T])(render: => ReactNode)(implicit ev: T =:= Boolean): ReactNode =
    if (cond.getOrElse(false)) render else nullNode

  /** Render something or return a null element. Render is by name. Could just use fold. */
  def when(cond: Boolean)(render: => ReactNode): ReactNode =
    if (cond) render else nullElement

  /** Render something if notcond or return a null element. Render is by name. Could also use fold. */
  def whenNot(cond: Boolean)(render: => ReactNode): ReactNode =
    if (!cond) render else nullElement

  /** Render something if not cond or return a null element. Render is by name. Could also use fold. */
  def whenNot[T <: Boolean](cond: js.UndefOr[T])(render: => ReactNode)(implicit ev: T =:= Boolean): ReactNode =
    if (!cond.getOrElse(false)) render else nullElement

  /** Render something if not cond or return a null element. Render is by name. Could also use fold. */
  def whenNot[T <: Boolean](cond: Option[T])(render: => ReactNode)(implicit ev: T =:= Boolean): ReactNode =
    if (!cond.getOrElse(false)) render else nullElement

  def when[T](cond: js.UndefOr[T])(render: => ReactNode): ReactNode =
    if (cond.isDefined) render else nullElement

  def whenNot[T](cond: js.UndefOr[T])(render: => ReactNode): ReactNode =
    if (cond.isEmpty) render else nullElement

}
