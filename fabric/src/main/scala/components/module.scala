package fabric
package components

import scala.scalajs.js
import js.annotation._ 

import org.scalajs.dom

@js.native
@JSImport("office-ui-fabric-react", "lib/utilities")
object module extends js.Object {
  def setResponsiveMode(m: js.UndefOr[ResponsiveMode] = js.undefined): Unit = js.native
  def initializeResponsiveMode(element: js.UndefOr[dom.html.Element] = js.undefined): Unit = js.native
  def getResponsiveMode(currentwindow: js.UndefOr[dom.Window] = js.undefined): ResponsiveMode = js.native
}
