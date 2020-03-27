package fabric

import scala.scalajs.js
import fabric.styling.IIconOptions

package object icons {
  def initializeIcons(baseUrl: js.UndefOr[String] = js.undefined, options: js.UndefOr[IIconOptions] = js.undefined) =
    fabric.icons.module.initializeIcons()
}
