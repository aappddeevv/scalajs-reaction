// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package native

import scala.scalajs.js
import js.annotation._

trait AppConfig extends js.Object {
  var appKey: String
  var component: js.UndefOr[ComponentProvider] = js.undefined
  var run: js.UndefOr[Runnable] = js.undefined
}

@js.native
@JSImport("react-native", "AppRegistry")
object AppRegistry extends js.Object {
  def registerConfig(config: js.Array[AppConfig]): Unit = js.native
  def registerComponent(appKey: String, thunk: js.Function0[ReactJsComponent]): String = js.native
  def registerRunnable(appKey: String, func: Runnable): String = js.native
  def registerKeys(): js.Array[String] = js.native
  def unmountAplicationComponentAtRootTag(rootTag: Int): Unit = js.native
  def runApplication(appKey: String, appParameters: js.Any): Unit = js.native
  def registerHeadlessTask(appKey: String, task: js.Any): Unit = js.native
  def getRunnable(appKey: String): js.UndefOr[Runnable] = js.native
}

