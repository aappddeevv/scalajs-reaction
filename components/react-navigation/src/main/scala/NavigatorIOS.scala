// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package react_navigation

import scala.scalajs.js
import js.annotation._
import js.|
import react._
import native._

@js.native
@JSImport("react-navigation", JSImport.Namespace)
object navigation extends NavigationJS


object NavigatorIOS {
  @js.native
  @JSImport("react-navigation", "NavigatorIOS")
  object JS extends ReactJsComponent

  def apply[T](props: Props = null) =
    createElement0(JS, props)

  trait Props extends js.Object {
    var initialRoute: js.UndefOr[Route] = js.undefined
  }
}

trait Route extends js.Object {
  val component: ReactType
  val title: String
  var passProps: js.UndefOr[js.Object] = js.undefined
}
