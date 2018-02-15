---
layout: home
---
# What is scalajs-react?

A scala.js facade over reactjs that provides a functional API in the spirit of ReasonReact. It focuses on integration with existing javascript-based react applications so you can plug in scalajs-react compopnents to any part of the component hierarchy..

The library is designed to create components that can be used and consumed by other react components in javascript and scala fairly seamlessly.

# ReasonReact
The scalajs-react interface is built on the model provided by [ReasonReact](https://reasonml.github.io/reason-react). You can apply the same concepts in that library to this API.

# API Documentation

* [all modules](api/ttg/react)

The scalajs-react redux integration focuses on allowing you to connect your component to an existing redux store. It does not try to provide access to the full redux API. It is essentially the scala.js side version for scalajs-react much like the project `react-redux`.

# Demo
A WIP demo is here:
[Demo](static/index.html)
