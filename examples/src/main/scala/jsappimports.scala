// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
package examples

import scala.scalajs.js
import js.|
import js.annotation._
import js.JSConverters._
import js.Dynamic.{literal => lit}

import ttg.react._
import ttg.react.implicits._
import ttg.react.redux
import elements._

trait LabelAndChildProps extends js.Object {
  var label: js.UndefOr[String]     = js.undefined
  var className: js.UndefOr[String] = js.undefined
}

@js.native
@JSImport("JSExamples/LabelAndChild", JSImport.Namespace)
object LabelAndChildNS extends js.Object {
  val LabelAndChild: ReactJsComponent = js.native
}

object JSAppImports {
  def LabelAndChild(props: LabelAndChildProps)(children: ReactNode*) =
    React.createElement(LabelAndChildNS.LabelAndChild, props)(children: _*)
}
@js.native
@JSImport("JSExamples/store", JSImport.Namespace)
object StoreNS extends js.Object {
  val store: redux.Store = js.native
}

@js.native
@JSImport("JSExamples/actions", JSImport.Namespace)
object ActionsNS extends js.Object {
  val ViewActions: js.Dynamic           = js.native
  val AddressManagerActions: js.Dynamic = js.native
  val Actions: js.Dynamic               = js.native
}
@js.native
@JSImport("react-markdown", JSImport.Default)
object ReactMarkdown extends ReactJsComponent

trait ReactMarkdownProps extends js.Object {
  var source: js.UndefOr[String]    = js.undefined
  var className: js.UndefOr[String] = js.undefined
}

object ReactMarkdownC {
  def make(props: ReactMarkdownProps = noProps()) =
    React.createElement0(ReactMarkdown, props)
}

@js.native
@JSImport("react-weather-display", JSImport.Default)
object ReactWeatherDisplayJS extends ReactJsComponent

trait ReactWeatherDisplayProps extends js.Object {
  var width: js.UndefOr[String | Int]  = js.undefined
  var height: js.UndefOr[String | Int] = js.undefined

  /** not used anymore ??? */
  var currentTemperature: js.UndefOr[Float] = js.undefined
  var temperature: js.UndefOr[Float]        = js.undefined
  var currentCondition
    : js.UndefOr[String] = js.undefined // ['sunny', 'cloudy', 'rainy', 'stormy', 'snowy']
  var condition
    : js.UndefOr[String]             = js.undefined // ['sunny', 'cloudy', 'rainy', 'stormy', 'snowy']
  var opacity: js.UndefOr[Double]    = js.undefined
  var isVisible: js.UndefOr[Boolean] = js.undefined
}

object ReactWeatherDisplay {
  def apply(props: ReactWeatherDisplayProps = null) =
    React.createElement0(ReactWeatherDisplayJS, props)
}

class PrettyJsonOptions(
    val noColor: js.UndefOr[Boolean] = js.undefined
) extends js.Object

@js.native
@JSImport("prettyjson", JSImport.Namespace)
object PrettyJson extends js.Object {
  def render(
      data: js.Object | js.Dynamic,
      options: js.UndefOr[PrettyJsonOptions] = js.undefined): String =
    js.native
}

@js.native
@JSImport("Examples/README.md", JSImport.Default)
object ReadmeText extends js.Object

@js.native
@JSImport("classnames", JSImport.Default)
object cx extends js.Object {
  def apply(args: String | Number | js.UndefOr[js.Any] | js.Object | js.Dynamic | Null*): String =
    js.native
}

@js.native
@JSImport("react-content-loader", JSImport.Namespace)
object ReactContentLoader extends js.Object {
  val BulletList: ReactJsComponent = js.native
  val List: ReactJsComponent       = js.native
  val Code: ReactJsComponent       = js.native
  val Instagram: ReactJsComponent  = js.native
  val Facebook: ReactJsComponent   = js.native
}

class ReactContentLoaderOptions(
    val animate: js.UndefOr[Boolean] = js.undefined,
    val speed: js.UndefOr[Int] = js.undefined,
    val className: js.UndefOr[String] = js.undefined,
    val width: js.UndefOr[Double] = js.undefined,
    val height: js.UndefOr[Double] = js.undefined,
    val preserveAspectRatio: js.UndefOr[String] = js.undefined,
    val primaryColor: js.UndefOr[String] = js.undefined,
    val secondaryColor: js.UndefOr[String] = js.undefined,
    val style: js.UndefOr[js.Object | js.Dynamic] = js.undefined,
    val uniquekey: js.UndefOr[String] = js.undefined
) extends js.Object

object ReactContentLoaderComponents {
  def BulletList(props: ReactContentLoaderOptions = null)(children: ReactNode*) =
    React.createElement(ReactContentLoader.List, props)(children: _*)
}
