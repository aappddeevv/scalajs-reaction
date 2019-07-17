---
layout: docs
title: Defining Components
---
# Defining Components

Use the hooks pattern to define your components. This approach puts you close to
the bare metal JS syntax needed to create hooks. scalajs-reaction provides some
convenience conversions and functions to make this easier and typesafe.

To understand the API you can look at the
[ReasonReact](https://reasonml.github.io/reason-react) docs to understand the
approach to creating Components in scala.js.

In the next section, we show the stateless component. Look at the
demo code for an example of using a reducer component, a component that manages
state.

## Stateless Component Approach #1 (Easiest)

The easiest approach to creating a component is to use a simple function
definition to define your SFC. There are two type aliases defined, SFC0 (no
args) and SFC1 (1 arg), to create components. These components are equivalent to
defining your component in javascript using a javascript function.

```scala
object Component1 {
    // some syntax ceremony so you can call Component1() to create.
    def apply() = sfc
    // SFC0 does not take any arguments.
    val sfc = SFC0 { div("hello world") }
}


object Component2 {
   trait Props extends js.Object {
     var p: js.UndefOr[String] = js.undefined
   }

   def apply(props:Props) = sfc(props)
   
   val sfc = SFC1[Prop]{ props =>
      div(s"""hello world ${p.getOrElse("no-arg")""")
   }
}
```

You may want to use `def apply(props: js.UndefOr[Props] = js.undefined) = ...`
to make it easier to call the function component without arguments.

To use these function-based components, they must be converted to a
ReactElement. For SFC1, you can use `yourFunctionComponent(arg)` to convert the
SFC to a react element and rely on an implicit conversion. You can also
automatically convert a tuple:

```scala
(component2, new Props {...})
```

A nice benefit of using SFCs is that you can export them easily for use in the
js/ts side of your application assuming the parameter is js friendly.

## Stateful Components

Since the API is based on hooks, just use the hooks `useState` or `useReducer`
hooks.

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

Children in scalajs-react are react nodes. Just include the children in a
property in the props object for an SFC1. That's it. 

If you wish, you could alter the scala side API slightly to make it more
friendly. Here's one way but its not super friendly because the children
are inside the Props object.

```scala
object Component {

  trait Props extends js.Object {
    val children: ReactNode
  }
  
  def apply(props: Props) = sfc(props)
  
  val sfc = SFC1[Props]{ props =>
    // ...
  }
}
```

Here's another way. It has a small amount of boilerplate but produces a more
ergonomic API. The children must still be explicit in the `Props` type.

```scala
object Component {
  trait Props extends js.Object {
    val arg: Int
    var children: ReactNode
  }
  
  def apply(props: Props)(children: ReactNode*) = 
    sfc(props.combine(js.Dynamic.literal("children" -> children)))
    // or you could do a new Props {} but literal is easier here.
  
  val sfc = SFC1[Props] { props =>
  }
  
}
```

A simple macro would probably be helpful here.
