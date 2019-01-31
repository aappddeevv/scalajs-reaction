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

trait ContentOffset extends js.Object {
  var x: Int
  var y: Int
}

trait ContentOffsetEvent extends js.Object {
  var contentOffset: ContentOffset
}

trait Selection extends js.Object {
  var start: Int
  var end: Int
}

object TextInput {
  @js.native
  @JSImport("react-native", "TextInput")
  object JS extends ReactJsComponent

  def apply(props: Props = null)(children: ReactNode*) =
    wrapJsForScala(JS, props, children:_*)

  trait Props extends js.Object {
  }
}
