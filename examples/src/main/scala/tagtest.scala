// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package examples

import scala.scalajs.js
import js.Dynamic.{literal => lit}
import elements._
import implicits._
import vdom.StyleAttr
import vdom.SetInnerHTML
import vdom.tags._
import vdom.svgtags._

object TagTestC {
  val c = statelessComponent("TagTest")
  import c.ops._

  def make() =
    c.copy(new methods {
      render = js.defined {
        self =>
          div(new DivProps {
            style = new StyleAttr {
              display = "flex"
              flexDirection = "column"
              alignItems = "flex-start"
            }
          })(
            label("Testing raw HTML tags..."),
            label(new LabelProps      { htmlFor = "username" })("Username:"),
            input.text(new InputProps { id = "username"      })(),
            textarea(new TextareaProps {
              defaultValue = "Text area value"
              onChange = js.defined { e =>
                println(s"text area changed ${e.target.value}")
              }
            })(),
            select(new SelectProps {
              name = "text"
              defaultValue = "value2"
              // or controlled
              //value="value2"
            })(
              option(new OptionProps { value = "value1" })("Value 1"),
              option(new OptionProps {
                value = "value2"
              })("Value 2"),
              option(new OptionProps { value = "value3" })("Value 3")
            ),
            map(new MapProps { name = "primary" })(
              area(new AreaProps { shape = "circle"; coords = "75,75,75"; href = "left.html"   })(),
              area(new AreaProps { shape = "circle"; coords = "275,75,75"; href = "right.html" })()
            ),
            img(new ImgProps {
              useMap = "#primary"; src = "https://placehold.it/350x150"; alt = "350 x 150 pic"
            })(),
            button(new ButtonProps {
              name = "button"
              onClick = js.defined { _ =>
                println("click!")
              }
            })("Click Me"),
            ul(
              li("first item"),
              li("second item"),
              li("third item"),
            ),
            ol(
              li("first item"),
              li("second item"),
              li("third item"),
            ),
            // 2 different syntax if no children
            h1("Heading 1"),
            h2("Heading 2"),
            h3("Heading 3"),
            h4("Heading 4"),
            h5("Heading 5"),
            h6("Heading 6"),
            u("this should be underlined"),
            s("this should be stiked through"),
            b(new BProps   {})("this should be boldfaced"),
            em(new EmProps {})("this should be emphasized"),
            i("this should be italized"),
            a(new AProps {
              href = "#todo"
            })("Clicking this takes you to the standalone todo page"),
            progress(new ProgressProps {
              value = "70"
              max = "100"
            })("70 %"),
            p("Simple table with header"),
            table(
              thead(
                tr(
                  th("First name"),
                  th("Last name"),
                )),
              tbody(tr(
                      td("John"),
                      td("Doe"),
                    ),
                    tr(
                      td("Jane"),
                      td("Doe"),
                    ))
            ),
            label("SVG test"),
            svg(new SVGProps { width = "12cm"; height = "4cm"; viewBox = "0 0 1200 400" })(
              rect(new RectProps {
                x = "1"; y = "1"; width = "1198"; height = "398"; fill = "none"; stroke = "blue";
                strokeWidth = "2"
              })(),
              rect(new RectProps {
                x = "400"; y = "100"; width = "400"; height = "200"; fill = "yellow";
                stroke = "navy"; strokeWidth = "10"
              })()
            ),
            label("SVG test by setting inner HTML"),
            div(new DivProps {
              dangerouslySetInnerHTML = new SetInnerHTML {
                val __html = """
<?xml version="1.0" standalone="no"?>
<svg width="12cm" height="4cm" viewBox="0 0 1200 400"
     xmlns="http://www.w3.org/2000/svg" version="1.1">
  <desc>Example line01 - lines expressed in user coordinates</desc>

  <!-- Show outline of viewport using 'rect' element -->
  <rect x="1" y="1" width="1198" height="398"
        fill="none" stroke="blue" stroke-width="2" />

  <g stroke="green" >
    <line x1="100" y1="300" x2="300" y2="100"
            stroke-width="5"  />
    <line x1="300" y1="300" x2="500" y2="100"
            stroke-width="10"  />
    <line x1="500" y1="300" x2="700" y2="100"
            stroke-width="15"  />
    <line x1="700" y1="300" x2="900" y2="100"
            stroke-width="20"  />
    <line x1="900" y1="300" x2="1100" y2="100"
            stroke-width="25"  />
  </g>
</svg>
"""
              }
            })(),
          )
      }
    })
}
