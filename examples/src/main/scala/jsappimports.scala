// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react.examples

import scala.scalajs.js
import js.annotation._
import js.JSConverters._
import js.Dynamic.{literal => lit}

import ttg.react._
import ttg.react.implicits._
import ttg.react.redux

@js.native
@JSImport("JSExamples/store", JSImport.Namespace)
object StoreNS extends js.Object {
  val store: redux.Store = js.native
}

@js.native
@JSImport("JSExamples/actions", JSImport.Namespace)
object ActionsNS extends js.Object {
  val ViewActions: js.Dynamic = js.native
  val AddressManagerActions: js.Dynamic = js.native
  val Actions: js.Dynamic = js.native
}
