// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react.examples
package materialui

import scala.scalajs.js
import js.|
import org.scalajs.dom
import ttg.react
import react._
import elements._
import react.implicits._
import vdom._
import vdom.styling._
import vdom.tags._

import react.materialui._
import react.materialui.components._

object MaterialUIPage {

  styles.install()

  val c = statelessComponent("MaterialUIPage")
  import c.ops._

  def apply(
    rootClassName: js.UndefOr[String] = js.undefined
  ) = render { self =>
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
          variant = "contained"
          color = "primary"
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
      )
    )
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
