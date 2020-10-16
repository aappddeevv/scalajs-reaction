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

import scala.scalajs.js

import js.|

package object jss {

  /** An object that itemizes JssStyles. You can create this as a dictionary or as
   * a scala.js defined js object. The idea is that there is a set of string =>
   * JssStyle. scala.js cannot enforce RuleTag to have specific types on its
   * properties. The name could be an element name like button or a component
   * name such as root. You define your specific styles in an object that
   * inherits from this tag.
   */
  type RuleTag = js.Object

  /** An object that list classnames. Values should be strings but this is not
   * enforced with types. Objects with this tag are usually annotated with
   * `@js.native`.  This is output of some function that creates the styles
   * indexed by a string name used as a class name in react components e.g.
   * Stylesheet.addRule.
   */
  type ClassNamesTag = js.Object

  type JssStyleBase = react.vdom.StyleAttr | String | js.Dynamic | js.Object
  trait JssStyleArray extends js.Array[JssStyleBase]
  type JssStyle = JssStyleBase | JssStyleArray // not sure about the array part

  /**
   * Presets I think are the right ones to use. These contrast with the
   * jssPreset provded in mui or the preset from "jss-preset-default".
   */
  def jssPreset(): JssOptions = new JssOptions {
    plugins = js.Array(
      //JssPluginCache(), // cache causes a crash! must be version conflict
      JssPluginRuleValueFunction(),
      JssPluginGlobal(),
      JssPluginExtend(),
      JssPluginNested(),
      JssPluginCompose(),
      JssPluginCamelCase(),
      JssPluginDefaultUnit(),
      JssPluginExpand(),
      JssPluginVendorPrefixer(),
      JssPluginPropsSort()
    )
    //js.Array(
    //   JssPluginExpand(),
    //   JssPluginCompose()
    // ).concat(muiJssPreset().plugins.getOrElse(js.Array()))
  }

  /** link = true StylesheetOptions so you can updated/remove a rule once added to
   * a Stylesheet.
   */
  val linkTrue = new StylesheetFactoryOptions {
    link = true
  }

  val _jss = module._jss
  def create(config: js.UndefOr[JssOptions] = js.undefined) = module.create(config)
}
