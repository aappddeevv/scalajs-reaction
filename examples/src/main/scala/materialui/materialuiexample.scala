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

package ttg.examples
package materialui

import scala.scalajs.js
import js.Dynamic.{ literal => jsobj }
import js.|
import org.scalajs.dom
import react._
import syntax.*
import conversions.given
import vdom._
import vdom.styling._
import components._
import jss._
import mui._
import mui.components._

object MaterialUIPage:
  val NAME = "MaterialUIPage"
    
  @js.native
  trait ClassNames extends ClassNamesTag:
    val bullet: String = js.native
    val card: String   = js.native
    val title: String  = js.native
    val pos: String    = js.native

  trait Styles extends RuleTag:
    var foo: js.UndefOr[JssStyle]    = js.undefined
    var foo2: js.UndefOr[JssStyle]   = js.undefined
    var card: js.UndefOr[JssStyle]   = js.undefined
    var bullet: js.UndefOr[JssStyle] = js.undefined
    var title: js.UndefOr[JssStyle]  = js.undefined
    var pos: js.UndefOr[JssStyle]    = js.undefined

  val xxx = jsobj("background" -> "rgb(245, 222, 179)") // wheat
  val yyy = jsobj("color"      -> "rgb(255,255,255)")

  val styles = new Styles {
    card = jsobj(
      "minWidth" -> 275,
      "extend"   -> js.Array(xxx, yyy), // show array of composes
      "composes" -> "ttg-MaterialUI-Example btn" // adds in order at use site
    )
    bullet = jsobj(
      "display"   -> "inline-block",
      "margin"    -> "0 2px",
      "transform" -> "scale(0.8)"
    )
    title = jsobj(
      "fontSize" -> 14
    )
    pos = jsobj(
      "marginBottom" -> 12
    )
  }

  val useStyles = createUseStyles0[ClassNames](styles)

  trait Props extends js.Object:
    var rootClassName: js.UndefOr[String] = js.undefined

  def apply(props: Props = new Props {}) = render.elementWith(props)

  val render: ReactFC[Props] = props => {
    val cn = useStyles()

    js.Dynamic.global.console.log("cn", cn)
    val bull = span(new SpanProps {
      className = cn.bullet
    })("•")

    // the list is of mixed type so specify type explicitly
    val parts: Seq[ReactNode] = Seq(
      InputLabel(new InputLabel.Props {})("This is a label"),
      TextField(new TextField.Props {
        onChange = js.defined(e => dom.console.log("TextField::onChange", e))
      })(),
      Button(new Button.Props {
        variant = Button.Variant.contained
        color = Button.Color.primary
        onClick = js.defined(_ => println("Click!"))
      })("Default"),
      Radio(new Radio.Props {
        color = Radio.Color.primary
        checked = true
      })("My Radio Button"),
      Fragment(
        Tabs(new Tabs.Props {
          var value: js.Any = 0
        })(
          Tab(new Tab.Props {
            label = "Tab 1".toNode
          }),
          Tab(new Tab.Props {
            label = "Tab 2".toNode
          })
        ),
        "add some tab content here"
      ),
      Card(new Card.Props {
        // 0
        className = cn.card
      })(
        Card.Content()(
          Typography(new Typography.Props {
            // 0
            className = cn.title
            color = Typography.Color.textSecondary
            gutterBottom = true
          })("Word of the day"),
          Typography(new Typography.Props {
            variant = Typography.Variant.h5
            component = "h2"
          })(
            "be",
            bull,
            "nev",
            bull,
            "o",
            bull,
            "lent"
          ),
          Typography(new Typography.Props {
            // 0
            className = cn.pos
            color = Typography.Color.textSecondary
          })("adjective"),
          Typography(new Typography.Props {
            component = "p"
          })(
            "well meaning and kindly.",
            br(),
            "a benevolent smile"
          )
        ),
        Card.Actions()(Button(new Button.Props {
          size = Button.Size.small
        })("Learn More"))
      )
    )

    divWithClassname(
      props.rootClassName,
      parts.map(example(_))
    )
  }
  render.displayName(NAME)

object components:
  val NAME = "MaterialUIExample"

  trait Props extends js.Object {
    val child: ReactNode
  }

  def example(c: ReactNode) = render.elementWith(new Props { val child = c })

  val render: ReactFC[Props] = props => {
    div(new DivProps {
      className = "ttg-muiExample"
      // inline style to keep it easy
      style = new StyleAttr {
        marginTop = 10
        marginBottom = 10
      }
    })(
      props.child
    )
  }
  render.displayName(NAME)
