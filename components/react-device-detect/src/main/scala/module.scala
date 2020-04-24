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
