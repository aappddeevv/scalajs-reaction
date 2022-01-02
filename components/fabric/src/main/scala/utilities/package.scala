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

package fabric

import scala.scalajs.js
import js.annotation.*

package object utilities {
  type SettingsFunction = Settings => Settings
  type Settings = js.Dictionary[js.Any]

  val NO_CUSTOMIZATIONS: ICustomizations = (new js.Object {
    val settings = js.Dynamic.literal()
    val scopedSettings = js.Dynamic.literal()
    val inCustomizerContext = false
  }).asInstanceOf[ICustomizations]

  val NoScope: js.UndefOr[String] = js.undefined

  /** string, serializable (has toString), dictionary, null, undefined, boolean...*/
  @js.native
  @JSImport("@uifabric/utilities", "css")
  def css(various: js.Any|js.UndefOr[js.Any]|String|js.UndefOr[String]*): String = js.native

}
