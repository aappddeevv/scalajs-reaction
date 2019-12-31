// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package react
package native

import scala.scalajs.js
import js.|

package object navigation {

  type RouteSpecType = RouteConfig[js.Object] | ReactJsComponent
  type RouteConfigs = js.Dictionary[RouteSpecType]

  /** Create routes using easier syntax. */
  object routes {
    def apply(pairs: (String, RouteSpecType)*): RouteConfigs =
      js.Dictionary[RouteSpecType](pairs:_*)
  }

  // what's the func arg?
  type NavigationOptions[T <: js.Object] = NavigationOptionsRec[T] | js.Function1[NavProps, NavigationOptionsRec[T]]

}
