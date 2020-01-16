// Copyright (c) 2019 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package fabric
package components

import scala.scalajs.js
import js.annotation._
import react._

object Text {

  @js.native
  @JSImport("office-ui-fabric-react", "lib/Text")
  object JS extends ReactJsComponent

  def apply(props: Props)(text: js.UndefOr[String] = js.undefined) =
    createElement(JS, props)(text.asInstanceOf[ReactNode])

  // from ReactJsProps
  trait Props extends js.Object {
    var variant: js.UndefOr[Variant] = js.undefined
    var nowrap: js.UndefOr[Boolean] = js.undefined
    var block: js.UndefOr[Boolean] = js.undefined
  }

  @js.native
  abstract trait Variant extends js.Any
  object Variant {
    val large = "large".asInstanceOf[Variant]
    val medium = "medium".asInstanceOf[Variant]
    val mediumPlus = "mediumPlus".asInstanceOf[Variant]
    val mega = "mega".asInstanceOf[Variant]
    val small = "small".asInstanceOf[Variant]
    val smallPlus = "smallPlus".asInstanceOf[Variant]
    val superLarge = "superLarge".asInstanceOf[Variant]
    val tiny = "tiny".asInstanceOf[Variant]
    val xLarge = "xLarge".asInstanceOf[Variant]
    val xLargePlus = "xLargePlus".asInstanceOf[Variant]
    val xSmall = "xSmall".asInstanceOf[Variant]
    val xxLarge = "xxLarge".asInstanceOf[Variant]
    val xxLargePlus = "xxLargePlus".asInstanceOf[Variant]
  }

}
