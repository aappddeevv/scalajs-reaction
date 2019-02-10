---
layout: docs
title: Defining Components
---
# Defining Components

You use a flavor of the "builder" type pattern to define a component.

In scala.js, a Component is a "data structure" (a javascript object = "record")
that holds a callback functions. A proxy is setup in javascript that forwards
the react calls to the scala side Component.

To understand the API you can look at the
[ReasonReact](https://reasonml.github.io/reason-react) docs to understand the
approach to creating Components in scala.js.

In the next section, we show the stateless component example below. Look at the
demo code for an example of using a reducer component, a component that manages
state. There is no `setState` in this facade to manage state, a stateful
component has a buit-in reducer which is essentially a Finite State Machine
(FSM).

## Stateless Component Approach #1 (Easiest)

The easiest approach to creating a component is to use a simple function
definition to define your SFC. There are two type aliases defined, SFC0 (no
args) and SFC1 (1 arg), to create components. These components are equivalent to
defining your component in javascript using a javascript function.

```scala
val component1 = SFC0{ () => div("hello world")
// or val component1: SFC0 = () => div("hello world")

trait Props extends js.Object {
 var p: js.UndefOr[String] = js.undefined
}

val component2 = SFC1[Prop]{ props =>
  div(s"""hello world ${p.getOrElse("no-arg")""")
}
```

You can use a scala object as the parameter and you will probably want to
memoize the function to ensure that rendering is efficient.

To use these function-based components, they must be converted to a
ReactElement. For SFC1, you can use `yourFunctionComponent.toEl(arg)` to
explicitly convert the SFC to a Component for use in other Component
objects. You can also automatically convert a tuple:

```scala
(component2, new Props {...})
```

using an automatic conversions (evil!). Like all react components, both SFC0 and
SFC1 must end up with ha call to `ReactJS.createElement(func object, arg)` to
become an instance of a Component so we need the "component" and the argument
separated to plug into the standard react API.

A nice benefit of using SFCs is that you can export them easily for use in the
js/ts side of your application assuming the parameter is js friendly.

## Stateless Component Approach #2

This approach uses the machinery of the ReasonReact component model.

When we say "defining" a component, we mean that you creating a "template" for a
component instance. When you create a component instance through make or apply,
you are copying that template and providing customizations for rendering,
mounting and other lifecycle actions.

Here is a simple example of defining a component that uses a non-native JS trait
for the props. You can make the props anything you want, individual parameter, a
scala parameter object or a javascript friendly parameter object. You generally
use a javascript friendly parameter object when the component needs to be
exported and used in a javascript environment.

```scala
// You can use individual props or a trait or a scala object.
// It's often convenient to use a subtype of js.Object
// non-native JS trait
trait ToDoProps extends js.Object {
  var todo: ToDo // has property "name"
  var remove: Unit => Unit
  // look no children on this props...
}

object ToDo {
  val c = statelessComponent("ToDo")
  import c.ops._
  // def make(todo: ToDo, remove: Unit => Unit) =
  // or bundle them together into a trait like below
  def make(props: ToDoProps) =
    c.copy(new methods {
      val render = self => {
       div(new DivProps { style = new StyleAttr("display" = "flex")})(
          Label()("Item:"),
          Label()(props.todo.name),
          DefaultButton(new IButtonProps{
             text = "Remove"
             onClick = js.defined{(_: ReactEvent) => props.remove(())}}))
        })
}
```

The parameter `ToDoProps` was set as a `js.Object` trait so it is easier to
interface into the reactjs world but you could have just as easily used a
parameter list and listed each parameter individually.

You need to use a `val` to define `render`. Render is required for all
components so the `methods` that you are creating forces you to define the
render method with a val. The val is standard scala syntax--you need to define
`val` when you instantiate the trait.

Some methods are not required, such as `willMount`. The definition for
`willMount` would look like:

```scala
  willMount = js.defined({ self => 
  })
```

Since `willMount` is optional, it is defined as a var and defaults to
`js.undefined`. The `js.defined` is needed because scala.js cannot work trough
two levels of implicits so `js.defined()` helps with type inference. If you
already use scala.js you are probably used to this.

If you define a stateful component, you are required to define, using `val`,
`reducer` and `initialState` in the same way you defined `render`. You do not
need to define the parameter types although can if desired.

You can pass in children via your ToDoProps or just add them to the make call,
if you need children. Here's an example where they are passed in as a
`js.Array[]`.

```scala
def make(props: ToDoProps, children: js.Array[ReactNode]) =
  c.copy(new methods {
    val render = self => {
       children
     })}
```

Or you can use the scala spread `param: Param, children: ReactNode*) = `.

For stateless components with only a render method, you can make the definition
even shorter:

```scala
object MyComponent {
  val c = statelessComponent("MyComponent")
  import c.ops._
  
  def make(arg: String) = 
    render { self =>
        "something renderable"
    }
}

```

This shortcut works because `c.ops._` imports a method that attaches itself to
`c` and calls `c.copy(new methods { ... })` for you under the covers. If you
want to use any other methods though you'll have to default back to the more
verbose copy approach.

## Stateful Components

There are a few different types of components:

* stateless
* stateless with retained props
* reducer (stateful)
* reducer (stateful) with retained props

You can read the standard ReasonReact documentation to understand the
differences between them.

The methods and their parameters required for each component differ based on the
component type. The "self" parameter also contains slightly different content
based on the method being called. For example, in the unmount method, it does
not make sense to provide access to `onUnmount` method in self. If retained
props are used, `self` also contains those values.

If you are creating a reducer component you need to have methods:

* initialState
* reducer

in addition to the render method. All three are required and hence should be
declared with `val`:

```scala
object MyComponent { 

  case class State(...)
  
  sealed trait Action
  case class ReducerAction1(arg: String)
  case class ReducerAction2(arg: String)  
  
  val c = reducerComponent[State, Action]("MyComponent")
  import c.ops._
  
  def make(arg: String) = 
    c.copy(new methods { 
      val initialState = self => State()
      val reducer = (action, state, gen) =>
          action match { 
            case ReducerAction1(arg) => gen.update(state.copy(...))
            case ReducerAction2(arg) => gen.effect(slf => print("run a side effect with a self"))
            case _ => gen.skip // no state update
          }
      val render = self => {
         div()("Some renderable content")
      }
    })
}
```

You can also use SFC components with hooks. See the React Interop section for
information on hooks.

## Attributes

You can define your make function to take either a list of attributes or an
object that bundles the attributes together. You can choose how you want to
define the make parameters.  In scala if you define your non-native JS traits
(you cannot instantiate a trait annotated with @js.native), then you can create
them without having to use "override val" syntax:

```scala
trait MyOpts extends js.Object {
  var prop1: js.UndefOr[String] = js.undefined
  var prop2: js.UndefOr[String] = js.undefined
}
```
Then you can do:
```scala
new MyOpts { 
  prop1 = "foo",
  prop2 = "bar"
}
```
instead of:

```scala
new MyOpts {
  override val prop1 = "foo"
  override val prop2 = "bar"
}
```

In order to allow your props to contain other attributes, such as those from
div, just have your props inherit from the appropriate attributes trait that is
provided:

```scala
trait MyOpts extends HTMLAttributes[dom.html.Div] {
   // your extra traits
}
```

You may need to filter your props so you can access only your attributes. See
office-ui-fabric-react for an example of a function that can do that
filtering. There are many approaches to supporting this.

## Child Elements

Children in scalajs-react are ReactNode or ReactElement (subclass of
ReactNode). Whatever you do, you need to take your imported react components,
your scalajs-react components and convert them to a ReactNode in order to use
them as children in other scalajs-react components. You have a few ways of doing
this.

* Explicitly using the API: `createElement(YourComponentC.make(...))`
* Using syntax: `YourComponentC.make(..).toEl`.
* Implicit conversions: `YourComponentC.make(...)`

Note that every child must be converted to a `ReactNode` type, including strings
and numbers. There are some functions for doing that:

```scala
val stringElement = stringToElement("blah")
```

which can be quite verbose, hence the implicit conversions which will a wide
range of simple types to a `ReactNode` automatically.

## What's Ops?

The import `c.ops._` imports the "methods" trait so you can customize the
component for your specific render, reducer, and processing needs. It also
contains a few types you can use to help you break out methods into small
parts. For example, it contains the "Self" type so that you can define a
function separate from the methods:

```scala
def renderFooter(self: Self, ...): ReactNode = { ... }
```

If you use state or retained props, it contains `S` and `RP` as well. Generally
though you should not pass self around but the specific arguments you need to
perform the rendering.

The types from a component will *not* work anywhere else but for that
component. The "Self" type is tied to the component as a dependent type. Another
component will have another "Self" type...you cannot inappropriately mix things
up. If you have common processing in your function call that takes a "Self"
merely pass in the parts of "Self" that are needed and factor out the common
processing.

When you create a component, you indicate whether it takes retainded props or
initial state. You are forced to create those when you perform the copy by
having to use a "val" in your "methods" definitions. Optional parameters can be
specified just using "name=value". Functions have to be wrapped in "js.defined"
due to some scala.js needs for type resolution but the initial state or retained
prop values do not need that:

```scala
case class MyState(...)
sealed trait Action
val c = reducerComponent[MyState, Action]("component")
import c.ops._

def make(...) = c.copy(new methods {
    // stateful component so you *must* define initialState
    val initialState = self => MyState()
    // render and most everything else is optional, default render returns null
    val render = self => { ... }
    val reducer = (action, state, gen) => { ... /* return a reducer result */ }
})

```

## Implementation Note

The object returned from "statelessComponent" is actually not the actually
javascript proxy or the scala side component (ComponentSpec, which is a
non-native JS trait). The returned value is a "cake" since the proxy and the
component are created in a cake. To obtain the actual scala side component, use
`myComponentCake.component`. Each cake allocates a proxy and component which
amount to two javascript objects. If you wish to change the internals, you can
reformulate the cake and re-use the different parts with a fairly high degree of
reuse. In a sense, the cake employs the "factory" and "builder" design patterns
when you write your "make" function.

The API is designed so that you don't need to know the representation of any
part of the API other than you use `.copy(new methods { ...})` in your make
function. The provided "ops.copy" function copies the scala side component and
adds your methods to customize its behavior. The copy and merge processing has
the same complexity of using javascript's `Object.assign` method--so its
efficient. The current API, with an emphasis on opaqueness, allows us to
completely change the underlying implementation if need be. reason-react
documents that they use the OCAML record format which maps to a javascript array
and they use that explicitly in their API.
