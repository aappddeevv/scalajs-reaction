---
id: exporting
title: Exporting From Scala to JS
---

Exporting a component for use in javascript environments requires you to export
the function component. It is quite easy.

Since your function component already takes a js object as the props parameter,
you can simple export the component using scala.js export API.


Here's the API. This is all you need to do:

```scala
object MyComponent {
  trait Props extends js.Object { ... }
  def apply(props: Props) = render.elementWith(props)
  
  @JSExportTopLevel("MyComponent") // or whatever name you want  
  val render: ReactFC[Props] = props => { ... }
}
```

The export can be placed anywhere in the code but it was included in the
Component object for convenience.

If your `Props` takes type parameters, you have to lock the type down
to create a stable value before exporting:

```scala
  trait Props[T] ...
  def apply[T](props: Props[T]) = stable_render.elementWith(props)
  def render[T]: ReactFC[Props[T]] = props => { ... }

  // Use any T type since js does not care about types
  // But since it was def above, you need to create a stable value.
  @JSExportTopLevel("MyComponent")
  val stable_render: ScalaJSFunctionComponent1 = render[js.Object]
```

## Exporting from scala.js and javascript bundling

When you bundle, say using webpack, your `MyComponent` will be available at the
name `WebPackLibName.MyComponent` because MyComponent was exported at the module level.
WebPackLibName is the name of the access point given to the webpacka configuration.
Since MyComponent was exported at the top level of this access point, the
dotted notation is all you need to access it.

If you want to use typescript as your "javascript" language for
external-to-scala.js components, you will need to reproduce your typescript
definitions for the parameters manually. Or you could write them in typescript
and translate them into scala.js :-). It's up to you.
