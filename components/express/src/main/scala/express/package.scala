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

import js.|

package object express {
  type Next                         = js.Function1[js.UndefOr[_ <: js.Any | String], Unit]
  type TotalHandler                 = js.Function2[Request, Response, _]
  type Handler                      = js.Function3[Request, Response, Next, _]
  type ErrorHandler[E <: js.Object] = js.Function4[E, Request, Response, Next, _]
  type PathParams                   = String | js.RegExp | Array[String | js.RegExp]

  implicit class RichApp(app: App) {
    def onMount(callback: js.Function1[express.App, Unit]): Unit = app.on("mount", callback)
  }

  def apply(): App                           = express.ExpressImport.apply()
  def json(options: JSONOptions)             = express.ExpressImport.json(options)
  def Router(options: RouterOptions)         = express.ExpressImport.Router(options)
  def urlencoded(options: URLEncodedOptions) = express.ExpressImport.urlencoded(options)
  def static(options: StaticOptions)         = express.ExpressImport.static(options)
}
