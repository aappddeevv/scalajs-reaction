// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package examples

import scala.scalajs.js
import ttg.react._
import fabric._
import implicits._
import vdom._
import prefix_<^._
import elements._

object HeaderC {

  private val component = statelessComponent("Header")

  val hstyle = new StyleAttr {
    lineHeight = "40px"
    backgroundColor = "gray"
    color = "white"
    textAlign = "center"
    fontSize = "14px"
  }

  def make(className: String, target: String) =
  component.
    withRender{self =>
      reactdom.createPortalInElementWithId(
        <.div(^.className := className, ^.style := hstyle)(
          "scalajs-react Examples (header via createPortal, inlines styles)"
        ),
        target)
    }
}
