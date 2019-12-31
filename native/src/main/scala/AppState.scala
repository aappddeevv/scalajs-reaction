// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package react
package native



import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
sealed trait AppStateValue extends js.Any
object AppStateValue {
  val active = "active".asInstanceOf[AppStateValue]
  val background = "background".asInstanceOf[AppStateValue]
  val inactive = "inactive".asInstanceOf[AppStateValue]
}

@js.native
@JSImport("react-native", "AppState")
object AppState extends js.Object {
  val currentState: AppStateValue = js.native
  // use "change"
  def addEventListener(t: String, cb: js.Function1[AppStateValue, Unit]): Unit = js.native
  def removeEventListener(t: String, cb: js.Function1[AppStateValue, Unit]): Unit = js.native

}
