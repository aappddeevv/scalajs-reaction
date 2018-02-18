---
layout: docs
title: React Interop
---
# React Interop
The interop layer is structured much like ReasonReact so you can read those documents to understand how scalaj-react is structured. It's pretty much the same thing.

A few differences:
* Instead of a record structure for scala side component, we use a javascript object.
* initialState takes a self so you have access to callback handlders in case any object you need to create requires them. You can use the other methods to get around this or even use mutable state (and change it directly) in another API callback like didMount but this makes it easier.
* A generator function is provided in the reducer to create the state update messages. This is a better approach than having to create your own objects for the state update directive.
* The methods have been changed so that you are *only* provided "self" that make sense. You cannot, for example, see a "state" object if you are using a stateless component.
* You are forced to defined the retained props or initial state in the "new methods" call if they are required. If you have a stateful comonent, you must define them. Mandatory fields on "methods" are indicated by a val. 
```scala
case class MyState(...)
val c = reducerComponent[MyState]("component")
import c.ops._

def make(...) = c.copy(new methods {
    val initialState = MyState()
    render = js.defined{self => ... }
})

```

