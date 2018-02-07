---
layout: docs
title: Exporting Components to Javascript
---
Exporting a component for use in javascript environments requires you to map the props interface in scalajs-react to javascript. By providing a function like `jsProps: js.Object => Component`, you can map your components.

The interface to export your component is actually quite simple although you can create your own "mapping" framework if you want to make your mapping process easier. For example, you could create a set of "Write" typeclasses.

Here's the API. This is all you need to do:
```scala
```scala
@JSExportTopLevel("ToDo")
val exportedToDo = ToDo.wrapScalaForJs((jsProps: ToDoProps) => make(jsProps))
```
Note that when you export at the top level using `@JSExportTopLevel`, your export is at the module level.

If you create your own typeclass system you could:
```scala
trait Writer[A <: js.Object] {
   def toJs(props: A): Component[_,_,_,_]
}

object MyExporter {
  def myExporter[T](component: Component[_,_,_,_])(implicit writer: Writer[T]) = 
     component.wrapScalaForJs(component, writer.toJs _)
}
```
It's not clear how much value this adds overall but there are many scala ways to define a function wrapper e.g. a Kleisli, that could be useful.

If you had setup your Component to take a js.Object derived trait/class as the only argument, then you could use that for `jsProps`'s type as show in the first example above. Or, if your component has multiple input parameters, then your mapping function must take a js.Object and break out the values and call `make` as appropriate.

Hence, when you bundle, say using webpack, your `ToDo` will be available at the name `WebPackLibName.ToDo` because ToDo was exported at the module level and webpack bundles all modules together and manipulates those exports. You could, as indicated in the scala.js documentation, use an export name that has multiple levels in it to help separate out your scalajs-react components into groups:
```scala
@JSExportTopLevel("PIM.ToDo")
...
```
in which case you would access it via `WebPackLibName.PIM.ToDo`.
