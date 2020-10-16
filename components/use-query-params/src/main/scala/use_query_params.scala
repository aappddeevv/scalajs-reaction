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

import scala.scalajs.js

package object use_query_params {
  import use_query_params._
  type QueryConfig = js.Dictionary[Param]

  def useQueryParam[T](
    name: String,
    encoder: Param): (js.UndefOr[T], js.Function2[T, js.UndefOr[UrlUpdateType], Unit]) =
    module.useQueryParam[T](name, encoder)

  def useQueryParams(
    name: String,
    encoders: QueryConfig): (String, js.Function2[js.Dictionary[js.Any], UrlUpdateType, Unit]) =
    module.useQueryParams(name, encoders)

  val NumberParam: Param = module.NumberParam
  val StringParam: Param = module.StringParam
  val ObjectParam: Param = module.ObjectParam
  val ArrayParam: Param = module.ArrayParam
  val JsonParam: Param = module.JsonParam
  val DateParam: Param = module.DateParam
  val BooleanParam: Param = module.BooleanParam
  val NumericObjectParam: Param = module.NumericObjectParam
  val DelimitedArrayParam: Param = module.DelimitedArrayParam
  val DelimitedNumericArrayParam: Param = module.DelimitedNumericArrayParam
}
