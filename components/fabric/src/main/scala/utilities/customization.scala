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
trait CustomizerContext extends js.Object {
  val customizations: ICustomizations = js.native
}

/** In js, this is a class with all static methods */
@js.native
@JSImport("@uifabric/utilities/lib/customizations/Customizations", "Customizations")
class Customizations() extends js.Object

@js.native
@JSImport("@uifabric/utilities/lib/customizations/Customizations", "Customizations")
object Customizations extends js.Object {
  def reset(): Unit = js.native
  // static!, T is based on fields so write your own object
  def getSettings[T <: js.Object](
    fields: js.Array[String],
    scopeName: js.UndefOr[String] = js.undefined,
    localSettings: js.UndefOr[ICustomizations] = js.undefined
  ): T = js.native
  def applyScopedSettinsg(scopeName: String, settings: Settings): Unit = js.native
  def applyBatchedUpdates(code: js.Function0[Unit], suppressUpdates: js.UndefOr[Boolean]=js.undefined): Unit = js.native
  def applySettings(settings: Settings): Unit = js.native
  def observe(cb: js.Function0[Unit]): Unit = js.native
  def unobserve(cb: js.Function0[Unit]): Unit = js.native


}

@js.native
@JSImport("@uifabric/utilities/lib/customizations/CustomizerContext", "CustomizerContext")
object CustomizerContext extends ReactContext[CustomizerContext]
