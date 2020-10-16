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
package styling

// from @uifabric/merge-styles

import scala.scalajs.js

@js.native
sealed trait InjectionMode extends js.Any
object InjectionMode {
  val none = 0.asInstanceOf[InjectionMode]
  val insertMode = 1.asInstanceOf[InjectionMode]
  val appendChild = 2.asInstanceOf[InjectionMode]
}

trait IStylesheetConfig extends js.Object {
  var injectionMode: js.UndefOr[InjectionMode] = js.undefined
  var defaultPrefix: js.UndefOr[String] = js.undefined
  var onInsertRule: js.UndefOr[js.Function1[String, Unit]] = js.undefined
}

@js.native
trait Stylesheet extends js.Object {
  def getInstance(): Stylesheet = js.native
  def setConfig(config: js.UndefOr[IStylesheetConfig]): Unit = js.native
  def reset(): Unit = js.native
  def resetKeys(): Unit = js.native
  def getRules(): String = js.native
  def argsFromClassName(className: String): js.Array[IStyle] = js.native
  def insertedRulesFromClassName(className: String): js.Array[String] = js.native
  def insertRule(rule: String): Unit = js.native
  def getClassName(displayName: js.UndefOr[String]): String = js.native
  def onReset(callback: js.Function0[Unit]): Unit = js.native
}
