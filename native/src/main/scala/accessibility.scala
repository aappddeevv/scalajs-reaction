// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package react
package native

import scala.scalajs.js
import js.|
import js.annotation._

trait AccessibilityPropsAndroid extends js.Object {
}

trait  AccessibilityPropsIOS extends js.Object {
}

trait AccessibilityProps
    extends AccessibilityPropsAndroid
    with AccessibilityPropsIOS {
  var accessible: js.UndefOr[Boolean] = js.undefined
  var accessibilityLabel: js.UndefOr[String] = js.undefined
  // accessibilityRole: AccessibilityRole
  var accessibilityRole: js.UndefOr[String] = js.undefined
  // accessibilityStates: js.Array[AccessibilityState]
  var accessibilityStates: js.UndefOr[js.Array[String]] = js.undefined
  var accessibilityHint: js.UndefOr[String] = js.undefined  
}
