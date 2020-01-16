// Copyright (c) 2019 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package react

import scala.scalajs.js
import js.annotation._

@js.native
@JSGlobal
class WeakMap extends js.Object {
  // returned undefind if not present
  def get(k: js.Object): js.UndefOr[js.Any] = js.native
  def set(k: js.Object, v: js.Any): js.Any = js.native
  def has(k: js.Object): Boolean = js.native
  def delete(k: js.Object): Unit = js.native
}
