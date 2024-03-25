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

package ttg
package examples

import scala.scalajs.js

import js.Dynamic.{ literal => lit }
import js.JSConverters.*
import js.annotation.*
import react.*
import react.syntax.*
import react_redux.*

trait LabelAndChildProps extends js.Object:
  var label: js.UndefOr[String]     = js.undefined
  var className: js.UndefOr[String] = js.undefined

@js.native
@JSImport("JSExamples/LabelAndChild", JSImport.Namespace)
object LabelAndChildNS extends js.Object:
  val LabelAndChild: ReactJSComponent = js.native

object JSAppImports:
  def LabelAndChild(props: LabelAndChildProps)(children: ReactNode*) =
    createElement(LabelAndChildNS.LabelAndChild, props, children*)

trait StoreState extends js.Object {}

trait StoreAction extends Action {}

@js.native
@JSImport("JSExamples/store", JSImport.Namespace)
object StoreNS extends js.Object:
  val store: Store[GlobalAppState, GlobalAppAction] = js.native

/** Maps of actions bundled into the namespace. Use js.Dynamic to find them to
 * make it easier to type into scala :-). Ideally all of these would be typed
 * all the way down. Theses should extend redux's Action...
 */
@js.native
@JSImport("JSExamples/actions", JSImport.Namespace)
object ActionsNS extends js.Object:
  val ViewActions: js.Dynamic           = js.native
  val AddressManagerActions: js.Dynamic = js.native
  val Actions: js.Dynamic               = js.native

@js.native
@JSImport("react-markdown", JSImport.Default)
object ReactMarkdown extends ReactJSComponent

trait ReactMarkdownProps extends js.Object:
  var source: js.UndefOr[String]    = js.undefined
  var className: js.UndefOr[String] = js.undefined

object ReactMarkdownC:
  def make(props: ReactMarkdownProps = noProps()) =
    createElement0(ReactMarkdown, props)

@js.native
@JSImport("react-weather-display", JSImport.Default)
object ReactWeatherDisplayJS extends ReactJSComponent

trait ReactWeatherDisplayProps extends js.Object:
  var width: js.UndefOr[String | Int]  = js.undefined
  var height: js.UndefOr[String | Int] = js.undefined

  /** not used anymore ??? */
  var currentTemperature: js.UndefOr[Float] = js.undefined
  var temperature: js.UndefOr[Float]        = js.undefined
  var currentCondition: js.UndefOr[String]  = js.undefined // ['sunny', 'cloudy', 'rainy', 'stormy', 'snowy']
  var condition: js.UndefOr[String]         = js.undefined // ['sunny', 'cloudy', 'rainy', 'stormy', 'snowy']
  var opacity: js.UndefOr[Double]           = js.undefined
  var isVisible: js.UndefOr[Boolean]        = js.undefined

object ReactWeatherDisplay:
  def apply(props: ReactWeatherDisplayProps) =
    createElement0(ReactWeatherDisplayJS, props)

class PrettyJsonOptions(
  val noColor: js.UndefOr[Boolean] = js.undefined
) extends js.Object

@js.native
@JSImport("prettyjson", JSImport.Namespace)
object PrettyJson extends js.Object:
  def render(data: js.Object | js.Dynamic, options: js.UndefOr[PrettyJsonOptions] = js.undefined): String =
    js.native

@js.native
@JSImport("Examples/README.md", JSImport.Default)
object ReadmeText extends js.Object

@js.native
@JSImport("classnames", JSImport.Default)
object cx extends js.Object:
  def apply(args: String | Number | js.UndefOr[js.Any] | js.Object | js.Dynamic | Null*): String =
    js.native

@js.native
@JSImport("react-content-loader", JSImport.Namespace)
object ReactContentLoader extends js.Object:
  val BulletList: ReactJSComponent = js.native
  val List: ReactJSComponent       = js.native
  val Code: ReactJSComponent       = js.native
  val Instagram: ReactJSComponent  = js.native
  val Facebook: ReactJSComponent   = js.native

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

object ReactContentLoaderComponents:
  def BulletList(props: ReactContentLoaderOptions)(children: ReactNode*) =
    createElement(ReactContentLoader.List, props, children*)
