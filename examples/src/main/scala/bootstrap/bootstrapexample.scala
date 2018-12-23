// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react.examples
package bootstrap

import scala.scalajs.js
import ttg.react
import react._
import elements._
import react.implicits._
import vdom._
import vdom.tags._

//import bootstrap._
//import bootstrap.components._

object BootstrapPage {

  val c = statelessComponent("BootstrapPage")
  import c.ops._

  def apply(
    rootClassName: js.UndefOr[String] = js.undefined
  ) = render { self =>
    div(new DivProps {
      className = rootClassName
    })(
      "bootstrap example"
    )
  }

}
