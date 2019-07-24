// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package native

import react.elements._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

object Picker {

  @js.native
  @JSImport("react-native", "Picker")
  object JS extends ReactJsComponent {
    val Item: ReactJsComponent = js.native
  }

  def apply[T](props: Props[T] = null)(children: ReactNode*) =
    React.createElement(JS, props)(children:_*)

  trait IOS extends View.Props {
    var itemStyle: js.UndefOr[ViewStyle] = js.undefined
  }

  trait Android extends View.Props {
    var enabled: js.UndefOr[Boolean] = js.undefined
    var mode: js.UndefOr[Mode] = js.undefined
    var prompt: js.UndefOr[String] = js.undefined
  }


  trait Props[T] extends Android with IOS {
    var onValueChange: js.UndefOr[js.Function2[T, Int, Unit]] = js.undefined
    var selectedValue: js.UndefOr[T] = js.undefined
    //val style: js.UndefOr[StyleProp[ViewStyle]] = js.undefined
    //var testID: js.UndefOr[String] = js.undefined
  }

  // values can only be this...
  type ValueType = String | Int

  @js.native
  sealed trait Mode extends js.Any
  object Mode {
    val dialog = "dialog".asInstanceOf[Mode]
    val dropdown  = "dropdown".asInstanceOf[Mode]
  }

  object Item {
    def apply[T](props: Props[T] = null) = React.createElement0(JS.Item, props)

    trait Props[T] extends js.Object {
      val label: String
      val value: T
    }
  }

}
