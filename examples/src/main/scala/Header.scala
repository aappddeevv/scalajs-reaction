// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package examples

import scala.scalajs.js
import ttg.react._
import fabric._
import fabric.components._
import implicits._
import vdom._
import vdom.tags._
import elements._

/** Render into a portal to see if it works. */
object Header {

  val hstyle = new StyleAttr {
    lineHeight = "40px"
    backgroundColor = "darkgray"
    color = "white"
    textAlign = "center"
    fontSize = "18px"
    height = 48
  }

  val Name = "Header"
  val c = statelessComponent(Name)
  import c.ops._

  def apply(
    rootClassName: js.UndefOr[String] = js.undefined
  ) =
    c.copy(new methods {
      val render = self => {
        div(new DivProps {
          className = rootClassName
          style = hstyle
        })(
          a(new AProps {
            href = "https://github.com/aappddeevv/scalajs-reaction"
            target = "_blank"
          })("scalajs-reaction demo SPA"),
        )
      }
    })
}
