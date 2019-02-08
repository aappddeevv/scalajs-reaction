// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react.examples
package helloworld

import scala.scalajs.js
import js.annotation._
import org.scalajs.dom
import js.Dynamic.{literal => lit}

import ttg.react
import react._
import elements._
import reactdom._
import react.implicits._

import vdom._
import tags._

object SuspenseTest {

  val sfc1 = SFC[js.Object](_ => vdom.tags.div("Text from a SFC"))

  val sfc2 = SFC[js.Object]{ _ =>
    val (v,update) = React.useState[Int](0)
    div(
      p(s"Using hooks useState: You clicked $v times"),
      button(new ButtonProps {
        onClick = js.defined(_ => update(v + 1))
      })("Click me"),
    )
  }

  sealed trait Action
  case object Increment extends Action
  case object Decrement extends Action
  case class Reset(value: Int) extends Action
  case class State(count: Int = 0)

  def init(x: Int) = State(x)

  def reducer(state: State, action: Action): State =
    action match {
      case Increment => State(state.count + 1)
      case Decrement => State(state.count - 1)
      case Reset(v) => init(v)
      case _ => state
    }

  trait State2 extends js.Object {
    val count: Int
  }

  @JSExportTopLevel("s1")
  val s1 = new State2 { val count = 10 }
  @JSExportTopLevel("s2")
  val s3 = new State2 { val count = 10 }
  @JSExportTopLevel("s3")
  val s4 = new State2 { val count = 30 }

  case class Foo(count: Int = 0)
  @JSExportTopLevel("ss1")
  val ss1 = Foo()
  @JSExportTopLevel("ss2")  
  val ss2 = Foo(10)
  @JSExportTopLevel("ss3")  
  val ss3 = Foo(30)

  val sfc3 = SFC[js.Dynamic]{ obj =>
    val initialCount: Int = obj.initialCount.asInt
    val (state, dispatch) = React.useReducer[State, Action, Int](reducer, initialCount, State(_))
    Fragment(
      div(s"Use hooks with reducer: Count: ${state.count}"),
      button(new ButtonProps {
        onClick = js.defined(_ => dispatch(Reset(initialCount)))
      })("Reset"),
      button(new ButtonProps {
        onClick = js.defined(_ => dispatch(Increment))
      })("Increment"),
      button(new ButtonProps {
        onClick = js.defined(_ => dispatch(Decrement))
      })("Decrement")
    )
  }

  val useKeyPress: js.Function1[String, Boolean] = { targetKey =>
    val (keyPressed, setKeyPressed) = React.useState[Boolean](false)

    val downHandler: js.Function1[js.Dynamic, Unit] =
      p => if(p.key.asString == targetKey) setKeyPressed(true)

    val upHandler: js.Function1[js.Dynamic, Unit] =
      p => if(p.key.asString == targetKey) setKeyPressed(false)

    React.useEffect(() => {
      dom.window.addEventListener("keydown", downHandler)
      dom.window.addEventListener("keyup", upHandler)
      () => {
        dom.window.removeEventListener("keydown", downHandler)
        dom.window.removeEventListener("keyup", upHandler)
      }
    }, Some(js.Array()))

    keyPressed
  }

  val sfc4 = SFC[js.Object]{ _ =>

    val happyPress = useKeyPress("h")
    val sadPress = useKeyPress("s")
    val robotPress = useKeyPress("r")
    val foxPress = useKeyPress("f")

    div(
      div(s"* h, s, r, f *"),
      Fragment(
        if(happyPress) "h" else null,
        if(sadPress) "s" else null,
        if(robotPress) "r" else null,
        if(foxPress) "f" else null
      )
    )
  }

  def blah(): ReactNode = {
    Fragment(
      sfc1(null),
      sfc2(null),
      sfc3(lit("initialCount" -> 10)),
      div("Keypress listener, press & hold key to display character"),
      sfc4(null)
    )
  }

}
