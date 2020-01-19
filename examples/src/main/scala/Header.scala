
package ttg
package examples

import scala.scalajs.js
import _root_.react._
import fabric._
import fabric.components._
import implicits._
import vdom._
import vdom.tags._

/** Render into a portal to see if it works. */
object Header {

  val hstyle = new StyleAttr {
    lineHeight = "40px"
    backgroundColor = "darkgray"
    color = "white"
    textAlign = "center"
    fontSize = "18px"
    height = 48
  }

  trait Props extends js.Object {
    var rootClassName: js.UndefOr[String] = js.undefined
  }

  val Name = "Header"
  def apply(rcn: js.UndefOr[String] = js.undefined) =
    sfc(new Props {
      rootClassName = rcn
    })

  val sfc = SFC1[Props] { props =>
    React.useDebugValue(Name)
    div(new DivProps {
      className = props.rootClassName
      style = hstyle
    })(
      a(new AProps {
        href = "https://github.com/aappddeevv/scalajs-reaction"
        target = "_blank"
      })("scalajs-reaction demo SPA"),
    )
  }
}
