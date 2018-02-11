# redux

The scalajs-react integration is not designed to create or write middleware in scalajs-react. There are some other scala.js libraries that can provide full access to the redux API. For example see [https://github.com/eldis/scalajs-redux](https://github.com/eldis/scalajs-redux) or [https://github.com/shogowada/scalajs-reactjs](https://github.com/shogowada/scalajs-reactjs). Both of these contain redux and full scala.js react integration layers. For example, you can define a redux store in scala using those libraries.

In its simplest, `react-redux`, the react integration layer with redux, creates an intermediate component that obtains the store from the context (or props if they are passed down with the name "store" or it finds the storeKey), calls mapStateToProps (with the redux state and your component's  props) and calls mapDispatchToProps (dispatch func and your component's props) then calls your component's render method with the merged set of props. The magic is that react-redux tries to memoize the results so that it reduces the number of renders in your component if the state changes but those changes do not change the merged set of props.

Memoizing a function means that a function remembers (stores) its previous input and on the next function call if the input has not changed, it returns the same result. Of course, this means the function stores state and potentially hold onto large amounts of "input" data as well as previousu results--all of which inflates memory usage.

`react-redux` also uses a Subscription object to subscribe to events hold a link to their parent subscription and managed the unsubscribe process in the correct order (parent to child).

It's pretty clear that if we are emphasizing two way integration (parent and child) of components in scalajs-react, we need to use `react-redux` so that sub-components of scala components that are using redux continue to work correctly both in production and for hot reload.

There are 2 approaches to integrating a scala defined react component. You can create more advanced integration model using more clever scala features for wrapping and unwrapping the javascript props, but its probably best to keep it simple and explicit while leaving the ability to employ more advanced constructs.

* javascript side: You have a scala component and wish to use it in javascript or scala connected to redux.
   * scala: create the scala component
   * javascript: define a proxy wrapper
   * javascript: use the new proxied component
   * scala: import the proxied component and use it
* scala side: You have a scala component and wish to use it in javascript or scala connected to redux
   * scala: create the scala component
   * scala: connect the component to the redux store
   * scala: use the connected component
   * scala: export the new connected component
   * javascript: use the exported connected component

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
  val MyScalaComponent = statelessComponent("MyComponent")
  
  // non-native JS trait so we can use create it and use it for jsinterop
  trait Props extends js.Object {
    val prop1: Int // e.g. required attribute
    // redux props
    var propFromRedux: js.UndefOr[String] = js.undefined
  }
  
  private def _make(props: Props) = MyScalaComponent.withRender{ self => ... }

  def mapStateToProps(reduxState: js.Dynamic, ownProps: js.Dynamic): js.Object { ... }

  def mapDispatchTopProps(dispatch: redux.Dispatch, ownProps: js.Dynamic): js.Object { ... }

  // connect returns another new component that wraps the component we pass in
  val MyScalaComponentRedux = redux.connect(MyScalaComponent, mapStateToProps, mapDispatchToProps)
  
  def make(scalaProp1: Int) = {
    // you do not need to fill in the redux properties
    val props = new Props { val prop1 = scalaProp1 }
    wrapJsForScala(MyScalaComponentRedux, props, children:_*)
  }
}
```
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
  @JSImport("ExportModule.js", "MyScalaComponentRedux")
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
defining the mapping in scala.
