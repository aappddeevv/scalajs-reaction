// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package fabric
package utilities

import scala.scalajs.js
import js.annotation._

import react._

/** Really a dict with (string,js.Any). Extend as needed with your setting keys.
 */
@js.native
trait Settings extends js.Object {
}

/** What you get if you use: `fields=["styles", "theme"]`.
 */
@js.native
trait StandardSettings extends js.Object {
  val styles: js.Object = js.native
  val theme: ITheme = js.native
}

@js.native
trait ICustomizations extends js.Object {
  def settings[T <: Settings]: T = js.native
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
 
