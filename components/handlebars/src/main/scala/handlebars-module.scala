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

package handlebars

import scala.scalajs.js
import js.|
import js.annotation._
import js._

trait HandlebarsOptions extends js.Object {
  var data: js.UndefOr[Boolean] = js.undefined
  var compat: js.UndefOr[Boolean] = js.undefined
  var knownHelpersOnly: js.UndefOr[Boolean] = js.undefined
  var noEscape: js.UndefOr[Boolean] = js.undefined
  var strict: js.UndefOr[Boolean] = js.undefined
  var assumeObjects: js.UndefOr[Boolean] = js.undefined
  var preventIndent: js.UndefOr[Boolean] = js.undefined
  var ignoreStandalone: js.UndefOr[Boolean] = js.undefined
  var explicitPartialContext: js.UndefOr[Boolean] = js.undefined
}

@js.native
trait HandlebarsUtils extends js.Object {
  def isEmpty(value: js.Any): Boolean = js.native
  def extend(base: js.Object | js.Dynamic, value: js.Object | js.Dynamic): js.Object = js.native
  def toString(value: js.Any): String = js.native
  def isArray(value: js.Any): Boolean = js.native
  def isFunction(value: js.Any): Boolean = js.native
  def escapeExpression(text: String): String = js.native
  def log(level: Int, message: String): Unit = js.native
}

@js.native
trait TemplateSpec extends js.Object

trait TemplateOptions extends js.Object {
  var data: js.UndefOr[js.Object] = js.undefined
  var helpers: js.UndefOr[js.Object] = js.undefined
  var partials: js.UndefOr[js.Object] = js.undefined
  var allowCallsToHelperMissing: js.UndefOr[Boolean] = js.undefined
  var allowProtoMethodsByDefault: js.UndefOr[Boolean] = js.undefined
  var allowProtoMethods: js.UndefOr[Boolean] = js.undefined
  var allowProtoPropertiesByDefault: js.UndefOr[Boolean] = js.undefined
  var allowProtoProperties: js.UndefOr[Boolean] = js.undefined
}

@js.native
trait Template extends js.Object {
  def apply(
    context: js.Object | js.Dynamic,
    options: js.UndefOr[TemplateOptions | js.Object | js.Dynamic] = js.undefined): String = js.native
}

@js.native
trait HelperOptions extends js.Object {
  def fn(value: js.Any): String = js.native
}
