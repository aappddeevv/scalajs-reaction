package react_content_loader

import scala.scalajs.js
import js.|
import js.annotation._

import react._
import react.implicits._
import org.scalajs.dom
import react.vdom._

//
@js.native
@JSImport("react-content-loader", JSImport.Namespace)
object module extends js.Object {}

object ContentLoader {
  @js.native
  @JSImport("react-content-loader", "ContentLoader")
  object JS extends ReactJSComponent

  trait Props extends SVGAttributes[dom.svg.Element] {
    var key: js.UndefOr[String] = js.undefined
    var animate: js.UndefOr[Boolean] = js.undefined
    // var backgroundColor: js.UndefOr[String] = js.undefined
    // var backgroundOpacity: js.UndefOr[Int] = js.undefined
    var baseUrl: js.UndefOr[String] = js.undefined
    var foregroundColor: js.UndefOr[String] = js.undefined
    var foregroundOpacity: js.UndefOr[Int] = js.undefined
    var gradientRatio: js.UndefOr[Int] = js.undefined
    var interval: js.UndefOr[Int] = js.undefined
    var rtl: js.UndefOr[Boolean] = js.undefined
    //var speed: js.UndefOr[Float] = js.undefined
    var title: js.UndefOr[String] = js.undefined
    var uniqueKey: js.UndefOr[String] = js.undefined
  }

  /** Should only be SVG children. */
  def apply(props: Props)(children: ReactNode*) =
    createElementN(JS, props)(children: _*)

  object Facebook {
    @js.native
    @JSImport("react-content-loader", "Facebook")
    object JS extends ReactJSComponent
    def apply(props: Props)(children: ReactNode*) =
      createElementN(JS, props)(children: _*)
  }
  object Instagrcam {
    @js.native
    @JSImport("react-content-loader", "Instagram")
    object JS extends ReactJSComponent
    def apply(props: Props)(children: ReactNode*) =
      createElementN(JS, props)(children: _*)
  }
  object Code {
    @js.native
    @JSImport("react-content-loader", "Code")
    object JS extends ReactJSComponent
    def apply(props: Props)(children: ReactNode*) =
      createElementN(JS, props)(children: _*)
  }
  object List {
    @js.native
    @JSImport("react-content-loader", "List")
    object JS extends ReactJSComponent
    def apply(props: Props)(children: ReactNode*) =
      createElementN(JS, props)(children: _*)
  }
  object BulletListStyle {
    @js.native
    @JSImport("react-content-loader", "BulletListStyle")
    object JS extends ReactJSComponent
    def apply(props: Props)(children: ReactNode*) =
      createElementN(JS, props)(children: _*)
  }
}
