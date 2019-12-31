// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

import scala.scalajs.js
import js.|

package object express {
  type Next = js.Function1[js.UndefOr[_ <: js.Any|String], Unit]
  type TotalHandler = js.Function2[Request, Response, _]
  type Handler = js.Function3[Request, Response, Next, _]
  type ErrorHandler[E <: js.Object] = js.Function4[E, Request, Response, Next, _]
  type PathParams = String | js.RegExp | Array[String|js.RegExp]

  implicit class RichApp(app: App) {
    def onMount(callback: js.Function1[express.App, Unit]): Unit = app.on("mount", callback)
  }

  def apply(): App = express.ExpressImport.apply()
  def json(options: JSONOptions) = express.ExpressImport.json(options)
  def Router(options: RouterOptions) = express.ExpressImport.Router(options)
  def urlencoded(options: URLEncodedOptions) = express.ExpressImport.urlencoded(options)
  def static(options: StaticOptions) = express.ExpressImport.static(options)
}
