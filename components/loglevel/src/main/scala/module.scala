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

// Copyright (c) 2019 The Trapelo Group LLC - All Rights Reserved
// Unauthorized copying of this file, via any medium is strictly prohibited.
// Proprietary and confidential.

package loglevel

import scala.scalajs.js
import js.|
import js.annotation._

@js.native
abstract trait LogLevel extends js.Any
object LogLevel {
  val trace = 0.asInstanceOf[LogLevel]
  val debug = 1.asInstanceOf[LogLevel]
  val info = 2.asInstanceOf[LogLevel]
  val warn = 3.asInstanceOf[LogLevel]
  val error = 4.asInstanceOf[LogLevel]
  val silent = 5.asInstanceOf[LogLevel]
}

@js.native
trait Logger extends js.Object {
  val levels: LogLevel = js.native
  val methodFactory: MethodFactory = js.native
  def trace(msg: js.Any*): Unit = js.native
  def debug(msg: scala.Any*): Unit = js.native
  def info(msg: js.Any*): Unit = js.native
  def error(msg: scala.Any*): Unit = js.native
  def setLevel(desc: LogLevel | String | Int, persist: js.UndefOr[Boolean] = js.undefined): Unit = js.native
  def getLevel(): LogLevel = js.native
  def setDefaultLevel(level: LogLevel | String | Int): Unit = js.native
  def enableAll(persist: js.UndefOr[Boolean]): Unit = js.native
  def disableAll(): Unit = js.native
}

@js.native
trait RootLogger extends Logger {
  def noConflict(): js.Any = js.native
  def getLogger(name: String): Logger = js.native
  def getLoggers(): js.Dictionary[Logger] = js.native
}

@js.native
@JSImport("loglevel", JSImport.Default)
object module extends RootLogger
