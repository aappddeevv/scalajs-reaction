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

package jss

import scala.scalajs.js

import js.annotation._
@js.native
trait Plugin extends js.Object {
  // bunch of on* methods...
}

trait AddRuleOptions extends js.Object {
  var index: js.UndefOr[Int]        = js.undefined
  var className: js.UndefOr[String] = js.undefined
}

@js.native
trait Rule extends js.Object {
  val className: String    = js.native
  val key: String          = js.native
  val isProcessed: Boolean = js.native
  val options: RuleOptions = js.native
}

@js.native
trait RuleOptions extends js.Object {
  @JSName("jss")
  val _jss: JSS                     = js.native
  val sheet: js.UndefOr[Stylesheet] = js.native
  val index: js.UndefOr[Int]        = js.native
  val selector: js.UndefOr[String]  = js.native
  // more...
  // should make this a trait parameter so can we index our names?
  val classes: js.Dictionary[String] = js.native
}

@js.native
trait Stylesheet extends js.Object {
  def attach(): Unit                                                                    = js.native
  def detach(): Unit                                                                    = js.native
  def deploy(): Unit                                                                    = js.native
  def addRule(name: String, style: JssStyle, options: js.UndefOr[AddRuleOptions]): Rule = js.native
  def addRule(style: JssStyle, options: js.UndefOr[AddRuleOptions]): Rule               = js.native
  def deleteRule(name: String): Unit                                                    = js.native
  def getRule(name: String): js.Dynamic                                                 = js.native
  // more typing needed...
  def addRules(rules: js.Object): Unit = js.native
}

trait StylesheetFactoryOptions extends js.Object {
  var meta: js.UndefOr[String]           = js.undefined
  var index: js.UndefOr[Int]             = js.undefined
  var classNamePrefx: js.UndefOr[String] = js.undefined
  var link: js.UndefOr[Boolean]          = js.undefined
  // more...
}

trait IdOptions extends js.Object {
  var minify: js.UndefOr[Boolean] = js.undefined
}

trait JssOptions extends js.Object {
  var plugins: js.UndefOr[js.Array[Plugin]] = js.undefined
  var virtual: js.UndefOr[String]           = js.undefined
  var insertionPoint: js.UndefOr[String]    = js.undefined
  //var createGenerateId: js.UndefOr[] = js.undefined
  var id: js.UndefOr[IdOptions] = js.undefined
}

@js.native
trait JSS extends js.Object {
  def use(plugin: js.Object*): Unit = js.native
  def createStyleSheet(
    styles: js.UndefOr[RuleTag] = js.undefined,
    options: js.UndefOr[StylesheetFactoryOptions] = js.undefined
  ): Stylesheet                                 = js.native
  def removeStyleSheet(sheet: Stylesheet): Unit = js.native
  def setup(options: JssOptions): Unit          = js.native
}

@js.native
@JSImport("jss-preset-default", JSImport.Default)
object preset extends js.Object {
  def apply(args: js.Object): JssOptions = js.native
}

@js.native
@JSImport("jss", JSImport.Namespace)
private[jss] object module extends JSS {
  @JSName("jss")
  val _jss: JSS                                                  = js.native
  def create(config: js.UndefOr[JssOptions] = js.undefined): JSS = js.native
}

@js.native
@JSImport("jss-plugin-cache", JSImport.Default)
object JssPluginCache extends js.Object {
  def apply(): Plugin = js.native
}

@js.native
@JSImport("jss-plugin-rule-value-function", JSImport.Default)
object JssPluginRuleValueFunction extends js.Object {
  def apply(): Plugin = js.native
}

@js.native
@JSImport("jss-plugin-global", JSImport.Default)
object JssPluginGlobal extends js.Object {
  def apply(): Plugin = js.native
}

@js.native
@JSImport("jss-plugin-extend", JSImport.Default)
object JssPluginExtend extends js.Object {
  def apply(): Plugin = js.native
}

@js.native
@JSImport("jss-plugin-nested", JSImport.Default)
object JssPluginNested extends js.Object {
  def apply(): Plugin = js.native
}

@js.native
@JSImport("jss-plugin-compose", JSImport.Default)
object JssPluginCompose extends js.Object {
  def apply(): Plugin = js.native
}

@js.native
@JSImport("jss-plugin-camel-case", JSImport.Default)
object JssPluginCamelCase extends js.Object {
  def apply(): Plugin = js.native
}

@js.native
@JSImport("jss-plugin-default-unit", JSImport.Default)
object JssPluginDefaultUnit extends js.Object {
  def apply(): Plugin = js.native
}

@js.native
@JSImport("jss-plugin-expand", JSImport.Default)
object JssPluginExpand extends js.Object {
  def apply(): Plugin = js.native
}

@js.native
@JSImport("jss-plugin-vendor-prefixer", JSImport.Default)
object JssPluginVendorPrefixer extends js.Object {
  def apply(): Plugin = js.native
}

@js.native
@JSImport("jss-plugin-props-sort", JSImport.Default)
object JssPluginPropsSort extends js.Object {
  def apply(): Plugin = js.native
}
