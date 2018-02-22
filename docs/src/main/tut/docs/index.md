---
layout: docs
title: Getting Started
---
# Getting Started

Like any scala.js project to get started you need to include the libraries in
your build. the artifacts are published to a bintray only and not jcenter or
maven in general.

```scala
// bintray resolvers
resolvers += Resolver.bintrayRepo("aappddeevv", "maven")
val scalaJsReactVersion = "latest.version"
//  or
//val scalaJsReactVersion = "0.1.0-M1"

// grab the the latest version or use a specific version
libraryDependencies ++= Seq(
    "ttg" %%% "scalajs-react-core" % scalaJsReactVersion,
    "ttg" %%% "scalajs-react-vdom" % scalaJsReactVersion,
    // optional but includes a Microsoft UI component set
    "ttg" %%% "scalajs-react-fabric" % scalaJsReactVersion,
    // optional unless you want to connect to redux state
    "ttg" %%% "scalajs-react-redux" % scalaJsReactVersion
    // if you need react-dom
    "ttg" %%% "scalajs-react-react-dom" % scalaJsReactVersion
    // if you need prop-types
    "ttg" %%% "scalajs-react-prop-types" % scalaJsReactVersion)
```

Then use the following imports to import functionality you need:

```scala
// standard scala.js
import scala.scalajs.js
import js.JSConverters._

// for working with the dom: "org.scalajs" %%% "jsdom" % "latest.version"
import org.scalajs.dom

// top level types
import ttg.react._

// imports functions for creating elements via statelessComponent("MyComponent")
import react.elements._

// imports renderToElementWithId, include this in the package with your top level render call
import react.reactdom._

// various converters, described below and in other sections
import react.implicits._

// contains virtual dom objects
import vdom._

// non-native JS trait style attributes
import vdom.tags._
import vdom.svgtags._

// import this if you are using the office-ui-fabric-react library, otherwise skip
import fabric

// import the core components
import fabric.components._
```

## Implicits
Most facade libraries and scala.js use implicit conversion to automatically convert scala world objects to javascript objects. Since this is a facade library that is designed to be integrated into other javascript/reason react applications, you will most likely need to use javascript objects on your imported objects as well as your exported components.

The `ttg.react.implicits._` imports a variety of syntax and automatic converters. Since implicit conversion can make your code base more complicated when automatic conversions go awry, the syntax enhancements and conversions are offered ala cart.
* syntax enhancements: Syntax enhancements require calling functions in an easy-to-use API to perform conversions. For example, you can use `YourComponent.make(..args...).toEl` instead of calling `elements.createElement(YourComponent.make(arg1, arg2, childrenarg))`.
* conversions: If you want to skip even calling `.toEl` import the implicits as shown above and whenever a ReactNode is needed (for example as a child), the Component will be automatically converted.

The syntax could be:
```scala
// import only the syntax enhancements
import ttg.react.syntax.all._
// import only conversions
import ttg.react.instances.all._
// imports both conversions and syntax
import ttg.react.implicits._
```

## npm and js packages
Based on the scala libraries you use, you will need to bundle the appropriate packages. All of the scala.js imports underneath in the interop layer convert to CJS `require` calls.

* core: react, create-react-class
* react-dom: react-dom
* prop-types: prop-types
* vdom: no js dependencies
* fabric: office-ui-fabric-react
* redux: react-redux, redux

Generally, if you include these in your package.json as runtime dependencies you should be all set. If you use `scalajs-bundler` you would need to add these to your build.sbt file.
