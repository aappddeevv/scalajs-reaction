# Welcome to scalajs-react Examples

The apps are powered by a both scala.js and typescript (javascript). Redux is used to store some, but not all, of the application state for testing purposes. Switching tabs remounts the component being displayed. You would never structure an application like this demo program but the demo program is designed to test different aspects of the scalajs-react library.

If you run the demo from a web server, you can use react devtools (browser extension) to see the actions and state changes.

## To Do
Simple to do app. It uses all locally managed state using the scalajs-react reducer built into every component. The state is reset whenever you change tabs so any todos you added or removed are lost. fabric's Pivot unmounts the component when the tab changes.

## Change Redux State
Change the "reduxLabel" state property in the the redux state. You can see the label change reflected in the address manager tab.

## Typescript Wrapping Scala.js
Simple example that shows a typescript component wrapping the HelloWorld component.

## Address Manager
Manage addresses using master detail. The selection is stored in redux but the data is fetched from a fake, local DAO that has a small, random fetch delay. 

The DAO is fairly standard but we wrap the "get/set" selected ids into a "viewmodel" which can only be created by accessing the redux state and dispatch objects at the same time. Hence, we create it when creating the redux component wrapper.

* The detail list sends the active address to the redux addressManager state.
* The redux state is used to provide the "active" address to the summary and detail views, both of which are non-native react components hooked up to the redux state.
* The active item is nulled in redux when the component unmounts, which in this case is when the tab changes based on Pivot works in fabric.
* Redux stores the lastActiveAddressId whenever the active address changes. If the new active address is null, the lastActiveAddressId is not changed of course.

## Hello World
A simple hello world component. It is also exported for use in another tab.
