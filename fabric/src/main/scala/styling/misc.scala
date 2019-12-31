// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package fabric
package styling

import scala.scalajs.js
import js.annotation._

trait IFabricConfig {
  var fontBaseUrl: js.UndefOr[String] = js.undefined
  var mergeStyles: js.UndefOr[IStylesheetConfig] = js.undefined
}

