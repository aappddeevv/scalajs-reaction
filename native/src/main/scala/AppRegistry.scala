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
  def registerComponent(appKey: String, thunk: js.Function0[ReactJSComponent]): String = js.native
  def registerRunnable(appKey: String, func: Runnable): String = js.native
  def registerKeys(): js.Array[String] = js.native
  def unmountAplicationComponentAtRootTag(rootTag: Int): Unit = js.native
  def runApplication(appKey: String, appParameters: js.Any): Unit = js.native
  def registerHeadlessTask(appKey: String, task: js.Any): Unit = js.native
  def getRunnable(appKey: String): js.UndefOr[Runnable] = js.native
  def getAppKeys(): js.Array[String] = js.native
}
