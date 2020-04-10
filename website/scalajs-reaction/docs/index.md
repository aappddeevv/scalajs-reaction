---
id: index
title: Getting Started
---

Like any scala.js project to get started you need to include the libraries in
your build. the artifacts are published to a bintray only and not jcenter or
maven.

```scala
// bintray resolvers
resolvers += Resolver.bintrayRepo("aappddeevv", "maven")
val scalaJsReactVersion = "<latest version> // e.g. "0.1.0-M7"

// grab the the latest version or use a specific version
libraryDependencies ++= Seq(
    "ttg" %%% "react" % scalaJsReactVersion,

    "ttg" %%% "vdom" % scalaJsReactVersion,

    // optional but includes a Microsoft UI component set
    "ttg" %%% "fabric" % scalaJsReactVersion,

    // optional unless you want to connect to redux state
    "ttg" %%% "react-redux" % scalaJsReactVersion

    // if you need react-dom
    "ttg" %%% "react-dom" % scalaJsReactVersion

    // if you need prop-types
    "ttg" %%% "prop-types" % scalaJsReactVersion
)
```

Then use the following imports to import functionality you need:

```scala
// standard scala.js
import scala.scalajs.js
import js.JSConverters._

// for working with the dom: "org.scalajs" %%% "jsdom" % "latest.version"
import org.scalajs.dom

// top level types
import react._
import react.implicits._

// imports renderToElementWithId, include this in the package with your top level render call
import react_dom._

// contains virtual dom objects
import vdom._

// non-native JS trait style attributes
import vdom._
import vdom.styling._
import vdom.svg._

// import this if you are using the office-ui-fabric-react library
import fabric._
import fabric.components._
import fabric.styling._
```

## Implicits

Most facade libraries and scala.js use implicit conversion to automatically
convert scala objects to javascript objects. Since this is a facade library that
is designed to be integrated into other javascript/reason react applications,
you will most likely need to use javascript objects on your imported objects as
well as your exported components.

The `react.implicits._` imports a variety of syntax and automatic
converters. Since implicit conversion can make your code base more complicated
when automatic conversions go awry, the syntax enhancements and conversions are
offered ala cart. However, you should use the general `import react.implicits._` 
when first starting out. See the API docs for ala cart imports.

## npm and js packages

Based on the scala libraries you use, you will need to bundle the appropriate
packages. All of the scala.js imports underneath in the interop layer convert to
CJS `require` calls.

* core: react (>=16.8+)
* react-dom: react-dom (>=16.8+)
* prop-types: prop-types
* vdom: no js dependencies
* fabric: office-ui-fabric-react (>= 7.0), or merge-styles directly for only that
* redux: react-redux, redux

If you include these in your package.json as runtime dependencies you
should be all set. If you use `scalajs-bundler` you would need to add these to
your build.sbt file.
