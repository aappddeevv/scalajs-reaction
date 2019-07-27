---
id: intro
title: What is it?
---

Note the new website is a work in progress. API docs and the example demo are
not hooked up yet...

# A scala.js facade

scalajs-reaction is a scala.js facade over reactjs that provides a functional
API in the spirit of ReasonReact. It focuses on integration with existing
javascript-based react applications so you can plug in scalajs-react components
to any part of the component hierarchy.

The library is designed to create components that can be used and consumed by
other react components in javascript and scala fairly seamlessly.

# ReasonReact
The interface is built on the model provided by
[ReasonReact](https://reasonml.github.io/reason-react). You can apply the same
concepts in that library to this API.

# API Documentation

* [API: all modules](api/ttg/react)

The API focuses on integration, for example, reactjs redux integration, allows
you to connect your component to an existing redux store if you want to use
redux. This faced is essentially the scala.js side version for scalajs-reaction
much like the project `react-redux`.

The API link does not include some of the individual projects that provide
facades for additional functionality but it provides a significant amount of the
core facades. In other words, there is more there so check the source!

# Demo
A WIP demo is here:
[Demo](static/index.html)
