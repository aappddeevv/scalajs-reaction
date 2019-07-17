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

Model ref and key explicitly in your SFC props.

## Suspense

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
javascript Promise can be used which is how React.lazy and dynamic imports
work. Creating a dynamic import creates as javascript Promise that resolves to
the module content.

Since scala cannot throw a raw javascript Promise, you can create a lazy
component by throwing inside a ts/js defined Component. The demo shows how this
can be done in ts/js. This also means that if you use Suspense, the chain of
parent components must not be a Component object since a `didCatch` proxy is
installed and once that handler is used, you cannot rethrow easily into the js
world again. Hence, you any component that implements the `throwit` trick below
must check for a Promise object and throw it in the javascript world and so
on. Since this is a bit awkward, it is suggested that your Promise throwing
component be defined using `SFC`, which is a pure javscript function component,
not a Component (in this facade), and that it be a direct child of a Suspense
component.

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

We suggest you look at other libraries that may be easier to use such as
[loadable
components](https://www.smooth-code.com/open-source/loadable-components/).


