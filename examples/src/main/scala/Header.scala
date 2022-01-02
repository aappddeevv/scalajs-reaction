/*
 * Copyright (c) 2018 The Trapelo Group
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package ttg
package examples

import scala.scalajs.js
import react._
import react.syntax.*
import react.conversions.given
import vdom._
import fabric._
import fabric.components._

/** Render into a portal to see if it works. */
object Header:

  val hstyle = new StyleAttr:
    lineHeight = "40px"
    backgroundColor = "darkgray"
    color = "white"
    textAlign = "center"
    fontSize = "18px"
    height = 48

  trait Props extends js.Object:
    var rootClassName: js.UndefOr[String] = js.undefined

  val Name = "Header"
  
  def apply(rcn: js.UndefOr[String] = js.undefined) =
    render.elementWith(new Props {
      rootClassName = rcn
    })

  val render: ReactFC[Props] = props => {
    div(new DivProps {
      className = fabric.utilities.css("ttg-Header", props.rootClassName)
      style = hstyle
    })(
      a(new AProps {
        href = "https://github.com/aappddeevv/scalajs-reaction"
        target = "_blank"
      })("scalajs-reaction demo SPA")
    )
  }
  render.displayName(Name)
end Header