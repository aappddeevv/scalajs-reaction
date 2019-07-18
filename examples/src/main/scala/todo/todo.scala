// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package examples
package todo

import scala.scalajs.js
import js.|
import js.annotation._
import js.Dynamic.{literal => lit, global => g}
import js.JSConverters._

import org.scalajs.dom
import ttg.react
import react._
import react.elements._
import react.reactdom._
import react.implicits._
import react.fabric
import fabric._
import fabric.components._
import fabric.styling._

import vdom._
import vdom.tags._

case class ToDo(id: Int, name: String, added: js.Date = null, completed: Boolean = false)

object ToDoItem {
  val Name = "ToDoItem"

  trait Props extends js.Object {
    var todo: ToDo
    var remove: () => Unit
    var rootClassname: js.UndefOr[String] = js.undefined
    var titleClassname: js.UndefOr[String] = js.undefined
    var key: js.UndefOr[String] = js.undefined
  }

  def apply(props: Props) = sfc(props)

  val sfc = SFC1[Props]{ props =>
    React.useDebugValue(Name)
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

  val sfc = SFC1[Props]{ props =>
    React.useDebugValue(Name)
    div(Label()(s"# To Dos - ${props.length}"))
  }.memo
}

object ToDoList {
  val Name = "ToDoList"

  trait Props extends js.Object {
    var length: Int
    var todos: Seq[ToDo]
    var remove: Int => Unit
    var listClassname: js.UndefOr[String] = js.undefined
    var todoClassname: js.UndefOr[String] = js.undefined
    var titleClassname: js.UndefOr[String] = js.undefined
  }

  // case class Props(
  //   length: Int = 0,
  //   todos: Seq[ToDo] = Nil,
  //   remove: Int => Unit = _ => (),
  //   listClassname: js.UndefOr[String] = js.undefined,
  //   todoClassname: js.UndefOr[String] = js.undefined,
  //   titleClassname: js.UndefOr[String] = js.undefined
  // )

  def apply(props: Props) = sfc(props)

  val sfc = SFC1[Props] { props =>
    React.useDebugValue(Name)
    divWithClassname(
      props.listClassname,
      ToDoListHeader(new ToDoListHeader.Props{ var length = props.length}),
      props.todos.map(t =>
        ToDoItem(new ToDoItem.Props {
          var todo = t
          var remove = () => props.remove(t.id)
          rootClassname = props.todoClassname
          titleClassname = props.titleClassname
          key = t.id.toString
        })
      ))
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
  }

  def apply(props: Props) = sfc(props)

  val sfc = SFC1[Props] { props =>
    React.useDebugValue(Name)
    val ifield = React.useRef[Option[TextField.ITextField]](None)    
    React.useEffectMountingCb{() =>
      println("ToDo: subscriptions: called during mount")
        () => println("ToDo: subscriptions: unmounted")
    }

    val (state, dispatch) =
      React.useReducer[State,Action](reducer, State(props.todos, None))
    // if the input is added as a todo or todo remove, reset focus
    React.useEffect(state.todos.length){() =>
      ifield.current.foreach(_.focus())
    }

    val cn = getClassNames(resolve[StyleProps, Styles](
      new StyleProps {
      },
      getStyles
    ))

    div(new DivProps {
      className = cn.root
    })(
      Label()(s"""App: ${props.title}"""),
      div(new DivProps { className = cn.dataEntry })(
        TextField(new TextField.Props {
          placeholder = "enter new todo"
          componentRef = js.defined{
            // Option(r) -> None if r is null
            r => ifield.current = Option(r)
          }
          onChangeInput = js.defined{(_, e: String) =>
            dispatch(InputChanged(Option(e)))
          }
          value = state.input.getOrElse[String]("")
          autoFocus = true
          onKeyPress = js.defined{
            e => if (e.which == dom.ext.KeyCode.Enter) addit(state.input, dispatch)
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
          var todos = state.todos
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

object ToDoStyling {

  @js.native
  trait ClassNames extends js.Object {
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
    var randomArg: js.UndefOr[Int] = js.undefined
  }

  val getStyles = stylingFunction[StyleProps, Styles] { props =>
    val randomArg = props.randomArg.getOrElse(300)
    new Styles {
      val root = stylearray(
        new IRawStyle {
          selectors = selectorset(
            ":global(:root)" -> lit(
              "--label-width" -> s"${randomArg}px",
            ))
        })
      val todo = new IRawStyle {
        displayName = "machina"
        display = "flex"
        marginBottom = "10px"
        selectors = selectorset("& $title" -> new IRawStyle {})
      }
      val title =  new IRawStyle {
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
  val getClassNames =
    fabric.Utilities.memoizeFunction[Styles, ClassNames](Styling.mergeStyleSets[ClassNames](_))
}
