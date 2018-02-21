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
import vdom.tags._
import elements._
import components._

object HeaderC {

  val header = statelessComponent("Header")

  val hstyle = new StyleAttr {
    lineHeight = "40px"
    backgroundColor = "gray"
    color = "white"
    textAlign = "center"
    fontSize = "18px"
  }

  import header.ops._

  def make(className: String, target: String) =
    header.copy(new methods {
      render = js.defined { self =>
        reactdom.createPortalInElementWithId(
          div(new DivProps {
            className = className
            style = hstyle
          })(
            a(new AProps {
              href = "https://github.com/aappddeevv/scalajs-react"
              target = "_blank"
            })("scalajs-react"),
            " Examples (header via createPortal, inlines styles) ",
            Link(new ILinkProps {
              style = new StyleAttr { marginRight = "10px" }
              onClick = js.defined { _ =>
                router.push("#todo")
              }
            })("Route to ToDos")
          ),
          target
        )
      }
    })
}
