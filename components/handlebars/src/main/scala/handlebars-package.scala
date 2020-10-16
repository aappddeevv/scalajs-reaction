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

// Copyright (c) 2019 The Trapelo Group LLC - All Rights Reserved
// Unauthorized copying of this file, via any medium is strictly prohibited.
// Proprietary and confidential.

import scala.scalajs.js
import js.|
import js.annotation._
import js._

package object handlebars {
  @js.native
  @JSImport("handlebars", "compile")
  def compile(source: String): Template = js.native

  @js.native
  @JSImport("handlebars", "precompile")
  def precompile(source: String): TemplateSpec = js.native

  @js.native
  @JSImport("handlebars", "registerHelper")
  def registerHelper(name: String, cb: js.ThisFunction2[js.Dynamic, js.Any, HelperOptions, String]): Unit = js.native

  @js.native
  @JSImport("handlebars", "unregisterHelper")
  def unregisterHelper(name: String): Unit = js.native
  @js.native
  @JSImport("handlebars", "registerPartial")
  def registerHelper(name: String, partial: String): Unit = js.native

  @js.native
  @JSImport("handlebars", "unregisterPartial")
  def registerHelper(name: String): Unit = js.native

  @js.native
  @JSImport("handlebars", "Utils")
  val Utils: HandlebarsUtils = js.native

  @js.native
  @JSImport("handlebars", "log")
  def log(level: Int, message: String): Unit = js.native

  @js.native
  @JSImport("handlebars", "template")
  def template(spec: TemplateSpec): Template = js.native

  @js.native
  @JSImport("handlebars", "SafeString")
  def SafeString(spec: String): String = js.native

}
