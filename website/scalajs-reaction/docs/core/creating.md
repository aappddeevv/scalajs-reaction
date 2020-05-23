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

- https://overreacted.io/a-complete-guide-to-useeffect
- https://www.robinwieruch.de/react-hooks-fetch-data
- https://itnext.io/how-to-create-react-custom-hooks-for-data-fetching-with-useeffect-74c5dc47000a
- https://www.codebeast.dev/react-memoize-hooks-useRef-useCallback-useMemo
- https://blog.logrocket.com/react-hooks-cheat-sheet-unlock-solutions-to-common-problems-af4caf699e70
- https://usehooks.com/

## Components

The easiest way to create a component is to use a simple function.

```scala
object Component1 {
    // defining apply so you call Component1() to create.
    def apply() = render()
    val render: ReactFC0 = () => div("hello world")
}


object Component2 {
  val NAME = "Component2"
   trait Props extends js.Object {
     var p: js.UndefOr[String] = js.undefined
   }

   def apply(props:Props) = render.elementWith(props)

   val render: ReactFC[Props] = props =>
      div(s"""hello world ${p.getOrElse("no-arg")""")
   render.displayName(NAME)
}
```

The components must be converted to a ReactNode for rendering so you
need both the component definition (in this case the `render` function)
and some props together to pass along to react. React requires that you
provide a "description" and "data" on how to render a component but
ensure that you do not "run" the rendering function to actually create the
component. React will put the rendering function and props together
at the right time for you. To create the "description" you need some
way for scala to detect that it has both parts. `.elementWith`
is one way to do that but you could just call `createElement` yourself, see below.

Hence, you do not to use `ReactFC0` or `ReactFC`. Define your render function as a
normal javascript function `js.Function1[Props, ReactNode]` directly.
The types `ReactFC` force a conversion from a scala function to
a javascript function. That's all it does.

In the above `def apply(props: Props) = render.elementWith(props)` there is
an implicit helper that provides the `.elementWith` syntax on a scala or
javascript function `Props => ReactNode`.

You can skip all of this by defining directly:

```scala
  def apply(props: Props) = createElement(render, props)
  val render: js.Function1[Props, ReactNode] = props => div("hello world")
```

The reason you need to specify a few types it the right places is to
activate the syntax or implicit conversions. Scala function to javascript
function conversion is part of scala.js predef so it happens automatically.
But to ensure that a `div("hello world")` is converted to a `ReactNode`
(the return type of your function)
you need to set the return type of `render` so that another implicit will
pick up the `div` declaration in the `vdom` library and convert that
to a `ReactNode` via `createElement`.

Another implicit conversion is provided so you can also
automatically convert a tuple of the component and props:

```scala
// use this anywhere a ReactNode is expected
(component2, new Props {...})
```

A nice benefit of using simple functions is that you can easily export them
for use in the
js/ts side of your application assuming the parameter is js friendly.

Here's a tuple way to define your component using the tuple
concept above. The `ReactElementTuple` helps coerce `render`
to a javascript function, that's all it does.

```scala
object Components {
  trait Props extends js.Object { val name: String }

  // need this type signature
  val render: Props => ReactNode = props => div(s"hello ${name}")

  // Call like Components.HelloWorld(...)
  def HelloWorld(props: Props): ReactElementTuple[Props] = (render, props)
```

You could also use:

```scala
  // use any name you want as long it makes you happy!

  // Call like Components(theProps)
  def apply(props: Props) = render.elementWith(props)

  // Call like Components.make(theProps)
  def make(props: Props) = render.elementWith(props)

  // Call like Components.doit(props),
  def doit(props: Props) = render.toEl(props)
```

`.toEl` is an alias for to `.elementWith`.

You can define your own protocol for creating a component as long as:

- Your component is a js function or is converted to a js function.
- You separate out the "component" and the props in a way that allows
  `react.createElement` to be called.

This facade provides a few implicit conversions to improve type inference
and calling `createElement` at the right time. That's pretty much all
this facade does.

## Components with `Props[T]`

If your `Props` takes a `T`, you can create a class:

```scala
class MyComponent[T] {
 // component stuff here like above
}
```

If you want to keep the `T` close to the render function
you need to create the parameterized render but also
a stable value:

```scala
class MyComponent {
   trait Props[T] extends js.Object {
     val items: js.UndefOr[js.Array[T]] = js.undefined
   }

   def apply[T](props: Props[T]) = stable_render.elementWith(props)
   
   def render[T]: ReactFC[Props[T]] = props => { ... }
   def stable_render: ScalaJSFunctionComponent1 = render[js.Object]   
}
```

So pushing down the `T` from the class costs you 1 extra line
of code to stablize the value.


## Stateful Components

Since the API is based on hooks, just use the hooks `useState` or `useReducer`
hooks inside your component. You can define your own hooks using standard
scala functions, they do not have to be javascript functions.

## Attributes

A component's props can take either a list of attributes or an
object that bundles the attributes together. You can choose how you want to
define the make parameters. If you define your non-native JS traits
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

Include a children property in your `Props` object. That's it.

If you wish, you could alter your API slightly to make it more friendly.

For example, here's a component that exposes a children property but its awkward
sometimes to use it that way.

```scala
object Component {

  trait Props extends js.Object {
    val children: ReactNode
  }

  def apply(props: Props) = render.elementWith(props)

  val render = Props => ReactNode = ???
}
```

You can use scala's standard multiple parameter lists feature
to produces a more
ergonomic API. The children must still be explicit in the `Props` type if
you want to access them.

```scala
object Component {
  trait Props extends js.Object {
    val arg: Int
    var children: js.Undefined[ReactNode] = js.undefined
  }

  // ergonomic API
  def apply(props: Props)(children: ReactNode*) = react.createElementN(render, props)(children:_*)

  val render: Props => ReactNode = ???
}
```
