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
import ttg.react._
import ttg.react.elements._
import ttg.react.reactdom._
import ttg.react.implicits._
import ttg.react.fabric
import fabric._
import fabric.components._

import vdom._
import vdom.tags._
import prefix_F._

@js.native
@JSImport("Examples/todo/todo.css", JSImport.Namespace)
object componentStyles extends js.Object

object styles {
  val component = componentStyles.asInstanceOf[js.Dynamic]
}

import styles._

case class ToDo(id: Int, name: String)

sealed trait ToDoAction
case class Add(todo: ToDo)                     extends ToDoAction
case class Remove(id: Int)                     extends ToDoAction
case class InputChanged(input: Option[String]) extends ToDoAction
case class SetTextFieldRef(ref: ITextField)    extends ToDoAction

object ToDoC {
  val ToDo = statelessComponent("ToDoItem")
  import ToDo.ops._

  def make(todo: ToDo, remove: Unit => Unit) =
    ToDo.copy(new methods {
      val render = self => {
        div(new DivProps { className = component.todo.asString })(
          Label()(todo.name),
          DefaultButton(new IButtonProps {
            text = "Remove"
            onClick = js.defined(_ => remove(()))
          })()
        )
      }
    })
}

object ToDoListHeader {
  val ToDoListHeader = statelessComponent("ToDoListheader")
  import ToDoListHeader.ops._

  def make(length: Int) =
    ToDoListHeader.copy(new methods {
      val render = self => {
        div(
          Label()(s"# To Dos - ${length}")
        )
      }
    })
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

  case class State(
      todos: Seq[ToDo] = Seq(),
      input: Option[String] = None,
      textFieldRef: Option[ITextField] = None)

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
      val retainedProps = RP(title)
      val reducer = (action, state, gen) => {
        action match {
          case Add(t) =>
            gen.update(state.copy(todos = state.todos :+ t, input = None))
          case Remove(id) =>
            gen.updateAndEffect(state.copy(todos = state.todos.filterNot(_.id == id)))
          case InputChanged(iopt) =>
            gen.updateAndEffect(state.copy(input = iopt))
          case SetTextFieldRef(ref) =>
            gen.silent(state.copy(textFieldRef = Some(ref)))
          case _ =>
            gen.skip
        }
      }

      val initialState = _ => State(todos, None)
      val render =
        self => {
          div(new DivProps {})(
            Label()(s"""App: ${title.getOrElse("The To Do List")}"""),
            div(new DivProps { className = component.dataEntry.asString })(
              TextField(new ITextFieldProps {
                placeholder = "enter new todo"
                componentRef = js.defined((r: ITextField) => self.send(SetTextFieldRef(r)))
                onChanged = js.defined((e: String) => self.handle(inputChanged(Option(e))))
                value = self.state.input.getOrElse[String]("")
                autoFocus = true
                onKeyPress = js.defined(e => if (e.which == dom.ext.KeyCode.Enter) addit(self))
              })(),
              PrimaryButton(new IButtonProps {
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