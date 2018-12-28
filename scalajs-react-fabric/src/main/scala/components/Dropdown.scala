// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
package fabric
package components

import scala.scalajs.js
import js.annotation._
import js.|
import org.scalajs.dom

import vdom._
import fabric.styling._

object Dropdown {
  import ttg.react.elements.wrapJsForScala

  @js.native
  @JSImport("office-ui-fabric-react/lib/Dropdown", "Dropdown")
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    wrapJsForScala(JS, props, children: _*)

  @js.native
  trait IDropdown extends js.Object {

    /** Should focus on open? */
    var focus: js.Function1[Boolean, Unit]
  }
  @js.native
  abstract sealed trait ResponsiveMode extends js.Any

  object ResponsiveMode {
    val small    = 0.asInstanceOf[ResponsiveMode]
    val medium   = 1.asInstanceOf[ResponsiveMode]
    val large    = 2.asInstanceOf[ResponsiveMode]
    val xLarge   = 3.asInstanceOf[ResponsiveMode]
    val xxLarge  = 4.asInstanceOf[ResponsiveMode]
    val xxxLarge = 5.asInstanceOf[ResponsiveMode]
  }

  trait Props extends ISelectableDroppableTextProps[dom.html.Div] {
    var placeholder: js.UndefOr[String] = js.undefined
    //var onChanged: js.UndefOr[js.Function1[IDropdownOption, Unit] | js.Function2[IDropdownOption, Int, Unit]] = js.undefined
    var onChanged: js.UndefOr[js.Function2[IDropdownOption, js.UndefOr[Int], Unit]] = js.undefined
    var onDismiss: js.UndefOr[js.Function0[Unit]]                                   = js.undefined
    var onRenderPlaceholder: js.UndefOr[IRenderFunction[Props]]            = js.undefined
    var onRenderTitle
        : js.UndefOr[IRenderFunction[IDropdownOption] | IRenderFunction[js.Array[IDropdownOption]]] =
      js.undefined
    var onRenderCaretDown: js.UndefOr[IRenderFunction[IDropdownOption]] = js.undefined
    var dropdownWidth: js.UndefOr[Int]                                  = js.undefined

    /** Responsive mode */
    var responsiveMode: js.UndefOr[ResponsiveMode]                                   = js.undefined
    var multiselect: js.UndefOr[Boolean]                                  = js.undefined
    var defaultSelectedKeys: js.UndefOr[js.Array[String] | js.Array[Int]] = js.undefined
    var selectedKeys: js.UndefOr[js.Array[String] | js.Array[Int]]        = js.undefined
    var multiSelectDelimiter: js.UndefOr[String]                          = js.undefined
    var isDisabled: js.UndefOr[Boolean]                                   = js.undefined
  }


}

