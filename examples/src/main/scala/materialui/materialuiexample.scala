// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react.examples
package materialui

import scala.scalajs.js
import js.Dynamic.{literal => jsobj}
import js.|
import org.scalajs.dom
import ttg.react
import react._
import elements._
import react.implicits._
import vdom._
import vdom.styling._
import vdom.tags._

import react.mui._
import react.mui.components._

object MaterialUIPage {

  @js.native
  trait ClassNames extends ClassNamesTag {
    val bullet: String = js.native
    val card: String = js.native
    val title: String = js.native
    val pos: String = js.native
  }

  trait Styles extends StyleSetTag {
    var foo: js.UndefOr[JssStyle] = js.undefined
    var foo2: js.UndefOr[JssStyle] = js.undefined
    var card: js.UndefOr[JssStyle] = js.undefined
    var bullet: js.UndefOr[JssStyle] = js.undefined
    var title: js.UndefOr[JssStyle] = js.undefined
    var pos: js.UndefOr[JssStyle] = js.undefined
  }

  val xxx = jsobj("background" -> "rgb(245, 222, 179)") // wheat
  val yyy = jsobj("color" -> "rgb(255,255,255)")  

  val styles = new Styles {
    card = jsobj(
      "minWidth" -> 275,
      "extend" -> js.Array(xxx, yyy), // show array of composes
      "composes" -> "ttg-MaterialUI-Example btn"  // adds in order at use site
    )
    bullet = jsobj(
      "display"-> "inline-block",
      "margin" -> "0 2px",
      "transform" -> "scale(0.8)"
    )
    title = jsobj(
      "fontSize" -> 14
    )
    pos = jsobj(
      "marginBottom" -> 12
    )
  }

  // Do this once outside render, since the style is static (not dependent on
  // props). Could do this inside component methods, if needed.


  val c = statelessComponent("MaterialUIPage")
  import c.ops._

  def apply(
    rootClassName: js.UndefOr[String] = js.undefined
  ) = render { self =>
    val Styled = makeStyled[Styles, ClassNames](styles)
    Styled{ msr =>
      dom.console.log("msr", msr)

      val bull = span(new SpanProps{
        className = msr.classes.bullet
      })("•")
      
      div(new DivProps {
        className = rootClassName
      })(
        MaterialUIExample(
          InputLabel(new InputLabel.Props{
          })("This is a label")
        ),
        MaterialUIExample(
          TextField(new TextField.Props {
            onChange = js.defined(e => dom.console.log("onChange", e))
          })()
        ),
        MaterialUIExample(
          Button(new Button.Props {
            variant = Button.Variant.contained
            color = Button.Color.primary
            onClick = js.defined(_ => println("Click!"))
          })("Default")
        ),
        MaterialUIExample(
          Radio(new Radio.Props {
            color = Radio.Color.primary
            checked = true
          })("My Radio Button")
        ),
        MaterialUIExample(
          Fragment(
            Tabs(new Tabs.Props {
              var value: js.Any = 0
            })(
              Tab(new Tab.Props {
                label = "Tab 1".toNode
              }),
              Tab(new Tab.Props {
                label = "Tab 2".toNode
              }),
            ),
            "add some tab content here"
          )
        ),
        MaterialUIExample(
          Card(new Card.Props {
            className = msr.classes.card
          })(
            Card.Content()(
              Typography(new Typography.Props {
                className = msr.classes.title
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
                bull, "o", bull,
                "lent"
              ),
              Typography(new Typography.Props {
                className = msr.classes.pos
                color = Typography.Color.textSecondary
              })("adjective"),
              Typography(new Typography.Props {
                component = "p"
              })(
                "well meaning and kindly.",
                br(),
                "a benevolent smile"
              ),
            ),
            Card.Actions()(
              Button(new Button.Props{
                size = Button.Size.small
              })("Learn More")))
        ))}
  }
}

object MaterialUIExample {
  val c = statelessComponent("MaterialUIExample")
  import c.ops._

  def apply(
    child: ReactElement
  ) = render { self =>
    div(new DivProps {
      className = "ttg-materialUIExample"
      style = new StyleAttr {
        marginTop = 10
        marginBottom = 10
      }
    })(
      child
    )
  }
}
