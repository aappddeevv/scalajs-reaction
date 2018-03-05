# Welcome to scalajs-react Examples

The apps are powered by a both scala.js and typescript (javascript). Redux is
used to store some, but not all, of the application state for testing
purposes. Switching tabs remounts the component being displayed. You would never
structure an application like this demo program but the demo program is designed
to test different aspects of the scalajs-react library.

If you run the demo from a web server, you can use redux devtools (a browser
extension) to see the actions and state changes as with any redux-based
application.

All of the examples are a Work in Progress (WIP) so some of them do not display anything or crazy things. Other tabs are more polished.

## To Do
Simple to do app. It uses all locally managed state using the scalajs-react
reducer built into every component. The state is reset whenever you change tabs
so any todos you added or removed are lost upon tab change. fabric's Pivot
unmounts the component when the tab changes.

To Do uses css-in-js via the fabric scala facade.

## Change Redux State
Change the "reduxLabel" state property in the the redux state. You can see the
label change reflected in the address manager tab.

## Typescript Wrapping Scala.js
Simple example that shows a typescript component wrapping the HelloWorld
component.

## Address Manager
Manage addresses using master detail. The selection is stored in redux but the
data is fetched from a fake, local DAO that has a small, random fetch delay.

The DAO is fairly standard but we wrap the "get/set" selected ids into a
"viewmodel" which can only be created by accessing the redux state and dispatch
objects at the same time. Hence, we create it when creating the redux component
wrapper.

For testing purposes, we store the address manager state in multiple places and
bring them together which is why this example is good for testing bringing
together component arguments wherever they come from but is not a good example
of how you would actually code state management for a component.

fabric remounts a component when the tab changes. Hence, the last active item
(or active item when you are restricted to single select) must be passed in from
the parent component owner and *not* sourced from redux directly. However, the
component should continuously update the last selected address key in the redux
store whenever it changes.

* The detail list sends the active address (and hence the active address key) to
  the redux addressManager state when it changes.
* The redux state is used to provide the "active" address object to the summary
  and detail view. The summary view is in typescript and the detail view in
  scala. Both components are presentation components.
* The active item is nulled in redux when the component unmounts, which in this
  case is when the tab changes based on Pivot works in fabric.
* If a null active address is sent to redux, it is assumed that the component
  unmounted and the last active address id is not updated in redux so that it is
  available when the components remounts.

There is a CSS var containing the height of the footer. It can be incremented using a button in the command bar. The change does not go through the reducer and hence, not through a re-render in react. Going through the reducer is the proper way to do it. For the moment, it modifies the CSS var directly and the visual layout sometimes takes a moment to catch up.

## Hello World
A simple hello world component. It is also exported for use in another tab.

## Tag Test
Display a large number of standard HTML tags.

## Weather
Use api.openweather.org to obtain temperature forecasts. Only cities that are listed at openweather.org can be used--so try some major cities. Weather results are cached and the city name is stored in the state, etc.

## Graph
Show a graph of relationships. This does not show much "reducer" wise but does show that state can be large, complex and spread out to some degree.

## Movies
Using r16.4 alphas, demonstrate some of the asynchronous rendering that react can perform if your rendering method throws a "promise" that completes when your component is ready to be rendered.

This is bleeding edge stuff from an early March 2018 conference where Dan previewed some of their changes based on their react "fiber" core.
