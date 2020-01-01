---
id: creating
title: Defining Components
---

Use the hooks pattern to define your components. This approach puts you close to
the bare metal JS syntax needed to create hooks. scalajs-reaction provides some
convenience conversions and functions to make this easier and typesafe.

To understand the API you can look at the
[ReasonReact](https://reasonml.github.io/reason-react) docs to understand the
approach to creating Components in scala.js.

In the next section, we show the stateless component. Look at the
demo code for an example of using a reducer component, a component that manages
state.

Some good tutorials on react hooks and how to use them can be found at:
* https://overreacted.io/a-complete-guide-to-useeffect
* https://www.robinwieruch.de/react-hooks-fetch-data
* https://itnext.io/how-to-create-react-custom-hooks-for-data-fetching-with-useeffect-74c5dc47000a
* https://www.codebeast.dev/react-memoize-hooks-useRef-useCallback-useMemo
* https://blog.logrocket.com/react-hooks-cheat-sheet-unlock-solutions-to-common-problems-af4caf699e70
* https://usehooks.com/

## Stateless Component

The easiest way to create a component is to use a simple function.
There are helpers defined:

* SFC0: Create a function component with no args.
* SFC1: Create a function component with "prop" args.

These components are equivalent to
defining your component in javascript using a javascript function.

```scala
object Component1 {
    // defining apply so you call Component1() to create.
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

You can use `def apply(props: js.UndefOr[Props] = js.undefined) = ...`
to make it easier to call the function component without arguments.

The components must be converted to a
ReactElement for rendering. 

For SFC1, you can use `Component2(arg)` to convert the
SFC to a react node. An implicit conversion takes a SFC0/SFC1
and calls "createElement" for you. 

You can also
automatically convert a tuple of the component and props:

```scala
(component2, new Props {...})
```

A nice benefit of using SFC and this approach is that you can easly export them 
for use in the
js/ts side of your application assuming the parameter is js friendly.

## Stateful Components

Since the API is based on hooks, just use the hooks `useState` or `useReducer`
hooks inside your component. You can define your own hooks using standard
scala functions, they do not have to be javascript functions.

## Attributes

A component's props can take either a list of attributes or an
object that bundles the attributes together. You can choose how you want to
define the make parameters.  If you define your non-native JS traits
 with vars you can create
them without having to use "override val" syntax:

```scala
trait MyOpts extends js.Object {
  var prop1: js.UndefOr[String] = js.undefined
  var prop2: js.UndefOr[String] = js.undefined
}
```

You can create your props using "new" and without the "val" or "override"
keywords:

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

Scala 3 should allow you to create these javascript objects (that's what they
are under the hood) without needing to use the word new. You could also define
a companion object and define a constructor function that is more ergonomic.

In order to allow your props to contain other attributes, such as those from
div, just have your props inherit from the appropriate attributes trait that is
provided:

```scala
trait MyOpts extends HTMLAttributes[dom.html.Div] {
   // your extra traits
}
```

You may need to filter your props so you can access only your attributes. See
office-ui-fabric-react for an example of a filtering function 
. 

## Child Elements

Include a children property in a
props object for an SFC1. That's it. 

If you wish, you could alter your API slightly to make it more
friendly. 

For example, here's a component that exposes a children property
but its awkward. 

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

You can use scala's standard multiple parameter lists feature
to produces a more
ergonomic API. The children must still be explicit in the `Props` type.

```scala
object Component {
  trait Props extends js.Object {
    val arg: Int
    var children: js.Undefined[ReactNode] = js.undefined
  }

  // include basic api
  def apply(props: js.UndefOr[Props] = js.undefiend) = sfc(props)
 
  // ergonomic API 
  def apply(props: Props)(children: ReactNode*) = 
    sfc(props.combine(js.Dynamic.literal("children" -> children)))
    // or you could do a new Props {} but literal is easier here.
  
  val sfc = SFC1[Props] { props =>
    // ...
  }
  
}
```
