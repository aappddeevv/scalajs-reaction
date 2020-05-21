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

package react_device_detect

import scala.scalajs.js
import js.|
import js.annotation._

import react._

@js.native
@JSImport("react-device-detect", JSImport.Namespace)
object module extends js.Object {
  val isMobile: Boolean = js.native
  val isMobileOnly: Boolean = js.native
  val isTablet: Boolean = js.native
  val isBrowser: Boolean = js.native
  val isSmartTV: Boolean = js.native
  val isWearable: Boolean = js.native
  val isConsole: Boolean = js.native
  val isAndroid: Boolean = js.native
  val isWinPhone: Boolean = js.native
  val isIOS: Boolean = js.native
  val isChrome: Boolean = js.native
  val isFirefox: Boolean = js.native
  val isSafari: Boolean = js.native
  val isOpera: Boolean = js.native
  val isIE: Boolean = js.native
  val isYandex: Boolean = js.native
  val isChromium: Boolean = js.native
  val isMobileSafari: Boolean = js.native
  val osVersion: String = js.native

  val osName: String = js.native
  val fullBrowserVersion: String = js.native
  val browserVersion: String = js.native
  val browserName: String = js.native
  val mobileVendor: String = js.native
  val mobileModel: String = js.native
  val engineName: String = js.native
  val engineVersion: String = js.native
  val getUA: String = js.native
  val deviceType: String = js.native

  val isIOS13: Boolean = js.native

  val isIPhone13: Boolean = js.native

  val isIPad13: Boolean = js.native

  val isIPod13: Boolean = js.native

  val isElectron: Boolean = js.native

  def deviceDetect(): Unit = js.native
  def withOrientationChange(component: ReactComponentType): ReactType = js.native

}

trait Props extends js.Object {
  var viewClassName: js.UndefOr[String] = js.undefined
  var style: js.UndefOr[js.Object] = js.undefined
  var renderWithFragment: js.UndefOr[Boolean] = js.undefined
}
