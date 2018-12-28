// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react.examples
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

object ToDoStyling {

  @js.native
  trait ToDoClassNames extends js.Object {
    var root: String      = js.native
    var todo: String      = js.native
    var title: String     = js.native
    var dataEntry: String = js.native
  }

  def getClassNames(randomArg: Int = 400) =
    Styling.mergeStyleSets[ToDoClassNames](
      styleset(
        "root" -> new IRawStyle {
          selectors = selectorset(
            ":global(:root)" -> lit(
              "--label-width" -> s"${randomArg}px",
            ))
        },
        "todo" -> new IRawStyle {
          displayName = "machina"
          display = "flex"
          marginBottom = "10px"
          selectors = selectorset("& $title" -> new IRawStyle {})
        },
        "title" -> new IRawStyle {
          width = "var(--label-width)"
          marginRight = "10px"
        },
        "dataEntry" -> new IRawStyle {
          display = "flex"
          selectors = selectorset("& .ms-Textfield" -> new IRawStyle {
            width = "var(--label-width)"
            marginRight = "10px"
          })
        }
      ))

  // example of memoizing, you need a js.Function to use memoizeFunction
  val memoized = fabric.Utilities.memoizeFunction(js.Any.fromFunction1(getClassNames))
  val cn       = memoized(500)
}

case class ToDo(id: Int, name: String, added: js.Date = null)

sealed trait ToDoAction
case class Add(todo: ToDo)                     extends ToDoAction
case class Remove(id: Int)                     extends ToDoAction
case class InputChanged(input: Option[String]) extends ToDoAction
//case class SetTextFieldRef(ref: ITextField)    extends ToDoAction
case class Complete(id: Int) extends ToDoAction

object ToDoC {

  val ToDo = statelessComponent("ToDoItem")
  import ToDo.ops._
  import ToDoStyling._
  println(s"classnames\n${PrettyJson.render(cn)}")

  def make(todo: ToDo, remove: Unit => Unit) =
    render { self =>
      div(new DivProps { className = cn.todo })(
        Label(new Label.Props {
          className = cn.title
        })(
          todo.name
        ),
        Button.Default(new Button.Props {
          text = "Remove"
          onClick = js.defined(_ => remove(()))
        })()
      )
    }
}

object ToDoListHeader {
  val ToDoListHeader = statelessComponent("ToDoListheader")
  import ToDoListHeader.ops._

  def make(length: Int) =
    render { self =>
      div(Label()(s"# To Dos - ${length}"))
    }
}

object ToDoListC {
  val ToDoList = statelessComponentWithRetainedProps[Int]("ToDoList")
  import ToDoList.ops._

  def make(length: Int, todos: Seq[ToDo], remove: Int => Unit) =
    ToDoList.copy(new methods {
      val retainedProps = length
      val render = self => {
        div(
          ToDoListHeader.make(length),
          arrayToElement(
            todos.map(t => element(ToDoC.make(t, _ => remove(t.id)), key = Some(t.id.toString()))))
        )
      }
    })
}

object ToDosC {
  var idCounter: Int = -1
  def mkId(): Int    = { idCounter = idCounter + 1; idCounter }
  import ToDoStyling._

  case class State(
      todos: Seq[ToDo] = Seq(),
      input: Option[String] = None,
      var textFieldRef: Option[TextField.ITextField] = None)

  case class RP(title: Option[String] = None)
  val ToDos = reducerComponentWithRetainedProps[State, RP, ToDoAction]("ToDos")
  import ToDos.ops._

  def remove(id: Int)(self: ToDos.Self): Unit = self.send(Remove(id))
  def inputChanged(e: Option[String])(self: ToDos.Self): Unit =
    self.send(InputChanged(e))

  def addit(self: Self) =
    self.state.input.foreach { i =>
      self.handle { s =>
        s.send(Add(ToDo(mkId(), i)))
        s.state.textFieldRef.foreach(ref => refToJs(ref).focus())
      }
    }

  def make(title: Option[String] = None, todos: Seq[ToDo] = Seq()) =
    ToDos.copy(new methods {
      subscriptions = js.defined { self =>
        js.Array(() => {
          println("ToDo: subscriptions: called during mount")
          () =>
            println("ToDo: subscriptions: unmounted")
        })
      }
      val retainedProps = RP(title)
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
      val render =
        self => {
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
            ToDoListC.make(self.state.todos.length,
                           self.state.todos,
                           (id: Int) => self.handle(remove(id)))
          )
        }
    })

  @JSExportTopLevel("ToDos")
  val exportedApp = ToDos.wrapScalaForJs((jsProps: js.Object) => make())
}

object fakedata {
  val initialToDos = Seq(
    ToDo(ToDosC.mkId(), "Call Fred")
  )
}

object ToDoApp {

  @JSExportTopLevel("todos")
  def todos(): Unit = {
    println("Running todos...")
    renderToElementWithId(
      Fabric()(ToDosC.make(Some("My To Do List"), Seq(ToDo(ToDosC.mkId(), "Call Fred"))).toEl),
      "container")
  }
}
