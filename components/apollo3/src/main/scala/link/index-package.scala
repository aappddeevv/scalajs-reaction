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

package apollo
package client3

import scala.scalajs.js
import js.|
import js.annotation._

package object link extends link.core.types_module with http.http_module with context.context_module {

  @js.native
  @JSImport("@apollo/client/link/http/selectHttpOptionsAndBody", "selectHttpOptionsAndBody")
  val selectHttpOptionsAndBody: js.Function3[js.Object, HttpConfig, js.Array[HttpConfig], js.Dynamic] = js.native

  @js.native
  @JSImport("@apollo/client/link/http/createHttpLink", "createHttpLink")
  def createHttpLink(linkOptions: HttpOptions): ApolloLink[?] = js.native

  @js.native
  @JSImport("@apollo/client/link/context", "setContext")
  def setContext[C](setter: ContextSetter[C]): ApolloLink[?] = js.native
}
