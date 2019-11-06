// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package fabric
package styling

import scala.scalajs.js
import js.|
import js.annotation._
import js.Dynamic.{literal => lit}
import ttg.react.vdom._
import js.JSConverters._

@js.native sealed trait InjectionMode extends js.Any
object InjectionMode {

  val none        = 0.asInstanceOf[InjectionMode]
  val insertMode  = 1.asInstanceOf[InjectionMode]
  val appendChild = 2.asInstanceOf[InjectionMode]
}

trait IStylesheetConfig extends js.Object {
  var injectionMode: js.UndefOr[InjectionMode]                       = js.undefined
  var defaultPrefix: js.UndefOr[String]                    = js.undefined
  var onInsertRule: js.UndefOr[js.Function1[String, Unit]] = js.undefined
}

@js.native
trait Stylesheet extends js.Object {
  def getInstance(): Stylesheet                                       = js.native
  def setConfig(config: js.UndefOr[IStylesheetConfig]): Unit          = js.native
  def reset(): Unit                                                   = js.native
  def getRules(): String                                              = js.native
  def argsFromClassName(className: String): js.Array[IStyle]          = js.native
  def insertedRulesFromClassName(className: String): js.Array[String] = js.native
  def insertRule(rule: String): Unit                                  = js.native
}
