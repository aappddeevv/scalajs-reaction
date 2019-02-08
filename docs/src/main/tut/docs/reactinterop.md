---
layout: docs
title: React Interop
---

# React Interop

The interop layer is structured much like ReasonReact so you can read those
documents to understand how scalaj-react is structured. It's pretty much the
same thing.

A few differences:
* Instead of a record structure for scala side component, we use a javascript
  object.
* initialState takes a `Self` so you have access to callback handlders in case
  any object you need to create for your state requires them. You can use the
  other methods to get around this or even use mutable state (and change it
  directly) in another API callback like didMount but this makes it easier.
* A generator function is provided in the reducer to create the state update
  messages. This is a better approach than having to create your own objects for
  the state update directive.
* The methods have been changed so that you are *only* provided a "self"
  function parameter that make sense for that method. You cannot, for example,
  see a "state" object in "self" if you are using a stateless component.
* You are forced to defined the retained props or initial state in the "new
  methods" call if they are required. If you have a stateful comonent, you must
  define them. Mandatory fields on "methods" are indicated by a val as show below.

```scala
case class MyState(...)
val c = reducerComponent[MyState]("component")
import c.ops._

def make(...) = c.copy(new methods {
    val initialState = MyState()
    val render = self => { ... }
})

```

## Latest reactjs features

### Fragment
scalajs-react supports fragments through `fragmentElement`.
```scala
import ttg.react.elements._

val frag = fragmentElement()(
    MyComponent.make(...),
    MyOtherComponent.make(...)
)
```

### Context Provider
The new context provider API is also support as found in react v16.3.
```scala
import ttg.react._

object Contexts {
  case class App(name: String = "noname")
  val appContext = context.make[App](App())
}

// provider
object Root {
   import Contexts._
   import ttg.react.context._ // import implicits
   
   val Root = statelessComponent("Root")
   
   def make(namePrefix: String) =
     Root.copy(new methods {
       val render = self => {
         // or use the default using makeProvider(...children...)
         appContext.makeProvider(App(name=namePrefix + appContext.currentValue.name))(
           MyComponent.make()
         )}
     }
}

// consumer
object MyComponent {
    import Contexts.
    import ttg.react.context._ // import implicit
    
    val MyComponent = statelessComponent("MyComponent")
    
    def make() =
      MyComponent.copy(new methods {
        val render =  self => {
           appContext.makeConsumer{ app =>
             app.name
           }}
      }
}
```

You can provide a scala object, but you can also provide a non-native JS trait
that is automatically merged together for a partial update from the default
value provided when the context was created. This approach is about "props
merging" much like you find in redux. You should take this into account when
updating the value in the provider. It may be much more efficient to use a
non-native JS trait (i.e. a javascript object). The API is in flux until 16.3 is
published.

### Hooks

Hooks can be found using the `React` prefix such as `React.useState`. Hooks must
be run inside a "function component." You can define a function component using
the Stateless Functional Component (SFC) object:

```scala
val sfc = SFC[js.Object]{ _ =>
 val (s, setState) = React.useState[String]("initial state")
 div(
    div(s"my function component with useState: $s"),
    button(new ButtonProps {
      onClick = js.defined(_ => setState("next state"))
    })("Click me")
 )
}
```

See the demo examples for code copied from
[usehooks.com](http://usehooks.com). Currently, `useImperativeHandler` is not
implemented.

You can write your own hooks by defining the dependent hooks just like in the js
examples. Your authored hooks can be defined in a scala function as long as they
are *used* in a function component as descried above:

```scala
def myHook(key: String): Boolean = {
    val (keyPressed, setKeyPressed) = React.useState[Boolean](false)
    ...
    keyPressed
}
```

### Suspense

Suspense allows you to delay rendering until a js.Promise completes. In the
react rendering engine, suspended components continue to display their content
until the effect completion, with a possible fallback in case the effect
fails. This allows you to continue to show user relevant content until the
rendering completes. While you can do some of this today, without using
suspense, the user-experience is bit jerky. You throw the promise in the
rendering function.

Note that lazy is not currently supported.

The Suspense element is not currently supported.


