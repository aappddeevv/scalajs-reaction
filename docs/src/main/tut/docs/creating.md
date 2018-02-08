---
layout: docs
title: Creating Components
---
Creating react components in scalajs-react uses a "builder" type pattern. 

In scala.js, Component is just a "data structure" (a record really) that holds a few values and functions. A proxy is setup on the javascript side that forwards the react calls to the scala side Component. You can create the Component almost anyway you want but some functions are provided for convenience. It's important to realize that the type parameters you provide are very different than those provided to other scala.js react facades as the API is very different as well. For example, scalajs-react includes a simple router in every component. There is also no `setState` API. Instead this has been replaced by a fine state machine.

It's best to look to the [ReasonReact](https://reasonml.github.io/reason-react) docs to understand the approach to creating Components in scala.js. It's pretty much the same approach.


## Creating a Component
Here's a simple example that uses a non-native JS trait for the props:
```scala
// You can use individual props or a trait or a scala object.
// It's often convenient to use a subtype of js.Object
// non-native JS trait
trait ToDoProps extends js.Object {
  val todo: ToDo // has property "name"
  val remove: Unit => Unit
  // look no children on this props...
}

object ToDoC {
  val ToDo = statelessComponent("ToDo")
  // def make(todo: ToDo, remove: Unit => Unit) =
  // or bundle them together into a trait
  def make(props: ToDoProps) =
    ToDo.
      .withRender{self => 
        <.div(^.style := Style("display" := "flex"))(
          Label()("Item:"),
          Label()(props.todo.name),
          defaultButton(
            F.text := "Remove",
            ^.onClick ==> ((_: ReactEvent) => props.remove(())))().toEl)}
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
