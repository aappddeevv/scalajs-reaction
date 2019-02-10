---
layout: docs
title: Exporting Components to Javascript
---

# Exporting

Exporting a component for use in javascript environments requires you to map the
props interface in scalajs-react to javascript. By providing a function like
`jsProps: js.Object => Component`, you can map your components.

The interface to export your component is actually quite simple although you can
create your own "mapping" framework if you want to make your mapping process
easier. For example, you could create a set of "Write" typeclasses.

Here's the API. This is all you need to do:

```scala
// ToDoProp is a non-native JS trait
@JSExportTopLevel("ToDo")
val exportedToDo = ToDo.wrapScalaForJs((jsProps: ToDoProps) => make(jsProps))
```

We mentioned in the previous section that if your make/apply parameter object is
javascript friendly, such as ToDoProps, it's easier to interface. In fact, if
you do it this waya and there is no other processing you need to perform on the
properties, you can shrink the above to:

```scala
// ToDoProp is a non-native JS trait
@JSExportTopLevel("ToDo")
val exportedToDo = ToDo.wrapScalaForJs(make(_))
```

The type return is `ReactJsComponent` which is the same type you use for
importing. Importing is described in the next section.

Note that when you export at the top level using `@JSExportTopLevel`, your
export is at the module level and depending on your javascript bundler, it is
accessible just like any other export you may create in a javascript module.

If you have logic that needs to run to convert the props, just put them in the
wrap function:

```scala
@JSExportTopLevel("ToDo")
val exportedToDo = ToDo.wrapScalaForJs{(jsProps: ToDoProps) => 
    // conversion logic
    val prop1 = convert1(jsProps)
    val prop2 = convert2(jsProps)
    // this component's "make" takes 2 arguments broken out
    make(prop1, prop2)
}
```

## Exporting from scala.js and javascript bundling

If you had setup your Component to take a js.Object derived trait/class as the
only argument, then you could use that for `jsProps`'s type as shown in the
first example above. Or, if your component has multiple input parameters, then
your mapping function must take a js.Object and break out the values and call
`make` as appropriate.

When you bundle, say using webpack, your `ToDo` will be available at the name
`WebPackLibName.ToDo` because ToDo was exported at the module level and webpack
bundles all modules together and manipulates those exports. You could, as
indicated in the scala.js documentation, use an export name that has multiple
levels in it to help separate out your scalajs-react components into groups:

```scala
@JSExportTopLevel("PIM")
object PIM extends js.Object
  @JSExport("ToDo")
  ...
}
```

in which case you would access it in javascript via
`WebPackLibName.PIM.ToDo`. This is standard scala.js export syntax.

If you want to use typescript as your "javascript" language for
external-to-scala.js components, you will need to reproduce your typescript
definitions for the parameters manually. Or you could write them in typescript
and translate them into scala.js :-). It's up to you.

## You Can Get Fancy But It's Probably Not Worth It

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

The reason it may not be worth it to be too fancy is that the conversion between
props is not shared between components. If something in your props is common
across scala components, then you can create a function to convert that part of
the data.
