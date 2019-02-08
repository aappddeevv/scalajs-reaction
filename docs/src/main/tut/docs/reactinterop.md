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

## Ref and Key

Ref and key are special properties used by react to assist in accessing the
component instance or managing arrays of values. These properties are ripped out
of props and processed special by reactjs.

In this facade, the scala "props" are appended to the real props under a
specially named key. If you were to add key and prop to your scala prop,s they
would not be seen. So...we need a way to create a scala Component with key and
ref.

To add ref and key you can add them using `createElement` directly or using the
implicit syntax support, `yourComponent.toEl(Some("key"), Some(yourReactRef))`.

The entire ref and key issue can be confusing because in reactjs libraries
imported into scala.js, you can typically define the key and prop in the
imported "props" type definition and reactjs will find them. Since these
elements are created differently than scala components, it just works that way.

## Plain javascript

You can write your components using raw javascript content. For example, as long as you use js semantics you could do the following:

```scala
val jscomponent: js.Function0[ReactNode] = () => {
  div("some content")
}
```

But you'll need to create an instance using the raw `ReactJS.createElement` function:

```scala
   ...some othercomponent render method...
   ReactJS.createElement[Null](jscomponent, null)
   ...more othercomonent render method...
```

This a good alternative to declaring `statelessComponent` objects and their
related render method.

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
are *used* in a function component as described above:

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

There are a couple of complications.
* You cannot throw a raw promise in scala.js. It's always wrapped up before it
  hits the js side.
* You cannot call `import` as a function as its really a keyword in javascript
  now.
  
To use Suspense and React.lazy, you'll need to do one of a few different
approaches:
* Create a .ts/.js file that exports a component that is created using
  `React.lazy`. The component needs to be non-scalajs component since scalajs
  bundles *everything* together. Import that component as a ReactLazyComponent
  and then call `wrapJsForScala` to create a scala component.
* Create a .ts/.js file and include only exports of the form `() =>
  import("amodule")`. The import type is DynamicImportThunk and that can used as
  an argument to scala's `React.lazy`. You need to define it as a function so
  the ts/js Promise is not started immediately. Since you are created deferred
  computations, you can define as many of these as you want in a single file and
  import them all at once.
* Externalize the entire Suspense and React.lazy machine and just import a
  component from a js/ts module in its entirety.

In react 16.8, only lazy loading is supported but any component that throws a
javascript Promise can be used which is actually how React.lazy and dynamic
imports work. Creating a dynamic import creates as javascript Promise that
resolves to the module content.

Since scala cannot throw a raw javascript Promise, you can create a lazy
component by throwing inside a ts/js defined component. The demo shows how this
can be done in ts/js.

As a trick, you can define an scala import module that imports a single function
that throws an object for you.

```javascript
export default throwit(e) { throw e }
```

then import it

```scala
@js.native
@JSImport("throwit", JSImport.Default)
object throwit { 
   def apply(t: js.Any): Unit = js.native
}
```

Then throw it in your function, such as a SFC

```scala
val sfc = SFC[js.Object]{ _ =>
  val x = throwit(..code that creates a promise...)
  div("My nodes to display")
}
```

But the trick is to have a js.Promise that is smart enough to resolve once the
content has been loaded. This typically uses a backing store of some sort
e.g. cache. If you naviavely try this and its a new promise that never completes
(resolve or fail), you may hang the UI at some point since the promise never
completes.

It's not clear how useful this all is although the lazy loading use case is
interesting in a mixed project.

Because of the Suspense feature, `didCatch` has been removed from scala
Components until the interaction between them is better understood.
