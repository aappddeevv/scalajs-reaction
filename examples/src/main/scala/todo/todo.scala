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
  val c = statelessComponent(Name)
  import c.ops._

  def apply(
    todo: ToDo,
    remove: () => Unit,
    rootClassname: js.UndefOr[String] = js.undefined,
    titleClassname: js.UndefOr[String] = js.undefined
  ) =
    render { self =>
      divWithClassname(
        rootClassname,
        Label(new Label.Props {
          className = titleClassname
        })(
          todo.name
        ),
        Button.Default(new Button.Props {
          text = "Remove"
          onClick = js.defined(_ => remove())
        })()
      )
    }
}

object ToDoListHeader {

  val ToDoListHeader = statelessComponent("ToDoListheader")
  import ToDoListHeader.ops._

  def apply(length: Int) =
    render { self =>
      div(Label()(s"# To Dos - ${length}"))
    }
}

object ToDoList {
  val c = statelessComponent("ToDoList")
  import c.ops._

  def apply(
    length: Int,
    todos: Seq[ToDo],
    remove: Int => Unit,
    listClassname: js.UndefOr[String] = js.undefined,
    todoClassname: js.UndefOr[String] = js.undefined,
    titleClassname: js.UndefOr[String] = js.undefined
  ) = render{ self =>
    divWithClassname(
      listClassname,
      ToDoListHeader(length),
      todos.map(t =>
        ToDoItem(
          t,
          () => remove(t.id),
          rootClassname = todoClassname,
          titleClassname = titleClassname
        ).toEl(Option(t.id.toString), None)
      ))
  }
}

object ToDos {
  sealed trait ToDoAction
  case class Add(todo: ToDo)                     extends ToDoAction
  case class Remove(id: Int)                     extends ToDoAction
  case class InputChanged(input: Option[String]) extends ToDoAction
  //case class SetTextFieldRef(ref: ITextField)    extends ToDoAction
  case class Complete(id: Int) extends ToDoAction

  var idCounter: Int = -1
  def mkId(): Int    = { idCounter = idCounter + 1; idCounter }
  import ToDoStyling._

  case class State(
    todos: Seq[ToDo] = Seq(),
    input: Option[String] = None,
    // should be boxed
    var textFieldRef: Option[TextField.ITextField] = None
  )

  val Name = "ToDos"
  val c = reducerComponent[State, ToDoAction](Name)
  import c.ops._

  def remove(id: Int)(self: Self): Unit = self.send(Remove(id))
  def inputChanged(e: Option[String])(self: Self): Unit =
    self.send(InputChanged(e))

  def addit(self: Self) =
    self.state.input.foreach { i =>
      self.handle { s =>
        s.send(Add(ToDo(mkId(), i)))
        s.state.textFieldRef.foreach(_.focus())
      }
    }

  def apply(
    title: Option[String] = None,
    todos: Seq[ToDo] = Seq()
  ) =
    c.copyWith(new methods {

      didMount = js.defined{ self =>
        println("ToDo: subscriptions: called during mount")
        self.onUnmount(() => println("ToDo: subscriptions: unmounted"))
      }

      val reducer = (action, state, gen) => {
        action match {
          case Add(t) =>
            gen.update(state.copy(todos = state.todos :+ t, input = None))
          case Remove(id) =>
            gen.update(state.copy(todos = state.todos.filterNot(_.id == id)))
          case InputChanged(iopt) =>
            gen.update(state.copy(input = iopt))
          //case SetTextFieldRef(ref) =>
          //  gen.silent(state.copy(textFieldRef = Some(ref)))
          case _ =>
            gen.skip
        }
      }

      val initialState = _ => State(todos, None)

      val render =  self => {
        val cn = getClassNames(resolve[StyleProps, Styles](
          new StyleProps {
          },
          getStyles
        ))

        div(new DivProps {
          className = cn.root
        })(
          Label()(s"""App: ${title.getOrElse("The To Do List")}"""),
          div(new DivProps { className = cn.dataEntry })(
            TextField(new TextField.Props {
              placeholder = "enter new todo"
              componentRef = js.defined((r: TextField.ITextField) => self.state.textFieldRef = Option(r))
              onChangeInput = js.defined((_, e: String) =>
                self.handle(inputChanged(Option(e))))
              value = self.state.input.getOrElse[String]("")
              autoFocus = true
              onKeyPress = js.defined(e => if (e.which == dom.ext.KeyCode.Enter) addit(self))
            })(),
            Button.Primary(new Button.Props {
              text = "Add"
              disabled = self.state.input.size == 0
              // demonstrates inline callback
              // could be:
              // _ => since we don't use 'e', could
              // ReactEvent[dom.html.Input] to be more specifci
              // ReactKeyboardEvent[_] to be more specific
              // ReactKeyboardEvent[dom.html.Input] to be more specific
              onClick = js.defined((e: ReactEvent[_]) => addit(self))
            })()
          ),
          ToDoList(
            self.state.todos.length,
            self.state.todos,
            (id: Int) => self.handle(remove(id)),
            //listClassname = ???
            todoClassname = cn.todo,
            titleClassname = cn.title)
        )
      }
    })

  @JSExportTopLevel("ToDos")
  val exportedApp = c.wrapScalaForJs((jsProps: js.Object) => ToDos.apply())
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
