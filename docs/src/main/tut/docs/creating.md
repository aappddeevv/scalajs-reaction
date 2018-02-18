---
layout: docs
title: Creating Components
---
# Creating Components
Creating react components in scalajs-react uses a "builder" type pattern. 

In scala.js, Component is just a "data structure" (a javascript object = "record") that holds a few values that are callback functions. A proxy is setup on the javascript side that forwards the react calls to the scala side Component. You can create the Component almost in anyway you like but some functions are provided for convenience. It's important to realize that the type parameters you provide are very different than those provided to other scala.js react facades as the reason-react API is different. There is also no `setState` API. Instead this has been replaced by a fine state machine.

It's best to look to the [ReasonReact](https://reasonml.github.io/reason-react) docs to understand the approach to creating Components in scala.js. It's pretty much the same approach.


## Creating a Component
Here's a simple example that uses a non-native JS trait for the props:
```scala
// You can use individual props or a trait or a scala object.
// It's often convenient to use a subtype of js.Object
// non-native JS trait
trait ToDoProps extends js.Object {
  var todo: ToDo // has property "name"
  var remove: Unit => Unit
  // look no children on this props...
}

object ToDoC {
  val ToDo = statelessComponent("ToDo")
  import ToDo.ops._
  // def make(todo: ToDo, remove: Unit => Unit) =
  // or bundle them together into a trait
  def make(props: ToDoProps) =
    ToDo.copy(new methods {
      render = js.defined{self => 
        <.div(^.style := Style("display" := "flex"))(
          Label()("Item:"),
          Label()(props.todo.name),
          defaultButton(
            F.text := "Remove",
            ^.onClick ==> ((_: ReactEvent) => props.remove(())))().toEl)}
        })
}
```
You can pass in children via your ToDoProps or just add them to the make call, if you need children. Here's an example where they are passed in as a `js.Array[]`.
```scala
def make(props: ToDoProps, children: js.Array[ReactNode]) =
  ToDo
    .withRender{self =>
    }
```
Or you can use the spread approach:
```scala
def make(props: ToDoPropsp, children: ReactNode*) =
  ToDo
    .withRendedr{self => 
    }
```
## Attributes
Notice that you can define your make function to take either a list of attributes or an object that bundles the attributes together. You can choose how you want to define the make parameters.

In scala if you define your non-native JS traits (you cannot instantiate a trait annotated with @js.native), then you can create them without having to use "override val" syntax:
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
In order to allow your props to contain other attributes, such as those from div, just have your props inherit from the appropriate attributes trait that is provided:
```scala
trait MyOpts extends HTMLAttributes[dom.html.div] {
   // your extra traits
}
```
You may need to filter your props so you can access only your attributes. See office-ui-fabric-react for an example of a function that can do that filtering. There are many approaches to supporting this.

## Children
Children in scalajs-react are ReactNode or ReactElement (subclass of ReactNode). Whatever you do, you need to take your imported react components, your scalajs-react components and convert them to a ReactNode in order to use them as children in other scalajs-react components. You have a few ways of doing this.

* Explicitly using the API: `createElement(YourComponentC.make(...))`
* Using syntax: `YourComponentC.make(..).toEl`.
* Implicit conversions: `YourComponentC.make(...)`

Note that every child must be converted to a `ReactNode` type, including strings and numbers. There are some functions for doing that:
```scala
val stringElement = stringToElement("blah")
```
which can be quite verbose, hence the implicit conversions which will convert simple types to a `ReactNode`.

## What's Ops?
The import `myComponent.ops._` imports the "methods" trait so you can customize the component for your specific render, reducer, and processing needs. It also contains a few types you can use to help you break out methods into small parts. For example, it contains the "Self" type so that you can define a function separate from the methods:
```scala
def renderFooter(self: Self, ...): ReactNode = { ... }
```
If you use state or retained props, it contains `S` and `RP` as well.

These types will *not* work anywhere else but for this component. Another component will have another "Self" type...this way you cannot mix things up accidently.

When you create a component, you indicate whether it takes retainded props or initial state. You are forced to create those when you perform the copy by having to use a "val" in your "methods" definitions. Optional parameters can be specified just using "name=value". Functions have to be wrapped in "js.defined" due to some scala.js needs for type resolution but the initial state or retained prop values do not need that:
```scala
case class MyState(...)
val c = reducerComponent[MyState]("component")
import c.ops._

def make(...) = c.copy(new methods {
    // stateful component so you *must* define initialState
    val initialState = MyState()
    // render and most everything else is optional, default render returns null
    render = js.defined{self => ... }
})

```
