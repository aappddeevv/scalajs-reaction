package fabric
package components

import scala.scalajs.js
import js.|
import js.annotation._
import org.scalajs.dom
import react._
import react.implicits._
import fabric.styling._
import vdom._

object Rating {

  @js.native
  abstract trait Size extends js.Any
  object Size {
    val Large = 1.asInstanceOf[Size]
    val Small = 0.asInstanceOf[Size]
  }

  @js.native
  trait IRating extends js.Object

  @js.native
  @JSImport("office-ui-fabric-react/lib", "Rating")
  object JS extends ReactJSComponent

  def apply(props: Props) = createElement(JS, props)

  trait Props extends ComponentRef[IRating] with AllHTMLAttributes[dom.html.Element] {
    var rating: js.UndefOr[Double] = js.undefined
    //var min: js.UndefOr[Double] = js.undefined
    //var max: js.UndefOr[Double] = js.undefined
    var allowZeroStars: js.UndefOr[Boolean] = js.undefined
    var icon: js.UndefOr[String] = js.undefined
    var unselectedIcon: js.UndefOr[String] = js.undefined
    @JSName("size")
    var ratingSize: js.UndefOr[Size] = js.undefined
    var onChange: js.UndefOr[js.Function2[ReactFocusEvent[dom.html.Element], Double, Unit]] = js.undefined
    var ariaLabelFormat: js.UndefOr[String] = js.undefined
    var ariaLabelId: js.UndefOr[String] = js.undefined
    var getAriaLabel: js.UndefOr[js.Function2[Double, Double, String]] = js.undefined
    //var readOnly: js.UndefOr[Boolean] = js.undefined
    //var className: js.UndefOr[String] = js.undefined
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
  }

  trait StyleProps extends js.Object {
    var disabled: js.UndefOr[Boolean] = js.undefined
    var readOnly: js.UndefOr[Boolean] = js.undefined
  }

  @deriveClassNames trait Styles extends IStyleSetTag {
    var root: js.UndefOr[IStyle] = js.undefined
    // lots more
  }
}
