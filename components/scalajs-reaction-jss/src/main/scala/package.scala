// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react

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

  type JssStyleBase = ttg.react.vdom.StyleAttr | String | js.Dynamic | js.Object
  trait JssStyleArray extends js.Array[JssStyleBase]
  type JssStyle = JssStyleBase | JssStyleArray // not sure about the array part

  /** 
   * Presets I think are the right ones to use. These contrast with the
   * jssPreset provded in mui or the preset from "jss-preset-default".
   */
  def jssPreset(): JssOptions = new JssOptions {
    plugins =
      js.Array(
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
}

