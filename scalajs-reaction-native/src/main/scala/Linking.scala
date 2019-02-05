// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package native

import react.elements._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

@js.native
@JSImport("react-native", "Linking")
object Linking extends js.Object {
  def openURL(url: String): js.Promise[Unit] = js.native
  def canOpenURL(url: String): js.Promise[Boolean] = js.native
  def getInitialURL(): js.Promise[String|Null] = js.native
  def addEventHandler(url: String, handler: js.Function1[js.Dynamic,Unit]): Unit = js.native
  def removeEventHandler(url: String, andler: js.Function1[js.Dynamic,Unit]): Unit = js.native
}
