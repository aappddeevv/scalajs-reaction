---
layout: docs
title: React Interop
---
# React Interop
The interop layer is structured much like ReasonReact so you can read those documents to understand how scalaj-react is structured. It's pretty much the same thing.

A few differences:
* Instead of a record structure for scala side component, we use a case class.
* initialState takes a self so you have access to callback handlders in case any object you need to create requires them. You can use the other methods to get around this or even use mutable state (and change it directly) in another API callback like didMount but this makes it easier.
* A generator function is provided in the reducer to create the state update messages. This is a better approach than having to create your own objects for the state update directive.

