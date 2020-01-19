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
package utilities

import scala.scalajs.js

import js.annotation._

import react._

/** Really a dict with (string,js.Any). Extend as needed with your setting keys.
 */
@js.native
trait Settings extends js.Object {}

/** What you get if you use: `fields=["styles", "theme"]`.
 */
@js.native
trait StandardSettings extends js.Object {
  val styles: js.Object = js.native
  val theme: ITheme     = js.native
}

@js.native
trait ICustomizations extends js.Object {
  def settings[T <: Settings]: T               = js.native
  val scopedSettings: js.Dictionary[js.Object] = js.native
  var inCustomizerContext: js.UndefOr[Boolean] = js.native
}

@js.native
trait ICustomizerContext extends js.Object {
  val customizations: ICustomizations = js.native
}

/** In js, this is a class with all static methods */
@js.native
@JSImport("@uifabric/utilities/lib/customizations/Customizations", "Customizations")
object Customizations extends js.Object {
  // static!, T is based on fields so write your own object
  def getSettings[T <: js.Object](
    fields: js.Array[String],
    scopeName: js.UndefOr[String] = js.undefined,
    localSettings: js.UndefOr[ICustomizations] = js.undefined
  ): T = js.native
  // static!
  def observe(cb: js.Function0[Unit]): Unit = js.native
}

@js.native
@JSImport("@uifabric/utilities/lib/customizations/CustomizerContext", "CustomizerContext")
object CustomizerContext extends ReactContext[ICustomizerContext] {}
