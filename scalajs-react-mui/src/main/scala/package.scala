// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react

import scala.scalajs.js
import js.|

package object mui {

  /** An object that itemizes JssStyles. 
   */
  type StyleSetTag = js.Object

  /** An object that list classnames. Values should be strings but this is not
   * enforced. Objects with this tag are usually annotated with `@js.native`.
   */
  type ClassNamesTag = js.Object

  type RawJssStyle = js.Object // inclcudes literal created n code
  trait JssStyleArray extends js.Array[JssStyle]
  type JssStyle = RawJssStyle | String | JssStyleArray


  /** Presets we think are the right ones to use. */
  def jssPreset(): JssOptions = new JssOptions {
    plugins =
      js.Array(
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

}
