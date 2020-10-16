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

package react_responsive

import scala.scalajs.js
import js.|
import js.annotation._

import react._

trait HasQuery extends js.Object {
  var query: js.UndefOr[String] = js.undefined
}

trait MediaQueryFeatures extends js.Object {
  var minAspectRatio: js.UndefOr[String] = js.undefined
  var maxAspectRatio: js.UndefOr[String] = js.undefined
  var minDeviceAspectRatio: js.UndefOr[String] = js.undefined
  var maxDeviceAspectRatio: js.UndefOr[String] = js.undefined
  var minHeight: js.UndefOr[String | Int] = js.undefined
  var maxHeight: js.UndefOr[String | Int] = js.undefined
  var minDeviceHeight: js.UndefOr[String | Int] = js.undefined
  var maxDeviceHeight: js.UndefOr[String | Int] = js.undefined
  var minWidth: js.UndefOr[String | Int] = js.undefined
  var maxWidth: js.UndefOr[String | Int] = js.undefined
  var minDeviceWidth: js.UndefOr[String | Int] = js.undefined
  var maxDeviceWidth: js.UndefOr[String | Int] = js.undefined
  var minColor: js.UndefOr[String] = js.undefined
  var maxColor: js.UndefOr[String] = js.undefined
  var minColorIndex: js.UndefOr[String] = js.undefined
  var maxColorIndex: js.UndefOr[String] = js.undefined
  var minResolution: js.UndefOr[String | Int] = js.undefined
  var maxResolution: js.UndefOr[String | Int] = js.undefined
}

trait MediaQueryTypes extends js.Object {
  var all: js.UndefOr[Boolean] = js.undefined
  var grid: js.UndefOr[Boolean] = js.undefined
  var aural: js.UndefOr[Boolean] = js.undefined
  var braille: js.UndefOr[Boolean] = js.undefined
  var handheld: js.UndefOr[Boolean] = js.undefined
  var print: js.UndefOr[Boolean] = js.undefined
  var projection: js.UndefOr[Boolean] = js.undefined
  var screen: js.UndefOr[Boolean] = js.undefined
  var tty: js.UndefOr[Boolean] = js.undefined
  var tv: js.UndefOr[Boolean] = js.undefined
  var embossed: js.UndefOr[Boolean] = js.undefined
}

trait MediaQueryMatchers extends js.Object {
  var aspectRatio: js.UndefOr[String] = js.undefined
  var deviceAspectRation: js.UndefOr[String] = js.undefined
  var height: js.UndefOr[String | Int] = js.undefined
  var deviceHeight: js.UndefOr[String | Int] = js.undefined
  var width: js.UndefOr[String | Int] = js.undefined
  var deviceWidth: js.UndefOr[String | Int] = js.undefined
  var color: js.UndefOr[Boolean] = js.undefined
  var colorIndex: js.UndefOr[Boolean] = js.undefined
  var monochrome: js.UndefOr[Boolean] = js.undefined
  var resolution: js.UndefOr[String | Int] = js.undefined
  var orientation: js.UndefOr[String] = js.undefined
  var scan: js.UndefOr[String] = js.undefined
  var `type`: js.UndefOr[MediaQueryTypes] = js.undefined
}

trait MediaQueryAllQueryable extends MediaQueryFeatures with MediaQueryTypes

@js.native
@JSImport("react-responsive", JSImport.Namespace)
object module extends js.Object {
  def toQuery(matchers: js.UndefOr[MediaQueryAllQueryable] = js.undefined): String = js.native
  val Context: ReactContext[MediaQueryAllQueryable] = js.native
  def useMediaQuery(
    settings: MediaQueryAllQueryable with HasQuery,
    device: js.UndefOr[MediaQueryMatchers] = js.undefined,
    cb: js.UndefOr[js.Function1[Boolean, Unit]] = js.undefined): Boolean = js.native
}

object MediaQuery {
  @js.native
  @JSImport("react-responsive", "MediaQuery")
  object JS extends ReactJSComponent

  trait Props extends HasQuery with MediaQueryAllQueryable {
    var component: js.UndefOr[ReactType] = js.undefined
    var style: js.UndefOr[js.Object] = js.undefined
    var className: js.UndefOr[String] = js.undefined
    var device: js.UndefOr[MediaQueryMatchers] = js.undefined
    var values: js.UndefOr[MediaQueryMatchers] = js.undefined
    var onBeforeChange: js.UndefOr[js.Function1[Boolean, Unit]] = js.undefined
    var onChange: js.UndefOr[js.Function1[Boolean, Unit]] = js.undefined
  }

  def apply(props: Props)(children: ReactNode*) =
    createElement(JS, props, children: _*)
}
