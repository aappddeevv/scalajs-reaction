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
