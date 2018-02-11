// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react.examples

import scala.scalajs.js
import js.|
import js.annotation._

class PrettyJsonOptions(
  val noColor: js.UndefOr[Boolean] = js.undefined
) extends js.Object

@js.native
@JSImport("prettyjson", JSImport.Namespace)
object PrettyJson extends js.Object {
  def render(data: js.Object | js.Dynamic, options: js.UndefOr[PrettyJsonOptions] = js.undefined): String =
    js.native
}
