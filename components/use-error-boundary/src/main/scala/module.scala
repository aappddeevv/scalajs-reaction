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

package use_error_boundary

import scala.scalajs.js
import js.|
import js.annotation._

import react._
import react.implicits._

//
@js.native
trait ReactErrorInfo extends js.Object {
    val componentStack: String = js.native
}

/** Object tag so we can add extension methods. */
@js.native
trait ReactJSComponentErrorBoundary extends ReactJSComponent

trait Props extends js.Object {
  var children: js.UndefOr[ReactNode] = js.undefined
  var render: js.UndefOr[js.Function0[ReactNode]] = js.undefined
  var renderError: js.UndefOr[js.Function1[scala.Any, ReactNode]] = js.undefined
}

@js.native
trait HookValue extends js.Object {
    val ErrorBoundary: ReactJSComponentErrorBoundary = js.native
    val didCatch: Boolean = js.native
    /** Most of the time js.Error but could be anything. */
    val error: scala.Any | Null = js.native
    val errorInfo: ReactErrorInfo | Null = js.native
}

//
@js.native
@JSImport("use-error-boundary", JSImport.Namespace)
object module extends js.Object {
    def useErrorBoundary(): HookValue = js.native
}


