// Copyright (c) 2019 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package uifabric

import scala.scalajs.js
import js.annotation._

/** Import through uifabric/utilities directly. */
@js.native
@JSImport("@uifabric/utilities", JSImport.Namespace)
object utilities extends fabric.uifabric_utilities_module

@js.native
@JSImport("@uifabric/merge-styles", JSImport.Namespace)
object merge_styles extends fabric.styling.MergeStyles
