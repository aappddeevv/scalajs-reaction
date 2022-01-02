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

import js.Dynamic.{ literal => jsobj }
import js.JSConverters.*
import js.annotation.*
import react.*
import react.syntax.*
import vdom.*
import vdom.styling.*
import fabric.*
import fabric.components.*
import fabric.experiments.*
import fabric.styling.*
import react_router6.dom.*

object Nav:

  def makeItem(k: String, nm: String, nav: String, goto: NavigateFunction) =
    new Sidebar.ItemProps {
      val key = k
      title = nm
      text = nm
      active = false
      iconProps = jsobj("iconName" -> "Bullseye")
      onClick = IContextualMenuItem.OnClick((_,_) => goto(nav))
    }
    
  val fix: String => String = p => p

  val itemRoutes = Seq(
    ("readme", "Readme", fix("readme")),
    ("addressPage", "Addresses", fix("addresses")),
    ("todo", "To Do App", fix("todo")),
    ("helloworld", "Hello World/Hooks", fix("helloworld")),
    ("changeredux", "Change Redux", fix("changeredux")),
    ("labelandchild", "Label and Child", fix("labelandchild")),
    ("tagtest", "HTML Tag Test", fix("tagtest")),
    ("pressure", "Pressure", fix("pressure")),
    ("graph", "Graph", fix("graph")),
    ("calendar", "React Big Calendar", fix("calendar")),
    ("bootstrap", "Bootstrap", fix("bootstrap")),
    ("mui", "Material UI", fix("mui"))
  )

  trait Props extends js.Object:
    var rootClassName: js.UndefOr[String] = js.undefined    

  val Name                = "Nav"

  def apply(props: Props) = render.elementWith(props)
  def apply() = render.elementWith(new Props {})

  val render: ReactFC[Props] = props => {
    val navigate = useNavigate()
    Sidebar(new Sidebar.Props {
      className = props.rootClassName
      val theme = Styling.getTheme() // needed else exception!
      items = itemRoutes.map(p => makeItem(p._1, p._2, p._3, navigate)).toJSArray
    })
  }

  render.displayName(Name)

end Nav
