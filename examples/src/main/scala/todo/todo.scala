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
package todo

import scala.scalajs.js

import js.Dynamic.{ literal => lit, global => g }
import js.JSConverters._
import js.annotation._
import js.|

import org.scalajs.dom

import react._

import implicits._

import vdom._
import vdom.tags._

import fabric._
import fabric.components._
import fabric.styling._

import react_dom._

case class ToDo(id: Int, name: String, added: js.Date = null, completed: Boolean = false)

object ToDoItem {
  val Name = "ToDoItem"

  trait Props extends js.Object {
    var todo: ToDo
    var remove: () => Unit
    var rootClassname: js.UndefOr[String]  = js.undefined
    var titleClassname: js.UndefOr[String] = js.undefined
    var key: js.UndefOr[String]            = js.undefined
  }

  def apply(props: Props) = sfc(props)

  val sfc = SFC1[Props] { props =>
    useDebugValue(Name)
    divWithClassname(
      props.rootClassname,
      Label(new Label.Props {
        className = props.titleClassname
      })(
        props.todo.name
      ),
      Button.Default(new Button.Props {
        text = "Remove"
        onClick = js.defined(_ => props.remove())
      })()
    )
  }.memo
}

object ToDoListHeader {
  val Name = "ToDoListHeader"

  trait Props extends js.Object {
    var length: Int
  }

  def apply(props: Props) = sfc(props)

  val sfc = SFC1[Props] { props =>
    useDebugValue(Name)
    div(Label(s"# To Dos - ${props.length}"))
  }.memo
}

object ToDoList {
  val Name = "ToDoList"

  trait Props extends js.Object {
    var length: Int
    var todos: Seq[ToDo]
    var remove: Int => Unit
    var listClassname: js.UndefOr[String]  = js.undefined
    var todoClassname: js.UndefOr[String]  = js.undefined
    var titleClassname: js.UndefOr[String] = js.undefined
  }

  def apply(props: Props) = sfc(props)

  val sfc = SFC1[Props] { props =>
    useDebugValue(Name)
    divWithClassname(
      props.listClassname,
      ToDoListHeader(new ToDoListHeader.Props { var length = props.length }),
      props.todos.map(
        t =>
          ToDoItem(new ToDoItem.Props {
            var todo   = t
            var remove = () => props.remove(t.id)
            rootClassname = props.todoClassname
            titleClassname = props.titleClassname
            key = t.id.toString
          })
      )
    )
  }.memo
}

object ToDos {
  sealed trait Action
  case class Add(todo: ToDo)                     extends Action
  case class Remove(id: Int)                     extends Action
  case class InputChanged(input: Option[String]) extends Action

  var idCounter: Int = -1
  def mkId(): Int    = { idCounter = idCounter + 1; idCounter }
  import ToDoStyling._

  /** We put all state into one fat object. Probably better
   * to separate out `input` into its own useState.
   */
  case class State(
    todos: Seq[ToDo] = Seq(),
    input: Option[String] = None,
    var textFieldRef: Option[TextField.ITextField] = None
  )

  val Name = "ToDos"

  def addit(input: Option[String], dispatch: Dispatch[Action]) =
    input.foreach { i =>
      dispatch(Add(ToDo(mkId(), i)))
    // this is an effect so it should not go here
    //focusable.foreach(_.focus())
    }

  def reducer(state: State, action: Action): State =
    action match {
      case Add(t) =>
        state.copy(todos = state.todos :+ t, input = None)
      case Remove(id) =>
        state.copy(todos = state.todos.filterNot(_.id == id))
      case InputChanged(iopt) =>
        state.copy(input = iopt)
    }

  trait Props extends js.Object {
    val title: String
    val todos: Seq[ToDo]
    var styles: js.UndefOr[IStyleFunctionOrObject[StyleProps, Styles]] = js.undefined
    var className: js.UndefOr[String]                                  = js.undefined
  }

  def apply(props: Props) = sfc(props)

  val sfc = SFC1[Props] { props =>
    useDebugValue(Name)
    val ifield = useRef[Option[TextField.ITextField]](None)
    useEffectMounting { () =>
      println("ToDo: subscriptions: called during mount")
      () => println("ToDo: subscriptions: unmounted")
    }

    val (state, dispatch) =
      useReducer[State, Action](reducer, State(props.todos, None))
    // if the input is added as a todo or todo remove, reset focus
    useEffect(state.todos.length) { () =>
      ifield.current.foreach(_.focus())
    }

    val cn = getClassNames(new StyleProps { className = props.className }, props.styles)

    div(new DivProps {
      className = cn.root
    })(
      Label(s"""App: ${props.title}"""),
      div(new DivProps { className = cn.dataEntry })(
        TextField(new TextField.Props {
          placeholder = "enter new todo"
          componentRef = js.defined {
            // Option(r) -> None if r is null
            r =>
              ifield.current = Option(r)
          }
          onChangeInput = js.defined { (_, e: String) =>
            dispatch(InputChanged(Option(e)))
          }
          value = state.input.getOrElse[String]("")
          autoFocus = true
          onKeyPress = js.defined { e =>
            if (e.which == dom.ext.KeyCode.Enter) addit(state.input, dispatch)
          }
        })(),
        Button.Primary(new Button.Props {
          text = "Add"
          disabled = state.input.size == 0
          // demonstrates inline callback
          // could be:
          // _ => since we don't use 'e', could
          // ReactEvent[dom.html.Input] to be more specifci
          // ReactKeyboardEvent[_] to be more specific
          // ReactKeyboardEvent[dom.html.Input] to be more specific
          onClick = js.defined((e: ReactEvent[_]) => addit(state.input, dispatch))
        })()
      ),
      ToDoList(new ToDoList.Props {
        var length = state.todos.length
        var todos  = state.todos
        var remove = (id: Int) => dispatch(Remove(id))
        todoClassname = cn.todo
        titleClassname = cn.title
      })
    )
  }
}

object fakedata {
  val initialToDos = Seq(
    ToDo(ToDos.mkId(), "Call Fred")
  )
}

/** These would go directly in the component's enclosing object normally if
 * there were dependencies on other parts of that component. The below is just
 * pure styling so its here.
 */
object ToDoStyling {

  @js.native
  trait ClassNames extends IClassNamesTag {
    var root: String      = js.native
    var todo: String      = js.native
    var title: String     = js.native
    var dataEntry: String = js.native
  }

  trait Styles extends IStyleSetTag {
    val root: IStyle
    val todo: IStyle
    val title: IStyle
    val dataEntry: IStyle
  }

  trait StyleProps extends js.Object {
    var className: js.UndefOr[String] = js.undefined
    var randomArg: js.UndefOr[Int]    = js.undefined
  }

  val getStyles = stylingFunction[StyleProps, Styles] { props =>
    val randomArg = props.randomArg.getOrElse(300)
    new Styles {
      val root = stylearray(new IRawStyle {
        selectors = selectorset(
          ":global(:root)" -> lit(
            "--label-width" -> s"${randomArg}px"
          )
        )
      })
      val todo = new IRawStyle {
        displayName = "machina"
        display = "flex"
        marginBottom = "10px"
        selectors = selectorset("& $title" -> new IRawStyle {})
      }
      val title = new IRawStyle {
        width = "var(--label-width)"
        marginRight = "10px"
      }
      val dataEntry = new IRawStyle {
        display = "flex"
        selectors = selectorset("& .ms-Textfield" -> new IRawStyle {
          width = "var(--label-width)"
          marginRight = "10px"
        })
      }
    }
  }

  // example of memoizing, you need a js.Function to use memoizeFunction
  import fabric.merge_styles._
  val getClassNames: GetClassNamesFn[StyleProps, Styles, ClassNames] =
    (p, s) => mergeStyleSets(concatStyleSetsWithProps(p, getStyles, s))
}
