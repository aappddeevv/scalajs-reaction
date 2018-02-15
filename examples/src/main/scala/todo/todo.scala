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
import prefix_<^._
import prefix_F._

@js.native
@JSImport("Examples/todo/todo.css", JSImport.Namespace)
object componentStyles extends js.Object

object styles {
  val component = componentStyles.asInstanceOf[js.Dynamic]
}

case class ToDo(id: Int, name: String)

sealed trait ToDoAction
case class Add(todo: ToDo) extends ToDoAction
case class Remove(id: Int) extends ToDoAction
case class InputChanged(input: Option[String]) extends ToDoAction
case class SetTextFieldRef(ref: ITextField) extends ToDoAction

object ToDoC {
  val ToDo = statelessComponent("ToDoItem")
  def make(todo: ToDo, remove: Unit => Unit) =
    ToDo.withRender(self => {
      <.div(^.className := styles.component.todo)(
        Label(new LabelProps { className = styles.component.title.asString })(todo.name).toEl,
        DefaultButton(new IButtonProps {
          text = "Remove"
          onClick = js.defined(_ => remove(()))
        })().toEl
      )
    })
}

object ToDoListHeader {
  val ToDoListHeader = statelessComponent("ToDoListheader")
  def make(length: Int) =
    ToDoListHeader
      .withRender { self =>
        <.div()(
          Label()(s"# To Dos - ${length}").toEl
        )
      }
}

object ToDoListC {
  val ToDoList = statelessComponentWithRetainedProps[Int]("ToDoList")
  def make(length: Int, todos: Seq[ToDo], remove: Int => Unit) =
    ToDoList
      .withRetainedProps(length)
      .withRender(self => {
        <.div()(
          ToDoListHeader.make(length).toEl,
          arrayToElement(todos.map(t => element(ToDoC.make(t, _ => remove(t.id)), key = Some(t.id.toString()))))
        )
      })
      .withWillReceiveProps(self => {
        println(s"ToDoList: willReceiveProps: retained props ${self.retainedProps}")
        self.state
      })
}

object ToDosC {
  var idCounter: Int = -1
  def mkId(): Int = { idCounter = idCounter + 1; idCounter }
  case class State(todos: Seq[ToDo] = Seq(), input: Option[String] = None, textFieldRef: Option[ITextField] = None)
  case class RP(title: Option[String] = None)
  val ToDos = reducerComponentWithRetainedProps[State, RP, ToDoAction]("ToDos")

  def remove(id: Int)(self: Self[State, RP, ToDoAction]): Unit = self.send(Remove(id))
  def inputChanged(e: Option[String])(self: Self[State, RP, ToDoAction]): Unit = self.send(InputChanged(e))

  def make(title: Option[String] = None, todos: Seq[ToDo] = Seq()) =
    ToDos
      .withRetainedProps(RP(title))
      .withReducer((action, sopt, gen) => {
        action match {
          case Add(t) =>
            sopt
              .map(state => gen.update(Some(state.copy(todos = state.todos :+ t, input = None))))
              .getOrElse(gen.skip)
          case Remove(id) =>
            sopt
              .map(state => gen.updateAndEffect(Some(state.copy(todos = state.todos.filterNot(_.id == id)))))
              .getOrElse(gen.skip)
          case InputChanged(iopt) =>
            sopt
              .map(state => gen.updateAndEffect(Some(state.copy(input = iopt))))
              .getOrElse(gen.skip)
          case SetTextFieldRef(ref) =>
            sopt
              .map(state => gen.silent(Some(state.copy(textFieldRef = Some(ref)))))
              .getOrElse(gen.skip)
        }
      })
      .withInitialState(_ => Some(State(todos, None)))
      .withRender { self =>
        <.div(^.className := styles.component.todoApp)(
          Label()(s"""App: ${title.getOrElse("The To Do List")}"""),
          <.div(^.className := styles.component.dataEntry)(
            TextField(new ITextFieldProps {
              componentRef = js.defined((r: ITextField) => self.state.foreach(s => self.send(SetTextFieldRef(r))))
              onChanged = js.defined((e: String) => self.handle(inputChanged(Option(e))))
              value = self.state.flatMap(_.input).getOrElse[String]("")
            })(),
            PrimaryButton(new IButtonProps {
              text = "Add"
              disabled = self.state.flatMap(_.input).map(_.size == 0).getOrElse[Boolean](true)
              // demonstrates inline callback
              onClick = js.defined { (e: ReactEvent[_]) => // could be _ => since we don't use 'e'
                // if have state, add todo and refocus
                self.state.flatMap(_.input).foreach { i =>
                  self.handle { s =>
                    s.send(Add(ToDo(mkId(), i)))
                    s.state.flatMap(_.textFieldRef).foreach(ref => refToJs(ref).focus())
                  }
                }
              }
            })()
          ),
          self.state
            .map(s => ToDoListC.make(s.todos.length, s.todos, (id: Int) => self.handle(remove(id))))
            .getOrElse(nullComponent)
            .toEl
        )
      }

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
    renderToElementWithId(Fabric()(ToDosC.make(Some("My To Do List"), Seq(ToDo(ToDosC.mkId(), "Call Fred"))).toEl), "container")
  }
}
