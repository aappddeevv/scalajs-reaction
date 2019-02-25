// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react.examples
package helloworld

import concurrent._
import concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js
import js.annotation._
import org.scalajs.dom
import js.Dynamic.{literal => lit}
import js.JSConverters._

import ttg.react
import react._
import elements._
import reactdom._
import react.implicits._

import vdom._
import tags._

object SuspenseTest {

  val sfc1 = SFC0{ () => vdom.tags.div("Text from a SFC") }

  val sfc2 = SFC0{ () => 
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

  def widthS(n: ReactNode) =
    div(new DivProps{
      style = new StyleAttr {
        width = 100
      }
    })(n)

  // test pure SFC!
  val sfc3 = SFC1[js.Dynamic]{ obj =>
    val initialCount: Int = obj.initialCount.asInt
    val (state, dispatch) = React.useReducer[State, Action, Int](reducer, initialCount, State(_))
    Fragment(
      div(s"Use hooks with reducer: Count: ${state.count}"),
      widthS(button(new ButtonProps {
        onClick = js.defined(_ => dispatch(Reset(initialCount)))
      })("Reset")),
      widthS(button(new ButtonProps {
        onClick = js.defined(_ => dispatch(Increment))
      })("Increment")),
      widthS(button(new ButtonProps {
        onClick = js.defined(_ => dispatch(Decrement))
      })("Decrement"))
    )
  }

  def useKeyPress(targetKey: String): Boolean = {
    val (keyPressed, setKeyPressed) = React.useState[Boolean](false)

    val downHandler: js.Function1[js.Dynamic, Unit] =
      p => if(p.key.asString == targetKey) setKeyPressed(true)

    val upHandler: js.Function1[js.Dynamic, Unit] =
      p => if(p.key.asString == targetKey) setKeyPressed(false)

    React.useEffectMounting(() => {
      dom.window.addEventListener("keydown", downHandler)
      dom.window.addEventListener("keyup", upHandler)
      () => {
        dom.window.removeEventListener("keydown", downHandler)
        dom.window.removeEventListener("keyup", upHandler)
      }
    })

    keyPressed
  }

  // from useHooks.com
  val sfc4 = SFC0 { () =>
    val happyPress = useKeyPress("h")
    val sadPress = useKeyPress("s")
    val robotPress = useKeyPress("r")
    val foxPress = useKeyPress("f")
    div(
      s"* h, s, r, f *: ",
      Fragment(
        if(happyPress) "h" else null,
        if(sadPress) "s" else null,
        if(robotPress) "r" else null,
        if(foxPress) "f" else null
      )
    )
  }

  trait Sfc5Props extends js.Object {
    val child: ReactNode
  }

  object Sfc5Props {
    def apply(c: ReactNode) = new Sfc5Props { val child = c }
  }

  val sfc5 = SFC1[Sfc5Props]{ props =>
    //Fragment(
      //p("scala.js React.Suspense component test, parent is a SFC"),
      //p("Child is typescript SuspenseChild so we can throw a promise easily and test interop."),
      Suspense(div("Loading (scalajs)..."), props.child)
  //)
  }

  val sfc6 = SFC0 { () =>
    val happyPress = useKeyPress("h")
    val sadPress = useKeyPress("s")
    val robotPress = useKeyPress("r")
    val foxPress = useKeyPress("f")
    div(
      "Created using a plain js function in scala",
      s"* h, s, r, f *: ",
      Fragment(
        if(happyPress) "h" else null,
        if(sadPress) "s" else null,
        if(robotPress) "r" else null,
        if(foxPress) "f" else null
      )
    )    
  }

  val jsc1: SFC0 = () => div("hello worl")

  val jscomponent = SFC1[SProps]{ p =>
    div(s"End of Tests: ${p.label}")
  }

  // these don't really work
  val componentSucceed = SFC0 { () =>
    T.throwit(js.Promise.resolve[Boolean](true))
    "succeeded"
  }

  // val componentFail = SFC1[js.Object]{ _ =>
  //   T.throwit(js.Promise.reject(new js.Error("Server failure")))
  //   "failed!"
  // }

  @js.native
  @JSImport("Assets/throwit.js", JSImport.Namespace)
  object T extends js.Object {
    def throwit(x: js.Any): Unit = js.native
  }

  @js.native
  @JSImport("Assets/SuspenseParent", JSImport.Namespace)
  object SuspenseParent extends js.Object {
    @JSName("SuspenseParent")
    val SuspenseParentJS: ReactJsComponent = js.native
    val X: DynamicImportThunk = js.native
    @JSName("SuspenseChild") // this has React.lazy
    val SuspenseChildJS: ReactJsComponent = js.native
  }

  @js.native
  @JSImport("Assets/SuspenseChild", JSImport.Default)
  object SuspenseChildJS extends ReactJsComponent

  trait SProps extends js.Object {
    var className: js.UndefOr[String] = js.undefined
    var label: js.UndefOr[String] = js.undefined
    var doit: js.UndefOr[Boolean] = js.undefined
    var delay: js.UndefOr[Int] = js.undefined // in ms
    // cache key
    var ckey: js.UndefOr[String] = js.undefined // needed to handlle fake cache
  }

  def spropsKey(_key: String, _delay: Int = 7000) =
    new SProps {
      ckey = _key
      delay = _delay
    }

  // direct parent import
  def SuspenseParent(props: SProps = null)(children: ReactNode*): Component =
    elements.wrapJsForScala[SProps](SuspenseParent.SuspenseParentJS, props, children:_*)

  // lazy is called in the ts file
  def LazySuspenseChild(props: SProps = null)(children: ReactNode*): Component =
    elements.wrapJsForScala[SProps](SuspenseParent.SuspenseChildJS, props, children:_*)

  // child is imported directly
  def SuspenseChild(props: SProps = null)(children: ReactNode*): Component =
    elements.wrapJsForScala[SProps](SuspenseChildJS, props, children:_*)

  // the arguments to lazy() are imported so we can run lazy in scala.js
  def LazyChildViaReactLazy(props: SProps = null)(children: ReactNode*) =
    elements.wrapJsForScala[SProps](React.`lazy`(SuspenseParent.X), props, children:_*)

  def blah(): ReactNode = {
    Fragment(
      sfc1,
      sfc2,
      sfc3(lit("initialCount" -> 10)),
       "Keypress listener, press & hold key to display character: ",
      sfc4,
      sfc6,
      div("=====> js/ts suspense demo below, default load time is 7 seconds"),
      SuspenseParent()(SuspenseChild(spropsKey("ts"))(div("text for SuspenseChild from scalajs"))),
      div("=====> scala.js suspense test 1"),
      // uses scala.js import of React.Suspense
      sfc5(Sfc5Props(LazySuspenseChild(spropsKey("test1", 10000))(div("text for SuspenseChild from scalajs")))),
      div("=====> scala.js suspense test 2"),
      sfc5(Sfc5Props(LazyChildViaReactLazy(spropsKey("test2", 15000))())),
      div("=====> scala.js suspense test 3"),
      sfc5(Sfc5Props(SuspenseChild(spropsKey("test3", 12000))(div("text for SuspenseChild from scalajs")))),
      // // div("=====> scala.js suspense test 4"),
      // // sfc5(componentSucceed(null)),
      // testing some implicit and explicit conversions :-)
      // explicit
      jscomponent.toEl(new SProps { label = "Almost the end!"}),
      // implicit, tuple of component and args => ReactNode
      (jscomponent, new SProps { label = "Really!" }),
    )
  }

}
