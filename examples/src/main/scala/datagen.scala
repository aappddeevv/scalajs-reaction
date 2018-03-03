// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react.examples

import scala.scalajs.js
import js.annotation._

@js.native
trait address extends js.Object {
  def zipCode(): String    = js.native
  def city(): String       = js.native
  def cityPrefix(): String = js.native
  def state(): String      = js.native
  def country(): String    = js.native
}

@js.native
trait name extends js.Object {
  def lastName(): String  = js.native
  def firstName(): String = js.native
}

@js.native
@JSImport("faker", JSImport.Namespace)
object faker extends js.Object {
  val address: address = js.native
  val name: name       = js.native
}
