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
package helloworld

import scala.scalajs.js
import js.annotation.*
import org.scalajs.dom
import react.*
import syntax.*
import jshelpers.syntax.*
import react.conversions.given
import vdom.*

object SuspenseTest {

  val sfc1: ReactFC0 = () => { div("Text from a SFC") }

  val sfc2: ReactFC0 = () => {
    val (v, update) = useStateDirect[Int](() => 0)
    div(
      p(s"Using hooks useState: You clicked $v times"),
      button(new ButtonProps {
        onClick = js.defined(_ => update(v + 1))
      })("Click me")
    )
  }

  sealed trait Action
  case object Increment        extends Action
  case object Decrement        extends Action
  case class Reset(value: Int) extends Action
  case class State(count: Int = 0)

  def init(x: Int) = State(x)

  def reducer(state: State, action: Action): State =
    action match {
      case Increment => State(state.count + 1)
      case Decrement => State(state.count - 1)
      case Reset(v)  => init(v)
      case _         => state
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
    div(new DivProps {
      style = new StyleAttr {
        width = 100
      }
    })(n)

  trait Props extends js.Object {
    var initialCount: Int
  }

  // test pure SFC with js.Dynamic
  val sfc3: ReactFC[Props] = props => {
    val initialCount: Int = props.initialCount
    val (state, dispatch) =
      useReducer[State, Action, Int](reducer, initialCount, State(_))
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
    val (keyPressed, setKeyPressed) = useStateStrictDirect[Boolean](false)

    val downHandler: js.Function1[js.Dynamic, Unit] =
      p => if (p.key.asString == targetKey) setKeyPressed(true)

    val upHandler: js.Function1[js.Dynamic, Unit] =
      p => if (p.key.asString == targetKey) setKeyPressed(false)

    useEffectMounting(() => {
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
  val sfc4: ReactFC0 = () => {
    val happyPress = useKeyPress("h")
    val sadPress   = useKeyPress("s")
    val robotPress = useKeyPress("r")
    val foxPress   = useKeyPress("f")
    div(
      s"* h, s, r, f *: ",
      Fragment(
        when(happyPress)("h"),
        when(sadPress)("s"),
        when(robotPress)("r"),
        when(foxPress)("f"),
      )
    )
  }

  trait Sfc5Props extends js.Object {
    val child: ReactNode
  }

  object Sfc5Props {
    def apply(c: ReactNode) = new Sfc5Props { val child = c }
  }

  val sfc5: ReactFC[Sfc5Props] = props => {
    //Fragment(
    //p("scala.js React.Suspense component test, parent is a SFC"),
    //p("Child is typescript SuspenseChild so we can throw a promise easily and test interop."),
    Suspense(div("Loading (scalajs)..."))(props.child)
    //)
  }

  val sfc6: ReactFC0 = () => {
    val happyPress = useKeyPress("h")
    val sadPress   = useKeyPress("s")
    val robotPress = useKeyPress("r")
    val foxPress   = useKeyPress("f")
    div(
      "Created using a plain js function in scala",
      s"* h, s, r, f *: ",
      Fragment(
        when(happyPress)("h"),
        when(sadPress)("s"),
        when(robotPress)("r"),
        // should pick up conversion
        if (foxPress) "f" else nullNode
      )
    )
  }

  val jsc1: ReactFC0 = () => { div("hello world") }

  val jscomponent: ReactFC[SProps] = p => {
    div(s"End of Tests: ${p.label}")
  }

  // these don't really work
  val componentSucceed: ReactFC0 = () => {
    T.throwit(js.Promise.resolve[Boolean](true))
    "succeeded"
  }

  // val componentFail = SFC1[js.Object]{ _ =>
  //   T.throwit(js.Promise.reject(new js.Error("Server failure")))
  //   "failed!"
  // }

  @js.native
  @JSImport("Assets/throwit", JSImport.Namespace)
  object T extends js.Object {
    def throwit(x: js.Any): Unit = js.native
  }

  @js.native
  @JSImport("Assets/SuspenseParent", JSImport.Namespace)
  object SuspenseParentNS extends js.Object {
    @JSName("SuspenseParent")
    val SuspenseParentJS: ReactJSComponent = js.native
    val X: DynamicImportThunk              = js.native
    @JSName("SuspenseChild") // this has React.lazy
    val SuspenseChildJS: ReactJSComponent = js.native
  }

  @js.native
  @JSImport("Assets/SuspenseChild", JSImport.Default)
  object SuspenseChildJS extends ReactJSComponent

  trait SProps extends js.Object {
    var className: js.UndefOr[String] = js.undefined
    var label: js.UndefOr[String]     = js.undefined
    var doit: js.UndefOr[Boolean]     = js.undefined
    var delay: js.UndefOr[Int]        = js.undefined // in ms
    // cache key
    var ckey: js.UndefOr[String] = js.undefined // needed to handlle fake cache
  }

  def spropsKey(_key: String, _delay: Int = 7000) =
    new SProps {
      ckey = _key
      delay = _delay
    }

  // direct parent import
  def SuspenseParent(props: SProps )(children: ReactNode*) =
    createElement(SuspenseParentNS.SuspenseParentJS, props, children: _*)

  // lazy is called in the ts file
  def LazySuspenseChild(props: SProps)(children: ReactNode*) =
    createElement(SuspenseParentNS.SuspenseChildJS, props, children: _*)

  // child is imported directly
  def SuspenseChild(props: SProps)(children: ReactNode*) =
    createElement(SuspenseChildJS, props, children: _*)

  // the arguments to lazy() are imported so we can run lazy in scala.js
  def LazyChildViaReactLazy(props: SProps )(children: ReactNode*) =
    createElement(`lazy`(SuspenseParentNS.X), props, children: _*)

  def blah(): ReactNode =
    Fragment(
      sfc1,
      sfc2,
      sfc3(new Props { var initialCount = 10 }),
      "Keypress listener, press & hold key to display character: ",
      sfc4,
      sfc6,
      div("=====> js/ts suspense demo below, default load time is 7 seconds. SuspenseChild throws Promies in js"),
      SuspenseParent(new {})(SuspenseChild(spropsKey("ts"))(div("text for SuspenseChild from scalajs"))),
      div("=====> scala.js suspense test 1: Imported a component defined by React.lazy(()=>import(..)) in js"),
      // uses scala.js import of React.Suspense
      sfc5(Sfc5Props(LazySuspenseChild(spropsKey("test1", 10000))(div("text for SuspenseChild from scalajs")))),
      div("=====> scala.js suspense test 2: Imported () => import(child), called scala.js React.lazy"),
      sfc5(Sfc5Props(LazyChildViaReactLazy(spropsKey("test2", 15000))())),
      div("=====> scala.js suspense test 3: Import SuspenseChild directly."),
      sfc5(Sfc5Props(SuspenseChild(spropsKey("test3", 12000))(div("text for SuspenseChild from scalajs")))),
      // // div("=====> scala.js suspense test 4"),
      // // sfc5(componentSucceed(null)),
      // testing some implicit and explicit conversions :-)
      // explicit
      jscomponent(new SProps { label = "Almost the end!" }),
      // implicit, tuple of component and args => ReactNode
      (jscomponent, new SProps { label = "Really!" })
    )

}
