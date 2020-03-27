package fabric
package icons

import scala.scalajs.js
import js.annotation._

import fabric.styling.IIconOptions

@js.native
private[fabric] trait module_icons extends js.Object {
  def initializeIcons(
      baseUrl: js.UndefOr[String] = js.undefined,
      options: js.UndefOr[IIconOptions] = js.undefined
    ): Unit = js.native
}

@js.native
@JSImport("@uifabric/icons", JSImport.Namespace)
object module extends module_icons
