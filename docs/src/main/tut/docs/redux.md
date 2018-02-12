---
layout: docs
title: Redux
---
# redux

The scalajs-react integration is not designed to create or write middleware in
scalajs-react. There are some other scala.js libraries that can provide full
access to the redux API. Two projects in particular are:

* [https://github.com/eldis/scalajs-redux](https://github.com/eldis/scalajs-redux)
* [https://github.com/shogowada/scalajs-reactjs](https://github.com/shogowada/scalajs-reactjs)

Both of these contain redux and full scala.js react integration layers. For
example, you can define a redux store in scala using those libraries.

At its core, `react-redux`, the react integration layer with redux, creates
an intermediate component that:
1. Obtains the store from the context (or props if
they are passed down with the name "store" or it finds the storeKey).
1. Calls mapStateToProps (with the redux state and your component's props)  calls
mapDispatchToProps (dispatch func and your component's props) .
1. Sets the the state of the wrapper component (called Connect) that `react-redux` creates. It uses a dummy state. The setState call in Connect forces it and its children to render.

The magic is that react-redux tries to memoize the results so that it reduces
the number of renders in your component if the state changes but those changes
do not change the merged set of props.

Memoizing a function means that a function remembers (stores) its previous input
and on the next function call if the input has not changed, it returns the same
result. Of course, this means the function stores state and potentially holds
onto large amounts of "input" data as well as previousu results--all of which
inflates memory usage.

`react-redux` also uses a Subscription object to subscribe to events hold a link
to their parent subscription and managed the unsubscribe process in the correct
order (child to parent).

It's pretty clear that if we are emphasizing two way integration of components
in scalajs-react, we need to use `react-redux` so that sub-components of scala
components that are using redux continue to work correctly both in production
and for hot reload.

There are 2 approaches to integrating a scala defined react component. You can
create a more advanced integration model using more clever scala features for
wrapping and unwrapping the javascript props but its probably best to keep it
simple and explicit while leaving the ability to employ more advanced
constructs.

* scala side integration: You have a scala component and wish to use it in javascript or scala connected to redux
   * scala: create the scala component then create your "make" function as usual
   * scala: Wrap the component using `Component.wrapScalaForJs`. This creates a ReactJsComponent usable by javascript because it maps a general js.Object to the "make" function. You can export this component for use in javascript however it would not receive redux props.
   * scala: connect the result from the previous step using `redux.connect`. Like `connect` in javascript, you pass in a "component" an get a "component."
   * scala: Using the connected ReactJsComponent from above, wrap it using `elements.wrapJsForScala`. The rationale is that the "connect" call creates another javascript component, so you just need to wrap it for use just like an "imported" javascript component.
   * javascript: use the exported connected component if you want to.
* javascript side integration: You have a scala component and wish to use it in javascript or scala connected to redux.
   * scala: create the scala component
   * scala: export it using the description in the "Exporting" section
   * javascript: define a proxy wrapper
   * javascript: use the new proxied component
   * scala: import the proxied component (since it's a javascript component) and use it

We show both ways below. Its significantly easier to do all of this on the scala
side.

The easy way to think about connected to redux is that if you are going to use
existing infrastucture like `react-redux`, you need to define a component usable
in javascript. Then, since "connect" is a HOC, you need to make the result of
calling the HOC usable in scala again.

You could easily define more clever Reader/Writer typeclasses and macros to do
this, but in scalajs-react we keep them as simple functions you define directly
in order to allow you flexibility to define the API for make as appropriate for
your application.

## scala side
To preserve integration on the scala side, you have to do the same type of
mapping from redux state to props. scalajs-react is explicit in that to create a
component that takes props, you must go through a function (almost always called
"make") and statically know the parameters.

Let's assume that your component's "make" function already has placeholders for
the parameters that you want to pass in regardless of whether they are coming
from the react state or the parent props. We need mapping functions and a way to
connect. Like in javascript, the returned javascript component is a wrapper
around the actual component.

```
import ttg.react.redux

object MyScalaComponentC {
  private val MyScalaComponent = statelessComponent("MyComponent")
  
  // non-native JS trait, we use a "javascript" object for the arguments
  // although that is not necessary, it does make it easier for components
  // that need to integrate with javascript infrastructure. note the usage
  // of props throughout this object. We can do this only because it is a
  // non-native JS trait.
  trait Props extends js.Object {
    val prop1: Int // e.g. required attribute
    // redux props
    var propFromRedux: js.UndefOr[String] = js.undefined
  }
  
  // standard make function. Remove "private" if you want to expose this
  // make for clients that want to use a version of your component not hooked
  // up to redux.
  private def _make(props: Props) = MyScalaComponent.withRender{ self => ... }

  // you could @JSExportTopLevel this component but it would not receive redux props
  private val jsComponent = c.wrapScalaForJs { (jsProps: Props) => ...; _make(jsProps) }

  // connect returns another new "js component" that wraps the "js component" we pass in
  // you could @JSExportTopLevel this component or javascript usage and it would receive redux props
  private val reduxJsComponent = {
    // or use defs...
    val mapStateToProps = (reduxState: js.Dynamic, ownProps: Props): Props = { ... }
    val mapDispatchTopProps = (dispatch: redux.Dispatch, ownProps: Props): Props = { ... }
    redux.connect(MyScalaComponent, mapStateToProps, mapDispatchToProps)
  }
  
  // This "make" creates a component that receives redux props. You
  // have many choices on how to structure your "make" API.
  def make(scalaProp1: Int) = {
    // you do not need to fill in the redux properties
    val props = new Props { val prop1 = scalaProp1 }
    wrapJsForScala(reduxJsComponent, props)
  }
}
```
That's all you need to do. Just like with importing, if you choose your props carefully, you can reduce your API data swizzling work significantly but its up to you. Also, note that the `make` function takes no children. If it had, we would have added a second parameter list (or to the first parameter list) or added children explicitly to the `Props` trait and then added the children to the `wrapJsForScala` function call. It's up to you on how you want to structure your internal API.

## javascript side
This approach just has you create a connected component from the scala exported component. You must ensure that whatever props you expect to come from redux are reflecting in the scala mapping in its wrapper function as defined in "yourComponent.wrapScalaForJs". You can even pass in the dispatch function to the scala side if you want.

In scala
```scala
object MyScalaComponentC {
  // you can mark these private
  val _MyScalaComponent = statelessComponent("MyScalaComponent")
  def makeScala(...) = _MyScalaComponent.withRender(...)

  // Export component so its visible in javascript. The jsProps should be
  // the props from the parent and redux and their manipulation should 
  // reflect what's needed to call makeScala.
  @JSExportTopLevel("MyScalaComponent")
  val exportedComponent = MyScalaComponent.wrapScalaForJS(jsProps => makeScala(...))
  
  // Import the connected component, import location depends on the javascript "bundler"
  @js.native
  @JSImport("ExportModule", "MyScalaComponentRedux") // in ExportModule.js
  object ExportModuleNS extends js.Object {
     val MyScalaComponentRedux: ReactJSComponent = js.native
  }
   
  // Create the standard make, use a scala-side signature you prefer
  def make(aprop: String, children: ReactNode*) = {
     val theprops = ...
     wrapJsForScala(ExportModuleNS.MyScalaComponentRedux, theprops, children:_*)
  }
}
```
Here's the javascript (typescript) side.
```typescript
// ExportModule.js 
import { MyScalaComponent } from "scala" // adjust based on your javascript bundler

// passing null instead of this function means you do not get updates
// when the store changes
function mapStateToProps(state, ownProps) {
  return {
     somevar: state.somevar,
     // ...
  }
}

// If you skip this, the dispatch function is passed to the child automatically.
// If you pass in ownProps, this function gets called more frequently since
// the output *could* change so drop it if you can.
function mapDispatchToProps(dispatch, ownProps) {
  return {
    onWidgetClick: (widgetData: any) => dispatch(someActionCreator(widgetData)),
    // ...
  }
}

export const MyScalaComponentRedux = 
    connect(mapStateToProps, mapDispatchToProps)(MyScalaComponent)
```

At some point, whether scala.js or javascript, you have to map the redux state
to props that eventually reach your child component. In order to preserve
compatibility, you do not have much of a choice regardless of integration
method.

`react-redux` connect actually just composes functions in a redux specific way
to call your component's render method. That's really all its doing.

Now, `MyScalaComponent` can be used in javascript or "re-imported" into scala
using scalajs-react's import capability.

You may find that creating the mapping in javascript is more convenient than
defining the mapping in scala for complex property mappings but use the scala
side version until you reach that point of complexity.
